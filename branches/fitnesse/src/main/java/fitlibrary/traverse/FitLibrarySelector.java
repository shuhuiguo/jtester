/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import java.util.Collection;

import fitlibrary.DomainFixture;
import fitlibrary.collection.CollectionSetUpTraverse;
import fitlibrary.collection.CollectionTraverse;
import fitlibrary.collection.list.ListTraverse;
import fitlibrary.collection.set.SetTraverse;
import fitlibrary.object.DomainObjectCheckTraverse;
import fitlibrary.object.DomainObjectSetUpTraverse;
import fitlibrary.traverse.workflow.DoTraverse;

public class FitLibrarySelector {
	public static final Ordering ORDERED = new Ordering();
	public static final Ordering UNORDERED = new Ordering();
	public static final Subset EXPECT_ALL = new Subset();
	public static final Subset EXPECT_SOME = new Subset();

	public static Traverse selectWorkflow(Object sut) {
		return new DoTraverse(sut);
	}

	public static CollectionTraverse selectUnorderedList(Object actuals) {
		CollectionTraverse traverse = selectUnorderedList();
		traverse.setActualCollection(actuals);
		return traverse;
	}

	public static CollectionTraverse selectUnorderedList() {
		return select(UNORDERED, EXPECT_ALL);
	}

	public static CollectionTraverse selectOrderedList(Object actuals) {
		CollectionTraverse traverse = selectOrderedList();
		traverse.setActualCollection(actuals);
		return traverse;
	}

	public static CollectionTraverse selectOrderedList() {
		return select(ORDERED, EXPECT_ALL);
	}

	public static CollectionTraverse selectSet(Object actuals) {
		CollectionTraverse traverse = selectSet();
		traverse.setActualCollection(actuals);
		return traverse;
	}

	public static CollectionTraverse selectSet() {
		return select(UNORDERED, EXPECT_ALL);
	}

	public static CollectionTraverse selectSubset(Object actuals) {
		CollectionTraverse traverse = selectSubset();
		traverse.setActualCollection(actuals);
		return traverse;
	}

	public static CollectionTraverse selectSubset() {
		return select(UNORDERED, EXPECT_SOME);
	}

	private static CollectionTraverse select(Ordering ordering, Subset subset) {
		CollectionTraverse traverse;
		if (ordering == ORDERED)
			traverse = new ListTraverse();
		else
			traverse = new SetTraverse();
		if (subset == EXPECT_SOME)
			traverse.setShowSurplus(false);
		return traverse;
	}

	public static CollectionSetUpTraverse selectCollectionSetUp() {
		return new CollectionSetUpTraverse();
	}

	public static CollectionSetUpTraverse selectCollectionSetUp(Collection<Object> collection) {
		return new CollectionSetUpTraverse(collection);
	}

	public static DomainObjectCheckTraverse selectDomainCheck(Object domainObject) {
		return new DomainObjectCheckTraverse(domainObject);
	}

	public static DomainObjectSetUpTraverse selectDomainSetUp(Object domainObject) {
		return new DomainObjectSetUpTraverse(domainObject);
	}

	public static DomainFixture selectDomain(Object sut) {
		return new DomainFixture(sut);
	}

	static class Ordering {
		//
	}

	static class Subset {
		//
	}

	static class Header {
		//
	}
}
