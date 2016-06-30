package artificialIntelligence;

import java.util.ArrayList;

import chessModel.*;

public class RandomMove extends ComputerPlayer {
	
	public RandomMove(Board b, int s) {
		super(b, "Random Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove() {
		Integer[] move = { 1, 1, 3, 1 };
		ArrayList<Integer[]> allMoves = getBoard().getAllMoves(side);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		return allMoves.get((int) (Math.random()*(allMoves.size()-1)));
	}

}