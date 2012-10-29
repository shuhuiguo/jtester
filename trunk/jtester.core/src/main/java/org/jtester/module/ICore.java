package org.jtester.module;

import org.jtester.hamcrest.TheStyleAssertion;
import org.jtester.hamcrest.WantStyleAssertion;
import org.jtester.tools.commons.Reflector;
import org.jtester.tools.datagen.AbastractDataGenerator;
import org.jtester.tools.datagen.AbstractDataMap;
import org.jtester.tools.datagen.DataProviderIterator;

@SuppressWarnings({ "rawtypes" })
public interface ICore {

	final WantStyleAssertion want = new WantStyleAssertion();

	final TheStyleAssertion the = new TheStyleAssertion();

	final Reflector reflector = Reflector.instance;

	class Expectations extends org.jtester.module.jmockit.extend.JMockitExpectations {

		public Expectations() {
			super();
		}

		public Expectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
			super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		}

		public Expectations(Object... classesOrObjectsToBePartiallyMocked) {
			super(classesOrObjectsToBePartiallyMocked);
		}

	}

	class NonStrictExpectations extends org.jtester.module.jmockit.extend.JMockitNonStrictExpectations {

		public NonStrictExpectations() {
			super();
		}

		public NonStrictExpectations(int numberOfIterations, Object... classesOrObjectsToBePartiallyMocked) {
			super(numberOfIterations, classesOrObjectsToBePartiallyMocked);
		}

		public NonStrictExpectations(Object... classesOrObjectsToBePartiallyMocked) {
			super(classesOrObjectsToBePartiallyMocked);
		}
	}

	class MockUp<T> extends mockit.MockUp<T> {

		public MockUp() {
			super();
		}

		public MockUp(Class classToMock) {
			super(classToMock);
		}
	}

	/**
	 * 数据生成器<br>
	 * index计数从0开始
	 * 
	 * @author darui.wudr
	 */
	public static abstract class DataGenerator extends AbastractDataGenerator {
	}

	public static class DataMap extends AbstractDataMap {

		static final long serialVersionUID = 1L;
	}

	public static class DataIterator extends DataProviderIterator {
	}
}
