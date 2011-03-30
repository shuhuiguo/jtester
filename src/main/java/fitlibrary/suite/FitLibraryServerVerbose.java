/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.suite;

public class FitLibraryServerVerbose {
	public static void main(String[] args) {
		String[] args2 = new String[args.length + 1];
		for (int i = 0; i < args.length; i++)
			args2[i + 1] = args[i];
		args2[0] = "-v";
		FitLibraryServer.main(args2);
	}
}
