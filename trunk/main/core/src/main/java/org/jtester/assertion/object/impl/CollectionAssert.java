package org.jtester.assertion.object.impl;

import java.util.Collection;

import org.jtester.assertion.common.impl.AllAssert;
import org.jtester.assertion.object.intf.ICollectionAssert;

@SuppressWarnings("rawtypes")
public class CollectionAssert extends AllAssert<Collection, ICollectionAssert> implements ICollectionAssert {

	public CollectionAssert() {
		super(ICollectionAssert.class);
		this.valueClaz = Collection.class;
	}

	public CollectionAssert(Collection value) {
		super(value, ICollectionAssert.class);
		this.valueClaz = Collection.class;
	}
}
