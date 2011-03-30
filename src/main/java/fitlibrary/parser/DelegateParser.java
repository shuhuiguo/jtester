/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser;

import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;

public abstract class DelegateParser {
	protected Class<?> type;

	public DelegateParser() {
		this.type = null;
	}

	public DelegateParser(Class<?> type) {
		this.type = type;
	}

	public boolean matches(Object a, Object b) {
		if (a == null)
			return b == null;
		return a.equals(b);
	}

	public String show(Object object) {
		if (object == null)
			return "null";
		return object.toString();
	}

	public Parser parser(Evaluator evaluator, Typed typed) {
		return new DelegatingParser(cloneSelf(), evaluator, typed);
	}

	private DelegateParser cloneSelf() {
		try {
			return (DelegateParser) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public abstract Object parse(String s, Typed typed) throws Exception;
}
