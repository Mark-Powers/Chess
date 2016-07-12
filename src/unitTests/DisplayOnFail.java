package unitTests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import chessViewController.MessageCreator;

public class DisplayOnFail extends TestWatcher {
	@Override
	protected void failed(Throwable e, Description description) {
		//MessageCreator.display(BoardTests.getBoard());
		System.out.println(BoardTests.getBoard().getBoardTable());
	}
}