/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.eg;

@SuppressWarnings("rawtypes")
public class Count implements Comparable {
	private int count;

	public Count(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Count[" + count + "]";
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Count))
			return false;
		return ((Count) object).count == count;
	}

	@Override
	public int hashCode() {
		return count;
	}

	// @Override
	public int compareTo(Object object) {
		if (!(object instanceof Count))
			return +1;
		return count - ((Count) object).count;
	}
}
