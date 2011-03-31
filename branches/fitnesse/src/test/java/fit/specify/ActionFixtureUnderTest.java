/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fit.specify;

import fit.ActionFixture;
import fit.Fixture;
import fitlibrary.specify.AnotherActor;

public class ActionFixtureUnderTest extends Fixture {
	private int result = 0;

	public void pressMethod() {
		//
	}

	public void enterString(String s) {
		//
	}

	public void enterResult(int r) {
		this.result = r;
	}

	public int intResultMethod() {
		return result;
	}

	public boolean booleanTrue() {
		return true;
	}

	public boolean booleanFalse() {
		return false;
	}

	public void enterThrows(String s) {
		throw new RuntimeException();
	}

	public void pressThrows() {
		throw new RuntimeException();
	}

	public String checkThrows() {
		throw new RuntimeException();
	}

	public int pressMethodReturningInt() {
		return 123;
	}

	public void enterMethodWithNoArgs() {
		//
	}

	public void enterMethodWithTwoArgs(String a, String b) {
		//
	}

	public void switchActor() {
		ActionFixture.actor = new AnotherActor(this);
	}
}
