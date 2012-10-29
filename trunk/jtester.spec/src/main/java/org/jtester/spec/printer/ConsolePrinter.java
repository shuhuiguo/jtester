package org.jtester.spec.printer;

import org.jtester.spec.scenario.JSpecScenario;
import org.jtester.spec.scenario.step.JSpecStep;

/**
 * 将测试场景运行结果打印到控制台上
 * 
 * @author darui.wudr
 * 
 */
public class ConsolePrinter extends ISpecPrinter {

	@Override
	protected void printScenario(JSpecScenario scenario) {
		StringBuilder buff = new StringBuilder();
		buff.append(scenario.toString()).append("\n");
		for (JSpecStep step : scenario.getSteps()) {
			buff.append(step.toTxtString());
		}
		System.out.println(buff.toString());
	}
}
