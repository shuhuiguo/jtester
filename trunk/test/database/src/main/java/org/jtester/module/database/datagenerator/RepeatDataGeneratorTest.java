package org.jtester.module.database.datagenerator;

import java.util.Iterator;

import org.jtester.IAssertion;
import org.jtester.beans.DataGenerator;
import org.jtester.beans.DataIterator;
import org.jtester.beans.generator.RepeatDataGenerator;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class RepeatDataGeneratorTest implements IAssertion {

	@Test
	@DataFrom("dataRepeat")
	public void testRepeatDataGenerator(int index, Object expected) {
		DataGenerator generator = RepeatDataGenerator.repeat("a", "b", "c", "d");
		Object o = generator.generate(index);
		want.object(o).isEqualTo(expected);
	}

	public static Iterator dataRepeat() {
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
