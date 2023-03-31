package no.hvl.dat109.Uno.models;
import no.hvl.dat109.Uno.enums.ColorEnum;

import no.hvl.dat109.Uno.utils.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GameRules {

    private static List<ColorEnum> colors = ColorEnum.getColors();
    public static final int NUM_COLORS = colors.size(); // Black is not considered a color yet
    public static final int NUM_START_CARDS = 7; // Amont of cards starting on hand

    public static final int NUM_OF_CARDS = 112; // Total number of cards in a deck
    public static final int NUM_FOR_UNO = 1; // How many cards on hand to have uno
    public static final int NUM_FOR_WIN = 0; //Number of cards in hand to win

    @Autowired static DatabaseService databaseService;



    public static List<Player> createPlayers(int numOfPlayers, List<String> names) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player((long) i, names.get(i), new ArrayList<>()));
        }


        return players;
    }
    public static CardCollection makeDeck() {

        CardCollection deck = new CardCollection();
        int id = 0;
        for (ColorEnum color: colors) {
            for (int i = 0; i < NUM_OF_CARDS/NUM_COLORS; i++) {
                deck.addCard(new Card(id, color));
                id++;
            }
        }

        return deck;
    }

    public static CardCollection makeDiscardPile(CardCollection deck) {
        // Trenger deck får å legge til det øverste kortet i decard pile
        CardCollection discardPile = new CardCollection();
        discardPile.addCard(deck.takeCard());
        return discardPile;
    }

    public static void distributeCards(List<Player> players, CardCollection deck) {
        for (Player player: players) {
            player.setHand(deck.takeMultiplieCards(NUM_START_CARDS));
        }
    }

    public static boolean checkUno(Player player) {
        return player.getHand().size() == NUM_FOR_UNO;

    }

    public static boolean checkWin(Player player) {
        return player.getHand().size() == NUM_FOR_WIN;
    }




}
