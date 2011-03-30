package org.jtester.testng;

import org.jtester.core.IJTester;
import org.jtester.core.context.TestedSpringContext;
import org.jtester.core.testng.JTesterTestNG;
import org.jtester.fit.ErrorRecorder;
import org.testng.annotations.Test;

@Test(groups = "all-test")
public abstract class JTester extends JTesterTestNG implements IJTester {

	static {
		ErrorRecorder.createNewErrorFile();
	}

	protected final TestedSpringContext spring = new TestedSpringContext(this);

}
