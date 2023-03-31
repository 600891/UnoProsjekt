import "./App.css";
import { Route, Routes } from "react-router-dom";
import Login from "./components/Login";
import Lobby from "./components/Lobby";
import GameRoom from "./components/GameRoom";
import Logout from "./components/Logout";
import CreateUser from "./components/CreateUser";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/lobby" element={<Lobby />} />
        <Route path="/gameroom" element={<GameRoom />} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/createuser" element={<CreateUser />} />
      </Routes>
    </div>
  );
}

export default App;
