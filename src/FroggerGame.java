import javax.swing.*;

public class FroggerGame extends JFrame {
    public FroggerGame(){
        super("Frogger");
        setSize(500,500);
        setVisible(true);
    }
    public static void main(String[] args){
        System.out.println("Epic");
        FroggerGame game = new FroggerGame();
    }
}