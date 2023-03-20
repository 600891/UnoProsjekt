package no.hvl.dat109.Uno.models;

import java.util.List;

public class Game {

    private String uuid;
    private List<Player> players;
    private Player activePlayer;
    private GameStateEnum gameState;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public GameStateEnum getGameState() {
        return gameState;
    }

    public void setGameState(GameStateEnum gameState) {
        this.gameState = gameState;
    }

}
