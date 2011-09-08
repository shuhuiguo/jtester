package org.jtester.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jtester.exception.JTesterException;
import org.jtester.fortest.beans.Manager;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class JsonHelperTest extends JTester {
	private static String tempDir = System.getProperty("java.io.tmpdir");

	@Test
	public void toDat() {
		String filename = tempDir + "/manager.dat";
		JsonHelper.toDatFile(Manager.mock(), filename);
		want.file(filename).isExists();
	}

	@Test(dependsOnMethods = { "toDat" })
	public void fromDat() {
		String filename = "file:" + tempDir + "/manager.dat";
		want.file(filename).isExists();

		Manager manager = JsonHelper.fromDatFile(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester");
	}

	@Test
	public void fromDat_Classpath() {
		String filename = "classpath:org/jtester/utility/manager.dat";
		Manager manager = JsonHelper.fromDatFile(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).isYear(2009).isMonth("08").isHour(16);
	}

	@Test
	public void toDat_List() {
		String filename = tempDir + "/managers.dat";
		List<?> list = Arrays.asList(Manager.mock(), Manager.mock());
		JsonHelper.toDatFile(list, filename);
		want.file(filename).isExists();
	}

	@Test(expectedExceptions = { JTesterException.class })
	public void fromDat_List() {
		String filename = "classpath:org/jtester/utility/manager_classnotfound.dat";
		List<?> managers = JsonHelper.fromDatFile(List.class, filename);
		want.collection(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	@Test
	public void toJSON() {
		String filename = tempDir + "/manager.json";
		// want.file(filename).unExists();
		JsonHelper.toJsonFile(Manager.mock(), filename);
		want.file(filename).isExists();
	}

	@Test(dependsOnMethods = { "toJSON" })
	public void fromJSON() {
		String filename = "file:" + tempDir + "/manager.json";
		want.file(filename).isExists();

		Manager manager = JsonHelper.fromJsonFile(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester");
	}

	@Test
	public void fromJSON_Classpath() {
		String filename = "classpath:org/jtester/utility/manager.json";
		Manager manager = JsonHelper.fromJsonFile(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).isYear(2009).isMonth("08").isHour(16);
	}

	@Test
	public void fromJSON_Classpath2() {
		Manager manager = JsonHelper.fromJsonFile(Manager.class, JsonHelperTest.class, "manager.json");
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).isYear(2009).isMonth("08").isHour(16);
	}

	@Test
	public void toJSON_List() {
		String filename = tempDir + "/managers.xml";
		List<Manager> list = new ArrayList<Manager>();
		list.add(Manager.mock());
		list.add(Manager.mock());
		JsonHelper.toJsonFile(list, filename);
		want.file(filename).isExists();
	}

	@Test
	public void fromJSON_List() {
		String filename = "classpath:org/jtester/utility/managers.json";
		List<?> managers = JsonHelper.fromJsonFile(ArrayList.class, filename);
		want.collection(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	@Test
	public void fromJSON_List2() {
		String filename = "classpath:org/jtester/utility/managers2.json";
		List<?> managers = JsonHelper.fromJsonFile(ArrayList.class, filename);
		want.collection(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	@Test
	public void getPojoToXml_Array() {
		Manager[] managers = new Manager[2];
		managers[0] = Manager.mock();
		managers[1] = Manager.mock();
		JsonHelper.toJsonFile(managers, tempDir + "/managers-array.xml");
	}

	@Test
	public void fromJSON_Array() {
		Manager[] managers = JsonHelper.fromJsonFile(Manager[].class,
				"classpath:org/jtester/utility/managers-array.json");
		want.array(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}
}
