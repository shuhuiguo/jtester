package org.jtester.hamcrest.iassert.object.impl;

import java.util.Arrays;

import org.jtester.hamcrest.reflection.ReflectionComparatorMode;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class JSONAssertTest extends JTester {
	String json_arr = "[{\"gmtModified\":\"2009-06-19\",\"employeeId\":\"1234\",\"loginId\":\"system\",\"phone\":null,\"gmtCreate\":\"2008-02-26\",\"status\":\"enable\",\"isDeleted\":\"n\",\"password\":\"mhmW78lxgfCu4YMhqjs7Eg==\",\"gmtLastPasswordChanged\":\"2009-06-19\",\"creator\":\"sys\",\"homepageUrl\":null,\"modifier\":\"esb\",\"mobilePhone\":null,\"email\":\"system@b2btest.com\",\"name\":\"system\",\"gender\":\"M\",\"language\":\"zh_CN\",\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"wrreer\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enable\",\"isDeleted\":\"n\",\"password\":\"reerer\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"asdsda\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"wrreer\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enable\",\"isDeleted\":\"n\",\"password\":\"reerer\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"asdsda\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"wrreer\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enable\",\"isDeleted\":\"n\",\"password\":\"reerer\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"asdsda\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"wrreer\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enable\",\"isDeleted\":\"n\",\"password\":\"reerer\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"asdsda\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"wrreer\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enable\",\"isDeleted\":\"n\",\"password\":\"reerer\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"asdsda\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"mooon\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enbale\",\"isDeleted\":\"n\",\"password\":\"11222132\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"light\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"mooon\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enbale\",\"isDeleted\":\"n\",\"password\":\"11222132\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"light\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"mooon\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enbale\",\"isDeleted\":\"n\",\"password\":\"11222132\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"light\",\"gender\":\"m\",\"language\":null,\"signature\":null},{\"gmtModified\":\"2009-08-14\",\"employeeId\":null,\"loginId\":\"mooon\",\"phone\":null,\"gmtCreate\":\"2009-08-14\",\"status\":\"enbale\",\"isDeleted\":\"n\",\"password\":\"11222132\",\"gmtLastPasswordChanged\":null,\"creator\":\"demo\",\"homepageUrl\":null,\"modifier\":\"demo\",\"mobilePhone\":null,\"email\":null,\"name\":\"light\",\"gender\":\"m\",\"language\":null,\"signature\":null}]";

	@Test
	public void testIsArray() {
		want.json(json_arr).isArray();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testIsSimple_failure() {
		want.json(json_arr).isSimple();
	}

	@Test
	public void testJsonSizeIs() {
		want.json(json_arr).isArray().jsonSizeIs(10);
	}

	@Test
	public void testKeyValueMatch() {
		want.json(json_arr).isArray().keyValueMatch("employeeId",
				the.string().anyOf(the.string().isEqualTo("1234"), the.string().isNull()));
	}

	String json_obj = "{\"gmtModified\":\"2009-06-19\",\"employeeId\":\"1234\",\"loginId\":\"system\",\"phone\":null,\"gmtCreate\":\"2008-02-26\"}";

	@Test
	public void testHasKeyValues() {
		want.json(json_obj).isSimple().hasKeyValues("employeeId", "1234", "phone", null);
	}

	@Test
	public void testKeyValueEq() {
		want.json(json_arr).isArray().keyValueEq("employeeId",
				Arrays.asList("1234", null, null, null, null, null, null, null, null, null),
				ReflectionComparatorMode.IGNORE_DEFAULTS);
	}

	@Test
	public void testKeyValueEq_array() { 
		want.json(json_arr).isArray().keyValueEq("employeeId",
				new String[] { "1234", null, null, null, null, null, null, null, null, null },
				ReflectionComparatorMode.IGNORE_DEFAULTS);
	}
}
