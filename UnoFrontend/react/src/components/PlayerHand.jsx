import React from "react";
import { useState, useEffect } from "react";
import _ from "lodash";

const PlayerHand = ({
  name,
  hand,
  changeCards,
  currentTopCard,
  setCurrentCard,
  currentColor,
  setCurrentColor,
}) => {
  const onCardPlayedHandler = (played_card) => {
    let playedColor = null;
    // Check if color is legal
    if (currentColor === played_card.cardColor) {
      console.log("colors matched!");
      let newHand = hand.filter(
        (card) => !_.isEqual(card.cardId, played_card.cardId)
      );
      changeCards(newHand);
      playedColor = played_card.cardColor;

      setCurrentCard(played_card);
      setCurrentColor(currentColor);
    } else if (played_card.cardColor === "black") {
      //ask for new color until a valid color is chosen
      while (playedColor === null) {
        playedColor = prompt(
          "Enter the color you want to change to (blue, red, green, yellow)"
        );
        if (playedColor !== null) playedColor.toLowerCase();
      }
      let newHand = hand.filter(
        (card) => !_.isEqual(card.cardId, played_card.cardId)
      );
      changeCards(newHand);
      setCurrentCard(played_card);
      setCurrentColor(playedColor);
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
