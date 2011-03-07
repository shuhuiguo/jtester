package org.jtester.core.context;

import java.util.Map;

import org.jtester.fit.FitRunner;
import org.jtester.fit.util.SymbolUtil;
import org.jtester.module.dbfit.DbFitRunner;

public class JTesterFitnesse {
	/**
	 * 通过程序来准备数据库数据<br>
	 * 一般用于在spring加载缓存资源时使用，在@BeforeClass中调用
	 * 
	 * @param clazz
	 * @param wiki
	 * @param wikis
	 */
	public void runDbFit(Class<?> clazz, String wiki, String... wikis) {
		DbFitRunner.runDbFit(clazz, wiki, wikis);
	}

	public void runDbFit(Class<?> clazz, boolean cleanSymbols, String wiki, String... wikis) {
		DbFitRunner.runDbFit(clazz, cleanSymbols, wiki, wikis);
	}

	/**
	 * 通过程序来准备数据库数据<br>
	 * 一般用于在spring加载缓存资源时使用，在@BeforeClass中调用<br>
	 * 或者用程序来整备wiki变量时使用
	 * 
	 * @param clazz
	 * @param symbols
	 * @param wiki
	 * @param wikis
	 */
	public void runDbFit(Class<?> clazz, Map<String, ?> symbols, String wiki, String... wikis) {
		DbFitRunner.runDbFit(clazz, symbols, wiki, wikis);
	}

	/**
	 * 通过程序来运行fitnesse wiki文件<br>
	 * 一般用于在spring加载缓存资源时使用，在@BeforeClass中调用
	 * 
	 * @param clazz
	 * @param wiki
	 * @param wikis
	 */
	public void runFit(Class<?> clazz, String wiki, String... wikis) {
		FitRunner.runFit(clazz, wiki, wikis);
	}

	/**
	 * 通过程序来运行fitnesse wiki文件<br>
	 * 一般用于在spring加载缓存资源时使用，在@BeforeClass中调用<br>
	 * 或者用程序来整备wiki变量时使用
	 * 
	 * @param clazz
	 * @param symbols
	 * @param wiki
	 * @param wikis
	 */
	public void runFit(Class<?> clazz, Map<String, Object> symbols, String wiki, String... wikis) {
		FitRunner.runFit(clazz, symbols, wiki, wikis);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSymbol(String key) {
		Object o = SymbolUtil.getSymbol(key);
		return (T) o;
	}

	public void setSymbol(String key, Object value) {
		SymbolUtil.setSymbol(key, value);
	}

	public void setSymbols(Map<String, Object> symbols) {
		SymbolUtil.setSymbol(symbols);
	}

	public void cleanSymbols() {
		SymbolUtil.cleanSymbols();
	}
}
