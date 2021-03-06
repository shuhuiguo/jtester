/**
 * Copyright 2006-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ext.jtester.objenesis.instantiator.basic;

/**
 * Instantiates a class by grabbing the no-args constructor, making it
 * accessible and then calling Constructor.newInstance(). Although this still
 * requires no-arg constructors, it can call non-public constructors (if the
 * security manager allows it).
 * 
 * @author Joe Walnes
 * @see org.objenesis.instantiator.ObjectInstantiator
 */
public class AccessibleInstantiator extends ConstructorInstantiator {

	@SuppressWarnings("rawtypes")
	public AccessibleInstantiator(Class type) {
		super(type);
		if (constructor != null) {
			constructor.setAccessible(true);
		}
	}
}
