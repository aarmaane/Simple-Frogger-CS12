import javax.imageio.*;
import java.awt.*;
import java.io.*;

public class Player {
    // Declaring fields
    private int[] pos = new int[4];
    private Image[][] sprites = new Image[4][2];
    private int direction;
    private int xMove, yMove;
    private int lives;
    // Declaring constants
    public static final int X = 0;
    public static final int Y = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    // Constructor
    public Player(int x, int y, String spritePath){
        pos[X] = x;
        pos[Y] = y;
        direction = UP;
        lives = 3;
        try{
            int imageNum = 1;
            for(int i = 0; i < 4; i++){
                for(int f = 0; f < 2; f++){
                    sprites[i][f] = ImageIO.read(new File(spritePath + "/frog" +imageNum + ".png"));
                    imageNum++;
                }
            }
        }
        catch(IOException e){
            System.out.println("Invalid sprite path");
            System.exit(1);
        }
        pos[WIDTH]=sprites[0][0].getWidth(null);
        pos[HEIGHT]=sprites[0][0].getHeight(null);

    }
    // Methods
    public Image getCurrentImage(){
        if(xMove != 0 || yMove != 0){
            return sprites[direction][1];
        }
        return sprites[direction][0];
    }
    public int getPos(int coord) {
        return pos[coord];
    }
    public void jump(int dir, int deltaX, int deltaY){
        if(xMove == 0 && yMove == 0) {
            direction = dir;
            xMove += deltaX;
            yMove += deltaY;
        }
    }
    public void animate(){
        if(xMove != 0){
            int negativeModifier = 1;
            if(xMove < 0){
                negativeModifier = -1;
            }
            pos[X] += 5 * negativeModifier;
            xMove -= 5 * negativeModifier;
        }
        else if(yMove != 0){
            int negativeModifier = 1;
            if(yMove < 0){
                negativeModifier = -1;
            }
            pos[Y] += 5 * negativeModifier;
            yMove -= 5 * negativeModifier;
        }
    }
}
