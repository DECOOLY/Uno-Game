public class Test {
    public static void main(String[] args) {
        // 1. Card methods
        Card c = new Card("R", "7");
        System.out.println("Card: " + c.getColor() + ", " + c.getValue() + ", " + c.toString());

        // 2. Deck methods
        Deck d = new Deck();
        System.out.println("Deck initial: " + d.toString());
        Card drawn = d.drawCard();
        d.initializeDiscardPile();
        System.out.println("Deck after: " + drawn + " | Top: " + d.getTopDiscardCard() + " | Left: " + d.getCards().size());

        // 3. Player methods
        Player p = new Player("Alex");
        System.out.println("Player: " + p.getName() + " | Hand: " + p.getHandSize());
        p.receiveCard(new Card("G", "5"));
        
        // Print it here while he is still holding the card!!!!!!!!
        System.out.println("Alex's Hand:\n" + p.toString()); 
        
        System.out.println("Matches Green 2? " + p.hasPlayableCard(new Card("G", "2")));
        System.out.println("Played card: " + p.playCard(0));

        // 4. Uno Game methods
        Uno u = new Uno(3);
        System.out.println("Game over? " + u.isGameOver());
    }
}