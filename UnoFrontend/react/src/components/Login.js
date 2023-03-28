import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [userName, setUsername] = useState("");
  const [password, setpassword] = useState("");

  const [data, setData] = useState("");
  const navigate = useNavigate();

  async function fetchData() {
    const response = await fetch("/login");
    const body = await response.json();
    setData(body);
    console.log(data);
  }

  //code for logging the user in with an existing account. Must implement log in logic
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
  // Redirects users to the createuser page
  const createUserNav = () => {
    navigate("/createuser");
  };

  return (
    <div className="background-image">
      <div className="parent">
        <div className="center">
          <h1 className="white-text h1">Please sign in to play!</h1>
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
          <button className="button" onClick={createUserNav}>
            Don't have an account yet??
          </button>

          <div>
            <button onClick={fetchData}>TestButton</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
