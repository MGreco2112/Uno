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
	        String name
	        Hand hand
	        int score

	        playCard()

	        draw(int number of cards)

	    Table:
	        Deck deck
	        List<Player> players
	        List<Card> pile

            constructor asks for number of players and adds new players into players list

	        round() deals five cards to each player, draws from deck and places card in pile, loops turn, determines
	        winner

	        turn() runs logic for each turn, allows player to draw card, play card if able, call Uno

	        determineRotation() iterates through players in either direction depending on reverse cards in play

	        score() First player to call Uno causes all other players to add their hand totals to their score. Next
	        game prompt is asked

        */
    }
}
