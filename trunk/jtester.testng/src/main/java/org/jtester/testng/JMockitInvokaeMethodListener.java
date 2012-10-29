package org.jtester.testng;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import mockit.integration.testng.internal.TestNGRunnerDecorator;

import org.jtester.tools.reflector.FieldAccessor;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.internal.IConfiguration;

public class JMockitInvokaeMethodListener {
	private final static FieldAccessor<IConfiguration> mField = new FieldAccessor<IConfiguration>(TestRunner.class,
			"m_invokedMethodListeners");

	/**
	 * jmockit的hookable<br>
	 * 
	 * @see mockit.integration.testng.internal.TestNGRunnerDecorator
	 */
	private Boolean beenJMockite = null;

	@SuppressWarnings("unchecked")
	public JMockitInvokaeMethodListener(final ITestContext context) {
		if (context == null) {
			throw new RuntimeException("the testng conext can't be null.");
		}
		if (beenJMockite != null) {
			return;
		}
		List<IInvokedMethodListener> listeners = (List<IInvokedMethodListener>) mField.get(context);
		beenJMockite = false;
		for (IInvokedMethodListener listener : listeners) {
			if (listener instanceof TestNGRunnerDecorator) {
				beenJMockite = true;
			}
		}
		if (beenJMockite == false) {
			String jarFilePath = getJMockitJarFilePath();
			String hits = "JMockit has not been initialized. Check that your Java VM has been started with the -javaagent:"
					+ jarFilePath + " command line option.";
			throw new RuntimeException(hits);
		}
	}

	private static final Pattern JAR_REGEX = Pattern.compile(".*jmockit[-.\\d]*.jar");

	private static String getJMockitJarFilePath() {
		String javaClazzPaths = System.getProperty("java.class.path");
		if (javaClazzPaths == null) {
			return "jmockit.jar";
		}
		String[] classPath = javaClazzPaths.split(File.pathSeparator);
		if (classPath == null) {
			return "jmockit.jar";
		}
		for (String cpEntry : classPath) {
			if (JAR_REGEX.matcher(cpEntry).matches()) {
				return cpEntry;
			}
		}

		return "jmockit.jar";
	}
}
