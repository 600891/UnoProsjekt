package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.enums.ColorEnum;
import no.hvl.dat109.Uno.enums.ValueEnum;
import no.hvl.dat109.Uno.persistence.entity.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class CardRulesServiceTest {

    private final CardRuleService ruleService;

    @Autowired
     public CardRulesServiceTest(CardRuleService ruleService){
         this.ruleService = ruleService;
     }

     private static Stream<Arguments> playableCardPairs() {
        return Stream.of(
            Arguments.of(new Card(0, ColorEnum.BLUE, ValueEnum.FIVE), new Card(1, ColorEnum.BLUE, ValueEnum.FOUR)),
            Arguments.of(new Card(2, ColorEnum.RED, ValueEnum.ONE), new Card(3, ColorEnum.RED, ValueEnum.ONE)),
            Arguments.of(new Card(4, ColorEnum.YELLOW, ValueEnum.REVERSE), new Card(5, ColorEnum.YELLOW, ValueEnum.NINE)),
            Arguments.of(new Card(6, ColorEnum.GREEN, ValueEnum.SIX), new Card(7, ColorEnum.GREEN, ValueEnum.FOUR))
        );
    }

    @ParameterizedTest
    @MethodSource("playableCardPairs")
    void playableCardColorTest(Card card, Card otherCard){
        Assertions.assertTrue(ruleService.isCardPlayable(card, otherCard));
    }

    private static Stream<Arguments> unplayableCardCombinations() {
        return Stream.of(
            Arguments.of(new Card(0, ColorEnum.BLUE, ValueEnum.SKIP), new Card(1, ColorEnum.RED, ValueEnum.ONE), new Card(3, ColorEnum.YELLOW, ValueEnum.TWO), new Card(4, ColorEnum.GREEN, ValueEnum.THREE)),
            Arguments.of(new Card(5, ColorEnum.GREEN, ValueEnum.FIVE), new Card(6, ColorEnum.BLUE, ValueEnum.REVERSE), new Card(7, ColorEnum.RED, ValueEnum.SIX), new Card(8, ColorEnum.YELLOW, ValueEnum.SEVEN)),
            Arguments.of(new Card(9, ColorEnum.YELLOW, ValueEnum.EIGHT), new Card(10, ColorEnum.GREEN, ValueEnum.DRAW), new Card(11, ColorEnum.BLUE, ValueEnum.NINE), new Card(12, ColorEnum.RED, ValueEnum.ZERO)),
            Arguments.of(new Card(13, ColorEnum.RED, ValueEnum.THREE), new Card(14, ColorEnum.YELLOW, ValueEnum.EIGHT), new Card(15, ColorEnum.GREEN, ValueEnum.NINE), new Card(16, ColorEnum.BLUE, ValueEnum.REVERSE))
        );
    }

    @ParameterizedTest
    @MethodSource("unplayableCardCombinations")
    void unplayableCardColorTest(Card cardOne, Card cardTwo, Card cardThree, Card cardOnTable) {
        Assertions.assertFalse(ruleService.isCardPlayable(cardOne, cardOnTable));
        Assertions.assertFalse(ruleService.isCardPlayable(cardTwo, cardOnTable));
        Assertions.assertFalse(ruleService.isCardPlayable(cardThree, cardOnTable));
    }

}
