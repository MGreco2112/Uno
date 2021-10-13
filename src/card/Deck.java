package card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    /*
    	    Deck:
	        List<Card> cards

	        constructor builds individual cards and adds them to cards collection

	        draw()

	        shuffle()
	            shuffles cards collection

    */
    public static final int WILD_VALUE = 13;
    public static final int DRAW_WILD_VALUE = 14;
    private final List<Card> cards = new ArrayList<>();
    private final int[] values = new int[] {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 11, 12, 13,
            14};
    private final String[] colors = new String[] {"Red", "Blue", "Green", "Yellow", "Wild"};
    private final int DECK_SIZE = 108;

    public Deck() {
        buildDeck();
        shuffle();
    }

    public int getCardsRemaining() {
        return cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void reshuffle(List<Card> pile) {
        cards.addAll(pile);
        pile.clear();
    }

    public Card draw() {
        return cards.remove(0);
    }

    public void draw(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            draw();
        }
    }

    private void buildDeck() {
        int WILDS_CUTOFF = 12;
        int INDEX_CUTOFF = 24;
        int WILD_COLORS = 4;

        int valuesIndex = 0;
        int colorsIndex = 0;

        while (cards.size() < DECK_SIZE) {

            if (values[valuesIndex] <= WILDS_CUTOFF) {
                cards.add(new Card(colors[colorsIndex], values[valuesIndex]));

            } else {
                cards.add(new Card(colors[WILD_COLORS], values[valuesIndex]));

            }

            valuesIndex++;

            if (valuesIndex >= INDEX_CUTOFF) {
                valuesIndex = 0;
                colorsIndex++;
            }

            if (colorsIndex >= WILD_COLORS) {
                break;
            }

        }
    }

    public void displayDeck() {

        for (Card card : cards) {
            card.displayCard();
        }
    }


}
