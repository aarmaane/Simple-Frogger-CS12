//Lane.java
//Shivan Gaur and Armaan Randhawa
//Class that creates Lane objects with conditions including direction, Image, type and position to organize the Zones of the game.


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Lane {
    //This class helps organize the different zones in the game into Lanes

    //Declaring Constants
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    ////Declaring Object Fields
    private int speed;
    private int yPos;
    private int direction;
    private boolean isAnimate; //If the Lane has zones that animate they will need multiple sprites
    private boolean isAlt; //The turtles alternate between sinking and swimming so this boolean tracks the turtle which alternates
    private Zone[] zones;
    private Image sprite;

    // Animation related fields
    private Image[] animatedSprites = new Image[3];//Array of sprites for the animation of the zones
    private Image[] alternatingSprites = new Image[3];//Array of sprites for alternating animation of the zones
    private int alternatingZone;
    private int alternateCount;
    private int spriteCount = 0;

    //Constructor
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
            // Normal sprite loading
            if(!isAnimate){
                sprite = ImageIO.read(new File("Images/Objects/" + image));
                sizingSprite = sprite;
            }
            // Animating sprite loading
            else{
                for(int i = 0; i < 3; i++){
                    animatedSprites[i] = ImageIO.read(new File("Images/Objects/" + image + (i+1) + ".png"));
                }
                sizingSprite = animatedSprites[0];
                // Alternating sprite loading
                if(isAlt){
                    for(int i = 0; i < 3; i++){
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
        // Randomly choosing the zone that should alternate
        if(isAnimate){
            alternatingZone = randint(0,zones.length - 1);
        }

    }
    public void animate(){
        // Moving all of the zones forward
        int directionModifier = 1;
        if(direction == LEFT){
            directionModifier = -1;
        }
        for(Zone zone:zones){
            zone.moveX(speed * directionModifier);
        }
        // Progressing animation counters
        if(isAnimate){
           spriteCount += 1;
           if(spriteCount > (30*animatedSprites.length) - 1){
               spriteCount = 0;
           }
        }
        //Progressing animation counters for the alternating sprites
        if(isAlt){
            alternateCount += 1;
            int[] zoneRect = zones[alternatingZone].getZoneRect();//getting the zoneRect for the zone that will alternate
            if((alternateCount == (30*animatedSprites.length))){
                //Once the zone has cycled through one alternation, it is replaced with a "None" zone to mimic the turtle going underwater
                zones[alternatingZone] = new Zone(Zone.NONE, zones[alternatingZone].getScreenLength(), zoneRect[Zone.X], zoneRect[Zone.Y],zoneRect[Zone.WIDTH],zoneRect[Zone.HEIGHT]);
            }
            else if(alternateCount > (30*(animatedSprites.length + alternatingSprites.length)) - 1){
                //Once the counter has gone past the set number, the zone is changed back to a "Walk" zone to mimic the turtle coming
                //up from under the water
                alternateCount = 0;// Resetting the counter
                zones[alternatingZone] = new Zone(Zone.WALK, zones[alternatingZone].getScreenLength(), zoneRect[Zone.X], zoneRect[Zone.Y],zoneRect[Zone.WIDTH],zoneRect[Zone.HEIGHT]);
            }
        }
    }

    ////Methods that return fields from the Lane class

    public Image getSprite(){
        //This method returns the current sprite of the Lane
        if(isAnimate){
            //If the lane returns animated sprites, then the current Image in the Array is returned
            return animatedSprites[spriteCount/30];
        }
        return sprite; //Non-animated sprite
    }
    public Image getAltSprite(){
        //Returns the current alternating sprite of the Lane
        return alternatingSprites[alternateCount/30-animatedSprites.length];
    }
    public Zone[] getZones(){
        return zones;
    }
    public int getDirection(){return direction;}
    public int getSpeed(){return speed;}
    public boolean getIsAlt(){
        return isAlt;
    }
    // Extra helper methods
    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
}
