package org.jtester.spec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.exceptions.ScenarioAssertError;
import org.jtester.spec.exceptions.SkipStepException;
import org.jtester.spec.printer.ISpecPrinter;
import org.jtester.spec.scenario.ExceptionJSpecScenario;
import org.jtester.spec.scenario.JSpecScenario;
import org.jtester.spec.scenario.step.JSpecStep;
import org.jtester.spec.scenario.step.SpecMethod;
import org.jtester.spec.scenario.step.SpecMethodID;
import org.jtester.spec.scenario.step.StepType;
import org.jtester.spec.storypath.StoryPath;
import org.jtester.testng.JTesterCore;
import org.jtester.tools.commons.AnnotationHelper;
import org.jtester.tools.commons.StringHelper;
import org.testng.annotations.DataProvider;

@SuppressWarnings("rawtypes")
public abstract class JSpec extends JTesterCore {
	private Map<SpecMethodID, SpecMethod> specMethods;

	public void run(JSpecScenario scenario) throws Throwable {
		scenario.validate();
		this.specMethods = SpecMethod.findMethods(this.getClass());
		List<JSpecStep> steps = scenario.getSteps();
		List<Throwable> assertErrors = new ArrayList<Throwable>();
		Throwable suspend = null;
		for (JSpecStep step : steps) {
			if (step.isSkip() || suspend != null) {
				step.setError(SkipStepException.instance);
				continue;
			}
			Throwable error = this.runSingleStep(step, assertErrors);
			step.setError(error);
			if (step.getType() != StepType.Then) {
				suspend = error;
			}
		}
		ISpecPrinter.print(scenario);
		if (suspend != null) {
			throw suspend;
		} else if (assertErrors.size() != 0) {
			throw new ScenarioAssertError(scenario, assertErrors);
		}
	}

	private Throwable runSingleStep(JSpecStep step, List<Throwable> errors) {
		try {
			SpecMethodID id = step.getSpecMethodID();
			if (this.specMethods.containsKey(id) == false) {
				throw new RuntimeException(String.format("can't find %s in class[%s]", id, this.getClass().getName()));
			}
			SpecMethod specMethod = this.specMethods.get(id);
			specMethod.invoke(this, step);
			return null;
		} catch (Throwable error) {
			errors.add(error);
			return error;
		}
	}

	@DataProvider
	public Iterator story() {
		// long parseStartTime = System.currentTimeMillis();
		try {
			return parseScenarios();
		} catch (Throwable e) {
			return ExceptionJSpecScenario.iterator(e);
		} finally {
			// long durationTime = System.currentTimeMillis() - parseStartTime);
			// System.out.println("parse time:" + durationTime);
		}
	}

	/**
	 * 需要被执行的场景序号,序号从1开始
	 */
	static final String SCENARIO_RUN_INDEX = "scenario.index";

	public static Set<Integer> findScenarioRunIndex() {
		String sIndexes = System.getProperty(SCENARIO_RUN_INDEX);
		if (StringHelper.isBlankOrNull(sIndexes)) {
			return null;
		}
		if ("0".equals(sIndexes)) {
			return null;
		}
		String[] items = sIndexes.split(",");
		Set<Integer> set = new HashSet<Integer>();
		for (String item : items) {
			try {
				Integer no = Integer.parseInt(item);
				set.add(no);
			} catch (Exception e) {
				// do nothing
			}
		}
		return set;
	}

	/**
	 * 解析场景列表
	 * 
	 * @return
	 */
	private DataIterator parseScenarios() {
		Set<Integer> indexs = findScenarioRunIndex();

		StoryFile storyFile = AnnotationHelper.getClassLevelAnnotation(StoryFile.class, this.getClass());
		StoryPath storyPath = StoryPath.factory(this.getClass(), storyFile);
		String encoding = getEncoding();
		List<JSpecScenario> scenarios = storyPath.getStory(storyFile, encoding);
		DataIterator it = new DataIterator();
		int index = 1;
		for (JSpecScenario scenario : scenarios) {
			if (indexs == null || indexs.contains(index)) {
				it.data(scenario);
			}
			index++;
		}
		return it;
	}

	/**
	 * 默认不指定编码规则，由文本流自己动态获取
	 * 
	 * @return
	 */
	protected String getEncoding() {
		return null;
	}

	public abstract void runStory(JSpecScenario scenario) throws Throwable;
}
