/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
*/

package fitlibrary.specify.exception;

import fitlibrary.traverse.FitLibrarySelector;

public class ExceptionThrownByGetter {
	public Object checkUser() {
		return FitLibrarySelector.selectDomainCheck(new User());
	}
	public static class User {
		public String getName() {
			throw new ForcedException();
		}
		public User getBoss() {
			return new User();
		}
	}
}
