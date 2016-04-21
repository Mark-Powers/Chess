package chessModel;

public class Pawn extends Piece {
	private boolean hasTakenFirstMove = false;

	public Pawn(int x, int y, int side) {
		this.x = x;
		this.y = y;
		this.side = side;
	}

	public String getChar() {
		if (side == 0)
			return "P";
		return "p";
	}

	public boolean validMove(int x2, int y2, SquareStatus status) {
		// TODO En Passant
		if ((((side == 0 && y == y2 && x == (x2 - 1)) || (side == 1 && y == y2 && x == (x2 + 1))) && !status.equals(SquareStatus.ENEMY))
				|| (((side == 0 && Math.abs(y - y2) == 1 && x == (x2 - 1)) || (side == 1 && Math.abs(y - y2) == 1 && x == (x2 + 1))) && status.equals(SquareStatus.ENEMY))
				|| (((side == 0 && y == y2 && x == (x2 - 2)) || (side == 1 && y == y2 && x == (x2 + 2))) && !status.equals(SquareStatus.ENEMY) && !hasTakenFirstMove)) {
			hasTakenFirstMove = true;
			return true;
		}
		return false;
	}
	public int getValue(){
    	return 1;
    }
	
}
