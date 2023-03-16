package no.hvl.dat109.Uno.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {

    Card blueCard = new Card(Color.BLUE);
    Card redCard = new Card(Color.RED);


    @BeforeEach
    void makeCard() {

    }

    @Test
    void getColor() {
        Assertions.assertEquals(blueCard.getColor(), Color.BLUE);
        Assertions.assertNotEquals(redCard.getColor(), Color.YELLOW);
    }

    @Test
    void setColor() {
        Card newCard = new Card(Color.BLUE);
        Assertions.assertEquals(newCard.getColor(), Color.BLUE);
        newCard.setColor(Color.YELLOW);
        Assertions.assertEquals(newCard.getColor(), Color.YELLOW);
        newCard.setColor(Color.GREEN);
        Assertions.assertNotEquals(newCard.getColor(), Color.YELLOW);

    }
}