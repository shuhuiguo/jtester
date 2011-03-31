/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.suite;

import fitlibrary.suite.SuiteFixture;
import fitlibrary.traverse.workflow.DoTraverse;

public class VariantOnSimple extends SuiteFixture {
	public DoTraverse aFixture() {
		return new DoTraverse(new Sut());
	}
    public MyOtherDoFixture anotherFixture() {
        return new MyOtherDoFixture();
    }
    public boolean one() {
    	return false;
    }
    public boolean two() {
    	return true;
    }
	public static class Sut {
		public boolean andSomeImmediateAction() {
			return false;
		}
		public boolean andMore() {
			return true;
		}
        public boolean andMoreBesides() {
            return false;
        }
	}
}
