package game;

import card.Card;
import game.Hand;
import card.Deck;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Card currentCard;
    private String currentColor;
    private int currentValue;
    private boolean hasWinner = false;

    public Table() {

    }

    public void round() {
        setupGame();
        openingPile();
        while (!hasWinner) {
            //TODO modify this loop to flow both ways
            for (Player player : players) {
                turn(player);
                if (hasWinner) {
                    break;
                }
            }
        }


    }

    private void turn(Player activePlayer) {
        reshuffleDeck();
        playerAction(activePlayer);
        checkWinner(activePlayer);
    }



    private void setupGame() {
        int MIN_PLAYERS = 1;
        int MAX_PLAYERS = 5;
        numberOfPlayers = Utilities.getInt("How many Players?\nEnter a number 1 - 5", MIN_PLAYERS, MAX_PLAYERS);
        addPlayers();
        openingDeal();
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

    private void openingPile() {
        pile.add(deck.draw());
        updatePile();
    }

    private void updatePile() {
        currentCard = pile.get(pile.size() - 1);
        currentColor = currentCard.getCOLOR();
        currentValue = currentCard.getVALUE();
    }

    private void playCard(Player activePlayer) {
        System.out.println(currentCard.displayCard());
        Card card = activePlayer.playCard();
        if (Objects.equals(card.getCOLOR(), currentColor) || card.getVALUE() == currentValue || card.getVALUE() == Deck.WILD_VALUE
                || card.getVALUE() == Deck.DRAW_WILD_VALUE) {
            pile.add(card);
            activePlayer.removeCard(card);
        } else {
            System.out.println("Invalid selection, try again");
            playerAction(activePlayer);
        }
    }

    private void addCard(Player activePlayer) {
        int SINGLE_DRAW = 1;
        activePlayer.addCards(SINGLE_DRAW, deck);
    }

    private void checkWinner(Player activePlayer){
        if (activePlayer.getCalledUno() && activePlayer.isWinner()) {
            hasWinner = true;
        } else {
            activePlayer.callUno();
        }

    }

    private void reshuffleDeck() {
        int EMPTY_DECK = 0;
        if (deck.getCardsRemaining() == EMPTY_DECK) {
            deck.reshuffle(pile);
        }
    }

    public void playerAction(Player activePlayer) {
        int MIN_OPTION = 1;
        int MAX_OPTION = 2;

        System.out.println("Current Card:" + currentCard.displayCard() + "\n" + activePlayer.getNAME() + "'s Hand:");
        activePlayer.displayHand();
        int choice = Utilities.getInt(activePlayer.getNAME() + "\n1) Play Card\n2) Draw Card", MIN_OPTION, MAX_OPTION);

        switch (choice) {
            case 1 -> {
                reshuffleDeck();
                playCard(activePlayer);
                updatePile();
            }
            case 2 -> addCard(activePlayer);
        }

    }

}
