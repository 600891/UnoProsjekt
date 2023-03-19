package no.hvl.dat109.Uno.models;

import jakarta.persistence.Entity;

/**
 *
 */
@Entity
public class Card {

    private Color color;

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

    @Override
    public String toString() {
        return color + "";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Card card = (Card) obj;
        if (this.getColor() != card.getColor()) {
            return false;
        }

        return true;
    }
}
