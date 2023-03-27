package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.persistence.entity.Player;
import no.hvl.dat109.Uno.persistence.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class.
 * @author Oda Bastesen Storebo
 */
@Service
public class PersistenceService {

    @Autowired
    private PlayerRepo playerRepo;

    public Player findPlayerByUsername(String username) {
        // TODO implementer funksjonen mot basen og slett det som er under
        Player player = new Player();
        player.setName(username);
        return player;
    }

    public void saveGame(Game game) {
        // TODO lagre spill i basen
    }

}
