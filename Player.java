import java.util.ArrayList;

/**
 * Represents a player in the Uno game.
 * Manages the player's hand of cards and provides methods to play and receive cards.
 */
public class Player {
    /** The player's name or identifier. */
    private String name;
    
    /** The cards currently in the player's hand. */
    private ArrayList<Card> hand;

    /**
     * Creates a new player with the given name.
     * 
     * @param name the name of this player
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    /**
     * Displays this player's current hand to the console.
     */
    public void showHand() {
        System.out.println(this.toString());
    }

    /**
     * Removes and returns the card at the specified index from this player's hand.
     * 
     * @param index the position of the card to play
     * @return the card that was removed from the hand
     */
    public Card playCard(int index) {
        return hand.remove(index);
    }

    /**
     * Checks if this player has at least one playable card that matches the given top card.
     * Wild cards are always playable.
     * 
     * @param topCard the card currently face-up on the discard pile
     * @return true if the player has a valid move, false otherwise
     */
    public boolean hasPlayableCard(Card topCard) {
        for (int i = 0; i < hand.size(); i++) {
            Card myCard = hand.get(i);
            
            if (myCard.getColor().equals("W")) {
                return true;
            }
            
            if (myCard.getColor().equals(topCard.getColor()) || myCard.getValue().equals(topCard.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a formatted string representation of this player's hand.
     * Displays cards in a visual grid layout with indices for easy selection.
     * 
     * @return a multi-line string showing the player's name and cards
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n=== ").append(name).append("'s Hand ===\n");

        // Each card inner width is 6 visual columns; border = --------
        StringBuilder lineTop = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();
        StringBuilder line4 = new StringBuilder();
        StringBuilder lineBot = new StringBuilder();

        for (int i = 0; i < hand.size(); i++) {
            Card x = hand.get(i);
            String col = x.getColor();
            String val = x.getValue();

            lineTop.append(" ------     ");

            // Top-left color label, padded to 6 visual cols
            int colLen = getVisualLength(col);
            int padRight2 = 6 - colLen;
            line2.append("|").append(col).append(" ".repeat(Math.max(0, padRight2))).append("|    ");

            // Centered value
            int totalSpace = 6;
            int visualLen = val.length();
            int padLeft3 = (totalSpace - visualLen) / 2;
            int padRight3 = totalSpace - visualLen - padLeft3;
            String centeredVal = " ".repeat(Math.max(0, padLeft3)) + val + " ".repeat(Math.max(0, padRight3));
            line3.append("|").append(centeredVal).append("|    ");

            // Bottom-right color label, right-aligned
            int padLeft4 = 6 - colLen;
            line4.append("|").append(" ".repeat(Math.max(0, padLeft4))).append(col).append("|    ");

            lineBot.append(" ------     ");
        }

        result.append(lineTop).append("\n");
        result.append(line2).append("\n");
        result.append(line3).append("\n");
        result.append(line4).append("\n");
        result.append(lineBot).append("\n");

        return result.toString();
    }

    /**
     * Calculates the visual display width of a string, accounting for emojis.
     * Regular characters count as 1 unit; emojis count as 2 units.
     * 
     * @param str the string to measure
     * @return the visual width in display columns
     */
    private int getVisualLength(String str) {
        if (str == null) return 0;
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            int codePoint = str.codePointAt(i);
            if (codePoint > 0x7F) {
                length += 2;
                if (Character.isHighSurrogate(str.charAt(i))) {
                    i++;
                }
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * Returns this player's name.
     * 
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of cards currently in this player's hand.
     * 
     * @return the hand size
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * Adds a card to the end of this player's hand.
     * 
     * @param card the card to add
     */
    public void receiveCard(Card card) {
        hand.add(card);
    }

    /**
     * Adds a card back to this player's hand at a specific index.
     * Used when a played card is invalid and must be returned.
     * 
     * @param index the position where the card should be reinserted
     * @param card the card to add back
     */
    public void receiveCardAt(int index, Card card) {
        if (card != null) {
            hand.add(index, card);
        }
    }
}