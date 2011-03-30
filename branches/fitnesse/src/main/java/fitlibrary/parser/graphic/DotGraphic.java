/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.graphic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import fitlibrary.differences.LocalFile;
import fitlibrary.log.Logging;
import fitlibrary.traverse.Traverse;

/**
 * Used to check that the Dot file associated with the GIF matches the expected
 * Dot file contents. It assumes that Dot is installed, as it runs it to produce
 * a GIF for an actual value that doesn't match. This general approach can be
 * used with any image-making scheme.
 */
public class DotGraphic implements GraphicInterface {
	private static final Random random = new Random(System.currentTimeMillis());
	protected String dot;

	public DotGraphic(String dot) {
		this.dot = dot;
	}

	public static DotGraphic parseGraphic(LocalFile file) {
		return new DotGraphic(getDot(file));
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof DotGraphic))
			return false;
		return dot.equals(((DotGraphic) other).dot);
	}

	// @Override
	public LocalFile toGraphic() {
		try {
			LocalFile actualImageFile = actualImageFile(dot);
			Logging.log(this, "toGraphic(): '" + actualImageFile + "'");
			return actualImageFile;
		} catch (IOException ex) {
			throw new RuntimeException("Problem with Dot: " + ex);
		}
	}

	@Override
	public String toString() {
		String htmlImageLink = toGraphic().htmlImageLink();
		Logging.log(this, "toString(): '" + htmlImageLink + "'");
		return htmlImageLink;
	}

	private LocalFile actualImageFile(String actualDot) throws IOException {
		final String actuals = "tempActuals";
		String actualName;
		synchronized (random) {
			actualName = actuals + "/actual" + random.nextInt(999999);
		}
		Traverse.getLocalFile(actuals).mkdirs();
		File dotFile = Traverse.getLocalFile(actualName + ".dot").getFile();
		FileWriter writer = new FileWriter(dotFile);
		writer.write(actualDot);
		writer.close();
		LocalFile imageFileName = Traverse.getLocalFile(actualName + ".gif");
		File imageFile = imageFileName.getFile();
		// Run dot on the file to produce GIF
		Process process = Runtime.getRuntime().exec(
				"dot -Tgif \"" + dotFile.getAbsolutePath() + "\" -o \"" + imageFile.getAbsolutePath() + "\"");
		try {
			process.waitFor();
		} catch (InterruptedException e1) {
			throw new RuntimeException("Dot process timed out.");
		}
		if (process.exitValue() != 0)
			throw new RuntimeException("Problems with actual Dot:\n" + actualDot);
		return imageFileName;
	}

	@Override
	public int hashCode() {
		return dot.hashCode();
	}

	private static String getDot(LocalFile file) {
		return getFileContents(file.withSuffix("dot").getFile());
	}

	private static String getFileContents(File file) {
		FileReader reader;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			return new String(chars);
		} catch (IOException ex) {
			throw new RuntimeException("Problem reading " + file.getAbsolutePath() + ": " + ex);
		}
	}
}
