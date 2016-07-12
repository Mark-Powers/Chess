package chessModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import chessViewController.ChessView;
import chessViewController.GraphicsGUI;

public class HumanPlayer extends Player {

	private ChessView view;
	private GraphicsGUI gui;
	private Integer[] move;

	public void setupView(GraphicsGUI theGui, ChessView theView) {
		view = theView;
		gui = theGui;
		MouseAdapter humanInput = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int cellSize = view.getCellSize();
				int xLoc = (e.getY()) / cellSize;
				int yLoc = (e.getX()) / cellSize;	
				move = gui.handleLocationClicked(xLoc, yLoc);
			}
		};

		view.addMouseListener(humanInput);
	}

	@Override
	public Integer[] getMove(Board board) {
		while (move == null){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Integer[] moveCopy = move;
		move = null;
		return moveCopy;
	}
}
