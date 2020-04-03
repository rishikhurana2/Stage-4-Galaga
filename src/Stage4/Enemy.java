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
    private boolean isAlive;
    private boolean top;
    private boolean right;
    private boolean bottom;
    private boolean left;
    public Enemy(int x, int y, boolean top, boolean right, boolean left, boolean bottom) {
        startX = x;
        startY = y;
        sideLength = 30;
        a = Math.random()*5 + 1;
        divider = Math.random()*3 + 1.2;
        this.top = top;
        this.right = right;
        this.left = left;
        this.bottom = bottom;
        hitbox = new Rectangle(startX, startY, sideLength, sideLength);
        startTime = System.currentTimeMillis();
        isAlive = true;
        hitbox.setBounds(new Rectangle(x, y, sideLength, sideLength));
    }
    public void drawEnemies(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, sideLength, sideLength);
        hitbox.setBounds(x, y, sideLength, sideLength);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Color.MAGENTA);
//        g2d.draw(hitbox);
    }
    public void update() {
       // divider = 1;
        double currentTime = System.currentTimeMillis() - startTime;
        double t = Math.toRadians(currentTime)/ divider;
        double r = 20;
        double theta = -5.7;
        if (isAlive) { 
            //2D-Helical path
        	if (top) {
            	x = startX + (int)(r * Math.sin(t));
            	y = startY - (int)(r * Math.cos(t) * Math.cos(theta) - a * t * Math.sin(theta));
        	}
        	if (bottom) {
            	x = startX + (int)(r * Math.sin(t));
            	y = startY +((int)(r * Math.cos(t) * Math.cos(theta) - a * t * Math.sin(theta)));
        	}
        	if (right) {
            	x = (startX + (int)(r * Math.cos(t) * Math.cos(theta) - a * t * Math.sin(theta)));
        		y = (startY - (int)(r * Math.sin(t)));
        	}
        	if (left) {
            	x = (startX - (int)(r * Math.cos(t) * Math.cos(theta) - a * t * Math.sin(theta)));
        		y = (startY + (int)(r * Math.sin(t)));
        	}
            if (t >= 700) {
                startTime = System.currentTimeMillis();
            }
        } else {
            x = 1000000;
            y = 1000000;
        }
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getStartX() {
    	return startX;
    }
    public int getStartY() {
    	return startY;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setLifeStatus(boolean isAlive) {
        this.isAlive = isAlive;
    }
}