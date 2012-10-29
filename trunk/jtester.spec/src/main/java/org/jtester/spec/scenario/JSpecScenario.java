package org.jtester.spec.scenario;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.exceptions.SkipScenarioException;
import org.jtester.spec.scenario.step.JSpecStep;

public abstract class JSpecScenario {
	protected String scenario;

	protected String description;

	protected final List<JSpecStep> steps = new ArrayList<JSpecStep>();

	/**
	 * 本测试场景是否设置跳过
	 */
	protected boolean isSkip = false;

	/**
	 * 是否是跳过场景
	 * 
	 * @return
	 */
	public final boolean isSkip() {
		return this.isSkip;
	}

	/**
	 * 返回场景的步骤
	 * 
	 * @return
	 */
	public final List<JSpecStep> getSteps() {
		return steps;
	}

	public final String getScenario() {
		return scenario;
	}

	public final String getDescription() {
		return description;
	}

	/**
	 * 验证场景合法性
	 */
	public void validate() throws Throwable {
		if (this.isSkip()) {
			throw new SkipScenarioException(this);
		}
	}

	public static List<JSpecScenario> parseFrom(StoryType type, String story) {
		if (type == StoryType.XML) {
			return XmlJSpecScenario.parseJSpecScenarioFrom(story, "utf-8");
		} else {
			return TxtJSpecScenario.parseJSpecScenarioFrom(story);
		}
	}

	public static List<JSpecScenario> parseFrom(StoryType type, InputStream stream, String encoding) {
		if (type == StoryType.XML) {
			return XmlJSpecScenario.parseJSpecScenarioFrom(stream, encoding);
		} else {
			return TxtJSpecScenario.parseJSpecScenarioFrom(stream, encoding);
		}
	}

	@Override
	public final String toString() {
		return this.scenario.replaceAll("\\s", " ");
	}
}
