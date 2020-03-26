package Stage4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener, KeyListener {
    Timer t;
    int fps = 1000/60;
    Ship s;
    int enemiesAmount = 10;
    ArrayList<Enemy> ems;
    double initialTime;
    Projectile p;
    int velY = 5;
    int velX = 5;
    boolean gameOver = false;
    public GamePanel() {
        enemiesAmount *= 2;
        t = new Timer(fps, this);
        s = new Ship(Stage4.width/2 - Ship.sideLength/2, Stage4.height/2 + Ship.sideLength/2);
        ems = new ArrayList<Enemy>();
        p = new Projectile(s.getX(), s.getY());
        for (int i = 0; i < enemiesAmount; i++) {
            ems.add(new Enemy((int)(Math.random()*Stage4.width - 9) + 10, (int)(Math.random()*11) + 5));
        }
    }

    public void startGame() {
        initialTime = System.currentTimeMillis();
        t.start();
    }
    public void drawGameStage(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0, Stage4.width, Stage4.height);
        s.drawShip(g);
        for (Enemy e: ems) {
         //   System.out.println(ems.size());
            e.drawEnemies(g);
        }
        p.draw(g);
    }
    public void updateGameStage() {
        if (!gameOver) {
        System.out.println(ems.size());
        s.updateShip();
        for (Enemy e: ems) {
            e.update();
        }    
        p.update(s.getX(), s.getY());
        for (int i = 0; i < ems.size(); i++) {
            if (p.getHitbox().intersects(ems.get(i).getHitbox())) {
                ems.remove(ems.get(i));
            }
            if (ems.get(i).getHitbox().intersects(s.getHitbox())) {
                //gameOver = true;
                System.out.println("hit");
            }
         } 
        }
    }   
    @Override
	public void paintComponent(Graphics i) {
		drawGameStage(i);
	}
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            s.setVY(-velY);
            s.setVX(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            s.setVY(velY);
            s.setVX(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            s.setVX(-velX);
            s.setVY(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            s.setVX(velX);
            s.setVY(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            p.shoot(s.getDx(), s.getDy());
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        s.setVX(0);
        s.setVY(0);
        p.shoot(0, 0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        repaint();
        updateGameStage();
    }
}
