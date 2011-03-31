/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 20/08/2006
 */

package fitlibrary.specify.exception;

import fitlibrary.traverse.DomainAdapter;

public class ExceptionThrownByNestedEquals implements DomainAdapter {
	public Project project() {
		return new Project();
	}

	public User findUser(String key) {
		return new User();
	}

	public static class Project {
		public User getLeader() {
			return new User();
		}
	}

	public static class User {
		@Override
		public boolean equals(Object object) {
			throw new ForcedException();
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
