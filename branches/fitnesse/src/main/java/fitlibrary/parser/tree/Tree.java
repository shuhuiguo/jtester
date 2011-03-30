/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.tree;

import java.util.List;

public interface Tree {
	String getTitle();

	String getText(); // Title without HTML tags

	List<Tree> getChildren();

	String text();
}
