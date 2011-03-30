package org.jtester.unitils.dbwiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jtester.exception.JTesterException;
import org.jtester.utility.FindClazUtil;

/**
 * wiki转换成dbunit dataset工具类
 * 
 * @author darui.wudr
 * 
 */
public class WikiParser {
	private List<WikiTableMeta> metas = new ArrayList<WikiTableMeta>();
	private WikiTableMeta currMeta = null;

	/**
	 * 将claz package下面名称为wikiFile的wiki文件转成dbunit可以识别的xml dataset
	 * 
	 * @param claz
	 * @param wikiFile
	 * @return
	 */
	public String wiki2xml(Class<?> claz, String wikiFile) {
		try {
			String file = FindClazUtil.finePackageDir(claz) + "/" + wikiFile;
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = reader.readLine();
			while (line != null) {
				if (!WikiPaserUtil.isWikiTable(line)) {
					line = reader.readLine();
					continue;
				}
				parseTable(line);
				line = reader.readLine();
			}
			return WikiPaserUtil.parseMetas(this.metas);
		} catch (IOException e) {
			throw new JTesterException(e);
		}
	}

	public String wiki2xml(File wikiFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(wikiFile));
			String line = reader.readLine();
			while (line != null) {
				if (!WikiPaserUtil.isWikiTable(line)) {
					line = reader.readLine();
					continue;
				}
				parseTable(line);
				line = reader.readLine();
			}
			return WikiPaserUtil.parseMetas(this.metas);
		} catch (IOException e) {
			throw new JTesterException(e);
		}
	}

	private void parseTable(String line) {
		if (WikiPaserUtil.isTableSchema(line)) {
			tableStatus = TableStatus.SCHEMA;
			this.currMeta = new WikiTableMeta();
			this.metas.add(this.currMeta);
			WikiPaserUtil.parseSchema(this.currMeta, line);
		} else if (tableStatus == TableStatus.SCHEMA) {
			tableStatus = TableStatus.HEADER;
			WikiPaserUtil.parseHeader(this.currMeta, line);
		} else if (tableStatus == TableStatus.HEADER || tableStatus == TableStatus.FIELD) {
			tableStatus = TableStatus.FIELD;
			WikiPaserUtil.parseFields(this.currMeta, line);
		} else {
			tableStatus = TableStatus.NONE;
		}
	}

	private TableStatus tableStatus = TableStatus.NONE;

	private static enum TableStatus {
		SCHEMA, HEADER, FIELD, NONE;
	}

	public static String parser(File wikiFile) {
		WikiParser parser = new WikiParser();
		return parser.wiki2xml(wikiFile);
	}
}
