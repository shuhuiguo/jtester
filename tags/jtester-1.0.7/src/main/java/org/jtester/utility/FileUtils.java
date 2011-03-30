package org.jtester.utility;

import static ext.jtester.org.apache.commons.io.IOUtils.closeQuietly;
import static ext.jtester.org.apache.commons.io.IOUtils.write;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.jtester.exception.JTesterException;

import ext.jtester.org.apache.commons.io.IOUtils;

// todo javadoc
public class FileUtils {

	public static void copyClassPathResource(String classPathResourceName, String fileSystemDirectoryName) {
		InputStream resourceInputStream = null;
		OutputStream fileOutputStream = null;
		try {
			resourceInputStream = FileUtils.class.getResourceAsStream(classPathResourceName);
			String fileName = StringUtils.substringAfterLast(classPathResourceName, "/");
			File fileSystemDirectory = new File(fileSystemDirectoryName);
			fileSystemDirectory.mkdirs();
			fileOutputStream = new FileOutputStream(fileSystemDirectoryName + "/" + fileName);
			IOUtils.copy(resourceInputStream, fileOutputStream);
		} catch (IOException e) {
			throw new JTesterException(e);
		} finally {
			closeQuietly(resourceInputStream);
			closeQuietly(fileOutputStream);
		}
	}

	/**
	 * Creates an URL that points to the given file.
	 * 
	 * @param file
	 *            The file, not null
	 * @return The URL to the file, not null
	 */
	public static URL getUrl(File file) {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new JTesterException("Unable to create URL for file " + file.getName(), e);
		}
	}

	/**
	 * Writes the given string to the given file
	 * 
	 * @param file
	 *            the file to write, not null
	 * @param string
	 *            the string, not null
	 */
	public static void writeStringToFile(File file, String string) throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			write(string, out, null);
		} finally {
			closeQuietly(out);
		}
	}
}
