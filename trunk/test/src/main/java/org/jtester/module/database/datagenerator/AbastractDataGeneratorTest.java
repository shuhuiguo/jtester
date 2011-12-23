package org.jtester.module.database.datagenerator;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;

import org.jtester.beans.AbstractDataSet;
import org.jtester.beans.DataMap;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "serial", "unused", "rawtypes", "unchecked" })
@Test(groups = { "jtester", "database" })
public class AbastractDataGeneratorTest extends JTester {
	@Test
	public void testValue() {
		final List actual = new ArrayList();
		new MockUp<AbstractDataSet>() {
			AbstractDataSet it;

			@Mock
			public void insert(String table) {
				List list = reflector.getField(it, "datas");
				actual.addAll(list);
			}
		};
		db.table("tdd_user").insert(2, new DataMap() {
			{
				this.put("id", new String[] { "163", "sohu" });
				this.put("first_name", new DataGenerator() {
					@Override
					public Object generate(int index) {
						return "darui.wu@" + value("id") + ".com";
					}
				});
			}
		});
		want.collection(actual).sizeEq(2)
				.propertyEq("first_name", new String[] { "darui.wu@163.com", "darui.wu@sohu.com" });
	}
}
