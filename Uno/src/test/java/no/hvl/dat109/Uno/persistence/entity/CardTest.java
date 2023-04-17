package no.hvl.dat109.Uno.persistence.entity;

import no.hvl.dat109.Uno.enums.ColorEnum;
import no.hvl.dat109.Uno.enums.ValueEnum;
import no.hvl.dat109.Uno.persistence.entity.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {

    private final Card blueCard = new Card(0, ColorEnum.BLUE, ValueEnum.ONE);
    private final Card redCard = new Card(3, ColorEnum.RED, ValueEnum.TWO);


    @BeforeEach
    void makeCard() {
        // TODO
    }

    @Test
    void getColor() {
        Assertions.assertEquals(blueCard.getColor(), ColorEnum.BLUE);
        Assertions.assertNotEquals(redCard.getColor(), ColorEnum.YELLOW);
    }

    @Test
    void setColor() {
        Card newCard = new Card(0, ColorEnum.BLUE, ValueEnum.EIGHT);
        Assertions.assertEquals(newCard.getColor(), ColorEnum.BLUE);
        newCard.setColor(ColorEnum.YELLOW);
        Assertions.assertEquals(newCard.getColor(), ColorEnum.YELLOW);
        newCard.setColor(ColorEnum.GREEN);
        Assertions.assertNotEquals(newCard.getColor(), ColorEnum.YELLOW);

    }
}