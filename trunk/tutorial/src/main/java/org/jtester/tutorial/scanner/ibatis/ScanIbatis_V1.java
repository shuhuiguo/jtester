package org.jtester.tutorial.scanner.ibatis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ScanIbatis_V1 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		List<String> nameList = new ArrayList<String>();
		String baseUrl = args[0];

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(new File(baseUrl + "/sqlmap-config.xml"));
		NodeList no = doc.getElementsByTagName("sqlMap");
		for (int i = 0; i < no.getLength(); i++) {
			Node node = no.item(i);
			NamedNodeMap nnm = node.getAttributes();
			Node xx = nnm.item(0);

			String xmlString = xx.getNodeValue().replace("${baseurl}", baseUrl);
			File xmlFile = new File(xmlString);
			doc = builder.parse(xmlFile);
			Element ls = doc.getDocumentElement();
			String namespace = ls.getAttribute("namespace");
			
			NodeList mmx = ls.getChildNodes();
			if (mmx.getLength() > 0) {
				for (int jj = 0; jj < mmx.getLength(); jj++) {
					Node nn = mmx.item(jj);
					String nodeValue = nn.getNodeName();
					NamedNodeMap mmxx = mmx.item(jj).getAttributes();
					if (mmxx != null && nodeValue != null) {
						Node childNode = mmxx.getNamedItem("id");
						if (childNode != null) {
							String idValue = childNode.getNodeValue();
							String nameId = namespace + nodeValue + idValue;
							if (nameList.contains(nameId)) {
								System.out.println("fileName: " + xmlFile.getAbsolutePath() + "  " + nodeValue + " = "
										+ idValue + " repeat");
							} else {
								nameList.add(nameId);
							}
						}
					}
				}
			}
		}
	}
}
