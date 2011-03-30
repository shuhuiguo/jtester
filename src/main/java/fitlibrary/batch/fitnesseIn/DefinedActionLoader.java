/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.fitnesseIn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import fitlibrary.batch.trinidad.InMemoryTestImpl;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitnesse.wiki.PageCrawler;
import fitnesse.wiki.PathParser;
import fitnesse.wiki.VirtualEnabledPageCrawler;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPagePath;

public class DefinedActionLoader implements Runnable {
	private final String name;
	private final Queue<TestDescriptor> queue;
	private final WikiPage root;
	private final File topFile;

	public DefinedActionLoader(String name, Queue<TestDescriptor> queue, WikiPage root, File topFile) {
		this.name = name;
		this.queue = queue;
		this.root = root;
		this.topFile = topFile;
	}

	// @Override
	public void run() {
		try {
			List<String> pages = getPageNames();
			PageCrawler crawler = root.getPageCrawler();
			crawler.setDeadEndStrategy(new VirtualEnabledPageCrawler());
			for (String pageName : pages) {
				String fullPageName = name + "." + pageName;
				WikiPagePath path = PathParser.parse(fullPageName);
				WikiPage page = crawler.getPage(root, path);
				String html = page.getData().getHtml();
				if (html.contains("<table"))
					queue.add(new InMemoryTestImpl(fullPageName, html));
			}
		} catch (Exception e) {
			queue.add(new InMemoryTestImpl("Exception", "error reading suite " + name + ": " + e));
			e.printStackTrace();
		} finally {
			queue.add(ParallelFitNesseRepository.TEST_SENTINEL);
		}
	}

	private List<String> getPageNames() {
		List<String> pages = new ArrayList<String>();
		traverseFolder(topFile, topFile.getAbsolutePath().length(), pages);
		Collections.sort(pages);
		return pages;
	}

	private void traverseFolder(File file, int prefixLength, List<String> pagesToAccumulate) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory())
				traverseFolder(f, prefixLength, pagesToAccumulate);
			else if (f.getName().equals("content.txt"))
				pagesToAccumulate.add(pageName(file, prefixLength));
		}
	}

	private String pageName(File file, int prefixLength) {
		String resultingName = file.getAbsolutePath().substring(prefixLength);
		if ("".equals(resultingName))
			return resultingName;
		return resultingName.substring(1).replaceAll("\\\\", ".");
	}
}
