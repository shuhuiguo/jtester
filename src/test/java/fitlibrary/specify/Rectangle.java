/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

public class Rectangle {
	public int x, y, width, height;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Point getLocation() {
		return new Point(x,y);
	}
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
