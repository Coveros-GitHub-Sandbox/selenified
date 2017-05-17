package samples;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Element;
import tools.output.LocatorAssert;
import tools.output.Selenium.Locators;

public class SampleActionIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		// set the base URL for the tests here
		testSite = "http://172.31.2.65/";
		// set the author of the tests here
		author = "Max Saperstone\n<br/>max.saperstone@coveros.com";
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		version = "0.0.1";
	}

	@Test(groups = { "sample" }, description = "A sample test to check the acceptAlert method")
	public void sampleTestAcceptAlert() throws Exception {
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

	@Test(groups = { "sample" },
			description = "A sample negative test to check the acceptAlert method")
	public void sampleTestNegativeAcceptAlert() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptAlert();
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "sample" }, description = "A sample test to check the acceptPrompt method")
	public void sampleTestAcceptPrompt() throws Exception {
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

	@Test(groups = { "sample" },
			description = "A sample negative test to check the acceptPrompt method")
	public void sampleTestNegativeAcceptPrompt() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptPrompt();
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "sample" }, description = "A sample test to check the acceptConfirmation method")
	public void sampleTestAcceptConfirmation() throws Exception {
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

	@Test(groups = { "sample" },
			description = "A sample negative test to check the acceptConfirmation method")
	public void sampleTestNegativeAcceptConfirmation() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptConfirmation();
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the clear method")
	public void sampleTestClear() throws Exception {
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
	
	@Test(groups = { "sample" }, description = "A sample negative test to check the click method")
	public void sampleTestClick() throws Exception {
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
	
	@Test(groups = { "sample" }, description = "A sample test to check the dismissConfirmation method")
	public void sampleTestDismissConfirmation() throws Exception {
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

	@Test(groups = { "sample" },
			description = "A sample negative test to check the dismissConfirmation method")
	public void sampleTestNegativeDismissConfirmation() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.dismissConfirmation();
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "sample" }, description = "A sample test to check the dismissPrompt method")
	public void sampleTestDismissPrompt() throws Exception {
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

	@Test(groups = { "sample" },
			description = "A sample negative test to check the dismissPrompt method")
	public void sampleTestNegativeDismissPrompt() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.dismissPrompt();
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the waitForElementPresent method")
	public void sampleTestWaitForElementPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample", "virtual" },
			description = "A sample negative test to check the waitForElementPresent method")
	public void sampleTestNegativeWaitForElementPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(Locators.NAME, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the waitForElementNotPresent method")
	public void sampleTestWaitForElementNotPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotPresent(Locators.NAME, "non-existent-name");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample", "virtual" },
			description = "A sample negative test to check the waitForElementNotPresent method")
	public void sampleTestNegativeWaitForElementNotPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotPresent(Locators.NAME, "car_list");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the waitForElementDisplayed method")
	public void sampleTestWaitForElementDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementDisplayed(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample", "virtual" },
			description = "A sample negative test to check the waitForElementDisplayed method")
	public void sampleTestNegativeWaitForElementDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementDisplayed(Locators.NAME, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "sample", "virtual" },
			description = "A sample test to check the WaitForElementNotDisplayed method")
	public void sampleTestWaitForElementNotDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotDisplayed(Locators.NAME, "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample", "virtual" },
			description = "A sample negative test to check the WaitForElementNotDisplayed method")
	public void sampleTestNegativeWaitForElementNotDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		actions.waitForElementNotDisplayed(Locators.NAME, "car_list");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the WaitForElementEnabled method")
	public void sampleTestWaitForElementEnabled() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementEnabled(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample" }, description = "A sample negative test to check the WaitForElementEnabled method")
	public void sampleTestNegativeWaitForElementEnabled() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementEnabled(Locators.NAME, "enable_button");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "sample" }, description = "A sample test to check the WaitForElementNotEnabled method")
	public void sampleTestWaitForElementNotEnabled() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotEnabled(Locators.NAME, "enable_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample", "virtual" },
			description = "A sample negative test to check the WaitForElementNotEnabled method")
	public void sampleTestNegativeWaitForNotElementEnabled() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotEnabled(Locators.NAME, "car_list");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "sample" }, description = "A sample test to check the scroll method")
	public void sampleScrollTest() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "scroll_button");
		actions.scroll(50);
		// verify no issues
		finish();
	}

	@Test(groups = { "sample" }, description = "A sample negative test to check the scroll method")
	public void sampleNegativeScrollTest() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.scroll(50);
		// verify 1 issue
		finish(1);
	}

}