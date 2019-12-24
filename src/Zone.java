import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Zone {
    public static final int DEATH = 9;
    public static final int WALK = 10;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    private int zoneType;
    private Image picture;
    private int x,y,width,height;
    public Zone(int zoneType,int[]rect){
        this.zoneType=zoneType;
        x=rect[X];
        y=rect[Y];
        width=rect[WIDTH];
        height=rect[HEIGHT];

    }
    public boolean isSafe(){
        if(zoneType==DEATH){
            return false;
        }
        return true;
    }
    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    public void assignPicture() throws IOException {
        Image[] obstacles={ ImageIO.read(new File("Images/obstacles/cars/car1.png")),
                                    ImageIO.read(new File("Images/obstacles/cars/car2.png")),
                                    ImageIO.read(new File("Images/obstacles/cars/car3.png")),
                                    ImageIO.read(new File("Images/obstacles/cars/car4.png")),
                                    ImageIO.read(new File("Images/obstacles/cars/car5.png")) };
        picture=obstacles[randint(0,obstacles.length-1)];


    }
}
