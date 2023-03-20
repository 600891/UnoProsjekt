package no.hvl.dat109.Uno.repository;

import no.hvl.dat109.Uno.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository.
 * @author Siri Slyk
 * @author Oda Bastesen Storebo
 */
public interface PlayerRepo extends JpaRepository<Player, Integer> {

}
