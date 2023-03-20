package no.hvl.dat109.Uno.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {

    Card blueCard = new Card(ColorEnum.BLUE);
    Card redCard = new Card(ColorEnum.RED);


    @BeforeEach
    void makeCard() {

    }

    @Test
    void getColor() {
        Assertions.assertEquals(blueCard.getColor(), ColorEnum.BLUE);
        Assertions.assertNotEquals(redCard.getColor(), ColorEnum.YELLOW);
    }

    @Test
    void setColor() {
        Card newCard = new Card(ColorEnum.BLUE);
        Assertions.assertEquals(newCard.getColor(), ColorEnum.BLUE);
        newCard.setColor(ColorEnum.YELLOW);
        Assertions.assertEquals(newCard.getColor(), ColorEnum.YELLOW);
        newCard.setColor(ColorEnum.GREEN);
        Assertions.assertNotEquals(newCard.getColor(), ColorEnum.YELLOW);

    }
}