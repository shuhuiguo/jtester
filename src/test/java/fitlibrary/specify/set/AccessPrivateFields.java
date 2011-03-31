package fitlibrary.specify.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class AccessPrivateFields {
	@SuppressWarnings("unchecked")
	private Set aSet = new HashSet(Arrays.asList(new Fields[] {
			new Fields(3,4), new Fields(1,2)
	}));
	
	public Set getASet() {
		return aSet;
	}
	
	public static class Fields {
		@SuppressWarnings("unused")
		private int field1, field2; // accessed reflectively
		
		public Fields(int field1, int field2) {
			this.field1 = field1;
			this.field2 = field2;
		}
	}
}
