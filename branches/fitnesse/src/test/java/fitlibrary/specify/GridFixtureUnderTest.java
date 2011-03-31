/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.GridFixture;
import fitlibrary.ImageFixture;
import fitlibrary.parser.graphic.ImageNameGraphic;
import fitlibrary.parser.tree.ListTree;

public class GridFixtureUnderTest {
	public GridFixture empty() {
		return new GridFixture(new Object[][] {});
	}
	public GridFixture strings() {
		return new GridFixture(new String[][] { {"a", "b"}, {"c", "d"} });
	}
	public GridFixture ints() {
		return new GridFixture(new Integer[][] {
				{new Integer(1), new Integer(2)},
				{new Integer(3), new Integer(4)} });
	}
	public GridFixture trees() {
		return new GridFixture(new ListTree[][] {
				{ ListTree.parse("a"),
				  ListTree.parse("<ul><li>a</li></ul>") },
			    { ListTree.parse("<ul><li>BB</li></ul>"),
				  ListTree.parse("<ul><li>a</li><li>BB</li></ul>")} });
	}
    public GridFixture images() {
        return new GridFixture(new ImageNameGraphic[][] {
        		{   new ImageNameGraphic("gameImages/wall.jpg"),
        			new ImageNameGraphic("gameImages/space.jpg"),
        			new ImageNameGraphic("gameImages/box.jpg"),
        			new ImageNameGraphic("gameImages/space.jpg"),
        			new ImageNameGraphic("gameImages/wall.jpg") }});
    }
    public GridFixture imagesForImageFixture() {
        return new ImageFixture(new String[][] {
        		{   "gameImages/wall.jpg",
        			"gameImages/space.jpg",
        			"gameImages/box.jpg",
        			"gameImages/space.jpg",
        			"gameImages/wall.jpg" }});
    }
}
