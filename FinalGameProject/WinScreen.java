import javax.swing.*;
import java.awt.*;

public class WinScreen extends JFrame {
    GamePanel gp = new GamePanel();
    public WinScreen() {
        
        super("You Win");

        JLabel label = new JLabel("You Win!", SwingConstants.CENTER);
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