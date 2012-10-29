package org.jtester.spec.scenario.step;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jtester.spec.annotations.Given;
import org.jtester.spec.annotations.Named;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.annotations.When;
import org.jtester.spec.scenario.step.SpecMethod;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "unchecked" })
public class SpecMethodTest extends JTester {

	@Test(groups = "spec")
	public void testFindMethods_Normal() {
		Map<SpecMethodID, SpecMethod> map = SpecMethod.findMethods(StepMethodDemo.class);
		want.map(map).sizeEq(5).hasKeys(new SpecMethodID(StepType.Given, "given1", 0), // <br>
				new SpecMethodID(StepType.Given, "given2", 2),// <br>
				new SpecMethodID(StepType.Given, "given3", 3),// <br>
				new SpecMethodID(StepType.When, "when", 2),// <br>
				new SpecMethodID(StepType.Then, "then", 2)// <br>
				);
	}

	@Test(groups = "spec")
	public void testFindMethods_HasSameMethodID() {
		try {
			SpecMethod.findMethods(SameMethodID.class);
			want.fail();
		} catch (RuntimeException e) {
			String msg = e.getMessage();
			want.string(msg).contains("named given2 and with 2 arguments");
		}
	}

	@Test(groups = "spec")
	public void testGetParaAnnotationNames() throws Exception {
		SpecMethod specMethod = reflector.newInstance(SpecMethod.class);
		List<String> names = specMethod.getParaAnnotationNames(SameMethodID.class.getMethod("given2", String.class,
				int.class));
		want.list(names).isEqualTo(Arrays.asList("arg1", "arg2"));
	}

	@Test(groups = "spec")
	public void testGetParaTypes() throws Exception {
		SpecMethod specMethod = reflector.newInstance(SpecMethod.class);
		List<Type> types = specMethod.getParaTypes(SameMethodID.class.getMethod("given2", String.class, int.class));
		want.list(types).isEqualTo(Arrays.asList(String.class, int.class));
	}
}

class SameMethodID {
	@Given
	public void given2(@Named("arg1") String arg1, @Named("arg2") Boolean arg2) {

	}

	@Given
	public void given2(@Named("arg1") String arg1, @Named("arg2") int arg2) {

	}
}

class StepMethodDemo {
	@Given
	public void given1() {

	}

	@Given
	public void given2(@Named("arg1") String arg1, @Named("arg2") Boolean arg2) {

	}

	@Given
	public void given3(@Named("arg1") String arg1, @Named("arg2") int arg2, @Named("arg3") Object[] arg3) {

	}

	@When
	public void when(@Named("arg1") String arg1, @Named("arg2") Boolean arg2) {

	}

	@Then
	public void then(@Named("arg1") String arg1, @Named("arg2") Boolean arg2) {

	}
}
