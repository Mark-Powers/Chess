package artificialIntelligence;

import chessModel.*;

public class RandomMove extends ComputerPlayer {
	
	public RandomMove(Board b, int s) {
		super(b, "Random Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove() {
		Integer[] move = { 1, 1, 3, 1 };
		return move;
	}

}