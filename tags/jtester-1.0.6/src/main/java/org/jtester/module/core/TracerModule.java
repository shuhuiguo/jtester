package org.jtester.module.core;

import java.lang.reflect.Method;
import java.util.Properties;

import org.jtester.annotations.Tracer;
import org.jtester.module.TestListener;
import org.jtester.module.tracer.TracerManager;
import org.jtester.utility.AnnotationUtils;

public class TracerModule implements Module {

	public void afterInit() {
	}

	public TestListener getTestListener() {
		return new TracerListener();
	}

	public void init(Properties configuration) {
	}

	protected class TracerListener extends TestListener {
		public void beforeTestMethod(Object testObject, Method testMethod) {
			Tracer tracer = AnnotationUtils.getMethodOrClassLevelAnnotation(Tracer.class, testMethod, testObject
					.getClass());
			TracerManager.startTracer(tracer, testObject.getClass(), testMethod);
		}

		public void afterTestMethod(Object testObject, Method testMethod, Throwable testThrowable) {
			TracerManager.endTracer(testObject.getClass(), testMethod.getName());
		}
	}
}
