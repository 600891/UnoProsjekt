import React from "react";
import { useState, useEffect } from "react";
import GameRoom from "./GameRoom";
import {
  isColorLegal,
  isValueLegal,
  reverseDirection,
} from "../utils/GameRules";
import _ from "lodash";

const PlayerHand = ({
  player,
  hand,
  changeHand,
  currentCard,
  setCurrentCard,
  currentColor,
  setCurrentColor,
  direction,
  setDirection,
  turnHandler,
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
      // Handle special card logic in switch state
      switch (played_card.cardValue) {
        case "one":
        case "two":
        case "three":
        case "four":
        case "five":
        case "six":
        case "seven":
        case "eight":
        case "nine":
        case "zero":
          console.log("Regular card played.");
          break;
        case "reverse":
          console.log("Reverse card played " + direction);
          reverseDirection(direction, setDirection);
          break;
        case "skip":
          console.log("Skip card played");
          break;
        case "draw":
          console.log("Draw 2 played");
          break;
        case "drawwild":
          console.log("Draw 4 played");
          break;
        default:
          console.log("Something went wrong");
          break;
      }
      let newHand = hand.filter(
        (card) => !_.isEqual(card.cardId, played_card.cardId)
      );
      changeHand(newHand);
      turnHandler();
    }
  };

  return (
    <div>
      {player && (
        <div className="yourPlayer">
          <p className="yourPlayerHand">{player.name}</p>
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
