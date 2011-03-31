/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.CommentTraverse;

public class CommentFixture extends FitLibraryFixture {
	private CommentTraverse commentTraverse = new CommentTraverse();

	public CommentFixture() {
		this(false);
	}

	public CommentFixture(boolean markAsIgnored) {
		commentTraverse.setMarkAsIgnored(markAsIgnored);
		setTraverse(commentTraverse);
	}
}
