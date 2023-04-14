package no.hvl.dat109.Uno.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author Oda Bastesen Storebo
 *
 **/
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long player_id;

    private String username;
    private String passwordhash;
    private String name;
    private String email;
    byte[] passwordsalt;

    public User(Long player_id, String username, String passwordhash, String name, String email, byte[] passwordsalt){
        this.player_id = player_id;
        this.username = username;
        this.passwordhash = passwordhash;
        this.name = name;
        this.email = email;
        this.passwordsalt = passwordsalt;
    }

    public User() {

    }
}
