import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Lane {
    private int speed;
    private int yPos;
    private int direction;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private Zone[] zones;
    private Image sprite;
    public Lane(int yPos,int speed,int direction, int type, String image){
        this.yPos=yPos;
        this.speed=speed;
        this.direction=direction;
        try{
            sprite = ImageIO.read(new File("Images/Objects/" + image));
            int largeWidth = (int)(sprite.getWidth(null)*3.125);
            int largeHeight = (int)(sprite.getHeight(null)*3.125);
            sprite = sprite.getScaledInstance(largeWidth,largeHeight,Image.SCALE_SMOOTH);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public int getYPos(){
        return yPos;
    }
    public Image getSprite(){
        return sprite;
    }
}
