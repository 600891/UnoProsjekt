import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

function Lobby() {
    const location = useLocation();
    console.log(location);
    const navigate = useNavigate();
    const returnToLogin = () => {

        navigate("/", { replace: true });
    }
    const joinGame = () => {
        navigate("/gameroom", { replace: true })

    }

    return (
        <div>
            <h1>Welcome to the lobby {location.state ? location.state.userName : ""} </h1>


            <button onClick={joinGame}>Join UNO-Session</button>
            <button onClick={returnToLogin}>Logout</button>
        </div>

    )

}

export default Lobby;