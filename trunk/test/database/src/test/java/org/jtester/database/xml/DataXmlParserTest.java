package org.jtester.database.xml;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.beans.DataMap;
import org.jtester.database.executor.CleanTableExecutor;
import org.jtester.database.executor.InsertTableExecutor;
import org.jtester.database.executor.TableExecutor;
import org.junit.Test;

@SuppressWarnings("serial")
public class DataXmlParserTest implements IAssertion {
	static final String baseDir = System.getProperty("user.dir") + "/src/test/resources/org/jtester/database/xml/";

	@Test
	public void testParseInsertTable() throws Exception {
		String xml = baseDir + "insert-sample01.xml";
		List<TableExecutor> list = DataXmlParser.parse(xml);
		want.list(list)
				.sizeIs(2)
				.matchIterator(the.object().clazIs(CleanTableExecutor.class),
						the.object().clazIs(InsertTableExecutor.class));
		TableExecutor insert = list.get(1);
		List<DataMap> datas = reflector.getField(insert, "datas");
		want.list(datas).propertyEqMap(2, new DataMap() {
			{
				this.put("id", "1", "2");
				this.put("first_name", "darui", "jobs");
				this.put("last_name", "wu", "he");
			}
		});
	}

	@Test
	public void testParseQueryTable() throws Exception {
		String xml = baseDir + "query-sample01.xml";
		List<TableExecutor> list = DataXmlParser.parse(xml);
		want.list(list).sizeEq(1);
		TableExecutor executor = list.get(0);
		want.object(executor).propertyEq("table", "tdd_user")
				.propertyEq("query", "select * from tdd_user where id=102");
		List<DataMap> datas = reflector.getField(executor, "expected");
		want.list(datas).propertyEqMap(2, new DataMap() {
			{
				this.put("id", "1", "2");
				this.put("first_name", "darui", "jobs");
				this.put("last_name", "wu", "he");
			}
		});
	}

	@Test
	public void testParseCleanTable() {

	}
}
