package org.jtester.spec.exceptions;

import org.jtester.spec.scenario.JSpecScenario;

public class SkipScenarioException extends RuntimeException {

	private static final long serialVersionUID = -4210320495311303683L;

	public SkipScenarioException() {
		super();
	}

	public SkipScenarioException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkipScenarioException(String message) {
		super(message);
	}

	public SkipScenarioException(JSpecScenario scenario) {
		super(scenario.toString());
	}

	public SkipScenarioException(Throwable cause) {
		super(cause);
	}
}
