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

  let socket = null;
  let stompClient = null;
  const ENDPOINT = "http://localhost:8080";

  const location = useLocation();
  console.log(location);
  const navigate = useNavigate();
  const returnToLogin = () => {
    navigate("/", { replace: true });
  };
  // must fetch data to be displayed in the lobby and make sure the user is logged in
  const fetchData = () => {};
  const joinGame = () => {
    navigate("/gameroom", { replace: true });
  };
  // will create a new game session
  const handleCreateSession = () => {};

  // connect to websocket for lobby
  useEffect(() => {
    setUser({
      username: location.state.userName,
      password: location.state.password,
      session: location.state.session,
    });
    socket = new SockJS(ENDPOINT + "/uno");
    stompClient = Stomp.over(socket);

    stompClient.connect({ username: user.username }, (frame) => {
      console.log("Connected to the websocket server " + frame);

      stompClient.subscribe(ENDPOINT + "/topic/lobby", (message) => {
        console.log("Received message:", message);
      });
    });
  }, []);

  return (
    <div className="background-image">
      <div className="parent">
        <div className="center">
          <h1 className="white-text h1">Welcome to the lobby</h1>
          <div className="row">
            <div className="block">
              <GameSessionCard gameRoomName="Stian sitt rom" />
            </div>
            <div className="block">
              <GameSessionCard gameRoomName="Leif sitt rom" />
            </div>
            <div className="block">
              <GameSessionCard gameRoomName="Gygrid sitt rom" />
            </div>
            <div className="block">
              <GameSessionCard gameRoomName="Unorom" />
            </div>
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
