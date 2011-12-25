package org.jtester.beans;

import java.util.List;

public interface UserService {
	public double paySalary(String postcode);

	public void insertUser(User user);

	public void insertUserWillException(User user) throws Exception;

	public void insertUserException(User user) throws Exception;

	public String getServiceName();

	public List<User> findAllUser();
}
