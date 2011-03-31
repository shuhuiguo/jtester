/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.workflow.SequenceTraverse;

/**
 * Exactly the same as DoFixture, except that actions don't have keywords in
 * every second cell.
 */
public class SequenceFixture extends DoFixture {
	private SequenceTraverse sequenceTraverse = new SequenceTraverse(this);

	public SequenceFixture() {
		setTraverse(sequenceTraverse);
	}

	public SequenceFixture(Object sut) {
		this();
		setSystemUnderTest(sut);
	}
}
