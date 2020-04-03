package Stage4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ship {
    //member variables
    private int startX;
    private int startY;
    private int prevX;
    private int prevY;
    private int x;
    private int y;
    private int vx;
    private int vy;
    static int sideLength;
    private double startTime;
    private int dx;
    private int dy;
    private Rectangle hitbox;
    public Ship(int x, int y) {
        //initialization
        this.startX = x;
        this.startY = y;
        this.prevX = x;
        this.prevY = y;
        vx = 0;
        vy = 0;
        sideLength = 25;
        startTime = System.currentTimeMillis();
        hitbox = new Rectangle(x, y, sideLength, sideLength);
        hitbox.setBounds(new Rectangle(x, y, sideLength, sideLength));
        this.x = startX;
        this.y = startY;
    }
    public void drawShip(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, sideLength, sideLength);
        hitbox.setBounds(x, y, sideLength, sideLength);
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void updateShip() {
        //variables/constants
       double currentTime = System.currentTimeMillis() - startTime;
       double t = Math.toRadians(currentTime)/5;
       double r = 25;

       //update location of circular path based on user input
       if (x < Stage4.width-sideLength && x > 0 && y < Stage4.height - sideLength && y > 0 && GamePanel.GAME_STAGE != 2) {	
    	   startX += vx;
    	   startY += vy;
       }  if (x > Stage4.width-sideLength && GamePanel.GAME_STAGE != 2) {
    	  startX -= 5;
       }  if (x < 0 && GamePanel.GAME_STAGE != 2) {
    	   startX += 5;
       }  if (y < 0 && GamePanel.GAME_STAGE != 2) {
    	   startY += 5;
       }  if (y > Stage4.height - sideLength*3 && GamePanel.GAME_STAGE != 2) {
    	   startY -= 5;
       }
       if (GamePanel.GAME_STAGE == 2) {
    	   startX += vx;
    	   startY += vy;
       }
       //circular path (depends on the time the program has been running)
       	x = startX - (int) r/2 + (int)(r * Math.cos(t));
       	y = startY - (int)(r * Math.sin(t));
       	
       //calculate velocities through differential position values (dx = dx/dt, dy = dy/dt), v = <dx, dy>
       dx = x - prevX;
       prevX = x;
       dy = y - prevY;
       prevY = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getDx() {
        return dx;
    }
    public int getDy() {
        return dy;
    }
    public void setVX(int vx) {
        this.vx = vx;
    }
    public void setVY(int vy) {
        this.vy = vy;
    }
    public int getVY() {
        return vy;
    }
    public int getVX() {
        return vx;
    }
    public void setX(int x) {
        this.startX = x;
    }
    public void setY(int y) {
        this.startY = y;
    }
}