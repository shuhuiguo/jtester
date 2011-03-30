/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.global;

import fitlibrary.definedAction.DefinedActionsRepositoryStandard;
import fitlibrary.definedAction.DefinedActionsRepository;

/*
 * This will be eliminated once I've wired in a full Runtime into the execution, so there's no needed for these to be global.
 * For now, it manages thread-safety with ThreadLocals. 
 */
public class TemporaryPlugBoardForRuntime {
	private static ThreadLocal<DefinedActionsRepository> definedActionsPerThread = new ThreadLocal<DefinedActionsRepository>();

	public static DefinedActionsRepository definedActionsRepository() {
		DefinedActionsRepository repository = definedActionsPerThread.get();
		if (repository == null) {
			repository = new DefinedActionsRepositoryStandard();
			definedActionsPerThread.set(repository);
		}
		return repository;
	}
}
