package no.hvl.dat109.Uno.persistence.entity;

import no.hvl.dat109.Uno.enums.GameStateEnum;

import java.util.List;
//@Entity
public class Game {

    //@Id
    //@Column(name = "id")
    private String uuid;
    //@Column(name = "players")
    private List<Player> players;
    //@Column(name = "active_player")
    private Player activePlayer;
    //@Column(name = "game_state")
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
