/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 6/09/2006
*/

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class Array implements DomainFixtured {
	private int[] anIntArray = {1,2,4};
	private Colour[] aColourArray = { Colour.RED, Colour.GREEN };
	private int[][] a2DArray = {anIntArray,anIntArray};
	public int[][] getA2DArray() {
		return a2DArray;
	}
	public void setA2DArray(int[][] array) {
		a2DArray = array;
	}
	public Colour[] getAColourArray() {
		return aColourArray;
	}
	public void setAColourArray(Colour[] colourArray) {
		aColourArray = colourArray;
	}
	public int[] getAnIntArray() {
		return anIntArray;
	}
	public void setAnIntArray(int[] anIntArray) {
		this.anIntArray = anIntArray;
	}
	public Colour colour(String name) {
		return new Colour(name);
	}
}
