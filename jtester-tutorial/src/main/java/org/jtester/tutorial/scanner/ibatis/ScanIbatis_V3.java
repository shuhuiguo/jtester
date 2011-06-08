package org.jtester.tutorial.scanner.ibatis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

@SuppressWarnings({ "rawtypes" })
public class ScanIbatis_V3 {
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
		if (args == null || args.length != 3) {
			throw new RuntimeException("命令行参数个数不对，扫描需要3个参数：sqlmapconfig文件、baseurl参数和消息输出文件。");
		}
		String sqlmapconfig = args[0];
		String baseurl = args[1];
		String outputFile = args[2];

		Map<NamespaceID, List<String>> existedIDs = new HashMap<NamespaceID, List<String>>();
		Map<String, List<String>> illegalFiles = new HashMap<String, List<String>>();

		String[] sqlmaps = getAllSqlmapFiles(sqlmapconfig);
		for (String sqlmap : sqlmaps) {
			String sqlmapUrl = sqlmap.replace("${baseurl}", baseurl);
			NamespaceID[] ids = getAllNamespaceID(sqlmapUrl);
			addToExistedIDs(existedIDs, sqlmapUrl, ids);
			findIllegalChars(sqlmapUrl, illegalFiles);
		}

		Writer writer = new FileWriter(outputFile);
		try {
			checkDuplicatedIDs(existedIDs, writer);
			writerIllegaeInfo(illegalFiles, writer);
		} finally {
			writer.close();
		}
	}

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
	public static NamespaceID[] getAllNamespaceID(String sqlmap) throws DocumentException {
		File sqlmapFile = new File(sqlmap);
		if (sqlmapFile.exists() == false) {
			throw new RuntimeException(String.format("ibatis sqlmap文件[%s]不存在.", sqlmap));
		}

		Document doc = saxReader.read(sqlmapFile);
		Element root = doc.getRootElement();
		String namespace = root.attributeValue("namespace");
		List list = doc.selectNodes("//sqlMap/*[@id]");

		List<NamespaceID> ids = new ArrayList<NamespaceID>();
		for (Object o : list) {
			Element node = (Element) o;
			String id = node.attributeValue("id");
			String type = node.getName();

			ids.add(new NamespaceID(namespace, type, id));
		}
		return ids.toArray(new NamespaceID[0]);
	}

	/**
	 * 将从sqlmap中解析出来的id列表添加到已存在existedIDs Map中
	 * 
	 * @param existedIDs
	 *            以存在的id map列表
	 * @param sqlmap
	 *            sqlmap文件名
	 * @param ids
	 *            从sqlmap中解析出来的所有id列表
	 */
	public static void addToExistedIDs(Map<NamespaceID, List<String>> existedIDs, String sqlmap, NamespaceID[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		if (existedIDs == null) {
			throw new RuntimeException("传入的参数[已存在的id列表]不能为空。");
		}
		for (NamespaceID id : ids) {
			List<String> urls = existedIDs.get(id);
			if (urls == null) {
				urls = new ArrayList<String>();
				existedIDs.put(id, urls);
			}

			urls.add(sqlmap);
		}
	}

	/**
	 * 检测所有定义的id中存在重复的情况，并将消息输出
	 * 
	 * @param existedIDs
	 *            所有已定义的id map列表
	 * @param writer
	 *            消息输出器
	 * @throws IOException
	 */
	public static void checkDuplicatedIDs(Map<NamespaceID, List<String>> existedIDs, Writer writer) throws IOException {
		for (NamespaceID id : existedIDs.keySet()) {
			List<String> urls = existedIDs.get(id);
			if (urls == null || urls.size() <= 1) {
				continue;
			}
			writer.write("找到重复的ID定义：" + id.toString() + "\n");
			writer.write("在下列文件中：\n");
			for (String url : urls) {
				writer.write(String.format("\t%s\n", url));
			}
			writer.write("\n\n");
		}
	}

	/**
	 * 检测sqlmap文件中是否存在非法字符<br>
	 * o 如果有非法字符，则添加到illegalFiles列表中<br>
	 * o 如果无非法字符，则不做任何事
	 * 
	 * @param sqlmap
	 * @param illegalFiles
	 */
	public static void findIllegalChars(String sqlmap, Map<String, List<String>> illegalFiles) {
		// TODO
	}

	/**
	 * 输出非法字符的信息
	 * 
	 * @param illegalFiles
	 * @param writer
	 */
	public static void writerIllegaeInfo(Map<String, List<String>> illegalFiles, Writer writer) {
		// TODO
	}
}
