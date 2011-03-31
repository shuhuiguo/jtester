/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow.caller;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.flow.GlobalActionScope;
import fitlibrary.runtime.RuntimeContextInternal;

public class ValidCall {
	private List<String> tableCall;

	public ValidCall(List<String> tableCall) {
		this.tableCall = tableCall;
	}

	public List<String> getList() {
		return tableCall;
	}

	public void setCall(String call) {
		tableCall.remove(0);
		tableCall.add(0, "<i>" + call + "</i>");
	}

	public static void parseDefinedAction(String callOriginal, String methodNameOriginal, List<ValidCall> results) {
		List<String> tableCall = new ArrayList<String>();
		String[] keys = methodNameOriginal.split("\\|");
		String call = callOriginal;
		boolean firstKey = true;
		for (String key : keys) {
			if (!firstKey) {
				int indexOf = call.indexOf(key);
				if (indexOf < 0)
					return;
				tableCall.add(call.substring(0, indexOf).trim());
				call = call.substring(indexOf);
			}
			if (call.startsWith(key)) {
				tableCall.add(keyword(key));
				call = call.substring(key.length());
			} else
				return;
			firstKey = false;
		}
		String method = methodNameOriginal;
		while (method.endsWith("|")) {
			tableCall.add(call.trim());
			method = method.substring(0, method.length() - 1);
			call = "";
		}
		if (!"".equals(call))
			return;
		results.add(new ValidCall(tableCall));
	}

	public static void parseAction(List<String> call, String methodNameOriginal, int argCount, List<ValidCall> results,
			RuntimeContextInternal runtime) {
		String methodName = methodNameOriginal;
		List<String> tableCall = new ArrayList<String>();
		int tableArgs = 0;
		String keyWord = "";
		String arg = "";
		boolean keyWording = true;
		for (String word : call) {
			String normalisedWord = normaliseWord(word, runtime);
			if (methodName.startsWith(normalisedWord)) {
				if (!keyWording) {
					tableCall.add(arg.trim());
					tableArgs++;
					if (tableArgs > argCount)
						return;
					arg = "";
				}
				methodName = remainingMethodName(methodName, normalisedWord.length());
				keyWord += " " + word;
				keyWording = true;
			} else {
				if (keyWording) {
					tableCall.add(keyword(keyWord));
					keyWord = "";
				}
				keyWording = false;
				arg += " " + word;
			}
		}
		if (!methodName.equals(""))
			return;
		if (keyWording)
			tableCall.add(keyword(keyWord));
		else {
			tableCall.add(arg.trim());
			tableArgs++;
			if (tableArgs > argCount)
				return;
		}
		for (int i = 0; i < argCount - tableArgs; i++) {
			if (!keyWording)
				tableCall.add("");
			tableCall.add("");
			keyWording = false;
		}
		results.add(new ValidCall(tableCall));
	}

	private static String keyword(String keyWord) {
		if ("".equals(keyWord.trim()))
			return "";
		return "<i>" + keyWord.trim() + "</i>";
	}

	private static String normaliseWord(String wordOriginal, RuntimeContextInternal runtime) {
		if ("".equals(wordOriginal))
			return "blank";
		String word = runtime.extendedCamel("t" + wordOriginal);
		return word.substring(1, 2).toLowerCase() + word.substring(2);
	}

	private static String remainingMethodName(String methodName, int wordLength) {
		if (methodName.length() == wordLength)
			return "";
		return methodName.substring(wordLength, wordLength + 1).toLowerCase() + methodName.substring(wordLength + 1);
	}

	public void possibility(GlobalActionScope globalScope) {
		String result = "<table border=\"1\" cellspacing=\"0\"><tr>";
		for (String s : getList())
			result += "<td>" + s + "</td>";
		globalScope.showAsAfterTable("plain text", result + "</tr></table>");
	}
}
