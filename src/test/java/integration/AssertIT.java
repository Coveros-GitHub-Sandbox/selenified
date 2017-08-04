package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class AssertIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // set the base URL for the tests here
        setTestSite("http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @Test(groups = { "integration", "asserts", "virtual" }, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform the verification
        asserts.compareTitle("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check a title")
    public void negativeCompareTitleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTitle("Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareNumOfSelectOptions method")
    public void compareNumOfSelectOptionsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfSelectOptions(Locator.NAME, "car_list", 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfSelectOptions(new Element(Locator.NAME, "car_list"), 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfSelectOptions(new Element(Locator.NAME, "alert_button"), 0, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectOptions method")
    public void compareSelectOptionsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectValues(Locator.NAME, "car_list", new String[] { "volvo", "saab", "mercedes", "audi" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectValues(new Element(Locator.NAME, "non-existent-element"),
                new String[] { "volvo", "ford", "mercedes", "audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectValues(new Element(Locator.NAME, "car_list"), 0,
                new String[] { "volvo", "ford", "mercedes", "audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareSelectValues method")
    public void negativeCompareSelectValuesExtraTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectValues(Locator.NAME, "car_list",
                new String[] { "volvo", "saab", "mercedes", "audi", "chevrolet" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesMissingTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectValues(Locator.NAME, "car_list", new String[] { "volvo", "saab", "mercedes" });
        // verify no issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareNumOfTableRows method")
    public void compareNumOfTableRowsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfTableRows(Locator.ID, "table", 7);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfTableRows(new Element(Locator.ID, "table"), 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfTableRows(new Element(Locator.ID, "non-existent-element"), 0, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareNumOfTableColumns method")
    public void compareNumOfTableColumnsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfTableColumns(Locator.ID, "table", 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfTableColumns(new Element(Locator.ID, "table"), 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsNotPresetTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareNumOfTableColumns(new Element(Locator.ID, "non-existent-element"), 0, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTableCellText(Locator.ID, "table", 2, 2, "Francisco Chang");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTableCellText(new Element(Locator.ID, "table"), 1, 1, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTableCellText(new Element(Locator.ID, "non-existent-element"), 0, 0, 0, "");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkSelectValuePresent method")
    public void checkSelectValuePresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkSelectValuePresent(Locator.ID, "car_list", "volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkSelectValuePresent method")
    public void negativeCheckSelectValuePresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkSelectValuePresent(new Element(Locator.ID, "car_list"), "ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkSelectValuePresent method")
    public void negativeCheckSelectValuePresentNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkSelectValuePresent(new Element(Locator.ID, "alert_button"), 0, "ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkSelectValueNotPresent method")
    public void checkSelectValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkSelectValueNotPresent(Locator.ID, "car_list", "ford");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkSelectValueNotPresent(new Element(Locator.ID, "car_list"), "volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkSelectValueNotPresent(new Element(Locator.ID, "alert_button"), 0, "volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkIfOptionInSelect method")
    public void checkIfOptionInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkIfOptionInSelect(Locator.ID, "car_list", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkIfOptionInSelect(new Element(Locator.ID, "car_list"), "ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkIfOptionInSelect(new Element(Locator.ID, "alert_button"), 0, "ford");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectDelayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "delayed_enable_button");
        asserts.checkIfOptionInSelect(Locator.ID, "delayed_input", "ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkIfOptionNotInSelect method")
    public void checkIfOptionNotInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkIfOptionNotInSelect(Locator.ID, "car_list", "ford");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkIfOptionNotInSelect(new Element(Locator.ID, "car_list"), "audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkIfOptionNotInSelect(new Element(Locator.ID, "alert_button"), 0, "audi");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void checkIfOptionNotInSelectDelayedTest() {
        // use this object to verify the page looks as expected
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "delayed_enable_button");
        asserts.checkIfOptionNotInSelect(Locator.ID, "delayed_input", "ford");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkAlertPresent method")
    public void checkAlertPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkAlertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckAlertPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkAlertPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkAlertPresent method")
    public void checkConfirmationPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkConfirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckConfirmationPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkConfirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkAlertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckNotAlertPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkAlertNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkAlert method")
    public void checkAlertTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkAlert("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkAlert method")
    public void checkAlertRegexTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkAlert("([A-Z])\\w+!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertNoAlertTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkAlert("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkAlert("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkConfirmation("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkConfirmation("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationNoConfirmationTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkConfirmation("Disabled!");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkConfirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkConfirmationNotPresent method")
    public void negativeCheckConfirmationNotPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkConfirmationNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkElementContainsClass method")
    public void checkElementContainsClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementContainsClass(Locator.ID, "hidden_div", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementContainsClass(new Element(Locator.ID, "hidden_div"), "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementContainsClass(new Element(Locator.ID, "non-existent-element"), 0, "wrong_class");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementContainsClass(Locator.ID, "check", "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntContainClass(Locator.ID, "hidden_div", "wrong_class");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntContainClass(new Element(Locator.ID, "hidden_div"), "hidden_div");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntContainClass(new Element(Locator.ID, "non-existent-element"), 0, "hidden_div");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntContainClass(Locator.ID, "check", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntHaveAttribute(Locator.ID, "car_list", "class");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntHaveAttribute(new Element(Locator.ID, "car_list"), "name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntHaveAttribute(new Element(Locator.ID, "non-existent-element"), 0, "name");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementDoesntHaveAttribute(Locator.ID, "check", "name");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkElementHasAttribute method")
    public void checkElementHasAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasAttribute(Locator.ID, "car_list", "name");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasAttribute(new Element(Locator.ID, "car_list"), "class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasAttribute(new Element(Locator.ID, "non-existent-element"), 0, "class");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasAttribute(Locator.ID, "check", "class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkElementHasClass method")
    public void checkElementHasClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasClass(Locator.ID, "hidden_div", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasClass(new Element(Locator.ID, "hidden_div"), "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasClass(new Element(Locator.ID, "non-existent-element"), 0, "wrong_class");
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementHasClass(Locator.ID, "check", "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkPrompt method")
    public void checkPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkPrompt("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptNoPromptTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkPrompt("Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkPrompt("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkPromptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkPromptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkPromptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptNotPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        asserts.checkPromptNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkTextNotVisible("No such text on the page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkTextNotVisible method")
    public void negativeCheckTextNotVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkTextNotVisible("Click me to Disable/Enable a html button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkTextVisible("Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkTextVisible method")
    public void negativeCheckTextVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkTextVisible("No such text on the page");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkTextVisibleOR method")
    public void checkTextVisibleORTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkTextVisibleOR("Click me to Disable/Enable a html button", "Not found on page", "This");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkTextVisibleOR method")
    public void negativeCheckTextVisibleORTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkTextVisibleOR("No such text on the page", "Not found", "None");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the compareCssValue method")
    public void compareCssValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareCssValue(Locator.ID, "hidden_div", "display", "none");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareCssValue(new Element(Locator.ID, "hidden_div"), "display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareCssValue(new Element(Locator.ID, "non-existent-element"), 0, "display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareInputValue(Locator.ID, "that", "That");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareInputValue(new Element(Locator.ID, "that"), "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareInputValue(new Element(Locator.ID, "non-existent-element"), 0, "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedText(Locator.ID, "car_list", "Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedText(new Element(Locator.ID, "car_list"), "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedText(new Element(Locator.ID, "alert_button"), 0, "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValue(Locator.ID, "car_list", "volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValue(new Element(Locator.ID, "car_list"), "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValue(new Element(Locator.ID, "enabled_button"), 0, "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotInputTest() throws Exception {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValue(Locator.ID, "table", "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareSelectedValueNotEqual method")
    public void compareSelectedValueNotEqualTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValueNotEqual(Locator.ID, "car_list", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareSelectedValueNotEqual method")
    public void negativeCompareSelectedValueNotEqualTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValueNotEqual(new Element(Locator.ID, "car_list"), "volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the compareSelectedValueNotEqual method")
    public void negativeCompareSelectedValueNotEqualNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareSelectedValueNotEqual(new Element(Locator.ID, "enabled_button"), 0, "volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTextValue(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Centro comercial Moctezuma");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTextValue(new Element(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]"), "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTextValue(new Element(Locator.ID, "non-existent-element"), 0, "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTextValueContains(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Centro comer");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTextValueContains(new Element(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]"), "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTextValueContains(new Element(Locator.ID, "non-existent-element"), 0, "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookie("cookie", "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookie("cookie", "negativecookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieWrongNameTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookie("wrongcookie", "cookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookieNotPresent("wrongcookie");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookieNotPresent method")
    public void negativeCheckCookieNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookieNotPresent("cookie");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookiePresent("cookie");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookiePresent method")
    public void negativeCheckCookiePresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkCookiePresent("wrongcookie");
        // verify 1 issue
        finish(1);
    }
}