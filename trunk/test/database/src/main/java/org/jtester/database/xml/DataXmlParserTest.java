package org.jtester.database.xml;

import org.junit.Test;

public class DataXmlParserTest {
	static final String baseDir = System.getProperty("user.dir") + "src/main/resources/org/jtester/database/xml/";

	@Test
	public void testParseXml() {
		String xml = baseDir + "insert-sample01.xml";
		DataParser.parse(xml);
	}
}
