import javax.swing.*;
import java.awt.event.*;

public class FroggerGame extends JFrame implements ActionListener{
    GamePanel game;
    public FroggerGame(){
        super("Frogger");
        // Creating the JPanel with GamePanel class
        game = new GamePanel(this);
        // Creating the JFrame
        setSize(700,700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        System.out.println("Epic");
        FroggerGame game = new FroggerGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.repaint();
    }
}

class GamePanel extends JPanel implements ActionListener{
    private boolean[] keys;
    private FroggerGame gameFrame;
    public GamePanel(FroggerGame game){
        keys = new boolean[KeyEvent.KEY_LAST + 1];
        gameFrame = game;
        setSize(700,650);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Epic action");
    }
}