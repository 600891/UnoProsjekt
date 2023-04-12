import React from "react";

function GameSessionCard(props) {
  const { gameRoomName, gameParticipants } = props;

  return (
    <div>
      <div className="framing">
        <div>
          {" "}
          <h2>{gameRoomName}</h2>
        </div>
        <div>
          {gameParticipants && <p>Number of players: {gameParticipants}</p>}
        </div>
        <button className="button">Join the game </button>
      </div>
    </div>
  );
}

export default GameSessionCard;
