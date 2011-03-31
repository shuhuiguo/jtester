/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 21/09/2006
 */

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;
import fitlibrary.traverse.DomainAdapter;

public class BadClassFromClassFactoryMethod implements DomainAdapter, DomainFixtured {
	public void setAbstractUser(AbstractUser user) {
		//
	}

	public static abstract class AbstractUser {
		//
	}

	public static class PrivateUser extends AbstractUser {
		private String name;

		private PrivateUser() {
			//
		}

		@SuppressWarnings("unused")
		private String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		private void setName(String name) {
			this.name = name;
		}
	}

	public static class NoNullaryUser extends AbstractUser {
		public NoNullaryUser(int i) {
			//
		}
	}

	public Class<?> concreteClassOfAbstractUser(String typeName) {
		if ("Private".equals(typeName))
			return PrivateUser.class;
		if ("No Nullary".equals(typeName))
			return NoNullaryUser.class;
		if ("String".equals(typeName))
			return String.class;
		return null;
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
