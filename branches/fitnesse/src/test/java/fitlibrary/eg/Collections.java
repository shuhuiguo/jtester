/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fitlibrary.traverse.FitLibrarySelector;
import fitlibrary.traverse.Traverse;

@SuppressWarnings("unchecked")
public class Collections {
	@SuppressWarnings("rawtypes")
	private List elements = new ArrayList();
	private int[] ints;
	
	public void listIs(int[] array) {
		for (int i = 0; i < array.length; i++)
			elements.add(new Element(array[i]));
	}
	public void intsAre(int[] array) {
		ints = array;
	}
	public int[] getInts() {
		return ints;
	}
	@SuppressWarnings("rawtypes")
	public List getOrderedList() {
		return elements;
	}
	@SuppressWarnings("rawtypes")
	public Set getUnorderedList() {
		return new HashSet(elements);
	}
	public Traverse subset() {
		return FitLibrarySelector.selectSubset(elements);
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
