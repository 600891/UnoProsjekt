package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.persistence.entity.Card;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    /**
     * Checks if the card a player wants to play is playable.
     * @param cardToPlay the card the player wants to play
     * @param cardOnTable the card at the top of the card stack of played cards on the table
     * @return true if card is playable and false if card is not playable
     */
    public boolean isCardPlayable(Card cardToPlay, Card cardOnTable) {
        return cardToPlay.getColor().equals(cardOnTable.getColor());
    }

}
