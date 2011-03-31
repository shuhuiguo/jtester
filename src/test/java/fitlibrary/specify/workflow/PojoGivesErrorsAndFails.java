package fitlibrary.specify.workflow;

import fitlibrary.exception.FitLibraryException;

public class PojoGivesErrorsAndFails {
  public boolean returnsFalse() {
	  return false;
  }
  public int throwsException() {
	  throw new FitLibraryException("error");
  }
}
