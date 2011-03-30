/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.special;

import java.lang.reflect.Method;

public interface PositionedTargetFactory {
	PositionedTarget create(Method method, int from, int upTo);
}
