import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Lane {
    private int speed;
    private int yPos;
    private int direction;
    private ArrayList<Zone>zones = new ArrayList<Zone>();
    public Lane(int yPos,int speed,int direction,ArrayList<Zone>zones){
        this.zones=zones;
        this.yPos=yPos;
        this.speed=speed;
        this.direction=direction;
    }
}
