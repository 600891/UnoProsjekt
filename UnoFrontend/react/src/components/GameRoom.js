import { useNavigate } from "react-router-dom";

function GameRoom() {

    const navigate = useNavigate();

    const returnToLobby = () =>{
        navigate("/lobby", {replace:true});
    }

    return (

        <div>

            <h1>You have entered the game room!</h1>
            <button onClick={returnToLobby}>Go back to lobby</button>
        </div>

    )
}

export default GameRoom;