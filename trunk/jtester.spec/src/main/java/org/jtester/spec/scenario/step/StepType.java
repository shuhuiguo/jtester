package org.jtester.spec.scenario.step;

import java.lang.annotation.Annotation;

public enum StepType {
	Given {
		@Override
		public Class<? extends Annotation> getAnnotatonClaz() {
			return org.jtester.spec.annotations.Given.class;
		}
	}, // <br>
	When {
		@Override
		public Class<? extends Annotation> getAnnotatonClaz() {
			return org.jtester.spec.annotations.When.class;
		}
	}, // <br>
	Then {
		@Override
		public Class<? extends Annotation> getAnnotatonClaz() {
			return org.jtester.spec.annotations.Then.class;
		}
	};

	public abstract Class<? extends Annotation> getAnnotatonClaz();

	public static StepType getStepType(String type) {
		if ("given".equalsIgnoreCase(type)) {
			return Given;
		}
		if ("when".equalsIgnoreCase(type)) {
			return When;
		}
		if ("then".equalsIgnoreCase(type)) {
			return Then;
		}
		throw new RuntimeException("the step type can only be following values: Given, When and Then; but actual is "
				+ type);
	}
}
