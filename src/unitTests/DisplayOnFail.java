package unitTests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class DisplayOnFail extends TestWatcher {
	public static int REALBOARD = 0;
	public static int TESTBOARD = 1;
	private int type;

	public DisplayOnFail(int type) {
		this.type = type;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		if (type == REALBOARD) {
			TestBoardTests.display(RealBoardTests.getBoard());
		} else {
			RealBoardTests.display(TestBoardTests.getBoard());
		}
	}
}