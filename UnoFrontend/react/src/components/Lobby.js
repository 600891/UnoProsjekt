import React, { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import GameSessionCard from "./GameSessionCard";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

function Lobby() {
  //states
  // to display current game rooms. Must be fetched from backend
  const [gameRooms, setGameRooms] = useState([]);
  // sets the currnet user from backend
  const [user, setUser] = useState({});
  const [activeRoom, setActiveRoom] = useState(null);
  const [isGameCreator, setIsGameCreator] = useState(false);

  //**********
  // WEBSOCKETS
  //**********
  const ENDPOINT = "http://localhost:8080";
  const stompClientRef = useRef(null);

  // must fetch data to be displayed in the lobby and make sure the user is logged in
  const fetchData = () => {
    setUser({
      username: location.state.username,
      password: location.state.password,
      session: location.state.session,
    });
  };

  // connect to websocket for lobby and subscribe to lobby rooms, then get list of lobbies
  useEffect(() => {
    fetchData();
    const socket = new SockJS(ENDPOINT + "/uno");
    const stompClient = Stomp.over(socket);
    stompClient.connect({ username: location.state.username }, (frame) => {
      console.log("Connected to the websocket server " + frame);
      stompClient.subscribe("/user/topic/lobby", onMessageReceived);
      stompClient.subscribe("/topic/lobby", onMessageReceived);
      stompClient.send("/api/lobby", {}, {});
    });
    stompClientRef.current = stompClient;
  }, []);

  //**********
  // MESSAGE HANDLING
  //**********

  // called when the client receives a STOMP message from the server
  // checks the message type and does the appropriate action
  const onMessageReceived = (message) => {
    const messageObj = JSON.parse(message.body);

    // handle all message types:

    // errorMessage-property
    if (messageObj.hasOwnProperty("errorMessage")) {
      console.log("Error Message: " + message);
    }
    // listOfGames-property
    else if (messageObj.hasOwnProperty("listOfGames")) {
      showGameRooms(messageObj.listOfGames);
    } else if (messageObj.hasOwnProperty("event")) {
      const event = messageObj.event;
      switch (event) {
        // event-property - event er CREATE_GAME_EVENT
        case "CREATE_GAME_EVENT":
          if (messageObj.username === location.state.username) {
            // set that the user created a game if the current user was the one who created the game
            setIsGameCreator(true);
            setActiveRoom(messageObj.gameId);
          }
          // send a request for all game rooms when a new game is created
          stompClientRef.current.send("/api/lobby", {}, {});
          break;
        // event-property - event er JOIN_GAME_EVENT
        case "JOIN_GAME_EVENT":
          if (messageObj.username === location.state.username) {
            // set the users currently active room if you were the user joining the game
            setActiveRoom(messageObj.gameId);
          }

          // then re-fetch the games list
          stompClientRef.current.send("/api/lobby", {}, {});
          break;
        // event-property - event er LEAVE_GAME_EVENT
        case "LEAVE_GAME_EVENT":
          console.log(messageObj.username);
          if (messageObj.username === location.state.username) {
            setActiveRoom(null);
          }
          stompClientRef.current.send("/api/lobby", {}, {});
          break;
        // event-property - event er START_GAME_EVENT
        case "START_GAME_EVENT":
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
      stompClientRef.current.send(`api/lobby/game/start/${gameRoom}`, {}, {});
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
    console.log(getRoomById(activeRoom).gameParticipants.length > 2);
    return getRoomById(activeRoom).gameParticipants.length > 2;
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
                  {activeRoom ? (
                    <GameSessionCard
                      key={i}
                      gameRoom={gameRoom}
                      onClick={() => handleLeaveGame(gameRoom)}
                      activeRoom={activeRoom}
                    />
                  ) : (
                    <GameSessionCard
                      key={i}
                      gameRoom={gameRoom}
                      onClick={() => handleJoinGame(gameRoom)}
                      activeRoom={activeRoom}
                    />
                  )}
                </div>
              ))}
          </div>
          {isGameCreator ? (
            <button
              className="button"
              disabled={!isRoomReady}
              onClick={() => handleStartGame(activeRoom)}
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
}

export default Lobby;
