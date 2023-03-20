package no.hvl.dat109.Uno.models;

// todo: this entity is missing the primary key reference (@Id)
// @Entity
public class Card {

    ColorEnum colorEnum;

    public Card(ColorEnum colorEnum) {
        this.colorEnum = colorEnum;
    }

    public ColorEnum getColor() {
        return colorEnum;
    }

    public void setColor(ColorEnum colorEnum) {
        this.colorEnum = colorEnum;
    }

}
