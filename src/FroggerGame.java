import javax.swing.*;

public class FroggerGame extends JFrame {
    public FroggerGame(){
        super("Frogger");
        // Creating the JPanel
        JPanel pane = new JPanel();
        // Creating the JFrame
        setSize(700,700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        System.out.println("Epic");
        FroggerGame game = new FroggerGame();
    }
}