package fitlibrary.object;

import java.lang.reflect.InvocationTargetException;

public interface Finder {
	Object find(String text) throws Exception, IllegalAccessException, InvocationTargetException;

	String show(Object result) throws Exception;

	boolean hasFinderMethod();

}
