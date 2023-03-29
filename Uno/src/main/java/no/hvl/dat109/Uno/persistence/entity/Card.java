package no.hvl.dat109.Uno.persistence.entity;

import no.hvl.dat109.Uno.enums.ColorEnum;

import java.util.Objects;

// todo: this entity is missing the primary key reference (@Id)
// @Entity
public class Card {

    private int id;
    private ColorEnum color;

    public Card(int id, ColorEnum color) {
        this.color = color;
        this.id = id;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "Card [color=" + color + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return color == card.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
