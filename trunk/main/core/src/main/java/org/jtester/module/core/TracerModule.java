package org.jtester.module.core;

import java.lang.reflect.Method;

import org.jtester.annotations.Tracer;
import org.jtester.helper.AnnotationHelper;
import org.jtester.module.Module;
import org.jtester.module.TestListener;
import org.jtester.module.tracer.TracerManager;

public class TracerModule implements Module {
	public void init() {
	}

	public void afterInit() {
	}

	public TestListener getTestListener() {
		return new TracerTestListener();
	}

	protected class TracerTestListener extends TestListener {
		@Override
		public void beforeRunning(Object testObject, Method testMethod) {
			Tracer tracer = AnnotationHelper.getMethodOrClassLevelAnnotation(Tracer.class, testMethod,
					testObject.getClass());
			TracerManager.startTracer(tracer, testObject.getClass(), testMethod);
		}

		@Override
		public void afterRunned(Object testObject, Method testMethod, Throwable testThrowable) {
			TracerManager.endTracer(testObject.getClass(), testMethod.getName());
		}

		@Override
		protected String getName() {
			return "TracerTestListener";
		}
	}
}
