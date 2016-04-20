package chessViewController;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import chessModel.Board;

public class ChessLauncher extends JFrame {
	private JLabel title;
	private JButton go;
	private JPanel p;
	private HashMap<String,Integer> typesMap;
	private ButtonGroup groupTypes;
	int selectedGame;

	public static void main(String args[]) {
		new ChessLauncher();
	}

	public ChessLauncher() {
		this.setResizable(true);
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
		// title.setHorizontalAlignment(SwingConstants.NORTH);
		// title.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		this.add(title, BorderLayout.NORTH);

		p = new JPanel();
		this.add(p,BorderLayout.CENTER);
		
		go = new JButton("Start Game");
		this.add(go,BorderLayout.SOUTH);
		
		addButton("Standard",Board.STANDARD);
		addButton("Speedchess",Board.SPEEDCHESS);
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GraphicsGUI(new Board(selectedGame));
				dispose();
			}
		});
		go.setEnabled(false);
		

		pack();
	}

	public void addButton(String name, int type) {
		JRadioButton newButton = new JRadioButton(name);
		newButton.setActionCommand(name);
		p.add(newButton);
		groupTypes.add(newButton);
		typesMap.put(name, type);

		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(selectedGame);
				selectedGame = typesMap.get(groupTypes.getSelection().getActionCommand());
				go.setEnabled(true);
			}
		});
	}
}
