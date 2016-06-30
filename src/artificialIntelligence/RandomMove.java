package artificialIntelligence;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import chessModel.*;

public class RandomMove extends ComputerPlayer {
	
	private int delay;
	
	public RandomMove(Board b, int s) {
		super(b, "Random Player " + (s + 1), s);
		try {
			delay = Integer.parseInt(JOptionPane.showInputDialog("Enter delay time in millis", 200));
		} catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Invalid input. Set to default 200");
			delay = 200;
		}
	}

	@Override
	public Integer[] getMove() {
		Integer[] move = { 1, 1, 3, 1 };
		ArrayList<Integer[]> allMoves = getBoard().getAllMoves(side);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		return allMoves.get((int) (Math.random()*(allMoves.size()-1)));
	}

}