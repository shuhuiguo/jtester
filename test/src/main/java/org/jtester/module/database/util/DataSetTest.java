package org.jtester.module.database.util;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import mockit.Mock;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.hamcrest.matcher.string.StringMode;
import org.jtester.module.database.environment.TableMeta.ColumnMeta;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings({ "serial", "rawtypes", "unchecked", "unused" })
@Transactional(TransactionMode.COMMIT)
@Test(groups = { "jtester", "database" })
public class DataSetTest extends JTester {

	@Test
	@DbFit(then = "data/DataSetTest/testInsertData.then.wiki")
	public void testInsertData() throws SQLException {
		fit.execute("delete from tdd_user", "commit");

		new DataSet("tdd_user").insert(new HashMap<String, Object>() {
			{
				this.put("id", "1");
				this.put("first_name", "darui.wu");
				this.put("my_date", new Date());
			}
		});
	}

	@Test(dataProvider = "testGetInsertCommandText_data")
	public void testGetInsertCommandText(Map<String, Object> data, String result) {
		DataSet ds = new DataSet("tdd_user");
		String text = reflector.invoke(ds, "getInsertCommandText", data);
		want.string(text).eqWithStripSpace(result);
	}

	@DataProvider
	Iterator testGetInsertCommandText_data() {
		return new DataIterator() {
			{
				this.data(new LinkedHashMap() {
				}, "insert into tdd_user() values()");
				this.data(new HashMap() {
					{
						this.put("id", 1);
					}
				}, "insert into tdd_user(id) values(?)");
				this.data(new LinkedHashMap() {
					{
						this.put("id", 1);
						this.put("first_name", "darui.wu");
					}
				}, "insert into tdd_user(id,first_name) values(?,?)");
			}
		};
	}

	@Test
	public void testInsert_NotSetColumns() {
		try {
			new DataSet("tdd_user").insert(new Object[] { 1, "darui.wu" });
			want.fail();
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("please specify column names first");
		}
	}

	@Test
	public void testInsert_ColumnSizeNotMatch() {
		try {
			new DataSet("tdd_user").columns(new String[] { "id" }).insert(new Object[] { 1, "darui.wu" });
			want.fail();
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("size() must equal to");
		}
	}

	@Test
	public void testAdd() {
		db.table("tdd_user").clean().insert(new HashMap() {
			{
				this.put("id", 3);
				this.put("first_name", "darui.wu");
			}
		}).columns(new String[] { "id", "first_name" }).insert(new Object[] { 1, "name1" }).insert(2, "darui.wu");
		db.querySingle("select count(*) from tdd_user").isEqualTo(3L);
	}

	public void testInsert_NoSuchColumn() throws SQLException {
		try {
			new DataSet("tdd_user").insert(new HashMap<String, Object>() {
				{
					this.put("no_column", "darui.wu");
					this.put("my_date", new Date());
				}
			});
			want.fail();
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("no_column");
		}
	}

	public void testInsert_BadType() throws SQLException {
		try {
			new DataSet("tdd_user").insert(new HashMap<String, Object>() {
				{
					this.put("first_name", "darui.wu");
					this.put("my_date", "2011-08-19ss");
				}
			});
			want.fail();
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("2011-08-19ss");
		}
	}

	public void testInsert_DataIterator() {
		new DataSet("tdd_user").clean().columns("id", "first_name").insert(new DataIterator() {
			{
				this.data(1, "darui.wu");
				this.data(2, "data.iterator");
			}
		});
		db.queryToList("select * from tdd_user").sizeEq(2)
				.propertyEq("first_name", new String[] { "darui.wu", "data.iterator" });
	}

	public void testInsert_DuplicateKey() {
		try {
			new DataSet("tdd_user").clean().columns("id", "first_name").insert(new DataIterator() {
				{
					this.data(1, "darui.wu");
					this.data(1, "data.iterator");
				}
			});
			want.fail();
		} catch (Exception e) {
			String message = ExceptionWrapper.toString(e);
			want.string(message).contains("duplicate entry", StringMode.IgnoreCase);
		}
	}

	public void testInsert_CheckFillData() {
		new MockUp<ColumnMeta>() {
			@Mock
			public boolean isNullable() {
				return false;
			}
		};
		new DataSet("tdd_user").clean().columns("id", "first_name").insert(new DataIterator() {
			{
				this.data(1, "darui.wu");
				this.data(2, "data.iterator");
			}
		});
		db.commit().query("tdd_user").reflectionEqMap(toList(new HashMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui.wu");
				this.put("post_code", "jteste");
				this.put("address_id", 0);
				this.put("last_name", "jtester");
				this.put("sarary", 0.0);
			}
		}, new HashMap() {
			{
				this.put("id", 2);
				this.put("first_name", "data.iterator");
				this.put("post_code", "jteste");
				this.put("address_id", 0);
				this.put("last_name", "jtester");
				this.put("sarary", 0.0);
			}
		}));
	}
}
