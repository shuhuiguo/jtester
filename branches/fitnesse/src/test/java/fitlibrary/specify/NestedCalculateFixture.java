/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NestedCalculateFixture {
	public Point pointXY(int x, int y) {
		return new Point(x,y);
	}
	public int xPoint(Point point) {
		return point.getX();
	}
	public int yPoint(Point point) {
		return point.getY();
	}
	public Point shiftedPointPoint(Point point) { 
		return new Point(point.getX()+1,point.getY()+1);
	}
	public List pointsList(int[] xy) { 
		ArrayList list = new ArrayList();
		for (int i = 0; i < xy.length; i += 2)
			list.add(new Point(xy[i],xy[i+1]));
		return list;
	}
	public int firstXPoints(List points) {
		return ((Point)points.get(0)).getX();
	}
	// The following method is called to create an object for each
	// row of the embedded table:
	public Point xY(int x, int y) {
		return pointXY(x,y);
	}
	 public List identityPoints(List points) {
		 return points;
	 }
	 public Set identitySetOfPoints(Set setOfPoints) {
		 return setOfPoints;
	 }
	 public List pointsPoint(Point point) {
		 ArrayList list = new ArrayList();
		 list.add(point);
		 list.add(point);
		 return list;
	 }
	 public boolean validQuestionPoints(List points) {
		 for (Iterator it = points.iterator(); it.hasNext(); ) {
			 Object object = it.next();
			 it.remove();
			 if (points.contains(object))
				 return false;
		 }
		 return true;
	 }
}
