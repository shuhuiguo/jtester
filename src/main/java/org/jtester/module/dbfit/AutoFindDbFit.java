package org.jtester.module.dbfit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jtester.unitils.dbfit.DbFit;
import org.jtester.unitils.dbfit.DbFit.AUTO;
import org.jtester.utility.AnnotationUtils;
import org.jtester.utility.ResourceUtil;

/**
 * 用于自动查找dbfit文件
 * 
 * @author darui.wudr
 * 
 */
public class AutoFindDbFit {

	public static String[] autoFindClassWhen(Class<?> testClazz) {
		DbFit dbFit = AnnotationUtils.getClassLevelAnnotation(DbFit.class, testClazz);
		String[] whens = dbFit == null ? new String[0] : dbFit.when();
		if (dbFit != null && dbFit.auto() == AUTO.UN_AUTO) {
			return whens;
		}

		String when = dbFit == null ? DbFit.WHEN_CLAZZ_DEFAULT_WIKI : dbFit.whenClass();
		when = when.replace("${class}", testClazz.getSimpleName());

		boolean isFound = ResourceUtil.isExistsClassPathFile(testClazz, when);
		if (isFound == false) {
			return whens;
		}
		List<String> wikis = new ArrayList<String>();
		for (String wiki : whens) {
			wikis.add(wiki);
		}
		wikis.add(when);
		return wikis.toArray(new String[0]);
	}

	/**
	 * 查找test method的when文件
	 * 
	 * @param testClazz
	 * @param testMethod
	 * @return
	 */
	public static String[] autoFindMethodWhen(Class<?> testClazz, Method testMethod) {
		DbFit dbFit = testMethod.getAnnotation(DbFit.class);
		boolean isAutoFind = isAutoFind(testClazz, testMethod);
		String[] whens = dbFit == null ? new String[0] : dbFit.when();
		if (isAutoFind == false) {
			return whens;
		}

		String when = dbFit == null ? DbFit.WHEN_METHOD_DEFAULT_WIKI : dbFit.whenMethod();
		when = when.replace("${class}", testClazz.getSimpleName());
		when = when.replace("${method}", testMethod.getName());

		boolean isFound = ResourceUtil.isExistsClassPathFile(testClazz, when);
		if (isFound == false) {
			return whens;
		}

		List<String> wikis = new ArrayList<String>();
		for (String wiki : whens) {
			wikis.add(wiki);
		}
		wikis.add(when);
		return wikis.toArray(new String[0]);
	}

	/**
	 * 查找test method的then文件
	 * 
	 * @param testClazz
	 * @param testMethod
	 * @return
	 */
	public static String[] autoFindMethodThen(Class<?> testClazz, Method testMethod) {
		DbFit dbFit = testMethod.getAnnotation(DbFit.class);

		boolean isAutoFind = isAutoFind(testClazz, testMethod);
		String[] thens = dbFit == null ? new String[0] : dbFit.then();
		if (isAutoFind == false) {
			return thens;
		}
		String then = dbFit == null ? DbFit.THEN_METHOD_DEFAULT_WIKI : dbFit.thenMethod();
		then = then.replace("${class}", testClazz.getSimpleName());
		then = then.replace("${method}", testMethod.getName());

		boolean isFound = ResourceUtil.isExistsClassPathFile(testClazz, then);
		if (isFound == false) {
			return thens;
		}
		List<String> wikis = new ArrayList<String>();
		for (String wiki : thens) {
			wikis.add(wiki);
		}
		wikis.add(then);
		return wikis.toArray(new String[0]);
	}

	/**
	 * 判断一个测试方法根据规则自动查找wiki文件
	 * 
	 * @param testClazz
	 *            测试类
	 * @param testMethod
	 *            测试方法
	 * @return
	 */
	private static boolean isAutoFind(Class<?> testClazz, Method testMethod) {
		DbFit methodDbFit = testMethod.getAnnotation(DbFit.class);
		if (methodDbFit != null && methodDbFit.auto() != AUTO.DEFAULT) {
			return methodDbFit.auto() == AUTO.AUTO;
		}

		DbFit clazzDbFit = AnnotationUtils.getClassLevelAnnotation(DbFit.class, testClazz);
		if (clazzDbFit == null) {
			return true;
		} else {
			return !(clazzDbFit.auto() == AUTO.UN_AUTO);
		}
	}
}
