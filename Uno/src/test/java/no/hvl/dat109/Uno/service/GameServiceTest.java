package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.persistence.entity.Player;
import no.hvl.dat109.Uno.service.GameService;
import no.hvl.dat109.Uno.service.model.CardCollection;
import no.hvl.dat109.Uno.utils.ConstantUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GameServiceTest {
    private CardCollection deck;
    private List<String> names;
    private List<Player> players;
    
    private final GameService gameService;
    
    @Autowired
    public GameServiceTest(GameService gameService) {
        this.gameService = gameService;
    }

    @BeforeEach
    void initBefor() {
        deck = gameService.makeDeck();
        names = List.of("PLayer 1", "Player 2", "Player 3", "Player 4");
        players = gameService.createPlayers(4, names);
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
        Assertions.assertEquals(deck.getDeckSize(), ConstantUtil.NUM_OF_CARDS);
        deck.takeCard();
        deck.takeCard();
        deck.takeCard();
        Assertions.assertEquals(deck.getDeckSize(), ConstantUtil.NUM_OF_CARDS-3);

    }

    @Test
    void makeDiscardPileTest() {
        CardCollection discardPile = gameService.makeDiscardPile(deck);
        Assertions.assertEquals(deck.getDeckSize(), ConstantUtil.NUM_OF_CARDS-1);
        Assertions.assertEquals(discardPile.getDeckSize(), 1);
    }

    @Test
    void distributeCardsTest() {
        Assertions.assertEquals(players.get(0).getHand().size(), 0);
        Assertions.assertEquals(players.get(1).getHand().size(), 0);
        Assertions.assertEquals(players.get(2).getHand().size(), 0);
        Assertions.assertEquals(players.get(3).getHand().size(), 0);

        gameService.distributeCards(players, deck);

        Assertions.assertEquals(players.get(0).getHand().size(), ConstantUtil.NUM_START_CARDS);
        Assertions.assertEquals(players.get(1).getHand().size(), ConstantUtil.NUM_START_CARDS);
        Assertions.assertEquals(players.get(2).getHand().size(), ConstantUtil.NUM_START_CARDS);
        Assertions.assertEquals(players.get(3).getHand().size(), ConstantUtil.NUM_START_CARDS);



    }

    @Test
    void checkUnoTest() {
        gameService.distributeCards(players, deck);

        Player player = players.get(0);

        Assertions.assertFalse(gameService.checkUno(player));
        Assertions.assertFalse(gameService.checkUno(players.get(1)));

        while(player.getHand().size() > 1) {
            player.getHand().remove(0);
        }
        Assertions.assertTrue(gameService.checkUno(player));
    }

    @Test
    void checkWin() {
        gameService.distributeCards(players, deck);

        Player player = players.get(0);
        while(player.getHand().size() > 0) {
            player.getHand().remove(0);
        }
        Assertions.assertTrue(gameService.checkWin(player));
        Assertions.assertFalse(gameService.checkWin(players.get(1)));
    }

}
