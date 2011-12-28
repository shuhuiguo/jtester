package org.jtester.database.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jtester.beans.DataMap;
import org.jtester.database.executor.CleanTableExecutor;
import org.jtester.database.executor.InsertTableExecutor;
import org.jtester.database.executor.QueryTableExecutor;
import org.jtester.database.executor.TableExecutor;
import org.jtester.helper.StringHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DataXmlParser {
	public static List<TableExecutor> parse(String xml) throws ParserConfigurationException, FileNotFoundException,
			IOException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new FileInputStream(new File(xml)));
		Element element = document.getDocumentElement();
		NodeList list = element.getChildNodes();

		List<TableExecutor> executors = new ArrayList<TableExecutor>();
		for (int index = 0; index < list.getLength(); index++) {
			Node node = list.item(index);
			if (!(node instanceof Element)) {
				continue;
			}
			String name = node.getNodeName();
			if ("insert".equalsIgnoreCase(name)) {
				List<TableExecutor> children = parseInsertTable((Element) node);
				executors.addAll(children);
			} else if ("query".equalsIgnoreCase(name)) {
				List<TableExecutor> children = parseQueryTable((Element) node);
				executors.addAll(children);
			} else if ("clean".equalsIgnoreCase(name)) {
				List<TableExecutor> children = parseCleanTable((Element) node);
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
		String table = insert.getAttribute("table");
		if (table == null) {
			throw new RuntimeException("you must specify the insert table name.");
		}
		String isClean = insert.getAttribute("clean");

		if ("true".equalsIgnoreCase(isClean)) {
			list.add(new CleanTableExecutor(table));
		}
		NodeList children = insert.getChildNodes();
		List<DataMap> datas = parseDataMapListFromElement(children);
		list.add(new InsertTableExecutor(table, datas));
		return list;
	}

	static List<DataMap> parseDataMapListFromElement(NodeList elements) {
		List<DataMap> datas = new ArrayList<DataMap>();
		if (elements == null || elements.getLength() == 0) {
			return datas;
		}
		for (int index = 0; index < elements.getLength(); index++) {
			Node data = elements.item(index);
			if (!(data instanceof Element)) {
				continue;
			}
			DataMap map = parseDataMapFromElement((Element) data);
			datas.add(map);
		}
		return datas;
	}

	static DataMap parseDataMapFromElement(Element data) {
		DataMap map = new DataMap();
		NamedNodeMap attributes = data.getAttributes();
		for (int index = 0; index < attributes.getLength(); index++) {
			Attr field = (Attr) attributes.item(index);
			String name = field.getName();
			String value = field.getValue();
			map.put(name, value);
		}
		NodeList child = data.getChildNodes();
		for (int index = 0; index < child.getLength(); index++) {
			Node field = (Node) child.item(index);
			if (!(field instanceof Element)) {
				continue;
			}
			String name = field.getNodeName();
			String value = ((Element) field).getTextContent();
			map.put(name, value);
		}
		return map;
	}

	static List<TableExecutor> parseQueryTable(Element query) {
		NodeList children = query.getChildNodes();
		List<DataMap> datas = parseDataMapListFromElement(children);

		List<TableExecutor> list = new ArrayList<TableExecutor>();
		String select = query.getAttribute("select");
		if (StringHelper.isBlankOrNull(select)) {
			String table = query.getAttribute("table");
			String where = query.getAttribute("where");
			TableExecutor executor = new QueryTableExecutor(table, where, datas);
			list.add(executor);
		} else {
			TableExecutor executor = new QueryTableExecutor(select, datas);
			list.add(executor);
		}
		return list;
	}

	static List<TableExecutor> parseCleanTable(Element cleanor) {
		return null;
	}
}
