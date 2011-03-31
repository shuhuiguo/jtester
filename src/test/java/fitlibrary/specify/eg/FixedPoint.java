/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/08/2006
 */

package fitlibrary.specify.eg;

public class FixedPoint implements FixedPointInterface {
	private int x, y;

	public FixedPoint() {
		//
	}

	public FixedPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// @Override
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof FixedPoint))
			return false;
		FixedPoint other = (FixedPoint) object;
		return x == other.x && y == other.y;
	}

	@Override
	public int hashCode() {
		return x + y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
