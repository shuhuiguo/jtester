package temp;

import fit.RowFixture;

public class RowFix extends RowFixture {
	@Override
	public Class<?> getTargetClass() {
		return A.class;
	}
	@Override
	public Object[] query() throws Exception {
		return new Object[] { new A() };
	}
	
	public static class A {
		public int a = 1, b = 2;
	}

}
