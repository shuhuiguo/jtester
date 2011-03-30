/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.lookup;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;
import fitlibrary.parser.DelegateParser;
import fitlibrary.parser.DelegatingParser;
import fitlibrary.parser.Parser;
import fitlibrary.parser.self.SelfConstructorParser;
import fitlibrary.parser.self.SelfParser;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;

public class ParseDelegation {
	static Logger logger = FitLibraryLogger.getLogger(ParseDelegation.class);
	// This map is cleared at the end of each storytest, allowing for different
	// Parse Delegates
	// in different storytests.
	private static Map<Class<?>, DelegateParser> PARSE_DELEGATES = new ConcurrentHashMap<Class<?>, DelegateParser>();
	private static Map<Class<?>, DelegateParser> SUPER_PARSE_DELEGATES = new ConcurrentHashMap<Class<?>, DelegateParser>();

	/**
	 * Registers a delegate, a class that will handle parsing of other types of
	 * values.
	 */
	public static void registerParseDelegate(Class<?> type, Class<?> parseDelegate) {
		DelegateParser delegate = SelfParser.findSelfParser(parseDelegate);
		if (delegate != null) {
			PARSE_DELEGATES.put(type, delegate);
		} else
			throw new RuntimeException("Parse delegate class " + parseDelegate.getName()
					+ " does not have a suitable static parse() method.");
	}

	/**
	 * Registers a delegate object that will handle parsing of other types of
	 * values.
	 */
	public static void registerParseDelegate(Class<?> type, Object parseDelegate) {
		try {
			DelegateParser delegate = new DelegateObjectParser(type, parseDelegate);
			PARSE_DELEGATES.put(type, delegate);
		} catch (Exception ex) {
			throw new RuntimeException("Parse delegate object of class " + parseDelegate.getClass().getName()
					+ " does not have a suitable parse() method.");
		}
	}

	/**
	 * Registers a super delegate object that will handle parsing of other types
	 * of values.
	 */
	public static void registerSuperParseDelegate(Class<?> type, Object superParseDelegate) {
		try {
			DelegateParser delegate = new DelegateObjectSuperParser(type, superParseDelegate);
			SUPER_PARSE_DELEGATES.put(type, delegate);
		} catch (Exception ex) {
			throw new RuntimeException("Super parse delegate object of class "
					+ superParseDelegate.getClass().getName() + " does not have a suitable parse() method.");
		}
	}

	public static void clearDelegatesForNextStorytest() {
		PARSE_DELEGATES.clear();
		SUPER_PARSE_DELEGATES.clear();
	}

	public static boolean hasParseMethod(Class<?> type) {
		return SelfParser.findParseMethod(type) != null;
	}

	static abstract class AbstractDelegateObjectParser extends DelegateParser implements Cloneable {
		protected Object delegate;
		protected Method parseMethod;
		protected Method matchesMethod;
		protected Method showMethod;

		public AbstractDelegateObjectParser(Class<?> type, Object delegate) {
			super(null);
			this.delegate = delegate;
			try {
				this.matchesMethod = delegate.getClass().getMethod("matches", new Class[] { type, type });
			} catch (Exception e) {
				//
			}
			try {
				this.showMethod = delegate.getClass().getMethod("show", new Class[] { type });
			} catch (Exception e) {
				//
			}
		}

		@Override
		public boolean matches(Object a, Object b) {
			if (matchesMethod != null)
				try {
					return ((Boolean) matchesMethod.invoke(delegate, new Object[] { a, b })).booleanValue();
				} catch (Exception e) {
					//
				}
			return super.matches(a, b);
		}

		@Override
		public String show(Object object) {
			if (showMethod != null)
				try {
					return (String) showMethod.invoke(delegate, new Object[] { object });
				} catch (Exception e) {
					e.printStackTrace();
					//
				}
			return super.show(object);
		}
	}

	static class DelegateObjectSuperParser extends AbstractDelegateObjectParser {
		public DelegateObjectSuperParser(Class<?> type, Object delegate) throws SecurityException,
				NoSuchMethodException {
			super(type, delegate);
			this.parseMethod = delegate.getClass().getMethod("parse", new Class[] { String.class, Class.class });
		}

		@Override
		public Object parse(String s, Typed typed) throws Exception {
			logger.trace("Parsing with " + parseMethod.getName() + "() of " + delegate);
			return parseMethod.invoke(delegate, new Object[] { s, typed.asClass() });
		}
	}

	static class DelegateObjectParser extends AbstractDelegateObjectParser {
		public DelegateObjectParser(Class<?> type, Object delegate) throws SecurityException, NoSuchMethodException {
			super(type, delegate);
			this.parseMethod = delegate.getClass().getMethod("parse", new Class[] { String.class });
		}

		@Override
		public Object parse(String s, Typed typed) throws Exception {
			logger.trace("Parsing with " + parseMethod.getName() + "() of " + delegate);
			return parseMethod.invoke(delegate, new Object[] { s });
		}
	}

	public static DelegateParser getDelegate(Class<?> type) {
		DelegateParser delegateParser = PARSE_DELEGATES.get(type);
		if (delegateParser != null)
			return delegateParser;
		for (Class<?> keyType : SUPER_PARSE_DELEGATES.keySet()) {
			if (keyType.isAssignableFrom(type))
				return SUPER_PARSE_DELEGATES.get(keyType);
		}
		return null;
	}

	public static ParserFactory selfParseFactory(final Typed typed) {
		final DelegateParser classParser = findSelfParser(typed);
		if (classParser != null)
			return new ParserFactory() {
				// @Override
				public Parser parser(Evaluator evaluator, Typed typed2) {
					return new DelegatingParser(classParser, evaluator, typed2);
				}
			};
		return null;
	}

	private static DelegateParser findSelfParser(final Typed typed) {
		DelegateParser classParser = SelfParser.findSelfParser(typed.asClass());
		if (classParser == null)
			classParser = SelfConstructorParser.findSelfConstructorParser(typed.asClass());
		return classParser;
	}
}
