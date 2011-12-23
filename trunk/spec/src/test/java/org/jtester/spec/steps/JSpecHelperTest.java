package org.jtester.spec.steps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jtester.beans.DataIterator;
import org.jtester.spec.steps.JSpecStepPatternParser.JSpecStepMatcher;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
public class JSpecHelperTest extends JTester {
	@Test(dataProvider = "dataForMethodNameFromStep")
	public void testGetMethodNameFromStep(String stepWithoutStartingWord, String expected) {
		JSpecStepMatcher matcher = reflector.newInstance(JSpecStepMatcher.class);
		reflector.setField(matcher, "items", new String[] { expected });
		boolean matched = matcher.matches(stepWithoutStartingWord);
		want.bool(matched).isEqualTo(true);
	}

	@DataProvider
	public Iterator dataForMethodNameFromStep() {
		return new DataIterator() {
			{
				data("i am method", "iAmMethod");
				data("the method\n description", "theMethod");
				data("the method \n description", "theMethod");
			}
		};
	}

	@Test(dataProvider = "dataForParserSinglePara")
	public void testParseSinglePara(String words, String key, String value, int expectedIndex) {
		Map<String, String> paras = new HashMap<String, String>();
		int index = JSpecHelper.parseSinglePara(paras, words.toCharArray(), 0, "1");
		want.map(paras).hasEntry(key, value);
		want.number(index).isEqualTo(expectedIndex);
	}

	@DataProvider
	public Iterator dataForParserSinglePara() {
		return new DataIterator() {
			{
				data("customerId=10023", "customerId", "10023", 16);
				data("id=\"abc\"|说明}", "id", "abc", 11);
				data("id=\"a\\|bc\"|说明}", "id", "a|bc", 13);
				data("id=\"a\\}bc\"|说明}", "id", "a}bc", 13);
				data("id=\"a\\\\bc\"|说明}", "id", "a\\bc", 13);
				data("id=\"a\\\"bc\"|说明}", "id", "a\"bc", 13);
				data("abc", "1", "abc", 3);
				data("\"=abc\"", "1", "=abc", 6);
				data("\"\\}bc\"", "1", "}bc", 6);
			}
		};
	}

	@Test(dataProvider = "dataForParserParameter")
	public void testParserParameter(String words, Map<String, String> expected) {
		Map<String, String> maps = JSpecHelper.parserParameter(words);
		want.map(maps).reflectionEq(expected);
	}

	@SuppressWarnings("serial")
	@DataProvider
	public Iterator dataForParserParameter() {
		return new DataIterator() {
			{
				data("do${value1}in${value2}", new HashMap<String, String>() {
					{
						put("1", "value1");
						put("2", "value2");
					}
				});

				data("do${key1=value1|说明}in${value2", new HashMap<String, String>() {
					{
						put("key1", "value1");
						put("2", "value2");
					}
				});

				data("do${key1=va\\}lue1|说明}in${value2", new HashMap<String, String>() {
					{
						put("key1", "va}lue1");
						put("2", "value2");
					}
				});
			}
		};
	}
}
