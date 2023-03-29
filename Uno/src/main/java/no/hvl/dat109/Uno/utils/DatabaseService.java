package no.hvl.dat109.Uno.utils;

import no.hvl.dat109.Uno.models.Card;
import no.hvl.dat109.Uno.models.Player;
import no.hvl.dat109.Uno.utils.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class.
 * @author Oda Bastesen Storebo
 */
@Service
public class DatabaseService {

    @Autowired
    private PlayerRepo playerRepo;


    public Player createPlayer(Long id, String name, ArrayList<Card> hand) {
        Player player = new Player(id, name, hand);
        return playerRepo.save(player);

    }

    public List<Player> getPlayers () {return playerRepo.findAll();}

}
