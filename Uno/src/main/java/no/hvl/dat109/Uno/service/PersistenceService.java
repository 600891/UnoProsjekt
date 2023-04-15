package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.persistence.entity.Player;
import no.hvl.dat109.Uno.persistence.entity.User;
import no.hvl.dat109.Uno.persistence.repository.PlayerRepo;
import no.hvl.dat109.Uno.persistence.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class.
 * @author Oda Bastesen Storebo, Aurora Sætran
 */
@Service
public class PersistenceService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PlayerRepo playerRepo;

    public User findUserByUsername(String username) {
        // TODO implementer funksjonen mot basen og slett det som er under


        return userRepo.findByUsername(username);
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }


    public Player findPlayerByUsername(String username) {
        return playerRepo.findPlayerByUsername(username);
    }

    public void saveGame(Game game) {
        // TODO lagre spill i basen
    }

    public boolean isUser(User user) {
        if (user == null) {
            return false;
        }
        User userNew = userRepo.findByUsername(user.getUsername());
        if (userNew == null) {
            return false;
        }
        return userNew.getUsername().equals(user.getUsername());
    }

}
