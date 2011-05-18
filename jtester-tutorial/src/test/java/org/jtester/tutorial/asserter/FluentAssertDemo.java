package org.jtester.tutorial.asserter;

import java.util.Arrays;

import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.model.Customer;

import org.testng.annotations.Test;

@Test
public class FluentAssertDemo extends JTester {

	public void assertString() {
		String alibabaAddress = "杭州滨江网商路699号";

		want.string(alibabaAddress).start("杭州").end("699号").contains("网商路").regular(".*699.*");
	}

	public void assertObject() {
		Customer expectedItem = new Customer("darui.wu", "杭州滨江网商路699号");

		Customer actualItem = new Customer("darui.wu", "杭州滨江网商路699号");
		// want.object(actualItem).isEqualTo(expectedItem);
		want.object(actualItem).reflectionEq(expectedItem);
		want.object(actualItem).propertyEq("name", "darui.wu").propertyEq("address", the.string().start("159"));
	}

	@Test
	public void assertCollection() {
		want.collection(Arrays.asList(1, 2, 4)).sizeEq(3).hasItems(new int[] { 1, 4 });

		want.collection(Arrays.asList(newItem(), newItem())).propertyEq("username", new String[] { "darui", "darui" });

		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems("aaa");
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems("aaa", "ccc");
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems(Arrays.asList("aaa", "ccc"));
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems(new String[] { "aaa", "ccc" });

		want.collection(Arrays.asList(1, 2, 4)).hasItems(1, 4).sizeLt(4);
	}

	public void assertArray() {
		want.array(new String[] { "aaaa", "bbbb" }).hasItems("aaaa", "bbbb");
		want.array(new Customer[] { newItem(), newItem() }).propertyEq("username", new String[] { "darui", "darui" });
	}

	public static Customer newItem() {
		Customer user = new Customer("darui", "13900459999");
		return user;
	}
}
