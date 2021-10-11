package game;

import card.Card;
import game.Hand;
import card.Deck;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    /*
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


    private List<Player> players = new ArrayList<>();
    private List<Card> pile = new ArrayList<>();
    private final Deck deck = new Deck();
    private boolean isReverse = false;
    private int numberOfPlayers;

    //testing fields
    private Player player = new Player("Player 1", new Hand());

    public Table() {

    }

    private void setupGame() {
        numberOfPlayers = Utilities.getInt("How many Players?", 1, 5);
        openingDeal();
        addPlayers();
    }

    private void round() {
        setupGame();
        //load pile with top card of shuffled deck

    }

    private void turn() {

    }

    private void openingDeal() {
        int STARTING_HAND_COUNT = 7;

        for (Player player : players) {
            player.addCards(STARTING_HAND_COUNT, deck);
        }
    }

    private void addPlayers() {
        while (players.size() < numberOfPlayers) {
            String name = Utilities.getString("Enter your Name", true);

            players.add(new Player(name, new Hand()));

            System.out.println(name + " has been added to the game.");
        }
    }

    //testing method
    public void playerAction() {
        player.addCards(7, deck);

        player.displayHand();

        int choice = Utilities.getInt("It's your turn, will you Play a card or Draw a card?\n1) Play\n2) Draw",
                1, 2);

        switch (choice) {
            case 1 -> {
                player.removeCard(player.playCard());

            }
            case 2 -> {
                player.addCards(1 ,deck);
            }
        }

        player.displayHand();

    }

}
