package game;

import card.Card;

import java.util.ArrayList;
import java.util.List;
import card.Deck;
import utilities.Utilities;

public class Hand {
    /*
    	    Hand:
	        List<Card> cards
	        boolean calledUno

	        addCards(int numberOfCards) adds card to cards

	        playCard() removes card from hand and returns it


     */
    protected List<Card> cards = new ArrayList<>();
    protected boolean calledUno = false;

    public void addCards(Deck deck) {
        cards.add(deck.draw());
    }

    public void addCards(int numberOfCards, Deck deck) {
        int newHandSize = cards.size() + numberOfCards;

        while (cards.size() < newHandSize) {
            cards.add(deck.draw());
        }
    }

    public int playCard() {
        StringBuilder hand = new StringBuilder();
        int STARTING_CARD = 1;

        hand.append("Enter the number next to the card you wish to play:\n");

        int handPosition = STARTING_CARD;

        for (Card card : cards) {
            hand.append(handPosition).append(") ").append(card.displayCard().append("\n"));
            handPosition++;
        }

        return Utilities.getInt(hand.toString(), STARTING_CARD, cards.size()) - 1;

    }

    //This is a temporary method
    public void removeCard(Card card) {
        cards.remove(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void displayHand() {
        StringBuilder hand = new StringBuilder();

        for (Card card : cards) {
            hand.append(card.displayCard() + " | ");
        }

        System.out.println(hand);
    }

    public boolean isWinner() {
        return cards.size() == 0;
    }

    public int getHandSize() {
        return cards.size();
    }


}
