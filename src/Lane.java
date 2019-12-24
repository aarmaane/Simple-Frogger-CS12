import java.util.*;

public class Lane {
    private int speed;
    private int yPos;
    private int direction;
    private Zone[] zones;
    public Lane(int yPos,int speed,int direction){
        this.yPos=yPos;
        this.speed=speed;
        this.direction=direction;
    }
}
