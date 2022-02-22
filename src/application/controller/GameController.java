package application.controller;

import application.InputValidation;
import application.Settings;
import application.db.DatabaseHandler;
import application.model.Game;
import application.sound.SoundHandler;
import application.view.GamePanel;
import application.view.MainFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameController extends KeyAdapter implements GameEndListener, GameWinListener{

    private Timer timer = null;
    private Game game;
    private GamePanel gamePanel;
    private SoundHandler soundHandler = SoundHandler.getInstance();
    private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
    private ScheduleTask task;

    public GameController(Game game, GamePanel gamePanel) {
        this.game = game;
        this.gamePanel = gamePanel;
        gamePanel.addKeyListener(this);
        game.addGameEndListener(this);
        game.addGameWinListener(this);
        gamePanel.setFocusable(true);
        
    }

    public void initController() {
    	gamePanel.initGamePanel();
        timer = new Timer();
        task = new ScheduleTask();
        timer.scheduleAtFixedRate(task, Settings.DELAY, Settings.PERIOD);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
        		|| e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            game.stopPaddle();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	if(game.isStarted()) {
    		 if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
    	            game.setPaddleRight();
    	        }
    	        else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
    	        	game.setPaddleLeft();
    	        }
    	}
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
        	game.setStarted(true);
        	game.startBall();
        }
    }
    
    @Override
    public void gameEnd() {
    	task.cancel();
    	timer.cancel();
    	timer.purge();
    	soundHandler.start("fail");
    	showMessage("GAME OVER");
    	insertScore();
    }
		
    @Override
	public void gameWin() {
    	task.cancel();
    	timer.cancel();
		if(game.hasNext()) {
			game.nextLevel();
			showMessage("LEVEL " + game.getCurrentLevel().getLevelNumber());
			game.gameInit(game.getCurrentLevel());
			
			initController();
			soundHandler.loop("game");
    	}
		else {
			soundHandler.start("win");
			showMessage("GAME WIN");
			insertScore();
		}
	}
    
    private void showMessage(String message) { 
    	gamePanel.setMessage(message);
    	gamePanel.repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    private void insertScore() {
    	int newScore = game.getCurrentScore();
        String username = JOptionPane.showInputDialog("(Score: " + newScore + "pt)" + System.lineSeparator() + "Scrivi il tuo Username:");
        if(username != null) {
        	if(InputValidation.usernameValid(username)) {
        		if(!dbHandler.userExist(username))
        			dbHandler.loadScore(username, game.getCurrentScore());
        		else {
        			int score = dbHandler.getUsernameScore(username);
            		int choice = JOptionPane.showConfirmDialog(null, "L'utente inserito è già presente e ha un punteggio " + System.lineSeparator() + "pari a " + score + " pt!" + System.lineSeparator() + System.lineSeparator() + "Vuoi aggiornare il vecchio punteggio?", "Information", JOptionPane.YES_NO_OPTION);
            		if(choice == JOptionPane.YES_OPTION)
            			dbHandler.updateScore(username, newScore);
        		}
        	}
        }
        MainFrame gameView = (MainFrame) SwingUtilities.getWindowAncestor(gamePanel);
		gameView.showMenu();
    }
    
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
        	game.moveBall();
        	game.movePaddle();
        	game.checkCollision();
        	gamePanel.repaint();
            
        }
    }
}
