/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.suite;

import fitlibrary.suite.SuiteFixture;
import fitlibrary.traverse.workflow.DoTraverse;

public class Simple extends SuiteFixture {
	private int count = 0;
    
    public DoTraverse aFixture() {
		return new DoTraverse(new Sut(count++));
	}
    public MyOtherDoFixture anotherFixture() {
        return new MyOtherDoFixture();
    }
    public void countIs(int newCount) {
        this.count = newCount;
    }
	public static class Sut {
		private int count;
        
        public Sut(int count) {
            this.count = count;
        }
        public boolean andSomeImmediateAction() {
			return true;
		}
		public boolean andMore() {
			return true;
		}
        public boolean andMoreBesides() {
            return true;
        }
        public int getCount() {
            return count;
        }
	}
}
