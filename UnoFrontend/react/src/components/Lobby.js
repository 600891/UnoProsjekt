import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import GameSessionCard from "./GameSessionCard";

function Lobby() {
  //states
  // to display current game rooms. Must be fetched from backend
  const [gameRooms, setGameRooms] = useState([]);
  // sets the currnet user from backend
  const [user, setUser] = useState({});

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
