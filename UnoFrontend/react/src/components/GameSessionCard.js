import React from "react";

function GameSessionCard(props) {
  const { gameRoomName } = props;

  return (
    <div>
      <div className="framing">
        <div>
          {" "}
          <h2>{gameRoomName}</h2>
        </div>
        <div>
          <p>Number of players: </p>
        </div>
        <button className="button">Join the game </button>
      </div>
    </div>
  );
}

export default GameSessionCard;