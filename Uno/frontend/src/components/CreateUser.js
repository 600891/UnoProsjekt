import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function CreateUser() {
  const [username, setUsername] = useState("");
  const [password, setpassword] = useState("");
  const [loggedIn, setLogin] = useState(false);
  const navigate = useNavigate();

  const params = {
    username: username,
    password: password,
  };
  const options = {
    method: "POST",
    body: JSON.stringify(params),
    headers: { "Content-Type": "application/json" },
  };

  async function getServerResponse() {
    await fetch("http://localhost:8080/login", options)
      .then((response) => response.text())
      .then((response) => {
        console.log("The response from loginController is: " + response);
        if (response === "true") setLogin(true);
      });
  }

  useEffect(() => {
    if (loggedIn) {
      localStorage.setItem("username", username);
      localStorage.setItem("session", loggedIn);
      navigate("/lobby", {
        state: { username: username, password: password, session: loggedIn },
      });
    }
  }, [loggedIn]);

  //code for logging creating a new user and logging the user in. Must implement log in logic
  const createNewUser = () => {
    console.log(
      "New user created with username: " +
        username +
        " and password: " +
        password
    );

    getServerResponse();
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
                value={username}
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
          <button className="button" onClick={createNewUser}>
            Log in to this UNO-session
          </button>
        </div>
      </div>
    </div>
  );
}

export default CreateUser;
