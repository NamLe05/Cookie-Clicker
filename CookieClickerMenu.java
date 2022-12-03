// Programmer: Nam Le
// Date: 11/27/2022
// Class: CS 145
// Functions: Create a mulit class cookie clicker game 
//            with a menu and GUI, allows the user to save 
//            their game's data into a file,
//            and load their data on the menu.
// 
//Class Description: CookieClickerMenu class file, creates a 
//        main menu gui with a start and load button.
//        If user clicks start, create a new GUI object.
//        If the user clicks load, read the save.txt file,
//        and assign its integers as a GUI object's parameters
//        



package CookieClicker;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CookieClickerMenu implements ActionListener {

    protected JFrame mainFrame;
    protected JPanel mainPanel;
    protected JButton startButton;
    protected JButton loadButton;
    protected boolean start;
    protected boolean load;
    protected int savedCookies;
    protected int savedClickers;
    protected int savedGrandmas;
    protected int savedBakeries;



    //--------------{CONSTRUCTOR}--------------\\



    public CookieClickerMenu() throws FileNotFoundException {
        setFrame();
        background();
    }
    


    //--------------{FRAME METHODS}--------------\\



    // SET FRAME, creates main frame
    private void setFrame() {
        mainFrame = new JFrame();
        mainPanel = addPanel(mainPanel);
        mainFrame.setBounds(300,150,800,600); // (x,y,width,height)
        mainFrame.add(mainPanel, BorderLayout.CENTER); // add a panel to the center
        //closes frame when clicks close
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Cookie Clicker Menu");
        mainFrame.setVisible(true);
    }

    //--------------{PANEL METHODS}--------------\\



    // SET PANEL, creates main panel
    private JPanel setPanel() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        mainPanel.setBackground(new Color(193,154,107));
        mainPanel.setLayout(null); // (rows, colums)
        return mainPanel;
    }


    // ADD PANEL, adds objects to the main panel
    // POST-CONDITION: returns a panel with a background,
    // start button and load button.

    private JPanel addPanel(JPanel panel) {
        
        panel = setPanel();
        JLabel backgroundLabel = new JLabel();
        backgroundLabel = setBackgroundLabel(backgroundLabel);

        startButton = new JButton("Start Game");
        startButton = setStartButton(startButton);

        loadButton = new JButton("Load Game");
        loadButton = setLoadButton(loadButton);

        panel.add(startButton);
        panel.add(loadButton);
        panel.add(backgroundLabel);

        return panel;
    }



    //--------------{SET LABEL METHODS}--------------\\


    
    // SET BACKGROUND LABEL, creates background label
    private JLabel setBackgroundLabel(JLabel label) {
        label.setIcon(new ImageIcon("CookieBackground.png"));
        label.setBounds(-55, -80, 900, 700);
        label.setOpaque(false);
        label.setFocusable(false);
        label.setBackground(null);
        return label;
    }

    

    //--------------{SET BUTTON METHODS}--------------\\



    // SET START BUTTON, creates the start button
    private JButton setStartButton(JButton button) {
        button.setFont(new Font("Comic Sans", Font.BOLD, 25));
        button.setText("Start Game");
        button.setBounds(290, 220,200, 75);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(Color.white);
        return button;
    }



    // SET LOAD BUTTON, creates the load button.
    private JButton setLoadButton(JButton button) {
        button.setFont(new Font("Comic Sans", Font.BOLD, 25));
        button.setText("Load Game");
        button.setBounds(290, 320,200, 75);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(Color.white);
        return button;
    }



    //--------------{BACKGROUND METHODS}--------------\\



    // BACKGROUND METHOD, a while loop that checks the condition of
    // the booleans start and load. 

    // POST-CONDITION: creates a new GUI object
    public void background() throws FileNotFoundException {
        while (mainPanel.isVisible()) {
            // Note: For some reason the program will not function
            //       without this print statement
            System.out.print(""); 
            if (start == true) {
                mainFrame.setVisible(false);
                new GUI(0,0,0,0);
            } else if (load == true) {
                load = getData();
                if (load == true) {
                    mainFrame.setVisible(false);
                    new GUI(savedCookies, savedClickers, savedGrandmas, savedBakeries);
                }
            }
        }
    }



    // GET DATA, finds the save file, and relays its data
    private boolean getData() throws FileNotFoundException{
        File saveFile = new File("saveFile.txt");

        if(saveFile.exists()) {
            Scanner readFile = new Scanner(saveFile);
            savedCookies = readFile.nextInt();
            savedClickers = readFile.nextInt();
            savedGrandmas = readFile.nextInt();
            savedBakeries = readFile.nextInt();
            return true;
        } else {
            loadButton.setFont(new Font("Comic Sans", Font.BOLD, 17));
            loadButton.setText("Save File Not Found");
            return false;
        }
    }



    // ACTION PERFORMED, performs actions depending on the source.
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == startButton) {
            start = true;
        } else if (e.getSource() == loadButton) {
            load = true;
        }
    }

}


