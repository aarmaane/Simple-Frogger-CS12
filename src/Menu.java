//Menu.java
//Shivan Gaur and Armaan Randhawa
//This is a program given by Mr. Mckenzie, and is used to be the Menu for the Frogger Game

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Main Class
public class Menu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();

    public Menu() {
        //Constructor for this class which creates the Menu
        super("Frogger");
        setSize(680,750);//Sie

        ImageIcon backPic = new ImageIcon("Images/Background/Background1.png");
        ImageIcon logoPic= new ImageIcon("Images/Menu/logo.png");
        ImageIcon blackStrip = new ImageIcon("Images/Menu/blackStrip.png");
        JLabel back = new JLabel(backPic);
        JLabel logo = new JLabel(logoPic);
        JLabel strip = new JLabel(blackStrip);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        logo.setBounds(110,160,logoPic.getIconWidth(),logoPic.getIconHeight());
        strip.setBounds(0,710,blackStrip.getIconWidth(),blackStrip.getIconHeight());
        JButton startBtn = new JButton("Play Game");
        startBtn.addActionListener(new ClickStart());
        startBtn.setBounds(160,450,350,70);
        layeredPane.add(back, Integer.valueOf(0));
        layeredPane.add(logo, Integer.valueOf(1));
        layeredPane.add(startBtn, Integer.valueOf(2));
        layeredPane.add(strip, Integer.valueOf(3));
        //startBtn.setFocusPainted(false);
        //startBtn.setBorder(null);
        setContentPane(layeredPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        Menu frame = new Menu();
    }

    class ClickStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            //Once the user clicks on the JButton and exits the Menu Window, the actual Frogger Game Starts
            FroggerGame game = new FroggerGame();
            setVisible(false);
        }
    }
}
