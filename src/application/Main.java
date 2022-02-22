package application;

import application.view.MainFrame;
import application.model.Game;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            MainFrame mainFrame = new MainFrame(game);
        });
    }
}
