package fitlibraryGeneric.specify.genericFinder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import fitlibrary.object.DomainFixtured;
import fitlibrary.traverse.DomainAdapter;

@SuppressWarnings({ "rawtypes", "unused" })
public class GenericFinder implements DomainAdapter, DomainFixtured {
	private Pair<Integer, Integer> integerIntegerPair;
	private Pair<String, Double> stringDoublePair;

	public void setIntegerIntegerPair(Pair<Integer, Integer> pair) {
		this.integerIntegerPair = pair;
	}

	public void setStringDoublePair(Pair<String, Double> stringPair) {
		this.stringDoublePair = stringPair;
	}

	public Pair findPair(String key, Type type) {
		if (type instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
			if (actualTypeArguments[0] == Integer.class && actualTypeArguments[1] == Integer.class)
				return new Pair<Integer, Integer>(1, 2);
			if (actualTypeArguments[0] == String.class && actualTypeArguments[1] == Double.class)
				return new Pair<String, Double>("a", 4.0);
		}
		return null;
	}

	public String showPair(Pair pair) {
		throw new RuntimeException();
	}

	public String showPair(Pair pair, Type type) {
		return "Got " + pair.toString();
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
