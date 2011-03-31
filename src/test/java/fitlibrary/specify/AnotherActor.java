/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;
import fit.ActionFixture;
import fit.Fixture;
import fit.specify.ActionFixtureUnderTest;

public class AnotherActor extends Fixture {
	private ActionFixtureUnderTest test;
	
	public AnotherActor() {
		this(new ActionFixtureUnderTest());
	}
	public AnotherActor(ActionFixtureUnderTest test) {
		this.test = test;
	}
	public void start() {
		//
	}
	public void stop() {
		//
	}
	public void switchBack() {
		ActionFixture.actor = test;
	}
}
