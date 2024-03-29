import React from "react";
import { useState, useEffect } from "react";
import GameRoom from "./GameRoom";
import {
  chooseColor,
  drawCards,
  findMyPlayer,
  findNextPlayer,
  isColorLegal,
  isValueLegal,
  reverseDirection,
  skipPlayer,
  updateOwnHand,
} from "../utils/GameRules";
import _ from "lodash";

const PlayerHand = ({
  players,
  setPlayers,
  player,
  deck,
  discard,
  setDiscard,
  hand,
  changeHand,
  currentCard,
  setCurrentCard,
  currentColor,
  setCurrentColor,
  direction,
  setDirection,
  playerTurn,
  setPlayerTurn,
  turnPlayed,
}) => {
  const onCardPlayedHandler = (played_card) => {
    if (
      (isColorLegal(currentColor, played_card) ||
        isValueLegal(currentCard, played_card)) &&
      playerTurn === player.name
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
          if (played_card.cardColor === "black") {
            setCurrentColor(chooseColor());
          } else {
            setCurrentColor(played_card.cardColor);
          }
          setCurrentCard(played_card);
          setPlayerTurn(findNextPlayer(players, player, direction));
          break;
        case "reverse":
          console.log("Reverse card played " + direction);
          reverseDirection(
            direction,
            setDirection,
            player,
            players,
            setPlayerTurn
          );
          break;
        case "skip":
          console.log("Skip card played");
          skipPlayer(players, player, setPlayerTurn, direction);
          setCurrentColor(played_card.cardColor);
          setCurrentCard(played_card);
          break;
        case "draw":
          if (played_card.cardColor === "black") {
            console.log("Draw 4 played");
            setCurrentColor(chooseColor());
            setCurrentCard(played_card);
            setPlayerTurn(drawCards(players, 4, player, deck, direction));
          } else {
            console.log("Draw 2 played");
            setCurrentColor(played_card.cardColor);
            setCurrentCard(played_card);
            setPlayerTurn(drawCards(players, 2, player, deck, direction));
          }

          break;
        case "wild":
          setCurrentColor(chooseColor());
          setCurrentCard(played_card);
          setPlayerTurn(findNextPlayer(players, player, direction));
          break;
        default:
          console.log("Something went wrong");
          break;
      }
      let newHand = hand.filter(
        (card) => !_.isEqual(card.cardId, played_card.cardId)
      );
      setDiscard([played_card, ...discard]);
      updateOwnHand(players, setPlayers, player, newHand);
      turnPlayed(true);
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
