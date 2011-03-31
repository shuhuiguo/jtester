/*
 * @author Rick Mugridge 6/12/2003
 *
 * Copyright (c) 2003 Rick Mugridge, University of Auckland, NZ
 * Released under the terms of the GNU General Public License version 2 or later.
 *
 */
package fitbook;

public class DiscountGroupList extends fit.RowFixture { //COPY:ALL
	@Override
	public Object[] query() throws Exception { //COPY:ALL
		return DiscountGroup.getElements(); //COPY:ALL
	} //COPY:ALL
	@Override
	public Class<?> getTargetClass() { //COPY:ALL
		return DiscountGroup.class; //COPY:ALL
	} //COPY:ALL
} //COPY:ALL
