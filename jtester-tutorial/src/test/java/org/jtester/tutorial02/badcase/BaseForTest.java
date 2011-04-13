//package org.jtester.tutorial02.badcase;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jtester.testng.JTester;
//import org.jtester.tutorial.biz.model.LineItem;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
//public class BaseForTest extends JTester {
//	protected List<Object> registers;
//
//	@BeforeMethod
//	public void setup() {
//		registers = new ArrayList<Object>();
//	}
//
//	@AfterMethod
//	public void teardown() {
//		for (Object o : registers) {
//			try {
//				deleteObject(o);
//			} catch (RuntimeException e) {
//				// Nothing to do
//			}
//		}
//	}
//
//	public void deleteObject(Object o) {
//
//	}
//
//	public void assertExpectedLineItem(LineItem expected, LineItem actual) {
//		assertEquals("inv", expected.getInv(), actual.getInv());
//		assertEquals("prod", expected.getProd(), actual.getProd());
//		assertEquals("quant", expected.getQuantity(), actual.getQuantity());
//		assertEquals("discount", expected.getPercentDiscount(), actual.getPercentDiscount());
//		assertEquals("unit price", expected.getUnitPrice(), actual.getUnitPrice(), 0.01);
//		assertEquals("extended", expected.getExtendedPrice(), actual.getExtendedPrice());
//	}
//}
