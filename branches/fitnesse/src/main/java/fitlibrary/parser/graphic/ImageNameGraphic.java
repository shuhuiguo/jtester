/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.graphic;

import org.apache.log4j.Logger;

import fitlibrary.differences.LocalFile;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.traverse.Traverse;

/**
 * Used to check whether the name of the image is as expected.
 */
public class ImageNameGraphic implements GraphicInterface {
	private static Logger logger = FitLibraryLogger.getLogger(ImageNameGraphic.class);
	private LocalFile expectedFile;

	public ImageNameGraphic(String expectedFileName) {
		this.expectedFile = Traverse.getLocalFile(expectedFileName);
	}

	public ImageNameGraphic(LocalFile expectedFile) {
		this.expectedFile = expectedFile;
	}

	// @Override
	public LocalFile toGraphic() {
		return expectedFile;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ImageNameGraphic))
			return false;
		return expectedFile.equals(((ImageNameGraphic) object).expectedFile);
	}

	public static GraphicInterface parseGraphic(LocalFile expectedFile) {
		logger.trace("parseGraphic(): " + expectedFile);
		return new ImageNameGraphic(expectedFile);
	}

	@Override
	public String toString() {
		return "ImageNameGraphic[" + expectedFile + "]";
	}

	@Override
	public int hashCode() {
		return expectedFile.hashCode();
	}
}
