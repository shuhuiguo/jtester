/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.definedAction;

public class DefinedMultiAction {
	protected String name;

	public DefinedMultiAction(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DefinedMultiAction))
			return false;
		DefinedMultiAction other = (DefinedMultiAction) obj;
		return name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "DefinedMultiAction[" + name + "]";
	}
}
