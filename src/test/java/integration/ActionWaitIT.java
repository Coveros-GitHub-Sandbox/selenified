package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Selenium.Locators;

public class ActionWaitIT extends TestBase {

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
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the goToURL method")
	public void negativeWaitTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.wait(6);
		actions.click(Locators.ID, "five_second_button");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the waitForElementPresent method")
	public void waitForElementPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the waitForElementPresent method")
	public void negativeWaitForElementPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(Locators.NAME, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
	public void waitForElementNotPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotPresent(Locators.NAME, "non-existent-name");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the waitForElementNotPresent method")
	public void negativeWaitForElementNotPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotPresent(Locators.NAME, "car_list");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the waitForElementDisplayed method")
	public void waitForElementDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementDisplayed(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the waitForElementDisplayed method")
	public void waitForElementDisplayedDelayedPresenceTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "delayed_display_button");
		actions.waitForElementDisplayed(Locators.NAME, "added_div");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the waitForElementDisplayed method")
	public void waitForElementDisplayedDelayedDisplayTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "delayed_display_button");
		actions.waitForElementDisplayed(Locators.NAME, "delayed_hide_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the waitForElementDisplayed method")
	public void negativeWaitForElementDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementDisplayed(Locators.NAME, "non-existent-name");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the waitForElementDisplayed method")
	public void negativeWaitForElementDisplayedHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementDisplayed(Locators.NAME, "hidden_div");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the WaitForElementNotDisplayed method")
	public void waitForElementNotDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotDisplayed(Locators.NAME, "hidden_div");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" },
			description = "An integration test to check the WaitForElementNotDisplayed method")
	public void waitForElementNotDisplayedDelayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "delayed_display_button");
		actions.waitForElementDisplayed(Locators.NAME, "delayed_hide_button");
		actions.click(Locators.NAME, "delayed_hide_button");
		actions.waitForElementNotDisplayed(Locators.NAME, "delayed_hide_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the WaitForElementNotDisplayed method")
	public void negativeWaitForElementNotDisplayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		actions.waitForElementNotDisplayed(Locators.NAME, "car_list");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
	public void waitForElementEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementEnabled(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the WaitForElementEnabled method")
	public void waitForElementEnabledDelayedPresenceTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "delayed_display_button");
		actions.waitForElementEnabled(Locators.NAME, "added_div");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
	public void waitForElementEnabledDelayedEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "delayed_enable_button");
		actions.waitForElementEnabled(Locators.NAME, "delayed_input");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the WaitForElementEnabled method")
	public void negativeWaitForElementEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementEnabled(Locators.NAME, "enable_button");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the WaitForElementNotEnabled method")
	public void waitForElementNotEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotEnabled(Locators.NAME, "enable_button");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the WaitForElementNotEnabled method")
	public void waitForElementNotEnabledDelayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "delayed_enable_button");
		actions.waitForElementEnabled(Locators.NAME, "delayed_input");
		actions.click(Locators.NAME, "delayed_enable_button");
		actions.waitForElementNotEnabled(Locators.NAME, "delayed_input");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the WaitForElementNotEnabled method")
	public void negativeWaitForNotElementEnabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotEnabled(Locators.NAME, "car_list");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the waitForPromptPresent method")
	public void waitForPromptPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.waitForPromptPresent();
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "A integration negative test to check the waitForPromptPresent method")
	public void negativeWaitForPromptPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForPromptPresent();
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the waitForConfirmationPresent method")
	public void waitForConfirmationPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.waitForConfirmationPresent();
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the waitForConfirmationPresent method")
	public void negativeWaitForConfirmationPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForConfirmationPresent();
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the waitForAlertPresent method")
	public void waitForAlertPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.waitForAlertPresent();
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the waitForAlertPresent method")
	public void negativeWaitForAlertPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForAlertPresent();
		// verify 1 issue
		finish(1);
	}
}