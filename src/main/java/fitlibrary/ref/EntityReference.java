/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.ref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.utility.ClassUtility;

public class EntityReference {
	private static final String[] forms = { "this", "the" };
	private static final String[] counts = { "first", "second", "third", "fourth", "fifth", "sixth", "seventh",
			"eigth", "nineth", "tenth" };
	private static Map<String, EntityReference> entityReferenceCache = new ConcurrentHashMap<String, EntityReference>();
	private Map<String, Integer> mapStringToInt = new HashMap<String, Integer>();
	private String entityClassName;

	public static EntityReference create(String name) {
		EntityReference ref = entityReferenceCache.get(name);
		if (ref == null) {
			ref = new EntityReference(name);
			entityReferenceCache.put(name, ref);
		}
		return ref;
	}

	protected EntityReference(String entityClassName) {
		this.entityClassName = entityClassName;
		createMap();
	}

	private void createMap() {
		for (int form = 0; form < forms.length; form++) {
			final String theForm = forms[form];
			put(theForm, new Integer(0)); // "the"
			put(theForm + " " + entityClassName, new Integer(0)); // "the client"
			put(theForm + " last", new Integer(-1)); // "the last"
			put(theForm + " last " + entityClassName, new Integer(-1)); // "the last client"
			for (int count = 0; count < counts.length; count++) {
				final String theCountString = counts[count];
				final Integer theCount = new Integer(count);
				put(theCountString, theCount); // "first"
				put(theForm + " " + theCountString, theCount); // "the first"
				put(theForm + " " + theCountString + " " + entityClassName, theCount); // "the first client"
				put(entityClassName + "#" + (count + 1), theCount); // "client#0"
			}
		}
	}

	private void put(String referenceString, Integer index) {
		mapStringToInt.put(referenceString.toLowerCase(), index);
	}

	public int getIndex(String text) {
		Integer result = mapStringToInt.get(text.toLowerCase());
		if (result == null)
			throw new FitLibraryException("Reference not defined: '" + text + "'");
		return result.intValue();
	}

	private String reference(int index, int count) {
		if (count == 1)
			return "the " + entityClassName;
		if (index >= counts.length)
			return entityClassName + "#" + (index + 1);
		return "the " + counts[index] + " " + entityClassName;
	}

	public static String reference(Object object, List<?> list) {
		int index = list.indexOf(object);
		String className = ClassUtility.simpleClassName(object.getClass());
		EntityReference referenceParser = new EntityReference(className);
		return referenceParser.reference(index, list.size());
	}
}
