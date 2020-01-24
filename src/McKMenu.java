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

        ImageIcon backPic = new ImageIcon("Images/Menu/menu.png");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);

        //ImageIcon startPic = new ImageIcon("Images/Menu/start.png");
        //ImageIcon instPic = new ImageIcon("Images/Menu/inst.png");
        JButton startBtn = new JButton("Play Game");
        JButton instBtn= new JButton("Instructions");
        startBtn.addActionListener(new ClickStart());
        instBtn.addActionListener(new ClickStart());
        startBtn.setBounds(180,340,300,50);

        instBtn.setBounds(180,415,300,50);
        layeredPane.add(startBtn,2);
        layeredPane.add(instBtn,3);
        startBtn.setBorder(null);
        startBtn.setFocusPainted(false);
        instBtn.setBorder(null);
        instBtn.setFocusPainted(false);
        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
