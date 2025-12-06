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
    JPanel textPanel = new JPanel(); // text panel to hold text lablel
    JLabel textLabel2 = new JLabel(); // text label
    JPanel textPanel2 = new JPanel(); // text panel to hold text lablel
    JButton[] board = new JButton[9]; // make button array for tracking all 9 buttons efficiently

    ImageIcon moleIcon;
    ImageIcon plantIcon;
    ImageIcon holeIcon;
    ImageIcon molemockingIcon;

    // game stats vars
    JButton currentMoleTile;
    JButton currentPlantTile,currentPlantTile2;
    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer,setPlantTimer2;
    int score = 0,highScore=0;

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

         // constructing textLabel2
        textLabel2.setFont(new Font("Arial",Font.BOLD,25));// Set Font to Label, Arial Font , Plain 50px
        textLabel2.setHorizontalAlignment(JLabel.CENTER);// center text auto
        textLabel2.setText("<html>"+"HighScore: "+Integer.toString(highScore)+"<br>YourScore: 0"+"</html>");
        textLabel2.setOpaque(true);

        // constructing Reset Button
        JButton resetButton = new JButton("Retry");
        resetButton.setFont(new Font("Arial",Font.PLAIN,20));
        resetButton.setFocusable(false);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                resetGame();
            }
        });

        // constructing textPanel to hold textLabel
        // and making frame to hold textPanel
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER); // score at center
        textPanel.add(textLabel2, BorderLayout.WEST); // score at center
        textPanel.add(resetButton, BorderLayout.EAST); // right side reset button
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
        Image molemockingImage = new ImageIcon(getClass().getResource("./montymocking.png")).getImage();
        // Create Icon of scaling that Image Object
        moleIcon = new ImageIcon(moleImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        plantIcon = new ImageIcon(plantImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        holeIcon = new ImageIcon(holeImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        molemockingIcon = new ImageIcon(molemockingImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));


        // constructing buttons/ tiles 
        // JButton button1 = new JButton(); // one button
        for ( int  i = 0; i<9; i++){ // using loop, to create tiles/buttons; instead of hardcode
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile); // appending to boardPanel Grid one-by-one
            tile.setIcon(holeIcon); // debugging purpoes
            tile.setFocusable(false);
            // also clickable
            // Each button represents one tile on the game board.

            // Click Handler Logic
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton clickedTile = (JButton) e.getSource(); // input user click & typecast that input to button

                    // Score Logic, when Click Mole
                    if(clickedTile==currentMoleTile){
                        score+=10; // Score Add 10pts
                        textLabel.setText("Score: "+Integer.toString(score));
                    }

                    // GameOver Logic, when Click Plant
                    else if (clickedTile==currentPlantTile || clickedTile==currentPlantTile2){
                        textLabel.setText("Game Over");

                        // calculate and update highscore
                        if(highScore<score){highScore=score;}
                        textLabel2.setText("<html>"+"HighScore: "+Integer.toString(highScore)+"<br>YourScore: "+score+ "</html>");

                        // Stop Movement of Objects
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        setPlantTimer2.stop();

                        // Disable all buttons
                        for (int i = 0; i<9; i++){
                            board[i].setIcon(molemockingIcon);
                            board[i].setEnabled(false);
                        }
                    }
                }
                
            });

        }   
            

        setMoleTimer = new Timer(750, new ActionListener() { // 1 sec, init ActionListner
            public void actionPerformed(ActionEvent e){
                // Remove mole from current tile
                if (currentMoleTile != null){ // if button is not null
                    currentMoleTile.setIcon(holeIcon); // clear the hole, replace plant/mole with hole
                    currentMoleTile = null;
                }

                // // old normal way, but doesn't check if other object also have same tile or not
                // currentMoleTile = board[random.nextInt(9)];
                // currentMoleTile.setIcon(moleIcon);

                // new way, makes objects of random number and check if plant already is at tile
                // Select random tile
                int num = random.nextInt(9); // random 0 to 8
                JButton tile = board[num];

                // Check if plant is on this tile (avoid conflict)
                if (currentPlantTile == tile || currentPlantTile2 == tile){return;} // skip

                // Place mole on new tile
                currentMoleTile = tile;
                currentMoleTile.setIcon(moleIcon);

            }
        });

        setPlantTimer = new Timer(1000, new ActionListener() { // 1.5 sec, init ActionListner
            public void actionPerformed(ActionEvent e){
                // Remove mole from current tile
                if (currentPlantTile != null){ // if button is not null
                    currentPlantTile.setIcon(holeIcon); // clear the hole, replace plant/mole with hole
                    currentPlantTile = null;
                }

                // // old normal way, but doesn't check if other object also have same tile or not
                // currentPlantTile = board[random.nextInt(9)];
                // currentPlantTile.setIcon(moleIcon);

                // new way, makes objects of random number and check if plant already is at tile
                // Select random tile
                int num = random.nextInt(9); // random 0 to 8
                JButton tile = board[num];

                // Check if mole is on this tile (avoid conflict)
                if (currentMoleTile == tile || currentPlantTile2 == tile ){return;} // skip

                // Place plant on new tile
                currentPlantTile = tile;
                currentPlantTile.setIcon(plantIcon);
            }
        });

        setPlantTimer2 = new Timer(850, new ActionListener() { // 1.5 sec, init ActionListner
            public void actionPerformed(ActionEvent e){
                // Remove mole from current tile
                if (currentPlantTile2 != null){ // if button is not null
                    currentPlantTile2.setIcon(holeIcon); // clear the hole, replace plant/mole with hole
                    currentPlantTile2 = null;
                }

                // // old normal way, but doesn't check if other object also have same tile or not
                // currentPlantTile2 = board[random.nextInt(9)];
                // currentPlantTile2.setIcon(moleIcon);

                // new way, makes objects of random number and check if plant already is at tile
                // Select random tile
                int num = random.nextInt(9); // random 0 to 8
                JButton tile = board[num];

                // Check if mole is on this tile (avoid conflict)
                if (currentMoleTile == tile || currentPlantTile == tile ){return;} // skip

                // Place plant on new tile
                currentPlantTile2 = tile;
                currentPlantTile2.setIcon(plantIcon);
            }
        });

            // commented because of welcome game logic
            // setMoleTimer.start();
            // setPlantTimer.start();
            // setPlantTimer2.start();
            
            // frame.setVisible(true);// visibility, only after loading everything
            frame.setVisible(true);   // show main window first
            showWelcomeScreen();      // then show modal welcome + Play button

        
        }
    
    private void resetGame(){
        // stop timers safetly check
        setMoleTimer.stop();
        setPlantTimer.stop();
        setPlantTimer2.stop();

        // calculate and update highscore before reset score
            if(highScore<score){highScore=score;}
        textLabel2.setText("<html>"+"HighScore: "+Integer.toString(highScore)+"<br>YourScore: "+score+ "</html>");

        // reset score
        score = 0;
        textLabel.setText("Score: 0");

        // clear board, for new start
        for (int i=0; i<9; i++){
            board[i].setIcon(holeIcon);
            board[i].setEnabled(true);
        }

        // clear reference
        currentMoleTile = null;
        currentPlantTile = null;
        currentPlantTile2 = null;

        // restart timers
        setMoleTimer.start();
        setPlantTimer.start();
        setPlantTimer2.start();
     
    }

    private void showWelcomeScreen() {
    // true = modal; blocks until closed
    JDialog dialog = new JDialog(frame, "Welcome to Whack-A-Mole!", true);
    dialog.setSize(350, 400);
    dialog.setLocationRelativeTo(frame);
    dialog.setLayout(new BorderLayout());

    // Image
    ImageIcon welcomeRaw = new ImageIcon(getClass().getResource("./monty2.png"));
    Image welcomeImg = welcomeRaw.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
    JLabel imageLabel = new JLabel(new ImageIcon(welcomeImg));
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    // Message label
    JLabel welcomeLabel = new JLabel(
        "<html><center>Welcome to Whack-A-Mole!<br>" +
        "Click PLAY to start the game.<br>" +
        "Hit the mole, avoid the plants!</center></html>",
        SwingConstants.CENTER
    );
    welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    dialog.add(imageLabel,BorderLayout.NORTH);
    dialog.add(welcomeLabel, BorderLayout.CENTER);

    // Play button
    JButton playButton = new JButton("Play");
    playButton.setFont(new Font("Arial", Font.BOLD, 18));
    playButton.setFocusable(false);

    playButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Start the game when user clicks Play
            startGame();
            dialog.dispose();
        }
    });

    // Optional: Quit button
    JButton quitButton = new JButton("Quit");
    quitButton.setFont(new Font("Arial", Font.PLAIN, 14));
    quitButton.setFocusable(false);
    quitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(playButton);
    buttonPanel.add(quitButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setResizable(false);
    dialog.setVisible(true);
}private void startGame() {
    setMoleTimer.start();
    setPlantTimer.start();
    setPlantTimer2.start();
}


    
}
