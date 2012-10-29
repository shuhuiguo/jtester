package org.jtester.spec.printer;

import java.util.ArrayList;
import java.util.List;

import org.jtester.spec.scenario.JSpecScenario;

public abstract class ISpecPrinter {
	final static List<ISpecPrinter> printers = new ArrayList<ISpecPrinter>();
	static {
		printers.add(new ConsolePrinter());
	}

	/**
	 * 输出测试场景信息
	 * 
	 * @param scenario
	 */
	protected abstract void printScenario(JSpecScenario scenario);

	public static void addSpecPrinter(ISpecPrinter printer) {
		printers.add(printer);
	}

	public static void print(JSpecScenario scenario) {
		for (ISpecPrinter printer : printers) {
			printer.printScenario(scenario);
		}
	}
}
