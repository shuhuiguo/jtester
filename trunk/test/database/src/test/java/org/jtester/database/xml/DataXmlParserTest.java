package org.jtester.database.xml;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.database.executor.CleanTableExecutor;
import org.jtester.database.executor.InsertTableExecutor;
import org.jtester.database.executor.TableExecutor;
import org.junit.Test;

public class DataXmlParserTest implements IAssertion {
	static final String baseDir = System.getProperty("user.dir") + "/src/test/resources/org/jtester/database/xml/";

	@Test
	public void testParseXml() throws Exception {
		String xml = baseDir + "insert-sample01.xml";
		List<TableExecutor> list = DataXmlParser.parse(xml);
		want.list(list).sizeIs(2)
				.matchIterator(the.object().clazIs(CleanTableExecutor.class), the.object().clazIs(InsertTableExecutor.class));
	}
}
