package chessModel;

import java.util.ArrayList;

public class Board {
	public int boardWidth;
	public int boardHeight;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private ArrayList<Integer[]> movelog;

	public Board() {
		boardWidth = 8;
		boardHeight = 8;
		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn(1, i, 0));
			pieces.add(new Pawn(6, i, 1));
		}
		pieces.add(new Rook(0, 0, 0));
		pieces.add(new Rook(0, 7, 0));
		pieces.add(new Rook(7, 0, 1));
		pieces.add(new Rook(7, 7, 1));
		pieces.add(new Knight(0, 1, 0));
		pieces.add(new Knight(0, 6, 0));
		pieces.add(new Knight(7, 1, 1));
		pieces.add(new Knight(7, 6, 1));
		pieces.add(new Bishop(0, 2, 0));
		pieces.add(new Bishop(0, 5, 0));
		pieces.add(new Bishop(7, 2, 1));
		pieces.add(new Bishop(7, 5, 1));
		pieces.add(new Queen(0, 3, 0));
		pieces.add(new Queen(7, 3, 1));
		pieces.add(new King(0, 4, 0));
		pieces.add(new King(7, 4, 1));
		movelog = new ArrayList<Integer[]>();
	}

	public void move(int oldX, int oldY, int x, int y) {
		Piece selectedP = null;
		Piece otherP = null;
		SquareStatus status = SquareStatus.EMPTY;
		// Sets selectedP to the right piece
		selectedP = getPiece(oldX, oldY);

		if (selectedP != null) {
			otherP = getPiece(x, y);
			if (otherP != selectedP) {
				if (otherP.getSide() == selectedP.getSide()) {
					status = SquareStatus.TEAM;
				}
				if (otherP.getSide() != selectedP.getSide()) {
					status = SquareStatus.ENEMY;
				}
			}

			if (!status.equals(SquareStatus.TEAM)
					&& !isObstructed(selectedP, x, y)
					&& selectedP.validMove(x, y, status)) {
				selectedP.setX(x);
				selectedP.setY(y);
				if (status.equals(SquareStatus.ENEMY)) {
					pieces.remove(otherP);
				}
				Integer[] numsForLog = new Integer[4];
				numsForLog[0] = oldX;
				numsForLog[1] = oldY;
				numsForLog[2] = x;
				numsForLog[3] = y;
				movelog.add(numsForLog);
			}
		}
		}
		// Otherwise it must be fine
		return false;
	}

	public void display() {
		print(getBoard());
	}

	public String getBoard() {
		String board = "";
		boolean wasPrinted;
		board += ("  01234567\n\n");
		for (int i = 0; i < boardHeight; i++) {
			board += (i + " ");
			for (int u = 0; u < boardWidth; u++) {
				wasPrinted = false;
				for (Piece piece : pieces) {
					if (piece.x == i && piece.y == u) {
						board += (piece.getChar());
						wasPrinted = true;
						break;
					}
				}
				if (!wasPrinted) {
					if ((i % 2 == 0 && u % 2 == 0)
							|| (i % 2 != 0 && u % 2 != 0))
						board += ("O");
					else
						board += ("O");
				}
			}
			board += ("\n");
		}
		board += ("\n");
		return board;
	}

	public void print(String s) {
		System.out.print(s);
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public Piece getPiece(int x, int y) {
		for (Piece piece : pieces) {
			if (x == piece.x && y == piece.y) {
				return piece;
			}
		}
		return null;
	}

	public ArrayList<Integer[]> getMoveLog() {
		return movelog;
	}
}
