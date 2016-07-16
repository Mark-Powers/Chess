package chessViewController;

import javax.swing.JOptionPane;

import chessModel.Board;

public class MessageCreator {
	public static void display(Board displayboard) {
		JOptionPane.showMessageDialog(null, new ChessView(displayboard), "Error Display View",
				JOptionPane.PLAIN_MESSAGE);
	}
	public static void display(String str) {
		JOptionPane.showMessageDialog(null, str, "Error Message View", JOptionPane.WARNING_MESSAGE);
	}
}
