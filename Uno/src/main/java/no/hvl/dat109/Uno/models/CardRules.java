package no.hvl.dat109.Uno.models;

/**
 * Contains values of all cards.
 * @author Oda Bastesen Storebo
 */
public class CardRules {

    /**
     * Checks if card is legal to play.
     * @param playerCard
     * @param deckCard
     * @return
     */
    public static boolean isCardLegal(Card playerCard, Card deckCard){
        return playerCard.getColor() == deckCard.getColor();
    }

}
