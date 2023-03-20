package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.models.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static no.hvl.dat109.Uno.models.Color.*;

@SpringBootTest
public class RulesServiceTest {

    private final CardRuleService ruleService;

    @Autowired
     public RulesServiceTest(CardRuleService ruleService){
         this.ruleService = ruleService;
     }

     private static Stream<Arguments> playableCardPairs() {
        return Stream.of(
            Arguments.of(new Card(BLUE), new Card(BLUE)),
            Arguments.of(new Card(RED), new Card(RED)),
            Arguments.of(new Card(YELLOW), new Card(YELLOW)),
            Arguments.of(new Card(GREEN), new Card(GREEN))
        );
    }

    @ParameterizedTest
    @MethodSource("playableCardPairs")
    void playableCardColorTest(Card card, Card otherCard){
        Assertions.assertTrue(CardRuleService.isCardPlayable(card, otherCard));
    }

    private static Stream<Arguments> unplayableCardCombinations() {
        return Stream.of(
            Arguments.of(new Card(BLUE), new Card(RED), new Card(YELLOW), new Card(GREEN)),
            Arguments.of(new Card(GREEN), new Card(BLUE), new Card(RED), new Card(YELLOW)),
            Arguments.of(new Card(YELLOW), new Card(GREEN), new Card(BLUE), new Card(RED)),
            Arguments.of(new Card(RED), new Card(YELLOW), new Card(GREEN), new Card(BLUE))
        );
    }

    @ParameterizedTest
    @MethodSource("unplayableCardCombinations")
    void unplayableCardColorTest(Card cardOne, Card cardTwo, Card cardThree, Card cardOnTable) {
        Assertions.assertFalse(CardRuleService.isCardPlayable(cardOne, cardOnTable));
        Assertions.assertFalse(CardRuleService.isCardPlayable(cardTwo, cardOnTable));
        Assertions.assertFalse(CardRuleService.isCardPlayable(cardThree, cardOnTable));
    }

}
