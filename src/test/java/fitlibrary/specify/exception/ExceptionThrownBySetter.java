/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.exception;

import fitlibrary.traverse.FitLibrarySelector;

public class ExceptionThrownBySetter {
	public Object createUser() {
		return FitLibrarySelector.selectDomainSetUp(new User());
	}

	public static class User {
		public void setName(String name) {
			throw new ForcedException();
		}

		public void setBoss(User user) {
			//
		}
	}
}
