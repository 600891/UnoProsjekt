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

  const ENDPOINT = "http://localhost:8080";
  let socket = new SockJS(ENDPOINT + "/uno");
  let stompClient = Stomp.over(socket);

  const location = useLocation();
  const navigate = useNavigate();

  const returnToLogin = () => {
    navigate("/", { replace: true });
  };

  // must fetch data to be displayed in the lobby and make sure the user is logged in
  const fetchData = () => {
    setUser({
      username: location.state.userName,
      password: location.state.password,
      session: location.state.session,
    });
  };
  const joinGame = () => {
    navigate("/gameroom", { replace: true });
  };

  // connect to websocket for lobby
  useEffect(() => {
    stompClient.connect({ username: location.state.userName }, (frame) => {
      console.log("Connected to the websocket server " + frame);

      stompClient.subscribe("/user/topic/lobby", onMessageReceived);

      stompClient.subscribe("/topic/lobby", onMessageReceived);
    });
  }, []);

  const onMessageReceived = (message) => {
    // called when the client receives a STOMP message from the server
    if (JSON.parse(message.body).hasOwnProperty("errorMessage")) {
      console.log("Error Message: " + message);
    } else if (message) {
      console.log("got message with body " + message);
      showGameRooms(message);
    } else {
      alert("got empty message");
    }
  };

  // will create a new game session
  const handleCreateSession = () => {
    stompClient.send("/api/lobby/game/create", {}, {});
  };

  function showGameRooms(message) {
    const newGameRoom = JSON.parse(message.body);
    setGameRooms((oldGameRooms) => [...oldGameRooms, newGameRoom]);
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
            {gameRooms.map((gameRoom) => (
              <div className="block">
                <GameSessionCard
                  gameRoomName={gameRoom.gameCreator + " sitt rom"}
                  gameParticipants={gameRoom.gameParticipants.length}
                />
              </div>
            ))}
          </div>
          <button className="button" onClick={joinGame}>
            Join UNO-Session
          </button>
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
