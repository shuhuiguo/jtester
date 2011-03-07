package org.jtester.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.StringTokenizer;

@SuppressWarnings({ "rawtypes" })
public class ClazzUtil {
	/**
	 * 类路径中是否有 org.hibernate.tool.hbm2ddl.SchemaExport class
	 * 
	 * @return
	 */
	public final static boolean doesImportSchemaExport() {
		try {
			Class.forName("org.hibernate.tool.hbm2ddl.SchemaExport");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public final static String getPackFromClassName(String clazzName) {
		int index = clazzName.lastIndexOf(".");
		String pack = "";
		if (index > 0) {
			pack = clazzName.substring(0, index);
		}
		return pack;
	}

	/**
	 * 根据类名获得package的路径
	 * 
	 * @param clazzName
	 * @return
	 */
	public final static String getPathFromPath(String clazzName) {
		String pack = getPackFromClassName(clazzName);
		return pack.replace(".", String.valueOf(File.separatorChar));
	}

	/**
	 * 根据类名获得package的路径
	 * 
	 * @param clazzName
	 * @return
	 */
	public final static String getPathFromPath(Class<?> clazz) {
		return getPathFromPath(clazz.getName());
	}

	public final static void generateClazz(String clazzName, byte[] r) throws IOException {
		// debug，调试生成的字节码
		FileOutputStream file = new FileOutputStream("d:/" + clazzName.substring(clazzName.lastIndexOf('.') + 1)
				+ ".class");
		file.write(r);
		file.flush();
		file.close();
	}

	/**
	 * package名称有效字符表达式
	 */
	public final static String VALID_PACK_REGULAR = "[^\\.]*";

	/**
	 * 将带有星号的package名称转换为正则表达式
	 * 
	 * @param regexPackage
	 *            带有星号的package名称，比如 com.**.service.*Impl
	 * @return
	 */
	public static String getPackageRegex(String regexPackage) {
		String _interfaceKey = regexPackage.replace('*', '#');// 先把*替换成不会被使用的字符#，避免后面的正则表达式替换错误
		String regex = _interfaceKey.replace(".", "\\.");
		regex = regex.replace("\\.##\\.", "(\\.|\\..*\\.)");
		regex = regex.replace("##\\.", ".*\\.");
		regex = regex.replace("#", VALID_PACK_REGULAR);
		return regex;
	}

	/**
	 * 判断类型是否是接口或者抽象类
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isInterfaceOrAbstract(Class type) {
		if (type == null) {
			throw new RuntimeException("type can't be null.");
		}
		if (type.isInterface()) {
			return true;
		} else if (Modifier.isAbstract(type.getModifiers())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从void setBeanName(Type instance)方法中解析出beanName
	 * 
	 * @param method
	 * @return
	 */
	public static String exactBeanName(Method method) {
		String methodname = method.getName();
		if (methodname.equalsIgnoreCase("set") || methodname.startsWith("set") == false) {
			return null;
		}
		String beanName = methodname.substring(3);
		beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
		return beanName;
	}

	/**
	 * 返回驼峰命名
	 * 
	 * @param name
	 * @return
	 */
	public static String camel(String name) {
		if (StringHelper.isBlankOrNull(name)) {
			return "";
		}
		StringBuffer b = new StringBuffer(name.length());
		StringTokenizer t = new StringTokenizer(name);
		b.append(t.nextToken());
		while (t.hasMoreTokens()) {
			String token = t.nextToken();
			b.append(token.substring(0, 1).toUpperCase());
			// replace spaces with camelCase
			b.append(token.substring(1));
		}
		return b.toString();
	}

	public static String camel(String name, String... strings) {
		StringBuffer b = new StringBuffer(name);
		for (String s : strings) {
			b.append(" ");
			b.append(s);
		}
		return camel(b.toString());
	}
}
