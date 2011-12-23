package org.jtester.spec.steps;

import static java.util.Arrays.asList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.ParameterConverters.MethodReturningConverter;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

public class JSpecStepsFactory extends AbstractStepsFactory {
	private final List<Object> stepsInstances;

	private final Configuration configuration;

	public JSpecStepsFactory(Configuration configuration, Object... stepsInstances) {
		this(configuration, asList(stepsInstances));
	}

	public JSpecStepsFactory(Configuration configuration, List<Object> stepsInstances) {
		super(configuration);
		this.stepsInstances = stepsInstances;
		this.configuration = configuration;
	}

	@Override
	protected List<Object> stepsInstances() {
		return stepsInstances;
	}

	@Override
	public List<CandidateSteps> createCandidateSteps() {
		List<Object> stepsInstances = stepsInstances();
		List<CandidateSteps> steps = new ArrayList<CandidateSteps>();
		for (Object instance : stepsInstances) {
			configuration.parameterConverters().addConverters(methodReturningConverters(instance));
			steps.add(new JSpecSteps(configuration, instance));
		}
		return steps;
	}

	/**
	 * Create parameter converters from methods annotated with @AsParameterConverter
	 */
	private List<ParameterConverter> methodReturningConverters(Object instance) {
		List<ParameterConverter> converters = new ArrayList<ParameterConverter>();

		for (Method method : instance.getClass().getMethods()) {
			if (method.isAnnotationPresent(AsParameterConverter.class)) {
				converters.add(new MethodReturningConverter(method, instance));
			}
		}

		return converters;
	}
}
