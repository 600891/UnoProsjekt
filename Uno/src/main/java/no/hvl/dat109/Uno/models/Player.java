package no.hvl.dat109.Uno.models;

import jakarta.persistence.*;

import java.util.ArrayList;

/**
 * Player has a hand, a name and id.
 * @author Oda Bastesen Storebo
 */
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private ArrayList<Card> hand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public ArrayList<Card> getHand(){ return hand;}

    public void setHand(ArrayList<Card> hand){ this.hand = hand; }

}
