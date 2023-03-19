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

    public Table(int countOfPlayers, Long id, String name) {



        for (int i = 0; i < countOfPlayers; i++) {
            //Lage en med alle spillerene med key spillerX
            ArrayList<Card> hand = new ArrayList<>();
            players.add(databaseService.createPlayer(id, name, hand));

        }
    }






}
