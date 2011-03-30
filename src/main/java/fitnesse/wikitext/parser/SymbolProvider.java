package fitnesse.wikitext.parser;

import java.util.*;

public class SymbolProvider {
	public static final SymbolProvider refactoringProvider = new SymbolProvider(// <br>
			new SymbolType[] { Alias.symbolType,// <br>
					SymbolType.OpenBracket, /** <br> **/
					SymbolType.CloseBracket, /** <br> **/
					Comment.symbolType, /** <br> **/
					Image.symbolType, /** <br> **/
					Literal.symbolType, /** <br> **/
					Preformat.symbolType, /** <br> **/
					Link.symbolType, /** <br> **/
					Path.symbolType, /** <br> **/
					WikiWord.symbolType, /** <br> **/
					SymbolType.Newline, /** <br> **/
					SymbolType.Whitespace });

	public static final SymbolProvider wikiParsingProvider = new SymbolProvider(// <br>
			new SymbolType[] { Link.symbolType,// <br>
					new Table(), /** <br> **/
					SymbolType.EndCell, /** <br> **/
					new HashTable(), /** <br> **/
					new HeaderLine(), /** <br> **/
					Literal.symbolType, /** <br> **/
					new Collapsible(), /** <br> **/
					new AnchorName(), /** <br> **/
					new Contents(), /** <br> **/
					SymbolType.CenterLine, /** <br> **/
					new Define(), /** <br> **/
					new Help(), /** <br> **/
					new Include(), /** <br> **/
					SymbolType.Meta, /** <br> **/
					SymbolType.NoteLine, /** <br> **/
					Path.symbolType, /** <br> **/
					new PlainTextTable(), /** <br> **/
					new See(), /** <br> **/
					SymbolType.Style, /** <br> **/
					new LastModified(), /** <br> **/
					Image.symbolType, /** <br> **/
					new Today(), /** <br> **/
					SymbolType.Delta, /** <br> **/
					new HorizontalRule(), /** <br> **/
					SymbolType.CloseLiteral, /** <br> **/
					SymbolType.Strike, /** <br> **/
					Alias.symbolType, /** <br> **/
					SymbolType.UnorderedList, /** <br> **/
					SymbolType.OrderedList, /** <br> **/
					Comment.symbolType, /** <br> **/
					SymbolType.Whitespace, /** <br> **/
					SymbolType.CloseCollapsible, /** <br> **/
					SymbolType.Newline, /** <br> **/
					SymbolType.Colon, /** <br> **/
					SymbolType.Comma, /** <br> **/
					Evaluator.symbolType, /** <br> **/
					SymbolType.CloseEvaluator, /** <br> **/
					Variable.symbolType, /** <br> **/
					Preformat.symbolType, /** <br> **/
					SymbolType.ClosePreformat, /** <br> **/
					SymbolType.OpenParenthesis, /** <br> **/
					SymbolType.OpenBrace, /** <br> **/
					SymbolType.OpenBracket, /** <br> **/
					SymbolType.CloseParenthesis, /** <br> **/
					SymbolType.CloseBrace, /** <br> **/
					SymbolType.ClosePlainTextTable, /** <br> **/
					SymbolType.CloseBracket, /** <br> **/
					SymbolType.CloseLiteral, /** <br> **/
					SymbolType.Bold, /** <br> **/
					SymbolType.Italic, /** <br> **/
					SymbolType.Strike, /** <br> **/
					new AnchorReference(), /** <br> **/
					WikiWord.symbolType, /** <br> **/
					SymbolType.EMail, /** <br> **/
					SymbolType.Text, });

	public static final SymbolProvider aliasLinkProvider = new SymbolProvider(// <br>
			new SymbolType[] { SymbolType.CloseBracket,// <br>
					Evaluator.symbolType, /** <br> **/
					Literal.symbolType, /** <br> **/
					Variable.symbolType });

	public static final SymbolProvider linkTargetProvider = new SymbolProvider(// <br>
			new SymbolType[] { Literal.symbolType,// <br>
					Variable.symbolType });

	public static final SymbolProvider pathRuleProvider = new SymbolProvider(// <br>
			new SymbolType[] { Evaluator.symbolType,// <br>
					Literal.symbolType, Variable.symbolType });

	public static final SymbolProvider literalTableProvider = new SymbolProvider(// <br>
			new SymbolType[] { SymbolType.EndCell,// <br>
					SymbolType.Newline, /** <br> **/
					Evaluator.symbolType, /** <br> **/
					Literal.symbolType, /** <br> **/
					Variable.symbolType });

	private static final char defaultMatch = '\0';

	private HashMap<Character, ArrayList<Matchable>> currentDispatch;
	private ArrayList<SymbolType> symbolTypes;

	public SymbolProvider(Iterable<SymbolType> types) {
		symbolTypes = new ArrayList<SymbolType>();
		currentDispatch = new HashMap<Character, ArrayList<Matchable>>();
		currentDispatch.put(defaultMatch, new ArrayList<Matchable>());
		for (char c = 'a'; c <= 'z'; c++)
			currentDispatch.put(c, new ArrayList<Matchable>());
		for (char c = 'A'; c <= 'Z'; c++)
			currentDispatch.put(c, new ArrayList<Matchable>());
		for (char c = '0'; c <= '9'; c++)
			currentDispatch.put(c, new ArrayList<Matchable>());
		addTypes(types);
	}

	public SymbolProvider(SymbolProvider other) {
		this(other.symbolTypes);
	}

	public SymbolProvider(SymbolType[] types) {
		this(Arrays.asList(types));
	}

	public void addTypes(Iterable<SymbolType> types) {
		for (SymbolType symbolType : types) {
			add(symbolType);
		}
	}

	public SymbolProvider add(SymbolType symbolType) {
		if (matchesFor(symbolType))
			return this;
		symbolTypes.add(symbolType);
		for (Matcher matcher : symbolType.getWikiMatchers()) {
			for (char first : matcher.getFirsts()) {
				if (!currentDispatch.containsKey(first))
					currentDispatch.put(first, new ArrayList<Matchable>());
				currentDispatch.get(first).add(symbolType);
			}
		}
		return this;
	}

	private ArrayList<Matchable> getMatchTypes(Character match) {
		if (currentDispatch.containsKey(match))
			return currentDispatch.get(match);
		return currentDispatch.get(defaultMatch);
	}

	public void addMatcher(Matchable matcher) {
		ArrayList<Matchable> defaults = currentDispatch.get(defaultMatch);
		defaults.add(matcher);
	}

	public boolean matchesFor(SymbolType type) {
		return symbolTypes.contains(type);
	}

	public SymbolMatch findMatch(ScanString input, MatchableFilter filter) {
		for (Matchable candidate : getMatchTypes(input.charAt(0))) {
			if (filter.isValid(candidate)) {
				SymbolMatch match = candidate.makeMatch(input);
				if (match.isMatch())
					return match;
			}
		}
		return SymbolMatch.noMatch;
	}
}
