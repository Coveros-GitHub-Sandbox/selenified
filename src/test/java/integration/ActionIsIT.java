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
		boolean present = actions.isElementPresent(Locators.ID, "submit_button");
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
		boolean input = actions.isElementInput(Locators.ID, "input_box");
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
		boolean input = actions.isElementInput(Locators.ID, "car_list");
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
		boolean input = actions.isElementInput(Locators.ID, "textarea_input");
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
		boolean input = actions.isElementInput(new Element(Locators.ID, "non-existent-name"));
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
		boolean enabled = actions.isElementEnabled(Locators.ID, "scroll_button");
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
		boolean checked = actions.isElementChecked(Locators.ID, "that");
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
		boolean checked = actions.isElementChecked(new Element(Locators.ID, "that"));
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
		boolean displayed = actions.isElementDisplayed(Locators.ID, "that");
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
		boolean selected = actions.isSomethingSelected(Locators.ID, "car_list");
		Assert.assertEquals(selected, true);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "actions", "is",
	"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingSelectedMultipleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.ID, "car_list_multiple"), 1);
		boolean selected = actions.isSomethingSelected(Locators.ID, "car_list_multiple", true);
		Assert.assertEquals(selected, true);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "actions", "is",
	"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingNotSelectedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "car_list_multiple"), true);
		Assert.assertEquals(selected, false);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "actions", "is",
	"virtual" }, description = "An integration test to check if something is selected from a checkbox")
	public void isSomethingCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(new Element(Locators.ID, "that"));
		boolean selected = actions.isSomethingSelected(Locators.ID, "that");
		Assert.assertEquals(selected, true);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a checkbox")
	public void isSomethingNotCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "that"), true);
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
	public void isSomethingSelectedNotSelectOrInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		boolean selected = actions.isSomethingSelected(new Element(Locators.ID, "disable_click"), true);
		Assert.assertEquals(selected, false);
		// verify no issues
		finish();
	}
}