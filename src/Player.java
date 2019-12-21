import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.Buffer;


public class Player {
    // Declaring fields
    private int[] pos = new int[4];
    private BufferedImage[][] sprites = new BufferedImage[4][3];
    private int move,frame;
    private int lives;
    // Declaring constants
    public static final int X = 0;
    public static final int Y = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    // Constructor
    public Player(int x, int y, String spritePath){
        pos[X] = x;
        pos[Y] = y;
        move=frame=0;
        lives=3;
        try{
            sprites[0][0] = ImageIO.read(new File(spritePath + "/frog1.png"));
            sprites[0][1] = ImageIO.read(new File(spritePath + "/frog2.png"));
            sprites[0][2] = ImageIO.read(new File(spritePath + "/frog3.png"));
            sprites[1][0] = ImageIO.read(new File(spritePath + "/frog4.png"));
            sprites[1][1] = ImageIO.read(new File(spritePath + "/frog5.png"));
            sprites[1][2] = ImageIO.read(new File(spritePath + "/frog6.png"));
            sprites[2][0] = ImageIO.read(new File(spritePath + "/frog7.png"));
            sprites[2][1] = ImageIO.read(new File(spritePath + "/frog8.png"));
            sprites[2][2] = ImageIO.read(new File(spritePath + "/frog9.png"));
            sprites[3][0] = ImageIO.read(new File(spritePath + "/frog10.png"));
            sprites[3][1] = ImageIO.read(new File(spritePath + "/frog11.png"));
            sprites[3][2] = ImageIO.read(new File(spritePath + "/frog12.png"));

        }
        catch(IOException e){
            System.out.println("Invalid sprite path");
            System.exit(1);
        }
        pos[WIDTH]=sprites[move][frame].getWidth();
        pos[HEIGHT]=sprites[move][frame].getHeight();

    }
    // Methods
    public BufferedImage getCurrentImage(){
        return sprites[move][frame];
    }
    public int getPos(int coord) {
        return pos[coord];
    }
    public void setMove(int move){
        this.move=move;

    }
    public void setFrame(int frame){
        this.frame=frame;
    }
    public void movePlayer(int deltaX, int deltaY){
        pos[X] += deltaX;
        pos[Y] += deltaY;
    }
}
