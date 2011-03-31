/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

import fitlibrary.DoFixture;

public class VectorAddition extends DoFixture {
	public Vector addTo(Vector v1, Vector v2) {
		return v1.add(v2);
	}
	public static class Vector {
		private int x, y;
		
		public Vector() {
			//
		}
		public Vector(int x, int y) {
			setX(x);
			setY(y);
		}
		public Vector add(Vector v2) {
			return new Vector(x+v2.getX(),y+v2.getY());
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
	}
}
