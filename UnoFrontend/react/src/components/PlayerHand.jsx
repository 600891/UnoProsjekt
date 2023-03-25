import React from "react";
import { useState, useEffect } from "react";
import _ from "lodash";

const PlayerHand = ({ name, hand, changeCards, currentTopCard }) => {
  let currentColor;
  const onCardPlayedHandler = (played_card) => {
    // Check if color is legal
    if (
      currentTopCard.cardColor === played_card.cardColor ||
      played_card.cardColor === "black"
    ) {
      console.log("colors matched!");
      let newHand = hand.filter(
        (card) => !_.isEqual(card.cardId, played_card.cardId)
      );
      changeCards(newHand);
      if (played_card.cardColor === "black") {
        //ask for new color
        currentColor = prompt(
          "Enter the color you want to change to (blue, red, green, yellow"
        ).toLowerCase();
      }
    }
    // Check if value is legal
    if (currentTopCard.cardValue === played_card.cardValue) {
    }

    // Remove played_card from hand

    // Add played_card to discard pile
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
