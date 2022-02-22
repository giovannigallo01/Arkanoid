package application.view;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import application.Settings;
import application.model.Ball;
import application.model.Brick;
import application.model.Game;
import application.model.Paddle;


public class GamePanel extends JPanel {

	private static final long serialVersionUID = 3611319638777943673L;
	
	private String message;
    private Game game;
    private Paddle paddle;
    private Ball ball;
    private Brick bricks[];
    private ImageIcon background;
    private Image lifeImage;

    public GamePanel(Game game) {
        this.game = game;
        initGamePanel();
    }

    public void initGamePanel() {
        paddle = game.getPaddle();
        ball = game.getBall();
        bricks = game.getBricks();
        background = new ImageIcon(("src/application/resources/images/background.png"));
        lifeImage = new ImageIcon(("src/application/resources/images/life.png")).getImage();
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public void addNotify() {
    	super.addNotify();
    	setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = background.getImage();
        g.drawImage(image, 0, 0, null);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if(game.isInGame())
            drawObjects(g2d);
        else
            gameFinished(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getImageWidth(), paddle.getImageHeight(), this);
        for(int i=0; i<game.getNumOfBricks(); i++)
            if(!bricks[i].isDestroyed())
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getImageWidth(), bricks[i].getImageHeight(), this);
        for(int i=0, x=Settings.INIT_LIFE_X; i<game.getLife(); i++, x-=lifeImage.getWidth(null)+Settings.DISTANCE_LIFE) {
        	g2d.drawImage(lifeImage, x, Settings.INIT_LIFE_Y, lifeImage.getWidth(null), lifeImage.getHeight(null), this);
        	g2d.setFont(new Font("Verdana", Font.BOLD, 14));
        	g2d.setColor(Color.RED);
        	g2d.drawString("Score: " + game.getCurrentScore(), Settings.INIT_SCORE_X, Settings.INIT_SCORE_Y);
        }
    }

    private void gameFinished(Graphics2D g2d) {
        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);
        g2d.setColor(Color.YELLOW);
        g2d.setFont(font);
        g2d.drawString(message, (Settings.WIDTH - metr.stringWidth(message)) / 2, Settings.WIDTH / 2);
    }
}
