/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

import fitlibrary.DoFixture;
import fitlibrary.SetFixture;
import fitlibrary.SubsetFixture;

public class StartListing extends DoFixture {
	private Element[] elements;
	
	public void listIs(int[] array) {
		elements = new Element[array.length];
		for (int i = 0; i < array.length; i++)
			elements[i] = new Element(array[i]);
	}
	public Element[] orderedList() {
		return elements;
	}
	public SetFixture unorderedList() {
		return new SetFixture(elements);
	}
	public SubsetFixture subset() {
		return new SubsetFixture(elements);
	}
	public static class Element {
		private int item;

		public Element(int i) {
			this.item = i;
		}
		public int getItem() {
			return item;
		}
		public void setItem(int item) {
			this.item = item;
		}
	}
}
