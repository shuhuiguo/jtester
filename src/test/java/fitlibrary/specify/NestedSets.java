/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NestedSets extends AbstractNestedSets {
	public Fruit[] nested() {
		return new Fruit[]{ apple(), orange(), pear() };
	}
	public Set nestedSet() {
		HashSet hashSet = new HashSet();
		hashSet.add(apple());
		hashSet.add(orange());
		hashSet.add(pear());
		return hashSet;
	}
	private static Fruit apple() {
		Set set = new HashSet();
		set.add(new Element("a",1,firstInnerMostList()));
		set.add(new Element("b",2,secondInnerMostList()));
		return new Fruit("apple",set);
	}
	private static Set firstInnerMostList() {
		Set innerSet = new HashSet();
		innerSet.add(new SubElement("x","y"));
		innerSet.add(new SubElement("x","z"));
		return innerSet;
	}
	private static Set secondInnerMostList() {
		Set innerSet = new HashSet();
		innerSet.add(new SubElement("xx","ZZ"));
		return innerSet;
	}
	private static Fruit orange() {
		Set set = new HashSet();
		set.add(new Element("c",3));
		return new Fruit("orange", set);
	}
	private static Fruit pear() {
		return new Fruit("pear", new HashSet());
	}
}
