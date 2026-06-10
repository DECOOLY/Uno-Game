import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main game engine for Uno.
 * Manages players, turns, game, and winning.
 * * * Instance Variables:
 * - deck: The deck of cards used for drawing and discarding throughout the game.
 * - players: List of all participating players in the game.
 * - currentTurn: The index of the player whose turn it currently is.
 * - reverseDirection: The direction of play (false for clockwise, true for counter-clockwise).
 * - activeColor: The active color set by wild cards ("R", "B", "G", "Y").
 */
public class Uno {
    /** The deck of cards for the game. */
    private Deck deck;
    
    /** List of all players in the game. */
    private ArrayList<Player> players;
    
    /** Index of the current player whose turn it is. */
    private int currentTurn;
    
    /** Direction of play: false for clockwise, true for counter-clockwise. */
    private boolean reverseDirection;
    
    /** The current active color set by wild cards ("R", "B", "G", "Y"). */
    private String activeColor;

    /**
     * Initializes a new Uno game with the specified number of players.
     * * @param numPlayers the number of players (2-10)
     */
    public Uno(int numPlayers) {
        this.deck = new Deck();
        this.players = new ArrayList<>();
        this.currentTurn = 0;
        this.reverseDirection = false;
        this.activeColor = null;

        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }
    }

    /**
     * Deals 7 cards to each player and starts the game by flipping the first card.
     * Handles special card penalties for first card if needed.
     * * @param scanner the Scanner object for user input
     */
    public void setupGame(Scanner scanner) {
        for (Player p : players) {
            for (int i = 0; i < 7; i++) {
                p.receiveCard(deck.drawCard());
            }
        }
        deck.initializeDiscardPile();
        
        Card firstCard = deck.getTopDiscardCard();
        handleFirstCardPenalty(firstCard, scanner);
    }

    /**
     * Checks if the first card flipped is a special card and applies penalties.
     * * @param firstCard the card flipped to start the game
     * @param scanner the Scanner object for user input
     */
    private void handleFirstCardPenalty(Card firstCard, Scanner scanner) {
        String val = firstCard.getValue();
        String firstName = players.get(0).getName();
        if (val.equals("SKP")) {
            System.out.println("First card is Skip! " + firstName + " loses their turn.");
            moveToNextPlayer();
        } else if (val.equals("REV")) {
            System.out.println("First card is Reverse! Direction reversed.");
            reverseDirection = !reverseDirection;
        } else if (val.equals("+2 ")) {
            System.out.println("First card is +2! " + firstName + " draws 2 cards.");
            drawCardsForPlayer(players.get(0), 2);
            moveToNextPlayer();
        } else if (val.equals("+4 ")) {
            System.out.println("First card is +4! " + firstName + " draws 4 cards and loses their turn.");
            drawCardsForPlayer(players.get(0), 4);
            moveToNextPlayer();
        } else if (val.equals("CLR")) {
            System.out.println("First card is Change Colour!");
            promptColorChange(scanner, players.get(0));
        }
    }

    /**
     * Helper method to make a specific player draw a given number of cards.
     * * @param player the player who must draw cards
     * @param count the number of cards to be drawn
     */
    private void drawCardsForPlayer(Player player, int count) {
        for (int i = 0; i < count; i++) {
            Card drawnCard = deck.drawCard();
            if (drawnCard != null) {
                player.receiveCard(drawnCard);
            }
        }
    }

    /**
     * Checks if the game is over which is when any player has 0 cards.
     * * @return true if a player has won, false otherwise
     */
    public boolean isGameOver() {
        for (Player p : players) {
            if (p.getHandSize() == 0) {
                System.out.println("\n CONGRATULATIONSSSSSS! " + p.getName() + " wins the game! ");
                return true;
            }
        }
        return false;
    }

    /**
     * Executes a single player's turn.
     * Handles card selection, validation, and special card effects.
     * * @param scanner the Scanner object for user input
     */
    public void playTurn(Scanner scanner) {
        Player currentPlayer = players.get(currentTurn);
        Card topCard = deck.getTopDiscardCard();

        System.out.println("\n------------------------------------");
        System.out.println("It's " + currentPlayer.getName() + "'s turn!");
        if (activeColor != null) {
            System.out.println("Top Discard Card: " + topCard + " (Active Color: " + activeColor + ")");
        } else {
            System.out.println("Top Discard Card: " + topCard);
        }
        currentPlayer.showHand();

        while (!handlePlayerMove(scanner, currentPlayer, topCard)) {
            // Loop continues until valid move is made
        }
        moveToNextPlayer();
    }

    /**
     * Handles a single player input and validates their move using IllegalArgumentException.
     * * @param scanner the Scanner for input
     * @param player the player making the move
     * @param topCard the current top card on the discard pile
     * @return true if a valid move was made, false otherwise
     */
    private boolean handlePlayerMove(Scanner scanner, Player player, Card topCard) {
        System.out.print("Enter card index to play, or type -1 to draw a card: ");
        String input = scanner.next();
        
        try {
            if (!input.matches("-?\\d+")) {
                throw new IllegalArgumentException("Input '" + input + "' is not a whole number index!");
            }
            
            int choice = Integer.parseInt(input);
            if (choice < -1 || choice >= player.getHandSize()) {
                throw new IllegalArgumentException("Index selection " + choice + " is out of bounds!");
            }
            
            if (choice == -1) {
                return drawCard(player);
            }
            return playCardAtIndex(scanner, player, choice, topCard);
            
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage() + " Please try again.");
            return false;
        }
    }

    /**
     * Handles the player drawing a card.
     * * @param player the player drawing a card
     * @return true (indicates turn ends after drawing)
     */
    private boolean drawCard(Player player) {
        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            player.receiveCard(drawnCard);
            System.out.println(player.getName() + " drew a card: " + drawnCard);
        }
        return true;
    }

    /**
     * Handles playing a card at a specific index.
     * * @param scanner the current system input stream
     * @param player the player playing the card
     * @param index the index of the card in the player's hand
     * @param topCard the current top card on the discard pile
     * @return true if card was played successfully, false otherwise
     */
    private boolean playCardAtIndex(Scanner scanner, Player player, int index, Card topCard) {
        Card chosenCard = player.playCard(index);

        if (isValidMove(chosenCard, topCard)) {
            deck.discardCard(chosenCard);
            System.out.println(player.getName() + " played: " + chosenCard);
            
            if (!chosenCard.getColor().equals("W")) {
                activeColor = null;
            }
            
            executeCardEffect(scanner, chosenCard);
            return true;
        } else {
            System.out.println(" Invalid move! That card doesn't match the color or value. Try again pls.");
            player.receiveCardAt(index, chosenCard);
            return false;
        }
    }

    /**
     * Checks if a card is a valid move against the top card.
     * * @param chosenCard the card being played
     * @param topCard the current top card on the discard pile
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(Card chosenCard, Card topCard) {
        if (chosenCard.getColor().equals("W")) {
            return true;
        }
        if (activeColor != null) {
            return chosenCard.getColor().equals(activeColor);
        }
        if (topCard.getColor().equals("W")) {
            return true;
        }
        return chosenCard.getColor().equals(topCard.getColor()) ||
               chosenCard.getValue().equals(topCard.getValue());
    }

    /**
     * Applies the effect of a special card when played.
     * Handles Reverse, Skip, +2, +4, and Color Change cards.
     * * @param scanner the system input stream
     * @param card the special card being executed
     */
    private void executeCardEffect(Scanner scanner, Card card) {
        if (card.getValue().equals("REV")) {
            reverseDirection = !reverseDirection;
            System.out.println("Direction reversed!");
        } else if (card.getValue().equals("SKP")) {
            moveToNextPlayer();
            System.out.println("Next player skipped!");
        } else if (card.getValue().equals("+2 ")) {
            penalizeNextPlayer(2);
        } else if (card.getValue().equals("+4 ")) {
            Player cardPlayer = players.get(currentTurn);
            penalizeNextPlayer(4);
            promptColorChange(scanner, cardPlayer);
        } else if (card.getValue().equals("CLR")) {
            promptColorChange(scanner, players.get(currentTurn));
        }
    }

    /**
     * Makes the next player draw cards and skip their turn.
     * * @param cardCount the number of cards to draw
     */
    private void penalizeNextPlayer(int cardCount) {
        moveToNextPlayer();
        Player nextPlayer = players.get(currentTurn);
        System.out.println(nextPlayer.getName() + " draws " + cardCount + " cards and loses their turn!");
        drawCardsForPlayer(nextPlayer, cardCount);
    }

    /**
     * Prompts the current player to choose a new active color for wild cards.
     * * @param scanner the shared system input scanner stream
     * @param choosingPlayer the player who is choosing the new color
     */
    private void promptColorChange(Scanner scanner, Player choosingPlayer) {
        boolean validColor = false;
        scanner.nextLine(); // Clear scanner buffer

        while (!validColor) {
            System.out.print("Enter a colour (red, blue, green, yellow): ");
            String colorInput = scanner.nextLine().trim().toLowerCase();
            
            if (colorInput.equals("red") || colorInput.equals("blue") || 
                colorInput.equals("green") || colorInput.equals("yellow")) {
                activeColor = colorInput.substring(0, 1).toUpperCase();
                validColor = true;
            } else {
                System.out.println("Invalid color! Please enter red, blue, green, or yellow.");
            }
        }
        System.out.println(choosingPlayer.getName() + " changed the color to: " + activeColor);
    }

    /**
     * Moves the turn to the next player based on current direction.
     */
    private void moveToNextPlayer() {
        int totalPlayers = players.size();
        if (!reverseDirection) {
            currentTurn = (currentTurn + 1) % totalPlayers;
        } else {
            currentTurn = (currentTurn - 1 + totalPlayers) % totalPlayers;
        }
    }
}
