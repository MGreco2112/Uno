package game;

import game.Hand;
import card.Deck;
import utilities.Utilities;

public class Table {
    private Deck deck = new Deck();
    private Hand hand = new Hand();


    public void playerAction() {
        hand.addCards(7, deck);

        hand.displayHand();

        int choice = Utilities.getInt("It's your turn, will you Play a card or Draw a card?\n1) Play\n2) Draw",
                1, 2);

        switch (choice) {
            case 1 -> {
                hand.removeCard(hand.getCards().get(hand.playCard()));
            }
            case 2 -> {
                hand.addCards(deck);
            }
        }

        hand.displayHand();

    }

}
