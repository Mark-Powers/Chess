package artificialIntelligence;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import chessModel.Board;
import chessModel.ComputerPlayer;
import chessModel.TestBoard;
import chessViewController.ChessView;

public class SeekImproveScoreOneDeep extends ComputerPlayer {
	public SeekImproveScoreOneDeep(int s) {
		super("Improve Score " + (s + 1), s);
	}

	@Override
	public Integer[] getMove(Board board) {
		ArrayList<Integer[]> allMoves = board.getAllMoves(side);

		Integer[] currentMove = allMoves.get((int) (Math.random()*(allMoves.size()-1)));
		int bestScore = Integer.MIN_VALUE;
		if (side == 0) {
			bestScore = board.getWhiteScore();
		}
		for (Integer[] move : allMoves) {
			TestBoard tb = new TestBoard();
			tb.populateFromFEN(board.getFEN());
			if (tb.move(move[0], move[1], move[2], move[3])) {
				if (tb.getScore(side)>bestScore){
					currentMove = move;
					bestScore = tb.getScore(side);
				}
			}
		}

		return currentMove;
	}

	public static void display(Board displayboard) {
		if (displayboard.getPieces().size() == 0) {
			return;
		}
		JOptionPane.showMessageDialog(null, new ChessView(displayboard), "Error Display View",
				JOptionPane.WARNING_MESSAGE);
	}

}
