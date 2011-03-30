package org.jtester.module.spring;

import org.jtester.testng.JTester;
import org.jtester.utility.ClazzUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class ImplementorFinderTest extends JTester {

	public static class ImplementorFinderEx extends ImplementorFinder {
		// ImplementorFinderEx(final Class<?> beanClazz, final List<BeanMap>
		// beanMapping) {
		// super(beanClazz, beanMapping);
		// }

		public static String replace(String interfaceKey, String implementKey, String interfaceClass) {
			return ImplementorFinder.replace(interfaceKey, implementKey, interfaceClass);
		}

		public static String[] splitIntfClazzByExpression(String intfClazz, String[] exps) {
			return ImplementorFinder.splitIntfClazzByExpression(intfClazz, exps);
		}

		public static boolean regMatch(String interfaceKey, String interfaceClass) {
			String regex = ClazzUtil.getPackageRegex(interfaceKey);
			return interfaceClass.matches(regex);
		}
	}

	@Test
	public void testSplitIntfClazzByExpression() {
		String[] exps = { "org.jtester.", ".*Service" };
		String[] packs = ImplementorFinderEx.splitIntfClazzByExpression("org.jtester.service.UserService", exps);
		want.array(packs).sizeEq(3).reflectionEq(new String[] { "org.jtester.", "service", ".UserService" });
	}

	@Test(dataProvider = "testRegMatch_dataPorvider")
	public void testRegMatch(String interfaceKey, String interfaceClass, boolean match) {
		boolean ret = ImplementorFinderEx.regMatch(interfaceKey, interfaceClass);
		want.bool(ret).isEqualTo(match);
	}

	@DataProvider
	public Object[][] testRegMatch_dataPorvider() {
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

	@Test(dataProvider = "testReplace_dataProvider")
	public void testReplace(String intfExpress, String implExpress, String intfClazz, String implClazz) {
		String implementClass = ImplementorFinderEx.replace(intfExpress, implExpress, intfClazz);
		want.string(implementClass).isEqualTo(implClazz);
	}

	@DataProvider
	public Object[][] testReplace_dataProvider() {
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

}
