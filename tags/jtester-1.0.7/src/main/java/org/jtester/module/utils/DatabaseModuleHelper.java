/*
 * Copyright 2008,  Unitils.org
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
package org.jtester.module.utils;

import java.lang.reflect.Method;

import org.jtester.module.ModulesRepository;
import org.jtester.module.core.DatabaseModule;

/**
 * Class providing access to the functionality of the database module using
 * static methods. Meant to be used directly in unit tests.
 * 
 * @author Filip Neven
 * @author Tim Ducheyne
 */
public class DatabaseModuleHelper {
	private final static ModulesRepository modulesRepository = ModuleHelper.getInstance().getModulesRepository();

	/**
	 * Disables all foreign key and not-null constraints on the configured
	 * schema's.
	 */
	public static void disableConstraints() {
		getDatabaseModule().disableConstraints();
	}

	/**
	 * Gets the instance DatabaseModule that is registered in the modules
	 * repository. This instance implements the actual behavior of the static
	 * methods in this class. This way, other implementations can be plugged in,
	 * while keeping the simplicity of using static methods.
	 * 
	 * @return the instance, not null
	 */
	private static DatabaseModule getDatabaseModule() {
		return ModuleHelper.getInstance().getModulesRepository().getModuleOfType(DatabaseModule.class);
	}

	/**
	 * 当前testcase中测试是否被disabled
	 * 
	 * @param testObject
	 * @param testMethod
	 * @return
	 */
	public static boolean isDisabledTransaction(Object testObject, Method testMethod) {
		if (modulesRepository.isModuleEnabled(DatabaseModule.class) == false) {
			return true;
		} else {
			DatabaseModule module = modulesRepository.getModuleOfType(DatabaseModule.class);
			boolean enabled = module.isTransactionsEnabled(testObject, testMethod);
			return !enabled;
		}
	}
}
