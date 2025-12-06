// Import Packages
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class WhackAMole {
    // Build Board
    int boardWidth= 600;
    int boardHeight= 650;
    JFrame frame = new JFrame("Whack-A-Mole (github.com/kintsugi-programmer)");// frame

    JLabel textLabel = new JLabel(); // text label
    JPanel textPanel = new JPanel(); // text panel to hold text lable
    JButton[] board = new JButton[9]; // make button array for tracking all 9 buttons efficiently

    ImageIcon moleIcon;
    ImageIcon plantIcon;
    ImageIcon holeIcon;

    // game stats vars
    JButton currentMoleTile;
    JButton currentPlantTile;
    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score = 0;

    // Constructor
    WhackAMole(){

        // constructing frame
        // frame.setVisible(true);// visibility
        frame.setSize(boardWidth, boardHeight);// set size
        frame.setLocationRelativeTo(null);// make sure to open at center
        frame.setResizable(false);// no resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Close (X) Button of program,exit it
        frame.setLayout(new BorderLayout());

        // constructing textLabel
        textLabel.setFont(new Font("Arial",Font.PLAIN,50));// Set Font to Label, Arial Font , Plain 50px
        textLabel.setHorizontalAlignment(JLabel.CENTER);// center text auto
        textLabel.setText("Score: 0");// set text as score 0 default
        textLabel.setOpaque(true);

        // constructing textPanel to hold textLabel
        // and making frame to hold textPanel
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        // constructing board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3,3)); // 3x3 Grid Tiles
        frame.add(boardPanel);// add boardpanel to frame
        // boardPanel.setBackground(Color.BLACK);// Optional: makes background visible during development

        // Loading and Scaling Images
        // moleIcon = new ImageIcon(getClass().getResource("./monty.png")); old
        // New Approach        
        // Create Image Objects
        Image moleImage = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        Image plantImage = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        Image holeImage = new ImageIcon(getClass().getResource("./hole.png")).getImage();
        // Create Icon of scaling that Image Object
        moleIcon = new ImageIcon(moleImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        plantIcon = new ImageIcon(plantImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        holeIcon = new ImageIcon(holeImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));



        // constructing buttons/ tiles 
        // JButton button1 = new JButton(); // one button
        for ( int  i = 0; i<9; i++){ // using loop, to create tiles/buttons; instead of hardcode
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile); // appending to boardPanel Grid one-by-one
            tile.setIcon(holeIcon); // debugging purpoes
            tile.setFocusable(false);

        }   // Each button represents one tile on the game board.
            // also clickable

        setMoleTimer = new Timer(1000, new ActionListener() { // 1 sec, init ActionListner
            public void actionPerformed(ActionEvent e){
                // Remove mole from current tile
                if (currentMoleTile != null){ // if button is not null
                    currentMoleTile.setIcon(holeIcon); // clear the hole, replace plant/mole with hole
                    currentMoleTile = null;
                }

                currentMoleTile = board[random.nextInt(9)];
                currentMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() { // 1.5 sec, init ActionListner
            public void actionPerformed(ActionEvent e){
                // Remove mole from current tile
                if (currentPlantTile != null){ // if button is not null
                    currentPlantTile.setIcon(holeIcon); // clear the hole, replace plant/mole with hole
                    currentPlantTile = null;
                }

                currentPlantTile = board[random.nextInt(9)];
                currentPlantTile.setIcon(plantIcon);
            }
        });

            setMoleTimer.start();
            setPlantTimer.start();
            frame.setVisible(true);// visibility, only after loading everything
        }
    
    
}
