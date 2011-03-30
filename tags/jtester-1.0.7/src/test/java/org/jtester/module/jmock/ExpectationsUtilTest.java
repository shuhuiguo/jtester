package org.jtester.module.jmock;

//import static org.jtester.core.Je.checking;
//import org.jtester.core.Je;
import org.jtester.annotations.Inject;
import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.fortest.service.CalledService;
import org.jtester.fortest.service.CallingService;
import org.jtester.module.utils.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@Test(groups = { "JTester" })
public class ExpectationsUtilTest extends JTester {
	@Mock
	@Inject(targets = "callingService")
	private CalledService calledService;

	private CallingService callingService = new CallingService();

	@Test
	public void register_SinglThread() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(calledService).called(the.string().contains("test").wanted());
				will.returns.value("dddd");
				will.call.ignoring(calledService).called(the.string().any().wanted());
				will.returns.value("dddd");
			}
		});
		callingService.call("i am a test message!");
	}

	@Test
	public void expectedBoolean() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(calledService).expectedBoolean(the.bool().isEqualTo(true).wanted());
			}
		});
		callingService.expectedBoolean(true);
	}
}
