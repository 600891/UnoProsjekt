import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [userName, setUsername] = useState("");
    const navigate = useNavigate();

    const login = () => {
        console.log("Bruker trykket login med brukernavn:" + userName)

        navigate("/lobby",{
            state:{ userName: userName}

        })

    }

    return (

        <div>
            <h1>Choose a username for this UNO game</h1>
            <input value={userName} onChange={(e) => setUsername(e.target.value)}></input>

            <button onClick={login}>trykk for å logge inn i UNO</button>
        </div>
    )

}

export default Login;