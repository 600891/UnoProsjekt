import React from "react";

const Opponent = ({ opponent }) => {
  if (opponent && opponent.name !== "") {
    let handSize = opponent.hand.length;
    return (
      <div>
        {opponent && (
          <div className="opponentcontainer">
            <img
              className="Card"
              alt="Deck"
              src={require(`../assets/card-back.png`)}
            />
            <p className="opponentinfo">
              {opponent.name} <br /> has {handSize} cards.
            </p>
          </div>
        )}
      </div>
    );
  } else {
    return (
      <div>
        <div className="opponentcontainer">
          <img
            className="Card"
            alt="Deck"
            src={require(`../assets/card-back.png`)}
          />
          <p className="opponentinfo">No player</p>
        </div>
      </div>
    );
  }
};

export default Opponent;
