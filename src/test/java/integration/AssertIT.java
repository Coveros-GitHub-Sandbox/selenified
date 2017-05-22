package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Element;
import tools.output.Selenium.Locators;

public class AssertIT extends TestBase {

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

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check a title")
	public void compareTitleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform our verification
		asserts.compareTitle("Selenified Test Page");
		// perform our verification
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration negative test to check a title")
	public void negativeCompareTitleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTitle("Yahoo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the getNumOfSelectOptions method")
	public void getNumOfSelectOptionsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(Locators.NAME, "car_list", 4);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the getNumOfSelectOptions method")
	public void negativeGetNumOfSelectOptionsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(Locators.NAME, "car_list", 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getSelectOptions method")
	public void getSelectOptionsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(Locators.NAME, "car_list", new String[] { "volvo", "saab", "mercedes", "audi" });
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getNumOfTableRows method")
	public void getNumOfTableRowsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(Locators.ID, "table", 7);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the getNumOfTableRows method")
	public void negativeGetNumOfTableRowsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(Locators.ID, "table", 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the getNumOfTableColumns method")
	public void getNumOfTableColumnsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(Locators.ID, "table", 4);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the getNumOfTableColumns method")
	public void negativeGetNumOfTableColumnsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(Locators.ID, "table", 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableRowWHeader method")
	public void getTableRowWHeaderTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareRowHeader(Locators.ID, "table", "CEO", 3);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the getTableRowWHeader method")
	public void negativeGetTableRowWHeaderTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareRowHeader(Locators.ID, "table", "CEO", 0);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkSelectValuePresent method")
	public void checkSelectValuePresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(Locators.ID, "car_list", "volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkSelectValuePresent method")
	public void negativeCheckSelectValuePresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(Locators.ID, "car_list", "ford");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkSelectValueNotPresent method")
	public void checkSelectValueNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(Locators.ID, "car_list", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkSelectValueNotPresent method")
	public void negativeCheckSelectValueNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(Locators.ID, "car_list", "volvo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkIfOptionInSelect method")
	public void checkIfOptionInSelectTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(Locators.ID, "car_list", "audi");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkIfOptionInSelect method")
	public void negativeCheckIfOptionInSelectTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(Locators.ID, "car_list", "ford");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkIfOptionNotInSelect method")
	public void checkIfOptionNotInSelectTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(Locators.ID, "car_list", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkIfOptionNotInSelect method")
	public void negativeCheckIfOptionNotInSelectTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(Locators.ID, "car_list", "audi");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the checkAlertPresent method")
	public void checkAlertPresentTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckAlertPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlertPresent();
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the checkAlertPresent method")
	public void checkConfirmationPresentTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckConfirmationPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmationPresent();
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the checkAlertPresent method")
	public void checkAlertNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlertNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckNotAlertPresentTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration test to check the checkAlert method")
	public void checkAlertTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkAlert method")
	public void negativeCheckAlertNoAlertTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlert("Disabled!");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the checkAlert method")
	public void negativeCheckAlertTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration test to check the checkConfirmation method")
	public void checkConfirmationTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkConfirmation method")
	public void negativeCheckConfirmationTest() throws Exception {
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
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the checkConfirmation method")
	public void negativeCheckConfirmationNoConfirmationTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmation("Disabled!");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the checkConfirmationNotPresent method")
	public void checkConfirmationNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" },
			description = "An integration negative test to check the checkConfirmationNotPresent method")
	public void negativeCheckConfirmationNotPresentTest() throws Exception {
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

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the checkElementChecked method")
	public void checkElementCheckedTest() throws Exception {
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

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementChecked method")
	public void negativeCheckElementCheckedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementChecked(Locators.ID, "this");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementChecked method")
	public void negativeCheckElementCheckedNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementChecked(Locators.ID, "non-existent-element");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementContainsClass method")
	public void checkElementContainsClassTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(Locators.ID, "hidden_div", "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementContainsClass method")
	public void negativeCheckElementContainsClassTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(Locators.ID, "hidden_div", "wrong_class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementDisplayed method")
	public void checkElementDisplayedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(Locators.ID, "enable_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDisplayed method")
	public void negativeCheckElementDisplayedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(Locators.ID, "non_existent");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementDisplayedAndChecked method")
	public void checkElementDisplayedAndCheckedTest() throws Exception {
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

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDisplayedAndChecked method")
	public void negativeCheckElementDisplayedAndCheckedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndChecked(Locators.ID, "this");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDisplayedAndChecked method")
	public void negativeCheckElementDisplayedAndCheckedNotDisplayedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndChecked(Locators.ID, "non-existent-element");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementDisplayedAndEditable method")
	public void checkElementDisplayedAndEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "input_box"));
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDisplayedAndEditable method")
	public void negativeCheckElementDisplayedAndEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "non_existent"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementDisplayedAndNotEditable method")
	public void checkElementDisplayedAndNotEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
	public void negativeCheckElementDisplayedAndNotEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(Locators.ID, "input_box");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementDisplayedAndUnchecked method")
	public void checkElementDisplayedAndUncheckedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndUnchecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
	public void negativeCheckElementDisplayedAndUncheckedTest() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "this");
		asserts.checkElementDisplayedAndUnchecked(Locators.ID, "this");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementDoesntContainClass method")
	public void checkElementDoesntContainClassTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(Locators.ID, "hidden_div", "wrong_class");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementDoesntContainClass method")
	public void negativeCheckElementDoesntContainClassTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(Locators.ID, "hidden_div", "hidden_div");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration" },
			description = "An integration test to check the checkElementDoesntHaveAttribute method")
	public void checkElementDoesntHaveAttributeTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(Locators.ID, "car_list", "class");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" },
			description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
	public void negativeCheckElementDoesntHaveAttributeTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(Locators.ID, "car_list", "name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementEditable method")
	public void checkElementEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "input_box");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementEditable method")
	public void negativeCheckElementEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "non_existent");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the checkElementHasAttribute method")
	public void checkElementHasAttributeTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasAttribute(Locators.ID, "car_list", "name");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" },
			description = "An integration negative test to check the checkElementHasAttribute method")
	public void negativeCheckElementHasAttributeTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasAttribute(Locators.ID, "car_list", "class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementHasClass method")
	public void checkElementHasClassTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasClass(Locators.ID, "hidden_div", "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementHasClass method")
	public void negativeCheckElementHasClassTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementHasClass(Locators.ID, "hidden_div", "wrong_class");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementNotChecked method")
	public void checkElementNotCheckedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotChecked(Locators.ID, "that");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementNotChecked method")
	public void negativeCheckElementNotCheckedTest() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "that");
		asserts.checkElementNotChecked(Locators.ID, "that");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementNotChecked method")
	public void negativeCheckElementNotCheckedNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotChecked(Locators.ID, "non-existent-element");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementNotDisplayed method")
	public void checkElementNotDisplayedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementNotDisplayed method")
	public void negativeCheckElementNotDisplayedTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "scroll_button");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkElementNotEditable method")
	public void checkElementNotEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotEditable(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkElementNotEditable method")
	public void negativeCheckElementNotEditableTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotEditable(Locators.ID, "that");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the checkPrompt method")
	public void checkPromptTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkPrompt method")
	public void negativeCheckPromptNoPromptTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkPrompt("Enabled!");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration negative test to check the checkPrompt method")
	public void negativeCheckPromptTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration test to check the checkPromptNotPresent method")
	public void checkPromptPresentTest() throws Exception {
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

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkPromptNotPresent method")
	public void negativeCheckPromptPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkPromptPresent();
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration" }, description = "An integration test to check the checkPromptNotPresent method")
	public void checkPromptNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkPromptNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the checkPromptNotPresent method")
	public void negativeCheckPromptNotPresentTest() throws Exception {
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

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the checkTextNotVisible method")
	public void checkTextNotVisibleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextNotVisible("No such text on the page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkTextNotVisible method")
	public void negativeCheckTextNotVisibleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextNotVisible("Click me to Disable/Enable a html button");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the checkTextVisible method")
	public void checkTextVisibleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisible("Click me to Disable/Enable a html button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkTextVisible method")
	public void negativeCheckTextVisibleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisible("No such text on the page");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the checkTextVisibleOR method")
	public void checkTextVisibleORTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisibleOR("Click me to Disable/Enable a html button", "Not found on page", "This");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkTextVisibleOR method")
	public void negativeCheckTextVisibleORTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkTextVisibleOR("No such text on the page", "Not found", "None");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration" }, description = "An integration test to check the compareCssValue method")
	public void compareCssValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareCssValue(Locators.ID, "hidden_div", "display", "none");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration" }, description = "An integration negative test to check the compareCssValue method")
	public void negativeCompareCssValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareCssValue(Locators.ID, "hidden_div", "display", "inline");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the compareCssValue method")
	public void compareInputValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareInputValue(Locators.ID, "that", "That");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the compareCssValue method")
	public void negativeCompareInputValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareInputValue(Locators.ID, "that", "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the compareSelectedText method")
	public void compareSelectedTextTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedText(Locators.ID, "car_list", "Volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the compareSelectedText method")
	public void negativeCompareSelectedTextTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedText(Locators.ID, "car_list", "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the compareSelectedValue method")
	public void compareSelectedValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValue(Locators.ID, "car_list", "volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the compareSelectedValue method")
	public void negativeCompareSelectedValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValue(Locators.ID, "car_list", "wrong value");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the compareSelectedValueNotEqual method")
	public void compareSelectedValueNotEqualTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValueNotEqual(Locators.ID, "car_list", "audi");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the compareSelectedValueNotEqual method")
	public void negativeCompareSelectedValueNotEqualTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectedValueNotEqual(Locators.ID, "car_list", "volvo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the compareTextValue method")
	public void compareTextValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValue(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Alfreds Futterkiste");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the compareTextValue method")
	public void negativeCompareTextValueTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValue(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Maria Anders");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the compareTextValueContains method")
	public void compareTextValueContainsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValueContains(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Alfreds");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the compareTextValueContains method")
	public void negativeCompareTextValueContainsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTextValueContains(Locators.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Anders");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkCookie method")
	public void checkCookieTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookie("cookie", "cookietest");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkCookie method")
	public void negativeCheckCookieTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookie("cookie", "negativecookietest");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkCookie method")
	public void negativeCheckCookieWrongNameTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookie("wrongcookie", "cookietest");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkCookieNotPresent method")
	public void checkCookieNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookieNotPresent("wrongcookie");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkCookieNotPresent method")
	public void negativeCheckCookieNotPresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookieNotPresent("cookie");
		// verify 1 issue
		finish(1);
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration test to check the checkCookiePresent method")
	public void checkCookiePresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookiePresent("cookie");
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" },
			description = "An integration negative test to check the checkCookiePresent method")
	public void negativeCheckCookiePresentTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkCookiePresent("wrongcookie");
		// verify 1 issue
		finish(1);
	}
}