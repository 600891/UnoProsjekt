package no.hvl.dat109.Uno.models;

import jakarta.persistence.Entity;
import no.hvl.dat109.Uno.utils.DatabaseService;
import no.hvl.dat109.Uno.utils.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;



public class Table {

    private List<Player> players = new ArrayList<>();
    private List<Card> discardPile;
    private List<Card> deck;

    @Autowired
    DatabaseService databaseService;

    public Table() {



        
    }

    private makePlayers() {

    }

    public Card playCard() {}

    private makeDecks() {}
    
    private makeCardDeck() {}

    private makeDiscardPile() {}

    private writeToDatabase() {}

    private sendToFrontEnd() {}

    private hasUno() {}

    private hasWon() {}

    private countPoints() {}

    










}
