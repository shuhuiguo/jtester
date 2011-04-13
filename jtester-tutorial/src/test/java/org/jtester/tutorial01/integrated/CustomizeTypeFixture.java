package org.jtester.tutorial01.integrated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.fit.fixture.DtoPropertyFixture;
import org.jtester.fit.util.ParseArg;

public class CustomizeTypeFixture extends DtoPropertyFixture {

	public void testMethod_String(String value) {
		want.string(value).start("a");
		System.out.println("test string");
	}

	public void testMethod_Integer(int value) {
		want.number(value).isEqualTo(3);
	}

	public void testMethod_dto(CustomizedTypeDto dto) {
		want.string(dto.firstName).contains("a");
		want.number(dto.age).isEqualTo(34);
		want.array(dto.getAddresses()).sizeEq(2);
		want.map(dto.map).hasKeys("key1", "key2");
		want.collection(dto.myList).sizeGe(2);
	}

	public void testMethod_SingleValue(Map<String, String> map) {
		want.map(map).hasKeys("key1", "key2");
	}

	public static Map<String, String> parseMyMap(String str) {
		return parseMap(str);
	}

	public static class CustomizedTypeDto {
		String firstName;
		int age;

		private String[] addresses;
		Map<String, String> map = new HashMap<String, String>();
		private List<String> myList;

		public void parseMap(String value) {
			this.map = ParseArg.parseMap(value);
		}

		public void parseMyList(String value) {
			this.myList = parseList(value);
		}

		public String[] getAddresses() {
			return addresses;
		}
	}
}
