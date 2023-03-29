package no.hvl.dat109.Uno.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @Transient // todo: remove this when card becomes entity
    private List<Card> hand;

    public Player() {/*default contructor*/}

    public Player(Long id, String name, List<Card> hand){
        this.id = id;
        this.name = name;
        this.hand = hand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public List<Card> getHand(){ return hand;}

    public void setHand(List<Card> hand){ this.hand = hand; }

}
