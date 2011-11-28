package org.jtester.tutorial.scanner.ibatis;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import mockit.Mock;

import org.dom4j.DocumentException;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings({ "unused" })
public class ScanIbatis_V2ImplTest extends JTester {

	@Test(description = "解析sqlmapconfig文件，获取所有sqlmap文件列表")
	public void testGetAllSqlmapFiles() throws DocumentException {
		String sqlmapconfigFile = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis/sqlmap-config.xml";
		String[] files = ScanIbatis_V2Impl.getAllSqlmapFiles(sqlmapconfigFile);
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
			ScanIbatis_V2Impl.getAllSqlmapFiles(sqlmapconfigFile);
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
		ScanIbatis_V2Impl.getAllSqlmapFiles(sqlmapconfigFile);
	}

	@Test(description = "测试解析sqlmap配置文件，返回该文件定义的所有ID")
	public void testGetAllNamespaceID() throws DocumentException {
		String sqlmapFile = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis/sqlmap/SqlMap_A.xml";
		String[] ids = ScanIbatis_V2Impl.getAllNamespaceID(sqlmapFile);
		want.array(ids)
				.sizeEq(4)
				.reflectionEq(
						new String[] { "FAX_RECEIVE_LOG|resultMap|FAX_RECEIVE_LOG_RM",
								"FAX_RECEIVE_LOG|insert|INSERT_FAXRECEIVELOG",
								"FAX_RECEIVE_LOG|select|SELECT_FAXRECEIVELOG",
								"FAX_RECEIVE_LOG|update|UPDATE_FAXRECEIVELOG" });
	}

	@Test(expectedExceptions = Exception.class, description = "测试解析sqlmap配置文件，但该文件不存在")
	public void testGetAllNamespaceID_FileUnExisted() throws DocumentException {
		String sqlmapFile = System.getProperty("user.dir") + "/sqlmap-unexisted.xml";
		ScanIbatis_V2Impl.getAllNamespaceID(sqlmapFile);
	}

	@Test(description = "检测重复的ID")
	public void testCheckExistedIDs() throws FileNotFoundException {
		final String duplicatedID = "FAX_RECEIVE_LOG|insert|INSERT_FAXRECEIVELOG";
		List<String> existedIDs = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				this.add("FAX_RECEIVE_LOG|resultMap|FAX_RECEIVE_LOG_RM");
				this.add(duplicatedID);
				this.add("FAX_RECEIVE_LOG|select|SELECT_FAXRECEIVELOG");
			}
		};
		final PrintStream stdout = System.out;
		try {
			MyStdoutPrintStream myStdout = new MyStdoutPrintStream(stdout);
			System.setOut(myStdout);

			String newId = "FAX_RECEIVE_LOG|update|UPDATE_FAXRECEIVELOG";
			ScanIbatis_V2Impl.checkExistedIDs(existedIDs, new String[] { duplicatedID, newId });
			want.collection(existedIDs).sizeEq(4).hasAllItems(duplicatedID, newId);
			String message = myStdout.getLog();
			want.string(message).contains(duplicatedID);
		} finally {
			System.setOut(stdout);
		}
	}

	@Test(expectedExceptions = Exception.class, description = "测试方法参数异常时，应该抛出对应的异常")
	public void testCheckExistedIDs_IllegalParameter() throws FileNotFoundException {
		ScanIbatis_V2Impl.checkExistedIDs(null, new String[] { "" });
	}

	@Test(description = "测试没有重复id的情况", dataProvider = "noduplicatedIDs")
	public void testCheckExistedIDs_NoDuplicatedIds(String[] ids, int size) throws FileNotFoundException {
		List<String> existedIDs = new ArrayList<String>();

		ScanIbatis_V2Impl.checkExistedIDs(existedIDs, ids);
		want.collection(existedIDs).sizeEq(size);
	}

	@DataProvider
	public Object[][] noduplicatedIDs() {
		return new Object[][] { { null, 0 },// <br>
				{ new String[] {}, 0 },// <br>
				{ new String[] { "FAX_RECEIVE_LOG|select|SELECT_FAXRECEIVELOG" }, 1 } // <br>
		};
	}

	public static class MyStdoutPrintStream extends PrintStream {
		private StringBuffer buff = new StringBuffer();
		final PrintStream stdout;

		public MyStdoutPrintStream(PrintStream out) throws FileNotFoundException {
			super(out);
			this.stdout = out;
		}

		@Override
		public void println(String info) {
			super.println(info);
			buff.append(info);
		}

		public String getLog() {
			return buff.toString();
		}
	}

	@Test(description = "对入口函数的正常流程进行验证")
	public void testMain() throws Exception {
		final String baseUrl = System.getProperty("user.dir")
				+ "/src/test/resources/org/jtester/tutorial/scanner/ibatis";
		new MockUp<ScanIbatis_V2Impl>() {
			@Mock
			public String[] getAllSqlmapFiles(String sqlmapconfig) throws DocumentException {
				want.string(sqlmapconfig).isEqualTo(baseUrl + "/sqlmap-config.xml");
				return new String[] { "${baseurl}/sqlmap/SqlMap_A.xml",// <br>
						"${baseurl}/sqlmap/SqlMap_B.xml",// <br>
						"${baseurl}/sqlmap/SqlMap_C.xml" };
			}

			@Mock
			public String[] getAllNamespaceID(String sqlmap) throws DocumentException {
				want.string(sqlmap).start(baseUrl + "/sqlmap/").end(".xml");
				return null;
			}

			@Mock
			public void checkExistedIDs(List<String> existedIDs, String[] ids) {
				want.collection(existedIDs).notNull();
			}
		};
		ScanIbatis_V2Impl.main(new String[] { baseUrl });
	}

	@Test(description = "测试命令行参数异常情况", dataProvider = "mainData_IllegalParameter")
	public void testMain_IllegalParameter(String[] args) {
		try {
			ScanIbatis_V2Impl.main(args);
			want.fail("异常情况测试，代码不应该运行到这行");
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("命令行参数个数不对");
		}
	}

	@DataProvider
	public Object[][] mainData_IllegalParameter() {
		return new Object[][] { { null },// <br>
				{ new String[] {} },// <br>
				{ new String[] { "", "" } } };
	}
}
