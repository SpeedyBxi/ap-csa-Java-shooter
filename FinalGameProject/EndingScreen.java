import javax.swing.*;
import java.awt.*;

public class EndingScreen extends JFrame {
    GamePanel gp = new GamePanel();
    public EndingScreen() {
        
        super("Game Over");

        JLabel label = new JLabel("Game Over!", SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(label);
        add(Box.createVerticalGlue());

        // Customize the appearance of the JFrame and its components
        pack();
        setSize(gp.screenWidth, gp.screenHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
    }
       
}