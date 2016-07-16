package artificialIntelligence;

import chessModel.Board;
import chessModel.Player;

/**
 * 
 * @author Roger
 */
public class UnimplementedPlayer extends Player {

	@Override
	public Integer[] getMove(Board board) {
		Integer[] move = { 1, 1, 3, 1 };
		return move;
	}

}