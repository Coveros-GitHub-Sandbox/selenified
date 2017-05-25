package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Element;
import tools.output.Selenium.Locators;

public class AssertIT extends TestBase {

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

	@Test(groups = { "integration", "asserts", "virtual" }, description = "An integration test to check a title")
	public void compareTitleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform our verification
		asserts.compareTitle("Selenified Test Page");
		// perform our verification
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check a title")
	public void negativeCompareTitleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTitle("Yahoo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareNumOfSelectOptions method")
	public void compareNumOfSelectOptionsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(Locators.NAME, "car_list", 4);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
	public void negativeCompareNumOfSelectOptionsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(new Element(Locators.NAME, "car_list"), 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
	public void negativeCompareNumOfSelectOptionsNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(new Element(Locators.NAME, "enable_button"), 0, 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareSelectOptions method")
	public void compareSelectOptionsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(Locators.NAME, "car_list", new String[] { "volvo", "saab", "mercedes", "audi" });
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "asserts", "virtual" },
			description = "An integration test to check the compareSelectValues method")
	public void negativeCompareSelectValuesNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(new Element(Locators.NAME, "non-existent-element"),
				new String[] { "volvo", "ford", "mercedes", "audi" });
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareSelectValues method")
	public void negativeCompareSelectValuesTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(new Element(Locators.NAME, "car_list"), 0,
				new String[] { "volvo", "ford", "mercedes", "audi" });
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareSelectValues method")
	public void negativeCompareSelectValuesExtraTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(Locators.NAME, "car_list",
				new String[] { "volvo", "saab", "mercedes", "audi", "chevrolet" });
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareSelectValues method")
	public void negativeCompareSelectValuesMissingTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(Locators.NAME, "car_list", new String[] { "volvo", "saab", "mercedes" });
		// verify no issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareNumOfTableRows method")
	public void compareNumOfTableRowsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(Locators.ID, "table", 7);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
	public void negativeCompareNumOfTableRowsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(new Element(Locators.ID, "table"), 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
	public void negativeCompareNumOfTableRowsNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(new Element(Locators.ID, "non-existent-element"), 0, 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareNumOfTableColumns method")
	public void compareNumOfTableColumnsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(Locators.ID, "table", 4);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
	public void negativeCompareNumOfTableColumnsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(new Element(Locators.ID, "table"), 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
	public void negativeCompareNumOfTableColumnsNotPresetTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(new Element(Locators.ID, "non-existent-element"), 0, 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkSelectValuePresent method")
	public void checkSelectValuePresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(Locators.ID, "car_list", "volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkSelectValuePresent method")
	public void negativeCheckSelectValuePresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(new Element(Locators.ID, "car_list"), "ford");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkSelectValuePresent method")
	public void negativeCheckSelectValuePresentNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(new Element(Locators.ID, "enable_button"), 0, "ford");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkSelectValueNotPresent method")
	public void checkSelectValueNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(Locators.ID, "car_list", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkSelectValueNotPresent method")
	public void negativeCheckSelectValueNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(new Element(Locators.ID, "car_list"), "volvo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkSelectValueNotPresent method")
	public void negativeCheckSelectValueNotPresentNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(new Element(Locators.ID, "enable_button"), 0, "volvo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkIfOptionInSelect method")
	public void checkIfOptionInSelectTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(Locators.ID, "car_list", "audi");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkIfOptionInSelect method")
	public void negativeCheckIfOptionInSelectTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(new Element(Locators.ID, "car_list"), "ford");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkIfOptionInSelect method")
	public void negativeCheckIfOptionInSelectNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(new Element(Locators.ID, "enable_button"), 0, "ford");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkIfOptionInSelect method")
	public void negativeCheckIfOptionInSelectDelayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "delayed_enable_button");
		asserts.checkIfOptionInSelect(Locators.ID, "delayed_input", "ford");
		// verify 1 issues
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkIfOptionNotInSelect method")
	public void checkIfOptionNotInSelectTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(Locators.ID, "car_list", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
	public void negativeCheckIfOptionNotInSelectTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(new Element(Locators.ID, "car_list"), "audi");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
	public void negativeCheckIfOptionNotInSelectNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(new Element(Locators.ID, "enable_button"), 0, "audi");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkIfOptionNotInSelect method")
	public void checkIfOptionNotInSelectDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "delayed_enable_button");
		asserts.checkIfOptionNotInSelect(Locators.ID, "delayed_input", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkAlertPresent method")
	public void checkAlertPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkAlertPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckAlertPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlertPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkAlertPresent method")
	public void checkConfirmationPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkConfirmationPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckConfirmationPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmationPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkAlertPresent method")
	public void checkAlertNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlertNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckNotAlertPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkAlertNotPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkAlert method")
	public void checkAlertTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkAlert("Enabled!");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkAlert method")
	public void checkAlertRegexTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkAlert("([A-Z])\\w+!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlert method")
	public void negativeCheckAlertNoAlertTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlert("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlert method")
	public void negativeCheckAlertTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkAlert("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkConfirmation method")
	public void checkConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkConfirmation("Enabled!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkConfirmation method")
	public void negativeCheckConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkConfirmation("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkConfirmation method")
	public void negativeCheckConfirmationNoConfirmationTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmation("Disabled!");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkConfirmationNotPresent method")
	public void checkConfirmationNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkConfirmationNotPresent method")
	public void negativeCheckConfirmationNotPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkConfirmationNotPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementChecked method")
	public void checkElementCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "this");
		asserts.checkElementChecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementChecked method")
	public void negativeCheckElementCheckedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementChecked(new Element(Locators.ID, "this"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementChecked method")
	public void negativeCheckElementCheckedNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementChecked(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementChecked method")
	public void negativeCheckElementCheckedDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementChecked(Locators.ID, "check");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementContainsClass method")
	public void checkElementContainsClassTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(Locators.ID, "hidden_div", "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
	public void negativeCheckElementContainsClassTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(new Element(Locators.ID, "hidden_div"), "wrong_class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
	public void negativeCheckElementContainsClassNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(new Element(Locators.ID, "non-existent-element"), 0, "wrong_class");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementContainsClass method")
	public void negativeCheckElementContainsClassDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(Locators.ID, "check", "wrong_class");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementDisplayed method")
	public void checkElementDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(Locators.ID, "enable_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayed method")
	public void negativeCheckElementDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(new Element(Locators.ID, "non_existent"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayed method")
	public void negativeCheckElementDisplayedHiddenTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(new Element(Locators.ID, "hidden_div"), 0);
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDisplayed method")
	public void checkElementDisplayedDelayed() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "delayed_display_button");
		asserts.checkElementDisplayed(Locators.ID, "delayed_hide_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementDisplayedAndChecked method")
	public void checkElementDisplayedAndCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "this");
		asserts.checkElementDisplayedAndChecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndChecked method")
	public void negativeCheckElementDisplayedAndCheckedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndChecked(new Element(Locators.ID, "this"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndChecked method")
	public void negativeCheckElementDisplayedAndCheckedNotDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndChecked(new Element(Locators.ID, "non-existent-element"), 0);
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDisplayedAndChecked method")
	public void negativeCheckElementDisplayedAndCheckedDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndChecked(Locators.ID, "check");
		// verify 1 issues
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementDisplayedAndEditable method")
	public void checkElementDisplayedAndEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "input_box"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndEditable method")
	public void negativeCheckElementDisplayedAndEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "non_existent"));
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDisplayedAndEditable method")
	public void checkElementDisplayedAndEditableDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "check"), 0);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementDisplayedAndNotEditable method")
	public void checkElementDisplayedAndNotEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
	public void negativeCheckElementDisplayedAndNotEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(new Element(Locators.ID, "input_box"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
	public void negativeCheckElementDisplayedAndNotEditableNotDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(new Element(Locators.ID, "non-existent-element"), 0);
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
	public void negativeCheckElementDisplayedAndNotEditableDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(Locators.ID, "check");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementDisplayedAndUnchecked method")
	public void checkElementDisplayedAndUncheckedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndUnchecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
	public void negativeCheckElementDisplayedAndUncheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "this");
		asserts.checkElementDisplayedAndUnchecked(new Element(Locators.ID, "this"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
	public void negativeCheckElementDisplayedAndUncheckedNotDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndUnchecked(new Element(Locators.ID, "non-existent-element"), 0);
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
	public void checkElementDisplayedAndUncheckedDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndUnchecked(Locators.ID, "check");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementDoesntContainClass method")
	public void checkElementDoesntContainClassTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(Locators.ID, "hidden_div", "wrong_class");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
	public void negativeCheckElementDoesntContainClassTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(new Element(Locators.ID, "hidden_div"), "hidden_div");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
	public void negativeCheckElementDoesntContainClassNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(new Element(Locators.ID, "non-existent-element"), 0, "hidden_div");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDoesntContainClass method")
	public void checkElementDoesntContainClassDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(Locators.ID, "check", "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkElementDoesntHaveAttribute method")
	public void checkElementDoesntHaveAttributeTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(Locators.ID, "car_list", "class");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
	public void negativeCheckElementDoesntHaveAttributeTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(new Element(Locators.ID, "car_list"), "name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
	public void negativeCheckElementDoesntHaveAttributeNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(new Element(Locators.ID, "non-existent-element"), 0, "name");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
	public void checkElementDoesntHaveAttributeDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(Locators.ID, "check", "name");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementEditable method")
	public void checkElementEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "input_box");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementEditable method")
	public void negativeCheckElementEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(new Element(Locators.ID, "non_existent"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementEditable method")
	public void negativeCheckElementEditableNotInputTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(new Element(Locators.ID, "table"), 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkElementEditable method")
	public void negativeCheckElementEditableNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "enable_button");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementEditable method")
	public void checkElementEditableDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "check");
		// verify 0 issue
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkElementHasAttribute method")
	public void checkElementHasAttributeTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasAttribute(Locators.ID, "car_list", "name");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkElementHasAttribute method")
	public void negativeCheckElementHasAttributeTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasAttribute(new Element(Locators.ID, "car_list"), "class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkElementHasAttribute method")
	public void negativeCheckElementHasAttributeNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasAttribute(new Element(Locators.ID, "non-existent-element"), 0, "class");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementHasAttribute method")
	public void negativeCheckElementHasAttributeDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasAttribute(Locators.ID, "check", "class");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementHasClass method")
	public void checkElementHasClassTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasClass(Locators.ID, "hidden_div", "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementHasClass method")
	public void negativeCheckElementHasClassTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasClass(new Element(Locators.ID, "hidden_div"), "wrong_class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementHasClass method")
	public void negativeCheckElementHasClassNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasClass(new Element(Locators.ID, "non-existent-element"), 0, "wrong_class");
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementHasClass method")
	public void negativeCheckElementHasClassDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasClass(Locators.ID, "check", "wrong_class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementNotChecked method")
	public void checkElementNotCheckedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotChecked(Locators.ID, "that");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementNotChecked method")
	public void negativeCheckElementNotCheckedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "that");
		asserts.checkElementNotChecked(new Element(Locators.ID, "that"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementNotChecked method")
	public void negativeCheckElementNotCheckedNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotChecked(new Element(Locators.ID, "non-existent-element"), 0);
		// verify 2 issue
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementNotChecked method")
	public void checkElementNotCheckedDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotChecked(Locators.ID, "check");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementNotDisplayed method")
	public void checkElementNotDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementNotDisplayed method")
	public void negativeCheckElementNotDisplayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(new Element(Locators.ID, "scroll_button"));
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" }, 
			description = "An integration negative test to check the checkElementNotDisplayed method")
	public void checkElementNotDisplayedDelayedTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "delayed_display_button");
		actions.waitForElementDisplayed(Locators.ID, "delayed_hide_button");
		actions.click(Locators.ID, "delayed_hide_button");
		asserts.checkElementNotDisplayed(new Element(Locators.ID, "delayed_hide_button"), 0);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkElementNotEditable method")
	public void checkElementNotEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotEditable(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementNotEditable method")
	public void negativeCheckElementNotEditableTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotEditable(new Element(Locators.ID, "that"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkElementNotEditable method")
	public void negativeCheckElementNotEditableNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotEditable(new Element(Locators.ID, "non-existent-element"), 0);
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "asserts" },
			description = "An integration negative test to check the checkElementNotEditable method")
	public void negativeCheckElementNotEditableDelayedTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotEditable(Locators.ID, "check");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkPrompt method")
	public void checkPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkPrompt("Enabled!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPrompt method")
	public void negativeCheckPromptNoPromptTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkPrompt("Enabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPrompt method")
	public void negativeCheckPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkPrompt("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkPromptNotPresent method")
	public void checkPromptPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkPromptPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
	public void negativeCheckPromptPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkPromptPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkPromptNotPresent method")
	public void checkPromptNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkPromptNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
	public void negativeCheckPromptNotPresentTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkPromptNotPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkTextNotVisible method")
	public void checkTextNotVisibleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextNotVisible("No such text on the page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkTextNotVisible method")
	public void negativeCheckTextNotVisibleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextNotVisible("Click me to Disable/Enable a html button");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkTextVisible method")
	public void checkTextVisibleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisible("Click me to Disable/Enable a html button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkTextVisible method")
	public void negativeCheckTextVisibleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisible("No such text on the page");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkTextVisibleOR method")
	public void checkTextVisibleORTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisibleOR("Click me to Disable/Enable a html button", "Not found on page", "This");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkTextVisibleOR method")
	public void negativeCheckTextVisibleORTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisibleOR("No such text on the page", "Not found", "None");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the compareCssValue method")
	public void compareCssValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareCssValue(Locators.ID, "hidden_div", "display", "none");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareCssValue method")
	public void negativeCompareCssValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareCssValue(new Element(Locators.ID, "hidden_div"), "display", "inline");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareCssValue method")
	public void negativeCompareCssValueNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareCssValue(new Element(Locators.ID, "non-existent-element"), 0, "display", "inline");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareCssValue method")
	public void compareInputValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareInputValue(Locators.ID, "that", "That");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareCssValue method")
	public void negativeCompareInputValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareInputValue(new Element(Locators.ID, "that"), "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareCssValue method")
	public void negativeCompareInputValueNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareInputValue(new Element(Locators.ID, "non-existent-element"), 0, "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareSelectedText method")
	public void compareSelectedTextTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedText(Locators.ID, "car_list", "Volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareSelectedText method")
	public void negativeCompareSelectedTextTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedText(new Element(Locators.ID, "car_list"), "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareSelectedText method")
	public void negativeCompareSelectedTextNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedText(new Element(Locators.ID, "enable_button"), 0, "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareSelectedValue method")
	public void compareSelectedValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValue(Locators.ID, "car_list", "volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareSelectedValue method")
	public void negativeCompareSelectedValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValue(new Element(Locators.ID, "car_list"), "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareSelectedValue method")
	public void negativeCompareSelectedValueNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValue(new Element(Locators.ID, "enabled_button"), 0, "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareSelectedValue method")
	public void negativeCompareSelectedValueNotInputTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValue(Locators.ID, "table", "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareSelectedValueNotEqual method")
	public void compareSelectedValueNotEqualTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValueNotEqual(Locators.ID, "car_list", "audi");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareSelectedValueNotEqual method")
	public void negativeCompareSelectedValueNotEqualTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValueNotEqual(new Element(Locators.ID, "car_list"), "volvo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the compareSelectedValueNotEqual method")
	public void negativeCompareSelectedValueNotEqualNotEnabledTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValueNotEqual(new Element(Locators.ID, "enabled_button"), 0, "volvo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareTextValue method")
	public void compareTextValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValue(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Centro comercial Moctezuma");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareTextValue method")
	public void negativeCompareTextValueTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValue(new Element(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]"), "Maria Anders");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareTextValue method")
	public void negativeCompareTextValueNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValue(new Element(Locators.ID, "non-existent-element"), 0, "Maria Anders");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the compareTextValueContains method")
	public void compareTextValueContainsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValueContains(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Centro comer");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareTextValueContains method")
	public void negativeCompareTextValueContainsTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValueContains(new Element(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]"), "Anders");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the compareTextValueContains method")
	public void negativeCompareTextValueContainsNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValueContains(new Element(Locators.ID, "non-existent-element"), 0, "Anders");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkCookie method")
	public void checkCookieTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookie("cookie", "cookietest");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookie method")
	public void negativeCheckCookieTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookie("cookie", "negativecookietest");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookie method")
	public void negativeCheckCookieWrongNameTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookie("wrongcookie", "cookietest");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkCookieNotPresent method")
	public void checkCookieNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookieNotPresent("wrongcookie");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookieNotPresent method")
	public void negativeCheckCookieNotPresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookieNotPresent("cookie");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkCookiePresent method")
	public void checkCookiePresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookiePresent("cookie");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookiePresent method")
	public void negativeCheckCookiePresentTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookiePresent("wrongcookie");
		// verify 1 issue
		finish(1);
	}
}