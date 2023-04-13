import React, { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import GameSessionCard from "./GameSessionCard";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

const Lobby = () => {
  //states
  // to display current game rooms. Must be fetched from backend
  const [gameRooms, setGameRooms] = useState([]);
  // sets the currnet user from backend
  const user = useRef({});
  const activeRoom = useRef(null);
  const isGameCreator = useRef(false);

  //**********
  // WEBSOCKETS
  //**********
  const ENDPOINT = "http://localhost:8080";
  const stompClientRef = useRef(null);

  // must fetch data to be displayed in the lobby and make sure the user is logged in
  const fetchData = () => {
    const setUser = {
      username: location.state.username,
      password: location.state.password,
      session: location.state.session,
    };
    user.current = setUser;
  };

  // connect to websocket for lobby and subscribe to lobby rooms, then get list of lobbies
  useEffect(() => {
    fetchData();
    const socket = new SockJS(ENDPOINT + "/uno");
    const stompClient = Stomp.over(socket);
    stompClient.connect({ username: location.state.username }, (frame) => {
      console.log("Connected to the websocket server " + frame);
      stompClient.subscribe("/user/topic/lobby", (message) =>
        onMessageReceived(message)
      );
      stompClient.subscribe("/topic/lobby", (message) =>
        onMessageReceived(message)
      );
      stompClient.send("/api/lobby", {}, {});
    });
    stompClientRef.current = stompClient;
  }, []);

  //**********
  // MESSAGE HANDLING
  //**********

  // called when the client receives a STOMP message from the server
  // checks the message type and does the appropriate action
  // IMPORTANT! This callback method can not use states.
  const onMessageReceived = (message) => {
    const messageObj = JSON.parse(message.body);
    console.log(messageObj);
    // handle all message types:

    // errorMessage-property
    if (messageObj.hasOwnProperty("errorMessage")) {
      console.log("Error Message: " + message);
    }
    // listOfGames-property
    else if (messageObj.hasOwnProperty("listOfGames")) {
      console.log(messageObj.listOfGames);
      showGameRooms(messageObj.listOfGames);
    } else if (messageObj.hasOwnProperty("event")) {
      const event = messageObj.event;
      switch (event) {
        // event-property - event er CREATE_GAME_EVENT
        case "CREATE_GAME_EVENT":
          if (messageObj.username === location.state.username) {
            // set that the user created a game if the current user was the one who created the game
            isGameCreator.current = true;
            activeRoom.current = messageObj.gameId;
            console.log(activeRoom.current);
          }
          // send a request for all game rooms when a new game is created
          stompClientRef.current.send("/api/lobby", {}, {});
          break;
        // event-property - event er JOIN_GAME_EVENT
        case "JOIN_GAME_EVENT":
          if (messageObj.username === location.state.username) {
            // set the users currently active room if you were the user joining the game
            activeRoom.current = messageObj.gameId;
          }

          // then re-fetch the games list
          stompClientRef.current.send("/api/lobby", {}, {});
          break;
        // event-property - event er LEAVE_GAME_EVENT
        case "LEAVE_GAME_EVENT":
          if (messageObj.username === location.state.username) {
            activeRoom.current = null;
          }
          stompClientRef.current.send("/api/lobby", {}, {});
          break;
        // event-property - event er START_GAME_EVENT
        case "START_GAME_EVENT":
          sendUsersToGame(messageObj.gameId);
          break;
        default:
          console.log("Could not handle message");
          break;
      }
    }
  };

  // **********
  // EVENT HANDLING
  // **********
  const location = useLocation();
  const navigate = useNavigate();

  // disconnect user and return to login screen
  const returnToLogin = () => {
    stompClientRef.current.disconnect(() => {
      console.log("User disconnected");
    });
    localStorage.clear();
    navigate("/", { replace: true });
  };

  // join an already created game
  const handleJoinGame = (gameRoom) => {
    const gameID = gameRoom.gameId;
    // Send message to join game
    if (stompClientRef !== null) {
      stompClientRef.current.send(`/api/lobby/game/join/${gameID}`, {}, {});
      //navigate("/gameroom", { replace: true });
    }
  };

  const handleLeaveGame = (gameRoom) => {
    // Send message to leave currently joined game
    if (stompClientRef !== null && activeRoom) {
      console.log(activeRoom.current);
      stompClientRef.current.send("/api/lobby/game/leave", {}, {});
    } else {
      console.log("You are not in a room");
    }
  };

  // will create a new game session
  const handleCreateSession = () => {
    // Send message to create game
    if (stompClientRef !== null) {
      stompClientRef.current.send("/api/lobby/game/create", {}, {});
    }
  };

  // handle when game creator presses start game button
  const handleStartGame = (gameRoom) => {
    // send start game message to backend
    if (stompClientRef !== null) {
      console.log(gameRoom);
      stompClientRef.current.send(`/api/lobby/game/start/${gameRoom}`, {}, {});
    }
  };

  function showGameRooms(message) {
    setGameRooms(message);
  }

  //**********
  // HELPER FUNCTIONS
  //**********

  function getRoomById(id) {
    gameRooms.forEach((gameRoom) => {
      if (gameRoom.gameId === id) {
        return gameRoom;
      }
    });
    return null;
  }

  function isRoomReady() {
    console.log(getRoomById(activeRoom.current).gameParticipants.length > 2);
    return getRoomById(activeRoom.current).gameParticipants.length > 2;
  }

  function sendUsersToGame(gameId) {
    console.log("Hello from sendUsersToGame");
    console.log(activeRoom.current);
    console.log(gameId);
    if (activeRoom.current === gameId) {
      // if you are in the started game, go to game room
      navigate("/gameroom", { replace: true });
    }
  }

  return (
    <div className="background-image">
      <div className="parent">
        <div className="center">
          <h1 className="white-text h1">Welcome to the lobby</h1>
          <div className="row">
            {gameRooms &&
              gameRooms.map((gameRoom, i) => (
                <div className="block" key={i}>
                  {activeRoom.current ? (
                    <GameSessionCard
                      key={i}
                      gameRoom={gameRoom}
                      onClick={() => handleLeaveGame(gameRoom)}
                      activeRoom={activeRoom.current}
                    />
                  ) : (
                    <GameSessionCard
                      key={i}
                      gameRoom={gameRoom}
                      onClick={() => handleJoinGame(gameRoom)}
                      activeRoom={activeRoom.current}
                    />
                  )}
                </div>
              ))}
          </div>
          {isGameCreator.current ? (
            <button
              className="button"
              onClick={() => handleStartGame(activeRoom.current)}
            >
              Start game
            </button>
          ) : (
            <button className="button" onClick={handleCreateSession}>
              Create new game session
            </button>
          )}
          <button className="button" onClick={returnToLogin}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default Lobby;
