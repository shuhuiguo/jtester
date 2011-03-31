/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 6/09/2006
*/

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Account;
import fitlibrary.specify.eg.User;

public class Objects implements DomainFixtured {
	private User user = new User();
	
	public Objects() {
		user.setName("Paul");
		user.setAccount(new Account(2,"good payer"));
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
