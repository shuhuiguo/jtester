package org.jtester.fit.util;

import java.util.HashMap;
import java.util.Map;

import org.jtester.utility.DateUtil;
import org.jtester.utility.StringHelper;

/**
 * ugly workaround for fit change in release 200807, which internally converts
 * NULL into a string value "null"; for db access, we need to make a difference
 * between NULL and "null" so this class provides a centralised place for the
 * change; for dbfit fixtures use this class to access symbols rather than
 * directly fit.fixture
 */
public class SymbolUtil {
	private static final Object dbNull = new Object();

	public static void setSymbol(String name, Object value) {
		fit.Fixture.setSymbol(name, value == null ? dbNull : value);
	}

	@SuppressWarnings("rawtypes")
	public static Object getSymbol(String name) {
		int index = name.indexOf("[");
		if (index > 0) {
			String symbolName = StringHelper.trim(name.substring(0, index));
			String keyName = StringHelper.trim(name.substring(index + 1, name.length() - 1));
			Object symbol = fit.Fixture.getSymbol(symbolName);
			if (symbol instanceof Map) {
				Map map = (Map) symbol;
				return map.get(keyName);
			} else {
				return symbol;
			}
		}
		if (system_symbols.containsKey(name)) {
			return system_symbols.get(name);
		}
		Object value = fit.Fixture.getSymbol(name);
		if (value == dbNull) {
			return null;
		} else {
			return value;
		}
	}

	/**
	 * 清空wiki中已设置的变量
	 */
	public static void cleanSymbols() {
		fit.Fixture.ClearSymbols();
	}

	/**
	 * 批量设置wiki中用到的变量
	 * 
	 * @param symbols
	 */
	public static void setSymbol(Map<String, ?> symbols) {
		for (Map.Entry<String, ?> symbol : symbols.entrySet()) {
			setSymbol(symbol.getKey(), symbol.getValue());
		}
	}

	/**
	 * 系统预设的变量
	 * 
	 * @return
	 */
	private final static Map<String, Object> system_symbols = new HashMap<String, Object>() {
		private static final long serialVersionUID = 2824018273733392296L;
		{
			put("date", DateUtil.currDateStr());
			put("datetime", DateUtil.currDateTimeStr());
			put("space", " ");
			put("at", "@");
		}
	};
}
