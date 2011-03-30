package fitlibrary.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class FileLogger {
	private static ThreadLocal<String> LOG_FILE = new ThreadLocal<String>();
	private static final ReentrantLock fileCreationLock = new ReentrantLock();

	public void start(String fileName) {
		createAnyDirectories(fileName);
		try {
			fileCreationLock.lock();
			if (LOG_FILE.get() == null)
				LOG_FILE.set(selectFileName(fileName + "_" + formattedDateTime()));
		} finally {
			fileCreationLock.unlock();
		}
	}

	private void createAnyDirectories(String fileName) {
		int slash = fileName.lastIndexOf("/");
		if (slash < 0)
			slash = fileName.lastIndexOf("\\");
		if (slash >= 0)
			new File(fileName.substring(0, slash)).mkdirs();
	}

	public void println(String s) throws IOException {
		String pathname = LOG_FILE.get();
		if (pathname != null) {
			PrintWriter logWriter = new PrintWriter(new FileWriter(new File(pathname), true));
			logWriter.println(s);
			logWriter.close();
		}
	}

	private static String selectFileName(String fileName) {
		String fullFileName = fileName + ".0.txt";
		if (new File(fullFileName).exists()) {
			for (int i = 1; i < 10000; i++) {
				String logFileName = fileName + "." + i + ".txt";
				if (!new File(logFileName).exists()) {
					return logFileName;
				}
			}
		}
		return fullFileName;
	}

	private static String formattedDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
	}
}
