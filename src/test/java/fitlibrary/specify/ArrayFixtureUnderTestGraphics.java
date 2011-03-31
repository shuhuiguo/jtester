/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.parser.tree.ListTree;

public class ArrayFixtureUnderTestGraphics extends fitlibrary.ArrayFixture {
	public ArrayFixtureUnderTestGraphics() throws Exception {
		super(new GraphicElement[]{
	   			new GraphicElement(1,"a"),
				new GraphicElement(1,"<ul><li>a</li></ul>"),
				new GraphicElement(2,"<ul><li>a</li><li>BB</li></ul>")});
	}
	public static class GraphicElement {
		private int i;
		private ListTree tree;

		public GraphicElement(int i, String tree) {
			this.i = i;
			this.tree = ListTree.parse(tree);
		}
		public int getI() {
			return i;
		}
		public ListTree getTree() {
			return tree;
		}
	}
}
