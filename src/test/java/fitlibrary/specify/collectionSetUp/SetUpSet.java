/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/08/2006
*/

package fitlibrary.specify.collectionSetUp;

import java.util.HashSet;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.User;

@SuppressWarnings("rawtypes")
public class SetUpSet implements DomainFixtured {
	private Set iOUSet = new HashSet();
	
	public User nameOwe(String name, double owe) {
		return new User(name,owe);
	}
	public Set getIOUSet() {
		return iOUSet;
	}
	public void setIOUSet(Set set) {
		iOUSet = set;
	}
}
