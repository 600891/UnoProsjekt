package no.hvl.dat109.Uno.persistence.entity;

import jakarta.persistence.*;
import no.hvl.dat109.Uno.enums.GameStateEnum;
import no.hvl.dat109.Uno.service.model.CardCollection;

import java.util.List;
@Entity
@Table(name = "game", schema = "uno")
public class Game {

    @Id
    @Column(name = "game_id")
    private String uuid;

    //@Column(name = "active_player")
    private Player activePlayer;

    private Player gameCreator;

    private List<Player> players;
    //@Column(name = "game_state")
    private GameStateEnum gameState;

    private CardCollection deck;
    private CardCollection discard;
    private String playDirection;

    public Game() {}


    public String getUuid() {
        return uuid;

    }

    public void setUuid(String gameId) {
        this.uuid = gameId;
    }

    public Player getGameCreator() {
        return gameCreator;
    }

    public void setGameCreator(Player gameCreator) {
        this.gameCreator = gameCreator;
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

    public CardCollection getDeck() {
        return deck;
    }

    public void setDeck(CardCollection deck) {
        this.deck = deck;
    }

    public CardCollection getDiscard() {
        return discard;
    }

    public void setDiscard(CardCollection discard) {
        this.discard = discard;
    }

    public String getPlayDirection() {
        return playDirection;
    }

    public void setPlayDirection(String playDirection) {
        this.playDirection = playDirection;
    }



    @Override
    public String toString() {
        return "Game{" +
                "uuid='" + uuid + '\'' +
                ", gameCreator=" + gameCreator +
                ", players=" + players +
                ", activePlayer=" + activePlayer +
                ", gameState=" + gameState +
                ", deck=" + deck +
                ", discard=" + discard +
                ", playDirection='" + playDirection + '\'' +
                '}';
    }
}
