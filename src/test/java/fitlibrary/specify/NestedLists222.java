/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NestedLists222 extends AbstractNestedLists {
	public Fruit[] nested() {
		return new Fruit[]{ apple(), orange(), pear() };
	}
	private static Fruit apple() {
		List list = new ArrayList();
		list.add(new Element("a",1,firstInnerMostList()));
		list.add(new Element("b",2,secondInnerMostList()));
		return new Fruit("apple",list);
	}
	private static List firstInnerMostList() {
		List innerList = new ArrayList();
		innerList.add(new SubElement("x","y"));
		innerList.add(new SubElement("x","z"));
		return innerList;
	}
	private static List secondInnerMostList() {
		List innerList = new ArrayList();
		innerList.add(new SubElement("xx","ZZ"));
		return innerList;
	}
	private static Fruit orange() {
		List list = new ArrayList();
		list.add(new Element("c",3));
		return new Fruit("orange", list);
	}
	private static Fruit pear() {
		return new Fruit("pear", new ArrayList());
	}
}
