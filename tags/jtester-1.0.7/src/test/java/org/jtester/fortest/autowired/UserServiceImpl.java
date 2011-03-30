package org.jtester.fortest.autowired;

import org.jtester.fortest.beans.User;
import org.jtester.utility.JTesterLogger;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	public void insertUser(User user) {
		JTesterLogger.info("actual service insertUser executed!");
		userDao.insertUser(user);
	}
}
