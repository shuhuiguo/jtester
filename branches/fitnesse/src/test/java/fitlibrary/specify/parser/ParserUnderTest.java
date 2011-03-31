package fitlibrary.specify.parser;

import fitlibrary.DoFixture;
import fitlibrary.traverse.function.ConstraintTraverse;

public class ParserUnderTest extends DoFixture {
	public ConstraintTraverse valid() {
		return new ConstraintTraverse(this);
	}

	public ConstraintTraverse invalid() {
		return new ConstraintTraverse(this, false);
	}

	@SuppressWarnings("rawtypes")
	public boolean className(Class type) {
		return true;
	}

	public boolean boolValue(boolean value) {
		return value;
	}

	public boolean booleanValue(Boolean value) {
		return value.booleanValue();
	}

	public boolean short_(short s) {
		return true;
	}

	public boolean classShort(Short s) {
		return true;
	}

	public boolean classShortNull(Short s) {
		return s == null;
	}

	public boolean int_(int arg1) {
		return true;
	}

	public boolean integer(Integer arg1) {
		return true;
	}

	public boolean integerNull(Integer i) {
		return i == null;
	}

	public boolean long_(long arg1) {
		return true;
	}

	public boolean classLong(Long arg1) {
		return true;
	}

	public boolean classLongNull(Long arg1) {
		return arg1 == null;
	}

	public boolean float_(float d) {
		return true;
	}

	public boolean classFloat(Float d) {
		return true;
	}

	public boolean classFloatNull(Float d) {
		return d == null;
	}

	public boolean double_(double d) {
		return true;
	}

	public boolean classDouble(Double d) {
		return true;
	}

	public boolean classDoubleNull(Double d) {
		return d == null;
	}

	public boolean char_(char c) {
		return true;
	}

	public boolean character(Character c) {
		return true;
	}

	public boolean byte_(byte b) {
		return true;
	}

	public boolean classByte(Byte b) {
		return true;
	}

	public boolean string(String s) {
		return true;
	}
}
