package artificialIntelligence;

import java.util.ArrayList;
import java.util.Random;

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
		Random random = new Random();
		return allMoves.get(random.nextInt(allMoves.size()));
	}

}