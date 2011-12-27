package org.jtester.module.database.datagenerator;

import java.util.Iterator;

import org.jtester.IAssertion;
import org.jtester.beans.DataGenerator;
import org.jtester.beans.DataIterator;
import org.jtester.beans.generator.RandomDataGenerator;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class RandomDataGeneratorTest implements IAssertion {

	@Test
	@DataFrom("dataRandom")
	public void testRandomDataGenerator(Class type, int index) {
		DataGenerator generator = RandomDataGenerator.random(type);
		Object o = generator.generate(index);
		want.object(o).notNull();
		System.out.println(o);
	}

	public static Iterator dataRandom() {
		return new DataIterator() {
			{
				data(Integer.class, 10);
				data(String.class, 3);
			}
		};
	}
}
