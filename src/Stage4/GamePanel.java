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
	int fps = 1000 / 60;
	Ship s;
	static int enemiesAmount = 50;
	ArrayList<Enemy> ems;
	double initialTime;
	Projectile p;
	int velY = 5;
	int velX = 5;
	boolean gameOver = false;
	double startTime = 0;
	Font displayFont;
	static int GAME_STAGE = 0;
	int amountRemaining;
	double winTime = 0;
	static boolean left = false;
	static boolean right = false;
	static boolean down = false;
	static boolean up = false;
	boolean decreaseAmount = false;
	public GamePanel() {
		// enemiesAmount *= 2;
		t = new Timer(fps, this);
		s = new Ship(Stage4.width / 2 - Ship.sideLength / 2, Stage4.height / 2 + Ship.sideLength / 2);
		ems = new ArrayList<Enemy>();
		p = new Projectile(s.getX(), s.getY());
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		boolean left = false;
		displayFont = new Font("Timnes New Roman", 0, 24);
		for (int i = 0; i < enemiesAmount; i++) {
			top = false;
			bottom = false;
			right = false;
			left = false;
			if (i <= enemiesAmount / 4) {
				top = true;
				right = false;
				bottom = false;
				left = false;
			} else if (i > enemiesAmount / 4 && i <= enemiesAmount / 2) {
				top = false;
				right = true;
				bottom = false;
				left = false;
			} else if (i > enemiesAmount / 2 && i <= enemiesAmount * 3 / 4) {
				top = false;
				right = false;
				bottom = true;
				left = false;
			} else if (i > enemiesAmount * 3 / 4 && i <= enemiesAmount) {
				top = false;
				right = false;
				bottom = false;
				left = true;
			}
			if (top)
				// System.out.println("top");
				ems.add(new Enemy((int) (Math.random() * Stage4.width - 9) + 10, (int) (Math.random() * 11) + 10, top,
						right, left, bottom));
			if (bottom)
				// System.out.println("bottom");
				ems.add(new Enemy((int) (Math.random() * Stage4.width - 9) + 10, (int) (Math.random() * 11) + Stage4.height - 70, top,
						right, left, bottom));
			if (left)
				// System.out.println("top");
				ems.add(new Enemy((int) (Math.random() * 11) + 5, (int) (Math.random() * Stage4.height - 9), top, right,
						left, bottom));
			if (right)
				// System.out.println("right");
				ems.add(new Enemy((int) (Math.random() * 11) + Stage4.width - 40, (int) (Math.random() * Stage4.height - 9), top,
						right, left, bottom));
		}
		amountRemaining = ems.size();
		startTime = System.currentTimeMillis();
	}

	public void startGame() {
		initialTime = System.currentTimeMillis();
		t.start();
	}

	public void drawGameStage(Graphics g) {
		
		g.setColor(Color.black);
		
		g.fillRect(0, 0, Stage4.width, Stage4.height);
		// p.draw(g);
		s.drawShip(g);
		p.draw(g);
		g.setFont(displayFont);
		// for (Enemy e : ems) {
		// if (e.getHitbox().intersects(p.getHitbox())) {
		// // System.out.println("hit");
		// }
		// }
		for (Enemy e : ems) {
			// System.out.println(ems.size());
			e.drawEnemies(g);
		}

	}

	public void drawEndStage(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Stage4.width, Stage4.height);
		g.setFont(new Font("Times New Roman", 0, 48));
		g.setColor(Color.red);
		g.drawString("Yikers, you lost, wanna restart?", Stage4.width/2 - 300, Stage4.height/2- 100);
		g.drawString("If so, then click enter", Stage4.width/2 - 200, Stage4.height/2);
	}
	
	public void drawWinStage(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Stage4.width, Stage4.height);
		s.drawShip(g);
		g.setFont(new Font("Times New Roman", 0, 48));
		if (System.currentTimeMillis() - winTime <= 5000) {	
			g.setColor(Color.blue);
			g.drawString("Nice, you beat enough!", Stage4.width/2 - 250 , Stage4.height/2 - 50);
			g.drawString("You can now leave in peace", Stage4.width/2 - 280, Stage4.height/2);
		}
	}
	public void updateGameStage() {
		if (!gameOver) {
			if (decreaseAmount) {
				amountRemaining--;
				decreaseAmount = false;
			}
			s.updateShip();
			for (Enemy e : ems) {
				e.update();
				if (System.currentTimeMillis() - startTime >= 500) {
					if (e.getHitbox().intersects(s.getHitbox())) {
						gameOver = true;
					}
					if (e.getHitbox().intersects(p.getHitbox()) && !e.getHitbox().intersects(s.getHitbox())) {
						e.setLifeStatus(false);
						decreaseAmount = true;
					}
					if (amountRemaining == 0) {
						winTime = System.currentTimeMillis();
						GAME_STAGE = 2;
					}
				}
			}
			p.update(s.getX(), s.getY());
		}
		if (gameOver) {
			s.setX(Stage4.width / 2 - Ship.sideLength / 2);
			s.setY(Stage4.height / 2 + Ship.sideLength / 2);
			p = new Projectile(s.getX(), s.getY());
			ems.clear();
			resetEnemies(ems);
			amountRemaining = ems.size();
			GAME_STAGE = 1;
		}
	}
	public void updateWinStage() {
		s.updateShip();
		if (System.currentTimeMillis() - winTime >= 5000) {	
			s.setVX(0);
			s.setVY(-2);
		}
	}
	public static void resetEnemies(ArrayList<Enemy> ems) {
		boolean top = false;
		boolean bottom = false;
		boolean left = false;
		boolean right = false;
		for (int i = 0; i < enemiesAmount; i++) {
			top = false;
			bottom = false;
			right = false;
			left = false;
			if (i <= enemiesAmount / 4) {
				top = true;
				right = false;
				bottom = false;
				left = false;
			} else if (i > enemiesAmount / 4 && i <= enemiesAmount / 2) {
				top = false;
				right = true;
				bottom = false;
				left = false;
			} else if (i > enemiesAmount / 2 && i <= enemiesAmount * 3 / 4) {
				top = false;
				right = false;
				bottom = true;
				left = false;
			} else if (i > enemiesAmount * 3 / 4 && i <= enemiesAmount) {
				top = false;
				right = false;
				bottom = false;
				left = true;
			}
			if (top)
				// System.out.println("top");
				ems.add(new Enemy((int) (Math.random() * Stage4.width - 9) + 10, (int) (Math.random() * 11) + 10, top,
						right, left, bottom));
			if (bottom)
				// System.out.println("bottom");
				ems.add(new Enemy((int) (Math.random() * Stage4.width - 9) + 10, (int) (Math.random() * 11) + 1010, top,
						right, left, bottom));
			if (left)
				// System.out.println("top");
				ems.add(new Enemy((int) (Math.random() * 11) + 5, (int) (Math.random() * Stage4.height - 9), top, right,
						left, bottom));
			if (right)
				// System.out.println("right");
				ems.add(new Enemy((int) (Math.random() * 11) + 1880, (int) (Math.random() * Stage4.height - 9), top,
						right, left, bottom));
		}

	}
	// @Override
	public void paintComponent(Graphics i) {
		if (GAME_STAGE == 0)
			drawGameStage(i);
		if (GAME_STAGE == 1)
			drawEndStage(i);
		if (GAME_STAGE == 2)
			drawWinStage(i);
	}

	// @Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (GAME_STAGE == 0) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				up = true;
				s.setVY(-velY);
				s.setVX(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				down = true;
				s.setVY(velY);
				s.setVX(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				left = true;
				s.setVX(-velX);
				s.setVY(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				right = true;
				s.setVX(velX);
				s.setVY(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				p.shoot(s.getDx(), s.getDy());
			}
		}
		if (GAME_STAGE == 1) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				GAME_STAGE = 0;
				gameOver = false;
			}
		}
	}
	// @Override

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (GAME_STAGE == 0) {
			s.setVX(0);
			s.setVY(0);
			p.shoot(0, 0);
		}
//		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//			GAME_STAGE = 0;
//		}
		
	}
	// @Override

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	// @Override

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		System.out.println(GAME_STAGE);
		if (GAME_STAGE == 0)
			updateGameStage();
		if (GAME_STAGE == 2)
			
			updateWinStage();
	}
}
