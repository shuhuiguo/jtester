/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.closure;

import java.util.List;

import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;

public class ActionSignature {
	public final String name;
	public final int arity;

	public ActionSignature(String name, int arity, RuntimeContextInternal runtimeContext) {
		this.name = runtimeContext.extendedCamel(name);
		this.arity = arity;
	}

	@Override
	public String toString() {
		return name + "/" + arity;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActionSignature))
			return false;
		ActionSignature other = (ActionSignature) obj;
		return arity == other.arity && name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode() + arity * 17;
	}

	public static ActionSignature create(Row row, int from, int upTo, boolean doStyle, RuntimeContextInternal runtime) {
		if (!doStyle)
			return new ActionSignature(row.text(from, runtime), upTo - from - 1, runtime);
		StringBuilder name = new StringBuilder(row.text(from, runtime));
		for (int i = from + 2; i < upTo; i += 2)
			name.append(" ").append(row.text(i, runtime));
		return new ActionSignature(name.toString(), (upTo - from) / 2, runtime);
	}

	public static ActionSignature doStyle(RuntimeContextInternal runtime, List<String> cells) {
		return doStyle(runtime, cells.toArray(new String[0]));
	}

	public static ActionSignature doStyle(RuntimeContextInternal runtime, String... cells) {
		String name = cells[0];
		for (int i = 2; i < cells.length; i += 2)
			name += " " + cells[i];
		return new ActionSignature(name, cells.length / 2, runtime);
	}

	public static ActionSignature seqStyle(RuntimeContextInternal runtime, List<String> cells) {
		return seqStyle(runtime, cells.toArray(new String[0]));
	}

	public static ActionSignature seqStyle(RuntimeContextInternal runtime, String... cells) {
		return new ActionSignature(cells[0], cells.length - 1, runtime);
	}
}
