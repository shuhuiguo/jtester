package org.jtester.module.tracer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import org.jtester.annotations.Tracer;
import org.jtester.module.core.helper.TracerModuleHelper;
import org.jtester.module.tracer.jdbc.JdbcTracerManager;
import org.jtester.module.tracer.spring.BeanTracerManager;
import org.jtester.module.tracer.spring.MethodTracerEvent;
import org.jtester.utility.ResourceHelper;
import org.jtester.utility.StringHelper;

@SuppressWarnings({ "rawtypes" })
public class TracerManager {
	static ThreadLocal<File> tracerFile = new ThreadLocal<File>();
	static ThreadLocal<File> jpgFile = new ThreadLocal<File>();

	/**
	 * 设置是否跟踪spring或jdbc跟踪信息
	 * 
	 * @param tracer
	 * @param testedClazz
	 */
	public static void startTracer(Tracer tracer, Class testedClazz, Method testMethod) {
		boolean traceBean = false;
		boolean traceJdbc = false;
		if (tracer != null) {
			traceBean = tracer.spring();
			traceJdbc = tracer.jdbc();
		} else {
			traceBean = TracerModuleHelper.traceSpringBean();
			traceJdbc = TracerModuleHelper.traceJDBC();
		}

		if (traceBean) {
			BeanTracerManager.initMonitorBeans(traceBean, testedClazz, tracer);
		}
		if (traceJdbc) {
			JdbcTracerManager.initJdbcTracer(traceJdbc);
		}

		File htmlFile = getAboutMethodFile(testedClazz, testMethod, ".html");
		File jpg = getAboutMethodFile(testedClazz, testMethod, ".jpg");

		tracerFile.set(null);
		jpgFile.set(jpg);
		if (traceBean || traceJdbc) {
			tracerFile.set(htmlFile);
		}
		if (traceBean) {
			writeSequenceChart(htmlFile, jpg.getName());
		}
	}

	/**
	 * 往当前spring bean跟踪记录器中增加一条跟踪信息
	 * 
	 * @param tracer
	 */
	public static void addBeanTracerEvent(MethodTracerEvent event) {
		if (event == null) {
			return;
		}
		String info = event.toHtmlString();
		appendHtmlFile(info);
		BeanTracerManager.addTracer(event);
	}

	/**
	 * 记录bean跟踪api返回值
	 * 
	 * @param result
	 */
	public static void addBeanTracerResult(final MethodTracerEvent event, final Object result) {
		if (event == null) {
			return;
		}
		String info = BeanTracerManager.toTracerString(result);
		String html = MethodTracerEvent.getResultHtml(event.getSourceClazz(), event.getTargetClazz(),
				event.getMethodName(), info);
		appendHtmlFile(html);
	}

	public static void addBeanTracerException(final MethodTracerEvent event, final Throwable e) {
		if (event == null) {
			return;
		}
		String info = "throw exception:\n" + StringHelper.exceptionTrace(e);
		String html = MethodTracerEvent.getResultHtml(event.getSourceClazz(), event.getTargetClazz(),
				event.getMethodName(), info);
		appendHtmlFile(html);
	}

	private static void appendHtmlFile(String html) {
		File htmlFile = tracerFile.get();
		boolean traceBean = BeanTracerManager.isTracerEnabled();
		if (htmlFile == null || traceBean == false) {
			return;
		}
		try {
			FileWriter writer = new FileWriter(htmlFile, true);
			writer.append(html);
			writer.close();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void addJdbcTracerEvent(String sql) {
		boolean traceJdbc = JdbcTracerManager.isTracerEnabled();

		if (traceJdbc == false) {
			return;
		}
		JdbcTracerManager.addSQLTracer(sql);
	}

	/**
	 * 结束输出spring bean和jdbc跟踪信息
	 * 
	 * @param htmlFile
	 * @param beanTracerInfo
	 * @param jdbcTracerInfo
	 */
	public static void endTracer(Class testClazz, String method) {
		File htmlFile = tracerFile.get();
		boolean traceBean = BeanTracerManager.isTracerEnabled();
		if (htmlFile == null || traceBean == false) {
			return;
		}
		try {
			File jpg = jpgFile.get();
			BeanTracerManager.endMonitorBean(jpg);

			FileWriter writer = new FileWriter(htmlFile, true);
			writer.write("</table><br/>");
			String sql = JdbcTracerManager.endTracer();

			if (StringHelper.isBlankOrNull(sql) == false) {
				writer.write(sql);
			}
			writer.append("</html>");
			writer.close();

		} catch (Throwable e) {
			String message = String.format("test class:%s, test method:%s.", testClazz.getName(), method);
			Exception newE = new RuntimeException(message, e);
			// 这里无法做任何处理，我也不想把异常再往外面抛
			newE.printStackTrace();
		}
	}

	/**
	 * 返回由classpath + classname#methodname+ surfix组成的文件
	 * 
	 * @param testObject
	 * @param testMethod
	 * @param surfix
	 * @return
	 */
	private static File getAboutMethodFile(Class testClazz, Method testMethod, String surfix) {
		String basedir = System.getProperty("user.dir") + "/target/tracer/";
		String clazzName = testClazz.getName();
		String methodName = testMethod.getName();
		String path = basedir + clazzName.replace('.', '/');
		File htmlFile = new File(path + "#" + methodName + surfix);
		ResourceHelper.mkFileParentDir(htmlFile);
		return htmlFile;
	}

	/**
	 * 记录spring bean的序列图url
	 * 
	 * @param htmlFile
	 * @param jpgFile
	 */
	private static void writeSequenceChart(File htmlFile, String jpgFile) {
		try {
			FileWriter writer = new FileWriter(htmlFile, false);
			writer.append("<html><head>");
			writer.append(String.format("<META HTTP-EQUIV='Content-Type' CONTENT='text/html; charset=%s'>",
					ResourceHelper.defaultFileEncoding()));
			writer.append("<style>");
			String css = ResourceHelper.readFromFile("org/jtester/testng/UserTestReporter.css");
			writer.append(css);
			writer.append("</style></head>");
			if (jpgFile != null) {
				writer.append(String.format("<img src='%s'/><br/>", URLEncoder.encode(jpgFile, "utf-8")));
			}
			writer.append("<table>");
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
