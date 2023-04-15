package no.hvl.dat109.Uno.persistence.entity;

import no.hvl.dat109.Uno.enums.ColorEnum;
import no.hvl.dat109.Uno.enums.ValueEnum;

import java.util.Objects;

// todo: this entity is missing the primary key reference (@Id)
// @Entity
public class Card {

    private int id;
    private ColorEnum color;
    private ValueEnum value;

    public Card() {}

    public Card(int id, ColorEnum color, ValueEnum value) {
        this.color = color;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int nyId) {
        this.id = nyId;
    }

    public ValueEnum getValue() {
        return value;
    }

    public void setValue(ValueEnum value) {
        this.value = value;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", color=" + color +
                ", value=" + value +
                '}';
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
