package artificialIntelligence;

import java.util.ArrayList;

import chessModel.Board;
import chessModel.Player;

public class RandomMove extends Player {
	
	private int delay;

	public Integer[] getMove(Board board) {
		ArrayList<Integer[]> allMoves = board.getAllMoves(side);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		return allMoves.get((int) (Math.random()*(allMoves.size()-1)));
	}

}