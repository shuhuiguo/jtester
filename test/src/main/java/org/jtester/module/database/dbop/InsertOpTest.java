package org.jtester.module.database.dbop;

import java.util.Iterator;

import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "serial" })
@Test(groups = { "jtester", "database" })
public class InsertOpTest extends JTester {
	@Test(dataProvider = "testGetInsertCommandText_data")
	public void testGetInsertCommandText(DataMap data, String result) {
		InsertOp ds = reflector.newInstance(InsertOp.class);
		reflector.setField(ds, "quato", "");
		reflector.setField(ds, "table", "tdd_user");
		reflector.setField(ds, "data", data);

		String text = reflector.invoke(ds, "getInsertCommandText");
		want.string(text).eqWithStripSpace(result);
	}

	@DataProvider
	Iterator testGetInsertCommandText_data() {
		return new DataIterator() {
			{
				this.data(new DataMap() {
				}, "insert into tdd_user() values()");

				this.data(new DataMap() {
					{
						this.put("id", 1);
					}
				}, "insert into tdd_user(id) values(?)");

				this.data(new DataMap() {
					{
						this.put("id", 1);
						this.put("first_name", "darui.wu");
					}
				}, "insert into tdd_user(id,first_name) values(?,?)");
			}
		};
	}
}
