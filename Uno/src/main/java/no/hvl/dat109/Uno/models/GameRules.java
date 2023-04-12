//package no.hvl.dat109.Uno.models;
//
//import no.hvl.dat109.Uno.enums.ColorEnum;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//
//public class GameRules {
//    private static final int TOTCARDS = 112;
//    private static final int STARTCARDCOUNT = 7;
//    private static List<ColorEnum> colors = List.of(ColorEnum.BLUE, ColorEnum.RED, ColorEnum.GREEN, ColorEnum.YELLOW);
//
//
//
//    /**
//     * @return List<Card>
//     *
//     * Generates a complete, shuffled Uno-deck containing all cards.
//     */
//    public static List<Card> generateDeck() {
//        List<Card> deck = new ArrayList<>();
//        int idCount = 1;
//        for (ColorEnum color: colors) {
//            for (int i = 0; i < TOTCARDS/colors.size(); i++) {
//                deck.add(new Card(idCount, color));
//                idCount++;
//            }
//        }
//        Collections.shuffle(deck);
//        return deck;
//    }
//
//
//
//
//    /**
//     * @return List<Card>
//     *
//     *
//     */
//    public static List<Card> createDiscardPile() {
//
//        return new ArrayList<>();
//    }
//
//
//    /**
//     * @param players
//     * @param deck
//     *
//     * Takes cards from the deck and give them to a player
//     */
//    public static void dealCards(List<Player> players, List<Card> deck) {
//
//        players.forEach(p -> {
//            List<Card> hand = new ArrayList<Card>();
//            for (int i = 0; i < STARTCARDCOUNT; i++) {
//                hand.add(deck.remove(0));
//            }
//            System.out.println(hand);
//            p.setHand(hand);
//        });
//
//    }
//
//
//    /**
//     * @param player
//     * @param deck
//     *
//     * Draw a card from the deck and give it to the player
//     */
//    public static void drawCard(Player player, List<Card> deck) {
//
//        player.getHand().add(deck.remove(0));
//    }
//
//
//    /**
//     * @param player
//     * @return List<Card>
//     *
//     *
//     */
//    public static List<Card> countPoints(List<Player> player) {
//        return null;
//
//    }
//
//}
