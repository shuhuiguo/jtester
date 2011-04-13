package org.jtester.tutorial01.asserter;

import java.util.Arrays;

import org.jtester.testng.JTester;
import org.jtester.tutorial01.beans.PhoneItem;
import org.testng.annotations.Test;

@Test
public class FluentAssertDemo extends JTester {

	public void assertString() {
		String alibabaAddress = "中国浙江杭州滨江区网商路699号";

		want.string(alibabaAddress).start("中国").end("号").contains("杭州").regular(".*699.*");
	}

	public void assertObject() {
		PhoneItem expectedItem = new PhoneItem("darui.wu", "159xxxxxxxx");

		PhoneItem actualItem = new PhoneItem("darui.wu", "159xxxxxxxx");
		// want.object(actualItem).isEqualTo(expectedItem);
		want.object(actualItem).reflectionEq(expectedItem);
		want.object(actualItem).propertyEq("username", "darui.wu").propertyEq("mobile", the.string().start("159"));
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
		want.array(new PhoneItem[] { newItem(), newItem() }).propertyEq("username", new String[] { "darui", "darui" });
	}

	public static PhoneItem newItem() {
		PhoneItem user = new PhoneItem("darui", "13900459999");
		return user;
	}
}
