package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Selenium.Locators;

public class ActionDoIT extends TestBase {

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
	
	@DataProvider(name = "car list items", parallel = true)
	public Object[][] DataSetOptions() {
		return new Object[][] { new Object[] { "volvo" }, new Object[] { "saab" }, new Object[] { "mercedes" } };
	}
	
	@Test(dataProvider = "car list items", groups = { "integration", "virtual" },
			description = "An integration test using a data provider to perform searches")
	public void selectTest(String listItem) throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "car_list", listItem);
		asserts.compareSelectedValue(Locators.ID, "car_list", listItem);
		// close out the test
		finish();
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the goToURL method")
	public void goToURLTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.goToURL("https://www.google.com/");
		asserts.compareURL("https://www.google.com/");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration negative test to check the goToURL method")
	public void negativeGoToURLTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.goToURL("https://www.yahoo.com/");
		asserts.compareURL("https://www.google.com/");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration negative test to check the goToURL method")
	public void negativeInvalidGoToURLTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.goToURL("https:///");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the acceptAlert method")
	public void acceptAlertTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.acceptAlert();
		asserts.checkAlertNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the acceptAlert method")
	public void negativeAcceptAlertTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptAlert();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the acceptPrompt method")
	public void acceptPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.acceptPrompt();
		asserts.checkPromptNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the acceptPrompt method")
	public void negativeAcceptPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptPrompt();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the acceptConfirmation method")
	public void acceptConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.acceptConfirmation();
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the acceptConfirmation method")
	public void negativeAcceptConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptConfirmation();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the clear method")
	public void clearTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "input_box", "Text");
		asserts.compareInputValue(Locators.ID, "input_box", "Text");
		actions.clear(Locators.ID, "input_box");
		asserts.compareInputValue(Locators.ID, "input_box", "");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the click method")
	public void clickTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		asserts.checkElementEditable(Locators.ID, "enable_button");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the click method")
	public void negativeClickDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "enable_button");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration negative test to check the click method")
	public void negativeClickTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration negative test to check the click method")
	public void negativeClickHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the dismissConfirmation method")
	public void dismissConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.dismissConfirmation();
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the dismissConfirmation method")
	public void negativeDismissConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.dismissConfirmation();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the dismissPrompt method")
	public void dismissPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.dismissPrompt();
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the dismissPrompt method")
	public void negativeDismissPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.dismissPrompt();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the type method")
	public void typeTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "input_box", "This is a test");
		asserts.compareInputValue(Locators.ID, "input_box", "This is a test");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the waitForAlertPresent method")
	public void submitTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.submit(Locators.ID, "submit_button");
		asserts.checkTextVisible("You're on the next page");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the scroll method")
	public void scrollTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "scroll_button");
		actions.scroll(50);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the scroll method")
	public void negativeScrollTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.scroll(50);
		// verify 1 issue
		finish(1);
	}

}