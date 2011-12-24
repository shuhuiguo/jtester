package org.jtester.module.database.datagenerator;

import java.util.Iterator;

import org.jtester.beans.DataIterator;
import org.jtester.beans.dataset.AbastractDataGenerator;
import org.jtester.beans.dataset.RepeatDataGenerator;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
@Test(groups = { "jtester", "tools" })
public class RepeatDataGeneratorTest implements IAssertion {

	@Test(dataProvider = "dataRepeat")
	public void testRepeatDataGenerator(int index, Object expected) {
		AbastractDataGenerator generator = RepeatDataGenerator.repeat("a", "b", "c", "d");
		Object o = generator.generate(index);
		want.object(o).isEqualTo(expected);
	}

	@DataProvider
	public Iterator dataRepeat() {
		return new DataIterator() {
			{
				data(0, "a");
				data(3, "d");
				data(4, "a");
				data(5, "b");
				data(11, "d");
			}
		};
	}
}
