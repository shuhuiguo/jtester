package org.jtester.fortest.autowired;

import org.apache.log4j.Logger;
import org.jtester.fortest.beans.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements IUserService {
	private final static Logger log4j = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserDao userDao;

	public void insertUser(User user) {
		log4j.info("actual service insertUser executed!");
		userDao.insertUser(user);
	}
}
