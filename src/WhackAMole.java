// Import Packages
import java.awt.*;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.*;

public class WhackAMole {
    // Build Board
    int boardWidth= 600;
    int boardHeight= 650;
    JFrame frame = new JFrame("Whack-A-Mole (github.com/kintsugi-programmer)");// frame

    JLabel textLabel = new JLabel(); // text label
    JPanel textPanel = new JPanel(); // text panel to hold text lable


    // Constructor
    WhackAMole(){

        // constructing frame
        frame.setVisible(true);// visibility
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

        


        
    }
    
}
