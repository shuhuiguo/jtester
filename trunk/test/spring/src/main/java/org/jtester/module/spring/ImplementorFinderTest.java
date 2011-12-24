package org.jtester.module.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mockit.Mock;

import org.jtester.IAssertion;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.exception.FindBeanImplClassException;
import org.jtester.helper.ClazzHelper;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings({ "unused", "rawtypes" })
public class ImplementorFinderTest implements IAssertion {

	public static class ImplementorFinderEx extends ImplementorFinder {

		public static String replace(String interfaceKey, String implementKey, String interfaceClass) {
			return ImplementorFinder.replace(interfaceKey, implementKey, interfaceClass);
		}

		public static String[] splitIntfClazzByExpression(String intfClazz, String[] exps) {
			return ImplementorFinder.splitIntfClazzByExpression(intfClazz, exps);
		}

		public static boolean regMatch(String interfaceKey, String interfaceClass) {
			String regex = ClazzHelper.getPackageRegex(interfaceKey);
			return interfaceClass.matches(regex);
		}
	}

	@Test
	public void testSplitIntfClazzByExpression() {
		String[] exps = { "org.jtester.", ".*Service" };
		String[] packs = ImplementorFinderEx.splitIntfClazzByExpression("org.jtester.service.UserService", exps);
		want.array(packs).sizeEq(3).reflectionEq(new String[] { "org.jtester.", "service", ".UserService" });
	}

	@Test
	@DataFrom("testRegMatch_dataPorvider")
	public void testRegMatch(String interfaceKey, String interfaceClass, boolean match) {
		boolean ret = ImplementorFinderEx.regMatch(interfaceKey, interfaceClass);
		want.bool(ret).isEqualTo(match);
	}

	public static Object[][] testRegMatch_dataPorvider() {
		return new Object[][] { { "org.jtester.**.service.*", "org.jtester.service.UserService", true }, /** <br> */
		{ "org.jtester.**.*", "org.jtester.service.UserService", true }, /** <br> */
		{ "org.jtester1.**.*", "org.jtester.service.UserService", false }, /** <br> */
		{ "org.jtester.**.service.*", "org.jtester.my.service.UserService", true }, /** <br> */
		{ "**.jtester.**.*", "org.jtester.my.service.UserService", true }, /** <br> */
		{ "**.jtester1.**.*", "org.jtester.my.service.UserService", false }, /** <br> */
		{ "**.jtester.**.*Service", "org.jtester.my.service.UserService", true }, /** <br> */
		{ "**.jtester.**.User*", "org.jtester.my.service.UserService", true } /** <br> */
		};
	}

	@Test
	@DataFrom("testReplace_dataProvider")
	public void testReplace(String intfExpress, String implExpress, String intfClazz, String implClazz) {
		String implementClass = ImplementorFinderEx.replace(intfExpress, implExpress, intfClazz);
		want.string(implementClass).isEqualTo(implClazz);
	}

	public static Object[][] testReplace_dataProvider() {
		return new Object[][] {
				{ "org.jtester.**.*", "org.jtester.**.*Impl", "org.jtester.service.UserService",
						"org.jtester.service.UserServiceImpl" },
				/** <br> */
				{ "org.jtester.**.*", "org.jtester.**.impl.*Impl", "org.jtester.service.UserService",
						"org.jtester.service.impl.UserServiceImpl" },
				/** <br> */
				{ "**.*", "**.impl.*Impl", "org.jtester.service.UserService",
						"org.jtester.service.impl.UserServiceImpl" },
				/** <br> */
				{ "**.*Dao", "**.impl.*DaoImpl", "org.jtester.service.UserDao", "org.jtester.service.impl.UserDaoImpl" } };
	}

	/**
	 * 测试class名称的大小写有差异时，查找实现抛出的是错误NoClassDefFoundError，
	 * 而不是异常NoClassDefFoundException的case
	 */
	@Test
	public void testGetImplClass() {
		BeanMap beanMap = ICharacterDiff.class.getAnnotation(BeanMap.class);
		try {
			reflector.invokeStatic(ImplementorFinder.class, "getImplClass", ICharacterDiff.class,
					Arrays.asList(beanMap));
			want.fail();
		} catch (Exception e) {
			String err = e.getMessage();
			want.string(err).contains("FindBeanImplClassException")
					.contains("ImplementorFinderTest$ICharacterDiffImpl");
		}
	}

	@BeanMap(intf = "**.*", impl = "**.*Impl")
	public static interface ICharacterDiff {

	}

	public static class ICharacterdiffImpl implements ICharacterDiff {

	}

	public static class NotPublicConstruction implements ICharacterDiff {
		private NotPublicConstruction() {

		}
	}

	public static class NoHasDefaultConstruction implements ICharacterDiff {
		public NoHasDefaultConstruction(String d) {

		}
	}

	/**
	 * 测试实现类只要有默认的构造函数，而不敢是否是private还是public的，都可以通过spring注册
	 * 
	 * @throws FindBeanImplClassException
	 */
	@Test
	public void testFindImplClazz_() throws FindBeanImplClassException {
		new MockUp<ImplementorFinder>() {

			@Mock
			public Class getImplClass(final Class beanClazz, final List<BeanMap> beanMapping) {
				return NotPublicConstruction.class;
			}
		};
		Class clazImpl = ImplementorFinder.findImplClazz(ImplementorFinder.class, "tet", ICharacterDiff.class,
				new ArrayList<BeanMap>());
		want.object(clazImpl).isEqualTo(NotPublicConstruction.class);
	}

	/**
	 * 测试实现类没有默认的构造函数，无法通过spring注册
	 * 
	 * @throws FindBeanImplClassException
	 */
	@Test
	public void testFindImplClazz_NoHasDefaultConstruction() throws FindBeanImplClassException {
		new MockUp<ImplementorFinder>() {

			@Mock
			public Class getImplClass(final Class beanClazz, final List<BeanMap> beanMapping) {
				return NoHasDefaultConstruction.class;
			}
		};
		try {
			ImplementorFinder.findImplClazz(ImplementorFinder.class, "tet", ICharacterDiff.class,
					new ArrayList<BeanMap>());
			want.fail();
		} catch (Exception e) {
			want.object(e).clazIs(FindBeanImplClassException.class);
		}
	}

}
