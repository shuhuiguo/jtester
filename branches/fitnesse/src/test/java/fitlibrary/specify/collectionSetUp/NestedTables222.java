/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.collectionSetUp;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.SetUpFixture;
import fitlibrary.specify.AbstractNestedLists;

@SuppressWarnings({"unchecked","rawtypes"})
public class NestedTables222 extends AbstractNestedLists {
	protected List fruits;

	public class MySetUpFixture extends SetUpFixture {
		public SetUpFixture nested() {
			fruits = new ArrayList();
			return this;
		}
		public void nameElements(String name, List elements) { 
			fruits.add(new Fruit(name,elements));
		}
		public Element idCount(String id, int count) {
			return new Element(id,count);
		}
		public Element id(String id) {
			return idCount(id,0);
		}
		public Element idNext(String id, Element next) {
			Element id2 = id(id);
			id2.setNext(next);
			return id2;
		}
		public Element idCountSubElements(String id, int count, List subElements) {
			return new Element(id,count,subElements);
		}
		public SubElement aB(String a, String b) {
			return new SubElement(a,b);
		}
	}
	public List nested() {
		return fruits;
	}
}
