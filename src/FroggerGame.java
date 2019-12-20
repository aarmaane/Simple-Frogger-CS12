import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FroggerGame extends JFrame{
    private GamePanel game;
    private Timer myTimer;
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
    private Player player = new Player(0,0, "Images/Frog");
    // Images
    public GamePanel(FroggerGame game){
        gameFrame = game;
        setSize(700,650);
        addKeyListener(this);
    }
    // All window related methods
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
        BufferedImage background1= null;
        try {
            background1 = ImageIO.read(new File("Images/Background/Background1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setColor(new Color(151, 158, 255));
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(background1,0,0,this);
        g.drawImage(player.getCurrentImage(), player.getPos(player.X), player.getPos(player.Y),this);
    }
    // Keyboard related methods
    @Override
    public void keyPressed(KeyEvent e) {
        if(player.getPos(player.Y)>0) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                System.out.println("up");
                player.movePlayer(0, -50);
            }
        }
        if(player.getPos(player.Y)<gameFrame.getHeight()-100) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                System.out.println(gameFrame.getWidth());
                System.out.println("down");
                player.movePlayer(0, 50);
            }
        }
        if(player.getPos(player.X)>0) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                System.out.println("left");
                player.movePlayer(-50, 0);
            }
        }
        if(player.getPos(player.X)<gameFrame.getWidth()-100) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                System.out.println("right");
                player.movePlayer(50, 0);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}