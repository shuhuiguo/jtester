/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.exception;

import fitlibrary.traverse.DomainAdapter;

public class ExceptionThrownByShow implements DomainAdapter {
	public User user() {
		return new User();
	}

	public User findUser(String s) {
		return new User(); // Won't match
	}

	public String showUser(User user) {
		throw new ForcedException();
	}

	public static class User {
		//
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
