package no.hvl.dat109.Uno.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


@Entity
@Table(name = "user", schema = "uno")
public class User {

    @Id
    private String username;

    private String passwordhash;

    private String name;
    private String email;
    private int playerId;

    //User user = new User(player_id, username, passwordhash, name, email, passwordsalt);
    public User(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordhash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordhash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = mail;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
