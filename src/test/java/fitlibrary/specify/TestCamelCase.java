package fitlibrary.specify;

import fitlibrary.CalculateFixture;

public class TestCamelCase extends CalculateFixture {
	public String identifierName(String name) {
		return getRuntimeContext().extendedCamel(name);
	}
}
