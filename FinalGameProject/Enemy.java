import java.lang.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;

public class Enemy
{
    public int x;
    public int y;
    public int width;
    public int length;
    public int time;
    public boolean alive;
    public boolean shoot;
    public int speed;
    public int bulletX;
    public int bulletY;
    public int bulletSize;
    public int EnbulletSpeed;
    public BufferedImage player, player2;
    public boolean green = false;
    
    
    public Enemy(int x, int y, int size, int time, boolean alive) {
        if ((int) (Math.random() * 5) == 1) {
            green = true;
        }
        this.x = x;
        this.y = y;
        this.width = size;
        this.length = size;
        this.time = time;
        this.alive = alive;
        shoot = false;
        speed = 1;
        bulletSize = width / 4;
        bulletX = x + width / 2 - bulletSize / 2;
        bulletY = y;
        EnbulletSpeed = 6;
        if (this.time > 30) {
            EnbulletSpeed += 1;
        }
        if (this.time > 60) {
            EnbulletSpeed += 1;
        }
        try {
            player = ImageIO.read(new File("boy_down_1.png"));
            player2 = ImageIO.read(new File("boy_down_2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (green) {
            EnbulletSpeed *= 2;
            try {
                player = ImageIO.read(new File("boy_down_1_g.png"));
                player2 = ImageIO.read(new File("boy_down_2_g.png"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateBullet() {
        bulletY = y;
    }
    
}