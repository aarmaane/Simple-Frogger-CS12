import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Lane {
    private int speed;
    private int yPos;
    private int direction;
    private boolean isAnimate;
    private boolean isAlt;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private Zone[] zones;
    private Image sprite;
    // Animation related fields
    private Image[] animatedSprites = new Image[3];
    private Image[] alternatingSprites = new Image[2];
    private int alternatingZone;
    private int alternateCount;
    private int spriteCount = 0;
    public Lane(int yPos, int speed, int direction, int type, int zoneNum, String image, int screenLength,boolean equallySpaced,boolean isAlt,boolean isAnimate){
        this.yPos=yPos;
        this.speed=speed;
        this.direction=direction;
        this.isAlt=isAlt;
        this.isAnimate=isAnimate;
        zones = new Zone[zoneNum];
        Image sizingSprite = null;
        // Loading the sprite for each of the zones in the lane
        try{
            if(!isAnimate){
                sprite = ImageIO.read(new File("Images/Objects/" + image));
                sizingSprite = sprite;
            }
            else{
                for(int i = 0; i < 3; i++){
                    animatedSprites[i] = ImageIO.read(new File("Images/Objects/" + image + (i+1) + ".png"));
                }
                sizingSprite = animatedSprites[0];
                if(isAnimate){
                    for(int i = 0; i < 2; i++){
                        alternatingSprites[i] = ImageIO.read(new File("Images/Objects/" + image + (i+4) + ".png"));
                    }
                }
            }

        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        // Creating the zones for the lane
        int spacing=screenLength/zoneNum;
        for(int i = 0; i < zoneNum; i++){
            if(equallySpaced) {
                zones[i] = new Zone(type, screenLength,spacing*i , yPos, sizingSprite.getWidth(null), sizingSprite.getHeight(null));
            }
            else{
                zones[i] = new Zone(type, screenLength, i * randint(1, 3) * 100 + sizingSprite.getWidth(null), yPos, sizingSprite.getWidth(null), sizingSprite.getHeight(null));

            }
        }
        // Choosing the alternating zone
        if(isAnimate){
            alternatingZone = randint(0,zones.length - 1);
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
        if(isAnimate){
           spriteCount += 1;
           if(spriteCount > (30*animatedSprites.length) - 1){
               spriteCount = 0;
           }
        }
        if(isAlt){
            alternateCount += 1;
            int[] zoneRect = zones[alternatingZone].getZoneRect();
            if((alternateCount == (30*alternatingSprites.length))){
                zones[alternatingZone] = new Zone(Zone.NONE, zones[alternatingZone].getScreenLength(), zoneRect[Zone.X], zoneRect[Zone.Y],zoneRect[Zone.WIDTH],zoneRect[Zone.HEIGHT]);
            }
            else if(alternateCount > (30*(alternatingSprites.length + 2)) - 1){
                alternateCount = 0;
                zones[alternatingZone] = new Zone(Zone.WALK, zones[alternatingZone].getScreenLength(), zoneRect[Zone.X], zoneRect[Zone.Y],zoneRect[Zone.WIDTH],zoneRect[Zone.HEIGHT]);
            }
        }
    }
    private void changeZones(int zoneType){
        for(int i = 0; i < zones.length; i++){
            int[] zoneRect = zones[i].getZoneRect();
            zones[i] = new Zone(zoneType, zones[i].getScreenLength(), zoneRect[Zone.X], zoneRect[Zone.Y],zoneRect[Zone.WIDTH],zoneRect[Zone.HEIGHT]);
        }
    }
    public int getYPos(){
        return yPos;
    }
    public Image getSprite(){
        if(isAnimate){
            return animatedSprites[spriteCount/30];
        }
        return sprite;
    }
    public Image getAltSprite(){
        return alternatingSprites[alternateCount/30-2];
    }
    public Zone[] getZones(){
        return zones;
    }
    public int getDirection(){ return direction;}
    public int getSpeed(){return speed;}
    public boolean getIsAlt(){
        return isAlt;
    }
    // Extra helper methods
    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
}
