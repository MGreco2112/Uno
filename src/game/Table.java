package game;

import card.Card;
import card.Deck;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Table {
    /*
            Deck deck= new Deck();
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


    private final List<Player> players = new ArrayList<>();
    private final List<Card> pile = new ArrayList<>();
    private Deck deck;
    private boolean isReverse = false;
    private int numberOfPlayers;
    private Card currentCard;
    private String currentColor;
    private int currentValue;
    private boolean hasSkipped = false;
    private boolean hasWinner = false;

    public Table() {
        setupGame();
    }


    public void round() {
        openingPile();
        playersTurn();
        declareWinner();
        scoreHands();
        displayScores();
        newGame();
    }

    private void turn(Player activePlayer) {
        if (!hasSkipped) {
            playerAction(activePlayer);
        } else {
            resetSkip(activePlayer);
        }
        checkWinner(activePlayer);
    }

    private void playersTurn() {
        while (!hasWinner) {
            for (int playerIndex = 0; playerIndex < players.size();) {

                System.out.println(deck.getCardsRemaining() + " cards remaining");

                turn(players.get(playerIndex));

                if (hasWinner) {
                    break;
                }

                if (!isReverse) {
                    playerIndex++;

                    if (playerIndex > players.size() - 1) {
                        playerIndex = 0;
                    }
                } else {
                    playerIndex--;

                    if (playerIndex < 0) {
                        playerIndex = players.size() - 1;
                    }
                }


            }
        }
    }



    private void setupGame() {
        createDeck();
        setNumberOfPlayers();
        addPlayers();
        openingDeal();
    }

    private void setNumberOfPlayers() {
        int MIN_PLAYERS = 2;
        int MAX_PLAYERS = 6;
        numberOfPlayers = Utilities.getInt("How many Players?\nEnter a number 2 - 6", MIN_PLAYERS, MAX_PLAYERS);
    }

    private void createDeck() {
        deck = new Deck();
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

        firstWildCase();
        firstSkipCase();
        firstReverseCase();
        firstDrawCase();

    }

    private void updatePile() {
        currentCard = pile.get(pile.size() - 1);
        currentColor = currentCard.getCOLOR();
        currentValue = currentCard.getVALUE();
    }

    private void firstWildCase() {
        Player firstPlayer = players.get(0);


        if (currentCard.getIsWild() && !currentCard.getIsDraw()) {
            System.out.println("The top card is Wild!\n" + firstPlayer.getNAME() + ", choose a color!");
            System.out.println(firstPlayer.displayHand());

            wildPower(firstPlayer);
        } else if (currentCard.getIsWild() && currentCard.getIsDraw()) {
            pile.clear();
            round();
        }
    }

    private void firstSkipCase() {
        if (currentCard.getIsSkip()) {
            System.out.println(players.get(0).getNAME() + " has been skipped!");
            skipPower();
        }
    }

    private void firstReverseCase() {
        if (currentCard.getIsReverse()) {
            reversePower();
        }
    }

    private void firstDrawCase() {
        if (currentCard.getIsDraw() && !currentCard.getIsWild()) {
            Player firstPlayer = players.get(0);

            System.out.println(firstPlayer.getNAME() + " draws 2 cards!");
            firstPlayer.addCards(2, deck);
            skipPower();
        }
    }

    private void playCard(Player activePlayer) {
        if (!currentCard.getIsWild()) {
            System.out.println(currentCard.displayCard());
        } else {
            System.out.println(currentColor + " Wild");
        }
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
        if (deck.getCardsRemaining() <= 1) {
            reshuffleDeck();
        }

        if (deck.getCardsRemaining() > 0) {
            int SINGLE_DRAW = 1;
            activePlayer.addCards(SINGLE_DRAW, deck);
        } else {
            System.out.println("There are no cards in the deck to draw");
        }
    }

    private void checkWinner(Player activePlayer){
        if (activePlayer.getCalledUno() && activePlayer.isWinner()) {
            hasWinner = true;
        } else {
            activePlayer.callUno();
        }

    }

    private void reshuffleDeck() {
        System.out.println("Reshuffling");
        deck.reshuffle(pile);

    }

    public void playerAction(Player activePlayer) {
        int MIN_OPTION = 1;
        int MAX_OPTION = 2;
        String prompt = "";

        if (!currentCard.getIsWild()) {
            prompt += "Current Card: " + currentCard.displayCard();
        } else {
            prompt += "Current Card: " + currentColor + " Wild";
        }

        prompt += "\n" + activePlayer.getNAME() + "'s turn\n";

        prompt += activePlayer.displayHand();

        prompt += "\n";

        int choice = Utilities.getInt(prompt + activePlayer.getNAME() + "\n1) Play Card\n2) Draw Card", MIN_OPTION, MAX_OPTION);

        switch (choice) {
            case 1 -> {
                playCard(activePlayer);
                updatePile();
                checkCardPowers(activePlayer);
            }
            case 2 -> addCard(activePlayer);
            default -> {
                System.out.println("Invalid selection");
                playerAction(activePlayer);
            }
        }

    }

    private void declareWinner() {
        for (Player player : players) {
            if (player.isWinner()) {
                System.out.println(player.getNAME() + " is the winner!");
            }
        }
    }

    private void checkCardPowers(Player activePlayer) {

        if (currentCard.getIsDraw() || currentCard.getIsDraw() && currentCard.getIsWild()) {
            drawPower(activePlayer);
        } else if (currentCard.getIsSkip()) {
            skipPower();
        } else if (currentCard.getIsWild()) {
            wildPower(activePlayer);
        } else if (currentCard.getIsReverse()) {
            reversePower();
        }
    }

    private void drawPower(Player activePlayer) {
        if (deck.getCardsRemaining() <= 2) {
            reshuffleDeck();
        }

        int drawTwo = 2;
        int drawFour = 4;
        int rotation = (isReverse ? -1 : 1);
        StringBuilder prompt = new StringBuilder();
        int nextPlayer;

        if (players.indexOf(activePlayer) != players.size() - 1) {
            nextPlayer = players.indexOf(activePlayer) + rotation;
        } else if (!isReverse){
            nextPlayer = 0;
        } else {
            nextPlayer = players.size() - 1;
        }

        if (players.indexOf(activePlayer) == 0 && !isReverse) {
            nextPlayer = rotation;
        } else if (players.indexOf(activePlayer) == 0 && isReverse) {
            nextPlayer = players.size() - 1;
        }

        prompt.append(players.get(nextPlayer).getNAME()).append(" draws");

        int drawnCards;

        if (currentCard.getVALUE() == Deck.DRAW_VALUE) {
            prompt.append(" 2 cards");
            drawnCards = drawTwo;
        } else {
            prompt.append(" 4 cards");
            drawnCards = drawFour;
            wildPower(activePlayer);
        }

        skipPower();

        if (deck.getCardsRemaining() > 0) {
            System.out.println(prompt);
            players.get(nextPlayer).addCards(drawnCards, deck);
        } else {
            prompt.append("\nThere are no cards in the deck to draw");
            System.out.println(prompt);
        }

    }

    private void skipPower() {
        hasSkipped = true;
    }

    private void resetSkip(Player activePlayer) {
        System.out.println(activePlayer.getNAME() + " has been skipped!");
        hasSkipped = false;
    }

    private void wildPower(Player activePlayer) {
        StringBuilder setColorPrompt = new StringBuilder();
        setColorPrompt.append(activePlayer.getNAME()).append(" sets the color to ");
        String colorSetter = "";

        String prompt = "1) Red\n2) Blue\n3) Yellow\n4) Green";

        int colorChoice = Utilities.getInt("Choose a Color\n" + prompt, 1, 4);



        switch (colorChoice) {
            case 1 -> {
                setColorPrompt.append("Red");
                colorSetter = "Red";
            }
            case 2 -> {
                setColorPrompt.append("Blue");
                colorSetter = "Blue";
            }
            case 3 -> {
                setColorPrompt.append("Yellow");
                colorSetter = "Yellow";
            }
            case 4 -> {
                setColorPrompt.append("Green");
                colorSetter = "Green";
            }
            default -> {
                System.out.println("invalid option");
                wildPower(activePlayer);}
        }

        System.out.println(setColorPrompt);
        currentColor = colorSetter;
    }

    private void reversePower() {
        int twoPlayerGame = 2;

        isReverse = !isReverse;

        if (!isReverse) {
            System.out.println("Order returns to normal!");
        } else {
            System.out.println("Order has been reversed!");
        }

        if (numberOfPlayers == twoPlayerGame) {
            skipPower();
        }
    }

    private void scoreHands() {
        for (Player player : players) {
            player.calculateScore();
        }
    }

    private void displayScores() {
        System.out.println("Round Scores:\n");

        for (Player player : players) {
            System.out.println(player.getNAME() + ": " + player.getScore());
        }
    }

    private void declareFinalWinner() {

        Player winner = null;
        int lowestScore = players.get(0).getScore();

        for (Player player : players) {
            if (player.getScore() <= lowestScore) {
                winner = player;
            }

            if (winner != null) {
                System.out.println(winner.getNAME() + " is the winner!");
            }
        }
    }

    private void resetBooleans() {
        isReverse = false;
        hasSkipped = false;
        hasWinner = false;
    }

    private void newGame() {
        resetBooleans();
        String choice = Utilities.getString("Do you want to play another game?\n(y)es\n(n)o", true);

        switch (choice.toLowerCase(Locale.ROOT)) {
            case "y" -> newGameOptions();
            case "n" -> {
                declareFinalWinner();
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid entry, try again");
                newGame();
            }
        }
    }

    private void newGameOptions() {
        System.out.println("Do you want to play again with New Players?");

        int choice  = Utilities.getInt("1) New Players\n2) Same Players\n0) Cancel", 0, 2);

        switch (choice) {
            case 1 -> continueWithNewPlayers();
            case 2 -> continueWithCurrentPlayers();
            case 0 -> newGame();
            default -> {
                System.out.println("Invalid selection");
                newGameOptions();
            }
        }

    }

    private void continueWithNewPlayers() {
        players.clear();
        setupGame();
        round();
    }

    private void continueWithCurrentPlayers() {

        for (Player player : players) {
            player.clearHand();
        }

        createDeck();
        openingDeal();
        round();
    }

}
