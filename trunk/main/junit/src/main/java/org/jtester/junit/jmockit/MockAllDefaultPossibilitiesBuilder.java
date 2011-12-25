package org.jtester.junit.jmockit;

import mockit.Instantiation;
import mockit.Mock;
import mockit.MockClass;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.internal.builders.JUnit4Builder;

@MockClass(realClass = AllDefaultPossibilitiesBuilder.class, instantiation = Instantiation.PerMockedInstance)
public class MockAllDefaultPossibilitiesBuilder {
	@Mock
	public JUnit4Builder junit4Builder() {
		return new JTesterBuilder();
	}
}
