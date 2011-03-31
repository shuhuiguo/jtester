package fitlibrary.specify.collection;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class NullInCollection {
	@SuppressWarnings("rawtypes")
	public List getList() {
		ArrayList list = new ArrayList();
		list.add(null);
		list.add("fitlibrary");
		return list;
	}

}
