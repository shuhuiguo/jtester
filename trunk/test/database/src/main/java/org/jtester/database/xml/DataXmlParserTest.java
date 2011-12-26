package org.jtester.database.xml;

import java.util.List;

import org.jtester.IAssertion;
import org.jtester.database.table.TabelExecutor;
import org.junit.Test;

public class DataXmlParserTest implements IAssertion {
	static final String baseDir = System.getProperty("user.dir") + "src/main/resources/org/jtester/database/xml/";

	@Test
	public void testParseXml() {
		String xml = baseDir + "insert-sample01.xml";
		List<TabelExecutor> list = DataParser.parse(xml);
		want.list(list).sizeIs(2).matchIterator();
	}
}
