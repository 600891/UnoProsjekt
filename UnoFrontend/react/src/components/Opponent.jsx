import React from "react";

const Opponent = ({ opponent }) => {
  if (opponent && opponent.name !== "") {
    let handSize = opponent.hand.length;
    return (
      <div>
        {opponent && (
          <div class="opponentcontainer">
            <p>{opponent.name}</p>
            <p class="opponentinfo">{handSize}</p>
            <img
              className="Card"
              alt="Deck"
              src={require(`../assets/card-back.png`)}
            />
          </div>
        )}
      </div>
    );
  } else {
    return (
      <div>
        <div class="opponentcontainer">
          <p>---</p>
          <p class="opponentinfo">0</p>
          <img
            className="Card"
            alt="Deck"
            src={require(`../assets/card-back.png`)}
          />
        </div>
      </div>
    );
  }
};

export default Opponent;
