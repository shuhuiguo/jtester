package org.jtester.fortest.autowired;

import org.jtester.fortest.beans.User;
import org.jtester.utility.JTesterLogger;

public class UserDaoImpl implements IUserDao {

	public void insertUser(User user) {
		JTesterLogger.info("user dao insert");
	}
}
