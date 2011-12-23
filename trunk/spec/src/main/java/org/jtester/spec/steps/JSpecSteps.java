package org.jtester.spec.steps;

import static java.util.Arrays.asList;
import static org.jbehave.core.steps.StepType.GIVEN;
import static org.jbehave.core.steps.StepType.THEN;
import static org.jbehave.core.steps.StepType.WHEN;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.Composite;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.Steps;

public class JSpecSteps extends Steps {
	final Configuration configuration;

	public JSpecSteps() {
		this(new JSpecConfiguration());
	}

	public JSpecSteps(Configuration configuration, Object instance) {
		super(configuration, instance);
		this.configuration = configuration;
	}

	public JSpecSteps(Configuration configuration) {
		this(configuration, null);
	}

	// @Override
	// public List<StepCandidate> listCandidates() {
	// List<StepCandidate> candidates = new ArrayList<StepCandidate>();
	// for (Method method : allMethods()) {
	// if (method.isAnnotationPresent(org.jbehave.core.annotations.Given.class))
	// {
	// org.jbehave.core.annotations.Given annotation = method
	// .getAnnotation(org.jbehave.core.annotations.Given.class);
	// String value = annotation.value();
	// int priority = annotation.priority();
	// addCandidate(candidates, method, GIVEN, value, priority);
	// }
	// if (method.isAnnotationPresent(org.jbehave.core.annotations.When.class))
	// {
	// org.jbehave.core.annotations.When annotation = method
	// .getAnnotation(org.jbehave.core.annotations.When.class);
	// String value = annotation.value();
	// int priority = annotation.priority();
	// addCandidate(candidates, method, WHEN, value, priority);
	// }
	// if (method.isAnnotationPresent(org.jbehave.core.annotations.Then.class))
	// {
	// org.jbehave.core.annotations.Then annotation = method
	// .getAnnotation(org.jbehave.core.annotations.Then.class);
	// String value = annotation.value();
	// int priority = annotation.priority();
	// addCandidate(candidates, method, THEN, value, priority);
	// }
	// }
	// return candidates;
	// }

	@Override
	public List<StepCandidate> listCandidates() {
		List<StepCandidate> candidates = new ArrayList<StepCandidate>();
		for (Method method : allMethods()) {
			if (method.isAnnotationPresent(org.jtester.spec.annotations.Given.class)) {
				org.jtester.spec.annotations.Given annotation = method
						.getAnnotation(org.jtester.spec.annotations.Given.class);
				// String value = annotation.value();
				int priority = annotation.priority();
				addCandidate(candidates, method, GIVEN, method.getName(), priority);
			}
			if (method.isAnnotationPresent(org.jtester.spec.annotations.When.class)) {
				org.jtester.spec.annotations.When annotation = method
						.getAnnotation(org.jtester.spec.annotations.When.class);
				// String value = annotation.value();
				int priority = annotation.priority();
				addCandidate(candidates, method, WHEN, method.getName(), priority);
			}
			if (method.isAnnotationPresent(org.jtester.spec.annotations.Then.class)) {
				org.jtester.spec.annotations.Then annotation = method
						.getAnnotation(org.jtester.spec.annotations.Then.class);
				// String value = annotation.value();
				int priority = annotation.priority();
				addCandidate(candidates, method, THEN, method.getName(), priority);
			}
		}
		return candidates;
	}

	private void addCandidate(List<StepCandidate> candidates, Method method, StepType stepType,
			String stepPatternAsString, int priority) {
		checkForDuplicateCandidates(candidates, stepType, stepPatternAsString);
		StepCandidate candidate = createCandidate(method, stepType, priority, configuration);
		candidate.useStepMonitor(configuration.stepMonitor());
		candidate.useParanamer(configuration.paranamer());
		candidate.doDryRun(configuration.storyControls().dryRun());
		if (method.isAnnotationPresent(Composite.class)) {
			candidate.composedOf(method.getAnnotation(Composite.class).steps());
		}
		candidates.add(candidate);
	}

	private StepCandidate createCandidate(Method method, StepType stepType, int priority, Configuration configuration) {
		String patternAsString = JSpecHelper.getPatternAsStringFromMethod(method);
		return new JSpecStepCandidate(patternAsString, priority, stepType, method, instance(),
				configuration.keywords(), configuration.stepPatternParser(), configuration.parameterConverters());
	}

	private void checkForDuplicateCandidates(List<StepCandidate> candidates, StepType stepType, String patternAsString) {
		for (StepCandidate candidate : candidates) {
			if (candidate.getStepType() == stepType && candidate.getPatternAsString().equals(patternAsString)) {
				throw new DuplicateCandidateFound(stepType, patternAsString);
			}
		}
	}

	private List<Method> allMethods() {
		return asList(instance().getClass().getMethods());
	}
}
