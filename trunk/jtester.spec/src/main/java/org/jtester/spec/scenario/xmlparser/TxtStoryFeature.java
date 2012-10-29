package org.jtester.spec.scenario.xmlparser;

import java.util.ArrayList;
import java.util.List;

import org.jtester.spec.scenario.JSpecScenario;
import org.jtester.spec.scenario.step.JSpecStep;

public class TxtStoryFeature {
	private String description;

	private List<JSpecStep> templates;

	private List<JSpecScenario> scenarios;

	public TxtStoryFeature() {
		this.description = "";
		this.templates = new ArrayList<JSpecStep>();
		this.scenarios = new ArrayList<JSpecScenario>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<JSpecStep> getTemplates() {
		return templates;
	}

	public void setTemplates(List<JSpecStep> templates) {
		this.templates = templates;
	}

	public List<JSpecScenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<JSpecScenario> scenarios) {
		this.scenarios = scenarios;
	}
}
