# Uno-Game

# Text-Based Uno Card Game

A fully featured, robust, object-oriented text-based implementation of the classic Uno card game built in Java. Designed as a player-versus-player command-line application, this project highlights clean console layout formatting, dynamic symbol padding logic, and custom error validation.

## 🚀 Features

- **Full Game Lifecycle:** Automatic deck initialization (108 standard Uno cards), Fisher-Yates shuffling, initial card-dealing (7 per player), action card penalty evaluations, and real-time win condition monitoring.
- **Complete Action & Wild Card Logic:**
  - (Reverse): Inverts the gameplay rotation direction.
  -  (Skip): Bypasses the next player's turn sequence.
  - (Draw Two): Forces the next player to draw two cards and skip their turn.
  -   `⓸` (Wild Color Change / Draw Four): Prompts the player to switch the active game color to Red, Blue, Green, or Yellow.
- **Clean ASCII Graphical Layout:** Cards are rendered dynamically inside custom-drawn border columns. Features a bespoke terminal visual length utility (`getVisualLength`) to calculate exact display footprints, ensuring text borders align flush even when holding uneven multi-byte action symbols.
- **Robust Custom Error Validation:** Replaces baseline terminal input exceptions with a unified `IllegalArgumentException` processing pipeline. Handles invalid character typing, mismatched color choices, out-of-bounds selections, and index out-of-range boundaries smoothly without crashing the application.
- **Code Modularity:** Built strictly around standard built-in Java frameworks (`java.util.ArrayList`, `java.util.Scanner`) with high logical cohesion. Fully compliant with a strict 30-lines-per-method architectural limit to guarantee maximum legibility.

## 🛠️ System Architecture & OOP Design

The system relies on a decoupled, object-oriented design split into clear, single-purpose classes:

1. **`Main.java`**: The game bootstrap interface. Captures player scale validation via regex matching and runs the core gameplay cycle execution engine.
2. **`Uno.java`**: The primary game coordinator. Directs player rotations, validates placement matching legalities, runs interactive prompts, and resolves special capability actions.
3. **`Player.java`**: Represents individual participant entities. Tracks identity, holds active collections of card objects, and produces the layout visualization of the player's current hand.
4. **`Deck.java`**: Controls the physical card lifecycle. Seeds standard color rows alongside utility modifiers, tracks drawing/discard pile migrations, and triggers automatic discard-shuffling when the draw pile becomes exhausted.
5. **`Card.java`**: The atomic immutable data model representing an individual card's color and value symbols.

## 💻 How to Run the Project

Ensure you have the Java Development Kit (JDK 8 or higher) installed on your system.

### 1. Clone the Repository
```bash
git clone [https://github.com/YOUR_GITHUB_USERNAME/text-based-uno.git](https://github.com/YOUR_GITHUB_USERNAME/text-based-uno.git)
cd text-based-uno
