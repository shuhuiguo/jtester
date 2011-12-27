package org.jtester.module.database.environment;

import java.util.HashMap;
import java.util.Map;

import mockit.Mock;
import mockit.MockUp;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.beans.DataMap;
import org.jtester.module.database.environment.TableMeta.ColumnMeta;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "serial", "unchecked", "unused" })
public class AbstractDBEnvironmentTest implements IAssertion, IDatabase {

	@Test
	public void testGetTableMetaData() throws Exception {
		new MockUp<ColumnMeta>() {
			@Mock
			public boolean isNullable() {
				return false;
			}
		};
		DBEnvironment db = DBEnvironmentFactory.getCurrentDBEnvironment();
		TableMeta meta = db.getTableMetaData("tdd_user");
		Map data = new HashMap() {
			{
				this.put("id", "123");
				this.put("first_name", "darui.wu");
			}
		};
		meta.fillData(data, db);
		want.object(meta).notNull();
		want.map(meta.getColumns()).sizeEq(8);
		want.map(data).sizeEq(8).reflectionEqMap(new DataMap() {
			{
				this.put("id", "123");
				this.put("first_name", "darui.wu");
				this.put("post_code", "jteste");
				this.put("address_id", 0);
				this.put("last_name", "jtester");
				this.put("sarary", 0.0);
			}
		});
	}
}
