package no.hvl.dat109.Uno.enums;

import java.util.List;

/**
 *  Colors that can be used in a card
 */
public enum ColorEnum {

    /**
     * Blue color
     */
    BLUE,
    /**
     * Green color
     */
    GREEN,
    /**
     * Red color
     */
    RED,
    /**
     * Yellow color
     */
    YELLOW,
    /**
     * Black color
     */
    BLACK;

    public static List<ColorEnum> getColors() {
        return List.of(ColorEnum.BLUE, ColorEnum.GREEN, ColorEnum.RED, ColorEnum.YELLOW, ColorEnum.BLACK);
    }
}
