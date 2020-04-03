package Stage4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;


public class Projectile {
    private int x;
    private int y;
    private int vy;
    private int vx;
    private int width;
    private int height;
    private double startTime;
    private Rectangle hitbox;
    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        vx = 0;
        vy = 0;
        width = 10;
        height = 15;
        hitbox = new Rectangle(x, y, width, height);
        hitbox.setBounds(new Rectangle(x, y, width, height));
    }
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
        hitbox.setBounds(x, y, width, height);
     //   Graphics2D g2d = (Graphics2D) g;
    //    g2d.setColor(Color.PINK);
    //    g2d.draw(hitbox);
    }
    public void update(int sx, int sy) {
        x =(int)(sx + Ship.sideLength/2 - width/2 + vx * (System.currentTimeMillis() - startTime));
        y = (int)(sy + Ship.sideLength/2 - height/2 +  vy * (System.currentTimeMillis() - startTime));
    }
    public void shoot(int dx, int dy) {
        startTime = System.currentTimeMillis();
        vx = (int)(dx);
        vy = (int)(dy);
        // if (vx > 1) {
        //     vx /= 3;
        // }
        // if (vy > 1) {
        //     vy /= 3;
        // }
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
}