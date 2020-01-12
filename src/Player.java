import javax.imageio.*;
import java.awt.*;
import java.io.*;

public class Player {
    // Declaring fields
    private int[] pos = new int[4];
    private Image[][] sprites = new Image[4][2];
    private Image[] deathSprites = new Image[7];
    private int direction;
    private int lives;
    private int xMove, yMove;
    private int score;
    private int status;
    private int deathCount;
    // Declaring constants
    public static final int X = 0;
    public static final int Y = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int DEAD = 0;
    public static final int ALIVE = 1;
    // Constructor
    public Player(int x, int y, String spritePath){
        pos[X] = x;
        pos[Y] = y;
        direction = UP;
        status = ALIVE;
        lives = 2;
        deathCount = 0;
        try{
            int imageNum = 1;
            for(int i = 0; i < 4; i++){
                for(int f = 0; f < 2; f++){
                    sprites[i][f] = ImageIO.read(new File(spritePath + "/frog" +imageNum + ".png"));
                    imageNum++;
                };
            }
            for(int i=0;i<7;i++){
                deathSprites[i] = ImageIO.read(new File("Images/Frog/death"+(i+1)+".png"));
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
        if(status == DEAD){
            return getDeathImage();
        }
        else if(xMove != 0 || yMove != 0){
            return sprites[direction][1];
        }
        return sprites[direction][0];
    }
    public Image getDeathImage(){
        // Seeing if we should return the water or road versions of sprites
        int waterSpriteOffset = 0;
        if(pos[Y] < 285){
            waterSpriteOffset = 4;
        }
        // Calculating which sprite to return
        if(deathCount>-1 && deathCount<26)
            return deathSprites[0 + waterSpriteOffset];
        else if(deathCount>25 && deathCount<51)
            return deathSprites[1 + waterSpriteOffset];
        else if(deathCount>50 && deathCount<76)
            return deathSprites[2 + waterSpriteOffset];
        return deathSprites[3];
    }
    public int getPos(int coord) {
        return pos[coord];
    }
    public int getStatus(){ return status;}
    public void jump(int dir, int deltaX, int deltaY){
        if(xMove == 0 && yMove == 0 && status == ALIVE) {
            direction = dir;
            xMove += deltaX;
            yMove += deltaY;
        }
    }
    public void animate(){
        if(status == DEAD){
            deathCount += 1;
        }
        else if(xMove != 0){
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
        int[] hitBox = {pos[X]+4,pos[Y]+5,pos[WIDTH]-16,pos[HEIGHT]-20};
        if (hitBox[X] > zoneRect[X]+zoneRect[WIDTH] || zoneRect[X] > hitBox[X]+hitBox[WIDTH]) {
            return false;
        }

        // If one rectangle is above other
        else if (hitBox[Y] > zoneRect[Y]+zoneRect[HEIGHT] || zoneRect[Y] > hitBox[Y]+hitBox[HEIGHT]) {
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
        lives = 2;
    }
    public void resetPos(){
        status = ALIVE;
        direction = UP;
        deathCount = 0;
        pos[X]=310;
        pos[Y]=625;
        xMove = 0;
        yMove = 0;
    }
    public void dieAnimation(){
        status=DEAD;
    }
    public boolean isDeathDone(){
        if(deathCount < 100){
            return false;
        }
        return true;
    }
    public void moveWithLane(Lane lane){
        if(yMove == 0 && status == ALIVE){
            if(lane.getDirection() == Lane.LEFT){
                pos[X] -= lane.getSpeed()*2;
            }
            else{
                pos[X] += lane.getSpeed()*2;
            }
        }
    }
    public int getScore(){
        return score;
    }
    public void addScore(int points){
        score += points;
    }
    public void resetScore(){
        score=0;
    }
}
