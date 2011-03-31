/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.eg;

public class MyPoint {
	private int x, y;

	public MyPoint() {
		//
	}
	public MyPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
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
		if (!(object instanceof MyPoint))
			return false;
		MyPoint other = (MyPoint) object;
		return x == other.x && y == other.y;
	}
	@Override
	public int hashCode() {
		return x+y;
	}
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	public static MyPoint parse(String s) {
		if (!s.startsWith("(") || !s.endsWith(")"))
			throw new RuntimeException("Badly formatted point");
		int comma = s.indexOf(",");
		if (comma < 0)
			throw new RuntimeException("Badly formatted point");
		return new MyPoint(
				Integer.parseInt(s.substring(1,comma)),
				Integer.parseInt(s.substring(comma+1,s.length()-1)));
	}
}
