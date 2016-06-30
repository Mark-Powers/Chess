package chessViewController;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import chessModel.Game;
import chessModel.HumanPlayer;
import chessModel.Player;
import util.Instantiator;

public class Launcher {
	private File[] files;
	private JComboBox<String> playerPicker1, playerPicker2, selectMode;

	public static void main(String args[]) {
		new Launcher();
	}

	public Launcher() {
		// Set to the native look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Could not find native look and feel.");
		}

		// Create an ArrayList of game modes
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Human vs. AI");
		modes.add("Human vs. Human");
		modes.add("AI vs. AI");

		// Create a combo box with list of game modes
		selectMode = new JComboBox(modes.toArray());

		// Get list of AIs from the package artificalIntelligence
		try {
			files = Instantiator.getPackageContent("artificialIntelligence");
		} catch (IOException e1) {
			System.err.println("Could not get package artificialIntelligence");
		}

		// Make an arraylist of the AI names
		ArrayList<String> aiNames = new ArrayList<String>();
		for (File f : files) {
			aiNames.add(f.getName().substring(0, f.getName().indexOf(".")));
		}

		// Add the names to combo boxes
		playerPicker1 = new JComboBox(aiNames.toArray());
		playerPicker2 = new JComboBox(aiNames.toArray());

		// Create submit button
		JButton submit = new JButton("Start");
		
		// Title
		JLabel title = new JLabel("Chess");
		title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		title.setHorizontalAlignment(SwingConstants.CENTER);

		// Create a panel add the components
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		panel.add(title);
		panel.add(selectMode);
		panel.add(playerPicker1);
		panel.add(playerPicker2);
		panel.add(submit);

		// make a window
		final JFrame launcherFrame = new JFrame();
		launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		launcherFrame.setResizable(false);
		launcherFrame.setVisible(true);
		launcherFrame.add(panel);
		launcherFrame.pack();
		launcherFrame.setSize(new Dimension(200, 200));
		launcherFrame.setLocationRelativeTo(null); // center it

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int gameMode = selectMode.getSelectedIndex();
				Player p1, p2;
				if (gameMode == Game.AI_VS_AI) {
					File f1 = files[playerPicker1.getSelectedIndex()];
					String className1 = "artificialIntelligence" + "."
							+ f1.getName().substring(0, f1.getName().indexOf("."));
					p1 = Instantiator.makeComputerPlayer(f1.getPath(), className1, 0);
				} else {
					p1 = new HumanPlayer("Human Player 1", 0);
				}
				if (gameMode != Game.HUMAN_VS_HUMAN) {
					File f2 = files[playerPicker2.getSelectedIndex()];
					String className2 = "artificialIntelligence" + "."
							+ f2.getName().substring(0, f2.getName().indexOf("."));
					p2 = Instantiator.makeComputerPlayer(f2.getPath(), className2, 1);
				} else {
					p2 = new HumanPlayer("Human Player 2", 1);
				}

				new GraphicsGUI(gameMode, p1, p2);
				launcherFrame.dispose();
			}
		});
	}
}