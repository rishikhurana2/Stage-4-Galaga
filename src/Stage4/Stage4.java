package Stage4;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Stage4 {
    JFrame gameFrame = new JFrame();
    GamePanel gp = new GamePanel();
    static int width = 400;
    static int height = 600;
    public static void main(String[] args) {
       Stage4 s4 = new Stage4();
       s4.setup();
    }
    public void setup() {
        gameFrame.add(gp);
        gameFrame.setPreferredSize(new Dimension(width, height));
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setSize(width, height);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gp.startGame();
        gameFrame.addKeyListener(gp);
    }
}