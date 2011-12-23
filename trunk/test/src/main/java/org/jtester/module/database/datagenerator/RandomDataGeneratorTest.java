package org.jtester.module.database.datagenerator;

import java.util.Iterator;

import org.jtester.beans.dataset.AbastractDataGenerator;
import org.jtester.beans.dataset.RandomDataGenerator;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
@Test(groups = { "jtester", "tools" })
public class RandomDataGeneratorTest extends JTester {

	@Test(dataProvider = "dataRandom")
	public void testRandomDataGenerator(Class type, int index) {
		AbastractDataGenerator generator = RandomDataGenerator.random(type);
		Object o = generator.generate(index);
		want.object(o).notNull();
		System.out.println(o);
	}

	@DataProvider
	public Iterator dataRandom() {
		return new DataIterator() {
			{
				data(Integer.class, 10);
				data(String.class, 3);
			}
		};
	}
}
