//Player.java
//Shivan Gaur and Armaan Randhawa
//Class that creates the user's "player" object that has it's coordinates, lives, score, and different sprites

import javax.imageio.*;
import java.awt.*;
import java.io.*;

public class Player {
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
    // Declaring fields
    private int[] pos = new int[4];
    private Image[][] sprites = new Image[4][2]; //Array with 2 sprites for each direction the player moves (8 total)
    private Image[] deathSprites = new Image[7]; //Array will all sprites for the death animations
    private int direction; // Direction that the player is facing (represented as an integer by constants)
    private int lives;
    private int highestLane; //The highest lane the player has reached
    private int xMove, yMove; // The amount the player still has to move by in the x and y coordinates
    private int score; // Player's current score
    private int status; // Status holds if the Player is alive or dead
    private int deathCount; //Counter for death animation
    // Constructor
    public Player(int x, int y, String spritePath){
        pos[X] = x;
        pos[Y] = y;
        direction = UP;
        highestLane=y;
        status = ALIVE;
        lives = 2;//2 Additional lives which excludes the current life the user has (3 total)
        deathCount = 0;
        try{
            //Loading Image sprites
            int imageNum = 1;
            for(int i = 0; i < 4; i++){
                for(int f = 0; f < 2; f++){
                    sprites[i][f] = ImageIO.read(new File(spritePath + "/frog" +imageNum + ".png"));
                    imageNum++;
                }
            }
            //Loading Death Sprites
            for(int i=0;i<7;i++){
                deathSprites[i] = ImageIO.read(new File("Images/Frog/death"+(i+1)+".png"));
            }
        }
        catch(IOException e){
            System.out.println("Invalid sprite path");
            System.exit(1);
        }
        // Storing sprite size
        pos[WIDTH]=sprites[0][0].getWidth(null);
        pos[HEIGHT]=sprites[0][0].getHeight(null);

    }
    ////Methods
    public void animate(){
        // Method that animates the player and continues to move it on the screen
        if(status == DEAD){
            deathCount += 1; //increasing death counter if the player is in the dying animation
        }
        // Moving/Animating the player by the stored value that they still have to move by
        else if(xMove != 0){
            // Modifying the values added to the X position and taken away from remaining xMove depending on the direction that was specified (by a negative or positive value)
            int negativeModifier = 1;
            if(xMove < 0){
                negativeModifier = -1;
            }
            pos[X] += 5 * negativeModifier;
            xMove -= 5 * negativeModifier;
        }
        else if(yMove != 0){
            // Same as above but for y coordinate
            int negativeModifier = 1;
            if(yMove < 0){
                negativeModifier = -1;
            }
            pos[Y] += 5 * negativeModifier;
            yMove -= 5 * negativeModifier;
        }
    }
    public void jump(int dir, int deltaX, int deltaY){
        // Method to make the Player jump
        if(xMove == 0 && yMove == 0 && status == ALIVE) { // Only letting the code run if there is no ongoing jump and the Player is alive
            direction = dir; // Setting the new player direction
            // Storing the amount the player needs to move by
            xMove += deltaX;
            yMove += deltaY;
            // Keeping track of the highest lane the Player has entered
            if(direction==UP && pos[Y]==highestLane){
                //Once the player reaches a new Lane, that becomes the new highest Lane, and 10 points gets added to their score
                highestLane-=50;
                score+=10;
            }
        }
    }
    public void addScore(int points,int time){
        //This method adds the amount of points passed as a parameter, and 10 points for every unused half second the player has.
        score += (points+20*time);
    }
    public void moveWithLane(Lane lane){
        // Method to move the player with the same momentum as the objects in the specified lane
        if(yMove == 0 && status == ALIVE){ // Only letting the code run if the player isn't jumping up/down and is alive
            // Moving the player depending on the speed and direction of the lane
            if(lane.getDirection() == Lane.LEFT){
                pos[X] -= lane.getSpeed();
            }
            else{
                pos[X] += lane.getSpeed();
            }
        }
    }

    public boolean doesOverlap(int[]zoneRect) {
        /*This method calculates whether the given zoneRect overlaps with the player's Rect by checking the situations where the Rects
        DO NOT overlap*/

        // If one rectangle is on left side of other
        int[] hitBox = {pos[X]+4,pos[Y]+5,pos[WIDTH]-16,pos[HEIGHT]-20};
        if (hitBox[X] > zoneRect[X]+zoneRect[WIDTH] || zoneRect[X] > hitBox[X]+hitBox[WIDTH]) {
            return false;
        }

        // If one rectangle is above other
        else if (hitBox[Y] > zoneRect[Y]+zoneRect[HEIGHT] || zoneRect[Y] > hitBox[Y]+hitBox[HEIGHT]) {
            return false;
        }
        //Otherwise the Rects overlap
        return true;
    }

    public boolean zoneCollide(Zone targetZone){
        //Method that calls doesOverlap to check if the zones collide.
        return doesOverlap(targetZone.getZoneRect());
    }

    ///Death Methods
    public void kill(){
        // Taking away a life from the player
        lives--;
    }
    public void dieAnimation(){
        // Setting the players status to Dead, which will allow the death animation code to run its course
        status=DEAD;
    }
    public boolean isDeathDone(){
        //This method returns whether the death animation has completed or not based on the counter.
        if(deathCount < 100){
            return false;
        }
        return true;
    }

    /////"Get" Methods
    public Image getCurrentImage(){
        //Method that returns current Image of the player that is associated with its status
        if(status == DEAD){
            //Getting the appropriate death sprite depending on where the player is and how much of the animation is done
            return getDeathImage();
        }
        // Returning the moving sprite for the current direction if the player is in motion
        else if(xMove != 0 || yMove != 0){
            return sprites[direction][1];
        }
        // Returning the stationary sprite for the current direction if none of the above were true
        return sprites[direction][0];
    }
    public Image getDeathImage(){
        // Seeing if we should return the water or road versions of sprites and setting an offset for the sprites if needed
        int waterSpriteOffset = 0;
        if(pos[Y] < 285){
            waterSpriteOffset = 4;
        }
        // Calculating which sprite to return depending on how much the animation has already progressed (by looking at the counter)
        if(deathCount>-1 && deathCount<26)
            return deathSprites[0 + waterSpriteOffset];
        else if(deathCount>25 && deathCount<51)
            return deathSprites[1 + waterSpriteOffset];
        else if(deathCount>50 && deathCount<76)
            return deathSprites[2 + waterSpriteOffset];
        return deathSprites[3];
    }

    public int getPos(int coord) { return pos[coord]; }
    public int getStatus(){ return status;}
    public int getLives(){ return lives; }
    public int getScore(){ return score; }

    //Reset Methods
    public void resetLives(){ lives = 2; }
    public void resetPos(){
        //This method resets all the position related fields of the player back to normal
        status = ALIVE;
        direction = UP;
        deathCount = 0;
        pos[X]=310;
        pos[Y]=625;
        xMove = 0;
        yMove = 0;
    }
    public void resetScore(){
        // Resets the score and calls the resetLane scoring method
        score=0;
        resetLaneScoring();
    }
    public void resetLaneScoring(){
        // This method resets the scoring based on reaching new lanes
        highestLane=625;
    }
}
