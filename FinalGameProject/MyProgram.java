import javax.swing.*;
import java.awt.*;

public class MyProgram {
    private int topScore;
    public static void main(String[] args) {
        //loaddata()
        //create a new window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing stuff
        window.setResizable(false); //no resize
        window.setTitle("2D Adventure"); //title
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack(); // sized to fit preferred size and stuff
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.startGameThread();
        
        //updatedata()
    }
}