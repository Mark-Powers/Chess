package artificialIntelligence;

import chessModel.*;

/**
 * 
 * @author Roger
 */
public class UnimplementedPlayer extends ComputerPlayer {
	
	public UnimplementedPlayer(Board b, int s) {
		super(b, "Dummy Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove() {
		Integer[] move = { 1, 1, 3, 1 };
		return move;
	}

}