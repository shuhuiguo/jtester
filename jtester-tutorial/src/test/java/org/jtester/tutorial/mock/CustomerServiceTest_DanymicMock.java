package org.jtester.tutorial.mock;

import java.util.Arrays;
import java.util.List;

import mockit.Mock;
import mockit.Mocked;
import mockit.NonStrict;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.model.Customer;
import org.jtester.tutorial.biz.model.Invoice;
import org.jtester.tutorial.biz.model.LineItem;
import org.jtester.tutorial.biz.model.Product;
import org.jtester.tutorial.biz.service.CustomerService;
import org.jtester.tutorial.biz.service.CustomerServiceImpl;
import org.jtester.tutorial.biz.service.InvoiceDao;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
@SuppressWarnings("unused")
public class CustomerServiceTest_DanymicMock extends JTester {
	@SpringBeanByName
	CustomerService customerService;

	@Test(description = "演示如何动态的mock一个有实现类的方法")
	public void testUseExpectaions() {
		final String name = "darui.wu";
		new Expectations() {
			@Mocked(methods = "findCustomerByName")
			CustomerServiceImpl customerServiceImpl;
			{
				customerServiceImpl.findCustomerByName(the.string().isEqualTo(name).wanted());
				result = new Customer(name, null);
			}
		};

		Customer cust = this.customerService.findCustomerByName(name);
		want.object(cust).notNull().propertyEq("name", name);
	}

	@SpringBeanFrom
	@NonStrict
	InvoiceDao invoiceDao;

	@Test(description = "演示如何动态mock一个纯接口类的方法")
	public void testStaticMockInterface() {
		final String name = "darui.wu";

		final Invoice invoice = new Invoice(new Customer(name, null));
		invoice.addItemQuantity(new Product("apple ipad", 3300.00d), 100);
		invoice.addItemQuantity(new Product("apple iphone4", 5000.00d), 100);
		new Expectations() {
			{
				invoiceDao.getInvoiceByCustomerName(the.string().isEqualTo(name).wanted());
				result = Arrays.asList(invoice);
			}
		};

		List<Invoice> invoices = this.customerService.getInvoiceByCustomerName(name);
		want.collection(invoices).sizeEq(1).propertyEq("customer.name", name);
		List<LineItem> lines = invoices.get(0).getLineItems();
		want.collection(lines).propertyEq("product.name", new String[] { "apple ipad", "apple iphone4" });
	}
}
