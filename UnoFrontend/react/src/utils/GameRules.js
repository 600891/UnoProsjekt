import React from "react";
import _ from "lodash";

// Helper functions with game rules
// Export every function instead of export default at the end

// Check if card played is legal

// First check the color
export function isColorLegal(
  setCurrentCard,
  currentColor,
  setCurrentColor,
  played_card
) {
  let playedColor = null;

  // Check if current top color is equal to the played cards color.
  // This method uses lodash to figure out equality between cards (_.isEqual)
  if (currentColor === played_card.cardColor) {
    console.log("colors matched!");
    playedColor = played_card.cardColor;

    setCurrentCard(played_card);
    setCurrentColor(played_card.cardColor);
    return true;
  } else if (played_card.cardColor === "black") {
    //ask for new color until a valid color is chosen
    while (playedColor === null) {
      playedColor = prompt(
        "Enter the color you want to change to (blue, red, green, yellow)"
      );
      if (playedColor !== null) playedColor.toLowerCase();
    }
    setCurrentCard(played_card);
    setCurrentColor(playedColor);
    return true;
  } else {
    console.log("Not a legal color! Try another card.");
    return false;
  }
}

// Value is legal if it is the same as the current card
export function isValueLegal(
  setCurrentCard,
  currentCard,
  played_card,
  setCurrentColor
) {
  if (currentCard.cardValue === played_card.cardValue) {
    console.log("Values matched!");
    setCurrentCard(played_card);
    setCurrentColor(played_card.cardColor);
    return true;
  }
  return false;
}
