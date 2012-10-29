package org.jtester.spec.scenario.step;


public class SpecMethodID {
	private StepType type;
	private String methodName;
	private int argCount;

	public SpecMethodID(StepType type, String methodName, int argCount) {
		this.type = type;
		this.methodName = methodName;
		this.argCount = argCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + argCount;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public StepType getType() {
		return type;
	}

	public String getMethodName() {
		return methodName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpecMethodID other = (SpecMethodID) obj;
		if (argCount != other.argCount)
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SpecMethod[type=" + type + ", methodName=" + methodName + ", argCount=" + argCount + "]";
	}
}