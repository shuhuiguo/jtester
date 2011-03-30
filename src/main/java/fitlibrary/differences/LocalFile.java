/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.differences;

import java.io.File;

public interface LocalFile {

	/**
	 * @param string
	 * @return another LocalFile that is the same as this except that the suffix is changed.
	 * Eg, "a.bbb" with new suffix "dot" would be "a.dot"
	 */
	LocalFile withSuffix(String string);

	/**
	 * @return a real File that can be used to read/write to the file system
	 */
	File getFile();

	/**
	 * If it doesn't exist, make a directory locally
	 */
	void mkdirs();

	/**
	 * @return a link to this local image file, of the form
	 *  "<img src="files/gameImages/wall.gif">
	 */
	String htmlImageLink();

	/**
	 * @return a link to this file, of the form
	 *  <a href="apple/pear">pear</a>";
	 */
	String htmlLink();

}
