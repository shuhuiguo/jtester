/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.util.HashSet;
import java.util.Set;

import fitlibrary.DoFixture;

@SuppressWarnings("rawtypes")
public abstract class AbstractNestedSets extends DoFixture {
	public static class Fruit {
		private String name;
		private Set set = new HashSet();
		
		public Fruit(String name, Set set) {
			this.name = name;
			this.set = set;
		}
		public String getName() {
			return name;
		}
		public Set getElements() {
			return set;
		}
		@Override
		public String toString() {
			return "Fruit("+name+","+set+")";
		}
	}
	public static class Element {
		private String id;
		private int count;
		private Set subElements = new HashSet();

		public Element(String id, int count) {
			this.id = id;
			this.count = count;
		}
		public Element(String id, int count, Set set) {
			this(id,count);
			this.subElements = set;
		}
		public int getCount() {
			return count;
		}
		public String getId() {
			return id;
		}
		public Set getSubElements() {
			return subElements;
		}
		@Override
		public String toString() {
			return "Element("+id+","+count+")";
		}
	}
	public static class SubElement {
		private String a, b;

		public SubElement(String a, String b) {
			this.a = a;
			this.b = b;
		}
		public String getA() {
			return a;
		}
		public String getB() {
			return b;
		}
		@Override
		public String toString() {
			return "SubElement("+a+","+b+")";
		}
	}
}
