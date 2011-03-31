/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.exception;

import fitlibrary.traverse.DomainAdapter;

public class ExceptionThrownByNestedFinder implements DomainAdapter {
	public void addToProject(Project subProject, Project project) {
		project.add(subProject);
	}

	public User findUser(String key) {
		throw new ForcedException();
	}

	public Project findProject(String key) {
		return new Project();
	}

	public static class Project {
		public void add(Project subProject) {
			//
		}

		public void setLeader(User user) {
			//
		}
	}

	public static class User {
		//
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
