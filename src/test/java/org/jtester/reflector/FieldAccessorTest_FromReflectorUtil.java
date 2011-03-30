package org.jtester.reflector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.fortest.beans.Address;
import org.jtester.fortest.beans.Employee;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Test(groups = { "JTester" })
public class FieldAccessorTest_FromReflectorUtil extends JTester {

	public void setFieldValue() {
		Employee employee = new Employee();
		want.object(employee.getName()).isNull();
		FieldAccessor.setFieldValue(employee, "name", "my name");
		want.object(employee).propertyEq("name", "my name");
	}

	@Test(expectedExceptions = { Exception.class })
	public void setFieldValue_exception() {
		Employee employee = new Employee();
		want.object(employee.getName()).isNull();
		FieldAccessor.setFieldValue(employee, "name1", "my name");
	}

	@Test(expectedExceptions = { RuntimeException.class })
	public void setFieldValue_AssertError() {
		FieldAccessor.setFieldValue(null, "name1", "my name");
	}

	public void getFieldValue() {
		Employee employee = new Employee();
		employee.setName("test name");
		Object name = FieldAccessor.getFieldValue(employee, "name");
		want.object(name).clazIs(String.class);
		want.string(name.toString()).isEqualTo("test name");
	}

	@Test
	public void getFieldValue_exception() {
		try {
			Employee employee = new Employee();
			employee.setName("test name");
			FieldAccessor.getFieldValue(employee, "name1");
			want.fail();
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).start("No such field:");
		}
	}

	@Test(expectedExceptions = { RuntimeException.class })
	public void getFieldValue_AssertError() {
		FieldAccessor.getFieldValue(null, "name1");
	}

	@Test(expectedExceptions = { RuntimeException.class })
	public void getFieldValue_AssertError2() {
		FieldAccessor.getFieldValue(Employee.class, null, "name1");
	}

	@Test
	public void testGetArrayItemProperty() {
		List<?> values = PropertiesAccessor.getArrayItemProperty(
				Arrays.asList(new User("ddd", "eeee"), new User("ccc", "dddd")), "first");
		want.collection(values).reflectionEq(new String[] { "ddd", "ccc" });
	}

	@Test
	public void testGetArrayItemProperty_数组类型() {
		List<?> values = PropertiesAccessor.getArrayItemProperty(
				new User[] { new User("ddd", "eeee"), new User("ccc", "dddd") }, "first");
		want.collection(values).reflectionEq(new String[] { "ddd", "ccc" });
	}

	@Test
	public void testGetArrayItemProperty_单值() {
		List<?> values = PropertiesAccessor.getArrayItemProperty(new User("ddd", "eeee"), "first");
		want.collection(values).reflectionEq(new String[] { "ddd" });
	}

	@Test
	public void testGetArrayItemProperty_Map类型() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("first", "ddd");

		List<?> values = PropertiesAccessor.getArrayItemProperty(map, "first");
		want.collection(values).reflectionEq(new String[] { "ddd" });
	}

	@Test
	public void testGetArrayItemProperty_Map类型_集合() {
		List list = new ArrayList();
		for (int i = 0; i < 2; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("first", "ddd");
			list.add(map);
		}
		List<?> values = PropertiesAccessor.getArrayItemProperty(list, "first");
		want.collection(values).reflectionEq(new String[] { "ddd", "ddd" });
	}

	@Test
	public void testGetArrayItemProperties() {
		Object[][] values = PropertiesAccessor.getArrayItemProperties(
				Arrays.asList(new User("ddd", "eeee"), new User("ccc", "dddd")), new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" }, { "ccc", "dddd" } });
	}

	@Test
	public void testGetArrayItemProperties_数组类型() {
		Object[][] values = PropertiesAccessor.getArrayItemProperties(new User[] { new User("ddd", "eeee"),
				new User("ccc", "dddd") }, new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" }, { "ccc", "dddd" } });
	}

	@Test
	public void testGetArrayItemProperties_单值() {
		Object[][] values = PropertiesAccessor.getArrayItemProperties(new User("ddd", "eeee"),
				new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" } });
	}

	@Test
	public void testGetArrayItemProperties_Map类型() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("first", "ddd");
		map.put("last", "eeee");

		Object[][] values = PropertiesAccessor.getArrayItemProperties(map, new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" } });
	}

	@Test
	public void testGetArrayItemProperties_Map类型_集合() {
		List list = new ArrayList();
		for (int i = 0; i < 2; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("first", "ddd");
			map.put("last", "eeee");
			list.add(map);
		}
		Object[][] values = PropertiesAccessor.getArrayItemProperties(list, new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" }, { "ddd", "eeee" } });
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testGetProperty() {
		PropertiesAccessor.getProperty(null, "dd");
	}

	@Test
	public void testGetProperty_对象是Map() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wikiName", "eeee");
		String value = (String) PropertiesAccessor.getProperty(map, "wikiName");
		want.string(value).isEqualTo("eeee");

		value = (String) PropertiesAccessor.getProperty(map, "kkkk");
		want.string(value).isNull();
	}

	@Test
	public void testGetProperty_没有这个属性() {
		try {
			PropertiesAccessor.getProperty(item, "dde");
			want.fail();
		} catch (Exception e) {
			String error = e.getMessage();
			want.string(error).start("No such field:");
		}
	}

	ForReflectUtil item = new ForReflectUtil("first name", "last name");

	public void testGetProperty_Get方法可以取到值() {
		String first = (String) PropertiesAccessor.getProperty(item, "first");
		want.string(first).isEqualTo("first name");
	}

	public void testGetProperty_Get方法可以取到值_但方法在父类() {
		SubForReflectUtil item1 = new SubForReflectUtil("first name", "last name");
		String field = (String) PropertiesAccessor.getProperty(item1, "myName");
		want.string(field).isEqualTo("first name,last name");
	}

	public void testGetProperty_Is方法可以取到值() {
		boolean isMan = (Boolean) PropertiesAccessor.getProperty(item, "man");
		want.bool(isMan).is(true);
	}

	public void testGetProperty_只能通过直接取字段() {
		String field = (String) PropertiesAccessor.getProperty(item, "noGetMethod");
		want.string(field).isEqualTo("no get method field");
	}

	public void testGetProperty_Get方法有逻辑() {
		String field = (String) PropertiesAccessor.getProperty(item, "myName");
		want.string(field).isEqualTo("first name,last name");
	}

	public void testGetProperty_只能通过直接取字段_且字段在父类中() {
		SubForReflectUtil item1 = new SubForReflectUtil("first name", "last name");
		String field = (String) PropertiesAccessor.getProperty(item1, "noGetMethod");
		want.string(field).isEqualTo("no get method field");
	}

	public static class SubForReflectUtil extends ForReflectUtil {
		public SubForReflectUtil(String first, String last) {
			super(first, last);
		}
	}

	@Test
	public void testGetArrayOrItemProperty_单值对象_且属性值非集合() {
		Collection values = PropertiesAccessor.getArrayOrItemProperty(new User("ddd", "ddd"), "first");
		want.collection(values).sizeEq(1).reflectionEq(new String[] { "ddd" });
	}

	@Test
	public void testGetArrayOrItemProperty_单值对象_且属性值为集合() {
		User user = new User("ddd", "ddd");
		user.setAddresses(Arrays.asList(new Address("aaa"), new Address("bbb")));
		Collection values = PropertiesAccessor.getArrayOrItemProperty(user, "addresses");
		want.collection(values).sizeEq(2).reflectionEq(new Address[] { new Address("aaa"), new Address("bbb") });
	}
}
