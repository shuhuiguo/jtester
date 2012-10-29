package org.jtester.module.spring.utility;


/**
 * 测试类中spring上下文
 * 
 * @author darui.wudr
 * 
 */
public class SpringTestHelper {

	public SpringTestHelper() {
	}

	/**
	 * 如果spring容器有效，返回名称为beanname的spring bean
	 * 
	 * @param beanname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanname) {
		return (T) SpringModuleHelper.getBeanByName(beanname);
	}

	/**
	 * 使原先的spring容器失效，重新初始化容器
	 */
	public void invalidate() {
		SpringModuleHelper.invalidateApplicationContext();
	}
}
