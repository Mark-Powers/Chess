package chessModel;

public abstract class Player {
	
	protected String name;
	protected int side;
	
	public void init(String n, int s){
		name = n;
		side = s;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Ask the player for a move. The game should run this on a different thread.
	 * @param board The board in play
	 * @return Array of two numbers, coordinates of move (fromX, fromY, toX, toY)
	 */
	public abstract Integer[] getMove(Board board);
}
