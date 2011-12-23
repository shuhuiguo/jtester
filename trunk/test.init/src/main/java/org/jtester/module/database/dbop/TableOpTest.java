package org.jtester.module.database.dbop;

import java.sql.SQLException;
import java.util.Date;

import mockit.Mock;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.Transactional;
import org.jtester.annotations.Transactional.TransactionMode;
import org.jtester.beans.DataMap;
import org.jtester.exception.ExceptionWrapper;
import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.matcher.string.StringMode;
import org.jtester.module.database.environment.TableMeta.ColumnMeta;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "serial", "unchecked", "unused" })
@Transactional(TransactionMode.COMMIT)
@Test(groups = { "jtester", "database" })
public class TableOpTest extends JTester {

	@Test
	@DbFit(then = "data/TableOpTest/testInsertData.then.wiki")
	public void testInsertData() throws SQLException {
		db.table("tdd_user").clean().insert(new DataMap() {
			{
				this.put("id", "1");
				this.put("first_name", "darui.wu");
				this.put("my_date", new Date());
			}
		});
	}

	@Test
	public void testInsert_ErrorColumnName() {
		try {
			db.table("tdd_user").insert(new DataSet() {
				{
					this.data("{'id':1,'my_name':'darui.wu'}");
				}
			});
			want.fail();
		} catch (Exception e) {
			String message = ExceptionWrapper.toString(e);
			want.string(message).contains("can't find column[my_name] field in table");
		}
	}

	@Test
	public void testAdd() {
		db.table("tdd_user").clean().insert(new DataSet() {
			{
				data("{'id':1, 'first_name':'name1'}");
				data(2, new DataMap() {
					{
						this.put("id", new int[] { 2, 3 });
						this.put("first_name", new String[] { "darui.wu", "jobs.he" });
					}
				});
			}
		});
		db.queryAsPoJo("select count(*) from tdd_user", Integer.class).isEqualTo(3L);
	}

	public void testInsert_NoSuchColumn() throws SQLException {
		try {
			new TableOp("tdd_user").insert(new DataMap() {
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
			new TableOp("tdd_user").insert(new DataMap() {
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
		new TableOp("tdd_user").clean().insert(2, new DataMap() {
			{
				this.put("id", new Integer[] { 1, 2 });
				this.put("first_name", new Object[] { "darui.wu", "data.iterator" });
			}
		});
		db.query("select * from tdd_user").sizeEq(2)
				.propertyEq("first_name", new String[] { "darui.wu", "data.iterator" });
	}

	public void testInsert_DataIterator_1() {
		db.table("tdd_user").clean().insert(new DataMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui.wu");
			}
		}, new DataMap() {
			{
				this.put("id", 2);
				this.put("first_name", "data.iterator");
			}
		});
		db.query("select * from tdd_user").sizeEq(2)
				.propertyEq("first_name", new String[] { "darui.wu", "data.iterator" });
	}

	public void testInsert_DataIterator_2() {
		db.table("tdd_user").clean().insert(new DataMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui.wu");
			}
		});
		db.table("tdd_user").insert(new DataMap() {
			{
				this.put("id", 2);
				this.put("first_name", "data.iterator");
			}
		});
		db.query("select * from tdd_user").sizeEq(2)
				.propertyEq("first_name", new String[] { "darui.wu", "data.iterator" });
	}

	public void testInsert_DuplicateKey() {
		try {
			db.table("tdd_user").clean().insert(new DataSet() {
				{
					this.data("{id:1,first_name:darui.wu}");
					this.data("{id:1,first_name:data.iterator}");
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
		db.table("tdd_user").clean().insert(new DataSet() {
			{
				this.data("{id:1,first_name:darui.wu}");
				this.data("{id:2,first_name:data.iterator}");
			}
		}).commit();
		db.table("tdd_user").query().reflectionEqMap(toList(new DataMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui.wu");
				this.put("post_code", "jteste");
				this.put("address_id", 0);
				this.put("last_name", "jtester");
				this.put("sarary", 0.0);
			}
		}, new DataMap() {
			private static final long serialVersionUID = 1L;

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

	public void testInsert_CheckFillData2() {
		new MockUp<ColumnMeta>() {
			@Mock
			public boolean isNullable() {
				return false;
			}
		};
		db.table("tdd_user").clean().insert(new DataSet() {
			{
				this.data("{id:1,first_name:darui.wu}");
				this.data("{id:2,first_name:data.iterator}");
			}
		}).commit();
		db.table("tdd_user").query().reflectionEqMap(2, new DataMap() {
			{
				this.put("id", 1, 2);
				this.put("first_name", "darui.wu", "data.iterator");
				this.put("post_code", "jteste");
				this.put("address_id", 0);
				this.put("last_name", "jtester");
				this.put("sarary", 0.0);
			}
		}, EqMode.IGNORE_ORDER);
	}

	public void testInsert_MapDataIterator() {
		new MockUp<ColumnMeta>() {
			@Mock
			public boolean isNullable() {
				return false;
			}
		};
		db.table("tdd_user").clean().insert(new DataSet() {
			{
				this.data(new DataMap() {
					{
						put("id", "1");
						put("first_name", "darui.wu");
					}
				});
				this.data(new DataMap() {
					{
						put("id", "2");
						put("first_name", "data.iterator");
					}
				});
			}
		});
		db.commit().table("tdd_user").count().isEqualTo(2);
	}

	public void testInsert_MapDataAndJSON() {
		new MockUp<ColumnMeta>() {
			@Mock
			public boolean isNullable() {
				return false;
			}
		};
		db.table("tdd_user").clean().insert(new DataSet() {
			{
				this.data(new DataMap() {
					{
						put("id", "1");
						put("first_name", "darui.wu");
					}
				});
				this.data("{'id':2, 'first_name':'data.iterator'}");
			}
		}).commit();
		db.table("tdd_user").count().isEqualTo(2);
	}

	@Test
	public void testInsert_JSON() {
		db.table("tdd_user").clean().insert("{'id':1,'first_name':'wang','last_name':'json'}").commit();
		db.table("tdd_user").query().reflectionEqMap(new DataMap() {
			{
				this.put("id", 1);
				this.put("first_name", "wang");
				this.put("last_name", "json");
			}
		});
	}

	@Test
	public void testCount_MySQL() {
		db.table("tdd_user").count().notNull();
	}

	@Test(groups = "oracle")
	public void testCount_Oralce() {
		db.useDB("eve").table("MTN_ACTIVITY").count().notNull();
	}

	@Test
	public void testQueryWhere_DataMap() {
		db.table("tdd_user").clean().insert(3, new DataMap() {
			{
				this.put("id", new int[] { 100, 101, 102 });
				this.put("first_name", "name1", "name2", "name3");
				this.put("post_code", "310012", "310000");
			}
		});
		db.table("tdd_user").count().eq(3);
		db.table("tdd_user").queryWhere(new DataMap() {
			{
				this.put("post_code", "310000");
			}
		}).propertyEq("id", new int[] { 101, 102 });
	}

	@Test
	public void testQueryWhere_String() {
		db.table("tdd_user").clean().insert(3, new DataMap() {
			{
				this.put("id", new int[] { 100, 101, 102 });
				this.put("first_name", "name1", "name2", "name3");
				this.put("post_code", "310012", "310000");
			}
		});
		db.table("tdd_user").count().eq(3);
		db.table("tdd_user").queryWhere("post_code=310000").propertyEq("id", new int[] { 101, 102 });
	}
}
