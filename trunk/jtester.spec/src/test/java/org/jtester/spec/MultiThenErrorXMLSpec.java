package org.jtester.spec;

import org.jtester.spec.annotations.Given;
import org.jtester.spec.annotations.Named;
import org.jtester.spec.annotations.StoryFile;
import org.jtester.spec.annotations.StorySource;
import org.jtester.spec.annotations.StoryType;
import org.jtester.spec.annotations.Then;
import org.jtester.spec.annotations.When;
import org.jtester.spec.exceptions.ScenarioAssertError;
import org.jtester.spec.scenario.JSpecScenario;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
@StoryFile(value = "MultiThenErrorSpec.xml", type = StoryType.XML, source = StorySource.ClassPath)
public class MultiThenErrorXMLSpec extends JSpec {
	Throwable specError = null;

	@Test(dataProvider = "story", groups = "jspec")
	public void runStory(JSpecScenario scenario) throws Throwable {
		this.errorType = ErrorType.Normal;
		specError = null;
		try {
			this.run(scenario);
		} catch (Throwable e) {
			specError = e;
		}
	}

	@AfterMethod
	public void checkScenarioAssertError() {
		switch (this.errorType) {
		case ScenarioError:
			want.object(this.specError).clazIs(ScenarioAssertError.class);
			break;
		case Normal:
			want.object(specError).isNull();
			break;
		case Exception:
			Class claz = this.specError.getClass();
			assert claz != ScenarioAssertError.class;
			break;
		default:
			throw new RuntimeException("check error");
		}
	}

	@Given
	public void initNormal() throws Exception {
	}

	@When
	public void executeNormal() throws Exception {
	}

	@Then
	public void checkError(final @Named("错误消息") String error// <br>
	) throws Exception {
		throw new AssertionError(error);
	}

	@Then
	public void checkNormal() throws Exception {
	}

	private ErrorType errorType = ErrorType.Normal;

	@Then
	public void checkScenarioError() throws Exception {
		errorType = ErrorType.ScenarioError;
	}

	@Then
	public void checkScenarioNormal() throws Exception {
		errorType = ErrorType.Normal;
	}

	@Given
	public void checkScenarioException() throws Exception {
		errorType = ErrorType.Exception;
	}

	@Given
	public void initError() throws Exception {
		throw new RuntimeException("normal exception");
	}
}