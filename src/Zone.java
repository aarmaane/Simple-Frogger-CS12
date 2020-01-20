// Zone.java
// Armaan Randhawa and Shivan Gaur
// Class that creates Zone objects that have their own rectangle coordinates and type

public class Zone {
    // Declaring constants
    public static final int NONE = 0;
    public static final int DEATH = 1;
    public static final int WALK = 2;
    public static final int WIN = 3;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    // Declaring object fields
    private int zoneType; // Holds the type of zone as an integer
    private int[] zoneRect = new int[4]; // Holds the position of the zone as an integer Array (X, Y, width, height)
    private int screenLength; // Holds the horizontal length of the screen so the zone knows what the outer bounds are

    // Constructor method
    public Zone(int zoneType, int screenLength, int x, int y, int width, int height){
        this.zoneType=zoneType;
        zoneRect[X] = x;
        zoneRect[Y] = y;
        zoneRect[WIDTH] = width;
        zoneRect[HEIGHT] = height;
        this.screenLength = screenLength;
    }

    // Method to move the zone by a specified amount (while checking if the zone goes outside of the screen)
    public void moveX(int deltaX){
        // If statements to check to see if the zone needs to be moved to the other side of the screen
        if(zoneRect[X] + deltaX > screenLength){
            // Shifting the zone to the right of the window
            zoneRect[X] += deltaX - (screenLength + zoneRect[WIDTH]);
        }
        else if(zoneRect[X] + deltaX < -zoneRect[WIDTH]){
            // Shifting the zone to the left of the window
            zoneRect[X] = screenLength;
        }
        else{
            // Moving the zone normally
            zoneRect[X] += deltaX;
        }
    }

    // Methods that return a boolean based on the zone's type
    public boolean isSafe(){  // Returns false of the zone is a death zone
        if(zoneType==DEATH){
            return false;
        }
        return true;
    }
    public boolean isNone(){
        if(zoneType==NONE){ // Returns true if the zone is a 'None' zone
            return true;
        }
        return false;
    }

    // Methods that return fields of each zone
    public int getX(){
        return zoneRect[X];
    }
    public int getY(){
        return zoneRect[Y];
    }
    public int[] getZoneRect(){
        return zoneRect;
    }
    public int getScreenLength(){
        return screenLength;
    }
}
