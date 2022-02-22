package application.controller;

import java.util.List;

import application.db.DatabaseHandler;
import application.model.Game;
import application.model.Score;

public class MenuController {

	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	private Game game;
	
	public MenuController(Game game) {
		this.game = game;
	}
	
	public List<Score> getScores() {
		return dbHandler.getScores();
	}

	public void restartGameModel() {
		game.restartGame();
	}
}
