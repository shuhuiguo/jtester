package org.jtester.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JTesterLogger {
	public static void info(String info) {
		System.out.println(info);
	}

	public static void warn(String warn) {
		System.out.println(warn);
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
