/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Allow for any character in a name, such as a field, method or action. Ensure
 * that the name is not a Java key word. Map any characters that are not valid
 * in a Java identifier into a word that is, in camel case. This includes
 * Unicode.
 */
public class ExtendedCamelCase {
	final static String[] map = {// <br>
	"!", " bang ", /** <br> **/
	"\"", " quote ", /** <br> **/
	"#", " hash ", /** <br> **/
	"$", " dollar ", /** <br> **/
	"%", " percent ", /** <br> **/
	"&", " ampersand ", /** <br> **/
	"'", " single quote ", /** <br> **/
	"(", " left parenthesis ", /** <br> **/
	")", " right parenthesis ", /** <br> **/
	"*", " star ", /** <br> **/
	"+", " plus ", /** <br> **/
	",", " comma ", /** <br> **/
	"-", " minus ", /** <br> **/
	".", " dot ", /** <br> **/
	"/", " slash ", /** <br> **/
	":", " colon ", /** <br> **/
	";", " semicolon ", /** <br> **/
	"<", " less than ", /** <br> **/
	">", " greater than ", /** <br> **/
	"=", " equals ", /** <br> **/
	"?", " question ", /** <br> **/
	"@", " at ", /** <br> **/
	"[", " left square bracket ", /** <br> **/
	"]", " right square bracket ", /** <br> **/
	"\\", " backslash ", /** <br> **/
	"^", " caret ", /** <br> **/
	"`", " backquote ", /** <br> **/
	"{", " left brace ", /** <br> **/
	"}", " right brace ", /** <br> **/
	"|", " bar ", /** <br> **/
	"~", " tilde " /** <br> **/
	};
	private static final String[] javaKeyWords = {
			// Can these be checked by some Java method?
			"abstract", /** <br> **/
			"assert", /** <br> **/
			"boolean", /** <br> **/
			"break", /** <br> **/
			"byte", /** <br> **/
			"case", /** <br> **/
			"catch", /** <br> **/
			"char", /** <br> **/
			"class", /** <br> **/
			"const", /** <br> **/
			"continue", /** <br> **/
			"default", /** <br> **/
			"do", /** <br> **/
			"double", /** <br> **/
			"else", /** <br> **/
			"enum", /** <br> **/
			"extends", /** <br> **/
			"false", /** <br> **/
			"final", /** <br> **/
			"finally", /** <br> **/
			"float", /** <br> **/
			"for", /** <br> **/
			"goto", /** <br> **/
			"if", /** <br> **/
			"implements", /** <br> **/
			"import", /** <br> **/
			"instanceof", /** <br> **/
			"int", /** <br> **/
			"interface", /** <br> **/
			"long", /** <br> **/
			"native", /** <br> **/
			"new", /** <br> **/
			"null", /** <br> **/
			"package", /** <br> **/
			"private", /** <br> **/
			"protected", /** <br> **/
			"public", /** <br> **/
			"return", /** <br> **/
			"short", /** <br> **/
			"static", /** <br> **/
			"strictfp", /** <br> **/
			"super", /** <br> **/
			"switch", /** <br> **/
			"synchronized", /** <br> **/
			"this", /** <br> **/
			"throw", /** <br> **/
			"throws", /** <br> **/
			"transient", /** <br> **/
			"true", /** <br> **/
			"try", /** <br> **/
			"void", /** <br> **/
			"volatile", /** <br> **/
			"while" };
	private static final Set<String> javaKeywordSet = new HashSet<String>();

	static {
		for (int i = 0; i < javaKeyWords.length; i++)
			javaKeywordSet.add(javaKeyWords[i]);
	}

	@Deprecated
	public static String camel(String s) {
		return camel(s, false);
	}

	public static String camel(String nameInitial, boolean keepUnicode) {
		String name = nameInitial.trim();
		if (name.length() == 0)
			return "blank";
		for (int i = 0; i < map.length; i += 2) {
			String from = map[i];
			String to = map[i + 1];
			while (true) {
				int pos = name.indexOf(from);
				if (pos < 0)
					break;
				name = name.substring(0, pos) + to + name.substring(pos + from.length());
			}
		}
		if (Character.isDigit(name.charAt(0)))
			name = mapNumber(name.charAt(0)) + name.substring(1);
		if (Character.isUpperCase(name.charAt(0)))
			name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		return hideJavaKeyword(translateUnicode(camelise(name), keepUnicode));
	}

	public static String camelClassName(String className, boolean keepUnicode) {
		if (className.indexOf(" ") < 0)
			return className;
		return camel("t " + className, keepUnicode).substring(1);
	}

	private static String camelise(String name) {
		// assert !name.equals("")
		StringTokenizer tokenizer = new StringTokenizer(name);
		String result = tokenizer.nextToken();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			result += token.substring(0, 1).toUpperCase() + token.substring(1);
		}
		return result;
	}

	private static String hideJavaKeyword(String name) {
		if (javaKeywordSet.contains(name))
			return name + "_";
		return name;
	}

	/**
	 * Translate all unicode characters into ASCII, except for valid Java chars
	 * when required.
	 */
	private static String translateUnicode(String name, boolean keepUnicode) {
		StringBuffer b = new StringBuffer(name.length());
		if (!"".equals(name)) {
			char ch = name.charAt(0);
			if (ch < 128 || (keepUnicode && Character.isJavaIdentifierStart(ch)))
				b.append(ch);
			else
				b.append("u" + Integer.toHexString(ch).toUpperCase());
		}
		for (int i = 1; i < name.length(); i++) {
			char ch = name.charAt(i);
			if (ch < 128 || (keepUnicode && Character.isJavaIdentifierPart(ch)))
				b.append(ch);
			else
				b.append("u" + Integer.toHexString(ch).toUpperCase());
		}
		return b.toString();
	}

	private static String mapNumber(char c) {
		switch (c) {
		case '0':
			return "zero ";
		case '1':
			return "one ";
		case '2':
			return "two ";
		case '3':
			return "three ";
		case '4':
			return "four ";
		case '5':
			return "five ";
		case '6':
			return "six ";
		case '7':
			return "seven ";
		case '8':
			return "eight ";
		case '9':
			return "nine ";
		}
		return "" + c;
	}
}
