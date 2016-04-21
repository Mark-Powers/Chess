package chessModel;

import java.util.ArrayList;

public class Board {
	public int boardWidth;
	public int boardHeight;
	public int currentTeamNo;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private Log movelog;
	public static final int STANDARD = 1;
	public static final int SPEEDCHESS = 2;
	public int moveNo;

	public Board (){
		this(STANDARD);
	}
	
	public Board(int config) {
		currentTeamNo = 0;
		moveNo = 1;
		boardWidth = 8;
		boardHeight = 8;
		movelog = new Log();
		
		initPieces(config);
	}

	private void initPieces(int config) {
		switch (config) {
		case 1:
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
			for (int i = 0; i < 8; i++) {
				pieces.add(new Pawn(1, i, 0));
				pieces.add(new Pawn(6, i, 1));
			}
			break;
		case 2:
			boardWidth = 5;
			boardHeight = 6;
			pieces.add(new Queen(0, 0, 0));
			pieces.add(new King(0, 1, 0));
			pieces.add(new Bishop(0, 2, 0));
			pieces.add(new Knight(0, 3, 0));
			pieces.add(new Rook(0, 4, 0));
			pieces.add(new Queen(5, 4, 1));
			pieces.add(new King(5, 3, 1));
			pieces.add(new Bishop(5, 2, 1));
			pieces.add(new Knight(5, 1, 1));
			pieces.add(new Rook(5, 0, 1));
			for (int i = 0; i < 8; i++) {
				pieces.add(new Pawn(1, i, 0));
				pieces.add(new Pawn(4, i, 1));
			}
			break;
		default:
			break;
		}

	}

	public void move(int oldX, int oldY, int x, int y) {
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

			if (currentTeamNo == selectedP.getSide() && !status.equals(SquareStatus.TEAM)
					&& selectedP.validMove(x, y, status) && !isObstructed(selectedP, x, y)) {
				selectedP.setX(x);
				selectedP.setY(y);
				if (status.equals(SquareStatus.ENEMY)) {
					pieces.remove(otherP);
				}
				
				movelog.addToLog(oldX,oldY,x,y,moveNo,currentTeamNo);
				
				if (currentTeamNo == 1){
					moveNo++;
				}
				currentTeamNo = currentTeamNo == 0 ? 1 : 0;
			}
		}
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
		// must be up down
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
					if ((i % 2 == 0 && u % 2 == 0) || (i % 2 != 0 && u % 2 != 0))
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

	public String getMoveLog() {
		return movelog.toString();
	}
}
