/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 14/09/2006
 */

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;
import fitlibrary.traverse.DomainAdapter;

public class NotConcrete implements DomainAdapter, DomainFixtured {
	private AbstractUser user;

	public static abstract class AbstractUser {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class BadPayer extends AbstractUser {
		//
	}

	public AbstractUser getAbstractUser() {
		return user;
	}

	public void setAbstractUser(AbstractUser user) {
		this.user = user;
	}

	public Class<?> concreteClassOfAbstractUser(String typeName) {
		if ("Bad Payer".equals(typeName))
			return BadPayer.class;
		return null;
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
