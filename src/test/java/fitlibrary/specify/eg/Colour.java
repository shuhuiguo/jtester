/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.eg;

@SuppressWarnings("rawtypes")
public class Colour implements Comparable {
	public static Colour RED = new Colour("red");
	public static Colour GREEN = new Colour("green");
	public static Colour BLACK = new Colour("black");
	public static Colour WHITE = new Colour("white");
	public static Colour YELLOW = new Colour("yellow");
	public static Colour BLUE = new Colour("blue");
	private String colour;

	public Colour() {
		//
	}

	public Colour(String colour) {
		this.colour = colour;
	}

	public static Object parse(String s) {
		return new Colour(s);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Colour))
			return false;
		return ((Colour) object).colour.equals(colour);
	}

	@Override
	public int hashCode() {
		return colour.hashCode();
	}

	@Override
	public String toString() {
		return "Colour[" + colour + "]";
	}

	// @Override
	public int compareTo(Object object) {
		if (!(object instanceof Colour))
			return -1;
		return colour.compareTo(((Colour) object).colour);
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
}
