/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/11/2006
 */

package fitlibrary.specify.workflow;

import fitlibrary.traverse.DomainAdapter;

public class ParserDelegateMethod implements DomainAdapter {
	/*
	 * Note that this technique can't be used with java.util.Date because a
	 * Parser is already defined for it, and so a finder method won't ever be
	 * checked for
	 */
	public Date sameDate(Date date) {
		return date;
	}

	public Date findDate(String s) {
		return new Date(s);
	}

	public String showDate(Date d) {
		return d.toString();
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}

	public static class Date {
		private String s;

		public Date(String s) {
			this.s = s;
		}

		@Override
		public boolean equals(Object object) {
			if (!(object instanceof Date))
				return false;
			return s.equals(((Date) object).s);
		}

		@Override
		public String toString() {
			return s;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

}
