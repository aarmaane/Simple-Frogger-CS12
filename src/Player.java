import javax.imageio.*;
import java.awt.*;
import java.io.*;

public class Player {
    // Declaring fields
    private int[] pos = new int[4];
    private Image[][] sprites = new Image[4][2];
    private int direction;
    private int lives;
    private int xMove, yMove;
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
    public boolean doOverlap(int[]zoneRect) {
        // If one rectangle is on left side of other
        int[] hitBox = {pos[X]+8,pos[Y]+10,pos[WIDTH]-16,pos[HEIGHT]-20};
        if (hitBox[X] >= zoneRect[X]+zoneRect[WIDTH] || zoneRect[X] >= hitBox[X]+hitBox[WIDTH]) {
            return false;
        }

        // If one rectangle is above other
        else if (hitBox[Y] >= zoneRect[Y]+zoneRect[HEIGHT] || zoneRect[Y] >= hitBox[Y]+hitBox[HEIGHT]) {
            return false;
        }

        return true;
    }

    public boolean zoneCollide(Zone targetZone){
        return doOverlap(targetZone.getZoneRect());
    }
    public int getLives(){
        return lives;
    }
    public void kill(){
        lives--;
    }
    public void resetLives(){
        lives = 3;
    }
    public void resetPos(){
        pos[X]=310;
        pos[Y]=625;
        xMove = 0;
        yMove = 0;
    }
    public void moveWithLane(Lane lane){
        if(lane.getDirection() == Lane.LEFT){
            pos[X] -= lane.getSpeed()*2;
        }
        else{
            pos[X] += lane.getSpeed()*2;
        }
    }
}
