package org.jtester.hamcrest.matcher.property;

import org.jtester.beans.DataMap;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "assertion")
@SuppressWarnings("serial")
public class PropertyEqMapTest extends JTester {
	@Test(expectedExceptions = AssertionError.class)
	public void testPropertyEqMap() {
		User user = new User();
		user.setAssistor(User.newUser("siri", new String[] { "139xxx", "159xxx" }));
		want.object(user).propertyEqMap(new DataMap() {
			{
				this.put("assistor.phones", new String[] { "133xxx", "131xxx" });
			}
		});
	}

	public void testPropertyEqMap_List() {
		User[] users = new User[] { new User() {
			{
				setAssistor(User.newUser("siri", new String[] { "139xxx", "159xxx" }));
			}
		}, new User() {
			{
				setAssistor(User.newUser("wade", new String[] { "130xxx", "0571xx" }));
			}
		} };
		want.list(users).propertyEqMap(2, new DataMap() {
			{
				this.put("assistor.phones",// <br>
						new String[] { "133xxx", "131xxx" },// <br>
						new String[] { "130xxx", "0571xx" });
			}
		});
	}
}
