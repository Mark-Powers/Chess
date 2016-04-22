package chessViewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
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

public class ChessFrame extends JFrame {
	private JMenuBar menu;
	private JMenu file;
	private JMenuItem save, viewLog;
	private Game g;
	private ChessView chessView;
	JLabel timer1Label;
	JLabel timer2Label;
	JLabel player1Score;
	JLabel player2Score;
	
	public static void main(String args[]) {
		new ChessFrame();
	}

	public ChessFrame() {
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
		save = new JMenuItem("Save PGN...");
		viewLog = new JMenuItem("View PGN...");
		menu.add(file);
		file.add(save);
		file.add(viewLog);

		this.setJMenuBar(menu);
		
		g = new Game();
		Board b = g.getBoard();

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
				// TODO CHECK FOR GAMEOVER
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
		this.setSize(getWidth(),(int) chessView.getSize().getHeight());


		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) ((screenDimensions.getWidth() - getWidth()) / 2),
				(int) ((screenDimensions.getHeight() - getHeight()) / 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final ActionListener writeActionListener = (new ActionListener() {


			// @Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				File f = new File(System.getProperty("user.home")+"/CHESSLOGS");
				fc.setFileFilter(new FileNameExtensionFilter("PGN File", "pgn"));
				f.mkdirs();
				fc.setCurrentDirectory(f);
				fc.showSaveDialog(fc.getParent());
				/*if (!fc.getSelectedFile().getName().contains(".")){
					fc.setSelectedFile(new File(fc.getSelectedFile().getAbsolutePath()+".pgn"));
				}*/
				try {
					PrintWriter pw = new PrintWriter(fc.getSelectedFile());
					pw.write(g.getBoard().getMoveLog().toString());
					pw.close();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Unable to write file");
				}
			}
		});
		
		save.addActionListener(writeActionListener);
		
		viewLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame("Log");
				JTextArea ta = new JTextArea(g.getBoard().getMoveLog().toString());
				f.setLayout(new BorderLayout());
				ta.setEditable(false);
				ta.setForeground(Color.gray);
				ta.setMargin(new Insets(12,12,12,12));
				f.add(ta,BorderLayout.CENTER);
				JButton button = new JButton("Save...");
				button.addActionListener(writeActionListener);
				f.add(button,BorderLayout.SOUTH);
				f.setVisible(true);
				f.pack();
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

		chessView.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int cellSize = chessView.getCellSize();
				int xLoc = (e.getY()) / cellSize - 1;
				int yLoc = (e.getX()) / cellSize - 1;
				if (chessView.getSelected() != null) {
					g.move(chessView.getSelected().getX(), chessView.getSelected().getY(), xLoc, yLoc);
					chessView.setSelected(null);
					chessView.repaint();
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
		});
	}
}

