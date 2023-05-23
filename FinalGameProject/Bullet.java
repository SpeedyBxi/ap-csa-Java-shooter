public class Bullet {
    public int x;
    public int y;
    public boolean shoot;
    
    public Bullet(int x, int y, boolean shoot) {
        this.x = x;
        this.y = y;
        this.shoot = shoot;
    }
    
    public void update(int x, int y, boolean shoot) {
        this.x = x;
        this.y = y;
        this.shoot = shoot;
    }
    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
