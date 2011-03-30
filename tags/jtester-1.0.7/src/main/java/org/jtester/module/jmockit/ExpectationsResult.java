package org.jtester.module.jmockit;

import java.io.File;

import org.jtester.utility.ClazzUtil;
import org.jtester.utility.SerializeUtil;

import mockit.internal.expectations.transformation.ActiveInvocations;

public class ExpectationsResult {
	protected JTesterInvocations currExpectations;

	protected ExpectationsResult(JTesterInvocations expectations) {
		this.currExpectations = expectations;
	}

	public void thenReturn(Object o) {
		if (o instanceof Throwable) {
			currExpectations.returnValue(o);
		} else {
			ActiveInvocations.addResult(o);
		}
	}

	/**
	 * return value parsed from xml file
	 * 
	 * @param claz
	 *            the class of the return object
	 * @param xml
	 */
	public void thenReturnFrom(Class<?> claz, String xml) {
		Object o = SerializeUtil.fromXML(claz, xml);
		thenReturn(o);
	}

	/**
	 * return value parsed from xml file
	 * 
	 * 
	 * @param claz
	 *            the class of the return object
	 * @param path
	 *            class path of the xml file
	 * @param xml
	 */
	public void thenReturnFrom(Class<?> claz, Class<?> clazPath, String xml) {
		String path = ClazzUtil.getPathFromPath(clazPath);
		Object o = SerializeUtil.fromXML(claz, path + File.separatorChar + xml);
		thenReturn(o);
	}

	public void thenReturn(Object o, Object... os) {
		thenReturn(o);
		for (Object o1 : os) {
			thenReturn(o1);
		}
	}

	public void thenThrows(Throwable e) {
		ActiveInvocations.addResult(e);
	}

	public void thenThrows(Throwable e, Throwable... es) {
		thenThrows(e);
		for (Throwable e1 : es) {
			thenThrows(e1);
		}
	}
}
