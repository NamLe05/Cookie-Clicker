// Programmer: Nam Le
// Date: 11/27/2022
// Class: CS 145
// Functions: Create a mulit class cookie clicker game 
//            with a menu and GUI, allows the user to save 
//            their game's data into a file,
//            and load their data on the menu.
// 
// Class Description: GUI class file, creates a gui with a cookie, upgrades,
//       and save button. 
//
//       Records the number of clicks on the cookie.
//       
//       Clicker upgrade: increases cookies per click
//       Grandma upgrade: adds 5 cookies every 3 seconds per grandma
//       Bakery upgrade: adds 50 cookies every 3 seconds per bakery
//       If user clicks one of the upgrade buttons, add 1 to the specific 
//       upgrade. Display the user's upgrades in their arsenal.
//      
//       Save Button: if clicked, creates a "save.txt" file and prints the user's
//       cookies, clickers, grandmas, bakeries, into said file to be recalled 
//       in the CookieClickerMenu class.          




package CookieClicker;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class GUI implements ActionListener {

    private JLabel cookiesLabel;
    private JLabel upgradeLabel;
    private JLabel upgradeDisplayLabel;
    private JLabel grandmaDisplayLabel;
    private JLabel cursorDisplayLabel;
    private JLabel bakeryDisplayLabel;
    private JFrame frame;
    private JPanel panel;
    private JButton cookieButton;
    private JButton clickUpgradeButton;
    private JButton upgradeButton;
    private JButton grandmaUpgradeButton;
    private JButton bakeryUpgradeButton;
    private JButton saveButton;
    private boolean upgradeIsVisible;
    private boolean hasClicked;
    private boolean saved;
    private int clickers;
    private int grandmas;
    private int bakeries;
    private int cookies;
    private String saveName;



    //--------------{CONSTRUCTOR}--------------\\



    public GUI(int cookies, int clickers, int grandmas,
    int bakeries) throws FileNotFoundException {
        if (cookies > 0 ) { // if there are existing cookies
            this.cookies = cookies;
            this.clickers = clickers;
            this.grandmas = grandmas;
            this.bakeries = bakeries;
        }
        else {
            this.cookies = 0;
            this.clickers = 1;
            this.grandmas = 0;
            this.bakeries = 0;
        }
        saveName = "saveFile.txt";
        upgradeIsVisible = false;
        hasClicked = true;
        saved = false;
        setFrame();
        background();
    }



    //--------------{BUTTON ACTION METHODS}--------------\\



    // ACTION PERFORMED METHOD, performs actions based on source
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cookieButton) {
            isCookieButton();
        }
        else if (e.getSource() == upgradeButton) {
            isUpgradeButton();
        }
        else if (e.getSource() == clickUpgradeButton) {
            isClickerUpgradeButton();
        }
        else if (e.getSource() == grandmaUpgradeButton) {
            isGrandmaUpgradeButton();
        }
        else if (e.getSource() == bakeryUpgradeButton) {
            isBakeryUpgradeButton();
        }
        else if (e.getSource() == saveButton) {
            saved = true;
        }
    }



    // IS COOKIE BUTTON,
    // POST-CONDITION: Adds to the total num of cookies
    public void isCookieButton() {
        if (hasClicked == true) {
            cookieButton.setIcon(new ImageIcon("CookieButtonSmall.png"));
            hasClicked = false;
        }
        else {
            cookieButton.setIcon(new ImageIcon("CookieButton.png"));
            hasClicked = true;
        }
        cookies+=clickers;
        cookiesLabel.setText(cookies + " Cookies!");
    }



    // IS UPGRADE BUTTON, 
    // POST-CONDITION: turns upgrade panel and buttons visible
    // or invisible
    public void isUpgradeButton() {

        if (upgradeIsVisible == true){
            setUpgradeInvisible();
        } else {
            setUpgradeVisible();
        }
    }



    // IS CLICKER UPGRADE BUTTON, 
    // POST-CONDITION: update cookies, num of clickers, 
    // and cost to upgrade
    public void isClickerUpgradeButton() {
        if (cookies > clickers*10) { // cost
            cookies -= clickers*10;
            clickers++; // determines how much cookie per click
            cursorDisplayLabel.setText("X " + clickers);
            clickUpgradeButton.setText("Cookies Per Click: " + clickers  +
            "  |  $" + clickers*10);
            cookiesLabel.setText(cookies + " Cookies!");
        }
    }



    // IS GRANDMA UPGRADE BUTTON,
    // POST-CONDITION: update cookies, num grandmas,
    // and cost to upgrade
    public void isGrandmaUpgradeButton() {
        if (cookies > (grandmas+1)*50) {
            cookies-=(grandmas+1)*50;
            grandmas++;
            grandmaDisplayLabel.setVisible(true);
            grandmaDisplayLabel.setText("X " + grandmas);
            grandmaUpgradeButton.setText("Grandmas: " + grandmas  +
        "  |  $" + (grandmas+1)*50);
        cookiesLabel.setText(cookies + " Cookies!");
        }
    }



    // IS BAKERY BUTTON, 
    // POST-CONDITION: update cookies, num of bakeries, 
    // and cost to upgrade
    public void isBakeryUpgradeButton() {
        if (cookies > (bakeries+1)*1000) {
            cookies-=(bakeries+1)*1000;
            bakeries++;
            bakeryDisplayLabel.setVisible(true);
            bakeryDisplayLabel.setText("X " + bakeries);
            bakeryUpgradeButton.setText("Bakeries: " + bakeries  +
        "  |  $" + (bakeries+1)*1000);
        cookiesLabel.setText(cookies + " Cookies!");
        }
    }



    //--------------{SET VISIBLE METHODS}--------------\\



    // SET UPGRADE INVISIBLE, 
    // POST-CONDITION: set all related upgrade objects to invisible
    public void setUpgradeInvisible() {
        upgradeButton.setText(">>");
        upgradeButton.setBounds(0, 250, 100, 30);    
        clickUpgradeButton.setVisible(false);
        grandmaUpgradeButton.setVisible(false);
        bakeryUpgradeButton.setVisible(false);
        upgradeLabel.setVisible(false);
        upgradeIsVisible = false;
    }



    // SET UPGRADE VISIBLE, 
    // POST-CONDITION: set all related upgrade objects to visible
    public void setUpgradeVisible() {
        upgradeButton.setText("<<");
        upgradeButton.setBounds(180, 250, 100, 30);
        clickUpgradeButton.setVisible(true);
        grandmaUpgradeButton.setVisible(true);
        bakeryUpgradeButton.setVisible(true);
        upgradeLabel.setVisible(true);
        upgradeIsVisible = true;
    }



    //--------------{FRAME METHODS}--------------\\




    // FRAME METHOD, 
    // POST-CONDITION: creates a frame & adds a panel to the frame
    public void setFrame() {
        frame = new JFrame();
        panel = addPanel(panel);
        frame.setBounds(300,150,800,600);
        frame.add(panel, BorderLayout.CENTER); // add a panel to the center
        // closes frame when clicks close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cookie Clicker");
        frame.setVisible(true);
    }



    //--------------{PANEL METHODS}--------------\\



    // SET PANEL METHOD, 
    // POST-CONDITION: creates and returns the panel
    public JPanel setPanel() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        panel.setBackground(new Color(193,154,107));
        panel.setLayout(null); // (rows, colums)
        return panel;
    }



    // ADD PANEl METHOD, 
    // POST-CONDITION: creates buttons and labels, then
    // adds them to the panel
    // returns the modified panel
    public JPanel addPanel(JPanel panel) {

        panel = setPanel();
         
        cookieButton = new JButton();
        cookieButton = setCookieButton(cookieButton);
        upgradeButton = new JButton("<<");
        upgradeButton = setUpgradeButton(upgradeButton);
        saveButton = new JButton("Save");
        saveButton = setSaveButton(saveButton);
        cookiesLabel = new JLabel(cookies + " Cookies!");
        cookiesLabel = setCookiesLabel(cookiesLabel);
        upgradeLabel = new JLabel();
        upgradeLabel = setUpgradeLabel(upgradeLabel);
        upgradeDisplayLabel = new JLabel();
        upgradeDisplayLabel = setUpgradeDisplayLabel(upgradeDisplayLabel);

        panel.add(upgradeButton);
        panel.add(saveButton);
        panel.add(cookieButton);
        panel.add(cookiesLabel);
        panel.add(upgradeLabel);
        panel.add(upgradeDisplayLabel);

        return panel;
    }


    
    //--------------{SET BUTTON METHODS}--------------\\



    // SET COOKIE BUTTON, 
    // POST-CONDITION: returns a modified button
    public JButton setCookieButton(JButton button) {

        button.setIcon(new ImageIcon("CookieButton.png"));
        button.addActionListener(this);
        button.setBounds(320, 200, 150, 150);
        // sets button's background to invisible
        button.setOpaque(false);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBackground(null);
        return button;
    }



    // SET UPGRADES BUTTON, 
    // POST-CONDITION: returns a modified button
    public JButton setUpgradeButton(JButton button) {
        button.setFont(new Font("Comic Sans", Font.BOLD, 25));
        button.setBounds(180, 250, 100, 30);
        button.setForeground(Color.white);
        button.addActionListener(this);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(null);
        return button;
    }



    // SET SAVE BUTTON,
    // POST-CONDITION: returns a modified button
    public JButton setSaveButton(JButton button) {
        button.setFont(new Font("Comic Sans", Font.BOLD, 25));
        button.setIcon(new ImageIcon("save.png"));
        button.setBounds(620, 500, 150, 50);
        button.setForeground(Color.white);
        button.addActionListener(this);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(null);
        return button;
    }


    // SET CLICK UPGRADE BUTTON,
    // POST-CONDITION: returns a modified button
    public JButton setClickUpgradeButton(JButton button) {
        button.setText("Cookies Per Click: " + clickers +
         "  |  $" + clickers*10);
        button.setBounds(0, 50, 200, 75);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(Color.white);
        return button;
    }


    // SET GRANDMA UPGRADE BUTTON, 
    // POST-CONDITION: returns a modified button
    public JButton setGrandmaUpgradeButton(JButton button) {
        button.setText("Grandmas: " + grandmas +
        "  |  $" + (grandmas+1)*50);
        button.setBounds(0, 150, 200, 75);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(Color.white);
        return button;
    }


    // SET BAKERY UPGRADE BUTTON, 
    // POST-CONDITION: returns a modified button
    public JButton setBakeryUpgradeButton(JButton button) {
        button.setText("Bakeries: " + bakeries +
        "  |  $" + (bakeries+1)*1000);
        button.setBounds(0, 250, 200, 75);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(Color.white);
        return button;
    }



    //--------------{SET LABEL METHODS}--------------\\



    // SET COOKIES LABEL, displays number of cookies
    // POST-CONDITION: returns a modified label
    public JLabel setCookiesLabel(JLabel label) {
        label = new JLabel(cookies + " Cookies!");
        label.setFont(new Font("Comic Sans", Font.BOLD, 30));
        label.setForeground(Color.white);
        label.setBounds(310, 0, 1000, 100);
        return label;
    }



    // SET UPGRADE LABEL, title
    // POST-CONDITION: returns a modified label
    public JLabel setUpgradeLabel (JLabel label) {
        label.setBounds(0, 0, 200, 650);
        label.setBackground(new Color(130,102,68));
        label.setOpaque(true);
        JLabel upgrades = new JLabel("Upgrades");
        upgrades.setBounds(50,-30,200,100);
        upgrades.setForeground(Color.white);
        upgrades.setFont(new Font("Comic Sans", Font.BOLD, 20));

        label = addUpgradeLabel(label, upgrades);
        return label;
    }



    // ADD UPGRADE LABEL, modifies label in parameter, 
    // adds the title parameter & buttons into the label

    // POST-CONDITION: returns a modified label
    public JLabel addUpgradeLabel(JLabel label, JLabel title) {
        clickUpgradeButton = new JButton();
        clickUpgradeButton = setClickUpgradeButton(clickUpgradeButton);

        grandmaUpgradeButton = new JButton();
        grandmaUpgradeButton = setGrandmaUpgradeButton(grandmaUpgradeButton);

        bakeryUpgradeButton = new JButton();
        bakeryUpgradeButton = setBakeryUpgradeButton(bakeryUpgradeButton);

        label.add(title);
        label.add(clickUpgradeButton);
        label.add(grandmaUpgradeButton);
        label.add(bakeryUpgradeButton);
        label.setVisible(true);
        return label;
    }



    // SET UPGRADE DISPLAY LABEL, 
    // POST-CONDITION: returns a modified label
    public JLabel setUpgradeDisplayLabel (JLabel label) {
        label.setBounds(610, 0, 200, 650);
        label.setBackground(new Color(130,102,68));
        label.setOpaque(true);
        
        label = addUpgradeDisplayLabel(label);
        return label;
    }



    // ADD UPGRADE DISPLAY LABEL, creates arsenal label,
    // adds buttons to the label in parameter
    // POST-CONDITION: returns a modified label
    public JLabel addUpgradeDisplayLabel(JLabel label) {

        JLabel arsenal = new JLabel("Arsenal");
        arsenal = setArsenalLabel(arsenal);

        cursorDisplayLabel = new JLabel("X " + clickers);
        cursorDisplayLabel = setCursorDisplayLabel(cursorDisplayLabel);

        grandmaDisplayLabel = new JLabel("X " + grandmas);
        grandmaDisplayLabel = setGrandmaDisplayLabel(grandmaDisplayLabel);

        bakeryDisplayLabel = new JLabel("X " + bakeries);
        bakeryDisplayLabel = setBakeryLabel(bakeryDisplayLabel);

        label.add(grandmaDisplayLabel);
        label.add(arsenal);
        label.add(cursorDisplayLabel);
        label.add(bakeryDisplayLabel);

        return label;
    }



    // SET ARSENAL LABEL,
    // POST-CONDITION: returns a modified label
    public JLabel setArsenalLabel (JLabel label) {
        label.setBounds(50,-30,200,100);
        label.setForeground(Color.white);
        label.setFont(new Font("Comic Sans", Font.BOLD, 20));
        return label;
    }


    // SET CURSOR DISPLAY LABEL, 
    // POST-CONDITION: returns a modified label
    public JLabel setCursorDisplayLabel(JLabel label) {

        label.setFont(new Font("Comic Sans", Font.BOLD, 20));
        label.setIcon(new ImageIcon("clicker.png"));
        label.setBounds(35, 50, 150, 150);
        label.setForeground(Color.white);
        label.setOpaque(false);
        label.setFocusable(false);
        label.setBackground(null);

        if (clickers > 0) { // if theres existing clickers
            label.setVisible(true);
            label.setText("X " + clickers);
        }

        return label;
    }



    // SET GRANDMA DISPLAY LABEL, 
    // POST-CONDITION: returns a modified label
    public JLabel setGrandmaDisplayLabel(JLabel label) {
        label.setFont(new Font("Comic Sans", Font.BOLD, 20));
        label.setIcon(new ImageIcon("grandma.png"));
        label.setBounds(35, 150, 150, 150);
        label.setForeground(Color.white);
        label.setOpaque(false);
        label.setFocusable(false);
        label.setBackground(null);
        label.setVisible(false);

        if (grandmas > 0) {
            label.setVisible(true);
            label.setText("X " + grandmas);
        }

        return label;
    }



    // SET BAKERY LABEL,
    // POST-CONDITION: returns a modified label
    public JLabel setBakeryLabel(JLabel label) {
        label.setFont(new Font("Comic Sans", Font.BOLD, 20));
        label.setIcon(new ImageIcon("bakery.png"));
        label.setBounds(35, 250, 150, 150);
        label.setForeground(Color.white);
        label.setOpaque(false);
        label.setFocusable(false);
        label.setBackground(null);
        label.setVisible(false);
        
        if (bakeries > 0) {
            label.setVisible(true);
            label.setText("X " + bakeries);
        }

        return label;
    }

    

    //--------------{BACKGROUND METHODS}--------------\\



    // background, manages process that happens in the background
    public void background() throws FileNotFoundException{

        int interval = 1;
        int time = 0;

        while (frame.isVisible()) {
            time++;
            wait(1000);
            if (time == interval * 3) { // 3 secs per interval
                cookies += grandmas*5; // 5 cookies added
                cookies += bakeries*50; //50 cookies added 
                interval++;
                cookiesLabel.setText(cookies + " Cookies!");
            }
            else if (saved == true) {
                save();
                saveButton.setBounds(570, 500, 250, 50);
                saveButton.setFont(new Font("Comic Sans", Font.BOLD, 15));
                saveButton.setText("Auto Save On");
            }
        }
    }



    // SAVE METHOD, creates and prints data into a file.
    public void save() throws FileNotFoundException {
        // creates new file using the saveName string
        PrintStream saveFile = new PrintStream(new File(saveName));
        saveFile.println(cookies);
        saveFile.println(clickers);
        saveFile.println(grandmas);
        saveFile.println(bakeries);
        // prints integers into file
    }



    // WAIT METHOD, interupts the excution of code.
    public static void wait(int ms) {
        try {
           Thread.sleep(ms); 
        }
        catch(InterruptedException ex) {
           Thread.currentThread().interrupt();
        }
     } // ends wait method

}