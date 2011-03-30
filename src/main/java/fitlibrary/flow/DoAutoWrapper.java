/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import fit.Fixture;
import fitlibrary.collection.CollectionTraverse;
import fitlibrary.object.DomainFixtured;
import fitlibrary.parser.collection.ArrayParser;
import fitlibrary.parser.collection.ListParser;
import fitlibrary.parser.collection.MapParser;
import fitlibrary.parser.collection.SetParser;
import fitlibrary.parser.lookup.ParseDelegation;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.function.Rule;
import fitlibrary.traverse.function.RuleTable;
import fitlibrary.traverse.workflow.DoTraverse;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

public class DoAutoWrapper implements IDoAutoWrapper {
	private final Evaluator evaluator;

	public DoAutoWrapper(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	// @Override
	public TypedObject wrap(TypedObject typedResult) {
		if (typedResult == null)
			return GenericTypedObject.NULL;
		Object result = typedResult.getSubject();
		if (result == null)
			return typedResult;
		if (notToBeAutoWrapped(result))
			return typedResult;
		if (result instanceof Evaluator || result instanceof Fixture)
			return typedResult;
		if (result instanceof Rule)
			return new GenericTypedObject(new RuleTable(result));

		Class<?> returnType = result.getClass();
		if (MapParser.applicableType(returnType) || ArrayParser.applicableType(returnType))
			return new GenericTypedObject(typedResult.traverse(evaluator));
		if (SetParser.applicableType(returnType) || ListParser.applicableType(returnType)) {
			CollectionTraverse traverse = (CollectionTraverse) typedResult.traverse(evaluator);
			traverse.setActualCollection(result);
			return new GenericTypedObject(traverse);
		}
		if (ParseDelegation.hasParseMethod(returnType))
			return typedResult;
		return new GenericTypedObject(new DoTraverse(typedResult));
	}

	// @Override
	public boolean canAutoWrap(Object result) {
		return !notToBeAutoWrapped(result);
	}

	private boolean notToBeAutoWrapped(Object result) {
		return result instanceof String || result instanceof StringBuffer || result instanceof DomainFixtured
				|| isPrimitiveType(result.getClass());
	}

	private static boolean isPrimitiveType(Class<?> returnType) {
		return returnType.isPrimitive() || returnType == Boolean.class || Number.class.isAssignableFrom(returnType)
				|| returnType == Character.class;
	}
}
