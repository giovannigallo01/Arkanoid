package application.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import application.model.Score;

public class ScoreDialog extends JDialog{

	private static final long serialVersionUID = -3534067840915577099L;
	
	private JButton okButton;
	private JScrollPane scrollPane;
	private JLabel topLabel;
	private JPanel topPanel;
	private JPanel bottomPanel;

	public ScoreDialog(JFrame parent, List<Score> list) {
		super(parent, "Scores", false);
		
		setSize(300,400);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		Font fontTopLabel = new Font("Verdana", Font.BOLD, 14);
		Font fontLabel = new Font("Verdana", Font.PLAIN, 17);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		topLabel = new JLabel("USERNAME: SCORE");
		topLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));
		topLabel.setFont(fontTopLabel);
		
		for(Score score : list) {
			JLabel label = new JLabel(score.toString());
			label.setFont(fontLabel);
			centerPanel.add(label);
		}
		
		scrollPane = new JScrollPane(centerPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		layoutComponents();	
		setLocationRelativeTo(parent);
	}
	
	private void layoutComponents() {
		setLayout(new BorderLayout());

		bottomPanel.add(okButton);
		topPanel.add(topLabel);
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}
