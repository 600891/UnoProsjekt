package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.persistence.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

}
