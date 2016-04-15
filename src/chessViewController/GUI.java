package chessViewController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import chessModel.Board;
import chessModel.Piece;

/**
 * 
 * @author Roger Veldman
 */

public class GUI extends JFrame {
	protected static final int SQUARESIZE = 40;
	private Board b;
	private JPanel viewPanel;
	private JPanel inputPanel;
	private JTextField inputField;
	private JEditorPane view;
	private HashMap<String, String> charMap;
	private int selectY;
	private int selectX;
	private static String darkClass = "class = \"dark\"";
	private static String selectedClass = "class = \"select\"";

	public static void main(String args[]) {
		new GUI();
	}

	public GUI() {
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		this.setLayout(new BorderLayout());

		// -1 means nothing is selected
		selectX = -1;
		selectY = -1;

		b = new Board();
		charMap = new HashMap<String, String>();

		charMap.put("K", "&#9812;");
		charMap.put("Q", "&#9813;");
		charMap.put("R", "&#9814;");
		charMap.put("B", "&#9815;");
		charMap.put("N", "&#9816;");
		charMap.put("P", "&#9817;");

		charMap.put("k", "&#9818;");
		charMap.put("q", "&#9819;");
		charMap.put("r", "&#9820;");
		charMap.put("b", "&#9821;");
		charMap.put("n", "&#9822;");
		charMap.put("p", "&#9823;");

		viewPanel = new JPanel();
		inputPanel = new JPanel();
		viewPanel.setLayout(new FlowLayout());
		inputPanel.setLayout(new FlowLayout());
		view = new JEditorPane();
		inputField = new JTextField(20);
		viewPanel.add(view);
		inputPanel.add(inputField);
		this.add(viewPanel, BorderLayout.NORTH);
		this.add(inputPanel, BorderLayout.SOUTH);
		view.setContentType("text/html");
		view.setEditable(false);

		// handle enter key
		inputField.addActionListener(new ActionListener() {

			@Override
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
					updateView();
					inputField.setText("");
					inputField.requestFocus();
				} catch (Exception exc) {
					// was unable to parse the input
					JOptionPane.showInputDialog(null, "Invalid format.");
				}
				deselect();
			}
		});

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int newSelectX = (e.getY() - 5) / 50;
				int newSelectY = (e.getX() - 5) / 50;
				
				// unselects if is the same point or
				// blank space
				boolean blankSpace = b.getPiece(newSelectX,newSelectY)==null;
				boolean noneSelected = (selectX == -1) && (selectY == -1);
				if ((newSelectX == selectX && newSelectY == selectY) || (blankSpace && noneSelected)){
					inputField.setText("");
					
					// deselect
					deselect();
					
					return;
				}
				
				selectX = newSelectX;
				selectY = newSelectY;
				if (inputField.getText().trim().isEmpty()) {
					inputField.setText(Integer.toString(selectX) + Integer.toString(selectY));
				} else if (inputField.getText().length() == 2) {
					inputField.setText(inputField.getText() + Integer.toString(selectX) + Integer.toString(selectY));
					inputField.postActionEvent();
					deselect();
				}
				updateView();
			}
		});
		// style stuff
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet style = kit.getStyleSheet();
		style.addRule("td{text-align:center;width:" + SQUARESIZE + "px;height:" + SQUARESIZE + "px;}");
		style.addRule("body{font-family:sans-serif;font-size:15px;}");
		style.addRule(".dark{background:#DDDDDD;}");
		style.addRule(".select{background:#99CCDD;}");
		kit.setStyleSheet(style);
		view.setEditorKit(kit);

		// present it for the first move
		updateView();
		pack();
		inputField.requestFocus();
	}

	private void updateView() {
		StringBuilder html = new StringBuilder();
		html.append("<table cellspacing='0'>");
		boolean parity = false;
		for (int x = 0; x < b.boardWidth; x++) {
			html.append("<tr>");
			for (int y = 0; y < b.boardHeight; y++) {
				Piece p = b.getPiece(x, y);
				if (p == null) {
					html.append("<td " + getBackgroundClass(parity, x, y) + "></td>");
				} else {
					String unicodeChar = convertChar(p.getChar());
					html.append("<td " + getBackgroundClass(parity, x, y) + ">" + unicodeChar + "</td>");
				}
				parity = !parity;
			}

			// if even board alternate
			if (b.boardWidth % 2 == 0) {
				parity = !parity;
			}

			html.append("</tr>");
		}
		html.append("</table>");
		view.setText(html.toString());
	}

	private String getBackgroundClass(boolean parity, int x, int y) {
		if (x == selectX && y == selectY) {
			return selectedClass;
		}
		if (parity) {
			return darkClass;
		}
		return "";
	}

	private String convertChar(String key) {
		String unicodeChar = charMap.get(key);
		if (unicodeChar == null) {
			return key;
		}
		return unicodeChar;
	}

	private void deselect() {
		selectX = -1;
		selectY = -1;
		updateView();
	}
}