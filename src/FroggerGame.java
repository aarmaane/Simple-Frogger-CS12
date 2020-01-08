import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        myTimer = new Timer(15, new TickListener());	 // trigger every 10 ms
        myTimer.start();
    }
    // TickListener Class
    class TickListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.animate();
                game.checkCollisions();
                game.repaint();
            }
        }
    }
    public static void main(String[] args){
        FroggerGame game = new FroggerGame();
    }
}

class GamePanel extends JPanel implements KeyListener{
    // Window related Objects
    public boolean ready = true;
    private boolean[] keysPressed;
    // Game related Objects
    private FroggerGame gameFrame;
    private Image background;
    private Player player;
    private Lane[] lanes = new Lane[10];
    private Zone[] winningZones = new Zone[6]; // 5 small zones that the player whens when they enter
    // Constructor for GamePanel
    public GamePanel(FroggerGame game){
        gameFrame = game;
        setSize(680,750);
        keysPressed = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);
        // Loading images
        try {
            background = ImageIO.read(new File("Images/Background/Background1.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        player = new Player(310,625, "Images/Frog");
        // Starting the game
        resetGame();
    }
    // Game related functions
    public void resetGame(){
        player.resetPos();
        player.resetLives();
        int direction;
        for(int i=0;i<5;i++){
            if(i%2==0) direction = Lane.LEFT;
            else direction = Lane.RIGHT;
            // Making the road lanes
            lanes[i] = new Lane(375+50*i,1, direction, Zone.DEATH, randint(1,3),"Cars/car" + (i+1) + ".png", this.getWidth());
            // Making the river lanes
            lanes[i+5] = new Lane(75+50*i,1, direction, Zone.WALK, 3,"Logs/log" + randint(1,3) + ".png", this.getWidth());
        }
    }
    public void animate(){
        player.animate();
        for(Lane lane:lanes){
            lane.animate();
        }
    }

    public void checkCollisions(){
        boolean laneCollided = false;
        for(Lane lane:lanes){
            for(Zone zone: lane.getZones()){
               // System.out.println(zone.getZoneRect()[0]+" "+zone.getZoneRect()[1]+" "+player.getPos(player.X)+" "+player.getPos(player.Y));
                if(player.zoneCollide(zone)){
                    laneCollided = true;
                    if(!zone.isSafe()){
                        System.out.println("dead");
                        if(player.getLives()>0) {
                            player.kill();
                            player.resetPos();
                        }
                        else{
                            resetGame();
                        }
                    }
                }

            }
        }
        if(player.getPos(player.Y)<325 && player.onWater(lanes)){
            System.out.println("water");
            if(player.getLives()>0) {
                player.kill();
                player.resetPos();
            }
            else{
                resetGame();
            }
        }
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
        // Drawing all of the objects in each lane
        for(Lane lane:lanes){
            for(Zone zone: lane.getZones()){
                g.drawImage(lane.getSprite(), zone.getX(), zone.getY(), this);
                g.drawRect(zone.getZoneRect()[0],zone.getZoneRect()[1],zone.getZoneRect()[2],zone.getZoneRect()[3]);
            }
        }
        g.drawImage(player.getCurrentImage(), player.getPos(Player.X), player.getPos(Player.Y),this);
        g.drawRect(player.getPos(player.X),player.getPos(player.Y),player.getPos(2),player.getPos(3));

    }
    // Keyboard related methods
    @Override
    public void keyPressed(KeyEvent e) {
        // Checking key presses and only accepting them if the allow the player to stay inbounds
        if (e.getKeyCode() == KeyEvent.VK_W && !keysPressed[KeyEvent.VK_W]) {
            if(player.getPos(Player.Y) - 50 > 0) {
                player.jump(Player.UP, 0 ,-50);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_S && !keysPressed[KeyEvent.VK_S]) {
            if(player.getPos(Player.Y)<gameFrame.getHeight()-150) {
                player.jump(Player.DOWN, 0, 50);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_A && !keysPressed[KeyEvent.VK_A]) {
            if(player.getPos(Player.X) - 50 > 0) {
                player.jump(Player.LEFT, -50, 0);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_D && !keysPressed[KeyEvent.VK_D]) {
            if(player.getPos(Player.X)<gameFrame.getWidth()-100) {
                player.jump(Player.RIGHT, 50, 0);
            }
        }
        // Keeping track of whether or not the key is pressed down
        keysPressed[e.getKeyCode()] = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
       keysPressed[e.getKeyCode()] = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    // Extra helper functions
    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }

}