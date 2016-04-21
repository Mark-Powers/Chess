package chessModel;

import java.util.ArrayList;

public class Board {
	public final int boardWidth;
	public final int boardHeight;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
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

			if (!status.equals(SquareStatus.TEAM)
					&& selectedP.validMove(x, y, status)
					&& !isObstructed(selectedP, x, y)) {
				if(isInCheck(selectedP.getSide())){
					
					// Check if new move undoes check.
				}
				
				selectedP.setX(x);
				selectedP.setY(y);
				if (status.equals(SquareStatus.ENEMY)) {
					int scoreEarned = otherP.getValue();
					if(selectedP.getSide()==0){
						whiteScore+=scoreEarned;
					} else if (selectedP.getSide()==1){
						blackScore+=scoreEarned;
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
		// must be up down
		if (p.getY() == y) {
			int dir = x > p.getX() ? 1 : -1;
			for (int tmpX = p.getX() + dir;  tmpX != x; tmpX += dir) {
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
			while(tmpX != x && tmpY != y) {
				if (getPiece(tmpX, tmpY) != null) {
					return true;
				}
				tmpX += dirX; 
				tmpY +=dirY;
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

	public boolean isThreatenedSquare(int x, int y, int side){
		for(Piece p:pieces){
			if(p.getSide()!=side){
				SquareStatus squareToCheck = SquareStatus.ENEMY;
				for(Piece otherP:pieces){
					if(otherP.getX()==x && otherP.getY()==y){
						if(otherP.getSide()==p.getSide()){
							squareToCheck = SquareStatus.TEAM;
						} else {
							squareToCheck = SquareStatus.ENEMY;
						}
						break;
					}
				}
				if(p.validMove(x, y, squareToCheck)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isInCheck(int side){
		for(Piece p:pieces){
			if(p instanceof King && p.getSide()==side){
				return isThreatenedSquare(p.getX(), p.getY(), side);
			}
		}
		return false;
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
	public int getWhiteScore(){
		return whiteScore;
	}
	public int getBlackScore(){
		return blackScore;
	}
}
