/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fit.RowFixture;

/**
 * Makes it easier to use a RowFixture with flow tables, as there is no need to
 * subclass.
 */
public class ParamRowFixture extends RowFixture {
	private Object[] objects;
	private Class<?> targetClass;

	/**
	 * There needs to be at least one element in the collection for this to be
	 * used.
	 */
	public ParamRowFixture(Object[] objects) {
		this(objects, objects[0].getClass());
	}

	public ParamRowFixture(Object[] objects, Class<?> targetClass) {
		this.objects = objects;
		this.targetClass = targetClass;
	}

	@Override
	public Object[] query() throws Exception {
		return objects;
	}

	@Override
	public Class<?> getTargetClass() {
		return targetClass;
	}
}
