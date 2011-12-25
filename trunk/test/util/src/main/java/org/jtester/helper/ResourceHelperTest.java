package org.jtester.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.jtester.IAssertion;
import org.jtester.matcher.string.StringMode;
import org.junit.Test;

public class ResourceHelperTest implements IAssertion {
	private final static String codedir = System.getProperty("user.dir") + "/src/main/java";

	@Test
	public void findWikiFile_FilePath() throws Exception {
		File file = ResourceHelper.findWikiFile(null,
				String.format("file:%s/org/jtester/helper/ResourceHelperTest.java", codedir));
		want.file(file).isExists();
	}

	@Test
	public void findWikiFile_ClassPath() throws Exception {
		InputStream stream = ResourceHelper.getResourceAsStream("org/jtester/helper/ResourceHelperTest.class");
		want.object(stream).notNull();
		File file = ResourceHelper.findWikiFile(null, "org/jtester/helper/ResourceHelperTest.class");
		want.file(file).isExists();
	}

	@Test
	public void findWikiFile_ClassPath2() throws Exception {
		File file = ResourceHelper.findWikiFile(ResourceHelperTest.class, "ResourceHelperTest.class");
		want.file(file).isExists();
	}

	@Test
	public void getFileEncodingCharset_utf() {
		File utf8 = new File("src/main/resources/org/jtester/helper/UTF8_File.txt");
		String encoding = ResourceHelper.getFileEncodingCharset(utf8);
		want.string(encoding).isEqualTo("UTF-8");
	}

	@Test
	public void getFileEncodingCharset_gbk() {
		File gbk = new File("src/main/resources/org/jtester/helper/GBK_File.txt");
		String encoding = ResourceHelper.getFileEncodingCharset(gbk);
		want.string(encoding).isEqualTo("GB2312");
	}

	@Test
	public void testGetResourceAsStream_file() throws FileNotFoundException {
		InputStream is = ResourceHelper.getResourceAsStream("file:src/main/resources/org/jtester/helper/UTF8_File.txt");
		want.object(is).notNull();
		String str = ResourceHelper.convertStreamToSQL(is);
		want.string(str).isEqualTo("我是utf8编码", StringMode.IgnoreSpace);
	}

	@Test
	public void testGetResourceAsStream_classpath() throws FileNotFoundException {
		InputStream is = ResourceHelper.getResourceAsStream("classpath:org/jtester/helper/UTF8_File.txt");
		want.object(is).notNull();
		String str = ResourceHelper.convertStreamToSQL(is);
		want.string(str).isEqualTo("我是utf8编码", StringMode.IgnoreSpace);
	}

	@Test
	public void testGetResourceAsStream_classpath2() throws FileNotFoundException {
		InputStream is = ResourceHelper.getResourceAsStream("org/jtester/helper/UTF8_File.txt");
		want.object(is).notNull();
		String str = ResourceHelper.convertStreamToSQL(is);
		want.string(str).isEqualTo("我是utf8编码", StringMode.IgnoreSpace);
	}

	/**
	 * 文件在同一package下
	 */
	@Test
	public void testIsResourceExists_InSamePackage() {
		boolean isExists = ResourceHelper.isResourceExists(ResourceHelperTest.class, "ResourceHelperTest.class");
		want.bool(isExists).isEqualTo(true);

		isExists = ResourceHelper.isResourceExists(ResourceHelperTest.class, "UTF8_File.txt");
		want.bool(isExists).isEqualTo(true);
	}

	/**
	 * 文件在子目录下的情况
	 */
	@Test
	public void testIsResourceExists_InSubPackage() {
		boolean isExists = ResourceHelper.isResourceExists(ResourceHelperTest.class, "sub/test.file");
		want.bool(isExists).isEqualTo(true);
	}

	/**
	 * 文件不存在的情况
	 */
	@Test
	public void testIsResourceExists_UnExisted() {
		boolean isExists = ResourceHelper.isResourceExists(ResourceHelper.class, "unexists.file");
		want.bool(isExists).isEqualTo(false);
	}

	@Test
	public void testIsResourceExists_Clazz为null的情况_且文件在jar包中() {
		boolean isExists = ResourceHelper.isResourceExists(null, "dbfit/jar/file/test.wiki");
		want.bool(isExists).isEqualTo(true);
	}

	@Test
	public void testReadFromFile() throws FileNotFoundException {
		String wiki = ResourceHelper.readFromFile("classpath:dbfit/jar/file/test.wiki");
		want.string(wiki).contains("|connect|");
	}

	// @Test
	// @DbFit(when = { "dbfit/jar/file/test.wiki" }, auto = AUTO.AUTO)
	// public void testUseJarWiki() {
	//
	// }

	@Test
	public void testCopyClassPathResource() {
		String targetFile = "target/test/test.css";
		new File(targetFile).delete();
		ResourceHelper.copyClassPathResource("org/jtester/fit/fitnesse_base.css", targetFile);
		want.file(targetFile).isExists();
	}

	@Test
	public void testGetSuffixPath() {
		String path = ResourceHelper.getSuffixPath(new File("src/java/org/jtester"), System.getProperty("user.dir")
				+ "/src/java/org/jtester/reflector/helper/ClazzHelper.java");
		want.string(path).isEqualTo("reflector/helper/ClazzHelper.java");

		path = ResourceHelper.getSuffixPath(new File("src/java/org/jtester/"), System.getProperty("user.dir")
				+ "/src/java/org/jtester/reflector/helper/ClazzHelper.java");

		want.string(path).isEqualTo("reflector/helper/ClazzHelper.java");
	}

	@Test
	public void testGetResourceUrl() {
		URL url = ResourceHelper.getResourceUrl("classpath:org/jtester/utility/log4j.xml");
		want.string(url.toString()).contains("org/jtester/utility/log4j.xml");
	}

	@Test
	public void testGetResourceUrl_InJar() {
		URL url = ResourceHelper.getResourceUrl("classpath:dbfit/jar/file/log4j.xml");
		want.string(url.toString()).start("jar:file").end("dbfit/jar/file/log4j.xml");
	}
}
