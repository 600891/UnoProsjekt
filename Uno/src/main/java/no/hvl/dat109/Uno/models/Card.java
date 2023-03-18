package no.hvl.dat109.Uno.models;

import jakarta.persistence.Entity;

// todo: this entity is missing the primary key reference (@Id)
// @Entity
public class Card {

    Color color;

    public Card(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
