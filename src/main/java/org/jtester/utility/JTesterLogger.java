package org.jtester.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import static org.jtester.module.core.ConfigurationConst.LOG4J_XML_FILE;

import org.jtester.module.core.helper.ConfigurationHelper;

public class JTesterLogger {

	public static void mark(String marker) {
		System.out.println(marker);
	}

	/**
	 * 重置log4j的设置
	 */
	public static void resetLog4jLevel() {
		String log4jxml = ConfigurationHelper.getString(LOG4J_XML_FILE);
		if (StringHelper.isBlankOrNull(log4jxml)) {
			return;
		}
		try {
			URL url = ResourceHelper.getResourceUrl(log4jxml);
			org.apache.log4j.xml.DOMConfigurator.configure(url);
		} catch (Throwable e) {
			mark("reset log4j leve error, " + e == null ? "null" : e.getMessage());
		}
	}

	private static File debugFile = new File(System.getProperty("user.dir") + "/target/jtester.log");

	/**
	 * 用于记录jtester运行时的信息，方便定位复杂的问题<br>
	 * 正式发布版本中所有方法都改为protected，禁止其它类引用
	 * 
	 * @author darui.wudr
	 * 
	 */
	protected static void writerDebugInfo(String info) {
		Writer writer = null;
		try {
			writer = new FileWriter(debugFile, true);
			writer.write(info);
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
