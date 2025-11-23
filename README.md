# Whack-a-Mole Game Development Notes (Java GUI)
![alt text](image.png)

## 1. Introduction and Game Overview

This project is suitable for those beginning to learn Java coding and wishing to learn how to build a Graphical User Interface (GUI).

### 1.1 Game Elements and Goal

*   **Theme:** The game uses images from the Mario franchise.
*   **Board:** The game uses **nine tiles**.
*   **The Mole:** A small mole (Monty) hops around the tiles.
*   **Goal:** Click on a tile containing the mole to gain points.
*   **The Piranha Plant:** The Piranha Plant (Piranha) also moves around.
*   **Game Over Condition:** If the player touches (clicks) the Piranha Plant, it results in "game over".
*   **Gameplay Demonstration:** The mole moves, clicking it grants points (it can be clicked multiple times until it moves). Clicking the plant results in "game over".

## 2. Resources and Setup

### 2.1 Development Environment

*   **Tool:** Visual Studio Code (VS Code) is used for the tutorial.
*   **Setup Note:** A separate tutorial exists on how to set up Java with Visual Studio Code.

### 2.2 Project Creation Steps

1.  Use `Ctrl + Shift + P`.
2.  Select `Java: Create Java project`.
3.  Select `No build tools`.
4.  Create the project on the desktop.
5.  Name the project `whack-a-mole`.

### 2.3 Initial File Structure and Code

The project folder (`whackamole`) contains a `source` folder with `app.java` (which holds the `main` function).

In `app.java` (within the `main` function), delete the existing line and instantiate the main game class:

```java
whackamole whackamole = new whackamole();
```

Create a new file called `whackamole.java` in the `source` folder. All game logic will be written in this `whackamole` class.

### 2.4 Assets (Images)

1.  Find the GitHub link in the video description (for completed code).
2.  Download the **two images** required.
3.  Drag the two images into the `Source` folder.

**Final Setup Structure:** `app.java`, `whack-a-mole.java`, and the two images.

### 2.5 Required Import Statements

The following libraries must be imported:

```java
import java.awt.Dimension; // Implicitly needed for size/layout properties
import java.awt.*; // java.awt dot asterisk
import java.awt.event.*; // java.awt dot event dot asterisk
import java.util.Random; // Used to randomly place the mole and plant
import javax.swing.*; // Java x dot swing dot asterisk
```

## 3. Creating the Game Window (JFrame)

### 3.1 Window Dimensions

*   Board Width (`boardWidth`): **600 pixels**.
*   Board Height (`boardHeight`): **650 pixels**.
    *   *Note:* The extra 50 pixels are added to provide room at the top of the window for displaying the score text.

### 3.2 Setting Up the JFrame

A `JFrame` called `frame` will serve as the window.

```java
// Variable definition (implied):
// int boardWidth = 600;
// int boardHeight = 650;
JFrame frame = new JFrame();
```

### 3.3 Window Properties (Within the Constructor)

Set properties for the window (frame):

| Property | Value | Description |
| :--- | :--- | :--- |
| Title | `"Mario whack a mole"` | Sets the window title. |
| Size | `boardWidth, boardHeight` | Sets the size (600x650). |
| Location | `null` | Opens the window at the center of the screen. |
| Resizable | `false` | Prevents resizing. |
| Default Close Operation | `JFrame.EXIT_ON_CLOSE` | Terminates the program when the 'X' button is clicked. |
| Layout | `new BorderLayout()` | Sets the layout manager. |

*Note: `frame.setVisible(true)` is moved to the end of the constructor to ensure all components are loaded first before the window appears, preventing visual delays in button loading.*

## 4. Creating Panels and Components

The window requires two main panels: one for the text (score) and one for the game board.

### 4.1 Text Panel (Score Display)

The text panel requires a `JLabel` to display the text and a `JPanel` to hold the label.

*   **Variable:** `JLabel textLabel`.
*   **Variable:** `JPanel textPanel` (implied).

#### 4.1.1 Text Label Properties

Set the properties for `textLabel` within the constructor:

```java
textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
textLabel.setHorizontalAlignment(JLabel.CENTER); // Centers the text
textLabel.setText("Score: 0");
textLabel.setOpaque(true); // Ensures background color is visible
```

#### 4.1.2 Text Panel Assembly and Positioning

```java
textPanel.setLayout(new BorderLayout());
textPanel.add(textLabel);

// Add text panel to the frame at the North position (top):
frame.add(textPanel, BorderLayout.NORTH);
```

### 4.2 Board Panel (Game Area)

The board panel (`boardPanel`) will hold the nine clickable tiles.

```java
Jpanel boardPanel = new Jpanel();
```

#### 4.2.1 Board Panel Properties

```java
// Set layout for a 3x3 grid:
boardPanel.setLayout(new GridLayout(3, 3)); 

frame.add(boardPanel);
```

*Note: Setting `boardPanel.setBackground(Color.BLACK)` can be used to visualize the panel initially, but it is commented out for the final design.*

#### 4.2.2 Creating Tiles (Buttons)

An array of `JButton` called `board` is used to keep track of all nine buttons.

```java
JButton[] board; // Array to keep track of all nine buttons

for (int i = 0; i < 9; i++) {
    JButton tile = new JButton();
    board[i] = tile;
    
    // Hide the focus rectangle when clicked:
    tile.setFocusable(false);
    
    // Add the tile to the board panel:
    boardPanel.add(tile);
    
    // Add action listener here (Implemented later in Section 6.1)
}
```

## 5. Loading and Scaling Images

Since the original image sizes are too large for the buttons, the images must be scaled before creating the `ImageIcon`.

### 5.1 Image Variables

```java
ImageIcon moleIcon;
ImageIcon plantIcon;
```

### 5.2 Scaling Process (Plant Example)

Instead of directly creating an `ImageIcon`, load the image, scale it, and then create the icon.

1.  **Get Image (Plant):** The image file is `piranha.PNG`.
    ```java
    Image plantImage = new ImageIcon(
        getClass().getResource("./piranha.PNG")).getImage();
    ```
2.  **Scale and Create Icon (Plant):** Scale the image to **150 pixels by 150 pixels**.
    ```java
    plantIcon = new ImageIcon(
        plantImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)
    );
    ```

### 5.3 Scaling Process (Mole Example)

1.  **Get Image (Mole):** The name of the mole in Mario is **Monty**. The image file is `Monty.PNG`.
    ```java
    Image moleImage = new ImageIcon(
        getClass().getResource("./Monty.PNG")).getImage();
    ```
2.  **Scale and Create Icon (Mole):**
    ```java
    moleIcon = new ImageIcon(
        moleImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)
    );
    ```

## 6. Game Logic Initialization

The game needs variables to track the position of the mole and plant, a random utility, timers for movement, and a score variable.

### 6.1 Game Variables

```java
JButton curMoleTile; // Keeps track of the tile with the mole
JButton curPlantTile; // Keeps track of the tile with the plant
Random random = new Random();
Timer setMoleTimer;
Timer setPlantTimer;
int score = 0;
```

### 6.2 Mole Movement Timer (`setMoleTimer`)

The timer is set to run every 1000 milliseconds (1 second).

```java
setMoleTimer = new Timer(1000, new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // 1. Clear previous mole position
        if (curMoleTile != null) {
            curMoleTile.setIcon(null); // Remove the image
            curMoleTile = null;
        }
        
        // 2. Select random tile (0 to 8)
        int num = random.nextInt(9);
        JButton tile = board[num];

        // 3. Conflict Check: Skip if plant is already on this tile
        // If tile is occupied by plant, skip tile for this turn
        if (curPlantTile == tile) {
            return;
        }
        
        // 4. Set new mole position
        curMoleTile = tile;
        curMoleTile.setIcon(moleIcon);
    }
});

setMoleTimer.start();
```

### 6.3 Plant Movement Timer (`setPlantTimer`)

The timer is set to run every 1500 milliseconds (1.5 seconds).

```java
setPlantTimer = new Timer(1500, new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // 1. Clear previous plant position
        if (curPlantTile != null) {
            curPlantTile.setIcon(null);
            curPlantTile = null;
        }
        
        // 2. Select random tile (0 to 8)
        int num = random.nextInt(9);
        JButton tile = board[num];

        // 3. Conflict Check: Skip if mole is already on this tile
        // If curMoleTile is equal to tile, the mole is already on this tile
        if (curMoleTile == tile) {
            return;
        }

        // 4. Set new plant position
        curPlantTile = tile;
        curPlantTile.setIcon(plantIcon);
    }
});

setPlantTimer.start();
```

**Conflict Resolution Rationale:** Without the conflict check (`return` statement), if both timers selected the same tile, one icon would overwrite the other. Although the two tile variables (`curPlantTile` and `curMoleTile`) might point to the same tile, only one icon can be displayed, causing issues when the player clicks the button. The conflict check ensures that if one tile is already occupied, the other object skips that position for the current turn, preventing ambiguity.

## 7. Button Click Handler (Gameplay)

An `ActionListener` must be added to each tile (button) within the initialization loop.

### 7.1 Action Listener Implementation

```java
// Inside the 'for' loop (Section 4.2.2):
tile.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // 1. Identify the clicked button (casting necessary as getSource returns Object)
        JButton tile = (JButton) e.getSource();

        // 2. Check for Mole Hit
        if (tile == curMoleTile) {
            score += 10; // Increment score by 10
            textLabel.setText("Score: " + Integer.toString(score));
        } 
        
        // 3. Check for Plant Hit (Game Over)
        else if (tile == curPlantTile) {
            textLabel.setText("Game Over");

            // Stop Game Logic (See Section 7.2)
            setMoleTimer.stop();
            setPlantTimer.stop();
            
            // Disable all buttons
            for (int i = 0; i < 9; i++) {
                board[i].setEnabled(false); // Disables interaction
            }
        }
    }
});
```

### 7.2 Game Over Functionality

Upon hitting the plant (`Game Over`), two issues must be fixed: the ability to still score points and the continued movement of the mole/plant.

The solution involves:

1.  Stopping the timers: `setMoleTimer.stop()` and `setPlantTimer.stop()`.
2.  Disabling all buttons: Iterating through the `board` array and setting `Board[i].setEnabled(false)`. This causes the buttons to appear grayed out and unclickable.

## 8. Potential Enhancements

The fully functional game can be enhanced with further development:

1.  **Multiple Piranha Plants:** Modify the game to have more than one piranha plant appear.
2.  **Reset Button:** Add a button to reset the game, allowing the player to play again without rerunning the program.
3.  **High Score:** Add a variable to track and display the all-time high score in the window.