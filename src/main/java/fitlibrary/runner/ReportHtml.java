/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import fit.Counts;
import fitlibrary.parser.tree.ListTree;
import fitlibrary.parser.tree.Tree;
import fitlibrary.utility.StringUtility;

public class ReportHtml {
	private static Random random = new Random(System.currentTimeMillis());
	private final String title;
	private final String path;
	private final File reportDiry;
	private final String topReportDiryName;
	private String leafRows = "";

	public ReportHtml(String title, File reportDiry, String path, File topReportDiry) {
		this.title = title;
		this.path = path;
		this.reportDiry = reportDiry;
		this.topReportDiryName = topReportDiry.getAbsolutePath() + "/";
	}

	public String html(ListTree tree) {
		return "<html><head><title>" + title + "</title>\n" + headerLinks() + javascript(path) + "</head><body>"
				+ toggledList(tree) + "\n</body></html>";
	}

	private String toggledList(ListTree tree) {
		List<Tree> treeChildren = tree.getChildren();
		if (treeChildren.isEmpty())
			return tree.getTitle();
		String result = "<ul>";
		for (Iterator<Tree> it = treeChildren.iterator(); it.hasNext();)
			result += "<li>" + toggledList(((ListTree) it.next())) + "</li>\n";
		return tree.getTitle() + toggledHtml(result + "</ul>", "Sub-Folders");
	}

	private String toggledHtml(String body, String foldingTitle) {
		int id = random.nextInt();
		return "<div class=\"main\">\n" + " <div class=\"setup\">\n" + "  <a href=\"javascript:toggleCollapsable(\'"
				+ id + "\');\">\n" + "  <img src=\"" + path
				+ "files/images/collapsableOpen.gif\" class=\"left\" id=\"img" + id + "\"/>\n" + "  </a>\n" + " <i>"
				+ foldingTitle + ":</i>\n" + "  <div class=\"collapsable\" id=\"" + id + "\">\n" + body
				+ " </div></div></div>\n";
	}

	public void addTableRow(File reportFile, Counts counts) {
		leafRows += "<tr><td></td><td>" + linkToReportIndexInDiry(reportFile) + "</td>" + tableCell(counts) + "</tr>\n";
	}

	public String title(ReportHtml reportHtml, Counts pageCounts, Counts assertionCounts) {
		String result = header(pageCounts, assertionCounts) + "</table>\n";
		if (!leafRows.equals(""))
			result += reportHtml.toggledHtml("<table>" + leafRows + "</table>\n", "Files");
		return result;
	}

	private String header(Counts pageCounts, Counts assertionCounts) {
		String result = "<table border cellspacing=0 cellpadding=3><tr>";
		if (reportDiry == null)
			result += "<td><b>" + title + "</b></td>";
		else
			result += "<td>" + linkToDiry(title, reportDiry) + "</td>";
		result += tableCell(pageCounts) + tableCell(assertionCounts) + "</tr>\n";
		return result;
	}

	public String headerLinks() {
		return "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path
				+ "files/css/fitnesse.css\" media=\"screen\"/>\n"
				+ "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path
				+ "files/css/fitnesse.css\" media=\"print\"/>\n";
	}

	private static String tableCell(Counts counts) {
		return colour("td", counts) + counts + "</td>";
	}

	private static String colour(String tag, Counts counts) {
		if (counts.wrong > 0)
			return colorTag(tag, "fail");
		else if (counts.exceptions > 0)
			return colorTag(tag, "fit_stacktrace");
		else if (counts.right > 0)
			return colorTag(tag, "pass");
		else
			return colorTag(tag, "ignore");
	}

	private static String colorTag(String tag, String color) {
		return "<" + tag + " class=\"" + color + "\"" + ">";
	}

	private String linkToDiry(String theTitle, File file) {
		String relativePath = relativeUrl(file);
		return "<A HREF=\"" + relativePath + "\">" + theTitle + "</A>";
	}

	private String relativeUrl(File file) {
		String relativePath = file.getAbsolutePath();
		if (relativePath.length() > topReportDiryName.length())
			relativePath = relativePath.substring(topReportDiryName.length());
		return StringUtility.replaceString(relativePath, "\\", "/");
	}

	private String linkToReportIndexInDiry(File file) {
		String relativePath = relativeUrl(file);
		return "<A HREF=\"" + reportName(relativePath) + "\">" + stripSuffix(file.getName()) + "</A>";
	}

	private static String stripSuffix(String name) {
		String upperName = name.toUpperCase();
		int pos = upperName.indexOf(".HTM");
		if (pos < 0)
			return name;
		return name.substring(0, name.length() - ".html".length());
	}

	public static String reportName(File file) {
		return reportName(file.getName());
	}

	private static String reportName(String name) {
		if (FileParseUtilities.isHtmlFileName(name))
			return name;
		int pos = name.indexOf(".");
		if (pos < 0)
			return name + "/" + FolderRunner.INDEX_HTML;
		return name.substring(0, pos) + name.substring(pos + 1) + ".html";
	}

	private String javascript(String thePath) {
		return "<script language=\"JavaScript\" type=\"text/javascript\">\n"
				+ "var collapsableOpenCss = \"collapsable\"\n" + "var collapsableClosedCss = \"hidden\"\n"
				+ "var collapsableOpenImg = \"files/images/collapsableOpen.gif\"\n"
				+ "var collapsableClosedImg = \"files/images/collapsableClosed.gif\"\n"
				+ "function toggleCollapsable(id){\n" + "var div = document.getElementById(id);\n"
				+ "var img = document.getElementById(\"img\" + id);\n"
				+ "if(div.className.indexOf(collapsableClosedCss) != -1) {\n"
				+ "  div.className = collapsableOpenCss;\n" + "  img.src = collapsableOpenImg;\n" + "} else {\n"
				+ "  div.className = collapsableClosedCss;\n" + "  img.src = collapsableClosedImg;\n" + "} }\n"
				+ "</script>\n";
	}
}
