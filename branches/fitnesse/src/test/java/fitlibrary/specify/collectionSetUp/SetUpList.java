/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/08/2006
*/

package fitlibrary.specify.collectionSetUp;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.User;

@SuppressWarnings("rawtypes")
public class SetUpList implements DomainFixtured {
	private List iOUs = new ArrayList();

	public User nameOwe(String name, double owe) {
		return new User(name,owe);
	}
	public List getIOUs() {
		return iOUs;
	}
	public void setIOUs(List us) {
		iOUs = us;
	}
}
