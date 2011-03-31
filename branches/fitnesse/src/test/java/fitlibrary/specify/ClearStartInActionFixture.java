/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fit.ActionFixture;

/**
 * Clear the actor of ActionFixture to be sure that it's clear in a subsequent table.
 */
public class ClearStartInActionFixture extends fit.Fixture {
	public ClearStartInActionFixture() {
		ActionFixture.actor = null;
	}
}
