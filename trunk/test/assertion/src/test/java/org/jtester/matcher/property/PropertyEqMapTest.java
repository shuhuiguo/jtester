package org.jtester.matcher.property;

import org.jtester.IAssertion;
import org.jtester.beans.DataMap;
import org.jtester.beans.User;
import org.jtester.matcher.property.reflection.EqMode;
import org.junit.Test;

@SuppressWarnings("serial")
public class PropertyEqMapTest implements IAssertion {
	@Test(expected = AssertionError.class)
	public void testPropertyEqMap() {
		User user = new User();
		user.setAssistor(User.newUser("siri", new String[] { "139xxx", "159xxx" }));
		want.object(user).propertyEqMap(new DataMap() {
			{
				this.put("assistor.phones", new String[] { "133xxx", "131xxx" });
			}
		});
	}

	@Test
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
						new String[] { "130xxx", "0571xx" },// <br>
						new String[] { "159xxx", "139xxx" });
			}
		}, EqMode.IGNORE_ORDER);
	}
}
