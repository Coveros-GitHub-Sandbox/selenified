package samples;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Element;
import tools.output.Selenium.Locators;

public class SampleIT extends TestBase {

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

	@DataProvider(name = "car list items", parallel = true)
	public Object[][] DataSetOptions() {
		return new Object[][] { new Object[] { "volvo" }, new Object[] { "saab" }, new Object[] { "mercedes" } };
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check a title")
	public void sampleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform our verification
		asserts.compareTitle("Selenified Test Page");
		// perform our verification
		finish();
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample negative test to check a title")
	public void sampleNegativeTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareTitle("Yahoo");
		// perform our verification
		finish(1);
	}

	@Test(dataProvider = "car list items", groups = { "sample",
			"virtual" }, description = "A sample test using a data provider to perform searches")
	public void sampleTestWDataProvider(String listItem) throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "car_list", listItem);
		// close out the test
		finish();
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the goToURL method")
	public void sampleTestGoToURL() throws Exception {
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

	@Test(groups = { "sample", "virtual" }, description = "A sample negative test to check the goToURL method")
	public void sampleTestNegativeGoToURL() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.goToURL("https://www.yahoo.com/");
		asserts.compareURL("https://www.google.com/");
		// verify no issues
		finish(1);
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

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the waitForElementPresent method")
	public void sampleTestNegativeWaitForElementPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(Locators.NAME, "non-existent-name");
		// verify no issues
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

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the waitForElementNotPresent method")
	public void sampleTestNegativeWaitForElementNotPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotPresent(Locators.NAME, "car_list");
		// verify no issues
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

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the waitForElementDisplayed method")
	public void sampleTestNegativeWaitForElementDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementDisplayed(Locators.NAME, "non-existent-name");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the WaitForElementNotDisplayed method")
	public void sampleTestWaitForElementNotDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotDisplayed(Locators.NAME, "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the WaitForElementNotDisplayed method")
	public void sampleTestNegativeWaitForElementNotDisplayed() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		actions.waitForElementNotDisplayed(Locators.NAME, "car_list");
		// verify no issues
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
		// verify no issues
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

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the WaitForElementNotEnabled method")
	public void sampleTestNegativeWaitForNotElementEnabled() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementNotEnabled(Locators.NAME, "car_list");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the getNumOfSelectOptions method")
	public void sampleGetNumOfSelectOptionsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(Locators.NAME, "car_list", 4);
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the getNumOfSelectOptions method")
	public void sampleNegativeGetNumOfSelectOptionsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfSelectOptions(Locators.NAME, "car_list", 0);
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the getSelectOptions method")
	public void sampleGetSelectOptionsTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareSelectValues(Locators.NAME, "car_list", new String[] { "volvo", "saab", "mercedes", "audi" });
		// verify no issues
		finish();
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the getNumOfTableRows method")
	public void sampleGetNumOfTableRows() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(Locators.ID, "table", 7);
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the getNumOfTableRows method")
	public void sampleNegativeGetNumOfTableRows() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableRows(Locators.ID, "table", 0);
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the getNumOfTableColumns method")
	public void sampleGetNumOfTableColumns() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(Locators.ID, "table", 4);
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the getNumOfTableColumns method")
	public void sampleNegativeGetNumOfTableColumns() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareNumOfTableColumns(Locators.ID, "table", 0);
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the getTableRowWHeader method")
	public void sampleGetTableRowWHeader() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareRowWHeader(Locators.ID, "table", "CEO", 3);
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the getTableRowWHeader method")
	public void sampleNegativeGetTableRowWHeader() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareRowWHeader(Locators.ID, "table", "CEO", 0);
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkSelectValuePresent method")
	public void sampleCheckSelectValuePresent() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(Locators.ID, "car_list", "volvo");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkSelectValuePresent method")
	public void sampleNegativeCheckSelectValuePresent() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValuePresent(Locators.ID, "car_list", "ford");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the checkSelectValueNotPresent method")
	public void sampleCheckSelectValueNotPresent() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(Locators.ID, "car_list", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkSelectValueNotPresent method")
	public void sampleNegativeCheckSelectValueNotPresent() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkSelectValueNotPresent(Locators.ID, "car_list", "volvo");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkIfOptionInSelect method")
	public void sampleCheckIfOptionInSelect() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(Locators.ID, "car_list", "audi");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkIfOptionInSelect method")
	public void sampleNegativeCheckIfOptionInSelect() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionInSelect(Locators.ID, "car_list", "ford");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkIfOptionNotInSelect method")
	public void sampleCheckIfOptionNotInSelect() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(Locators.ID, "car_list", "ford");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkIfOptionNotInSelect method")
	public void sampleNegativeCheckIfOptionNotInSelect() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkIfOptionNotInSelect(Locators.ID, "car_list", "audi");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample" }, description = "A sample test to check the checkAlertPresent method")
	public void sampleCheckAlertPresent() throws Exception {
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

	@Test(groups = { "sample" }, description = "A sample negative test to check the checkAlertPresent method")
	public void sampleNegativeCheckAlertPresent() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkAlertPresent();
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample" }, description = "A sample test to check the checkConfirmation method")
	public void sampleCheckConfirmation() throws Exception {
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

	@Test(groups = { "sample" }, description = "A sample negative test to check the checkConfirmation method")
	public void sampleNegativeCheckConfirmation() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkConfirmation("Disabled!");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample" }, description = "A sample test to check the checkConfirmationNotPresent method")
	public void sampleCheckConfirmationNotPresent() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "sample" }, description = "A sample negative test to check the checkConfirmationNotPresent method")
	public void sampleNegativeCheckConfirmationNotPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkElementChecked method")
	public void sampleCheckElementChecked() throws Exception {
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

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementChecked method")
	public void sampleNegativeCheckElementChecked() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementChecked(Locators.ID, "this");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkElementContainsClass method")
	public void sampleCheckElementContainsClass() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(Locators.ID, "hidden_div", "hidden_div");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementContainsClass method")
	public void sampleNegativeCheckElementContainsClass() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementContainsClass(Locators.ID, "hidden_div", "wrong_class");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkElementDisplayed method")
	public void sampleCheckElementDisplayed() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(Locators.ID, "enable_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementDisplayed method")
	public void sampleNegativeCheckElementDisplayed() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayed(Locators.ID, "non_existent");
		// verify no issues
		finish(2);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the checkElementDisplayedAndChecked method")
	public void sampleCheckElementDisplayedAndChecked() throws Exception {
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

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementDisplayedAndChecked method")
	public void sampleNegativeCheckElementDisplayedAndChecked() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndChecked(Locators.ID, "this");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the checkElementDisplayedAndEditable method")
	public void sampleCheckElementDisplayedAndEditable() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "input_box"));
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementDisplayedAndEditable method")
	public void sampleNegativeCheckElementDisplayedAndEditable() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndEditable(new Element(Locators.ID, "non_existent"));
		// verify no issues
		finish(2);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the checkElementDisplayedAndNotEditable method")
	public void sampleCheckElementDisplayedAndNotEditable() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementDisplayedAndNotEditable method")
	public void sampleNegativeCheckElementDisplayedAndNotEditable() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndNotEditable(Locators.ID, "input_box");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the checkElementDisplayedAndUnchecked method")
	public void sampleCheckElementDisplayedAndUnchecked() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDisplayedAndUnchecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementDisplayedAndUnchecked method")
	public void sampleNegativeCheckElementDisplayedAndUnchecked() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "this");
		asserts.checkElementDisplayedAndUnchecked(Locators.ID, "this");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to check the checkElementDoesntContainClass method")
	public void sampleCheckElementDoesntContainClass() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(Locators.ID, "hidden_div", "wrong_class");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementDoesntContainClass method")
	public void sampleNegativeCheckElementDoesntContainClass() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntContainClass(Locators.ID, "hidden_div", "hidden_div");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample" }, description = "A sample test to check the checkElementDoesntHaveAttribute method")
	public void sampleCheckElementDoesntHaveAttribute() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(Locators.ID, "car_list", "class");
		// verify no issues
		finish();
	}

	@Test(groups = {
			"sample" }, description = "A sample negative test to check the checkElementDoesntHaveAttribute method")
	public void sampleNegativeCheckElementDoesntHaveAttribute() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementDoesntHaveAttribute(Locators.ID, "car_list", "name");
		// verify no issues
		finish(1);
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the checkElementEditable method")
	public void sampleCheckElementEditable() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "input_box");
		// verify no issues
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample negative test to check the checkElementEditable method")
	public void sampleNegativeCheckElementEditable() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementEditable(Locators.ID, "non_existent");
		// verify no issues
		finish(2);
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
		// verify no issues
		finish(1);
	}
}