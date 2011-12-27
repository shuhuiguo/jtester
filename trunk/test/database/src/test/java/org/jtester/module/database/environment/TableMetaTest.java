package org.jtester.module.database.environment;

import mockit.Mock;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.beans.DataIterator;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("unused")
public class TableMetaTest implements IAssertion, IDatabase {

	@Test
	@DataFrom("dataTruncate")
	public void testTruncateString(String input, String expected) {
		TableMeta meta = reflector.newInstance(TableMeta.class);
		new MockUp<TableMeta>() {
			@Mock
			public int getCloumnSize(String column) {
				want.string(column).isEqualTo("columnName");
				return 5;
			}
		};
		String value = meta.truncateString("columnName", input);
		want.object(value).isEqualTo(expected);
	}

	public static DataIterator dataTruncate() {
		return new DataIterator() {
			{
				data("123456", "12345");
				data("123", "123");
				data("12345", "12345");
			}
		};
	}
}
