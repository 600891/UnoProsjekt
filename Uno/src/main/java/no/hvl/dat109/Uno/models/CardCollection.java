package no.hvl.dat109.Uno.models;

import no.hvl.dat109.Uno.enums.ColorEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardCollection {

    private List<Card> deck = new ArrayList<>();
    private final int NUM_OF_CARDS = 112;
    private final List<ColorEnum> COLORS = List.of(ColorEnum.BLUE, ColorEnum.GREEN, ColorEnum.RED, ColorEnum.YELLOW);

    public CardCollection() {

        int id = 0;
        for (int i = 0; i < NUM_OF_CARDS/COLORS.size(); i++) {
            for (ColorEnum color: COLORS) {
                deck.add(new Card(id, color));
                id++;
            }
        }

    }

    public Card takeCard() {
        return deck.remove(0);
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void emptyDeck() {
        deck = new ArrayList<>();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public void addCard(Card card) {
        deck.add(card);
    }
}

