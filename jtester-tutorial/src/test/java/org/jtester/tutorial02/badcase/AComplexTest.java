//package org.jtester.tutorial02.badcase;
//
//import static org.jtester.tutorial02.badcase.AComplexTestHelper.createAProduct;
//import static org.jtester.tutorial02.badcase.AComplexTestHelper.createInvoice;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.jtester.tutorial.biz.model.Address;
//import org.jtester.tutorial.biz.model.Customer;
//import org.jtester.tutorial.biz.model.Invoice;
//import org.jtester.tutorial.biz.model.LineItem;
//import org.jtester.tutorial.biz.model.Product;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//public class AComplexTest extends BaseForTest {
//	@Test
//	public void testAddItemQuantity_severalQuantity_v0() {
//		Address billingAddress = null;
//		Address shippingAddress = null;
//		Customer customer = null;
//		Product product = null;
//		Invoice invoice = null;
//		try {
//			// Set up fixture
//			billingAddress = new Address("1222 1st St SW Calgary Alberta T2N 2V2 Canada", "310012");
//			customer = new Customer("John.Doe", billingAddress);
//			product = new Product("SomeWidget", 19.99);
//			invoice = new Invoice(customer);
//
//			// Exercise SUT
//			invoice.addItemQuantity(product, 5);
//			// Verify outcome
//			List<LineItem> lineItems = invoice.getLineItems();
//			if (lineItems.size() == 1) {
//				LineItem actItem = (LineItem) lineItems.get(0);
//				actItem.caculate();
//				assertEquals("inv", invoice, actItem.getInvoice());
//				assertEquals("prod", product, actItem.getProduct());
//				assertEquals("quant", 5, actItem.getQuantity());
//				assertEquals("discount", 30, actItem.getDiscount());
//				assertEquals("unit price", 19.99, actItem.getUnitPrice(), 0.01);
//				assertEquals("extended", new BigDecimal("69.96"), actItem.getTotolPrice());
//			}
//		} finally {
//			// Teardown
//			deleteObject(invoice);
//			deleteObject(product);
//			deleteObject(customer);
//			deleteObject(billingAddress);
//			deleteObject(shippingAddress);
//		}
//	}
//
//	@Test
//	public void testAddItemQuantity_severalQuantity_v1() {
//		Address billingAddress = null;
//		Address shippingAddress = null;
//		Customer customer = null;
//		Product product = null;
//		Invoice invoice = null;
//		try {
//			// Set up fixture
//			billingAddress = new Address("1222 1st St SW Calgary Alberta T2N 2V2 Canada", "310012");
//			customer = new Customer("John.Doe", billingAddress);
//			product = new Product("SomeWidget", 19.99);
//			invoice = new Invoice(customer);
//
//			// Exercise SUT
//			invoice.addItemQuantity(product, 5);
//			// Verify outcome
//			List<LineItem> lineItems = invoice.getLineItems();
//			if (lineItems.size() == 1) {
//				LineItem actItem = (LineItem) lineItems.get(0);
//				actItem.caculate();
//				assertEquals("inv", invoice, actItem.getInvoice());
//				assertEquals("prod", product, actItem.getProduct());
//				assertEquals("quant", 5, actItem.getQuantity());
//				assertEquals("discount", 30, actItem.getDiscount());
//				assertEquals("unit price", 19.99, actItem.getUnitPrice(), 0.01);
//				assertEquals("extended", new BigDecimal("69.96"), actItem.getTotolPrice());
//			} else {
//				assertTrue("Invoice should have 1 item", false);
//			}
//		} finally {
//			// Teardown
//			deleteObject(invoice);
//			deleteObject(product);
//			deleteObject(customer);
//			deleteObject(billingAddress);
//			deleteObject(shippingAddress);
//		}
//	}
//
//	@Test
//	public void testAddItemQuantity_severalQuantity_v2() {
//		Address billingAddress = null;
//		Address shippingAddress = null;
//		Customer customer = null;
//		Product product = null;
//		Invoice invoice = null;
//		try {
//			// Set up fixture
//			billingAddress = new Address("1222 1st St SW Calgary Alberta T2N 2V2 Canada", "310012");
//			customer = new Customer("John.Doe", billingAddress);
//			product = new Product("SomeWidget", 19.99);
//			invoice = new Invoice(customer);
//
//			// Exercise SUT
//			invoice.addItemQuantity(product, 5);
//			// Verify outcome
//			List<LineItem> lineItems = invoice.getLineItems();
//			if (lineItems.size() == 1) {
//				LineItem expected = new LineItem(invoice, product, 5, 30, 19.99, new BigDecimal("69.96"));
//				LineItem actual = lineItems.get(0);
//				actual.caculate();
//				// assertExpectedLineItem(expected, actual);
//				want.object(lineItems.get(0)).reflectionEq(expected);
//				// want.object(lineItems.get(0)).propertyEq(new String[] {
//				// "inv", "prod", "quantity", "percentDiscount", "" }, new
//				// Object[] {});
//			} else {
//				assertTrue("Invoice should have 1 item", false);
//			}
//		} finally {
//			// Teardown
//			deleteObject(invoice);
//			deleteObject(product);
//			deleteObject(customer);
//			deleteObject(billingAddress);
//			deleteObject(shippingAddress);
//		}
//	}
//
//	@Test
//	public void testAddItemQuantity_severalQuantity_v3() {
//		Address billingAddress = null;
//		Address shippingAddress = null;
//		Customer customer = null;
//		Product product = null;
//		Invoice invoice = null;
//		try {
//			// Set up fixture
//			billingAddress = new Address("1222 1st St SW Calgary Alberta T2N 2V2 Canada", "310012");
//			customer = new Customer("John.Doe", billingAddress);
//			product = new Product("SomeWidget", 19.99);
//			invoice = new Invoice(customer);
//
//			// Exercise SUT
//			invoice.addItemQuantity(product, 5);
//			// Verify outcome
//
//			List<LineItem> lineItems = invoice.getLineItems();
//			want.collection(lineItems).sizeEq(1);
//			LineItem expected = new LineItem(invoice, product, 5, 30, 19.99, new BigDecimal("69.96"));
//			LineItem actual = lineItems.get(0);
//			actual.caculate();
//			// assertExpectedLineItem(expected, actual);
//			want.object(actual).reflectionEq(expected);
//
//		} finally {
//			// Teardown
//			deleteObject(invoice);
//			deleteObject(product);
//			deleteObject(customer);
//			deleteObject(billingAddress);
//			deleteObject(shippingAddress);
//		}
//	}
//
//	@Test
//	public void testAddItemQuantity_severalQuantity_v4() {
//		Address billingAddress = null;
//		Address shippingAddress = null;
//		Customer customer = null;
//		Product product = null;
//		Invoice invoice = null;
//		try {
//			// Set up fixture
//			billingAddress = new Address("1222 1st St SW Calgary Alberta T2N 2V2 Canada", "310012");
//			customer = new Customer("John.Doe", billingAddress);
//			product = new Product("SomeWidget", 19.99);
//			invoice = new Invoice(customer);
//
//			// Exercise SUT
//			invoice.addItemQuantity(product, 5);
//			// Verify outcome
//
//			List<LineItem> lineItems = invoice.getLineItems();
//			want.collection(lineItems).sizeEq(1);
//			LineItem expected = new LineItem(invoice, product, 5, 30, 19.99, new BigDecimal("69.96"));
//			LineItem actual = lineItems.get(0);
//			actual.caculate();
//			// assertExpectedLineItem(expected, actual);
//			want.object(actual).reflectionEq(expected);
//
//		} finally {
//			try {
//				deleteObject(invoice);
//			} finally {
//				try {
//					deleteObject(product);
//				} finally {
//					try {
//						deleteObject(customer);
//					} finally {
//						try {
//							deleteObject(billingAddress);
//						} finally {
//							deleteObject(shippingAddress);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	@Test
//	public void testAddItemQuantity_severalQuantity_v5() {
//		// Set up fixture
//		Address billingAddress = new Address("1222 1st St SW Calgary Alberta T2N 2V2 Canada", "310012");
//		Customer customer = new Customer("John.Doe", billingAddress);
//		registers.add(customer);
//		Product product = new Product("SomeWidget", 19.99);
//		registers.add(product);
//		Invoice invoice = new Invoice(customer);
//		registers.add(invoice);
//
//		// Exercise SUT
//		invoice.addItemQuantity(product, 5);
//		// Verify outcome
//
//		List<LineItem> lineItems = invoice.getLineItems();
//		want.collection(lineItems).sizeEq(1);
//		LineItem expected = new LineItem(invoice, product, 5, 30, 19.99, new BigDecimal("69.96"));
//		LineItem actual = lineItems.get(0);
//		actual.caculate();
//		// assertExpectedLineItem(expected, actual);
//		want.object(actual).reflectionEq(expected);
//	}
//
//	@Test
//	public void testAddItemQuantity_severalQuantity_v6() {
//		// Set up fixture
//		Product product = createAProduct(19.99);
//		Invoice invoice = createInvoice(30);
//		// Exercise SUT
//		invoice.addItemQuantity(product, 5);
//		// Verify outcome
//		List<LineItem> lineItems = invoice.getLineItems();
//		want.collection(lineItems).sizeEq(1);
//		LineItem expected = new LineItem(invoice, product, 5, 30, 19.99, new BigDecimal("69.96"));
//		assertExpectedLineItem(expected, lineItems.get(0));
//	}
//
//	@Test(dataProvider = "provideData")
//	public void testAddItemQuantity_severalQuantity_v7(double unitPrice, int quantity, int percentDiscount,
//			BigDecimal extendedPrice) {
//		// Set up fixture
//		Product product = createAProduct(unitPrice);
//		Invoice invoice = createInvoice(percentDiscount);
//		// Exercise SUT
//		invoice.addItemQuantity(product, quantity);
//		// Verify outcome
//		List<LineItem> lineItems = invoice.getLineItems();
//		want.collection(lineItems).sizeEq(1);
//		LineItem expected = new LineItem(invoice, product, quantity, percentDiscount, unitPrice, extendedPrice);
//		assertExpectedLineItem(expected, lineItems.get(0));
//	}
//
//	@DataProvider
//	public Object[][] provideData() {
//		return new Object[][] { { 19.99, 5, 30, new BigDecimal("69.96") }, // <br>
//				{ 19.99, 5, 100, new BigDecimal("0.00") },// <br>
//				{ 19.99, 5, 0, new BigDecimal("99.95") } };
//	}
//}
