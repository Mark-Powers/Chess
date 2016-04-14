package chessViewController;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private Board b;
	private JPanel viewPanel;
	private JPanel inputPanel;
	private JTextField inputField;
	private JEditorPane view;
	private HashMap<String,String> charMap;
	
	public static void main(String args[]){
		new GUI();
	}
	
	public GUI(){
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		this.setLayout(new BorderLayout());
		
		b = new Board();
		charMap = new HashMap<String,String>();
		
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
		this.add(viewPanel,BorderLayout.NORTH);
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
			}
		});
		
		// style stuff
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet style = kit.getStyleSheet();
		style.addRule("td{text-align:center;width:30px;height:30px;}");
		style.addRule("body{font-family:sans-serif;font-size:15px;}");
		style.addRule(".dark{background:#DDDDDD;}");
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
				Piece p = b.getPiece(x,y);
				String parityClass = "";
				if (parity){
					parityClass = "class = \"dark\"";
				}
				if (p==null){
					html.append("<td "+ parityClass +"></td>");
				} else {
					String unicodeChar = converChar(p.getChar());
					html.append("<td "+ parityClass +">"+ unicodeChar +"</td>");
				}
				parity = !parity;
			}
			
			// if even board alternate
			if (b.boardWidth%2 == 0){
				parity = !parity;
			}
			
			html.append("</tr>");
		}
		html.append("</table>");
		view.setText(html.toString());
	}

	private String converChar(String key) {
		String unicodeChar = charMap.get(key);
		if (unicodeChar == null){
			return key;
		}
		return unicodeChar;
	}
}
