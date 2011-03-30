/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.closure;

import java.lang.reflect.Method;

import fitlibrary.typed.TypedObject;
import fitlibrary.utility.MustBeThreadSafe;

public interface LookupClosure extends MustBeThreadSafe {
	Closure findMethodClosure(TypedObject typedObject, String methodName, int argCount);

	Closure findPublicMethodClosure(TypedObject typedObject, String name, Class<?>[] argTypes);

	boolean fitLibrarySystemMethod(Method method, int argCount, Object subject);
}
