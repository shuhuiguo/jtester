package org.jtester.module.database.dbop;

import java.util.Iterator;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.beans.DataIterator;
import org.jtester.beans.DataMap;
import org.jtester.database.operator.InsertOp;
import org.jtester.helper.DateHelper;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "serial" })
public class InsertOpTest implements IAssertion, IDatabase {
	@Test
	@DataFrom("testGetInsertCommandText_data")
	public void testGetInsertCommandText(DataMap data, String result) {
		InsertOp ds = reflector.newInstance(InsertOp.class);
		reflector.setField(ds, "quato", "");
		reflector.setField(ds, "table", "tdd_user");
		reflector.setField(ds, "data", data);

		String text = reflector.invoke(ds, "getInsertCommandText");
		want.string(text).eqWithStripSpace(result);
	}

	static Iterator testGetInsertCommandText_data() {
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

	@Test
	public void testInsert_OracleDate() {
		db.useDB("eve").table("MTN_PLAN").clean().insert(new DataMap() {
			{
				put("ID", 1);
				put("GMT_CREATE", DateHelper.parse("2010-11-10"));
			}
		});
		db.table("MTN_PLAN").query().propertyEqMap(new DataMap() {
			{
				put("ID", 1);
				put("GMT_CREATE", "2010-11-10");
			}
		});
	}
}
