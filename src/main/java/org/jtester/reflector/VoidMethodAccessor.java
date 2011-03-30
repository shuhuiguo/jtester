/**
 * Copyright � 2007 J2Speed. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.reflector;

/**
 * Accessor to a void method.
 * 
 * @version 1.0
 * @since 0.1
 * @author Alessandro Nistico
 */
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
	public VoidMethodAccessor(String methodName, Object target, Class<?>... parametersType) {
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
