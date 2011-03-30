package org.jtester.utility;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 将原生类型的数组转换成对象数组
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ArrayHelper {
	// boolean
	// byte
	// char
	// short int long
	// float double
	public static Object[] convert(char values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Character value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(float values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Float value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(long values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Long value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(short values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Short value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(int values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Integer value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(double values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Double value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(boolean values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Boolean value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static Object[] convert(byte values[]) {
		List<Object> objs = new ArrayList<Object>();
		for (Byte value : values) {
			objs.add(value);
		}
		return objs.toArray();
	}

	public static <T> Object convert(T value) {
		if (value instanceof int[]) {
			return convert((int[]) value);
		} else if (value instanceof long[]) {
			return convert((long[]) value);
		} else if (value instanceof short[]) {
			return convert((short[]) value);
		} else if (value instanceof float[]) {
			return convert((float[]) value);
		} else if (value instanceof double[]) {
			return convert((double[]) value);
		} else if (value instanceof char[]) {
			return convert((char[]) value);
		} else if (value instanceof byte[]) {
			return convert((byte[]) value);
		} else if (value instanceof boolean[]) {
			return convert((boolean[]) value);
		} else if (value instanceof Collection) {
			return ((Collection) value).toArray(new Object[0]);
		} else {
			return value;
		}
	}

	public static List<?> convert(Object values[]) {
		if (values == null) {
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for (Object o : values) {
			list.add(o);
		}
		return list;
	}

	/**
	 * 判断对象是数组类型
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isArray(Object o) {
		if (o instanceof char[]) {// char
			return true;
		} else if (o instanceof boolean[]) {// boolean
			return true;
		} else if (o instanceof byte[]) {// byte
			return true;
		} else if (o instanceof short[]) {// short
			return true;
		} else if (o instanceof int[]) {// int
			return true;
		} else if (o instanceof long[]) {// long
			return true;
		} else if (o instanceof float[]) {// float
			return true;
		} else if (o instanceof double[]) {// double
			return true;
		} else {
			return o instanceof Object[];
		}
	}

	/**
	 * 判断对象是数组类型或者集合类型
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isCollOrArray(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Collection) {
			return true;
		}
		if (isArray(o)) {
			return true;
		}
		return false;
	}

	/**
	 * 将数组转换为collection
	 * 
	 * @param o
	 * @return
	 */
	public static Collection convertArray(Object o) {
		Collection coll = new ArrayList();
		if (o instanceof char[]) {// char
			char[] chars = (char[]) o;
			for (char ch : chars) {
				coll.add(ch);
			}
		} else if (o instanceof boolean[]) {// boolean
			boolean[] bls = (boolean[]) o;
			for (boolean bl : bls) {
				coll.add(bl);
			}
		} else if (o instanceof byte[]) {// byte
			byte[] bys = (byte[]) o;
			for (byte by : bys) {
				coll.add(by);
			}
		} else if (o instanceof short[]) {// short
			short[] shs = (short[]) o;
			for (short sh : shs) {
				coll.add(sh);
			}
		} else if (o instanceof int[]) {// int
			int[] ints = (int[]) o;
			for (int i : ints) {
				coll.add(i);
			}
		} else if (o instanceof long[]) {// long
			long[] ls = (long[]) o;
			for (long l : ls) {
				coll.add(l);
			}
		} else if (o instanceof float[]) {// float
			float[] fs = (float[]) o;
			for (float f : fs) {
				coll.add(f);
			}
		} else if (o instanceof double[]) {// double
			double[] ds = (double[]) o;
			for (double d : ds) {
				coll.add(d);
			}
		} else if (o instanceof Object[]) {
			Object[] os = (Object[]) o;
			for (Object _o : os) {
				coll.add(_o);
			}
		} else {
			coll.add(o);
		}
		return coll;
	}

	/**
	 * 把一个对象转化为collection<br>
	 * o value是collection，直接返回<br>
	 * o value是数组，转化为collection<br>
	 * o value是Map，返回值对列表<br>
	 * o value是单值对象，new一个collection，返回
	 * 
	 * @param value
	 * @return
	 */
	public static Collection convertToCollection(Object value) {
		if (isCollection(value)) {
			return (Collection) value;
		} else if (isArray(value)) {
			return convertArray(value);
		} else if (value instanceof Map) {
			Map map = (Map) value;
			return map.values();
		} else {
			return Arrays.asList(value);
		}
	}

	/**
	 * 把一个对象转化为collection<br>
	 * o value是collection，直接返回<br>
	 * o value是数组，转化为collection<br>
	 * o value是Map，当作单值对象处理<br>
	 * o value是单值对象，new一个collection，返回
	 * 
	 * @param value
	 * @return
	 */
	public static Collection convertToCollectionWithoutMap(Object value) {
		if (isCollection(value)) {
			return (Collection) value;
		} else if (isArray(value)) {
			return convertArray(value);
		} else {
			return Arrays.asList(value);
		}
	}

	public static boolean isCollection(Object o) {
		if (o == null) {
			return false;
		}
		return o instanceof Collection<?>;
	}

	public static int sizeOf(Object o) {
		if (o == null) {
			return 0;
		}
		int size = 0;
		if (o instanceof Collection<?>) {
			size = ((Collection<?>) o).size();
		} else if (o instanceof char[]) {// char
			size = ((char[]) o).length;
		} else if (o instanceof boolean[]) {// boolean
			size = ((boolean[]) o).length;
		} else if (o instanceof byte[]) {// byte
			size = ((byte[]) o).length;
		} else if (o instanceof short[]) {// short
			size = ((short[]) o).length;
		} else if (o instanceof int[]) {// int
			size = ((int[]) o).length;
		} else if (o instanceof long[]) {// long
			size = ((long[]) o).length;
		} else if (o instanceof float[]) {// float
			size = ((float[]) o).length;
		} else if (o instanceof double[]) {// double
			size = ((double[]) o).length;
		} else if (o instanceof Object[]) {
			size = ((Object[]) o).length;
		} else {
			return 1;
		}

		return size;
	}

	public static String toString(long[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(int[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(short[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(char[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(byte[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(boolean[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(float[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(double[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();
		buf.append('[');
		buf.append(a[0]);

		for (int i = 1; i < a.length; i++) {
			buf.append(", ");
			buf.append(a[i]);
		}

		buf.append("]");
		return buf.toString();
	}

	public static String toString(Object[] a) {
		if (a == null)
			return "null";
		if (a.length == 0)
			return "[]";

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < a.length; i++) {
			if (i == 0)
				buf.append('[');
			else
				buf.append(", ");

			buf.append(String.valueOf(a[i]));
		}

		buf.append("]");
		return buf.toString();
	}

	/**
	 * Converts the given array of elements to a set.
	 * 
	 * @param elements
	 *            The elements
	 * @return The elements as a set, empty if elements was null
	 */
	public static <T> Set<T> asSet(T... elements) {
		Set<T> result = new HashSet<T>();
		if (elements == null) {
			return result;
		}
		result.addAll(asList(elements));
		return result;
	}
}
