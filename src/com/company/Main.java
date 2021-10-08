package com.company;

import card.Deck;
import game.Hand;
import utilities.Utilities;

public class Main {

    public static void main(String[] args) {
	    /*
	    Uno: Plan of Attack!

	    Required Classes:

	    Card Package:
	    Card
	    Deck

	    Utility Package:
	    GetString
	    GetInt

	    Game Package:
	    Player
	    Hand
	    Table


	    Class Details:

	    Card:
	        String Color
	        int value
	        boolean isSkip
	        boolean isReverse
	        boolean isWild
	        boolean isDraw

	        constructor sets each of above

	        determinePowers()
	            if value == regular card: return;
	            else: set proper boolean(s) to true;

	    Deck:
	        List<Card> cards

	        constructor builds individual cards and adds them to cards collection

	        draw()

	        shuffle()
	            shuffles cards collection

	    Hand:
	        List<Card> cards
	        boolean calledUno

	        addCards(int numberOfCards) adds card to cards

	        playCard() removes card from hand and returns it


	    Player:
	        String name
	        Hand hand
	        int score

	        playCard()

	        draw(int number of cards)

	        callUno() if hands.size() == 1, state to players player has called Uno
	    Table:
	        Deck deck
	        List<Player> players
	        List<Card> pile
	        boolean isReverse

            constructor asks for number of players and adds new players into players list

	        round() deals seven cards to each player, draws from deck and places card in pile, loops turn, determines
	        winner

	        turn() runs logic for each turn, allows player to draw card, play card if able, call Uno

	        determineRotation() iterates through players in either direction depending on reverse cards in play

	        score() First player to empty their hand causes all other players to add their hand totals to their score.
	        Next game prompt is asked

        */

        Deck testDeck = new Deck();
        Hand testHand = new Hand();

        testHand.addCards(7, testDeck);

        testHand.displayHand();

        int choice = Utilities.getInt("It's your turn, will you Play a card or Draw a card?\n1) Play\n2) Draw",
                1, 2);

        switch (choice) {
            case 1 -> {
                testHand.removeCard(testHand.getCards().get(testHand.playCard()));
            }
            case 2 -> {
                testHand.addCards(testDeck);
            }
        }

        testHand.displayHand();
    }
}
