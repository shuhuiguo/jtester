package org.jtester.tutorial.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.service.CustomerDao;
import org.jtester.tutorial.biz.service.CustomerDaoImpl;
import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial.biz.service.CustomerServiceImpl;
import org.testng.annotations.Test;

@SuppressWarnings("serial")
@Test(description = "动态注册spring bean演示")
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class SpringBeanFromDemo extends JTester {

	@SpringBeanByName(claz = CustomerServiceEx.class)
	CustomerService customerService;

	@SpringBeanFrom
	CustomerDao customerDao = new CustomerDaoImpl() {
		@Override
		public String doNothing() {
			return "manually new dao.";
		}
	};

	@SpringBeanFrom
	Map<String, String> myMap = new HashMap<String, String>() {
		{
			this.put("key1", "value1");
			this.put("key2", "value2");
		}
	};

	@SpringBeanFrom
	List<String> myList = new ArrayList<String>() {
		{
			this.add("value1");
			this.add("value2");
		}
	};

	@Test(description = "演示@SpringBeanFrom功能")
	public void testSpringBeanFromBean() {
		String result = this.customerService.doNothing();
		want.string(result).isEqualTo("the service call dao:manually new dao.");

		Map<String, String> map = reflector.getField(customerService, "myMap");
		want.map(map).sizeEq(2).hasKeys("key1", "key2");
	}

	@Test(description = "演示@SpringBeanFrom对应的Field字段值被改变的情况", dependsOnMethods = "testSpringBeanFromBean")
	public void testSpringBeanFromFieldHasModified() {
		this.customerDao = new CustomerDaoImpl() {
			@Override
			public String doNothing() {
				return "promatic manually new dao.";
			}
		};
		String result = this.customerService.doNothing();
		want.string(result).isEqualTo("the service call dao:promatic manually new dao.");
	}

	@Test(description = "思考：如果@SpringBeanFrom对应的字段没有初始化或被赋值为null?", dependsOnMethods = "testSpringBeanFromFieldHasModified")
	public void testSpringBeanFromNullField() {
		this.customerDao = null;
		try {
			this.customerService.doNothing();
			want.fail("上面的调用肯定抛出了Null异常，这里不会被调到!");
		} catch (NullPointerException e) {

		}
	}

	public static class CustomerServiceEx extends CustomerServiceImpl {
		private Map<String, String> myMap;

		private List<String> myList;

		@Override
		public String doNothing() {
			return "the service call dao:" + this.customerDao.doNothing();
		}

		public Map<String, String> getMyMap() {
			return myMap;
		}

		public void setMyMap(Map<String, String> myMap) {
			this.myMap = myMap;
		}

		public List<String> getMyList() {
			return myList;
		}

		public void setMyList(List<String> myList) {
			this.myList = myList;
		}
	}
}
