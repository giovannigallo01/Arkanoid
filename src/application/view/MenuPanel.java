package application.view;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import application.Settings;
import application.controller.MenuController;
import application.model.Score;
import application.sound.SoundHandler;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 7662211978147780673L;
	
	private Image image;
	private SoundHandler soundHandler = SoundHandler.getInstance();
	private MenuController menuController;
	private JButton play;
	private JButton scores;
	private JButton instructions;
	private JButton exit;

	public MenuPanel(MenuController menuController) {
		this.menuController = menuController;
		setSize(200, 200);
		setVisible(true);
		soundHandler.loop("menu");
		
		play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame gameView = (MainFrame) SwingUtilities.getWindowAncestor(MenuPanel.this);
				menuController.restartGameModel();
				gameView.showGame();
			}
		});
		
		scores = new JButton("Scores");
		scores.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Score> list = menuController.getScores();	
				MainFrame gameView = (MainFrame) SwingUtilities.getWindowAncestor(MenuPanel.this);
				ScoreDialog scoreDialog = new ScoreDialog(gameView, list);
				scoreDialog.setVisible(true);
			}
		});
		
		instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, instructionsText);
			}
		});
		
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100));

		 try {
				image = ImageIO.read(new File("src/application/resources/images/background.png"));
				image.getScaledInstance(Settings.WIDTH, Settings.HEIGTH, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
		 layoutComponents();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	private void layoutComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		play.setPreferredSize(new Dimension(120,20));
		scores.setPreferredSize(new Dimension(120,20));
		instructions.setPreferredSize(new Dimension(120,20));
		exit.setPreferredSize(new Dimension(120,20));
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		
		this.add(play, constraints);
		constraints.gridy++;
		constraints.weighty = 1;
		this.add(scores, constraints);
		constraints.gridy++;
		constraints.weighty = 1;
		this.add(instructions, constraints);
		constraints.gridy++;
		constraints.weighty = 1;
		this.add(exit, constraints);
		}
	
	private String instructionsText = "Il gioco ha due livelli. Il giocatore deve distruggere tutti i mattoncini senza far cadere la palla," + System.lineSeparator()
			+ "ha a disposizione 3 vite." + System.lineSeparator() + "Finite le 3 vite il giocatore perde e ha la possibilità di salvare il suo punteggio e iniziare una"
			+ System.lineSeparator() + "nuova partita." + System.lineSeparator() + "Per salvare il punteggio lo Username deve avere lunghezza 7-15, può contenere caratteri,"
			+ System.lineSeparator() + "numeri o underscore e deve iniziare con un carattere!" + System.lineSeparator() + System.lineSeparator()
			+ "Il giocatore per iniziare il gioco e lanciare la pallina deve premere il tasto SPAZIO, per muovere"
			+ System.lineSeparator() + "il Paddle a destra e a sinistra può usare le frecce direzionali oppure i tasti A e D.";
}
