package no.hvl.dat109.Uno.models;

import jakarta.persistence.*;
import no.hvl.dat109.Uno.utils.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Game {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    private List<Player> players = new ArrayList<>();


    private String discardPile;
    private String deck;

    //private CardCollection discardPile;
    //private CardCollection deck;
    



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(String discardPile) {
        this.discardPile = discardPile;
    }
}
