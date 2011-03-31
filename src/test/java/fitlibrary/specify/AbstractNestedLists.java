/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.object.DomainFixtured;

public abstract class AbstractNestedLists implements DomainFixtured {
	public static class Fruit {
		private String name;
		private List<Object> list = new ArrayList<Object>();

		public Fruit(String name, List<Object> list) {
			this.name = name;
			this.list = list;
		}

		public String getName() {
			return name;
		}

		public List<Object> getElements() {
			return list;
		}

		@Override
		public String toString() {
			return "Fruit(" + name + "," + list + ")";
		}
	}

	public static class Element {
		private String id;
		private int count;
		private List<Object> subElements = new ArrayList<Object>();
		private Element next;

		public Element() {
			//
		}

		public Element(String id, int count) {
			this.id = id;
			this.count = count;
		}

		public Element(String id, int count, List<Object> list) {
			this(id, count);
			this.subElements = list;
		}

		public int getCount() {
			return count;
		}

		public String getId() {
			return id;
		}

		public List<Object> getSubElements() {
			return subElements;
		}

		@Override
		public String toString() {
			return "Element(" + id + "," + count + ")";
		}

		public void setNext(Element next) {
			this.next = next;
		}

		public Element getNext() {
			return next;
		}

		public void setId(String id) {
			this.id = id;
		}

		public static Element parse(String s) {
			return new Element("newOne", 99);
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
			return "SubElement(" + a + "," + b + ")";
		}
	}
}
