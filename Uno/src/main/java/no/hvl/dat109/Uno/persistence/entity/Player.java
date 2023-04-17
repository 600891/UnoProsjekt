package no.hvl.dat109.Uno.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id", nullable = false)
    private Long playerId;
    @Column(name = "name")
    private String name;

    @Column(name = "hand", columnDefinition = "TEXT")
    @OneToMany
    private List<Card> hand;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    private Game gameFK;


    public Player() {/*default contructor*/}

    public Player(Long playerId, String name, List<Card> hand){
        this.playerId = playerId;
        this.name = name;
        this.hand = hand;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName()
    { return name; }

    public void setName(String name)
    { this.name = name; }

    public List<Card> getHand()
    { return hand;}

    public void setHand(List<Card> hand)
    { this.hand = hand; }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + playerId +
                ", name='" + name + '\'' +
                ", hand=" + hand +
                '}';
    }
}
