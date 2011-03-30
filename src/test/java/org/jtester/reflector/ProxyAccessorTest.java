package org.jtester.reflector;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jtester.reflector.model.TestException;
import org.jtester.reflector.model.TestObject;
import org.jtester.testng.JTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes" })
@Test(groups = "jtester")
public class ProxyAccessorTest extends JTester {

	private TestObject test;

	@BeforeMethod
	public void setUp() throws Exception {
		test = new TestObject();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		test = null;
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorNoMatchingTarget1() {
		ProxyAccessor.createAccessor(TestObjectAccess.class, new Object());
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorNoMatchingTarget2() {
		ProxyAccessor.createAccessor(TestWrong.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnIs1() {
		ProxyAccessor.createAccessor(TestWrongOnIs1.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnIs2() {
		ProxyAccessor.createAccessor(TestWrongOnIs2.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnIs3() {
		ProxyAccessor.createAccessor(TestWrongOnIs3.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnGet1() {
		ProxyAccessor.createAccessor(TestWrongOnGet1.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnGet2() {
		ProxyAccessor.createAccessor(TestWrongOnGet2.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnGet3() {
		ProxyAccessor.createAccessor(TestWrongOnGet3.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnSet1() {
		ProxyAccessor.createAccessor(TestWrongOnSet1.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnSet2() {
		ProxyAccessor.createAccessor(TestWrongOnSet2.class, test);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testCreateAccessorWrongOnSet3() {
		ProxyAccessor.createAccessor(TestWrongOnSet3.class, test);
	}

	@Test
	public void testCreateAccessorClassOfTObject() {
		TestObjectAccess access = ProxyAccessor.createAccessor(TestObjectAccess.class, test);
		want.number(access.getPrivate()).isEqualTo(26071973);
		access.setPrivate(26072007);
		want.number(access.getPrivate()).isEqualTo(26072007);
		want.bool(access.isKind().booleanValue()).is(true);
		want.bool(access.isRace()).is(false);
		want.bool(access.isMagic()).is(true);

		access.setMagic(false);
		want.bool(access.isMagic()).is(false);
		want.number(access.getPrivate()).isEqualTo(26072007);
	}

	@Test
	public void testCreateAccessorClassOfTObjectMapOfStringString() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("getBirthDate", "getPrivate");
		map.put("shutUp", "throwingMethod");
		map.put("getNonStandard", "getANonStandardJavaBeanStyleField");
		map.put("aNonStandardJavaBeanStyleField", "ANonStandardJavaBeanStyleField");

		TestRenameObjectAccess access = ProxyAccessor.createAccessor(TestRenameObjectAccess.class, test, map);

		want.number(access.getBirthDate()).isEqualTo(26071973);
		try {
			access.shutUp();
			want.fail("Expected TestException");
		} catch (Exception e) {
			want.object(e).clazIs(TestException.class);
		}
		want.number(access.getNonStandard()).isEqualTo(-1);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testWrongReturnType() {
		ProxyAccessor.createAccessor(TestWrongReturnType.class, new WrongReturnTypeObject());
	}

	@Test
	public void testWrongReturnTypeButRightField() {
		String date = "26/07/73";
		TestWrongReturnType access = ProxyAccessor.createAccessor(TestWrongReturnType.class,
				new WrongReturnTypeButRightFieldObject(date));
		want.string(access.getDate()).same(date);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCheckInterface1() {
		CheckInterface check = ProxyAccessor.createAccessor(CheckInterface.class, ProxyAccessor.class);
		check.checkInterface(TestObject.class);
	}

	@Test
	public void testCheckInterface2() {
		CheckInterface check = ProxyAccessor.createAccessor(CheckInterface.class, ProxyAccessor.class);
		check.checkInterface(TestObjectAccess.class);
	}

	private interface TestObjectAccess extends Super {

		public boolean isMagic();

		public boolean getMagic();

		public boolean isRace();

		public void setMagic(boolean magic);

		public int getPrivate();

		public int getAPrivate();
	}

	private interface TestWrong {

		boolean izWrong();
	}

	private interface TestWrongOnIs1 {

		boolean isWrong();
	}

	private interface TestWrongOnIs2 {

		boolean isWrong(int i);
	}

	private interface TestWrongOnIs3 {

		int isWrong();
	}

	private interface TestWrongOnGet1 {

		void getWrong();
	}

	private interface TestWrongOnGet2 {

		double getWrong();
	}

	private interface TestWrongOnGet3 {

		int getWrong(int i);
	}

	private interface TestWrongOnSet1 {

		void setWrong();
	}

	private interface TestWrongOnSet2 {

		double setWrong(int i);
	}

	private interface TestWrongOnSet3 {

		void setWrong(double i);
	}

	private interface TestWrongReturnType {

		String getDate();
	}

	private interface TestRenameObjectAccess {

		public int getBirthDate();

		public void shutUp() throws TestException;

		public int getNonStandard();
	}

	private interface CheckInterface {

		public void checkInterface(Class type);
	}

	private interface Super {

		int setPrivate(int newValue);

		Boolean isKind();
	}

	private class WrongReturnTypeObject {
		@SuppressWarnings("unused")
		private Date getDate() {
			return new Date();
		}
	}

	@SuppressWarnings("unused")
	private class WrongReturnTypeButRightFieldObject extends WrongReturnTypeObject {

		private String date;

		private WrongReturnTypeButRightFieldObject(String date) {
			this.date = date;
		}

		private Date getDate() {
			return null;
		}
	}
}
