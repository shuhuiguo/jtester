package org.jtester.junit.internal;

import java.util.Iterator;

import org.jtester.beans.DataIterator;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class FrameworkMethodWithParametersTest {
	/**
	 * 本测试用来预防junit框架中的无法识别字符<br>
	 * 测试参数化测试中，如果参数含有junit框架无法识别的字符,会导致测试无法运行<br>
	 * 
	 * @param value
	 */
	@Test
	@DataFrom("dataForJunitMethod")
	public void testParameterMethod(String value) {

	}

	public static Iterator dataForJunitMethod() {
		return new DataIterator() {
			{
				data("\n\f");
			}
		};
	}
}
