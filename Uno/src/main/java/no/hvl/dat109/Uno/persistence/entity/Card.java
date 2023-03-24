package no.hvl.dat109.Uno.persistence.entity;

import no.hvl.dat109.Uno.enums.ColorEnum;

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
