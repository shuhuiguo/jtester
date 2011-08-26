package org.jtester.module.database.util;

import java.io.FileNotFoundException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import mockit.Mock;

import org.jtester.testng.JTester;
import org.jtester.utility.ResourceHelper;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class DBHelperTest extends JTester {

	@Test
	public void testParseSQL() throws FileNotFoundException {
		String lines = ResourceHelper
				.readFromFile("org/jtester/module/database/util/data/SqlRunnerTest/testExecuteFromFile.sql");
		String[] statments = DBHelper.parseSQL(lines);
		want.array(statments).sizeEq(6);
	}

	@Test
	public void testParseSQL_SINGLE_LINE_NOTE_REGEX() {
		String line = "insert into tdd_user(id,first_name,last_name,my_date,sarary)--本行注释\n"
				+ "values(3,'name3','last3','2011-03-20',2336);";
		String[] statments = DBHelper.parseSQL(line);
		want.array(statments)
				.sizeEq(1)
				.hasItems(
						"insert into tdd_user(id,first_name,last_name,my_date,sarary) values(3,'name3','last3','2011-03-20',2336)");
	}

	@Test
	public void testParseSQL_MULTI_LINE_NOTE_REGEX() {
		String line = "insert into tdd_user(id,first_name,last_name,my_date,sarary)\n"
				+ "values(2,'name2','last2',/**插入注释**/'2011-03-19',2335);";
		String[] statments = DBHelper.parseSQL(line);
		want.array(statments)
				.sizeEq(1)
				.hasItems(
						"insert into tdd_user(id,first_name,last_name,my_date,sarary) values(2,'name2','last2','2011-03-19',2335)");
	}

	@Test
	public void testParseSQL_插入值有分行() {
		String line = "insert into tdd_user(id,first_name,last_name,my_date,sarary)\n"
				+ "values(3,'name\n3','last3','2011-03-20',2336);";
		String statement = "insert into tdd_user(id,first_name,last_name,my_date,sarary) values(3,'name\n3','last3','2011-03-20',2336)";
		String[] statments = DBHelper.parseSQL(line);
		want.array(statments).sizeEq(1);
		want.string(statments[0]).isEqualTo(statement);
	}

	@Test(description = "测试数据库字段为大写的时候，返回的camelName的正确性")
	public void testGetCamelFieldName() {
		ResultSetMetaData rsmeta = new MockUp<ResultSetMetaData>() {
			@SuppressWarnings("unused")
			@Mock
			public String getColumnName(int column) throws SQLException {
				return "UUID_NAME";
			}
		}.getMockInstance();
		String uuid = reflector.invoke(DBHelper.class, "getCamelFieldName", rsmeta, 1, true);
		want.string(uuid).isEqualTo("uuidName");
	}
}
