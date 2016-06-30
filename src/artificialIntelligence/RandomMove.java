package artificialIntelligence;

import java.util.ArrayList;

import chessModel.Board;
import chessModel.ComputerPlayer;

public class RandomMove extends ComputerPlayer {
	
	private int delay;
	
	public RandomMove(int s) {
		super("Random Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove(Board board) {
		ArrayList<Integer[]> allMoves = board.getAllMoves(side);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		board.move(1, 1, 3, 1);
				
		return allMoves.get((int) (Math.random()*(allMoves.size()-1)));
	}

}