package org.jtester.utility;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@SuppressWarnings("rawtypes")
public class XstreamUtil {
	/**
	 * 将对象转化为xstream形式的json
	 * 
	 * @param o
	 * @return
	 */
	public static String toXstreamJson(Object o) {
		try {
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
			String json = xstream.toXML(o);
			return json;
		} catch (Exception e) {
			return "{convert to json exception:" + StringHelper.exceptionTrace(e) + "}";
		}
	}

	/**
	 * 将json转化为对象
	 * 
	 * @param <T>
	 * @param clazzName
	 *            要转换的对象别名
	 * @param clazz
	 *            要转换的对象类型
	 * @param json
	 *            对象的json串
	 * @return 从json对象转换出来的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXStreamJson(String clazzName, Class clazz, String json) {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
		xstream.alias(clazzName, clazz);
		Object obj = xstream.fromXML(json);
		return (T) obj;
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromXStreamJson(Class clazz, String json) {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
		String simplename = clazz.getSimpleName();
		String objname = simplename.substring(0, 1).toLowerCase() + simplename.substring(1);
		xstream.alias(simplename, clazz);
		xstream.alias(objname, clazz);
		xstream.alias(clazz.getName(), clazz);
		Object obj = xstream.fromXML(json);
		return (T) obj;
	}
}
