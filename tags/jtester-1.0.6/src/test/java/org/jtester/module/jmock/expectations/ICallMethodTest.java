package org.jtester.module.jmock.expectations;

import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.module.utils.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@Test(groups = "jtester")
public class ICallMethodTest extends JTester {
	@Mock
	private SomeInterface someInterface;

	public void oneOf() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.oneOf(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
	}

	public void exactly() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.exactly(2).of(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
		this.someInterface.call();
	}

	public void allowing() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.allowing(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
		this.someInterface.call();
	}

	public void ignoring() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.ignoring(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
		this.someInterface.call();
	}

	@Test(expectedExceptions = Throwable.class)
	public void never() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.never(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
	}

	public void atLeast() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.atLeast(2).of(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
		this.someInterface.call();
		this.someInterface.call();
	}

	public void between() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.between(2, 3).of(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
		this.someInterface.call();
		this.someInterface.call();
	}

	public void atMost() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.atMost(2).of(someInterface).call();
				will.returns.value("call one");
			}
		});
		this.someInterface.call();
		this.someInterface.call();
		// this.someInterface.call();
	}

	public static class SomeInterface {
		public String call() {
			return "test";
		}
	}
}
