package integration;

import java.io.IOException;

import org.testng.Assert;
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

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is present")
	public void isElementPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean present = actions.isElementPresent(new Element(Locators.ID, "submit_button"), true);
		Assert.assertEquals(present, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is present")
	public void isElementPresentNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean present = actions.isElementPresent(new Element(Locators.ID, "non-existent-name"), true);
		Assert.assertEquals(present, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean input = actions.isElementInput(new Element(Locators.ID, "input_box"), true);
		Assert.assertEquals(input, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean input = actions.isElementInput(new Element(Locators.ID, "car_list"), true);
		Assert.assertEquals(input, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementTextAreaTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean input = actions.isElementInput(new Element(Locators.ID, "textarea_input"), true);
		Assert.assertEquals(input, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean input = actions.isElementInput(new Element(Locators.ID, "disable_click"), true);
		Assert.assertEquals(input, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementInputNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean input = actions.isElementInput(new Element(Locators.ID, "non-existent-name"), true);
		Assert.assertEquals(input, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is enabled")
	public void isElementEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean enabled = actions.isElementEnabled(new Element(Locators.ID, "scroll_button"), true);
		Assert.assertEquals(enabled, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is enabled")
	public void isElementEnabledNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean enabled = actions.isElementEnabled(new Element(Locators.ID, "non-existent-name"), true);
		Assert.assertEquals(enabled, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "that");
		boolean checked = actions.isElementChecked(new Element(Locators.ID, "that"), true);
		Assert.assertEquals(checked, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedNotTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean checked = actions.isElementChecked(new Element(Locators.ID, "that"), true);
		Assert.assertEquals(checked, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean checked = actions.isElementChecked(new Element(Locators.ID, "non-existent-name"), true);
		Assert.assertEquals(checked, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is displayed")
	public void isElementDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean displayed = actions.isElementDisplayed(new Element(Locators.ID, "that"), true);
		Assert.assertEquals(displayed, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is displayed")
	public void isElementDisplayedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean displayed = actions.isElementDisplayed(new Element(Locators.ID, "non-existent-name"), true);
		Assert.assertEquals(displayed, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingSelectedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "car_list"));
		Assert.assertEquals(selected, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a checkbox")
	public void isSomethingCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "that"));
		Assert.assertEquals(selected, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a non-existant element")
	public void isSomethingSelectedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "non-existent-name"));
		Assert.assertEquals(selected, false);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from an div")
	public void isSomethingSelectedNotSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "disable_click"));
		Assert.assertEquals(selected, false);
		// verify no issues
		finish();
	}
}