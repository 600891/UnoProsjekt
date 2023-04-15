import React, { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation, redirect, Navigate } from "react-router-dom";
import GameSessionCard from "./GameSessionCard";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

const Lobby = () => {
  //Check if the user is logged in. //TODO Should implement a call to a controller to check if user is logged in
  let isLoggedIn = localStorage.getItem("session");
  //states
  // to display current game rooms. Must be fetched from backend
  const [gameRooms, setGameRooms] = useState([]);
  // sets the currnet user from backend
  const [activeRoom, setActiveRoom] = useState(() => {
    // getting stored value
    const saved = localStorage.getItem("activeRoom");

    const initialValue = saved;
    return initialValue || null;
  });
  const [isGameCreator, setIsGameCreator] = useState(() => {
    // getting stored value
    const saved = localStorage.getItem("isGameCreator");
    const initialValue = JSON.parse(saved);
    return initialValue || false;
  });

  //**********
  // WEBSOCKETS
  //**********
  const ENDPOINT = "http://localhost:8080";

  const stompClientRef = useRef(() => {
    const saved = localStorage.getItem("stompClient");
    const initialValue = JSON.parse(saved);
    return initialValue || null;
  });

  // must fetch data to be displayed in the lobby and make sure the user is logged in
  const fetchData = () => {};

  // connect to websocket for lobby and subscribe to lobby rooms, then get list of lobbies
  useEffect(() => {
    fetchData();
    const socket = new SockJS(ENDPOINT + "/uno");
    const stompClient = Stomp.over(socket);
    stompClient.connect(
      { username: localStorage.getItem("username") },
      (frame) => {
        console.log("Connected to the websocket server " + frame);
        stompClient.subscribe("/user/topic/lobby", (message) =>
          onMessageReceived(message)
        );
        stompClient.subscribe("/topic/lobby", (message) =>
          onMessageReceived(message)
        );
        stompClient.send("/api/lobby", {}, {});
      }
    );
    stompClientRef.current = stompClient;
  }, []);

  useEffect(() => {
    localStorage.setItem("activeRoom", activeRoom);
  }, [activeRoom]);

  useEffect(() => {
    localStorage.setItem("isGameCreator", isGameCreator);
  }, [isGameCreator]);

  useEffect(() => {
    localStorage.setItem("stompClient", stompClientRef.current);
  }, [stompClientRef]);

  //**********
  // MESSAGE HANDLING
  //**********

  // called when the client receives a STOMP message from the server
  // checks the message type and does the appropriate action
  // IMPORTANT! This callback method can not read states, only update them.
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
          if (messageObj.username === location.state.username) {
            setIsGameCreator(false);
            setActiveRoom(null);
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
    if (stompClientRef !== null && activeRoom !== null) {
      console.log(activeRoom);
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
    console.log(getRoomById(activeRoom).gameParticipants.length > 2);
    return getRoomById(activeRoom).gameParticipants.length > 2;
  }

  function sendUsersToGame(gameId) {
    if (localStorage.getItem("activeRoom") === gameId) {
      // if you are in the started game, go to game room
      // Note - all neccessary states are saved in localStorage and can be retrieved in gameRoom
      navigate("/gameroom", { replace: true });
    }
  }
  // Will redirect the user to the log in page if not logged in
  if (!isLoggedIn) {
    return <Navigate to="/" />;
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
                  {activeRoom === gameRoom.gameId ? (
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
              onClick={() => handleStartGame(localStorage.getItem(activeRoom))}
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
