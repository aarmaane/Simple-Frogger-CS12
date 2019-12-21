import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;


public class Player {
    // Declaring fields
    private int[] pos = new int[2];
    private Image[] sprites = new BufferedImage[2];
    // Declaring constants
    public static final int X = 0;
    public static final int Y = 1;
    // Constructor
    public Player(int x, int y, String spritePath){
        pos[X] = x;
        pos[Y] = y;
        try{
            sprites[0] = ImageIO.read(new File(spritePath + "/frog1.png"));
            sprites[1] = ImageIO.read(new File(spritePath + "/frog2.png"));
        }
        catch(IOException e){
            System.out.println("Invalid sprite path");
            System.exit(1);
        }

    }
    // Methods
    public Image getCurrentImage(){
        return sprites[0];
    }
    public int getPos(int coord) {
        return pos[coord];
    }
    public void movePlayer(int deltaX, int deltaY){
        pos[X] += deltaX;
        pos[Y] += deltaY;
    }
}
