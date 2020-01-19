import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FroggerGame extends JFrame{
    private GamePanel game;
    private Timer myTimer;
    private int runTime;
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
                // Main game loop
                game.animate();
                game.checkCollisions();
                game.checkStatus();
                game.repaint();
                // Keeping track of time
                runTime+=15;
                if(runTime % 1005 == 0){
                    game.iterateTime();
                }
            }
        }
    }
    public static void main(String[] args){
        FroggerGame game = new FroggerGame();
    }
}

class GamePanel extends JPanel implements KeyListener{
    //Constants
    public static final int LANE2=1;
    public static final int LANE4=3;
    // Window related Objects
    public boolean ready = true;
    private boolean[] keysPressed;
    // Game related Objects
    private FroggerGame gameFrame;
    private Player player;
    private int level = 1;
    private int time = 30;
    private Lane[] lanes = new Lane[10];
    private Lane snakeLane;
    private Zone[] winningZones = new Zone[5]; // 5 small zones that the player whens when they enter
    private boolean[] winningOccupied = new boolean[5];
    // Game Images
    private Image background;
    private Image[] winningImage = new Image[2];
    private Image livePic;
    // Game Fonts
    private Font arcadeFont;
    // Constructor for GamePanel
    public GamePanel(FroggerGame game){
        // Setting up the GamePanel
        gameFrame = game;
        setSize(680,750);
        keysPressed = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);
        try {
            // Loading images
            livePic=ImageIO.read(new File("Images/Frog/frog1.png"));
            background = ImageIO.read(new File("Images/Background/Background1.png"));
            winningImage[0] = ImageIO.read(new File("Images/Frog/frogWin1.png"));
            winningImage[1] = ImageIO.read(new File("Images/Frog/frogWin2.png"));
            // Loading font
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/arcade.ttf"));
            arcadeFont = arcadeFont.deriveFont(20f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
        player = new Player(310,625, "Images/Frog"); // Making the player object
        // Making the winning zones
        for(int i = 0; i < 5; i++){
            winningZones[i] = new Zone(Zone.WIN,getWidth(),40+(i*147),40,10,10);
        }
        // Starting the game
        resetGame();
    }
    // Game related functions
    public void resetGame(){
        player.resetPos();
        player.resetLives();
        winningOccupied = new boolean[5];
        time = 30;
        // Making the road lanes
        int direction;
        for(int i=0;i<5;i++) {
            if (i % 2 == 0) direction = Lane.LEFT;
            else direction = Lane.RIGHT;
            lanes[i] = new Lane(375 + 50 * i, level, direction, Zone.DEATH, randint(1, 3), "Cars/car" + (i + 1) + ".png", this.getWidth(), false, false, false);
        }
        // Making the river lanes
        int rivSpeed;
        for(int i=0; i<5; i++) {
            if(i==LANE2 || i==LANE4) rivSpeed=2*level;
            else rivSpeed=level;

            if (i % 2 == 0) direction = Lane.RIGHT;
            else direction = Lane.LEFT;

            if (direction == Lane.RIGHT) {
                lanes[i + 5] = new Lane(75 + 50 * i, rivSpeed, direction, Zone.WALK, 3, "Logs/log" + randint(1, 3) + ".png", this.getWidth(), true, false, false);
            }
            else {
                lanes[i + 5] = new Lane(75 + 50 * i, rivSpeed, direction, Zone.WALK, 3, "Turtles/turtle", this.getWidth(), true, true, true);
            }
        }
        // Adding snake lane
        if(level > 1){
            snakeLane = new Lane(325, level, Lane.LEFT, Zone.DEATH, randint(1,3),"Snakes/snake", this.getWidth(), true, false, true);
        }
    }
    public void animate(){
        player.animate();
        for(Lane lane:lanes){
            lane.animate();
        }
        if(level > 1){
            snakeLane.animate();
        }
    }

    public void checkCollisions(){
        boolean collided = false;
        // Checking collision with the Lanes
        for(Lane lane:lanes){
            for(Zone zone: lane.getZones()){
                if(!zone.isNone() && player.zoneCollide(zone) && !collided){
                    collided = true;
                    if(!zone.isSafe()){
                        player.dieAnimation();
                    }
                    else{
                        player.moveWithLane(lane);
                    }
                }

            }
        }
        if(level>1) {
            for (Zone zone : snakeLane.getZones()){
                if(!zone.isSafe() && player.zoneCollide(zone)){
                    player.dieAnimation();
                }
            }
        }
        // Checking collisions with winning zones
        for(int i = 0; i < 5; i++){
            Zone zone = winningZones[i];
            if(player.zoneCollide(zone) && !winningOccupied[i]){
                winningOccupied[i] = true;
                player.resetPos();
                player.addScore(150,time);
                player.resetLaneScoring();
                resetTime();
            }
        }
        // Checking collision with the water (only if nothing else has been collided with
        if(!collided && player.getPos(Player.Y)<285){
            player.dieAnimation();
        }
    }
    public void checkStatus(){
        // Checking if the user has won
        int count = 0;
        for(boolean bool: winningOccupied){
            if(bool) count++;
        }
        if(count == winningOccupied.length){
            level++;
            resetGame();
        }
        // Checking if the death animation is done
        if(player.getStatus() == Player.DEAD && player.isDeathDone()){
            if(player.getLives() > 0){
                player.kill();
                player.resetPos();
            }
            else{
                level = 1;
                resetGame();
                player.resetScore();
            }
        }
    }
    public void iterateTime(){
        if(time>0) {
            time--;
        }
        else{
            resetGame();
        }

    }
    public void resetTime(){
        time=30;
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
        // Drawing the background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(background,0,0,this);
        // Drawing all of the objects in each lane
        g.setColor(new Color(255,255,255));
        for(Lane lane:lanes){
            for(Zone zone: lane.getZones()){
                if(!lane.getIsAlt() || !zone.isNone()){
                    g.drawImage(lane.getSprite(), zone.getX(), zone.getY(), this);
                    //g.drawRect(zone.getZoneRect()[0],zone.getZoneRect()[1],zone.getZoneRect()[2],zone.getZoneRect()[3]);
                }
                else{
                    g.drawImage(lane.getAltSprite(), zone.getX(), zone.getY(), this);
                }
            }
        }
        if(level > 1){
            for(Zone zone: snakeLane.getZones()){
                g.drawImage(snakeLane.getSprite(), zone.getX(), zone.getY(), this);
            }
        }
        // Drawing the player
        g.drawImage(player.getCurrentImage(), player.getPos(Player.X), player.getPos(Player.Y), this);
        //g.drawRect(player.getPos(Player.X)+8,player.getPos(Player.Y)+10,player.getPos(2)-16,player.getPos(3)-20);
        // Drawing the frogs to represent the winning areas the player has reached
        for(int i = 0; i < 5; i++){
            if(winningOccupied[i]){
                g.drawImage(winningImage[i%2], 19 + (i*148), 27, null);
            }
        }
        // Drawing the stats
        for(int i=0;i<player.getLives();i++){
            g.drawImage(livePic,50*i,670,this);
        }
        g.fillRect(100+7*(60-time),685,7*time,25);
        // Drawing text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(arcadeFont);
        g2d.drawString("Time:" + time,530,705);
        g2d.drawString("Score:" + player.getScore(), 105, 705);
    }
    // Keyboard related methods
    @Override
    public void keyPressed(KeyEvent e) {
        // Checking key presses and only accepting them if the allow the player to stay inbounds
        if (e.getKeyCode() == KeyEvent.VK_W && !keysPressed[KeyEvent.VK_W]) {
            if (player.getPos(Player.Y) - 50 > 0) {
                player.jump(Player.UP, 0, -50);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S && !keysPressed[KeyEvent.VK_S]) {
            if (player.getPos(Player.Y) < gameFrame.getHeight() - 150) {
                player.jump(Player.DOWN, 0, 50);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A && !keysPressed[KeyEvent.VK_A]) {
            if (player.getPos(Player.X) - 50 > 0) {
                player.jump(Player.LEFT, -50, 0);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D && !keysPressed[KeyEvent.VK_D]) {
            if (player.getPos(Player.X) < gameFrame.getWidth() - 100) {
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