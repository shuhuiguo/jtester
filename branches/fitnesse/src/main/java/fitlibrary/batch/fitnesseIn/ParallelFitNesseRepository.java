/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.fitnesseIn;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import fitlibrary.batch.trinidad.InMemoryTestImpl;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestResultRepository;
import fitnesse.ComponentFactory;
import fitnesse.FitNesseContext;
import fitnesse.WikiPageFactory;
import fitnesse.authentication.PromiscuousAuthenticator;
import fitnesse.html.HtmlPage;
import fitnesse.html.HtmlPageFactory;
import fitnesse.html.SetupTeardownAndLibraryIncluder;
import fitnesse.responders.ResponderFactory;
import fitnesse.responders.WikiImportTestEventListener;
import fitnesse.wiki.PageCrawler;
import fitnesse.wiki.PageCrawlerImpl;
import fitnesse.wiki.PageData;
import fitnesse.wiki.PathParser;
import fitnesse.wiki.WikiPage;
import fitnesse.wiki.WikiPagePath;

/**
 * Like FitNesseRepository but runs in parallel, feeding Tests through a
 * BlockQueue<Test>
 */
public class ParallelFitNesseRepository implements ParallelTestRepository {
	public static final TestDescriptor TEST_SENTINEL = new InMemoryTestImpl("__Finished__", "__Finished__");
	protected FitNesseContext context;
	protected String fitnesseRoot;
	public static final String SUITE_SETUP_NAME = "SuiteSetUp";
	public static final String SUITE_TEARDOWN_NAME = "SuiteTearDown";

	public ParallelFitNesseRepository(String rootDir) throws IOException {
		this(rootDir, 80);
	}

	public ParallelFitNesseRepository(String rootDir, int port) throws IOException {
		setUri(rootDir, port);
	}

	// @Override
	public BlockingQueue<TestDescriptor> getSuite(String name) throws IOException {
		BlockingQueue<TestDescriptor> queue = new LinkedBlockingQueue<TestDescriptor>();
		new Thread(new SuiteLoader(name, queue, context)).start();
		return queue;
	}

	public BlockingQueue<TestDescriptor> getDefinedActions(String name) {
		BlockingQueue<TestDescriptor> queue = new LinkedBlockingQueue<TestDescriptor>();
		String path = name.replaceAll("\\.", "/");
		File topFile = new File(fitnesseRoot + "/FitNesseRoot/" + path);
		new Thread(new DefinedActionLoader(name, queue, context.root, topFile)).start();
		return queue;
	}

	// @Override
	public TestDescriptor getTest(String name) throws IOException {
		try {
			WikiPagePath path = PathParser.parse(name);
			PageCrawler crawler = context.root.getPageCrawler();
			WikiPage page = crawler.getPage(context.root, path);
			if (page == null)
				throw new Error("Test " + name + " not found!");
			WikiPage suiteSetUp = PageCrawlerImpl.getClosestInheritedPage(SUITE_SETUP_NAME, page);
			WikiPage suiteTearDown = PageCrawlerImpl.getClosestInheritedPage(SUITE_TEARDOWN_NAME, page);
			String content = formatWikiPage(name, page, suiteSetUp, suiteTearDown, context);
			return new InMemoryTestImpl(name, content);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException("Error reading test " + name + " " + ex);
		}
	}

	// @Override
	public void prepareResultRepository(TestResultRepository resultRepository) throws IOException {
		File files = new File(new File(new File(fitnesseRoot), "FitNesseRoot"), "files");

		resultRepository.addFile(new File(new File(files, "css"), "fitnesse_base.css"), "fitnesse.css");

		resultRepository.addFile(new File(new File(files, "javascript"), "fitnesse.js"), "fitnesse.js");

		File images = new File(files, "images");
		resultRepository.addFile(new File(images, "collapsableClosed.gif"), "collapsableClosed.gif");
		resultRepository.addFile(new File(images, "collapsableOpen.gif"), "collapsableOpen.gif");
	}

	// @Override
	public void setUri(String uri, int port) throws IOException {
		context = makeContext(uri, port);
		fitnesseRoot = uri;
	}

	private FitNesseContext makeContext(String rootPath, int port) throws IOException {
		try {
			FitNesseContext resultContext = new FitNesseContext();
			FitNesseContext.globalContext = resultContext; // Make the port
															// visible so that
															// ${FITNESSE_PORT}
															// will work
			resultContext.port = port;
			resultContext.rootPath = rootPath;
			ComponentFactory componentFactory = new ComponentFactory(resultContext.rootPath);
			resultContext.rootDirectoryName = "FitNesseRoot"; // arguments.getRootDirectory();
			resultContext.setRootPagePath();
			String defaultNewPageContent = componentFactory.getProperty(ComponentFactory.DEFAULT_NEWPAGE_CONTENT);
			if (defaultNewPageContent != null)
				resultContext.defaultNewPageContent = defaultNewPageContent;
			WikiPageFactory wikiPageFactory = new WikiPageFactory();
			resultContext.responderFactory = new ResponderFactory(resultContext.rootPagePath);
			resultContext.htmlPageFactory = componentFactory.getHtmlPageFactory(new HtmlPageFactory());
			resultContext.root = wikiPageFactory.makeRootPage(resultContext.rootPath, resultContext.rootDirectoryName,
					componentFactory);
			resultContext.logger = null;
			resultContext.authenticator = new PromiscuousAuthenticator();
			WikiImportTestEventListener.register();
			return resultContext;
		} catch (Exception e) {
			throw new IOException(rootPath + " is not a fitnesse root url: " + e);
		}
	}

	public static boolean isSentinel(TestDescriptor test) {
		return test == TEST_SENTINEL;
	}

	public static String formatWikiPage(String name, WikiPage page, WikiPage suiteSetUp, WikiPage suiteTearDown,
			FitNesseContext context) throws Exception {
		PageData pd = page.getData();
		SetupTeardownAndLibraryIncluder.includeInto(pd);
		HtmlPage html = context.htmlPageFactory.newPage();
		html.title.use(name);
		html.header.use(name);

		StringBuffer content = new StringBuffer();
		content.append(pd.getHeaderPageHtml());
		if (suiteSetUp != null)
			content.append(suiteSetUp.getData().getHtml());
		content.append(pd.getHtml());
		if (suiteTearDown != null)
			content.append(suiteTearDown.getData().getHtml());
		pd.setContent(content.toString());
		content.append(pd.getFooterPageHtml());
		html.main.use(content.toString());
		String result = html.html();
		result = result.replace("href=\"/files/css/", "href=\"");
		result = result.replaceAll("/files/javascript/", "");
		result = result.replaceAll("/files/images/", "images/");
		return result;
	}
}
