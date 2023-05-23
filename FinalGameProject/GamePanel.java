import java.lang.Math.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable{
    
    public void image() {
        try {
    
            player = ImageIO.read(new File("boy_up_1.png"));
            player2 = ImageIO.read(new File("boy_up_2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    BufferedImage player, player2;
    public int sprite;
    public int sprite2 = 0;
    public int gameTime;
    public int gameState = 1;
    public int dialogueState = 1;
    public int gameOverState = 2;
    public int winState = 3;
    public boolean win = false;
    public int enemysKilled = 0;
    public int score = 0;
    
    //screen settings
    final int originalTileSize = 16; // 16x16 size
    final int scale = 3; //scaling to resolution
    
    final int tileSize = originalTileSize * scale; //tileoriginal * scale; to scale the tiles
    //4 : 3 ratio
    final int maxScreenCol = 16; //16 vertical tiles
    final int maxScreenRow = 12; // 12 horizontal tiles
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    
    //fps
    int FPS = 60;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //automatically calls run
    Graphics2D g3;
    //set player default position
    
    int playerX = (screenWidth / 2) - (tileSize / 2);
    int playerY = screenHeight - tileSize * 2;
    int playerSpeed = 4;
    
    Enemy[] npc = new Enemy[150];
    Bullet[] pew = new Bullet[4];
    

    int bulletY = playerY;
    int bulletSize = tileSize / 4;
    int bulletX = playerX + bulletSize;
    int bulletSpeed = playerSpeed * 2;
    int lives = 10;
    
    boolean hit = false;
    
    public int random() {
        return ((int) (Math.random() * (screenWidth - tileSize)) + 1);
    }
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size of JPanel
        this.setBackground(new Color(36, 45, 62));
        this.setDoubleBuffered(true); //if true, all drawing will be done in offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true); //gamepanel is "focused" to receive key input
        
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        
    }
    
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while (gameThread != null) {
            
            // update information such as character positions
            update();
            
            
            // draw the screen with updated info
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                if (gameState != dialogueState) {
                    gameTime++;
                }
                nextDrawTime += drawInterval;
            }
            catch (InterruptedException e) {
                e.printStackTrace(); 
            }
        }
        if (!win) {
            new EndingScreen();
        }
        else {
            new WinScreen();
        }
    }
    
    public void update() {
        if (gameState == dialogueState) {
            if (keyH.spacePressed == true) {
                gameState = 0;
                spawn();
                for(int i = 0; i < pew.length; i ++) {
                    pew[i] = new Bullet(playerX + bulletSize, playerY, false);
                }
            }
        }
        else{
            if (keyH.upPressed == true) {
                playerY -= playerSpeed;
                if (playerY < 0) {
                    playerY = 0;
                }
                
                if (sprite == 0) {
                    sprite = 1;
                } else {
                    sprite = 0;
                }
            }
            if (keyH.downPressed == true) {
                playerY += playerSpeed;
                if (playerY > screenHeight - tileSize) {
                    playerY = screenHeight - tileSize;
                }
                if (sprite == 0) {
                    sprite = 1;
                } else {
                    sprite = 0;
                }
            }
            if (keyH.leftPressed == true) {
                playerX -= playerSpeed;
                if (playerX < 0) {
                    playerX = 0;
                }
                if (sprite == 0) {
                    sprite = 1;
                } else {
                    sprite = 0;
                }
            }
            if (keyH.rightPressed == true) {
                playerX += playerSpeed;
                if (playerX > screenWidth - tileSize) {
                    playerX = screenWidth - tileSize;
                }
                if (sprite == 0) {
                    sprite = 1;
                } else {
                    sprite = 0;
                }
            }
            if (keyH.spacePressed == true) {
                
                if (!pew[0].shoot) {
                    pew[0].shoot = true;
                }
                else if (!pew[1].shoot) {
                    pew[1].shoot = true;
                }
                else if (!pew[2].shoot) {
                    pew[2].shoot = true;
                }
                else if (!pew[3].shoot) {
                    pew[3].shoot = true;
                }
                keyH.spacePressed = false;
            }
            if (gameTime / 60 == 90) {
                gameState = 3;
            }
            checkHit();
        }
    }
    
    public void spawn() {
        for (int i = 0; i < npc.length; i ++) {
            npc[i] = new Enemy(random(), 0, tileSize, ((int) (Math.random() * 75 + 1)), false);
        }
    }
    
    public void checkHit() {
        for (int i = 0; i < npc.length; i ++) {
            if (npc[i].alive) {
                if (npc[i].bulletX > playerX && npc[i].bulletX < playerX + tileSize && npc[i].bulletY > playerY && npc[i].bulletY < playerY + tileSize) {
                    dead();
                    npc[i].bulletY = npc[i].y;
                }
            }
        }
    }
    
    public void checkEnemyHit(Enemy e) {
        for (int i = 0; i < pew.length; i ++) {
            if ((pew[i].x > e.x && pew[i].x < e.x + tileSize && pew[i].y > e.y && pew[i].y < e.y + tileSize) || (pew[i].x + bulletSize > e.x && pew[i].x + bulletSize < e.x + tileSize && pew[i].y + bulletSize > e.y && pew[i].y + bulletSize < e.y + tileSize)) {
                e.alive = false;
                enemysKilled ++;
                score ++;
                if (e.green) {
                    enemysKilled += 4;
                    score++;
                }
    
            }
        }
    }
    public void dead() {
        if (lives > 1) {
            lives --;
        }
        else {
            gameState = 2;
        }
    }
    
    public void getEnemy() {
        for (int i = 0; i < npc.length; i ++) {
            if (npc[i].time == gameTime / 60) {
                npc[i].alive = true;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (gameState == gameOverState) {
            gameThread = null;
        }
        else if (gameState == dialogueState) {
            drawDialogueScreen(g2);
        }
        else if (gameState == winState) {
            win = true;
            gameThread = null;
        }
        else {
            
            getEnemy();
            for (int i = 0; i < npc.length; i ++) {
                if (npc[i].alive) {
                    g2.setColor(Color.red);
                    if (npc[i].shoot) {
                        npc[i].bulletY += npc[i].EnbulletSpeed;
                        g2.fillRect(npc[i].bulletX, npc[i].bulletY, bulletSize, bulletSize + bulletSize / 2);
                    }
                    else {
                        npc[i].updateBullet();
                    }   
                    if (npc[i].bulletY > screenHeight) {
                        npc[i].shoot = false;
                    }
                    
                    
                    BufferedImage image2 = npc[i].player;
                    if (sprite2 == 0) {
                        sprite2 = 1;
                    } else {
                        sprite2 = 0;
                    }
                    if (sprite2 == 0) {
                        image2 = npc[i].player;
                    } else {
                        image2 = npc[i].player2;
                    }                  
                    g2.drawImage(image2, npc[i].x, npc[i].y, tileSize, tileSize, null);
                    if ((gameTime / 60 - npc[i].time) % 4 == 0) {
                        npc[i].y += npc[i].speed;
                        if (npc[i].y > screenHeight) {
                            dead();
                            npc[i].alive = false;
                        }
                        
                        if ((int) (Math.random() * 3) == 0) {
                            npc[i].shoot = true;
                        }
                    }
                    checkEnemyHit(npc[i]);
                }
            }
            
            g2.setColor(Color.blue);
            for (int i = 0; i < pew.length; i ++) {
                if (pew[i].shoot) {
                    if (pew[i].y < 0) {
                        pew[i].shoot = false;
                    }
                    pew[i].y -= bulletSpeed;
                    g2.fillRect(pew[i].x, pew[i].y, bulletSize, bulletSize + bulletSize / 2);
                }
                if (!pew[i].shoot) {
                    pew[i].update(playerX + bulletSize, playerY);
                }
            }
            
            //if (score >= 25) {
            //    lives++;
            //    score = 0;
            //}
            BufferedImage image;
            
            if (sprite == 0) {
                image = player;
            } else {
                image = player2;
            }
            image();
            g2.drawImage(image, playerX, playerY, tileSize, tileSize, null);
            
            g2.setColor(Color.white);
            g2.drawString("Time: " + Integer.toString(gameTime / 60), screenWidth / 2 - tileSize, tileSize / 2);
            g2.drawString("Score: " + Integer.toString(enemysKilled), tileSize, tileSize / 2);
            g2.drawString("Lives: " + Integer.toString(lives), screenWidth - tileSize * 2, tileSize / 2);
            
            g2.dispose(); //dispose of graphics context and release resources being used
        }
        
    }
    
    
    public void drawDialogueScreen(Graphics2D g2) {
        int x = tileSize * 2;
        int y = tileSize / 2;
        int width = screenWidth - (tileSize * 4);
        int height = tileSize * 4;
        drawSubWindow(x, y, width, height, g2);
        
        x += tileSize * 2 - tileSize;
        y+= tileSize / 2;
        g2.drawString("Welcome soldier. You are here today because the Confederacy has seceded", x, y);
        x -= tileSize / 2;
        y += tileSize / 2;
        g2.drawString("over the issue of slavery. I am Ulysses S. Grant and I will be teaching you basic combat.", x, y);
        
        x += tileSize * 2 + tileSize / 4;
        y += tileSize / 2;
        g2.drawString("If you see an enemy, shoot him by pressing space.", x, y);
        
        x += tileSize / 4 - 2;
        y += tileSize / 2;
        g2.drawString("Use WASD to move around and dodge his bullets.", x, y);
        
        x -= tileSize;
        y += tileSize / 2;
        g2.drawString("If you get hit once, you die. If you hit the enemy once, he dies.", x, y);
        
        x -= tileSize * 2 - tileSize;
        y += tileSize / 2;
        g2.drawString("You only have one life out there, so be careful. When you are ready, press space.", x, y);
        
        x += tileSize * 2;
        y += tileSize / 2;
        g2.drawString("Good luck soldier. I will see you in three minutes.", x, y);
        
    }
    
    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    
    public void drawGameOverScreen(Graphics2D g2) {
        int x = 0;
        int y = 0;
        int width = screenWidth;
        int height = screenHeight;
        drawOverWindow(x, y, width, height, g2);
        g2.drawString("Game Over", x, y);
    }
    public void drawOverWindow(int x, int y, int width, int height, Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(x, y, width, height);
    }
    
}