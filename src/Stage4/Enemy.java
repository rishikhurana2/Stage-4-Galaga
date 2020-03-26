package Stage4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Enemy {
    private int startX;
    private int startY;
    private int x;
    private int y;
    static int sideLength;
    private double a;
    private double divider;
    private double startTime;
    private Rectangle hitbox;
    public Enemy(int x, int y) {
        startX = x;
        startY = y;
        sideLength = 15;
        a = Math.random()*5 + 1;
        divider = Math.random()*3 + 1.2;
        hitbox = new Rectangle(startX, startY, sideLength, sideLength);
        startTime = System.currentTimeMillis();
    }
    public void drawEnemies(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, sideLength, sideLength);
        hitbox.setBounds(x, y, sideLength, sideLength);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.MAGENTA);
        g2d.draw(hitbox);
    }
    public void update() {
       // divider = 1;
        double currentTime = System.currentTimeMillis() - startTime;
        double t = Math.toRadians(currentTime)/ divider;
        double r = 20;
        double theta = -5.7;
       //2D-Helical path
        x = startX + (int)(r * Math.sin(t));
        y = startY - (int)(r * Math.cos(t) * Math.cos(theta) - a * t * Math.sin(theta));
         if (t >= 270) {
             startTime = System.currentTimeMillis();
         }
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
}