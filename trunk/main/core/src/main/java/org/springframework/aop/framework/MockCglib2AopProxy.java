package org.springframework.aop.framework;

import mockit.Instantiation;
import mockit.Mock;
import mockit.MockClass;
import net.sf.cglib.proxy.Enhancer;

import org.springframework.aop.framework.Cglib2AopProxy;

@MockClass(realClass = Cglib2AopProxy.class, instantiation = Instantiation.PerMockSetup)
public class MockCglib2AopProxy {
	@Mock
	public Enhancer createEnhancer() {
		Enhancer enhancer = new Enhancer();
		enhancer.setUseCache(false);
		return enhancer;
	}
}
