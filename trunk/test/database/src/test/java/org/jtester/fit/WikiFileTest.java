package org.jtester.fit;

import org.jtester.IAssertion;
import org.junit.Test;

public class WikiFileTest implements IAssertion {

	@Test
	public void testFindWikiFile() throws Exception {
		WikiFile wiki = WikiFile.findWikiFile(null, "dbfit/jar/file/test.wiki");
		want.object(wiki).propertyEq("wikiUrl", "dbfit/jar/file/test.wiki").propertyEq("wikiName", "test");
	}

	@Test
	public void testReadWikiContent() throws Exception {
		WikiFile wiki = WikiFile.findWikiFile(null, "dbfit/jar/file/test.wiki");
		String content = wiki.readWikiContent();
		want.string(content).notNull().contains("|connect|");
	}

}
