/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.workflow;

public class AnyCharactersInActions {
	 public int plusEquals(int arg1, int arg2) {
		 return arg1+arg2;
	 }
	 public String quoteQuotePlusQuoteQuoteEquals(String arg1, String arg2) {
		 return arg1+" "+arg2;
	 }
	 public boolean leftSquareBracketAmpersandAmpersandRightSquareBracketEquals(boolean arg1, boolean arg2) {
		 return arg1 && arg2;
	 }
}
