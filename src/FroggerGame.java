import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class FroggerGame extends JFrame{
    private GamePanel game;
    private Timer myTimer;
    public static ArrayList<Lane>lanes;
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
                game.animate();
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
        int direction;
        setSize(680,750);
        addKeyListener(this);
        // Loading images
        try {
            background = ImageIO.read(new File("Images/Background/Background1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        for(int i=0;i<12;i++){
            if(i%2==0) direction=-1;
            else direction=1;
            lanes.add(new Lane((getHeight()/12)*i,1,direction,zones));
        }

         */
        // Starting the game
        resetGame();
    }
    // Game related functions
    public void resetGame(){
        player = new Player(310,625, "Images/Frog");
    }
    public void animate(){
        player.animate();
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
        // Checking key presses and only accepting them if the allow the player to stay inbounds
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if(player.getPos(Player.Y) - 50 > 0) {
                player.jump(Player.UP, 0 ,-50);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            if(player.getPos(Player.Y)<gameFrame.getHeight()-150) {
                player.jump(Player.DOWN, 0, 50);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            if(player.getPos(Player.X) - 50 > 0) {
                player.jump(Player.LEFT, -50, 0);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_D) {
            if(player.getPos(Player.X)<gameFrame.getWidth()-100) {
                player.jump(Player.RIGHT, 50, 0);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
       //player.setFrame(0);
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}