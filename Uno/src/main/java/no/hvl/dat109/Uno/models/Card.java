


public class Card {
    
    private ColorEnum color;
    

    public Card(ColorEnum color) {
        this.color = color;
        
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



}
