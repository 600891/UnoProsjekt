import React from "react";
import { useState, useEffect } from "react";
import "lodash";

const DiscardPile = ({ currentTopCard }) => {
  return (
    <div>
      {currentTopCard && (
        <div>
          <img
            className="Card"
            alt="DiscardPile"
            src={require(`../assets/cards-front/${
              currentTopCard.cardValue + currentTopCard.cardColor
            }.png`)}
          />
        </div>
      )}
    </div>
  );
};

export default DiscardPile;
