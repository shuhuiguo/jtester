/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.self;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;
import fitlibrary.parser.DelegateParser;
import fitlibrary.typed.Typed;

public class SelfConstructorParser extends DelegateParser implements Cloneable {
	static Logger logger = FitLibraryLogger.getLogger(SelfConstructorParser.class);
	private Constructor<?> constructor;

	public static SelfConstructorParser findSelfConstructorParser(Class<?> type) {
		Constructor<?> constructor = findConstructor(type);
		if (constructor == null)
			return null;
		return new SelfConstructorParser(constructor);
	}

	public SelfConstructorParser(Constructor<?> parseMethod) {
		this.constructor = parseMethod;
	}

	public static Constructor<?> findConstructor(Class<?> type) {
		if (Modifier.isAbstract(type.getModifiers()))
			return null;
		Constructor<?>[] declaredConstructors = type.getDeclaredConstructors();
		for (Constructor<?> constructor : declaredConstructors)
			if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == String.class)
				return constructor;
		return null;
	}

	@Override
	public Object parse(String s, Typed typed) throws Exception {
		logger.trace("Parsing with " + constructor.getName() + "()");
		constructor.setAccessible(true);
		return constructor.newInstance(s);
	}
}
