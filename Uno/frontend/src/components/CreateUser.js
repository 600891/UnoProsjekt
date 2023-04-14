import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function CreateUser() {
  const [userName, setUsername] = useState("");
  const [password, setpassword] = useState("");
  const navigate = useNavigate();

  //code for logging creating a new user and logging the user in. Must implement log in logic
  const login = () => {
    console.log(
      "Bruker trykket login med brukernavn:" +
        userName +
        " og passord: " +
        password
    );

    navigate("/lobby", {
      state: { userName: userName, password },
    });
  };

  return (
    <div className="background-image">
      <div className="parent">
        <div className="center">
          <h1 className="white-text h1">
            Please choose a username and password
          </h1>
          <div>
            <div className="inputfield">
              <label style={{ marginRight: 9 }} className="white-text">
                Username
              </label>
              <input
                className="input"
                value={userName}
                onChange={(e) => setUsername(e.target.value)}
              ></input>
            </div>
            <div className="inputfield">
              <label style={{ marginRight: 10 }} className="white-text label">
                Password{" "}
              </label>
              <input
                type="password"
                className="input"
                value={password}
                onChange={(e) => setpassword(e.target.value)}
              ></input>
            </div>
          </div>
          <button className="button" onClick={login}>
            Log in to this UNO-session
          </button>
        </div>
      </div>
    </div>
  );
}

export default CreateUser;
