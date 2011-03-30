/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.definedAction;

import java.io.File;
import java.util.List;

import fitlibrary.exception.FitLibraryExceptionInHtml;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.utility.FileIO;
import fitlibrary.utility.SimpleWikiTranslator;

public class DefineActionsOnPage extends DefineActionsOnPageSlowly {
	private final String rootLocation;

	public DefineActionsOnPage(String topPageName, String rootLocation, RuntimeContextInternal runtime) {
		super(topPageName, runtime);
		this.rootLocation = rootLocation;
	}

	public DefineActionsOnPage(String topPageName, RuntimeContextInternal runtime) {
		this(topPageName, "FitNesseRoot", runtime);
	}

	@Override
	public void process() throws Exception {
		String errors = processPagesAsFiles(topPageName.substring(1));
		if (!"".equals(errors))
			throw new FitLibraryExceptionInHtml("<ul>" + errors + "</ul>");
	}

	private String processPagesAsFiles(String pageName) throws Exception {
		String errors = "";
		String fullPageName = rootLocation + "/" + pageName.replaceAll("\\.", "/");
		File diry = new File(fitNesseDiry(), fullPageName);
		List<File> files = FileIO.filesWithSuffix(diry, "txt");
		for (File file : files) {
			String wiki = FileIO.read(file);
			String html = SimpleWikiTranslator.translate(wiki);
			try {
				if (html.contains("<table")) {
					String fileName = file.getAbsolutePath().replaceAll("/", ".").replaceAll("\\\\", ".");
					Tables tables = TableFactory.tables(html);
					parseDefinitions(tables, determineClassName("", fileName), fileToPageName(file));
				}
			} catch (Exception e) {
				errors += "<li>" + e.getMessage() + "</li>\n";
			}
		}
		return errors;
	}

	private String fileToPageName(File file) {
		String page = file.getAbsolutePath();
		int fitNesseRootIndex = page.indexOf("FitNesseRoot" + File.separator);
		if (fitNesseRootIndex >= 0)
			page = page.substring(fitNesseRootIndex + "FitNesseRoot/".length());
		int diryIndex = page.lastIndexOf(File.separator);
		if (diryIndex >= 0)
			page = page.substring(0, diryIndex);
		page = page.replaceAll("\\" + File.separator, ".");
		return page;
	}
}
