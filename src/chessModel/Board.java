package chessModel;

import java.util.ArrayList;

public class Board {
	public final int boardWidth;
	public final int boardHeight;
	protected ArrayList<Piece> pieces = new ArrayList<Piece>();
	private ArrayList<Integer[]> movelog;
	private int whiteScore;
	private int blackScore;

	public Board() {
		boardWidth = 8;
		boardHeight = 8;
		whiteScore = 0;
		blackScore = 0;
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

	public boolean move(int oldX, int oldY, int x, int y) {
		Piece selectedP = null;
		Piece otherP = null;
		SquareStatus status = SquareStatus.EMPTY;
		// Sets selectedP to the right piece
		selectedP = getPiece(oldX, oldY);

		if (selectedP != null) {
			otherP = getPiece(x, y);
			if (otherP != null && otherP != selectedP) {
				if (otherP.getSide() == selectedP.getSide()) {
					status = SquareStatus.TEAM;
				}
				if (otherP.getSide() != selectedP.getSide()) {
					status = SquareStatus.ENEMY;
				}
			}

			if (!status.equals(SquareStatus.TEAM) && selectedP.validMove(x, y, status) && !isObstructed(selectedP, x, y)) {
				if (isInCheck(selectedP.getSide())) {
					System.out.println("test");
					if (!resolvesCheck(selectedP, x, y)) {
						return false;
					}
				}
				selectedP.move(x, y, status);
				if (status.equals(SquareStatus.ENEMY)) {
					int scoreEarned = otherP.getValue();
					if (selectedP.getSide() == 0) {
						whiteScore += scoreEarned;
					} else if (selectedP.getSide() == 1) {
						blackScore += scoreEarned;
					}
					pieces.remove(otherP);

				}
				Integer[] numsForLog = new Integer[4];
				numsForLog[0] = oldX;
				numsForLog[1] = oldY;
				numsForLog[2] = x;
				numsForLog[3] = y;
				movelog.add(numsForLog);
				return true;
			}
		}
		return false;
	}

	public boolean isObstructed(Piece p, int x, int y) {
		// Must be left/right
		if (p.getX() == x) {
			int dir = y > p.getY() ? 1 : -1;
			for (int tmpY = p.getY() + dir; tmpY != y; tmpY += dir) {
				if (getPiece(x, tmpY) != null) {
					return true;
				}
			}
		}
		// must be up/down
		if (p.getY() == y) {
			int dir = x > p.getX() ? 1 : -1;
			for (int tmpX = p.getX() + dir; tmpX != x; tmpX += dir) {
				if (getPiece(tmpX, y) != null) {
					return true;
				}
			}
		}
		// Change in x == Change in y (diagonal movement)
		if (Math.abs(p.getX() - x) == Math.abs(p.getY() - y)) {
			int dirY = y > p.getY() ? 1 : -1;
			int dirX = x > p.getX() ? 1 : -1;
			int tmpY = p.getY() + dirY;
			int tmpX = p.getX() + dirX;
			while (tmpX != x && tmpY != y) {
				if (getPiece(tmpX, tmpY) != null) {
					return true;
				}
				tmpX += dirX;
				tmpY += dirY;
			}

		}
		// Otherwise it must be fine
		return false;
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

	public boolean isThreatenedSquare(int x, int y, int side) {
		for (Piece p : pieces) {
			if (p.getSide() != side) { // Only the other team can threaten a square for any given side
				SquareStatus squareToCheck = getSquareStatus(x, y, p.getSide());
				if (p.validMove(x, y, squareToCheck) && !isObstructed(p, x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param side The side testing for check
	 * @return If the side given is in check
	 */
	public boolean isInCheck(int side) {
		for (Piece p : pieces) {
			if (p instanceof King && p.getSide() == side) {
				return isThreatenedSquare(p.getX(), p.getY(), side);
			}
		}
		return false;
	}

	/**
	 * 
	 * @param p
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean resolvesCheck(Piece p, int x, int y) {
		Board b = new Board();
		for(Integer[] move : movelog){
			b.move(move[0], move[1], move[2], move[3]);
		}
		b.getPiece(p.getX(), p.getY()).forceMove(x, y);;
		if(b.isInCheck(p.side)){
			System.out.println("still in check");
			return false;
		}
		System.out.println("got out of check");
		return true;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public SquareStatus getSquareStatus(int x, int y, int side) {
		SquareStatus square = SquareStatus.EMPTY;
		for (Piece otherP : pieces) {
			if (otherP.getX() == x && otherP.getY() == y) {
				if (otherP.getSide() == side) {
					square = SquareStatus.TEAM;
				} else {
					square = SquareStatus.ENEMY;
				}
				break;
			}
		}
		return square;
	}

	/**
	 * @param x The x coordinate of the piece to find 
	 * @param y The y coordinate of the piece to find
	 * @return The piece at coordinates (x, y), or null if there is no piece
	 */
	public Piece getPiece(int x, int y) {
		for (Piece piece : pieces) {
			if (x == piece.x && y == piece.y) {
				return piece;
			}
		}
		return null;
	}

	public ArrayList<Integer[]> getAllMoves(int side) {
		ArrayList<Integer[]> moveList = new ArrayList<Integer[]>();
		for (Piece p : pieces) {
			moveList.add(getMovesForPiece(p));
		}
		return moveList;
	}

	public Integer[] getMovesForPiece(Piece p) {
		Integer[] moveList = new Integer[4];
		for (int i = 0; i < boardHeight; i++) {
			for (int u = 0; u < boardWidth; u++) {
				SquareStatus status = getSquareStatus(u, i, p.getSide());
				if (!status.equals(SquareStatus.TEAM)
						&& p.validMove(u, i, status)
						&& !isObstructed(p, u, i)) {
					moveList[0] = p.getX();
					moveList[1] = p.getY();
					moveList[2] = u;
					moveList[3] = i;
				}
			}
		}
		return moveList;
	}

	public ArrayList<Integer[]> getMoveLog() {
		return movelog;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public int getBlackScore() {
		return blackScore;
	}
	
	
}
