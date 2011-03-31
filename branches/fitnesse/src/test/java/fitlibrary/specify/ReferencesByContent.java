/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fitlibrary.DoFixture;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReferencesByContent extends DoFixture {
	HashMap map = new HashMap();

	public ReferencesByContent() {
		map.put("orange", new Fruit("orange", 3));
		map.put("apple", new Fruit("apple", 2));
	}

	public int fruit(Fruit fruit) {
		return fruit.getCount();
	}

	public Fruit look(Fruit fruit) {
		return fruit;
	}

	public int vegetable(Vegetable vegetable) {
		return -1;
	}

	public Fruit findFruit(String key) {
		Fruit fruit = (Fruit) map.get(key);
		// if (fruit == null)
		// throw new NullPointerException();
		return fruit;
	}

	public String showFruit(Fruit fruit) {
		return fruit.toString();
	}

	public List getStallFruit() {
		List list = new ArrayList();
		list.add(new SaleItem((Fruit) map.get("orange")));
		list.add(new SaleItem((Fruit) map.get("apple")));
		return list;
	}

	public static class SaleItem {
		private Fruit fruit;

		public SaleItem(Fruit fruit) {
			this.fruit = fruit;
		}

		public Fruit getFruit() {
			return fruit;
		}
	}

	static class Fruit {
		private String name;
		private int count;

		public Fruit(String name, int count) {
			this.name = name;
			this.count = count;
		}

		public int getCount() {
			return count;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	static class Vegetable {
		//
	}
}
