package org.jtester.database.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jtester.beans.DataMap;
import org.jtester.database.executor.CleanTableExecutor;
import org.jtester.database.executor.InsertTableExecutor;
import org.jtester.database.executor.TableExecutor;
import org.xml.sax.SAXException;

@SuppressWarnings({ "unchecked", "rawtypes" })
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
			list.add(new CleanTableExecutor(table));
		}
		List<Element> children = insert.selectNodes("//dataset/insert/data");
		if (children == null || children.size() == 0) {
			return list;
		}
		List<DataMap> datas = new ArrayList<DataMap>();
		for (Element data : children) {
			DataMap map = parseDataMapFromElement(data);
			datas.add(map);
		}
		list.add(new InsertTableExecutor(table, datas));
		return list;
	}

	static DataMap parseDataMapFromElement(Element data) {
		DataMap map = new DataMap();
		for (Iterator it1 = data.attributeIterator(); it1.hasNext();) {
			Attribute field = (Attribute) it1.next();
			String name = field.getName();
			String value = field.getValue();
			map.put(name, value);
		}
		for (Iterator it2 = data.elementIterator(); it2.hasNext();) {
			Element field = (Element) it2.next();
			String name = field.getName();
			String value = field.getText();
			map.put(name, value);
		}
		return map;
	}

	static List<TableExecutor> parseQueryTable(Element query) {
		return null;
	}

	static List<TableExecutor> parseCleanTable(Element cleanor) {
		return null;
	}
}
