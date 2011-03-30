/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fitlibrary.closure.ClassMethodTarget;
import fitlibrary.closure.ConstantMethodTarget;
import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.closure.MethodTarget;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.exception.IgnoredException;
import fitlibrary.exception.method.NoSuchPropertyException;
import fitlibrary.object.DomainObjectSetUpTraverse;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.ClassUtility;
import fitlibrary.utility.MapElement;

public abstract class CollectionTraverse extends Traverse {
	protected boolean[] usedFields;
	protected Collection<Object> actuals;
	protected boolean showSurplus = true;
	private Class<?> componentType = null; // Is only set if it is known

	protected CollectionTraverse(Object sut) {
		super(sut);
	}

	protected CollectionTraverse(Object sut, Object actuals) {
		super(sut);
		setActualCollection(actuals);
	}

	@SuppressWarnings("unchecked")
	public void setActualCollection(Object actuals) {
		if (actuals instanceof Collection)
			setActualCollection((Collection<Object>) actuals);
		else if (actuals instanceof Iterator)
			setActualCollection((Iterator<Object>) actuals);
		else if (actuals instanceof Map)
			setActualCollection((Map<Object, Object>) actuals);
		else if (actuals instanceof Object[])
			setActualCollection((Object[]) actuals);
		else if (actuals.getClass().isArray())
			setActualCollectionAsArray(actuals);
		else
			throw new RuntimeException("Unable to handle an object of type " + actuals.getClass());
	}

	private void setActualCollectionAsArray(Object actuals) {
		List<Object> list = new ArrayList<Object>();
		int length = Array.getLength(actuals);
		for (int i = 0; i < length; i++)
			list.add(Array.get(actuals, i));
		setActualCollection(list);
	}

	public void setActualCollection(Collection<Object> actuals) {
		this.actuals = actuals;
	}

	public void setActualCollection(Object[] actuals) {
		setActualCollection(Arrays.asList(actuals));
	}

	public void setActualCollection(Iterator<Object> it) {
		List<Object> actualCollection = new ArrayList<Object>();
		while (it.hasNext())
			actualCollection.add(it.next());
		setActualCollection(actualCollection);
	}

	public void setActualCollection(Map<Object, Object> map) {
		setActualCollection(mapMapToSet(map));
	}

	public static List<MapElement> mapMapToSet(Map<Object, Object> map) {
		List<MapElement> elements = new ArrayList<MapElement>();
		Set<Object> keySet = map.keySet();
		for (Iterator<Object> it = keySet.iterator(); it.hasNext();) {
			Object key = it.next();
			elements.add(new MapElement(key, map.get(key)));
		}
		return elements;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		if (actuals == null)
			throw new FitLibraryException("Actual list missing");
		Row firstRow = table.at(1);
		try {
			List<MethodTarget[]> getters = new ArrayList<MethodTarget[]>();
			if (!actuals.isEmpty())
				getters = bindGettersForAllActuals(firstRow, testResults);
			else if (!table.atExists(2))
				table.at(1).pass(testResults);
			for (int rowNo = 2; rowNo < table.size(); rowNo++) {
				firstRow = table.at(rowNo);
				interpretRow(firstRow, getters, testResults);
			}
			if (showSurplus)
				showSurplus(getters, table, testResults);
		} catch (IgnoredException e) {
			//
		} catch (Exception e) {
			firstRow.error(testResults, e);
		}
		return actuals;
	}

	public void setShowSurplus(boolean showSurplus) {
		this.showSurplus = showSurplus;
	}

	public void setComponentType(Class<?> componentType) {
		this.componentType = componentType;
	}

	/** List<Parser[]> */
	protected final List<MethodTarget[]> bindGettersForAllActuals(Row row, TestResults testResults) throws Exception {
		usedFields = new boolean[row.size()];
		for (int i = 0; i < usedFields.length; i++)
			usedFields[i] = false;
		List<MethodTarget[]> bindings = new ArrayList<MethodTarget[]>();
		for (Iterator<Object> it = actuals.iterator(); it.hasNext();) {
			TypedObject typedObject = asTypedObject(it.next());
			bindings.add(bindGettersForOneElement(row, typedObject));
		}
		for (int i = 0; i < usedFields.length; i++)
			if (!usedFields[i]) {
				String propertyName = extendedCamel(row.at(i).text(this));
				String classNames = ClassUtility.allElementClassNames(actuals);
				row.at(i).error(testResults, new NoSuchPropertyException(propertyName, classNames));
				throw new IgnoredException();
			}
		return bindings;
	}

	private MethodTarget[] bindGettersForOneElement(Row row, TypedObject typedObject) {
		MethodTarget[] columnBindings = new MethodTarget[row.size()];
		for (int i = 0; i < columnBindings.length; i++) {
			Cell cell = row.at(i);
			if (componentType != null && DomainObjectSetUpTraverse.givesClass(cell, this)) {
				columnBindings[i] = new ClassMethodTarget(componentType, this, typedObject);
				usedFields[i] = true;
			} else {
				try {
					columnBindings[i] = bindPropertyGetterForTypedObject(cell.text(this), typedObject);
					if (columnBindings[i] != null)
						usedFields[i] = true;
				} catch (NoSuchPropertyException e) {
					throw new IgnoredException();
				}
			}
		}
		return columnBindings;
	}

	protected ICalledMethodTarget bindPropertyGetterForTypedObject(String name, TypedObject typedObject) {
		String mappedName = extendedCamel(name);
		if (typedObject.getSubject() instanceof Map) {
			Object value = ((Map<?, ?>) typedObject.getSubject()).get(mappedName);
			if (value == null)
				value = ((Map<?, ?>) typedObject.getSubject()).get(name);
			if (value == null)
				return null;
			return new ConstantMethodTarget(value, this);
		}
		return typedObject.new_optionallyFindGetterOnTypedObject(name, this);
	}

	protected final boolean matchRow(Row row, MethodTarget[] columnBindings, TestResults testResults) throws Exception {
		boolean matchedAlready = false;
		for (int i = 0; i < columnBindings.length; i++) {
			Cell expectedCell = row.at(i);
			MethodTarget getter = columnBindings[i];
			if (getter == null)
				expectedCell.passOrFailIfBlank(testResults, this);
			else {
				boolean matched = getter.invokeAndCheckCell(expectedCell, matchedAlready, testResults);
				if (!matchedAlready && !matched)
					return false;
				matchedAlready = true;
			}
		}
		return true;
	}

	protected void showSurplus(List<MethodTarget[]> bindings, Table table, TestResults testResults) {
		if (!bindings.isEmpty())
			addSurplusRows(table, bindings, testResults);
	}

	private static void addSurplusRows(Table table, List<MethodTarget[]> surplusBindings, TestResults testResults) {
		for (MethodTarget[] getter : surplusBindings) {
			Row row = table.newRow();
			buildSurplusRow(row, getValues(getter), testResults);
			row.at(0).actualElementMissing(testResults);
		}
	}

	private static void buildSurplusRow(Row row, Object[] values, TestResults testResults) {
		if (values.length == 0) {
			row.addCell("null", values.length);
			return;
		}
		for (int i = 0; i < values.length; i++) {
			Cell addCell = row.addCell("&nbsp;");
			Object value = values[i];
			if (value == null)
				addCell.ignore(testResults);
			else if (value instanceof Exception)
				addCell.error(testResults, (Exception) value);
			else
				addCell.setUnvisitedEscapedText(value.toString());
		}
	}

	private static Object[] getValues(MethodTarget[] getters) {
		Object[] values = new Object[getters.length];
		for (int i = 0; i < getters.length; i++) {
			MethodTarget getter = getters[i];
			if (getter == null)
				values[i] = null;
			else
				try {
					values[i] = getter.getResult();
				} catch (Exception e) {
					values[i] = e;
				}
		}
		return values;
	}

	public abstract void interpretRow(Row row, List<MethodTarget[]> theActuals, TestResults testResults)
			throws Exception;
}
