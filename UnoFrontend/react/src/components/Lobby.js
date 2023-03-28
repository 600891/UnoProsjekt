import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

function Lobby() {
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
