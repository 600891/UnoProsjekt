package no.hvl.dat109.Uno.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRules {
    private static final int TOTCARDS = 112;
    private static List<Color> colors = List.of(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);


    public static List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        for (Color color: colors) {
            for (int i = 0; i < TOTCARDS/colors.size(); i++) {
                deck.add(new Card(color));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }



    public static List<Card> createDiscardPile() {

        return new ArrayList<>();
    }

    public static List<Card> dealCards() {return null;}

    public static List<Card> drawCard() {return null;}

    public static List<Card> countPints() {return null;}



}
