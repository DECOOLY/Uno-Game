/**
 * Represents a single playing card in the Uno game.
 * Each card has a color (R, G, B, Y, or W for wild) and a value (number or special action).
 */
public class Card {
    /** The color of the card: R (red), G (green), B (blue), Y (yellow), or W (wild). */
    private String color;
    
    /** The value of the card: numbers 0-9, action symbols (Skip, Reverse, +2), or wild symbols (+4, Color Change). */
    private String value;

    /**
     * Creates a card with the specified color and value.
     * 
     * @param color the card's color (R, G, B, Y, or W)
     * @param value the card's value (0-9, actions, or wild symbols)
     */
    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Returns the value of the card.
     * 
     * @return the card's value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the color of the card.
     * 
     * @return the card's color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the card.
     * 
     * @param value the new value for this card
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the color of the card.
     * 
     * @param color the new color for this card
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns a string representation of the card.
     * 
     * @return a formatted string showing the card's color and value
     */
    @Override
    public String toString() {
        return color + " " + value;
    }
}
