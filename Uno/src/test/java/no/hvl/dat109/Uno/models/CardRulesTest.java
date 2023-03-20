package no.hvl.dat109.Uno.models;

import no.hvl.dat109.Uno.service.RuleService;
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
    void isCardLegal(){
        Card playerCardRed = new Card(Color.RED);
        Card playerCardBlue = new Card(Color.BLUE);
        Card deckCardBlue = new Card(Color.BLUE);
        Assertions.assertTrue(CardRuleService.isCardPlayable(playerCardBlue, deckCardBlue));
        Assertions.assertFalse(CardRuleService.isCardPlayable(playerCardRed, deckCardBlue));
    }
}
