import React from "react";
import _ from "lodash";

// Helper functions with game rules
// Export every function instead of export default at the end

// Check if card played is legal

// First check the color
export function isColorLegal(currentColor, played_card) {
  if (
    currentColor === played_card.cardColor ||
    played_card.cardColor === "black"
  ) {
    console.log("Legal color played!");
    return true;
  } else {
    console.log("Illegal color played.");
    return false;
  }
}

// Value is legal if it is the same as the current card
export function isValueLegal(currentCard, played_card) {
  if (
    currentCard.cardValue === played_card.cardValue ||
    played_card.cardColor === "black"
  ) {
    console.log("Legal value played!");
    return true;
  }
  return false;
}

// Set new play direction opposite of the current direction
export function reverseDirection(direction, setDirection) {
  if (direction === "clockwise") {
    setDirection("counterclockwise");
  } else {
    setDirection("clockwise");
  }
}

export function chooseColor() {
  let playedColor = null;
  while (playedColor === null) {
    playedColor = prompt(
      "Enter the color you want to change to (blue, red, green, yellow)"
    );
    if (playedColor !== null) playedColor.toLowerCase();
    if (
      playedColor !== "green" &&
      playedColor !== "blue" &&
      playedColor !== "yellow" &&
      playedColor !== "red"
    ) {
      console.log("Wrong color named entered.");
      playedColor = null;
    }
  }
  return playedColor;
}

export function skipPlayer(players, yourPlayer, setPlayerTurn, direction) {
  // To find which player should get the turn, we need the list of players. It is always in the same order. If clockwise, it is the player +2 up from us, if counterclockwise -2
  // First, find our place in the list
  for (let i = 0; i < players.length; i++) {
    if (players.at(i) === yourPlayer) {
      // Give the turn to the player 2 away from us in the right direction
      if (direction === "clockwise") {
        setPlayerTurn(players.at((i + 2) % players.length).name);
      } else {
        setPlayerTurn(players.at((i - 2) % players.length).name);
      }
    }
  }
}

export function drawCards(players, amount, yourPlayer, deck, direction) {
  // Find which player should draw cards
  for (let i = 0; i < players.length; i++) {
    if (players.at(i) === yourPlayer) {
      // Add two cards from deck to next players hand
      let player;
      if (direction === "clockwise") {
        player = players.at((i + 1) % players.length);
      } else {
        player = players.at((i - 1) % players.length);
      }
      for (let j = 0; j < amount; j++) {
        player.hand.push(deck.shift());
      }
    }
  }
}

export function findNextPlayer(players, yourPlayer, direction) {
  let player;
  for (let i = 0; i < players.length; i++) {
    if (players.at(i) === yourPlayer) {
      if (direction === "clockwise") {
        player = players.at((i + 1) % players.length).name;
      } else {
        player = players.at((i - 1) % players.length).name;
      }
    }
  }
  console.log("Next player is " + player);
  return player;
}
