/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.graphic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Produce a Dot graph definition for an object, using a breadth-first approach
 * to walking, with a limit on the size of the graph.
 */
public class ObjectDotGraphic extends DotGraphic {
	private int nodeNo = 0;
	private String[] exclusions = { "sun.", "com.sun.", "org.omg.", "javax.", "sunw.", "java.", "org.w3c.dom.",
			"org.xml.sax.", "net.jini." };
	private HashMap<Object, String> visited = new HashMap<Object, String>();
	private List<Object> queue = new LinkedList<Object>();
	private int nodesToProcess = 50;

	public ObjectDotGraphic(Object object) {
		super("");
		String firstNodeID = "n0";
		this.dot = "digraph G {\n" + visit(object, firstNodeID) + buildGraph() + "}\n";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String buildGraph() {
		String result = "";
		while (!queue.isEmpty() && --nodesToProcess > 0) {
			Object object = queue.remove(0);
			String objectID = visited.get(object);

			Class<?> type = object.getClass();
			if (object instanceof Set)
				result += buildSetGraph(objectID, (Set<Object>) object);
			else if (object instanceof Map)
				result += buildSetGraph(objectID, ((Map) object).entrySet());
			else if (object instanceof Collection)
				result += buildCollectionGraph(objectID, (Collection<?>) object);
			else if (object instanceof Object[])
				result += buildCollectionGraph(objectID, Arrays.asList((Object[]) object));
			else {
				result += buildObjectGraph(objectID, object, getAllFields(type));
			}
		}
		return result;
	}

	private Object[] getAllFields(Class<?> type) {
		List<Field> fields = new LinkedList<Field>();
		getAllFields(type, fields);
		return fields.toArray();
	}

	private void getAllFields(Class<?> type, List<Field> list) {
		list.addAll(Arrays.asList(type.getDeclaredFields()));
		if (type != Object.class)
			getAllFields(type.getSuperclass(), list);
	}

	private String buildObjectGraph(String objectID, Object object, Object[] fields) {
		String result = "";
		for (int i = 0; i < fields.length; i++) {
			Field field = (Field) fields[i];
			try {
				String name = field.getName();
				Object value = field.get(object);
				result += makeArc(objectID, value, name);
			} catch (IllegalAccessException e) {
				result += accessThroughProperty(field, objectID, object);
			}
		}
		return result;
	}

	private String firstUpper(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private String accessThroughProperty(Field field, String objectID, Object object) {
		String name = firstUpper(field.getName());
		String methodName = "get" + name;
		if (field.getType().equals(Boolean.class))
			methodName = "is" + name;
		try {
			Method method = object.getClass().getMethod(methodName, new Class[] {});
			Object value = method.invoke(object, new Object[] {});
			return makeArc(objectID, value, name);
		} catch (Exception e) {
			return "";
		}
	}

	private String makeArc(String objectID, Object value, String label) {
		String result = "";
		String valueID = visited.get(value);
		if (valueID == null) {
			nodeNo++;
			valueID = "n" + nodeNo;
			result += visit(value, valueID);
		}
		result += objectID + " -> " + valueID + " [label=\"" + label + "\"];\n";
		return result;
	}

	private String visit(Object object, String nodeID) {
		visited.put(object, nodeID);
		if (excluded(object))
			return nodeID + " [label = \"" + object.toString() + "\"];\n";
		queue.add(object);
		Class<?> type = object.getClass();
		if (type.isArray())
			return nodeID + " [label = \"" + type.getComponentType().getName() + "[]\"];\n";
		if (collectionSetOrMap(object))
			return nodeID + " [shape=box, label = \"" + type.getName() + "\"];\n";
		return nodeID + " [label = \"" + type.getName() + "\"];\n";
	}

	private String buildCollectionGraph(String objectID, Collection<?> collection) {
		String result = "";
		int count = 0;
		for (Object value : collection)
			result += makeArc(objectID, value, "[" + count + "]");
		return result;
	}

	private String buildSetGraph(String objectID, Set<Object> set) {
		String result = "";
		for (Object value : set)
			result += makeArc(objectID, value, "");
		return result;
	}

	private boolean excluded(Object object) {
		if (collectionSetOrMap(object))
			return false;
		String name = object.getClass().getName();
		for (int i = 0; i < exclusions.length; i++)
			if (name.startsWith(exclusions[i]))
				return true;
		return false;
	}

	private boolean collectionSetOrMap(Object object) {
		return object instanceof Collection || object instanceof Set || object instanceof Map;
	}

	public Object getDot() {
		return dot;
	}
}
