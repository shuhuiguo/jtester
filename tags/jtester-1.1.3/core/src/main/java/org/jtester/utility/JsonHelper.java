package org.jtester.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jtester.bytecode.reflector.helper.ClazzHelper;
import org.jtester.exception.JTesterException;
import org.jtester.json.JSON;

/**
 * POJO对象序列化和反序列化工具类
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonHelper {
	/**
	 * 将pojo序列化后存储在dat类型的文件中
	 * 
	 * @param <T>
	 * @param o
	 *            需要序列化的对象
	 * @param filename
	 *            存储文件的路径名称
	 */
	public static <T> void toDatFile(T o, String filename) {
		ResourceHelper.mkFileParentDir(filename);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(o);
			out.close();
		} catch (Throwable e) {
			throw new JTesterException(e);
		}
	}

	/**
	 * 将对象存储为json格式的文件
	 */
	public static <T> void toJsonFile(T o, String filename) {
		String json = JSON.toJSON(o);
		ResourceHelper.writeStringToFile(new File(filename), json);
	}

	/**
	 * 从dat文件中将pojo反序列化出来
	 * 
	 * @param <T>
	 * @param claz
	 *            反序列化出来的pojo class类型
	 * @param filename
	 *            pojo序列化信息文件,如果以"classpath:"开头表示文件存储在classpth的package路径下，
	 *            否则表示文件的绝对路径
	 * @return
	 */
	public static <T> T fromDatFile(Class claz, String filename) {
		try {
			InputStream inputStream = ResourceHelper.getResourceAsStream(filename);
			ObjectInputStream in = new ObjectInputStream(inputStream);
			Object obj = in.readObject();
			in.close();
			return (T) obj;
		} catch (Throwable e) {
			throw new JTesterException(e);
		}
	}

	/**
	 * 从dat文件中反序列对象
	 * 
	 * @param <T>
	 * @param returnClazz
	 *            反序列化出来的pojo class类型
	 * @param pathClazz
	 *            dat文件所在的目录下的class，用来方便寻找dat文件
	 * @param filename
	 *            dat文件名称
	 * @return
	 */
	public static <T> T fromDatFile(Class returnClazz, Class pathClazz, String filename) {
		String path = ClazzHelper.getPathFromPath(pathClazz);
		return (T) fromDatFile(returnClazz, path + File.separatorChar + filename);
	}

	/**
	 * 从json文件中将对象反序列回来
	 */
	public static <T> T fromJsonFile(Class claz, String filename) {
		try {
			String json = ResourceHelper.readFromFile(filename);
			Object o = JSON.toObject(json, claz);
			return (T) o;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将pojo从json文件中反序列化出来
	 * 
	 * @param <T>
	 * @param returnClazz
	 *            反序列化出来的pojo class类型
	 * @param pathClazz
	 *            json文件所在的目录下的class，用来方便寻找dat文件
	 * @param filename
	 *            序列号文件json的名称
	 * @return
	 */
	public static <T> T fromJsonFile(Class returnClazz, Class pathClazz, String filename) {
		String path = ClazzHelper.getPathFromPath(pathClazz);
		return (T) fromJsonFile(returnClazz, path + File.separatorChar + filename);
	}

	public static String toJSON(Object o) {
		String json = JSON.toJSON(o);
		return json;
	}

	public static <T> T fromJSON(Class clazz, String json) {
		return null;// TODO
	}

	public static <T> T fromJSON(String clazzName, Class clazz, String json) {
		return null;
		// TODO
	}

	// /**
	// * 将json转化为对象
	// *
	// * @param <T>
	// * @param clazzName
	// * 要转换的对象别名
	// * @param clazz
	// * 要转换的对象类型
	// * @param json
	// * 对象的json串
	// * @return 从json对象转换出来的对象
	// */
	// @SuppressWarnings("unchecked")
	// public static <T> T fromXStreamJson(String clazzName, Class clazz, String
	// json) {
	// XStream xstream = new XStream(new JettisonMappedXmlDriver());
	// xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
	// xstream.alias(clazzName, clazz);
	// Object obj = xstream.fromXML(json);
	// return (T) obj;
	// }

	// @SuppressWarnings("unchecked")
	// public static <T> T fromXStreamJson(Class clazz, String json) {
	// XStream xstream = new XStream(new JettisonMappedXmlDriver());
	// xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
	// String simplename = clazz.getSimpleName();
	// String objname = simplename.substring(0, 1).toLowerCase() +
	// simplename.substring(1);
	// xstream.alias(simplename, clazz);
	// xstream.alias(objname, clazz);
	// xstream.alias(clazz.getName(), clazz);
	// Object obj = xstream.fromXML(json);
	// return (T) obj;
	// }

	// /**
	// * 将对象转化为xstream形式的json
	// *
	// * @param o
	// * @return
	// */
	// public static String toXstreamJson(Object o) {
	// try {
	// XStream xstream = new XStream(new JettisonMappedXmlDriver());
	// xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
	// String json = xstream.toXML(o);
	// return json;
	// } catch (Throwable e) {
	// return "{convert to json exception:" + StringHelper.exceptionTrace(e) +
	// "}";
	// }
	// }

	// /**
	// * 利用xstream将pojo从xml文件中反序列化出来
	// *
	// * @param <T>
	// * @param returnClazz
	// * 反序列化出来的pojo class类型
	// * @param pathClazz
	// * xml文件所在的目录下的class，用来方便寻找dat文件
	// * @param filename
	// * 序列号文件xml的名称
	// * @return
	// */
	// public static <T> T fromXML(Class returnClazz, Class pathClazz, String
	// filename) {
	// String path = ClazzHelper.getPathFromPath(pathClazz);
	// return (T) fromXML(returnClazz, path + File.separatorChar + filename);
	// }

	// /**
	// * 利用XStream将pojo保存为xml格式的文件
	// *
	// * @param <T>
	// * @param o
	// * 需要序列化的对象
	// * @param filename
	// * 存储文件的路径名称
	// */
	// public static <T> void toXML(T o, String filename) {
	// try {
	// ExXStream xs = new ExXStream(new DomDriver());
	// // XStream xs = new XStream();
	// FileOutputStream fos = new FileOutputStream(filename);
	// xs.toXML(o, fos);
	// fos.close();
	// } catch (Throwable e) {
	// throw new JTesterException(e);
	// }
	// }

	// /**
	// * 利用xstream将pojo从xml文件中反序列化出来
	// *
	// * @param <T>
	// * @param claz
	// * 反序列化出来的pojo class类型
	// * @param filename
	// * pojo序列化信息文件,如果以"classpath:"开头表示文件存储在classpth的package路径下 <br>
	// * 以"file:" 开头 表示文件的绝对路径
	// * @return
	// */
	// public static <T> T fromXML(Class claz, String filename) {
	// try {
	// InputStream fis = ResourceHelper.getResourceAsStream(filename);
	// if (fis == null) {
	// throw new JTesterException(String.format("file '%s' doesn't exist",
	// filename));
	// }
	// ExXStream xs = new ExXStream(new DomDriver());
	// // XStream xs = new XStream();
	// Object o = xs.fromXML(fis);
	// return (T) o;
	// } catch (FileNotFoundException e) {
	// throw new JTesterException(e);
	// }
	// }
}
