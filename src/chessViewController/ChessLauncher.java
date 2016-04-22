package chessViewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import chessModel.Board;

public class ChessLauncher extends JFrame {
	private JLabel title;
	private JButton go;
	private JPanel p;
	private HashMap<String,Integer> typesMap;
	private GridBagConstraints gbc;
	private ButtonGroup groupTypes;
	int selectedGame;

	public static void main(String args[]) {
		new ChessLauncher();
	}

	public ChessLauncher() {
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		groupTypes = new ButtonGroup();
		typesMap = new HashMap<String, Integer>();

		setVisible(true);

		// Set to the native look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Could not find native look and feel.");
		}

		this.setLayout(new BorderLayout());

		title = new JLabel("Chess");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		this.add(title, BorderLayout.NORTH);

		p = new JPanel();
		p.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		this.add(p,BorderLayout.CENTER);
		
		go = new JButton("Start Game");
		this.add(go,BorderLayout.SOUTH);
		
		addButton("Standard",Board.STANDARD);
		addButton("Speedchess",Board.SPEEDCHESS);
		
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChessFrame();
				dispose();
			}
		});
		
		pack();
		setSize(200,200);
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) ((screenDimensions.getWidth() - getWidth()) / 2),
				(int) ((screenDimensions.getHeight() - getHeight()) /3));
	}

	public void addButton(String name, int type) {
		JRadioButton newButton = new JRadioButton(name);
		newButton.setActionCommand(name);
		p.add(newButton,gbc);
		groupTypes.add(newButton);
		typesMap.put(name, type);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedGame = typesMap.get(groupTypes.getSelection().getActionCommand());
				go.setEnabled(true);
			}
		});
		
		// initial state
		if (newButton.getActionCommand().equals("Standard")){
			newButton.setSelected(true);
			selectedGame = Board.STANDARD;
		}		
	}
}
