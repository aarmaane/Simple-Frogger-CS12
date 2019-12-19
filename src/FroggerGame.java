import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FroggerGame extends JFrame{
    GamePanel game;
    Timer myTimer;
    public FroggerGame(){
        super("Frogger");
        // Creating the JPanel with GamePanel class
        game = new GamePanel(this);
        // Creating the JFrame
        setSize(700,700);
        add(game); // Adding the JPanel
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // Starting a timer to update the frames
        myTimer = new Timer(10, new TickListener());	 // trigger every 100 ms
        myTimer.start();
    }
    // TickListener Class
    class TickListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.repaint();
            }
        }
    }
    public static void main(String[] args){
        System.out.println("Epic");
        FroggerGame game = new FroggerGame();
    }
}

class GamePanel extends JPanel implements KeyListener{
    public boolean ready = true;
    private FroggerGame gameFrame;
    // Images
    private BufferedImage[] frogSprites;
    public GamePanel(FroggerGame game){
        gameFrame = game;
        setSize(700,650);
        addKeyListener(this);
    }
    // All related
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public void removeNotify(){
        super.removeNotify();
        ready = false;
    }
    public void paintComponent(Graphics g){
        //System.out.println("Painting");
        g.setColor(new Color(255, 0, 10));
        g.fillRect(0,0,getWidth(),getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            System.out.println("up");
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            System.out.println("down");
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            System.out.println("left");
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            System.out.println("right");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}