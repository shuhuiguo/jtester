package org.jtester.spec.steps;

import java.util.LinkedHashMap;

import org.jbehave.core.model.StepPattern;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jtester.helper.StringHelper;

public class JSpecStepPatternParser implements StepPatternParser {

	public StepMatcher parseStep(final StepType stepType, final String stepPattern) {
		return new JSpecStepMatcher(stepType, stepPattern);
	}

	static class JSpecStepMatcher implements StepMatcher {
		private final StepType stepType;

		private final String stepPattern;

		private final String methodName;

		private final String[] paraNames;

		private LinkedHashMap<String, String> paras = null;

		public JSpecStepMatcher(final StepType stepType, final String stepPattern) {
			this.stepType = stepType;
			this.stepPattern = stepPattern;
			String[] items = stepPattern.split(" \\$");
			this.methodName = items[0];
			this.paraNames = this.getParaNames(items);
		}

		private String[] getParaNames(String[] items) {
			String[] names = new String[items.length - 1];
			for (int index = 1; index < items.length; index++) {
				names[index - 1] = items[index];
			}
			return names;
		}

		public boolean matches(String stepWithoutStartingWord) {
			int index = stepWithoutStartingWord.indexOf('\n');
			String part1 = index > 0 ? stepWithoutStartingWord.substring(0, index) : stepWithoutStartingWord;
			String part2 = index > 0 ? stepWithoutStartingWord.substring(index + 1) : "";
			String method = StringHelper.camel(part1);
			this.paras = JSpecHelper.parserParameter(part2);

			boolean matched = this.doesMatched(method);
			return matched;
		}

		private boolean doesMatched(String method) {
			if (methodName.equals(method) == false) {
				return false;
			}
			if (this.paras.size() != this.paraNames.length) {
				return false;
			}
			for (int index = 0; index < this.paraNames.length; index++) {
				String key = paraNames[index];
				if (paras.containsKey(key) == false) {
					return false;
				}
			}
			return true;
		}

		public boolean find(String stepWithoutStartingWord) {
			return true;
		}

		public String parameter(int matchedPosition) {
			String name = this.paraNames[matchedPosition - 1];
			return this.paras.containsKey(name) ? this.paras.get(name) : "";
		}

		public String[] parameterNames() {
			return this.paraNames;
		}

		public StepPattern pattern() {
			return new StepPattern(stepType, methodName, stepPattern);
		}
	}
}
