package no.hvl.dat109.Uno.persistence.entity;

import jakarta.persistence.*;
import no.hvl.dat109.Uno.enums.GameStateEnum;

import java.util.List;
@Entity
@Table(name = "game", schema = "uno")
public class Game {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "game_id")
    private Long gameId;
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "playerId")
    //private Player player;

    @Column(name="player_one")
    private Long playerOne;

    @Column(name="player_two")
    private Long playerTwo;

    @Column(name="player_three")
    private Long playerThree;

    @Column(name="player_four")
    private Long playerFour;
    @Column(name="player_five")
    private Long playerFive;
    @Column(name="player_six")
    private Long playerSix;
    @Column(name = "player_seven")
    private Long playerSeven;
    @Column(name = "player_eight")
    private Long playerEight;
    @Column(name = "player_nine")
    private Long playerNine;
    @Column(name = "player_ten")
    private Long playerTen;
    @ElementCollection
    @Column (name = "deck", columnDefinition = "TEXT")
    private List<Card> deck;
    private Player gameCreator;

    //@Column(name = "players")
    //@OneToMany
    private List<Player> players;
    //@Column(name = "active_player")
    private Player activePlayer;
    //@Column(name = "game_state")
    private GameStateEnum gameState;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
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

    @OneToMany (mappedBy = "Game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Player player;



}
