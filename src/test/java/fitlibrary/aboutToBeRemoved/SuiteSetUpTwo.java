package fitlibrary.aboutToBeRemoved;
import fitlibrary.suite.SuiteFixture;


public class SuiteSetUpTwo extends SuiteFixture {

	public SetUp setUp2() { 
		return new SetUp();
	}
	public boolean go(int i) {
		return i == 2;
	}
}
