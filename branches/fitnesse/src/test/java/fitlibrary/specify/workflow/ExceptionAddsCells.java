package fitlibrary.specify.workflow;

import fitlibrary.DoFixture;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.exception.FitLibraryShowException.Show;

public class ExceptionAddsCells extends DoFixture {
	public boolean addCellTo(String t) { 
		throw new FitLibraryShowException(new Show("added: "+t+"<hr>next line"));
	}
}
