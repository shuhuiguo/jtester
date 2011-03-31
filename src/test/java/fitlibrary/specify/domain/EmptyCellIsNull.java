/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 3/11/2006
 */

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;

public class EmptyCellIsNull implements DomainFixtured {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static class User {
		private String name;
		private User manager;

		public User getManager() {
			return manager;
		}

		public void setManager(User manager) {
			this.manager = manager;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
