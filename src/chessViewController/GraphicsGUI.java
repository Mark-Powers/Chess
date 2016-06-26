package chessViewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import chessModel.Board;
import chessModel.Game;
import chessModel.Piece;

public class GraphicsGUI extends JFrame {
	private JMenuBar menu;
	private JMenu file;
	private JMenuItem save, details;
	private Game g;
	private ChessView chessView;
	JLabel timer1Label;
	JLabel timer2Label;
	JLabel player1Score;
	JLabel player2Score;
	
	public static void main(String args[]) {
		new GraphicsGUI();
	}

	public GraphicsGUI() {
		
		// this.setResizable(false);
		this.setMinimumSize(new Dimension(300, 300));

		setVisible(true);

		// Set to the native look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Could not find native look and feel.");
		}

		this.setLayout(new BorderLayout());

		menu = new JMenuBar();
		file = new JMenu("File");
		save = new JMenuItem("Save...");
		details = new JMenuItem("View...");
		menu.add(file);
		file.add(save);
		file.add(details);

		this.setJMenuBar(menu);
		
		g = new Game();
		final Board b = g.getBoard();

		chessView = new ChessView(b);
		
		
		// Timer/Score area stuff
		timer1Label = new JLabel("", SwingConstants.CENTER);
		timer2Label = new JLabel("", SwingConstants.CENTER);
		player1Score= new JLabel("", SwingConstants.CENTER);
		player2Score= new JLabel("", SwingConstants.CENTER);
		Timer updateTimer = new Timer(100, new ActionListener() { // Updated 10 times a second
			public void actionPerformed(ActionEvent e) {
				if(g.getCurrentSide()==0){
					timer1Label.setText("<html>P1 Time: <font color='red'>"+g.getPlayer1Time()+"</font></html>");
					timer2Label.setText("<html>P2 Time: "+g.getPlayer2Time()+"</html>");
				} else if(g.getCurrentSide()==1){
					timer1Label.setText("<html>P1 Time: "+g.getPlayer1Time()+"</html>");
					timer2Label.setText("<html>P2 Time: <font color='red'>"+g.getPlayer2Time()+"</font></html>");
				}
				player1Score.setText("Score: "+g.getPlayer1Score());
				player2Score.setText("Score: "+g.getPlayer2Score());
			}
		});
		updateTimer.start();
		JPanel timerScorePanel = new JPanel();
		timerScorePanel.setLayout(new GridLayout(1, 4));
		timerScorePanel.add(timer1Label);
		timerScorePanel.add(player1Score);
		timerScorePanel.add(player2Score);
		timerScorePanel.add(timer2Label);
		this.add(timerScorePanel, BorderLayout.SOUTH);
		
		
		this.add(chessView, BorderLayout.CENTER);

		pack();

		this.setSize(424, 500);

		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) ((screenDimensions.getWidth() - getWidth()) / 2),
				(int) ((screenDimensions.getHeight() - getHeight()) / 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		save.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				File f = new File("C:/CHESSLOGS");
				fc.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
				f.mkdirs();
				fc.setCurrentDirectory(f);
				fc.showSaveDialog(fc.getParent());
				try {
					PrintWriter pw = new PrintWriter(fc.getSelectedFile());
					StringBuilder log = new StringBuilder();
					log.append(b.getPGN());
					pw.write(log.toString());
					pw.close();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Unable to write file");
				}
			}
		});
		
		details.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel p = new JPanel();
				p.setLayout(new BorderLayout());
				
				JTextArea pgn = new JTextArea("PGN:\n"+b.getPGN());
				pgn.setEditable(false);
				
				JTextArea fen = new JTextArea("Fen:\n"+b.getFEN());
				fen.setEditable(false);
				fen.setBackground(Color.lightGray);
				
				JTextArea raw = new JTextArea("Raw Moves:\n"+b.getLogRaw());
				raw.setEditable(false);
				
				p.add(pgn, BorderLayout.NORTH);
				p.add(fen, BorderLayout.CENTER);
				p.add(raw, BorderLayout.SOUTH);
				
				JOptionPane.showMessageDialog(null, p, "Details", JOptionPane.PLAIN_MESSAGE);
			}
		});

		MouseAdapter humanInput = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int cellSize = chessView.getCellSize();
				int xLoc = (e.getY()) / cellSize;
				int yLoc = (e.getX()) / cellSize;
				if (g.isHumanInputEnabled()){
					handleLocationClicked(xLoc, yLoc);
				}
			}
		};
		
		chessView.addMouseListener(humanInput);
	}
	
	public void isOver(){
		if(g.isCheckMate()){
			JOptionPane.showMessageDialog(this, g.getCurrentSide()+" has lost from checkmate");
		} else if(g.isDraw()){
			JOptionPane.showMessageDialog(this, "It's a draw!");
		} else if(g.getPlayer1Time().equals("0:0") || g.getPlayer2Time().equals("0:0")){
			JOptionPane.showMessageDialog(this, "Time's Up!");
		}
	}

	/**
	 * @param xLoc
	 * @param yLoc
	 */
	private void handleLocationClicked(int xLoc, int yLoc) {
		if (chessView.getSelected() != null) {
			g.move(chessView.getSelected().getX(), chessView.getSelected().getY(), xLoc, yLoc);
			chessView.setSelected(null);
			chessView.repaint();
			g.gameLoop();
		} else {
			Piece p = g.getBoard().getPiece(xLoc, yLoc);
			if (p == null) {
				chessView.setSelected(null);
			} else if (p.equals(chessView.getSelected())) {
				chessView.setSelected(null);
			} else {
				chessView.setSelected(p);
			}
			chessView.repaint();
		}
	}
}

