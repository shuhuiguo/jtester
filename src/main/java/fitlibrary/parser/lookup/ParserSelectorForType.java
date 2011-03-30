/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.lookup;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fitlibrary.object.DomainObjectParser;
import fitlibrary.parser.ByStringParser;
import fitlibrary.parser.DelegateParser;
import fitlibrary.parser.DelegatingParser;
import fitlibrary.parser.EnumParser;
import fitlibrary.parser.FailingDelegateParser;
import fitlibrary.parser.Parser;
import fitlibrary.parser.collection.ArrayParser;
import fitlibrary.parser.collection.ListParser;
import fitlibrary.parser.collection.SetParser;
import fitlibrary.parser.graphic.GraphicParser;
import fitlibrary.parser.table.TableParser;
import fitlibrary.parser.tagged.TaggedStringParser;
import fitlibrary.parser.tree.TreeParser;
import fitlibrary.runResults.TestResults;
import fitlibrary.runResults.TestResultsFactory;
import fitlibrary.table.Cell;
import fitlibrary.table.TableFactory;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibraryGeneric.list.ListParser2;
import fitlibraryGeneric.map.MapParser2;
import fitlibraryGeneric.set.SetParser2;
import fitlibraryGeneric.typed.GenericTyped;

public class ParserSelectorForType {
	// The following map only hold factories for things that can't change.
	// The map doesn't hold ParseDelegates because they may be specified afresh
	// for each storytest
	private Map<Class<?>, ParserFactory> mapClassToParserFactory = new ConcurrentHashMap<Class<?>, ParserFactory>(10000);

	public ParserSelectorForType() {
		//
	}

	public Parser parserFor(Evaluator evaluator, Typed typed, boolean isResult) {
		Class<?> classType = typed.asClass();
		DelegateParser delegate = ParseDelegation.getDelegate(classType);
		if (delegate != null)
			return delegate.parser(evaluator, typed);
		DomainObjectParser domainObjectParser = new DomainObjectParser(evaluator, typed);
		if (domainObjectParser.hasFinderMethod())
			return domainObjectParser;
		ParserFactory parserFactory = mapClassToParserFactory.get(classType);
		if (parserFactory == null) {
			parserFactory = parserFactory(typed, isResult);
			if (parserFactory != null)
				mapClassToParserFactory.put(classType, parserFactory);
			else {
				if (classType != String.class && classType != Object.class)
					return domainObjectParser;
				return new DelegatingParser(new FailingDelegateParser(typed.asClass()), evaluator, typed);
			}
		}
		return parserFactory.parser(evaluator, typed);
	}

	private ParserFactory parserFactory(Typed typed, boolean isResult) {
		// The order of the applicability tests here is significant
		Class<?> classType = typed.asClass();
		if (Map.class.isAssignableFrom(classType))
			return mapParserFactory();
		if (SetParser.applicableType(classType))
			return setParserFactory();
		if (ArrayParser.applicableType(classType))
			return ArrayParser.parserFactory();
		if (ListParser.applicableType(classType))
			return listParserFactory();
		if (TreeParser.applicableType(classType))
			return TreeParser.parserFactory();
		if (TableParser.applicableType(classType))
			return TableParser.parserFactory();
		if (GraphicParser.applicableType(classType))
			return GraphicParser.parserFactory();
		if (TaggedStringParser.applicableType(classType))
			return TaggedStringParser.parserFactory();

		ParserFactory factory = LookupPropertyEditorBasedParser.parserFactory(typed);
		if (factory != null)
			return factory;
		factory = ParseDelegation.selfParseFactory(typed);
		if (factory != null)
			return factory;
		if (typed.isEnum())
			return EnumParser.parserFactory();
		if (canTreatAsString(classType, typed.hasMethodOrField(), isResult))
			return ByStringParser.parserFactory();
		return null;
	}

	protected ParserFactory listParserFactory() {
		return ListParser2.parserFactory();
	}

	protected ParserFactory setParserFactory() {
		return SetParser2.parserFactory();
	}

	protected ParserFactory mapParserFactory() {
		return MapParser2.parserFactory();
	}

	private boolean canTreatAsString(Class<?> type, boolean hasCaller, boolean isResult) {
		return isResult && hasCaller && type == Object.class;
	}

	public static Object evaluate(Evaluator evaluator, Type type, String text) throws Exception {
		Parser parserFor = new ParserSelectorForType().parserFor(evaluator, new GenericTyped(type), false);
		TestResults testResults = TestResultsFactory.testResults();
		Cell cell = TableFactory.cell(text);
		Object subject = parserFor.parseTyped(cell, testResults).getSubject();
		if (testResults.problems())
			throw new RuntimeException("Unable to parse '" + text + "' as a " + type + ": " + cell.fullText());
		return subject;
	}
}
