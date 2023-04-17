package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.enums.ColorEnum;
import no.hvl.dat109.Uno.enums.GameStateEnum;
import no.hvl.dat109.Uno.enums.ValueEnum;
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
       String gameId = UUID.randomUUID().toString();

        Player player = persistenceService.findPlayerByUsername(username);

        if(isPartOfGame(username)) {
           return null;
        }

        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game();
        game.setUuid(gameId);
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
        Game game = notStartedGames.stream().filter(g -> g.getUuid().equals(gameId)).findFirst().orElse(null);
        if(game == null || isGameFull(game)) {
            return null;
        }

        Player player = persistenceService.findPlayerByUsername(username);
        game.getPlayers().add(player);

        persistenceService.saveGame(game);

        return game;
    }

        /**
     * this function helps the given player to leave the game he is in, either started or not
     * @param username the username of the player that is about to leave
     * @return the game that the player has left successfully, null otherwise
     */
    public Game leaveNotStartedGame(String username) {
        Game game = findGame(username);
        if(game == null || game.getGameState() != GameStateEnum.NOT_STARTED) {
            return null;
        }

        game.getPlayers().removeIf(p -> p.getName().equals(username));
        if(game.getPlayers().isEmpty()) {
            notStartedGames.remove(game);
        }

        // todo: persistence

        return game;
    }

    /**
     * this function starts the game which the player has joined
     * @param username the player that wants to start the game
     * @return the game started, null if: non-existing, already started, finished, not enough players
     */
    public Game startGame(String username){
        Game game = findGame(username);
        if(game == null || game.getGameState() != GameStateEnum.NOT_STARTED || game.getPlayers().size() < 2){
            return null;
        }

        notStartedGames.remove(game);
        startedGames.add(game);
        game.setGameState(GameStateEnum.STARTED);

        // Lage deck
        CardCollection deck = makeDeck();
        // Shuffle deck
        deck.shuffleDeck();
        // Legg til deck til spillobjekt
        game.setDeck(deck);
        // Dele ut kort
        distributeCards(game.getPlayers(), game.getDeck());
        // Lage discard pile
        CardCollection discard = makeDiscardPile(game.getDeck());
        // Sett spillets discard pile
        game.setDiscard(discard);
        // Sette currentPlayer til den som lagde spillet
        Player creator;
        for(Player player : game.getPlayers()){
            if(player.getName() == username)    {
                     creator = player;
                     game.setActivePlayer(creator);
            }
        }
        game.setPlayDirection("clockwise");
        // TODO persist

        return game;
    }

    /**
     * this function helps to find the actual game that the given player has joined
     * @param username the players username to search for
     * @return the game joined, null if the player has not joined any game
     */
    public Game findGame(String username) {
        Game game = isPartOfGame(notStartedGames, username);
        return (game != null) ? game : isPartOfGame(startedGames, username);
    }

    /**
     * this function finds the not started game with the given gameId
     * @param gameId the id of the game one wants to find
     * @return the game found
     */
    public Game findNotStartedGameById(String gameId) {
        return notStartedGames.stream().filter(g -> g.getUuid().equals(gameId)).findFirst().orElse(null);
    }

        /**
     * this function finds the started game with the given gameId
     * @param gameId the id of the game one wants to find
     * @return the game found
     */
    public Game findStartedGameById(String gameId) {
        return startedGames.stream().filter(g -> g.getUuid().equals(gameId)).findFirst().orElse(null);
    }

    /**
     * this function determines if the given player is part of a game, either started or not
     * @param username the players username to search for
     * @return true if the player is part of a game
     */
    public boolean isPartOfGame(String username) {
       return isPartOfGame(notStartedGames, username) != null || isPartOfGame(startedGames, username) != null;
    }

    private Game isPartOfGame(List<Game> games, String username) {
        for(Game game : games) {
            for(Player player : game.getPlayers()) {
                if(player.getName().equals(username)) {
                    return game;
                }
            }
        }
        return null;
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
            if(color.equals(ColorEnum.BLACK)){
                for (int i = 0; i < 8; i++) {
                    if(i <4){
                        deck.addCard(new Card(id,color, ValueEnum.WILD));
                    } else {
                        deck.addCard(new Card(id, color, ValueEnum.DRAW));
                    }
                    id++;
                }
            }
            else{
                for(int i = 0; i < (ConstantUtil.NUM_OF_CARDS - 8) / (ColorEnum.values().length - 1); i++){
                    if(i < 2){
                        deck.addCard(new Card(id, color, ValueEnum.ONE));
                    } else if (i < 4){
                        deck.addCard(new Card(id, color, ValueEnum.TWO));
                    }else if (i < 6){
                        deck.addCard(new Card(id, color, ValueEnum.THREE));
                    } else if (i < 8){
                        deck.addCard(new Card(id, color, ValueEnum.FOUR));
                    }else if (i < 10){
                        deck.addCard(new Card(id, color, ValueEnum.FIVE));
                    }else if (i < 12){
                        deck.addCard(new Card(id, color, ValueEnum.SIX));
                    } else if (i < 14){
                        deck.addCard(new Card(id, color, ValueEnum.SEVEN));
                    } else if (i < 16){
                        deck.addCard(new Card(id, color, ValueEnum.EIGHT));
                    }else if (i < 18){
                        deck.addCard(new Card(id, color, ValueEnum.NINE));
                    }else if (i < 20){
                        deck.addCard(new Card(id, color, ValueEnum.REVERSE));
                    }else if (i < 22){
                        deck.addCard(new Card(id, color, ValueEnum.DRAW));
                    }else if (i < 24){
                        deck.addCard(new Card(id, color, ValueEnum.SKIP));
                    } else {
                        deck.addCard(new Card(id, color, ValueEnum.ZERO));
                    }
                    id++;
                }
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
