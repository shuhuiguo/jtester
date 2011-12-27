package org.jtester.database.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jtester.database.executor.TableExecutor;
import org.xml.sax.SAXException;

public class DataXmlParser {
	private final static SAXReader saxReader = new SAXReader(false);
	static {
		try {
			saxReader.setValidation(false);
			saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static List<TableExecutor> parse(String xml) throws DocumentException {
		File xmlFile = new File(xml);
		if (xmlFile.exists() == false) {
			throw new RuntimeException("xml file[" + xml + "] unexisted.");
		}

		List<TableExecutor> executors = new ArrayList<TableExecutor>();
		Document doc = saxReader.read(xmlFile);
		Element root = (Element) doc.getRootElement();
		for (Iterator it = root.elementIterator(); it.hasNext();) {
			Element item = (Element) it.next();
			String name = item.getName();
			if ("insert".equalsIgnoreCase(name)) {
				List<TableExecutor> children = parseInsertTable(item);
				executors.addAll(children);
			} else if ("query".equalsIgnoreCase(name)) {
				List<TableExecutor> children = parseQueryTable(item);
				executors.addAll(children);
			} else if ("clean".equalsIgnoreCase(name)) {
				List<TableExecutor> children = parseCleanTable(item);
				executors.addAll(children);
			} else {
				throw new RuntimeException("unknown operate type[" + name
						+ "], the operate type of dataset can only be 'insert', 'query' or 'clean'.");
			}
		}
		return executors;
	}

	static List<TableExecutor> parseInsertTable(Element insert) {
		List<TableExecutor> list = new ArrayList<TableExecutor>();
		String table = insert.attributeValue("table");
		if (table == null) {
			throw new RuntimeException("you must specify the insert table name.");
		}
		String isClean = insert.attributeValue("clean");

		if ("true".equalsIgnoreCase(isClean)) {
//			CleanTable cl
		}
		List datas = insert.selectNodes("/data");

		return list;
	}

	static List<TableExecutor> parseQueryTable(Element query) {
		return null;
	}

	static List<TableExecutor> parseCleanTable(Element cleanor) {
		return null;
	}
}
