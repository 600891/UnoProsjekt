package no.hvl.dat109.Uno.models;

import jakarta.persistence.Entity;

//TODO: koble klassen opp til databasen og legge til aktuelle linjer får at det skal bli en valid entityklasse
/**
 *
 * The implementation of a card-object used to create a deck of cards
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

    /**
     * @return Color of a card
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @Return String
     */
    @Override
    public String toString() {
        return color + "";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @Return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Card card = (Card) obj;
        return this.getColor() == card.getColor();
    }
}
