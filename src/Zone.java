import java.util.ArrayList;

public class Zone {
    public static final int DEATH = 0;
    public static final int WALK = 1;
    private int zoneType;
    //private int[]zoneRect;
    private int x,y,width,height;
    public Zone(int zoneType,int[]rect){
        this.zoneType=zoneType;
        rect[0]=x;
        rect[1]=y;
        rect[2]=width;
        rect[3]=height;

    }
}
