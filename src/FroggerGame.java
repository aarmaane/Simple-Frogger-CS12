import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FroggerGame extends JFrame{
    private GamePanel game;
    private Timer myTimer;
    public FroggerGame(){
        super("Frogger");
        // Creating the JPanel with GamePanel class
        game = new GamePanel(this);
        // Creating the JFrame
        setSize(680,750);
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
        FroggerGame game = new FroggerGame();
    }
}

class GamePanel extends JPanel implements KeyListener{
    public boolean ready = true;
    private FroggerGame gameFrame;
    private Player player;
    private Image background;
    // Constructor for GamePanel
    public GamePanel(FroggerGame game){
        gameFrame = game;
        setSize(680,750);
        addKeyListener(this);
        // Loading images
        try {
            background = ImageIO.read(new File("Images/Background/Background1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Starting the game
        resetGame();
    }
    // Game related functions
    public void resetGame(){
        player = new Player(0,625, "Images/Frog");
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
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(background,0,0,this);
        g.drawImage(player.getCurrentImage(), player.getPos(Player.X), player.getPos(Player.Y),this);
    }
    // Keyboard related methods
    @Override
    public void keyPressed(KeyEvent e) {
        if(player.getPos(Player.Y)>0) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                System.out.println("up");
                player.movePlayer(0, -50);
            }
        }
        if(player.getPos(Player.Y)<gameFrame.getHeight()-100) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                System.out.println(gameFrame.getWidth());
                System.out.println("down");
                player.movePlayer(0, 50);
            }
        }
        if(player.getPos(Player.X)>0) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                System.out.println("left");
                player.movePlayer(-50, 0);
            }
        }
        if(player.getPos(Player.X)<gameFrame.getWidth()-100) {
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