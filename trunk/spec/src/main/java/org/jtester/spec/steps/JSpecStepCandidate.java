package org.jtester.spec.steps;

import java.lang.reflect.Method;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepType;

public class JSpecStepCandidate extends StepCandidate {

	public JSpecStepCandidate(String patternAsString, int priority, StepType stepType, Method method,
			Object stepsInstance, Keywords keywords, StepPatternParser stepPatternParser,
			ParameterConverters parameterConverters) {
		super(patternAsString, priority, stepType, method, stepsInstance, keywords, stepPatternParser,
				parameterConverters);
	}

	@Override
	public boolean matches(String step, String previousNonAndStep) {
		return super.matches(step, previousNonAndStep);
	}
}
