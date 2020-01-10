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
    private Image livePic;
    private int speed=1;
    private Lane[] lanes = new Lane[10];
    private Zone[] winningZones = new Zone[5]; // 5 small zones that the player whens when they enter
    private boolean[] winningOccupied = new boolean[5];
    private Image[] winningImage = new Image[2];
    // Constructor for GamePanel
    public GamePanel(FroggerGame game){
        gameFrame = game;
        setSize(680,750);
        keysPressed = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);
        // Loading images
        try {
            livePic=ImageIO.read(new File("Images/Frog/frog1.png"));
            background = ImageIO.read(new File("Images/Background/Background1.png"));
            winningImage[0] = ImageIO.read(new File("Images/Frog/frogWin1.png"));
            winningImage[1] = ImageIO.read(new File("Images/Frog/frogWin2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        player = new Player(310,625, "Images/Frog");
        // Making the winning zones
        for(int i = 0; i < 5; i++){
            winningZones[i] = new Zone(Zone.WIN,getWidth(),40+(i*147),40,10,10);
        }
        // Starting the game
        resetGame(speed);
    }
    // Game related functions
    public void resetGame(int speed){
        player.resetPos();
        player.resetLives();
        winningOccupied = new boolean[5];
        int direction;
        for(int i=0;i<5;i++){
            if(i%2==0) direction = Lane.LEFT;
            else direction = Lane.RIGHT;
            // Making the road lanes
            lanes[i] = new Lane(375+50*i,speed, direction, Zone.DEATH, randint(1,3),"Cars/car" + (i+1) + ".png", this.getWidth());
            // Making the river lanes
            lanes[i+5] = new Lane(75+50*i,speed, direction, Zone.WALK, randint(2,3),"Logs/log" + randint(1,3) + ".png", this.getWidth());
        }
    }
    public void animate(){
        player.animate();
        for(Lane lane:lanes){
            lane.animate();
        }
    }

    public void checkCollisions(){
        boolean collided = false;
        for(Lane lane:lanes){
            for(Zone zone: lane.getZones()){
                if(player.zoneCollide(zone) && !collided){
                    collided = true;
                    if(!zone.isSafe()){
                        if(player.getLives()>0) {
                            player.kill();
                            player.resetPos();
                        }
                        else{
                            resetGame(speed);
                        }
                    }
                    else{
                        player.moveWithLane(lane);
                    }
                }

            }
        }
        for(int i = 0; i < 5; i++){
            Zone zone = winningZones[i];
            int count=0;
            if(player.zoneCollide(zone) && !winningOccupied[i]){
                winningOccupied[i] = true;
                player.resetPos();
                System.out.println("Won");
            }

            for(boolean bool: winningOccupied){
                if(bool) count++;
            }
            if(count==winningOccupied.length){
                speed=3;
                resetGame(speed);
            }

        }
        if(!collided && player.getPos(Player.Y)<325){
            if(player.getLives()>0) {
                player.kill();
                player.resetPos();
            }
            else{
                resetGame(speed);
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
        // Drawing the background

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(background,0,0,this);
        // Drawing all of the objects in each lane
        g.setColor(new Color(255,255,255));
        Graphics2D g2d = (Graphics2D) g;
        Font font =new Font("Consolas",0,35);
        g2d.setFont(font);
        g2d.drawString("Time:"+6,240,705);
        for(Lane lane:lanes){
            for(Zone zone: lane.getZones()){
                g.drawImage(lane.getSprite(), zone.getX(), zone.getY(), this);
                g.drawRect(zone.getZoneRect()[0],zone.getZoneRect()[1],zone.getZoneRect()[2],zone.getZoneRect()[3]);
            }
        }
        for(int i=0;i<player.getLives();i++){
            g.drawImage(livePic,5+50*i,670,this);
        }
        // Drawing the player
        g.drawImage(player.getCurrentImage(), player.getPos(Player.X), player.getPos(Player.Y),this);
        g.drawRect(player.getPos(Player.X)+8,player.getPos(Player.Y)+10,player.getPos(2)-16,player.getPos(3)-20);
        // Drawing the frogs to represent the winning areas the player has reached
        for(int i = 0; i < 5; i++){
            if(winningOccupied[i]){
                g.drawImage(winningImage[i%2], 19 + (i*148), 27, null);
            }
        }
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