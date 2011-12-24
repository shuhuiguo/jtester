package org.jtester.core;

import org.jtester.IAssertion;
import org.jtester.beans.AbstractDataSet;
import org.jtester.beans.dataset.AbastractDataGenerator;
import org.jtester.core.context.JTesterReflector;
import org.jtester.database.JTesterFitnesse;
import org.jtester.module.database.dbop.DBOperator;
import org.jtester.module.database.dbop.IDBOperator;
import org.jtester.module.database.dbop.InsertOp;
import org.jtester.spring.TestedSpringContext;

@SuppressWarnings("rawtypes")
public interface IJTester extends IAssertion {

	final JTesterFitnesse fit = new JTesterFitnesse();

	final IDBOperator db = new DBOperator();

	final JTesterReflector reflector = new JTesterReflector();

	final TestedSpringContext spring = new TestedSpringContext();

	class Expectations extends org.jtester.jmockit.JMockitExpectations {

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

	class NonStrictExpectations extends org.jtester.jmockit.JMockitNonStrictExpectations {

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

	public static abstract class DataSet extends AbstractDataSet {
		/**
		 * 插入列表中的数据集<br>
		 * 插入完毕后列表不做清空，方便重用
		 * 
		 * @param table
		 */
		public void insert(String table) {
			for (org.jtester.beans.DataMap map : this.datas) {
				InsertOp.insert(table, map);
			}
		}
	}
}
