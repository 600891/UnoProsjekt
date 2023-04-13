import React from "react";

function GameSessionCard(props) {
  const { gameRoom, onClick, activeRoom } = props;
  //console.log(gameRoom.gameId + " " + activeRoom);
  return (
    <div>
      <div className="framing">
        <div>
          {" "}
          <h2>{gameRoom.gameCreator} sitt rom</h2>
        </div>
        <div>
          {gameRoom.gameParticipants && (
            <p>Number of players: {gameRoom.gameParticipants.length}</p>
          )}
        </div>
        {activeRoom === gameRoom.gameId ? (
          <button className="button" onClick={onClick}>
            Leave game{" "}
          </button>
        ) : (
          <button className="button" onClick={onClick}>
            Join the game{" "}
          </button>
        )}
      </div>
    </div>
  );
}

export default GameSessionCard;
