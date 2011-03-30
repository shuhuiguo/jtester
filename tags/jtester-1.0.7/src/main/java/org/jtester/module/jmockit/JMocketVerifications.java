package org.jtester.module.jmockit;

import mockit.Verifications;

public class JMocketVerifications extends Verifications {

	public JMocketVerifications() {
		super();
		ExpectationsUtil.register(this);
	}

	public JMocketVerifications(int numberOfIterations) {
		super(numberOfIterations);
		ExpectationsUtil.register(this);
	}
}
