import java.util.Scanner;

/**
 * Main entry point for the Uno card game.
 * Prompts for the number of players, initializes the game, and runs the main game loop.
 */
public class Main {
    /**
     * The main method that starts the Uno game.
     * Gets the number of players from the user and runs the game until completion.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("... UNO ...");
        
        int numPlayers = 0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print("Enter number of players (2-10): ");
            String input = scanner.next();
            
            try {
                // Ensure the string entered contains only digits
                if (!input.matches("\\d+")) {
                    throw new IllegalArgumentException("Input '" + input + "' is not a valid number!");
                }
                
                numPlayers = Integer.parseInt(input);
                
                // Validate bounds rule
                if (numPlayers >= 2 && numPlayers <= 10) {
                    validInput = true;
                } else {
                    throw new IllegalArgumentException("Uno requires between 2 and 10 players.");
                }
                
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Please try again.\n");
            }
        }

        Uno game = new Uno(numPlayers);
        game.setupGame();

        // Main execution loop
        while (!game.isGameOver()) {
            game.playTurn(scanner);
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }
}