/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fitlibrary.DoFixture;

public class ListToSet extends DoFixture {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set listToSet(List list) {
		return new HashSet(list);
	}
	public Person name(String name) {
		return new Person(name);
	}
	public static class Person {
		private String name;

		public Person(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public int hashCode() {
			return name.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Person))
				return false;
			return name.equals(((Person)obj).name);
		}
	}
}
