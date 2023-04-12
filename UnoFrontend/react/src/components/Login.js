import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Login({ socket, stompClient }) {
  const [userName, setUsername] = useState("");
  const [password, setpassword] = useState("");
  const [session, setSession] = useState("");

  const [data, setData] = useState({});
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const params = {
    username: userName,
    password: password,
  };
  const options = {
    method: "POST",
    body: JSON.stringify(params),
    headers: { "Content-Type": "application/json" },
  };

  async function testPost() {
    await fetch("http://localhost:8080/login", options)
      .then((response) => response.text())
      .then((response) => {
        setSession(response);
      });
  }

  useEffect(() => {
    if (session != "") {
      localStorage.setItem("userName", userName);
      localStorage.setItem("session", session);
      navigate("/lobby", {
        state: { userName: userName, password: password, session: session },
      });
    }
  }, [session]);

  async function fetchData() {
    fetch("http://localhost:8080/login")
      .then((response) => response.text()) // parse response as text
      .then((data) => {
        console.log(data); // log the response string to the console
      })
      .catch((error) => console.error(error));
  }

  //code for logging the user in with an existing account. Must implement log in logic
  const login = () => {
    console.log(
      "Bruker trykket login med brukernavn:" +
        userName +
        " og passord: " +
        password
    );

    testPost();
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
        </div>
      </div>
    </div>
  );
}

export default Login;
