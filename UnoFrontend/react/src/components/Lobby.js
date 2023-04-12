import React, { useState, useEffect } from "react";
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

  //**********
  // WEBSOCKETS
  //**********
  const ENDPOINT = "http://localhost:8080";
  const socket = new SockJS(ENDPOINT + "/uno");
  const stompClient = Stomp.over(socket);

  // must fetch data to be displayed in the lobby and make sure the user is logged in
  const fetchData = () => {
    setUser({
      username: location.state.userName,
      password: location.state.password,
      session: location.state.session,
    });
  };

  // connect to websocket for lobby and subscribe to lobby rooms
  useEffect(() => {
    stompClient.connect({ username: location.state.userName }, (frame) => {
      console.log("Connected to the websocket server " + frame);
      stompClient.subscribe("/user/topic/lobby", onMessageReceived);
      stompClient.subscribe("/topic/lobby", onMessageReceived);
      stompClient.send("/api/lobby", {}, {});
    });
  }, []);

  const onLobbyEnter = () => {
    if (!user.username.trim()) {
      return;
    }
    //onLogin(user.username);
  };

  // called when the client receives a STOMP message from the server
  // checks the message type and does the appropriate action
  const onMessageReceived = (message) => {
    const messageObj = JSON.parse(message.body);

    if (messageObj.hasOwnProperty("errorMessage")) {
      console.log("Error Message: " + message);
    } else if (messageObj.hasOwnProperty("listOfGames")) {
      console.log("List of games: " + message);
      messageObj.listOfGames.forEach((element) => {
        console.log(element);
        showGameRooms(element);
      });
    } else if (messageObj) {
      console.log("Created game: " + message);
      showGameRooms(messageObj);
    } else {
      alert("got empty message");
    }
  };

  // **********
  // NAVIGATION
  // **********
  const location = useLocation();
  const navigate = useNavigate();

  // disconnect user and return to login screen
  const returnToLogin = () => {
    stompClient.disconnect(() => {
      console.log("User disconnected");
    });
    localStorage.removeItem("userName");
    navigate("/", { replace: true });
  };

  const joinGame = () => {
    navigate("/gameroom", { replace: true });
  };

  // will create a new game session
  const handleCreateSession = () => {
    stompClient.send("/api/lobby/game/create", {}, {});
  };

  function showGameRooms(message) {
    console.log(message);
    setGameRooms((oldGameRooms) => [...oldGameRooms, message]);
  }

  useEffect(() => {
    console.log(gameRooms);
  }, [gameRooms]);

  return (
    <div className="background-image">
      <div className="parent">
        <div className="center">
          <h1 className="white-text h1">Welcome to the lobby</h1>
          <div className="row">
            {gameRooms &&
              gameRooms.map((gameRoom) => (
                <div className="block">
                  <GameSessionCard
                    gameRoomName={gameRoom.gameCreator + " sitt rom"}
                    gameParticipants={gameRoom.gameParticipants.length}
                    onClick={joinGame}
                  />
                </div>
              ))}
          </div>
          <button className="button" onClick={handleCreateSession}>
            Create new game session
          </button>
          <button className="button" onClick={returnToLogin}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
}

export default Lobby;
