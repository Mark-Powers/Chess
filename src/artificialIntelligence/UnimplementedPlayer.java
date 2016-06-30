package artificialIntelligence;

import chessModel.*;

/**
 * 
 * @author Roger
 */
public class UnimplementedPlayer extends ComputerPlayer {
	
	public UnimplementedPlayer(int s) {
		super("Dummy Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove(Board board) {
		Integer[] move = { 1, 1, 3, 1 };
		return move;
	}

}