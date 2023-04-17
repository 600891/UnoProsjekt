package no.hvl.dat109.Uno.persistence.repository;

import no.hvl.dat109.Uno.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
