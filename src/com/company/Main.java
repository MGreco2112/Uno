package com.company;

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

	    Player:
	        Hand hand
	        int score

	        playCard()

	        draw(int number of cards)

	    Table:
	        List<Player> players

	        round() loops turn

	        turn() runs logic for each turn

	        determineRotation() iterates through players in either direction depending on reverse cards in play

        */
    }
}
