package fitlibrary.specify.definedAction;

public class DefinedActionUnderTest {
	public boolean withEnterText(String location, String s) {
		return !(location.indexOf("password") >= 0 && !s.equals(""));
	}

	public boolean submit(String location) {
		return true;
	}

	public boolean birds(Ab ab) {
		return ab.getA() == 1;
	}

	public int count() {
		return 22;
	}

	public Ab object() {
		return new Ab();
	}

	public void aVoid() {
		//
	}

	public static class Ab {
		private int a;
		private int b = 2;

		public int getB() {
			return b;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}
	}
}
