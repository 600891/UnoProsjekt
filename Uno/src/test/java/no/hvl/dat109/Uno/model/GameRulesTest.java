package no.hvl.dat109.Uno.model;

import no.hvl.dat109.Uno.models.CardCollection;
import no.hvl.dat109.Uno.models.GameRules;
import no.hvl.dat109.Uno.models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;



public class GameRulesTest {
    CardCollection deck;
    List<String> names;
    List<Player> players;

    @BeforeEach
    void initBefor() {
        deck = GameRules.makeDeck();
        names = List.of("PLayer 1", "Player 2", "Player 3", "Player 4");
        players = GameRules.createPlayers(4, names);
    }


    @Test
    void createPlayerTest() {

        //id name hand
        Assertions.assertEquals(players.size(), 4);
        Assertions.assertEquals(players.get(0).getName(), names.get(0));
        Assertions.assertEquals(players.get(1).getName(), names.get(1));
        Assertions.assertEquals(players.get(2).getName(), names.get(2));
        Assertions.assertEquals(players.get(3).getName(), names.get(3));

        Assertions.assertEquals(players.get(0).getId(), 0);
        Assertions.assertEquals(players.get(1).getId(), 1);
        Assertions.assertEquals(players.get(2).getId(), 2);
        Assertions.assertEquals(players.get(3).getId(), 3);

        Assertions.assertEquals(players.get(3).getHand().size(), 0);

    }

    @Test
    void makeDeckTest() {
        Assertions.assertEquals(deck.getDeckSize(), GameRules.NUM_OF_CARDS);
        deck.takeCard();
        deck.takeCard();
        deck.takeCard();
        Assertions.assertEquals(deck.getDeckSize(), GameRules.NUM_OF_CARDS-3);

    }

    @Test
    void makeDiscardPileTest() {
        CardCollection discardPile = GameRules.makeDiscardPile(deck);
        Assertions.assertEquals(deck.getDeckSize(), GameRules.NUM_OF_CARDS-1);
        Assertions.assertEquals(discardPile.getDeckSize(), 1);
    }

    @Test
    void distributeCardsTest() {
        Assertions.assertEquals(players.get(0).getHand().size(), 0);
        Assertions.assertEquals(players.get(1).getHand().size(), 0);
        Assertions.assertEquals(players.get(2).getHand().size(), 0);
        Assertions.assertEquals(players.get(3).getHand().size(), 0);

        GameRules.distributeCards(players, deck);

        Assertions.assertEquals(players.get(0).getHand().size(), GameRules.NUM_START_CARDS);
        Assertions.assertEquals(players.get(1).getHand().size(), GameRules.NUM_START_CARDS);
        Assertions.assertEquals(players.get(2).getHand().size(), GameRules.NUM_START_CARDS);
        Assertions.assertEquals(players.get(3).getHand().size(), GameRules.NUM_START_CARDS);



    }

    @Test
    void checkUnoTest() {
        GameRules.distributeCards(players, deck);

        Player player = players.get(0);
        while(player.getHand().size() == 1) {
            player.getHand().remove(0);
        }
        Assertions.assertTrue(GameRules.checkUno(player));
        Assertions.assertFalse(GameRules.checkUno(players.get(1)));


    }

    @Test
    void checkWin() {
        GameRules.distributeCards(players, deck);

        Player player = players.get(0);
        while(player.getHand().size() == 0) {
            player.getHand().remove(0);
        }
        Assertions.assertTrue(GameRules.checkWin(player));
        Assertions.assertFalse(GameRules.checkWin(players.get(1)));
    }

}
