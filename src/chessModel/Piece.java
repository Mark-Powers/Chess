package chessModel;

public abstract class Piece {
	protected int x;
	protected int y;
	protected int side;

	public boolean validMove(int x, int y, SquareStatus status, boolean checking) {
		return validMove(x, y, status);
	}

	public abstract String getChar();

	public abstract boolean validMove(int x, int y, SquareStatus status);

	public abstract int getValue();

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSide() {
		return side;
	}

}
