package org.jtester.spec.steps;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jbehave.core.annotations.Named;

public class JSpecHelper {
	/**
	 * ��Spec�ķ�������"methodname $paraname $paraname"�ַ���
	 * 
	 * @param method
	 * @return
	 */
	public static String getPatternAsStringFromMethod(Method method) {
		StringBuilder builder = new StringBuilder();
		builder.append(method.getName());
		Annotation[][] paraAnnotations = method.getParameterAnnotations();
		for (int index = 0; index < paraAnnotations.length; index++) {
			Annotation[] annotations = paraAnnotations[index];
			String name = annotationName(annotations, index + 1);
			builder.append(" ").append(name);
		}
		return builder.toString();
	}

	private static String annotationName(Annotation[] annotations, int index) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAssignableFrom(Named.class)) {
				return "$" + ((Named) annotation).value();
			}
		}
		return "$" + index;
	}

	/**
	 * ���ݴ���Ĳ��������ļ���������Ӧ�ı������ƺͱ���ֵ
	 * 
	 * @param stepWithoutStartingWord
	 * @return
	 */
	public static LinkedHashMap<String, String> parserParameter(String stepWithoutStartingWord) {
		LinkedHashMap<String, String> paras = new LinkedHashMap<String, String>();
		char[] words = (stepWithoutStartingWord + "\0").toCharArray();
		int count = 1;
		for (int index = 0; index < words.length;) {
			char ch = words[index];
			if (ch == '$' && words[index + 1] == '{') {
				index = parseSinglePara(paras, words, index + 2, String.valueOf(count++));
			}
			index++;
		}
		return paras;
	}

	/**
	 * ���������������ƺ�ֵ�����ؽ���������λ��
	 * 
	 * @param paras
	 *            �����б�
	 * @param words
	 *            ���������ַ���
	 * @param start
	 *            ������ʼλ��
	 * @param defaultKey
	 *            Ĭ�ϵ�key name
	 * @return
	 */
	static int parseSinglePara(Map<String, String> paras, char[] words, int start, String defaultKey) {
		StringBuilder buff = new StringBuilder();
		int wordsIndex = start;
		int equalsIndex = 0;

		boolean hasQuato = false;
		boolean ignoreRested = false;
		for (int pos = start; pos < words.length; pos++) {
			char ch = words[pos];
			if (ch == '\\') {
				pos++;
				ch = words[pos];
				buff.append(ch);
				continue;
			} else if (ch == '}' || ch == '\0') {
				break;
			}
			wordsIndex = pos;
			if (ignoreRested) {
				continue;
			}
			// ������'"'ʱ����û��'='����ʾû����ʽָ����������
			if (ch == '"' && equalsIndex == 0) {
				equalsIndex = -1;
				if (pos == start) {
					hasQuato = true;
				}
			} else if (ch == '=' && equalsIndex == 0) {
				equalsIndex = pos - start;
				if (words[pos + 1] == '"') {
					hasQuato = true;
				}
			} else if (ch == '|') {
				ignoreRested = true;
				continue;
			}
			buff.append(ch);
		}
		String para = buff.toString();
		String key = defaultKey;
		if (equalsIndex > 0) {
			key = para.substring(0, equalsIndex);
			para = para.substring(equalsIndex + 1);
		}
		String value = para;
		if (hasQuato) {
			value = para.substring(1, para.length() - 1);
		}
		paras.put(key, value);
		return wordsIndex + 1;
	}
}
