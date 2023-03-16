package no.hvl.dat109.Uno.models;

import jakarta.persistence.Entity;

/**
 *
 */
@Entity
public class Card {

    Color color;

    /**
     * @param color
     */
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
