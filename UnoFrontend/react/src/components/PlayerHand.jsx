import React from "react";
import { useState, useEffect } from "react";
import { isColorLegal, isValueLegal } from "../utils/GameRules";
import _ from "lodash";

const PlayerHand = ({
  name,
  hand,
  changeHand,
  currentCard,
  setCurrentCard,
  currentColor,
  setCurrentColor,
}) => {
  const onCardPlayedHandler = (played_card) => {
    if (
      isColorLegal(
        setCurrentCard,
        currentColor,
        setCurrentColor,
        played_card
      ) ||
      isValueLegal(setCurrentCard, currentCard, played_card, setCurrentColor)
    ) {
      let newHand = hand.filter(
        (card) => !_.isEqual(card.cardId, played_card.cardId)
      );
      changeHand(newHand);
    }
  };

  return (
    <div>
      {hand && (
        <div className="yourPlayer">
          <p className="yourPlayerHand">{name}</p>
          {hand.map((item, i) => (
            <img
              key={i}
              className="Card"
              alt="card"
              onClick={() => onCardPlayedHandler(item)}
              src={require(`../assets/cards-front/${
                item.cardValue + item.cardColor
              }.png`)}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default PlayerHand;
