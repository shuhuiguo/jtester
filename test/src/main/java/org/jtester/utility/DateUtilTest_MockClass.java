package org.jtester.utility;

import java.util.Calendar;
import java.util.Date;

import javax.security.auth.login.LoginContext;

import mockit.Mock;
import mockit.MockClass;
import mockit.UsingMocksAndStubs;

import org.testng.annotations.Test;

import org.jtester.testng.JTester;
import org.jtester.utility.DateUtilTest_MockClass.MockDateUtil;

@Test(groups = "jtester")
@UsingMocksAndStubs( { MockDateUtil.class })
public class DateUtilTest_MockClass extends JTester {
	@MockClass(realClass = DateUtil.class)
	public static class MockDateUtil {
		@Mock
		public static Date now() {
			Calendar cal = DateUtilTest.mockCalendar(2012, 1, 28);
			return cal.getTime();
		}
	}

	@Test
	public void testCurrDateTimeStr_format() {
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}

	@MockClass(realClass = LoginContext.class, stubs = { "(String)", "logout" })
	final class MockLoginContextWithStubs {
		@Mock
		void login() {
		} // this could also have been an stub
	}
}
