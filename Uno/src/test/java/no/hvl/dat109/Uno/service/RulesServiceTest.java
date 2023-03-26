package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.models.Card;
import no.hvl.dat109.Uno.models.ColorEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class RulesServiceTest {

    private final CardRuleService ruleService;

    @Autowired
     public RulesServiceTest(CardRuleService ruleService){
         this.ruleService = ruleService;
     }

     private static Stream<Arguments> playableCardPairs() {
        return Stream.of(
            Arguments.of(new Card(0, ColorEnum.BLUE), new Card(1, ColorEnum.BLUE)),
            Arguments.of(new Card(2, ColorEnum.RED), new Card(3, ColorEnum.RED)),
            Arguments.of(new Card(4, ColorEnum.YELLOW), new Card(5, ColorEnum.YELLOW)),
            Arguments.of(new Card(6, ColorEnum.GREEN), new Card(7, ColorEnum.GREEN))
        );
    }

    @ParameterizedTest
    @MethodSource("playableCardPairs")
    void playableCardColorTest(Card card, Card otherCard){
        Assertions.assertTrue(CardRuleService.isCardPlayable(card, otherCard));
    }

    private static Stream<Arguments> unplayableCardCombinations() {
        return Stream.of(
            Arguments.of(new Card(0, ColorEnum.BLUE), new Card(1, ColorEnum.RED), new Card(3, ColorEnum.YELLOW), new Card(4, ColorEnum.GREEN)),
            Arguments.of(new Card(5, ColorEnum.GREEN), new Card(6, ColorEnum.BLUE), new Card(7, ColorEnum.RED), new Card(8, ColorEnum.YELLOW)),
            Arguments.of(new Card(9, ColorEnum.YELLOW), new Card(10, ColorEnum.GREEN), new Card(11, ColorEnum.BLUE), new Card(12, ColorEnum.RED)),
            Arguments.of(new Card(13, ColorEnum.RED), new Card(14, ColorEnum.YELLOW), new Card(15, ColorEnum.GREEN), new Card(16, ColorEnum.BLUE))
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
