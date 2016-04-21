package chessViewController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.java.accessibility.util.java.awt.ButtonTranslator;

import chessModel.Board;
import chessModel.Piece;

public class ChessFrame extends JFrame {
	private JMenuBar menu;
	private JMenu file;
	private JMenuItem save,viewLog;
	private Board b;
	private ChessView chessView;
	private JTextField inputField;

	public ChessFrame(Board board) {
		this.setResizable(false);
		this.setMinimumSize(new Dimension(500,500));

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
		save = new JMenuItem("Save PGM...");
		viewLog = new JMenuItem("View PGM...");
		menu.add(file);
		file.add(save);
		file.add(viewLog);

		this.setJMenuBar(menu);

		b = board;

		chessView = new ChessView(b);

		inputField = new JTextField(10);
		
		this.add(inputField, BorderLayout.SOUTH);
		this.add(chessView, BorderLayout.CENTER);
		pack();
		this.setSize(getWidth(),(int) chessView.getSize().getHeight());


		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) ((screenDimensions.getWidth() - getWidth()) / 2),
				(int) ((screenDimensions.getHeight() - getHeight()) / 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		inputField.requestFocus();

		final ActionListener writeActionListener = (new ActionListener() {

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
					pw.write(b.getMoveLog().toString());
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
				JTextArea ta = new JTextArea(b.getMoveLog().toString());
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
				int xLoc = (e.getY()) / cellSize;
				int yLoc = (e.getX()) / cellSize;
				if (chessView.getSelected() != null) {
					b.move(chessView.getSelected().getX(), chessView.getSelected().getY(), xLoc, yLoc);
					chessView.setSelected(null);
					chessView.repaint();
				} else {
					Piece p = b.getPiece(xLoc, yLoc);
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

		// handle enter key
		inputField.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				try {
					// use each character as input to a board move
					String in = inputField.getText();
					int[] nums = new int[4];
					String[] chars = in.split("");
					for (int i = 0; i < nums.length; i++) {
						nums[i] = Integer.parseInt(chars[i]);
					}
					b.move(nums[0], nums[1], nums[2], nums[3]);
					chessView.repaint();
					inputField.setText("");
					inputField.requestFocus();
				} catch (Exception exc) {
					// was unable to parse the input
					JOptionPane.showMessageDialog(null, "Invalid format.");
				}
			}
		});
	}
}
