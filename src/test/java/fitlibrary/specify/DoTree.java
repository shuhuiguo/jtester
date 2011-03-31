/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import java.util.List;

import fitlibrary.parser.tree.ListTree;
import fitlibrary.parser.tree.Tree;
import fitlibrary.parser.tree.TreeInterface;

public class DoTree {
	public ListTree tree() {
		ListTree tree = new ListTree("");
		tree.addChild(new ListTree("a"));
		tree.addChild(new ListTree("BB"));
		return tree;
	}

	public ListTree tree(String s) {
		return ListTree.parse(s);
	}

	public TeeTree teeTree(TeeTree t) {
		return t;
	}

	public TeeTree getIt() {
		return new TeeTree(tree());
	}

	public static class TeeTree implements Tree, TreeInterface {
		private Tree tree;

		public TeeTree(Tree tree) {
			this.tree = new ListTree("B", tree.getChildren());
		}

		// @Override
		public String getTitle() {
			return tree.getTitle();
		}

		// @Override
		public String getText() {
			return tree.getText();
		}

		// @Override
		public List<Tree> getChildren() {
			return tree.getChildren();
		}

		// @Override
		public String text() {
			return tree.text();
		}

		public static Tree parseTree(Tree tree) {
			return new TeeTree(tree);
		}

		public Tree findTree(String child) {
			return new ListTree(child);
		}

		@Override
		public String toString() {
			return tree.toString();
		}

		// @Override
		public Tree toTree() {
			return tree;
		}
	}
}
