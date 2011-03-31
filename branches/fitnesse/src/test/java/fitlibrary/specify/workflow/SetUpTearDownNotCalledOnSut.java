package fitlibrary.specify.workflow;

import fitlibrary.DoFixture;

public class SetUpTearDownNotCalledOnSut {
	private int setUps;
	private int tearDowns;
	
	public void setUp() {
		setUps++;
	}
	public void tearDown() {
		tearDowns++;
	}
	public int getSetUps() {
		return setUps;
	}
	public int getTearDowns() {
		return tearDowns;
	}
	public void something() {
		//
	}
	
	public Object passSutOn() {
		return new DoFixture(this);
	}
}
