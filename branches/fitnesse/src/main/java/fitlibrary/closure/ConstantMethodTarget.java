/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import fitlibrary.parser.lookup.GetterParser;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.TypedObject;

public class ConstantMethodTarget extends CalledMethodTarget {
	private Object value;

	public ConstantMethodTarget(Object value, Evaluator evaluator) {
		super(evaluator);
		this.value = value;
		resultParser = new GetterParser(Traverse.asTyped(value).parser(evaluator), null);
	}

	@Override
	public Object invoke() throws Exception {
		return value;
	}

	@Override
	public Class<?> getReturnType() {
		return void.class;
	}

	@Override
	public Class<?>[] getParameterTypes() {
		return new Class[] {};
	}

	@Override
	public void setTypedSubject(TypedObject typedObject) {
		//
	}

	@Override
	public String getResult() throws Exception {
		return value.toString();
	}
}
