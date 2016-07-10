package chessModel;

public abstract class ComputerPlayer extends Player{

	/**
	 * Ask the player for a move. The game should run this on a different thread.
	 * @param board The board in play
	 * @return Array of two numbers, coordinates of move (fromX, fromY, toX, toY)
	 */
	public abstract Integer[] getMove(Board board);
}
