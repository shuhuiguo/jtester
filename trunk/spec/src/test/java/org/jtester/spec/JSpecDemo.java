package org.jtester.spec;

import org.jbehave.core.annotations.Named;
import org.jtester.spec.annotations.Given;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.annotations.When;

public class JSpecDemo extends JSpec {

	@Override
	protected JSpec getJTesterSpec() {
		return new JSpecDemo();
	}

	private String greeting = "";

	private String guest = "";

	private String housemaster = "";

	private String date = "";

	@Given
	public void giveGreeting(@Named("guest") String guest, @Named("greeting") String greeting,
			@Named("housemaster") String housemaster, @Named("date") String date) {
		this.greeting = greeting;
		this.guest = guest;
		this.housemaster = housemaster;
		this.date = date;
	}

	String wholeword = "";

	@When
	public void doGreeting() {
		this.wholeword = String.format("%s %s %s on %s.", this.housemaster, this.greeting, this.guest, this.date);
	}

	@Then
	public void checkGreeting(String expected) {
		want.string(this.wholeword).isEqualTo(expected);
	}
}
