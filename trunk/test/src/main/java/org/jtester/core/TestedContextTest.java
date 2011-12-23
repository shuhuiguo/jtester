package org.jtester.core;

import mockit.NonStrict;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SpringApplicationContext
@AutoBeanInject
public class TestedContextTest extends JTester {
	@SpringBeanByName(init = "init")
	TestedService testedService;

	@SpringBeanFrom
	@NonStrict
	TestedDao testedDao;

	@Test
	public void testSetContext() {
		new Expectations() {
			{
				testedDao.sayNo();
				result = "mock";
			}
		};
		String word = this.testedService.sayNo();
		want.string(word).isNull();
	}
}

class TestedService {
	private TestedDao testedDao;

	public TestedDao getTestedDao() {
		return testedDao;
	}

	public void setTestedDao(TestedDao testedDao) {
		this.testedDao = testedDao;
	}

	public String sayNo() {
		return word;
	}

	private String word;

	public void init() {
		this.word = this.testedDao.sayNo();
	}
}

class TestedDao {
	public String sayNo() {
		return "no";
	}
}
