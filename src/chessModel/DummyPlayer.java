package chessModel;

public class DummyPlayer extends ComputerPlayer {

	public DummyPlayer(Board b, int s) {
		super(b, "Dummy Player " + (s + 1), s);
	}

	@Override
	public Integer[] getMove() {
		Integer[] move = { 1, 1, 3, 1 };
		return move;
	}

}
