/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 23/09/2006
 */

package fitlibraryGeneric.specify.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;
import fitlibrary.utility.ListCreator;

@SuppressWarnings("unchecked")
public class GenericCollections implements DomainFixtured {
	private List<Colour> aList = ListCreator.list(Colour.RED, Colour.GREEN);
	private Set<Colour> aSet = ListCreator.set(Colour.RED, Colour.GREEN);
	private List<List<Colour>> aListOfLists = ListCreator.list(ListCreator.list(Colour.RED, Colour.GREEN),
			ListCreator.list(Colour.YELLOW, Colour.BLUE));
	private Set<Set<Colour>> aSetOfSets = ListCreator.set(ListCreator.set(Colour.RED, Colour.GREEN),
			ListCreator.set(Colour.YELLOW, Colour.BLUE));
	private List<Set<Colour>> aListOfSets = ListCreator.list(ListCreator.set(Colour.RED, Colour.GREEN),
			ListCreator.set(Colour.YELLOW, Colour.BLUE));
	private Set<List<Colour>> aSetOfLists = ListCreator.set(ListCreator.list(Colour.RED, Colour.GREEN),
			ListCreator.list(Colour.YELLOW, Colour.BLUE));
	private Set<List<Colour>> anEmptySetOfLists = ListCreator.set();
	private List<Set<Colour>> anEmptyListOfSets = ListCreator.list();
	private List<String> aListOfString = new ArrayList<String>();

	public List<Colour> getAList() {
		return aList;
	}

	public void setAList(List<Colour> list) {
		aList = list;
	}

	public List<List<Colour>> getAListOfLists() {
		return aListOfLists;
	}

	public void setAListOfLists(List<List<Colour>> listOfLists) {
		aListOfLists = listOfLists;
	}

	public Set<Colour> getASet() {
		return aSet;
	}

	public void setASet(Set<Colour> set) {
		aSet = set;
	}

	public List<Set<Colour>> getAListOfSets() {
		return aListOfSets;
	}

	public void setAListOfSets(List<Set<Colour>> listOfSets) {
		aListOfSets = listOfSets;
	}

	public Set<List<Colour>> getASetOfLists() {
		return aSetOfLists;
	}

	public void setASetOfLists(Set<List<Colour>> setOfLists) {
		aSetOfLists = setOfLists;
	}

	public Set<Set<Colour>> getASetOfSets() {
		return aSetOfSets;
	}

	public void setASetOfSets(Set<Set<Colour>> setOfSets) {
		aSetOfSets = setOfSets;
	}

	public List<Set<Colour>> getAnEmptyListOfSets() {
		return anEmptyListOfSets;
	}

	public void setAnEmptyListOfSets(List<Set<Colour>> anEmptyListOfSets) {
		this.anEmptyListOfSets = anEmptyListOfSets;
	}

	public Set<List<Colour>> getAnEmptySetOfLists() {
		return anEmptySetOfLists;
	}

	public void setAnEmptySetOfLists(Set<List<Colour>> anEmptySetOfLists) {
		this.anEmptySetOfLists = anEmptySetOfLists;
	}

	public List<String> getAListOfString() {
		return aListOfString;
	}

	public void setAListOfString(List<String> listOfStrings) {
		aListOfString = listOfStrings;
	}
}
