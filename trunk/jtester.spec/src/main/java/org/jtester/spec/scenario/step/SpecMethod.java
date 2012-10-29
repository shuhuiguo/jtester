package org.jtester.spec.scenario.step;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jtester.spec.JSpec;
import org.jtester.spec.annotations.Named;
import org.jtester.tools.commons.AnnotationHelper;
import org.jtester.tools.reflector.MethodAccessor;

@SuppressWarnings("rawtypes")
public class SpecMethod {
	private final MethodAccessor accessor;

	private final List<String> paraNameds;

	private final List<Type> paraTypes;

	public SpecMethod(Method method) {
		this.accessor = new MethodAccessor(method);
		this.paraNameds = this.getParaAnnotationNames(method);
		this.paraTypes = this.getParaTypes(method);
		if (this.paraNameds.size() != this.paraTypes.size()) {
			throw new RuntimeException(
					"the size of Parameter Named Annotation should be equal to the size of Parameter type.");
		}
	}

	List<Type> getParaTypes(Method method) {
		List<Type> types = new ArrayList<Type>();
		Type[] paras = method.getGenericParameterTypes();
		for (Type para : paras) {
			types.add(para);
		}
		return types;
	}

	List<String> getParaAnnotationNames(Method method) {
		List<String> names = new ArrayList<String>();
		Annotation[][] arrays = method.getParameterAnnotations();
		for (Annotation[] annotations : arrays) {
			String name = this.annotationName(annotations);
			if (name == null) {
				throw new RuntimeException(String.format("the argument of method[%s] missing Named annotation.",
						method.getName()));
			} else {
				names.add(name);
			}
		}
		return names;
	}

	private String annotationName(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAssignableFrom(Named.class)) {
				return ((Named) annotation).value();
			}
		}
		return null;
	}

	public Object invoke(JSpec test, JSpecStep step) {
		Object[] args = step.getArguments(paraNameds, paraTypes);
		Object o = this.accessor.invokeUnThrow(test, args);
		return o;
	}

	public static Map<SpecMethodID, SpecMethod> findMethods(Class claz) {
		Map<SpecMethodID, SpecMethod> map = new HashMap<SpecMethodID, SpecMethod>();
		SpecMethod.putSpecTypeMethod(map, claz, StepType.Given);
		SpecMethod.putSpecTypeMethod(map, claz, StepType.When);
		SpecMethod.putSpecTypeMethod(map, claz, StepType.Then);

		return map;
	}

	private static void putSpecTypeMethod(Map<SpecMethodID, SpecMethod> map, Class claz, StepType type) {
		Set<Method> methods = AnnotationHelper.getMethodsAnnotatedWith(claz, type.getAnnotatonClaz());
		for (Method method : methods) {
			String methodName = method.getName();
			int count = method.getParameterTypes().length;
			SpecMethodID id = new SpecMethodID(type, methodName, count);
			if (map.containsKey(id)) {
				throw new RuntimeException(String.format(
						"the class[%s] has contain a %s method named %s and with %d arguments.", claz.getName(), type,
						methodName, count));
			}
			SpecMethod specMethod = new SpecMethod(method);
			map.put(id, specMethod);
		}
	}

	/**
	 * This is a different class, because the @Inject jar may not be in the
	 * classpath.
	 */
	// public static class Jsr330Helper {
	// private static String getNamedValue(Annotation annotation) {
	// return ((javax.inject.Named) annotation).value();
	// }
	// }
}
