package org.jtester.reflector;

/**
 * Accessor to a void method.
 * 
 */
@SuppressWarnings({ "rawtypes" })
public class VoidMethodAccessor extends AbstractMethodAccessor<Void> {

	/**
	 * Constructor.
	 * 
	 * @param methodName
	 *            the method name
	 * @param target
	 *            the target object
	 * @param parametersType
	 *            the parameters signature.
	 */
	public VoidMethodAccessor(String methodName, Object target, Class... parametersType) {
		super(methodName, target, parametersType);
	}

	/**
	 * Common method invocation.
	 * 
	 * @param args
	 *            the arguments for the method.
	 */
	public void invoke(Object... args) {
		super.invokeBase(args);
	}
}
