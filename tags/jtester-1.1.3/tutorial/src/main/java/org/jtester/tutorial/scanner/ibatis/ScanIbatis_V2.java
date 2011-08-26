package org.jtester.tutorial.scanner.ibatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ScanIbatis_V2 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		List<String> existedIDs = new ArrayList<String>();
		String baseUrl = args[0];

		String[] sqlmaps = getAllSqlmapFiles(baseUrl + "/sqlmap-config.xml");
		for (String sqlmap : sqlmaps) {
			String[] ids = getAllNamespaceID(sqlmap);
			checkExistedIDs(existedIDs, ids);
		}
	}

	/**
	 * 传入sqlmapconfig文件，返回sqlmap文件列表
	 * 
	 * @param sqlmapconfig
	 *            ibatis的sqlmap-config.xml文件路径
	 * @return 所有sqlmap配置文件列表
	 */
	public static String[] getAllSqlmapFiles(String sqlmapconfig) {
		return null;// TODO
	}

	/**
	 * 解析单个sqlmap配置文件，获取文件中所有的ID值列表
	 * 
	 * @param sqlmapFile
	 *            具体的sqlmap配置文件路径
	 * @return 配置文件中所有的id值列表
	 */
	public static String[] getAllNamespaceID(String sqlmapFile) {
		return null;// TODO
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
		// TODO
	}
}
