package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Element;
import tools.output.Selenium.Locators;

public class ActionIsIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		// set the base URL for the tests here
		setTestSite("http://172.31.2.65/");
		// set the author of the tests here
		setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		setVersion("0.0.1");
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is present")
	public void isElementPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementPresent(new Element(Locators.ID, "submit_button"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is present")
	public void isElementPresentNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementPresent(new Element(Locators.ID, "non-existent-name"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is input")
	public void isElementInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementInput(new Element(Locators.ID, "input_box"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is input")
	public void isElementSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementInput(new Element(Locators.ID, "car_list"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is input")
	public void isElementTextAreaTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementInput(new Element(Locators.ID, "textarea_input"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is input")
	public void isElementNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementInput(new Element(Locators.ID, "disable_click"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is input")
	public void isElementInputNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementInput(new Element(Locators.ID, "non-existent-name"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is enabled")
	public void isElementEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementEnabled(new Element(Locators.ID, "scroll_button"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is enabled")
	public void isElementEnabledNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementEnabled(new Element(Locators.ID, "non-existent-name"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementChecked(new Element(Locators.ID, "that"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementChecked(new Element(Locators.ID, "non-existent-name"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check if an element is displayed")
	public void isElementDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementDisplayed(new Element(Locators.ID, "that"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check if an element is displayed")
	public void isElementDisplayedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isElementDisplayed(new Element(Locators.ID, "non-existent-name"), true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingSelectedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isSomethingSelected(new Element(Locators.ID, "car_list"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isSomethingSelected(new Element(Locators.ID, "that"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingSelectedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isSomethingSelected(new Element(Locators.ID, "non-existent-name"));
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration",
	"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingSelectedNotSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.isSomethingSelected(new Element(Locators.ID, "disable_click"));
		// verify no issues
		finish();
	}
}