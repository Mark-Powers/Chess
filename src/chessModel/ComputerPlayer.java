package chessModel;

public abstract class ComputerPlayer extends Player{

	public ComputerPlayer(String n, int s){
		super(n, s);
	}

	/**
	 * The move the game waits for.
	 * @param The board in play
	 * @return Array of two numbers, coordinates of move (fromX, fromY, toX,
	 *         toY)
	 */
	public abstract Integer[] getMove(Board board);
}
