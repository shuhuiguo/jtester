package org.jtester.hamcrest.iassert.common.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.fortest.beans.Employee;
import org.jtester.fortest.beans.Manager;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
@Test(groups = { "JTester" })
public class ReflectionAssertTest extends JTester {
	public void propertyMatch() {
		Manager manager = new Manager();
		manager.setName("I am darui.wu");
		want.object(manager).propertyMatch("name", the.string().contains("darui"));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void propertyMatch_AssertFail() {
		Manager manager = new Manager();
		manager.setName("I am darui.wu");
		want.object(manager).propertyMatch("name", the.string().contains("darui1"));
	}

	public void propertyEq() {
		Employee employee = new Employee();
		want.object(employee).propertyEq("name", null);
		employee.setName("my name");
		want.object(employee).propertyEq("name", "my name");
	}

	public void propertyMatch2() {
		Employee employee = new Employee();
		want.object(employee).propertyMatch("name", the.string().isNull());
		employee.setName("my name");
		want.object(employee).propertyMatch("name", the.string().isEqualTo("my name"));
		want.object(employee).propertyEq("name", "my name");
	}

	public void propertyMatch_forlist() {
		List<Employee> list = createEmployee();
		want.collection(list).sizeEq(4).propertyMatch("name", the.collection().hasItems("test name 1"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void propertyMatch_forlist_failure() {
		List<Employee> list = createEmployee();
		want.collection(list).sizeEq(4).propertyMatch("name", the.string().contains("name 1"));
	}

	public void propertyMatch_formap() {
		Map<String, String> map = createStringMap();
		want.map(map).propertyEq("key1", "test1").propertyEq("key2", "test2").propertyEq("key3", null);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void propertyMatch_formap_failure() {
		Map<String, String> map = createStringMap();
		want.map(map).propertyEq("key3", "test3");
	}

	public void propertyMatch_formap_list() {
		List<Map<String, String>> list = createMapList();
		want.collection(list).propertyMatch("key1", the.collection().hasItems("test1"));
	}

	@Test
	public void propertyMatchOne_forlist() {
		List<Employee> list = createEmployee();
		want.collection(list).sizeEq(4).hasPropertyMatch("name", the.string().contains("name 1"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void propertyMatchOne_forlist_failure() {
		List<Employee> list = createEmployee();
		want.collection(list).sizeEq(4).hasPropertyMatch("name", the.string().contains("name 5"));
	}

	@Test(timeOut = 1000, expectedExceptions = AssertionError.class)
	public void lenientEq_failure() {
		String[] expected = { "1", "2", "3", "4", "17", "18", "19", "20", "22", "23", "50" };
		String[] received = { "1", "3", "4", "2", "17", "18", "19", "20", "21", "22", "23" };

		want.array(received).lenientEq(expected);
	}

	@Test(timeOut = 1000)
	public void lenientEq() {
		String[] expected = { "1", "2", "3", "4", "17", "18", "19", "20", "22", "23", "50" };
		String[] received = { "1", "3", "4", "2", "17", "18", "19", "20", "50", "22", "23" };

		want.array(received).lenientEq(expected);
	}

	private static List<Employee> createEmployee() {
		List<Employee> list = new ArrayList<Employee>();
		for (int loop = 1; loop < 5; loop++) {
			Employee employee = new Employee();
			employee.setName("test name " + loop);
			list.add(employee);
		}
		return list;
	}

	private static Map<String, String> createStringMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "test1");
		map.put("key2", "test2");
		return map;
	}

	private static List<Map<String, String>> createMapList() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int loop = 1; loop < 5; loop++) {
			Map<String, String> map = createStringMap();
			list.add(map);
		}
		return list;
	}

	public void testEvaluatesToTheTheLogicalConjunctionOfManyOtherMatchers() {
		assertThat("good", allOf(equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good"), equalTo("good")));
	}

	@Test
	public void testPropertyEq() {
		Map<String, String> map = new HashMap<String, String>() {
			private static final long serialVersionUID = 1951222957450829884L;
			{
				put("name", "my name");
				put("age", "34");
			}
		};
		want.object(map).propertyEq(new String[] { "name", "age" }, new String[] { "my name", "34" });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testPropertyEq_Error() {
		Map<String, String> map = new HashMap<String, String>() {
			private static final long serialVersionUID = 1951222957450829884L;
			{
				put("name", "my name");
				put("age", "34");
			}
		};
		want.object(map).propertyEq(new String[] { "name", "age" }, new String[] { "my name", "35" });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testPropertyEq_Exception() {
		Map<String, String> map = new HashMap<String, String>() {
			private static final long serialVersionUID = 1951222957450829884L;
			{
				put("name", "my name");
				put("age", "34");
			}
		};
		want.object(map).propertyEq(new String[] { "name", "age" }, new String[] { "my name" });
	}

	@Test
	public void testAllPropertyMatch() {
		User[] users = new User[] { new User("adabcd", ""), new User("aaaabceee", "") };
		want.array(users).allPropertyMatch("first", the.string().contains("abc"));
	}

	@Test
	public void testPropertiesMatch() {
		List<User> users = Arrays.asList(new User("dfa123sdf", "abc"), new User("firs123tname", "abc"));
		want.collection(users).propertiesMatch(new String[] { "first", "last" },
				new Object[] { the.string().contains("123"), "abc" });
	}

	@Test
	public void testPropertiesMatch_单值() {
		User user = new User("df123asdf", "abc");
		want.object(user).propertiesMatch(new String[] { "first", "last" },
				new Object[] { the.string().contains("123"), "abc" });
	}
}
