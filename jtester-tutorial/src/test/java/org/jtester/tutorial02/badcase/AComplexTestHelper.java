//package org.jtester.tutorial02.badcase;
//
//import org.jtester.tutorial.biz.model.Invoice;
//import org.jtester.tutorial.biz.model.Product;
//
//public class AComplexTestHelper {
//
//	public static Customer createACustomer(int percentDiscount) {
//		Address billingAddress = new Address("1222 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
//		Address shippingAddress = new Address("1333 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
//		Customer customer = new Customer(99, "John", "Doe", percentDiscount, billingAddress, shippingAddress);
//		return customer;
//	}
//
//	public static Product createAProduct(double unitPrice) {
//		Product product = new Product("SomeWidget", 19.99);
//		return product;
//	}
//
//	public static Invoice createInvoice(int percentDiscount) {
//		Customer cust = createACustomer(percentDiscount);
//		Invoice invoice = new Invoice(cust);
//		return invoice;
//	}
//}
