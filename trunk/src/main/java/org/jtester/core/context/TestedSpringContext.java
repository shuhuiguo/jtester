package org.jtester.core.context;

import org.jtester.module.ModulesRepository;
import org.jtester.module.core.SpringModule;
import org.jtester.module.utils.SpringModuleHelper;
import org.jtester.module.utils.ModuleHelper;

/**
 * 测试类中spring上下文
 * 
 * @author darui.wudr
 * 
 */
public class TestedSpringContext {

	final boolean springEnabled;

	private final Object testedObject;

	public TestedSpringContext(Object testedObject) {
		ModulesRepository modulesRepository = ModuleHelper.getInstance().getModulesRepository();
		springEnabled = modulesRepository.isModuleEnabled(SpringModule.class);
		this.testedObject = testedObject;
		if (testedObject == null) {
			throw new RuntimeException("testeObject can't be null!");
		}
	}

	/**
	 * 如果spring容器有效，返回名称为beanname的spring bean
	 * 
	 * @param beanname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanname) {
		if (springEnabled == false) {
			throw new RuntimeException("spring module isn't enabled");
		}

		return (T) SpringModuleHelper.getBeanByName(testedObject, beanname);
	}

	/**
	 * 使原先的spring容器失效，重新初始化容器
	 */
	public void invalidate() {
		if (springEnabled == false) {
			throw new RuntimeException("spring module isn't enabled");
		}
		SpringModuleHelper.invalidateApplicationContext();
	}

}
