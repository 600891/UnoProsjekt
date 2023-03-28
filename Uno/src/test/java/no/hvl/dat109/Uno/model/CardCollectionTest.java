package no.hvl.dat109.Uno.model;

import no.hvl.dat109.Uno.models.Card;
import no.hvl.dat109.Uno.models.CardCollection;
import no.hvl.dat109.Uno.enums.ColorEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Test

import java.util.List;

public class CardCollectionTest {

    CardCollection deck;
    CardCollection deckShuffle;

    @BeforeEach
    public void initEach(){
        deck = new CardCollection();
        deckShuffle = new CardCollection();
        deckShuffle.shuffleDeck();
    }





    @Test
    void shuffleDeck() {
        Assertions.assertNotEquals(deck, deckShuffle);
        deck.shuffleDeck();
        Assertions.assertNotEquals(deck, deckShuffle);


    }


    @Test
    void takeCard() {
        List<Card> currentDeck = deckShuffle.getDeck();

        Card topCard = currentDeck.get(0);

        Assertions.assertEquals(topCard, deckShuffle.takeCard());
        Assertions.assertEquals(deckShuffle.getDeckSize(), deck.getDeckSize()-1);

        topCard = currentDeck.get(0);

        Assertions.assertEquals(topCard, deckShuffle.takeCard());
        Assertions.assertEquals(deckShuffle.getDeckSize(), deck.getDeckSize()-2);


    }

    @Test
    void addCardAndEmptyDeck() {
        Card blue = new Card(0, ColorEnum.BLUE);
        Card yellow = new Card(1, ColorEnum.YELLOW);

        deckShuffle.emptyDeck();
        Assertions.assertEquals(deckShuffle.getDeckSize(), 0);

        deckShuffle.addCard(blue);
        Assertions.assertEquals(deckShuffle.getDeckSize(), 1);
        deckShuffle.addCard(yellow);
        Assertions.assertEquals(deckShuffle.getDeckSize(), 2);

        deckShuffle.emptyDeck();
        Assertions.assertEquals(deckShuffle.getDeckSize(), 0);

        deckShuffle.addCard(blue);
        deckShuffle.addCard(blue);
        deckShuffle.addCard(blue);
        deckShuffle.addCard(yellow);
        deckShuffle.addCard(yellow);
        deckShuffle.addCard(yellow);
        Assertions.assertEquals(deckShuffle.getDeckSize(), 6);

    }

    @Test
    void setDeck() {
        Assertions.assertNotEquals(deck, deckShuffle);

        deck.setDeck(deckShuffle.getDeck());
        Assertions.assertEquals(deck.getDeck(), deckShuffle.getDeck());
    }


}
