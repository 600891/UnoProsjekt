package no.hvl.dat109.Uno.utils;

import no.hvl.dat109.Uno.enums.ColorEnum;

public class ConstantUtil {

    public static final int NUM_COLORS = ColorEnum.values().length;
    public static final int NUM_START_CARDS = 7; // Amount of cards starting on hand

    public static final int NUM_OF_CARDS = 112; // Total number of cards in a deck
    public static final int NUM_FOR_UNO = 1; // How many cards on hand to have uno
    public static final int NUM_FOR_WIN = 0; //Number of cards in hand to win

    private ConstantUtil() {
        // this util class cannot be instantiated
    }
}
