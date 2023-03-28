package no.hvl.dat109.Uno.model;

import no.hvl.dat109.Uno.models.Card;
import no.hvl.dat109.Uno.enums.ColorEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {


    private Card c = new Card(0, ColorEnum.BLUE);
    private final Card c1 = new Card(1, ColorEnum.BLUE);
    private final Card c2 = new Card(2, ColorEnum.GREEN);
    private final Card c3 = new Card(3, ColorEnum.RED);


    @Test
    void getCard() {

        Assertions.assertEquals(c, c1);
        Assertions.assertNotEquals(c1, c2);

        c.setColor(ColorEnum.RED);
        Assertions.assertEquals(c, c3);

        Assertions.assertEquals(c1.getColor(), ColorEnum.BLUE);
    }
}
