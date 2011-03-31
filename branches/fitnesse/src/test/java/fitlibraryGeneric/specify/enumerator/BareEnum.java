package fitlibraryGeneric.specify.enumerator;

import java.util.List;

import fitlibrary.object.DomainFixtured;

public class BareEnum implements DomainFixtured {
	private ColourEnum enumeration;
	private List<ColourEnum> list;
	private ColourEnum[] array;

	public static enum ColourEnum {
		RED, GREEN, BLUE, LIGHTRED, BLUE_GREEN
	}

	public ColourEnum getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(ColourEnum enumeration) {
		this.enumeration = enumeration;
	}

	public ColourEnum[] getArray() {
		return array;
	}

	public void setArray(ColourEnum[] array) {
		this.array = array;
	}

	public List<ColourEnum> getList() {
		return list;
	}

	public void setList(List<ColourEnum> list) {
		this.list = list;
	}
}
