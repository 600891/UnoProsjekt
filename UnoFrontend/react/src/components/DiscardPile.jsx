import React from "react";
import { useState, useEffect } from "react";
import "lodash";


const DiscardPile = ({currentTopCard, DiscardPile}) => {
  const onCardPlayedHandler = (played_card) => {
    if(currentTopCard.cardColor === played_card.cardColor || played_card.cardColor === "black"){
      console.log("colors matched!");
      
    }
  }

  return (
    <div>
      {currentTopCard && (
        <div>
          <img
            className="Card"
            alt="card"
            onClick={() => onCardPlayedHandler()}
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
