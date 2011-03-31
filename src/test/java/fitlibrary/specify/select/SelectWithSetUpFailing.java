package fitlibrary.specify.select;

import fitlibrary.DoFixture;

public class SelectWithSetUpFailing extends DoFixture {
	public void setUp() {
		throw new RuntimeException("failure in setUp()");
	}
}
