package no.hvl.dat109.Uno.persistence.repository;

import no.hvl.dat109.Uno.persistence.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepo extends JpaRepository<Player, Integer> {
    
}
