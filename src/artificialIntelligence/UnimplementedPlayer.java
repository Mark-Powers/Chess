package artificialIntelligence;

import chessModel.Board;
import chessModel.ComputerPlayer;

/**
 * 
 * @author Roger
 */
public class UnimplementedPlayer extends ComputerPlayer {

	@Override
	public Integer[] getMove(Board board) {
		Integer[] move = { 1, 1, 3, 1 };
		return move;
	}

}