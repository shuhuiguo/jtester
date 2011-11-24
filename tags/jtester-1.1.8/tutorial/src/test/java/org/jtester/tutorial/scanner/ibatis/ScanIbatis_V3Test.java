package org.jtester.tutorial.scanner.ibatis;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mockit.Mock;

import org.dom4j.DocumentException;
import org.jtester.core.IJTester.MockUp;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "unused" })
public class ScanIbatis_V3Test extends JTester {

	@Test(description = "解析sqlmapconfig文件，获取所有sqlmap文件列表")
	public void testGetAllSqlmapFiles() throws DocumentException {
		String sqlmapconfigFile = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis/sqlmap-config.xml";
		String[] files = ScanIbatis_V3.getAllSqlmapFiles(sqlmapconfigFile);
		want.array(files)
				.sizeEq(3)
				.reflectionEq(
						new String[] { "${baseurl}/sqlmap/SqlMap_A.xml", "${baseurl}/sqlmap/SqlMap_B.xml",
								"${baseurl}/sqlmap/SqlMap_C.xml" });
	}

	@Test(description = "解析sqlmapconfig文件，但文件不存在")
	public void testGetAllSqlmapFiles_FileUnExists() {
		String sqlmapconfigFile = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis/unexisted.xml";
		try {
			ScanIbatis_V3.getAllSqlmapFiles(sqlmapconfigFile);
			want.fail("异常测试，代码不应该走到这里。");
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("sqlmap config").contains(sqlmapconfigFile).contains("不存在");
		}
	}

	@Test(expectedExceptions = { Exception.class }, description = "测试解析sqlmapconfig文件，但文件不存在的异常")
	public void testGetAllSqlmapFiles_FileUnExists2() throws DocumentException {
		String sqlmapconfigFile = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis/unexisted.xml";
		ScanIbatis_V3.getAllSqlmapFiles(sqlmapconfigFile);
	}

	@Test(description = "测试解析sqlmap配置文件，返回该文件定义的所有ID")
	public void testGetAllNamespaceID() throws DocumentException {
		String sqlmapFile = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis/sqlmap/SqlMap_A.xml";
		NamespaceID[] ids = ScanIbatis_V3.getAllNamespaceID(sqlmapFile);
		want.array(ids)
				.sizeEq(4)
				.reflectionEq(
						new NamespaceID[] { new NamespaceID("FAX_RECEIVE_LOG", "resultMap", "FAX_RECEIVE_LOG_RM"),
								new NamespaceID("FAX_RECEIVE_LOG", "insert", "INSERT_FAXRECEIVELOG"),
								new NamespaceID("FAX_RECEIVE_LOG", "select", "SELECT_FAXRECEIVELOG"),
								new NamespaceID("FAX_RECEIVE_LOG", "update", "UPDATE_FAXRECEIVELOG") });
	}

	@Test(expectedExceptions = Exception.class, description = "测试解析sqlmap配置文件，但该文件不存在")
	public void testGetAllNamespaceID_FileUnExisted() throws DocumentException {
		String sqlmapFile = System.getProperty("user.dir") + "/sqlmap-unexisted.xml";
		ScanIbatis_V3.getAllNamespaceID(sqlmapFile);
	}

	@Test(description = "检测重复的ID")
	public void testCheckExistedIDs() throws IOException {
		final NamespaceID duplicateID = new NamespaceID("FAX_RECEIVE_LOG", "insert", "INSERT_FAXRECEIVELOG");
		Map<NamespaceID, List<String>> existedIDs = new HashMap<NamespaceID, List<String>>() {
			private static final long serialVersionUID = 1L;
			{
				this.put(new NamespaceID("FAX_RECEIVE_LOG", "resultMap", "FAX_RECEIVE_LOG_RM"), Arrays.asList("file1"));
				this.put(duplicateID, Arrays.asList("file1", "file2"));
			}
		};

		final Writer writer = new StringWriter();

		ScanIbatis_V3.checkDuplicatedIDs(existedIDs, writer);

		String message = writer.toString();
		want.string(message).contains("找到重复的ID定义").contains(duplicateID.toString()).contains("file1").contains("file2");
	}

	@Test(description = "对入口函数的正常流程进行验证")
	public void testMain() throws Exception {
		final String baseUrl = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis";
		final NamespaceID duplicatedID = new NamespaceID("my-namespace", "sql", "my-id");
		new MockUp<ScanIbatis_V3>() {
			@Mock
			public String[] getAllSqlmapFiles(String sqlmapconfig) throws DocumentException {
				want.string(sqlmapconfig).isEqualTo(baseUrl + "/sqlmap-config.xml");
				return new String[] { "${baseurl}/sqlmap/SqlMap_A.xml",// <br>
						"${baseurl}/sqlmap/SqlMap_B.xml",// <br>
						"${baseurl}/sqlmap/SqlMap_C.xml" };
			}

			@Mock
			public NamespaceID[] getAllNamespaceID(String sqlmap) throws DocumentException {
				want.string(sqlmap).start(baseUrl + "/sqlmap/").end(".xml");
				return new NamespaceID[] { duplicatedID,
						new NamespaceID("my-namespace", "sql", UUID.randomUUID().toString()) };
			}

			@Mock
			public void checkDuplicatedIDs(Map<NamespaceID, List<String>> existedIDs, Writer writer) throws IOException {
				Set<NamespaceID> ids = existedIDs.keySet();
				want.collection(ids).sizeEq(4).hasItems(duplicatedID);

				List<String> urls = existedIDs.get(duplicatedID);
				want.collection(urls).sizeEq(3);
			}
		};
		ScanIbatis_V3.main(new String[] { baseUrl + "/sqlmap-config.xml", baseUrl, "target/temp.txt" });
	}

}
