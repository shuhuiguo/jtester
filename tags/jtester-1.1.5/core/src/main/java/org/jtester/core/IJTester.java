package org.jtester.core;

import java.util.LinkedHashMap;

import org.jtester.core.context.JTesterFitnesse;
import org.jtester.core.context.JTesterReflector;
import org.jtester.core.context.TestedSpringContext;
import org.jtester.hamcrest.TheStyleAssertion;
import org.jtester.hamcrest.WantStyleAssertion;
import org.jtester.module.database.datagenerator.AbastractDataGenerator;
import org.jtester.module.database.dbop.AbstractDataSet;
import org.jtester.module.database.dbop.DBOperator;
import org.jtester.module.database.dbop.IDBOperator;

@SuppressWarnings("rawtypes")
public interface IJTester {

	final WantStyleAssertion want = new WantStyleAssertion();

	final TheStyleAssertion the = new TheStyleAssertion();

	final JTesterFitnesse fit = new JTesterFitnesse();

	final IDBOperator db = new DBOperator();

	final JTesterReflector reflector = new JTesterReflector();

	final TestedSpringContext spring = new TestedSpringContext();

	class Expectations extends org.jtester.module.jmockit.JMockitExpectations {

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

	class NonStrictExpectations extends org.jtester.module.jmockit.JMockitNonStrictExpectations {

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
	 * 
	 */
	public static abstract class DataGenerator extends AbastractDataGenerator {
	}

	public static class DataMap extends LinkedHashMap<String, Object> {
		private static final long serialVersionUID = 1L;
	}

	public static abstract class DataSet extends AbstractDataSet {
	}
}
