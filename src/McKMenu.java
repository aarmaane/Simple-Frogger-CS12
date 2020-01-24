//McKMenu.java
//Shivan Gaur and Armaan Randhawa
//This is a program given by Mr. Mckenzie, and is used to be the Menu for the Frogger Game

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Main Class
public class McKMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();

    public McKMenu() {
        //Constructor for this class which creates the Menu
        super("Frogger");
        setSize(680,750);//Sie

        ImageIcon backPic = new ImageIcon("Images/Background/Background1.png");
        ImageIcon logoPic= new ImageIcon("Images/Background/logo.png");
        JLabel back = new JLabel(backPic);
        JLabel logo = new JLabel(logoPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        logo.setBounds(100,100,logoPic.getIconWidth(),logoPic.getIconHeight());
        layeredPane.add(back, Integer.valueOf(0));
        layeredPane.add(logo, Integer.valueOf(1));
        JButton startBtn = new JButton("Play Game");
        startBtn.addActionListener(new ClickStart());
        startBtn.setBounds(180,410,300,50);
        layeredPane.add(startBtn, Integer.valueOf(2));
        //startBtn.setFocusPainted(false);
        //startBtn.setBorder(null);
        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        int ok=1;
    }

    public static void main(String[] arguments) {
        McKMenu frame = new McKMenu();
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
