package unitTests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class DisplayOnFail extends TestWatcher {
    @Override
    protected void failed(Throwable e, Description description) {
    	BoardTests.display(BoardTests.getBoard());
    }
}