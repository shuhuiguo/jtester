package org.jtester.core.context;

import org.jtester.core.ClazzForReflectTest;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class JTesterReflectorTest extends JTester {

	public void setField_私有静态字段() {
		reflector.setStaticField(ClazzForReflectTest.class, "myStatic", "zzzzz");
		String ret = ClazzForReflectTest.getMyStatic();
		want.string(ret).isEqualTo("zzzzz");
	}

	public void getField_私有静态字段() {
		ClazzForReflectTest.setMyStatic("bbbb");
		String ret = (String) reflector.getStaticField(ClazzForReflectTest.class, "myStatic");

		want.string(ret).isEqualTo("bbbb");
	}

	public void setField_私有字段() {
		ClazzForReflectTest tested = new ClazzForReflectTest();
		reflector.setField(tested, "myPrivate", "zzzzz");
		String ret = tested.getMyPrivate();
		want.string(ret).isEqualTo("zzzzz");
	}

	public void getField_私有字段() {
		ClazzForReflectTest tested = new ClazzForReflectTest();
		tested.setMyPrivate("bbbb");
		String ret = (String) reflector.getField(tested, "myPrivate");

		want.string(ret).isEqualTo("bbbb");
	}

	public void invoke_私有方法() {
		ClazzForReflectTest tested = new ClazzForReflectTest();
		String ret = (String) reflector.invoke(tested, "privateMethod");
		want.string(ret).isEqualTo("private method");
	}

	public void invoke_私有静态方法() {
		String ret = (String) reflector.invokeStatic(ClazzForReflectTest.class, "myStaticMethod");
		want.string(ret).isEqualTo("static method");
	}
}
