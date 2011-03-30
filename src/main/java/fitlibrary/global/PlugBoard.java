/*
 * Copyright (c) 2006, 2007 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */
package fitlibrary.global;

import fitlibrary.closure.LookupClosureStandard;
import fitlibrary.closure.LookupClosure;
import fitlibrary.closure.LookupMethodTarget;
import fitlibrary.closure.LookupMethodTargetStandard;
import fitlibrary.diff.StringDifferencing;
import fitlibrary.diff.StringDifferencingStandard;
import fitlibrary.exception.ExceptionHandling;
import fitlibrary.exception.ExceptionHandlingStandard;

/*
 * A PlugBoard for Thread-safe objects that can be substituted.
 * Feel free to substitute your own objects in here from your fixturing code.
 */
public class PlugBoard {
	public static LookupMethodTarget lookupTarget = new LookupMethodTargetStandard();
	public static ExceptionHandling exceptionHandling = new ExceptionHandlingStandard();
	public static StringDifferencing stringDifferencing = new StringDifferencingStandard();
	public static LookupClosure lookupClosure = new LookupClosureStandard();
}
