package org.jtester.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jtester.exception.JTesterException;
import org.jtester.fortest.beans.Employee;
import org.jtester.fortest.beans.Manager;
import org.jtester.fortest.beans.PhoneNumber;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class SerializeUtilTest extends JTester {
	private static String tempDir = System.getProperty("java.io.tmpdir");

	@Test
	public void toDat() {
		String filename = tempDir + "/manager.dat";
		SerializeUtil.toDat(this.mock(), filename);
		want.file(filename).isExists();
	}

	@Test(dependsOnMethods = { "toDat" })
	public void fromDat() {
		String filename = "file:" + tempDir + "/manager.dat";
		want.file(filename).isExists();

		Manager manager = SerializeUtil.fromDat(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester");
	}

	@Test
	public void fromDat_Classpath() {
		String filename = "classpath:org/jtester/utility/manager.dat";
		Manager manager = SerializeUtil.fromDat(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).yearIs(2009).monthIs("08").hourIs(16);
	}

	@Test
	public void toDat_List() {
		String filename = tempDir + "/managers.dat";
		List<?> list = Arrays.asList(mock(), mock());
		SerializeUtil.toDat(list, filename);
		want.file(filename).isExists();
	}

	@Test(expectedExceptions = { JTesterException.class })
	public void fromDat_List() {
		String filename = "classpath:org/jtester/utility/manager_classnotfound.dat";
		List<?> managers = SerializeUtil.fromDat(List.class, filename);
		want.collection(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	@Test
	public void toXML() {
		String filename = tempDir + "/manager.xml";
		// want.file(filename).unExists();
		SerializeUtil.toXML(this.mock(), filename);
		want.file(filename).isExists();
	}

	@Test(dependsOnMethods = { "toXML" })
	public void fromXML() {
		String filename = "file:" + tempDir + "/manager.xml";
		want.file(filename).isExists();

		Manager manager = SerializeUtil.fromXML(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester");
	}

	@Test
	public void fromXML_Classpath() {
		String filename = "classpath:org/jtester/utility/manager.xml";
		Manager manager = SerializeUtil.fromXML(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).yearIs(2009).monthIs("08").hourIs(16);
	}

	@Test
	public void fromXML_Classpath2() {
		Manager manager = SerializeUtil.fromXML(Manager.class, SerializeUtilTest.class, "manager.xml");
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).yearIs(2009).monthIs("08").hourIs(16);
	}

	@Test
	public void toXML_List() {
		String filename = tempDir + "/managers.xml";
		List<Manager> list = new ArrayList<Manager>();
		list.add(mock());
		list.add(mock());
		SerializeUtil.toXML(list, filename);
		want.file(filename).isExists();
	}

	@Test
	public void fromXML_List() {
		String filename = "classpath:org/jtester/utility/managers.xml";
		List<?> managers = SerializeUtil.fromXML(List.class, filename);
		want.collection(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	@Test
	public void fromXML_List2() {
		String filename = "classpath:org/jtester/utility/managers2.xml";
		List<?> managers = SerializeUtil.fromXML(List.class, filename);
		want.collection(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	@Test
	public void getPojoToXml_Array() {
		Manager[] managers = new Manager[2];
		managers[0] = mock();
		managers[1] = mock();
		SerializeUtil.toXML(managers, tempDir + "/managers-array.xml");
	}

	@Test
	public void fromXML_Array() {
		Manager[] managers = SerializeUtil.fromXML(Manager[].class, "classpath:org/jtester/utility/managers-array.xml");
		want.array(managers).sizeEq(2).propertyEq("name", new String[] { "Tony Tester", "Tony Tester" });
	}

	private Manager mock() {
		Employee harry = new Employee("Harry Hacker", 50000);
		Manager manager = new Manager("Tony Tester", 80000);
		PhoneNumber phone = new PhoneNumber(571, "0571-88886666");
		manager.setSecretary(harry);
		manager.setPhoneNumber(phone);
		manager.setDate(new Date());
		return manager;
	}
}
