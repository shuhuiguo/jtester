/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.matcher;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.table.TableElement;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class TableElementBuilder<From extends TableElement, Builder extends TableElementBuilder, To extends TableElement> {
	protected final Class<From> type;
	protected List<Builder> elementBuilders = new ArrayList<Builder>();
	final List<To> listOfMockElements = new ArrayList<To>();
	protected String leader = "";
	protected String trailer = "";
	protected String tagLine = "";

	public TableElementBuilder(Class<From> type) {
		this.type = type;
	}

	public From mock(final Mockery context) {
		return mock(context, "", 0);
	}

	public From mock(final Mockery context, String name) {
		return mock(context, name, 0);
	}

	public From mock(final Mockery context, String path, int index) {
		String localPath = localPath(path, index);
		final From from = context.mock(type, localPath);
		int count = 0;
		for (Builder builder : elementBuilders)
			listOfMockElements.add((To) builder.mock(context, localPath, (count++)));
		context.checking(new Expectations() {
			{
				allowing(from).isEmpty();
				will(returnValue(listOfMockElements.isEmpty()));
				allowing(from).size();
				will(returnValue(listOfMockElements.size()));
				allowing(from).iterator();
				will(returnIterator(listOfMockElements));
				allowing(from).getType();
				will(returnValue(type.getSimpleName()));
				allowing(from).getLeader();
				will(returnValue(leader));
				allowing(from).getTrailer();
				will(returnValue(trailer));
				allowing(from).getTagLine();
				will(returnValue(tagLine));
				for (int i = 0; i < elementBuilders.size(); i++) {
					final int ii = i;
					allowing(from).at(i);
					will(returnValue(listOfMockElements.get(ii)));
				}
			}
		});
		if (listOfMockElements.isEmpty())
			context.checking(new Expectations() {
				{
					allowing(from).last();
					will(throwException(new FitLibraryException("It's empty.")));
				}
			});
		else
			context.checking(new Expectations() {
				{
					allowing(from).last();
					will(returnValue(listOfMockElements.get(elementBuilders.size() - 1)));
				}
			});
		return from;
	}

	protected String localPath(String path, int index) {
		if (path == null || "".equals(path))
			return type.getSimpleName() + "[" + index + "]";
		return path + "." + type.getSimpleName() + "[" + index + "]";
	}

	protected List<Builder> withCopyExtendedWith(Builder... els) {
		List<Builder> copy = new ArrayList<Builder>(elementBuilders);
		for (Builder builder : els)
			copy.add(builder);
		return copy;
	}

	protected void with(TableElementBuilder<From, Builder, To> copy, Builder... els) {
		List<Builder> newElementBuilders = new ArrayList<Builder>(elementBuilders);
		for (Builder builder : els)
			newElementBuilders.add(builder);
		copy.leader = leader;
		copy.trailer = trailer;
		copy.tagLine = tagLine;
		copy.elementBuilders = newElementBuilders;
	}

	public void withLeader(TableElementBuilder<From, Builder, To> copy, String allowedLeader) {
		copy.leader = allowedLeader;
		copy.trailer = trailer;
		copy.tagLine = tagLine;
		copy.elementBuilders = elementBuilders;
	}

	public void withTrailer(TableElementBuilder<From, Builder, To> copy, String allowedTrailer) {
		copy.leader = leader;
		copy.trailer = allowedTrailer;
		copy.tagLine = tagLine;
		copy.elementBuilders = elementBuilders;
	}

	public void withTagLine(TableElementBuilder<From, Builder, To> copy, String allowedTagLine) {
		copy.leader = leader;
		copy.trailer = trailer;
		copy.tagLine = allowedTagLine;
		copy.elementBuilders = elementBuilders;
	}
}
