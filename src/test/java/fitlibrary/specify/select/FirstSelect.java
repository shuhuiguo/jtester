package fitlibrary.specify.select;

import fitlibrary.DoFixture;

public class FirstSelect extends DoFixture {
	public int count() {
		return 1;
	}
	public SecondSelect second() {
		return new SecondSelect();
	}
}
