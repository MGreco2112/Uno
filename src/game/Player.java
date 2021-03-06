package game;

import card.Card;
import card.Deck;

public class Player {
    /*
    Player:
	        String name
	        Hand hand
	        int score

	        playCard()

	        draw(int number of cards)

	        callUno() if hands.size() == 1, state to players player has called Uno

     */
    private final String NAME;
    private final Hand HAND;
    private int score = 0;
    private boolean calledUno = false;


    public Player(String name, Hand hand) {
        this.NAME = name;
        this.HAND = hand;
    }

    public Card playCard() {

        return HAND.cards.get(HAND.playCard());
    }

    public void addCards(int numberOfCards, Deck deck) {
        if (calledUno) {
            System.out.println(NAME + " no longer has Uno");
            calledUno = false;
        }

        switch (numberOfCards) {
            case 1 -> {
                System.out.println(NAME + " draws a " + HAND.addCards(deck).displayCard());
            }
            default -> HAND.addCards(numberOfCards, deck);
        }
    }

    public String displayHand() {
        return HAND.displayHand();
    }

    public void removeCard(Card card) {
        HAND.removeCard(card);
    }

    public String getNAME() {
        return NAME;
    }

    public void callUno() {
        if (HAND.getHandSize() == 1) {
            System.out.println(NAME + " calls Uno!");
            calledUno = true;
        }
    }

    public boolean getCalledUno() {
        return calledUno;
    }

    public boolean isWinner() {
        return HAND.isWinner();
    }

    public void calculateScore() {
        for (Card card : HAND.cards) {
            score += card.getVALUE();
        }
    }

    public int getScore() {
        return score;
    }

    public void clearHand() {
        HAND.cards.clear();
    }

}
