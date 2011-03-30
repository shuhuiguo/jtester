/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fit.Counts;
import fitlibrary.parser.tree.ListTree;

/**
 * Track the Counts at the page and assertion levels for FolderRunner. It builds
 * a tree of details that is expanded as FolderRunner runs.
 */
public class Report {
	private Counts pageCounts = new Counts();
	private Counts assertionCounts = new Counts();
	private ListTree tree = new ListTree("");
	private Report parent;
	private List<Report> children = new ArrayList<Report>();
	private ReportHtml reportHtml;

	public Report(String title, File reportDiry, String path, File topReportDiry) {
		this.reportHtml = new ReportHtml(title, reportDiry, path, topReportDiry);
	}

	public Report(String title, File reportDiry, Report parent, String path, File topReportDiry) {
		this(title, reportDiry, path, topReportDiry);
		this.parent = parent;
		if (parent != null)
			connectTrees(parent);
	}

	public String getCounts() {
		return pageCounts.toString();
	}

	public String getAssertionCounts() {
		return assertionCounts.toString();
	}

	public String getHtml() {
		return reportHtml.html(copySummariesWithLeaves(this));
	}

	private ListTree copySummariesWithLeaves(Report report) {
		ListTree copy = new ListTree(getTitleWithLeaves(report));
		for (Report child : children)
			copy.addChild(child.copySummariesWithLeaves(report));
		return copy;
	}

	private void setTitleOfTree() {
		tree.setTitle(getTitleWithLeaves(this));
	}

	private String getTitleWithLeaves(Report report) {
		return reportHtml.title(report.reportHtml, pageCounts, assertionCounts);
	}

	public Report firstChild() {
		if (children.isEmpty())
			return null;
		return children.get(0);
	}

	public void exit() {
		displayCounts();
		System.exit(assertionCounts.wrong + assertionCounts.exceptions);
	}

	public void displayCounts() {
		System.err.println(assertionCounts);
	}

	public void addAssertionCountsForPage(File reportFile, Counts counts) {
		incrementPageCounts(counts);
		addAssertions(counts);
		reportHtml.addTableRow(reportFile, counts);
	}

	public void setFinished() {
		if (pageCounts.right + pageCounts.wrong + pageCounts.exceptions + pageCounts.ignores == 0)
			pageCounts.ignores++;
		setTitleOfTree();
	}

	private void incrementPageCounts(Counts counts) {
		if (counts.wrong > 0)
			pageCounts.wrong++;
		else if (counts.exceptions > 0)
			pageCounts.exceptions++;
		else if (counts.right > 0)
			pageCounts.right++;
		else
			pageCounts.ignores++;
		if (parent != null)
			parent.incrementPageCounts(counts);
	}

	private void connectTrees(Report toParent) {
		toParent.tree.addChild(tree); // This builds the complete tree
		toParent.addChild(this); // This is needed to produce summaries
	}

	private void addChild(Report counts) {
		children.add(counts);
	}

	private void addAssertions(Counts counts) {
		add(assertionCounts, counts);
		if (parent != null)
			parent.addAssertions(counts);
	}

	protected static boolean failing(Counts counts) {
		return counts.wrong + counts.exceptions > 0;
	}

	protected boolean failing() {
		return failing(this.pageCounts);
	}

	private static void add(Counts toCounts, Counts counts) {
		toCounts.right += counts.right;
		toCounts.wrong += counts.wrong;
		toCounts.exceptions += counts.exceptions;
		toCounts.ignores += counts.ignores;
	}

	public String addLinks(String html, File input) {
		String body = "<body";
		int index = html.indexOf(body);
		if (index < 0)
			index = html.indexOf("<BODY");
		if (index < 0)
			throw new RuntimeException("Missing &lt;body&gt; in HTML file: " + input.getAbsolutePath());
		int endTag = html.indexOf(">", index);
		return html.substring(0, endTag + 1) + reportHtml.headerLinks() + html.substring(endTag + 1);
	}

	public boolean hasSingleChild() {
		return children.size() == 1;
	}

}
