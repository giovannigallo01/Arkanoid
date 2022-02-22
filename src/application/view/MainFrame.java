package application.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import application.Settings;
import application.controller.MenuController;
import application.controller.GameController;
import application.model.Game;
import application.sound.SoundHandler;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -56179713600182631L;
	
	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	private MenuController menuController;
	private Game game;
	private GameController gameController;
	private SoundHandler soundHandler = SoundHandler.getInstance();
	

    public MainFrame(Game game) {
    	this.game = game;
    	gamePanel = new GamePanel(game);
    	gameController = new GameController(game, gamePanel);
        menuController = new MenuController(game);
        menuPanel = new MenuPanel(menuController);
        
        setIconImage(new ImageIcon(("src/application/resources/images/game.png")).getImage());
        setTitle("Arkanoid");
        setContentPane(menuPanel);  
              
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Settings.WIDTH, Settings.HEIGTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public void showMenu() {
    	setContentPane(menuPanel);
    	soundHandler.stop("game");
    	soundHandler.loop("menu");
    }
    
    public void showGame() {
    	gamePanel.initGamePanel();
    	gameController.initController();
    	setContentPane(gamePanel);
    	gamePanel.setFocusable(true);
		revalidate();
		repaint();
		soundHandler.stop("menu");
		soundHandler.loop("game");
    }
}

