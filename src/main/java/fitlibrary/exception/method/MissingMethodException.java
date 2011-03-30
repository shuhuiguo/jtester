/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.method;

import java.util.List;

import fitlibrary.exception.FitLibraryExceptionInHtml;

public class MissingMethodException extends FitLibraryExceptionInHtml {
	private static final long serialVersionUID = 1L;

	private List<String> signatures;
	private List<Class<?>> classes;

	public MissingMethodException(List<String> signatures, List<Class<?>> classes) {
		super("Missing method, possibly: " + htmlListOfSignatures(signatures) + "<hr/>In:"
				+ htmlListOfClassNames(classes));
		this.signatures = signatures;
		this.classes = classes;
	}

	public List<String> getMethodSignature() {
		return signatures;
	}

	public List<Class<?>> getClasses() {
		return classes;
	}

	public String htmlListOfClassNames() {
		return htmlListOfClassNames(classes);
	}

	public static String htmlListOfClassNames(List<Class<?>> classes) {
		String result = "<ul>";
		for (Class<?> c : classes)
			result += "<li>" + c.getCanonicalName() + "</li>";
		return result + "</ul>";
	}

	public String htmlInnerListOfSignature() {
		String result = "";
		for (String s : signatures)
			result += "<li>" + s + "</li>";
		return result;
	}

	public static String htmlListOfSignatures(List<String> signatures) {
		String result = "<ul>";
		for (String s : signatures)
			result += "<li>" + s + "</li>";
		return result + "</ul>";
	}
}
