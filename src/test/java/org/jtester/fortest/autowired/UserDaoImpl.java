package org.jtester.fortest.autowired;

import org.apache.log4j.Logger;
import org.jtester.fortest.beans.User;

public class UserDaoImpl implements IUserDao {
	private final static Logger log4j = Logger.getLogger(UserDaoImpl.class);

	public void insertUser(User user) {
		log4j.info("user dao insert");
	}
}
