package org.jtester.hamcrest.iassert.object.impl;

import java.util.ArrayList;
import java.util.List;

import org.jtester.testng.JTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
public class ObjectContainerAssertTest_ItemMatch extends JTester {
	private List<String> list = new ArrayList<String>();

	@BeforeMethod
	public void setup() {
		list.clear();
		list.add("test.hello.one");
		list.add("test.hello.two");
		list.add("test.hello.three");
	}

	public void allItemMatch() {
		want.bool("test.hello.three".matches(".*hello.*")).is(true);

		want.collection(list).sizeIs(3).matchAllItems("test.*", ".*hello.*");
	}

	public void allItemMatch_2() {
		want.collection(list).sizeIs(3).matchAllItems(".*hello.*");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void allItemMatch_3() {
		want.collection(list).sizeIs(3).matchAllItems(".*hello.*", "test1.*");
	}

	public void hasItemMatch() {
		want.collection(list).sizeIs(3).matchAnyItems(".*one", ".*two");
	}

	public void hasItemMatch_2() {
		want.collection(list).sizeIs(3).matchAnyItems(".*three");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItemMatch_3() {
		want.collection(list).sizeIs(3).matchAnyItems(".*four");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItemMatch_4() {
		want.collection(list).sizeIs(3).matchAnyItems("test1", ".*four");
	}

	@Test
	public void hasItemMatch_arraytest1() {
		want.array(new String[] { "hello.one", "hello.two" }).sizeIs(2).matchAnyItems(".*one", ".*two");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItemMatch_arraytest2() {
		want.array(new String[] { "hello.one", "hello.two" }).sizeIs(2).matchAnyItems("test1", ".*four");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void allItemMatch_arraytest1() {
		want.array(new String[] { "hello.one", "heollo.two" }).sizeIs(2).matchAllItems("hello.*");
	}
	
	@Test
	public void allItemMatch_arraytest3() {
		want.array(new String[] { "hello.one", "hello.two" }).sizeIs(2).matchAllItems("hello.*");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void allItemMatch_arraytest2() {
		want.array(new String[] { "hello.one", "hello.two" }).sizeIs(2).matchAllItems(".*one");
	}
}
