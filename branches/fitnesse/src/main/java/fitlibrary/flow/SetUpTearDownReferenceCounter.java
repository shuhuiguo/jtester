/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import java.util.HashMap;
import java.util.Map;

import fitlibrary.traverse.DomainAdapter;

/**
 * Ensures that a DomainAdapter has its setUp() method called once when it's
 * first added and has its tearDown() method called once when it's last removed.
 * This allows or DomainAdapters that are referenced several times, such as
 * through the "calculate" action.
 */
public class SetUpTearDownReferenceCounter {
	private final Map<DomainAdapter, ReferenceCount> referenceCounts = new HashMap<DomainAdapter, ReferenceCount>();

	interface MethodCaller {
		void setUp(Object object);

		void tearDown(Object object);
	}

	static class ReferenceCount {
		private int count = 0;

		public ReferenceCount(int count) {
			this.count = count;
		}

		public void inc() {
			count++;
		}

		public void decrement() {
			count--;
		}

		public boolean isZero() {
			return count == 0;
		}
	}

	public void callSetUpOnNewReferences(Object sut, MethodCaller methodCaller) {
		Object object = sut;
		while (object instanceof DomainAdapter) {
			addReference((DomainAdapter) object, methodCaller);
			object = ((DomainAdapter) object).getSystemUnderTest();
		}
	}

	private void addReference(DomainAdapter object, MethodCaller methodCaller) {
		ReferenceCount referenceCount = referenceCounts.get(object);
		if (referenceCount == null) {
			methodCaller.setUp(object);
			referenceCount = new ReferenceCount(1);
			referenceCounts.put(object, referenceCount);
		} else
			referenceCount.inc();
	}

	public void callTearDownOnReferencesThatAreCountedDown(Object sut, MethodCaller methodCaller) {
		Object object = sut;
		while (object instanceof DomainAdapter) {
			removeReference((DomainAdapter) object, methodCaller);
			object = ((DomainAdapter) object).getSystemUnderTest();
		}
	}

	private void removeReference(DomainAdapter object, MethodCaller methodCaller) {
		ReferenceCount referenceCount = referenceCounts.get(object);
		if (referenceCount != null) {
			referenceCount.decrement();
			if (referenceCount.isZero()) {
				methodCaller.tearDown(object);
				referenceCounts.remove(object);
			}
		}
	}
}
