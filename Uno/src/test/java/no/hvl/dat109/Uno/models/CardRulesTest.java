package no.hvl.dat109.Uno.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for card rules.
 * @author Oda Bastesen Storebo
 */
public class CardRulesTest {

    /**
     * Checks if cards match.
     * @return
     */
    @Test
    boolean isCardLegal(){
        Card playerCardRed = new Card(Color.RED);
        Card playerCardBlue = new Card(Color.BLUE);
        Card deckCardBlue = new Card(Color.BLUE);
        Assertions.assertTrue(CardRules.isCardLegal(playerCardBlue, deckCardBlue));
        Assertions.assertFalse(CardRules.isCardLegal(playerCardRed, deckCardBlue));
    }
}
