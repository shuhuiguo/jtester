package org.jtester.tutorial.scanner.ibatis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

@SuppressWarnings({ "rawtypes" })
public class ScanIbatis_V2Impl {
	private final static SAXReader saxReader = new SAXReader(false);
	static {
		try {
			saxReader.setValidation(false);
			saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 1) {
			throw new RuntimeException("命令行参数个数不对，扫描要且只需要一个baseUrl地址参数.");
		}

		List<String> existedIDs = new ArrayList<String>();
		String baseUrl = args[0];
		String[] sqlmaps = getAllSqlmapFiles(baseUrl + "/sqlmap-config.xml");
		for (String sqlmap : sqlmaps) {
			String[] ids = getAllNamespaceID(sqlmap);
			checkExistedIDs(existedIDs, ids);
		}
	}

	// String sqlmapUrl = sqlmap.replace("${baseurl}", baseUrl);
	// String[] ids = getAllNamespaceID(sqlmapUrl);

	// String[] ids = getAllNamespaceID(sqlmap);

	/**
	 * 传入sqlmapconfig文件，返回sqlmap文件列表
	 * 
	 * @param sqlmapconfig
	 *            ibatis的sqlmap-config.xml文件路径
	 * @return 所有sqlmap配置文件列表
	 * @throws DocumentException
	 */
	public static String[] getAllSqlmapFiles(String sqlmapconfig) throws DocumentException {
		File sqlmapconfigFile = new File(sqlmapconfig);
		if (sqlmapconfigFile.exists() == false) {
			throw new RuntimeException(String.format("ibatis sqlmap config文件[%s]不存在.", sqlmapconfig));
		}

		List<String> sqlMapFiles = new ArrayList<String>();
		Document doc = saxReader.read(sqlmapconfigFile);
		List nl = doc.selectNodes("//sqlMapConfig/sqlMap");
		for (Object o : nl) {
			sqlMapFiles.add(((Element) o).attributeValue("url"));
		}
		return sqlMapFiles.toArray(new String[0]);
	}

	/**
	 * 解析单个sqlmap配置文件，获取文件中所有的ID值列表
	 * 
	 * @param sqlmapFile
	 *            具体的sqlmap配置文件路径
	 * @return 配置文件中所有的id值列表
	 * @throws DocumentException
	 */
	public static String[] getAllNamespaceID(String sqlmap) throws DocumentException {
		File sqlmapFile = new File(sqlmap);
		if (sqlmapFile.exists() == false) {
			throw new RuntimeException(String.format("ibatis sqlmap文件[%s]不存在.", sqlmap));
		}

		Document doc = saxReader.read(sqlmapFile);
		Element root = doc.getRootElement();
		String namespace = root.attributeValue("namespace");
		List list = doc.selectNodes("//sqlMap/*[@id]");

		List<String> ids = new ArrayList<String>();
		for (Object o : list) {
			Element node = (Element) o;
			String id = node.attributeValue("id");
			String type = node.getName();

			ids.add(String.format("%s|%s|%s", namespace, type, id));
		}
		return ids.toArray(new String[0]);
	}

	/**
	 * 判断当前解析出来的id值是否已经存在?<br>
	 * o 如果存在，输出警告信息<br>
	 * o 如果不存在，将id值加入到existedIDs列表中
	 * 
	 * @param existedIDs
	 *            已经存在的id列表
	 * @param ids
	 *            当前解析出来的id列表
	 */
	public static void checkExistedIDs(List<String> existedIDs, String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		if (existedIDs == null) {
			throw new RuntimeException("传入的参数[已存在的id列表]不能为空。");
		}
		for (String id : ids) {
			if (existedIDs.contains(id)) {
				System.out.println("该ID[" + id + "]已经定义过了。");
			} else {
				existedIDs.add(id);
			}
		}
	}
}
