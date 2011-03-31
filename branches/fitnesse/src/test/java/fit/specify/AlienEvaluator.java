/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 16/08/2006
*/

package fit.specify;

import fit.Fixture;

public class AlienEvaluator {
	public Fixture fixture() {
		return new MyColumnFixture();
	}
}
