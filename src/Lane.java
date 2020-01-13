import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Lane {
    private int speed;
    private int yPos;
    private int direction;
    private boolean equallySpaced;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private Zone[] zones;
    private Image sprite;
    public Lane(int yPos, int speed, int direction, int type, int zoneNum, String image, int screenLength,boolean equallySpaced){
        this.yPos=yPos;
        this.speed=speed;
        this.direction=direction;
        this.equallySpaced=equallySpaced;
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
        int spacing=(screenLength/zoneNum)/sprite.getWidth(null);
        //i*sprite.getWidth()
        for(int i = 0; i < zoneNum; i++){
            if(equallySpaced) {
                zones[i] = new Zone(type, screenLength,spacing*i , yPos, sprite.getWidth(null), sprite.getHeight(null));
            }
            else{
                zones[i] = new Zone(type, screenLength, i * randint(1, 3) * 100 + sprite.getWidth(null), yPos, sprite.getWidth(null), sprite.getHeight(null));

            }
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
    public int getDirection(){ return direction;}
    public int getSpeed(){return speed;}
    // Extra helper methods
    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
}
