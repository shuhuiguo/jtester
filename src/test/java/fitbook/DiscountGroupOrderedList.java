/*
 * @author Rick Mugridge 1/05/2004
 * Copyright (c) 2004 Rick Mugridge, University of Auckland, NZ
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitbook;

/**
 *
 */
public class DiscountGroupOrderedList extends fit.RowFixture { //COPY:ALL
	@Override
	public Class<?> getTargetClass() { //COPY:ALL
		return OrderedDiscountGroup.class; //COPY:ALL
	} //COPY:ALL
	@Override
	public Object[] query() throws Exception { //COPY:ALL
		DiscountGroup[] groups = DiscountGroup.getElements(); //COPY:ALL
		OrderedDiscountGroup[] ordered = //COPY:ALL
					new OrderedDiscountGroup[groups.length]; //COPY:ALL
		for (int i = 0; i < groups.length; i++) { //COPY:ALL
			DiscountGroup g = groups[i]; //COPY:ALL
			ordered[i] = new OrderedDiscountGroup(i+1,//COPY:ALL
					g.getFutureValue(),g.getMaxOwing(),//COPY:ALL
					g.getMinPurchase(),g.getDiscountPercent()); //COPY:ALL
		} //COPY:ALL
		return ordered; //COPY:ALL
	} //COPY:ALL
} //COPY:ALL
