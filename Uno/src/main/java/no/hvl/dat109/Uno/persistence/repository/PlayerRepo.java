package no.hvl.dat109.Uno.persistence.repository;

import no.hvl.dat109.Uno.persistence.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA repository.
 * @author Siri Slyk
 * @author Oda Bastesen Storebo
 */
public interface PlayerRepo extends JpaRepository<Player, Integer> {

    Player findPlayerByName(String name);
}
