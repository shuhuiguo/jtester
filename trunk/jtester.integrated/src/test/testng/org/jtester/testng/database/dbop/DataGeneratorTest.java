package org.jtester.testng.database.dbop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jtester.hamcrest.matcher.property.reflection.EqMode;
import org.jtester.module.database.IDatabase;
import org.jtester.testng.JTester;
import org.jtester.tools.datagen.AbstractDataSet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Test(groups = { "jtester", "database" })
public class DataGeneratorTest extends JTester implements IDatabase {

	@Test(dataProvider = "dataGenerator")
	public void testParseMapList(final Object input, Object expected) {
		AbstractDataSet ds = new EmptyDataSet();
		List<DataMap> maps = reflector.invoke(ds, "parseMapList", 5, new DataMap() {
			{
				this.put("key1", "value1");
				this.put("key2", input);
			}
		});
		want.collection(maps).sizeEq(5)
				.propertyEq("key1", new String[] { "value1", "value1", "value1", "value1", "value1" });
		want.collection(maps).propertyEq("key2", expected, EqMode.IGNORE_DEFAULTS);
	}

	@DataProvider
	public DataIterator dataGenerator() {
		return new DataIterator() {
			{
				data(1, new Integer[] { 1, 1, 1, 1, 1 });
				data(new Integer[] { 1, 2, 3 }, new Integer[] { 1, 2, 3, 3, 3 });
				data(new Integer[] { 1, 2, 3, 4, 5, 6 }, new Integer[] { 1, 2, 3, 4, 5 });
				data(DataGenerator.repeat(1, 2), new Integer[] { 1, 2, 1, 2, 1 });
				data(DataGenerator.random(Integer.class), new Integer[] { null, null, null, null, null });
				data(DataGenerator.increase(1, 1), new Integer[] { 1, 2, 3, 4, 5 });
			}
		};
	}

	@Test
	public void testInsert2() {
		db.table("tdd_user").clean().insert(5, new DataMap() {
			{
				this.put("id", DataGenerator.increase(100, 1));
				this.put("first_name", "wu");
				this.put("last_name", DataGenerator.random(String.class));
				this.put("post_code", DataGenerator.repeat("310012", "310000"));
				this.put("my_date", new Object[] { new Date(), "2011-09-06" });
			}
		}).commit();
	}

	@Test
	public void testInsert() {
		db.table("tdd_user").clean().insert(new DataSet() {
			{
				data(5, new DataMap() {
					{
						this.put("id", DataGenerator.increase(100, 1));
						this.put("first_name", "wu");
						this.put("last_name", DataGenerator.random(String.class));
						this.put("post_code", DataGenerator.repeat("310012", "310000"));
						this.put("my_date", new Object[] { new Date(), "2011-09-06" });
					}
				});
			}
		}).commit();
		db.table("tdd_user").query().propertyEq("first_name", new String[] { "wu", "wu", "wu", "wu", "wu" })
				.reflectionEqMap(new ArrayList() {
					{
						add(new DataMap() {
							{
								this.put("id", 100);
								this.put("post_code", "310012");
								this.put("my_date", null);
							}
						});
						add(new DataMap() {
							{
								this.put("id", 101);
								this.put("post_code", "310000");
								this.put("my_date", "2011-09-06");
							}
						});
						add(new DataMap() {
							{
								this.put("id", 102);
								this.put("post_code", "310012");
								this.put("my_date", "2011-09-06");
							}
						});
						add(new DataMap() {
							{
								this.put("id", 103);
								this.put("post_code", "310000");
								this.put("my_date", "2011-09-06");
							}
						});
						add(new DataMap() {
							{
								this.put("id", 104);
								this.put("post_code", "310012");
								this.put("my_date", "2011-09-06");
							}
						});
					}
				}, EqMode.IGNORE_DEFAULTS);
	}

	@Test
	public void testInsert_CountDataMap() {
		db.table("tdd_user").clean().insert(5, new DataMap() {
			{
				this.put("id", DataGenerator.increase(100, 1));
				this.put("first_name", "wu");
				this.put("last_name", DataGenerator.random(String.class));
				this.put("post_code", DataGenerator.repeat("310012", "310000"));
				this.put("my_date", new Object[] { new Date(), "2011-09-06" });
			}
		}).commit();

		db.table("tdd_user")
				.query()
				.propertyEq(new String[] { "id", "first_name", "post_code", "my_date" },
						new Object[][] { { 100, "wu", "310012", null },// <br>
								{ 101, "wu", "310000", "2011-09-06" },// <br>
								{ 102, "wu", "310012", "2011-09-06" },// <br>
								{ 103, "wu", "310000", "2011-09-06" },// <br>
								{ 104, "wu", "310012", "2011-09-06" } }, EqMode.IGNORE_DEFAULTS);
	}

	public void testInsertByDataGenerator() {
		db.table("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", new int[] { 100, 101 });
				this.put("first_name", new DataGenerator() {
					@Override
					public Object generate(int index) {
						return "myname_" + (index + 1);
					}
				});
			}
		});
		db.table("tdd_user").query().sizeEq(2).propertyEq("first_name", new String[] { "myname_1", "myname_2" });
	}

	public void testInsertByDataGenerator_UserFields() {
		db.table("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", new int[] { 100, 101 });
				this.put("first_name", new DataGenerator() {
					@Override
					public Object generate(int index) {
						return "myname_" + value("id");
					}
				});
			}
		});
		db.table("tdd_user").query().sizeEq(2).propertyEq("first_name", new String[] { "myname_100", "myname_101" });
	}

	@Test
	public void testData_JSON() {

	}
}
