package artificialIntelligence;

import java.util.ArrayList;

import chessModel.Board;
import chessModel.ComputerPlayer;
import chessModel.TestBoard;
import chessModel.piece.Piece;

public class ImproveScore extends ComputerPlayer {
	public ImproveScore(int s) {
		super("Random Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove(Board board) {
		ArrayList<Integer[]> allMoves = board.getAllMoves(side);

		Integer[] currentMove = allMoves.get(0);
		int bestScore = board.getBlackScore();
		if (side == 0) {
			bestScore = board.getWhiteScore();
		}

		int count = 0;
		for (Integer[] move : allMoves) {
			TestBoard testBoard = new TestBoard();
			testBoard.populateFromFEN(board.getFEN());
			
			Piece p = testBoard.getPiece(move[0], move[1]);
			if (p.validMove(move[2], move[3],
					testBoard.getSquareStatus(move[2], move[3], side))) {
				//System.out.print(p.getChar() + p.getY());
				testBoard.move(move[0], move[1], move[2], move[3]);
				//System.out.print(p.getY());
				//System.out.println();
				int compareScore = testBoard.getBlackScore();
				if (side == 0) {
					compareScore = testBoard.getWhiteScore();
				}
				
				if (p != null) {
					//System.out.println(p.getChar() + " " + bestScore);
					if (compareScore > bestScore) {
						bestScore = compareScore;
						currentMove = move;
						
					}
				}
			}

		}

		return currentMove;
	}

}
