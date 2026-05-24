import java.util.ArrayList;

/**
 * Manages the draw deck and discard pile for the Uno game.
 * Handles shuffling, drawing, and discarding cards, and reshuffles when needed.
 */
public class Deck {
    /** The main draw pile of cards available for players to draw. */
    private ArrayList<Card> cards;
    
    /** The pile where players discard their played cards. */
    private ArrayList<Card> discardPile;

    /**
     * Creates a new deck with all standard Uno cards and shuffles them.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        this.discardPile = new ArrayList<>();

        initializeDeck();
        shuffleDeck();
    }

    /**
     * Starts the game by drawing the first card from the deck and placing it on the discard pile.
     */
    void initializeDiscardPile() {
        if (!cards.isEmpty()) {
            Card firstCard = drawCard();
            discardPile.add(firstCard);
        }
    }
    
    /**
     * Adds a card that a player just played to the discard pile.
     * 
     * @param card the card being discarded
     */
    void discardCard(Card card) {
        discardPile.add(card);
    }

    /**
     * Returns the top card currently visible on the discard pile.
     * This is the card that players must match in color or value.
     * 
     * @return the card on top of the discard pile, or null if empty
     */
    Card getTopDiscardCard() {
        if (discardPile.isEmpty()) {
            return null;
        }
        return discardPile.get(discardPile.size() - 1);
    }
    
    /**
     * Builds the complete Uno deck with all standard cards:
     * numbered cards (0-9) in four colors, action cards (Skip, Reverse, +2),
     * and wild cards (+4 and Color Change).
     */
    private void initializeDeck() {
        String[] colors = {"R", "G", "B", "Y"};

        for (String color : colors) {
            cards.add(new Card(color, "0"));
            for (int i = 0 ; i < 2 ; i++) {
                for (int j = 1; j <= 9; j++) {
                    cards.add(new Card(color, Integer.toString(j)));
                }
            }
            for (int i = 0; i < 2; i++) {
                cards.add(new Card(color, "SKP"));
                cards.add(new Card(color, "REV"));
                cards.add(new Card(color, "+2 "));
            } 
        }

        for (int i = 0; i < 4; i++) {
            cards.add(new Card("W", "CLR"));
            cards.add(new Card("W", "+4 "));
        }
    }

    /**
     * Randomly shuffles the draw pile using the Fisher-Yates algorithm.
     */
    private void shuffleDeck() {
        int n = cards.size();
        for (int i = 0; i < n; i++) {
            int j = (int) (Math.random() * n);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }
    
    /**
     * Draws the top card from the draw pile.
     * If the draw pile is empty, reshuffles the discard pile back into it.
     * 
     * @return the card drawn, or null if completely out of cards
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            if (discardPile.size() <= 1) {
                return null;
            }
            Card topCard = discardPile.remove(discardPile.size() - 1);
            cards.addAll(discardPile);
            discardPile.clear();
            discardPile.add(topCard);
            shuffleDeck();
            System.out.println("The draw pile ran out, so the discard pile was shuffled back into the deck.");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Replaces the entire draw pile with a new list of cards.
     * Useful for testing or resetting the game.
     * 
     * @param cards the new list of cards for the draw pile
     */
    void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Returns the current draw pile.
     * 
     * @return the list of cards remaining in the draw pile
     */
    ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Returns a summary of the current deck state.
     * 
     * @return a string showing the number of cards in draw and discard piles
     */
    @Override
    public String toString() {
        return "Draw Pile: " + cards.size() + " cards | Discard Pile: " + discardPile.size() + " cards";
    }
}