import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Zone {
    public static final int DEATH = 0;
    public static final int WALK = 1;
    public static final int WIN = 2;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    private int zoneType;
    private Image picture;
    private int[] zoneRect = new int[4];
    private int screenLength;
    public Zone(int zoneType, int screenLength, int x, int y, int width, int height){
        this.zoneType=zoneType;
        zoneRect[X] = x;
        zoneRect[Y] = y;
        zoneRect[WIDTH] = width;
        zoneRect[HEIGHT] = height;
        this.screenLength = screenLength;
    }
    public void moveX(int deltaX){
        // If statements to check to see if the zone needs to be moved to the other side of the screen
        zoneRect[X] += deltaX;
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
    public int getX(){
        return zoneRect[X];
    }
    public int getY(){
        return zoneRect[Y];
    }
    public boolean isSafe(){
        if(zoneType==DEATH){
            return false;
        }
        return true;
    }
}
