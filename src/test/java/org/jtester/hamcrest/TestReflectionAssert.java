package org.jtester.hamcrest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jtester.fortest.beans.Address;
import org.jtester.fortest.beans.User;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.testng.JTester;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class TestReflectionAssert extends JTester {
	@Test(expectedExceptions = { AssertionError.class })
	public void test1() {
		User user1 = new User(1, "John", "Doe");
		User user2 = new User(1, "John", "Doe");
		Assert.assertEquals(user1, user2);
	}

	@Test
	public void test2() {
		User user1 = new User(1, "John", "Doe");
		User user2 = new User(1, "John", "Doe");
		want.object(user1).reflectionEq(user2);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void test3() {
		User user1 = new User(1, "John", "Doe");
		User user2 = new User(1, "John", "Doe1");
		want.object(user1).reflectionEq(user2);
	}

	@Test
	public void test4() {
		want.object(1).reflectionEq(1L);

		List<Double> myList = new ArrayList<Double>();
		myList.add(1.0);
		myList.add(2.0);
		want.object(myList).reflectionEq(Arrays.asList(1, 2));

	}

	@Test
	public void test5() {
		List<Integer> myList = Arrays.asList(3, 2, 1);
		want.object(myList).reflectionEq(Arrays.asList(1, 2, 3), ReflectionComparatorMode.LENIENT_ORDER);

		User actualUser = new User("John", "Doe", new Address("First street", "12", "Brussels"));
		User expectedUser = new User("John", null, new Address("First street", null, null));
		// assertReflectionEquals(expectedUser, actualUser, IGNORE_DEFAULTS);
		want.object(actualUser).reflectionEq(expectedUser, ReflectionComparatorMode.IGNORE_DEFAULTS);
	}

	@Test
	public void test6() {
		Date actualDate = new Date(44444);
		Date expectedDate = new Date();
		// assertReflectionEquals(expectedDate, actualDate, LENIENT_DATES);
		want.object(actualDate).reflectionEq(expectedDate, ReflectionComparatorMode.LENIENT_DATES);

	}

	@Test
	public void testLenientAssert() {
		List<Integer> myList = Arrays.asList(3, 2, 1);
		// assertLenientEquals(Arrays.asList(1, 2, 3), myList);
		want.collection(myList).lenientEq(Arrays.asList(1, 2, 3));

		// assertLenientEquals(null, "any"); // Succeeds
		want.object("any").lenientEq(null);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testLenientAssert2() {
		// assertLenientEquals("any", null); // Fails
		want.object(null).lenientEq("any");
	}

	@Test
	public void test7() {
		User user1 = new User(1, "John", "Doe");
		User user2 = new User("John", "Doe", new Address("First street", "", ""));

		want.object(user1).propertyEq("id", 1);
		want.object(user2).propertyEq("address.street", "First street");

		want.object(new User[] { new User("Jane"), new User("John") }).reflectionEq(
				Arrays.asList(new User("John"), new User("Jane")), ReflectionComparatorMode.LENIENT_ORDER);

		want.object(Arrays.asList(new User("John"), new User("Jane"))).reflectionEq(
				new User[] { new User("Jane"), new User("John") }, ReflectionComparatorMode.LENIENT_ORDER);

		want.array(new User[] { new User("Jane"), new User("John") }).reflectionEq(
				Arrays.asList(new User("John"), new User("Jane")), ReflectionComparatorMode.LENIENT_ORDER);

		want.collection(Arrays.asList(new User("John"), new User("Jane"))).reflectionEq(
				new User[] { new User("Jane"), new User("John") }, ReflectionComparatorMode.LENIENT_ORDER);

	}

	@Test(expectedExceptions = { AssertionError.class })
	public void test8() {
		User user = new User(1, "John", "Doe");
		want.object(user).propertyEq("id", 2);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void test9() {
		User user = new User("John", "Doe", new Address("First street", "", ""));
		want.object(user).propertyEq("address.street", "First street1");
	}

	@Test
	public void test10() {
		want.array(new User[] { new User("Jane", "Doe"), new User("John", "Doe") }).propertyEq("first",
				Arrays.asList("Jane", "John"));
	}
}
