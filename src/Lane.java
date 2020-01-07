import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Lane {
    private int speed;
    private int yPos;
    private int direction;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private Zone[] zones;
    private Image sprite;
    public Lane(int yPos, int speed, int direction, int type, int zoneNum, String image, int screenLength){
        this.yPos=yPos;
        this.speed=speed;
        this.direction=direction;
        zones = new Zone[zoneNum];
        // Loading the sprite for each of the zones in the lane
        try{
            sprite = ImageIO.read(new File("Images/Objects/" + image));
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        // Creating the zones for the lane
        for(int i = 0; i < zoneNum; i++){
            zones[i] = new Zone(type, screenLength, i * (randint(0,3)*100), yPos, sprite.getWidth(null), sprite.getHeight(null));
        }
    }
    public void animate(){
        int directionModifier = 1;
        if(direction == LEFT){
            directionModifier = -1;
        }
        for(Zone zone:zones){
            zone.moveX(speed * directionModifier);
        }
    }
    public int getYPos(){
        return yPos;
    }
    public Image getSprite(){
        return sprite;
    }
    public Zone[] getZones(){
        return zones;
    }
    // Extra helper methods
    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
}
