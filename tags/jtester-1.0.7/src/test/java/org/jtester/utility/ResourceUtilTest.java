package org.jtester.utility;

import java.io.File;
import java.io.InputStream;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester" })
public class ResourceUtilTest extends JTester {
	@Test
	public void findWikiFile_FilePath() throws Exception {
		File file = ResourceUtil.findWikiFile(null, "file:./src/main/java/org/jtester/utility/ResourceUtil.java");
		want.file(file).isExists();
	}

	@Test
	public void findWikiFile_ClassPath() throws Exception {
		InputStream stream = ClassLoader.getSystemResourceAsStream("org/jtester/utility/ResourceUtil.class");
		want.object(stream).notNull();
		File file = ResourceUtil.findWikiFile(null, "org/jtester/utility/ResourceUtil.class");
		want.file(file).isExists();
	}

	@Test
	public void findWikiFile_ClassPath2() throws Exception {
		File file = ResourceUtil.findWikiFile(ResourceUtil.class, "ResourceUtil.class");
		want.file(file).isExists();
	}

	@Test
	public void getFileEncodingCharset_utf() {
		File utf8 = new File("src/test/resources/org/jtester/module/core/DbFitModuleTest.testCn.utf8.when.wiki");
		String encoding = ResourceUtil.getFileEncodingCharset(utf8);
		want.string(encoding).isEqualTo("UTF-8");
	}

	@Test
	public void getFileEncodingCharset_gbk() {
		File gbk = new File("src/test/resources/org/jtester/module/core/DbFitModuleTest.testCn.gbk.when.wiki");
		String encoding = ResourceUtil.getFileEncodingCharset(gbk);
		want.string(encoding).isEqualTo("GB2312");
	}

	@Test
	public void testGetInputStreamFromFile_file() {
		InputStream is = ResourceUtil
				.getInputStreamFromFile("file:src/test/resources/org/jtester/utility/executeFile.sql");
		want.object(is).notNull();
		String sql = ResourceUtil.convertStreamToSQL(is);
		want.string(sql).contains("insert into tdd_user").notContain("--").notContain("#");
	}

	@Test
	public void testGetInputStreamFromFile_classpath() {
		InputStream is = ResourceUtil.getInputStreamFromFile("classpath:org/jtester/utility/executeFile.sql");
		want.object(is).notNull();
		String sql = ResourceUtil.convertStreamToSQL(is);
		want.string(sql).contains("insert into tdd_user").notContain("--").notContain("#");
	}

	@Test
	public void testGetInputStreamFromFile_classpath2() {
		InputStream is = ResourceUtil.getInputStreamFromFile("org/jtester/utility/executeFile.sql");
		want.object(is).notNull();
		String sql = ResourceUtil.convertStreamToSQL(is);
		want.string(sql).contains("insert into tdd_user").notContain("--").notContain("#");
	}

	@Test
	public void testIsExistsClassPathFile_文件在同一package下() {
		boolean isExists = ResourceUtil.isExistsClassPathFile(ResourceUtil.class, "ResourceUtil.class");
		want.bool(isExists).isEqualTo(true);

		isExists = ResourceUtil.isExistsClassPathFile(ResourceUtil.class, "executeFile.sql");
		want.bool(isExists).isEqualTo(true);
	}

	@Test
	public void testIsExistsClassPathFile_文件在子目录下的情况() {
		boolean isExists = ResourceUtil.isExistsClassPathFile(ResourceUtil.class, "sub/test.file");
		want.bool(isExists).isEqualTo(true);
	}

	@Test
	public void testIsExistsClassPathFile_文件不存在的情况() {
		boolean isExists = ResourceUtil.isExistsClassPathFile(ResourceUtil.class, "unexists.file");
		want.bool(isExists).isEqualTo(false);
	}
}
