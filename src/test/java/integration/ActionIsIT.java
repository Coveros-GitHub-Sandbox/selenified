package integration;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Selenium.Locators;
import com.coveros.selenified.tools.TestBase;

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
		Assert.assertTrue(actions.isElementPresent(Locators.ID, "submit_button"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is present")
	public void isElementPresentNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementPresent(new Element(Locators.ID, "non-existent-name"), true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertTrue(actions.isElementInput(Locators.ID, "input_box"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertTrue(actions.isElementInput(Locators.ID, "car_list"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementTextAreaTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertTrue(actions.isElementInput(Locators.ID, "textarea_input"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementInput(new Element(Locators.ID, "disable_click"), true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is input")
	public void isElementInputNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementInput(new Element(Locators.ID, "non-existent-name")));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is enabled")
	public void isElementEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertTrue(actions.isElementEnabled(Locators.ID, "scroll_button"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is enabled")
	public void isElementEnabledNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementEnabled(new Element(Locators.ID, "non-existent-name"), true));
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
		Assert.assertTrue(actions.isElementChecked(Locators.ID, "that"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedNotTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementChecked(new Element(Locators.ID, "that")));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is checked")
	public void isElementCheckedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementChecked(new Element(Locators.ID, "non-existent-name"), true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is displayed")
	public void isElementDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertTrue( actions.isElementDisplayed(Locators.ID, "that") );
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if an element is displayed")
	public void isElementDisplayedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isElementDisplayed(new Element(Locators.ID, "non-existent-name"), true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingSelectedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertTrue( actions.isSomethingSelected(Locators.ID, "car_list"));
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
		Assert.assertTrue( actions.isSomethingSelected(Locators.ID, "car_list_multiple", true) );
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a dropdown")
	public void isSomethingNotSelectedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isSomethingSelected(new Element(Locators.ID, "car_list_multiple"), true));
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
		Assert.assertTrue(actions.isSomethingSelected(Locators.ID, "that"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a checkbox")
	public void isSomethingNotCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse( actions.isSomethingSelected(new Element(Locators.ID, "that"), true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from a non-existant element")
	public void isSomethingSelectedNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isSomethingSelected(new Element(Locators.ID, "non-existent-name")));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check if something is selected from an div")
	public void isSomethingSelectedNotSelectOrInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isSomethingSelected(new Element(Locators.ID, "disable_click"), true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration test to check the isAlertPresent method")
	public void isAlertPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "alert_button");
		Assert.assertTrue(actions.isAlertPresent());
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration negative test to check the isAlertPresent method")
	public void negativeIsAlertPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isAlertPresent(true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration test to check the isConfirmationPresent method")
	public void isConfirmationPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "confirm_button");
		Assert.assertTrue(actions.isConfirmationPresent());
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration negative test to check the isConfirmationPresent method")
	public void negativeIsConfirmationPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isConfirmationPresent(true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "An integration test to check the isPromptPresent method")
	public void isPromptPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "prompt_button");
		Assert.assertTrue(actions.isPromptPresent());
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions",
			"is" }, description = "A integration negative test to check the isPromptPresent method")
	public void negativeIsPromptPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		Assert.assertFalse(actions.isPromptPresent(true));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration test to check the isTextPresentInSource method")
	public void isTextPresentInSourceTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(new Element(Locators.ID, "submit_button"), 0);
		Assert.assertTrue(actions.isTextPresentInSource("You're on the next page"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "is",
			"virtual" }, description = "An integration negative test to check the isTextPresentInSource method")
	public void negativeIsTextPresentInSourceTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(new Element(Locators.ID, "submit_button"), 0);
		Assert.assertFalse(actions.isTextPresentInSource("Hello World"));
		// verify no issues
		finish();
	}
}