package chessModel;

public abstract class Player {
	
	Game game;
	String name;
	int side;
	
	public Player(Game g, String n, int s){
		game = g;
		name = n;
		side = s;
	}
	
	/**
	 * The move the game waits for.
	 * @return Array of two numbers, coordinates of move (fromX, fromY, toX, toY)
	 */
	public abstract Integer[] getMove();
}
