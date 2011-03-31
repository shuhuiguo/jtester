/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.SetUpFixture;
import fitlibrary.traverse.DomainAdapter;

public class DomainAdapterUnderTest implements DomainAdapter {
	Object sut = new Sut();

	// @Override
	public Object getSystemUnderTest() {
		return sut;
	}

	public boolean call(int i) {
		return true;
	}

	public int productAB(int a, int b) {
		return a * b;
	}

	public SetUpFixture create() {
		return new SetUpFixture(this);
	}

	@SuppressWarnings("rawtypes")
	public static class Sut implements DomainAdapter {
		List ab = new ArrayList();

		public boolean callInSut(int i) {
			return true;
		}

		// @Override
		public Object getSystemUnderTest() {
			return new ChainedSut();
		}

		@SuppressWarnings("unchecked")
		public void aB(int a, int b) {
			ab.add(new AB(a, b));
		}

		public List getAb() {
			return ab;
		}
	}

	public static class ChainedSut {
		public boolean callInSutSut(int i) {
			return true;
		}

		public int sumXY(int x, int y) {
			return x + y;
		}
	}

	public static class AB {
		private int a;
		private int b;

		public AB(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public int getA() {
			return a;
		}

		public int getB() {
			return b;
		}
	}
}
