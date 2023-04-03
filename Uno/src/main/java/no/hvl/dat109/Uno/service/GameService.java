package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.enums.ColorEnum;
import no.hvl.dat109.Uno.service.model.CardCollection;
import no.hvl.dat109.Uno.persistence.entity.Card;
import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.persistence.entity.Player;
import no.hvl.dat109.Uno.utils.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GameService {

    private List<Game> notStartedGames;
    private List<Game> startedGames;
    private final PersistenceService persistenceService;

    @Autowired
    public GameService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        this.notStartedGames = new ArrayList<>();
        this.startedGames = new ArrayList<>();
    }

    /**
     * this method creates a new game
     * @param username the name of the player that creates the game
     * @return the game created, null if no game created
     */
    public Game createGame(String username) {
        Long gameId = new Random().nextLong();
       // String gameId = UUID.randomUUID().toString();

        Player player = persistenceService.findPlayerByUsername(username);

        if(isPartOfGame(username)) {
           return null;
        }

        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game();
        game.setId(gameId);
        game.setPlayers(players);
        game.setGameCreator(player);

        notStartedGames.add(game);
        persistenceService.saveGame(game);

        return game;
    }

    /**
     * this method joins the given player to the given game
     * @param username the username of the player that joins the game
     * @param gameId the gameId of the game to join
     * @return the game that is joined, null if the game is full or non-existing
     */
    public Game joinGame(String username, String gameId) {
        Game game = notStartedGames.stream().filter(g -> g.getId().equals(gameId)).findFirst().orElse(null);
        if(game == null || isGameFull(game)) {
            return null;
        }

        Player player = persistenceService.findPlayerByUsername(username);
        game.getPlayers().add(player);

        persistenceService.saveGame(game);

        return game;
    }

    private boolean isPartOfGame(String username) {
        for(Game game : notStartedGames) {
            for(Player player : game.getPlayers()) {
                if(player.getName().equals(username)) {
                    return true;
                }
            }
        }
        for(Game game : startedGames) {
            for(Player player : game.getPlayers()) {
                if(player.getName().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Game> getAllNotStartedGames() {
        return notStartedGames;
    }

    public boolean isGameFull(Game game) {
        return game.getPlayers().size() >= 4;
    }

    public List<Player> createPlayers(int numOfPlayers, List<String> names) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player((long) i, names.get(i), new ArrayList<>()));
        }

        return players;
    }

    public CardCollection makeDeck() {
        CardCollection deck = new CardCollection();
        int id = 0;
        for (ColorEnum color: ColorEnum.values()) {
            for (int i = 0; i < ConstantUtil.NUM_OF_CARDS/ConstantUtil.NUM_COLORS; i++) {
                deck.addCard(new Card(id, color));
                id++;
            }
        }

        return deck;
    }

    public CardCollection makeDiscardPile(CardCollection deck) {
        // Trenger deck får å legge til det øverste kortet i decard pile
        CardCollection discardPile = new CardCollection();
        discardPile.addCard(deck.takeCard());
        return discardPile;
    }

    public void distributeCards(List<Player> players, CardCollection deck) {
        for (Player player: players) {
            player.setHand(deck.takeMultiplieCards(ConstantUtil.NUM_START_CARDS));
        }
    }

    public boolean checkUno(Player player) {
        return player.getHand().size() == ConstantUtil.NUM_FOR_UNO;
    }

    public boolean checkWin(Player player) {
        return player.getHand().size() == ConstantUtil.NUM_FOR_WIN;
    }

}
