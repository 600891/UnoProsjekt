import React from "react";
import { useState, useEffect } from "react";
import _ from "lodash";
import PlayerHand from "./PlayerHand";

const Deck = ({name, setDeck, deck, setYourPlayerHand, yourPlayerHand}) => {
    const onCardDrawHandler = () => {
        const drawedCard = deck[0];
        let newDeck = deck.filter(
            (card) => !_.isEqual(card.cardId, drawedCard.cardId)
        );
        setDeck(newDeck);

        const newPlayerHand = [...yourPlayerHand, drawedCard];
        setYourPlayerHand(newPlayerHand);
    }
    

    return(
        <div>
        {deck && (
            <div>
                {deck.length !== 0 ?
                <img
                    className="Card"
                    alt="Deck"
                    onClick = {() => onCardDrawHandler()}
                    src={require(`../assets/card-back.png`)}
                />
                :<p>Deck is empty!</p>
                }
            </div>
            )} 
        </div>
    );
};

export default Deck;