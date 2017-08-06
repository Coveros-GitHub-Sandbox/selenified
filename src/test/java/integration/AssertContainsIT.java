package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Assert;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class AssertContainsIT extends TestBase {

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

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareNumOfSelectOptions method")
    public void compareNumOfSelectOptionsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOptions(Locator.NAME, "car_list", 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOptions(Locator.NAME, "car_list", 0, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOptions(Locator.NAME, "alert_button", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareNumOfTableRows method")
    public void compareNumOfTableRowsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().rows(Locator.ID, "table", 7);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().rows(Locator.ID, "table", 0, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().rows(Locator.ID, "non-existent-element", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareNumOfTableColumns method")
    public void compareNumOfTableColumnsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().columns(Locator.ID, "table", 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().columns(Locator.ID, "table", 0, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsNotTableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().columns(Locator.ID, "check", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsNotPresetTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().columns(Locator.ID, "non-existent-element", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the checkIfOptionInSelect method")
    public void checkIfOptionInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOption(Locator.ID, "car_list", "Audi");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOption(Locator.ID, "car_list", 0, "Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOption(Locator.ID, "alert_button", "Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectDelayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "delayed_enable_button");
        asserts.contains().selectOption(Locator.ID, "delayed_input", "Ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void checkIfValueInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectValue(Locator.ID, "car_list", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectValue(Locator.ID, "car_list", "ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectValue(Locator.ID, "alert_button", 0, "ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectDelayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "delayed_enable_button");
        asserts.contains().selectValue(Locator.ID, "delayed_input", "ford");
        // verify 1 issues
        finish(1);
    }
    
    @Test(groups = { "integration", "asserts",
    "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectCheckDelayedTest() {
    	// use this object to manipulate the page
    	Action actions = this.actions.get();
    	// use this object to verify the page looks as expected
    	Assert asserts = this.asserts.get();
    	// perform some actions
    	actions.click(Locator.ID, "delayed_enable_button");
    	asserts.contains().selectValue(Locator.ID, "check", "ford");
    	// verify 1 issues
    	finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the checkElementContainsClass method")
    public void checkElementContainsClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().classs(Locator.ID, "hidden_div", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().classs(Locator.ID, "hidden_div", 0, "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().classs(Locator.ID, "non-existent-element", "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().classs(Locator.ID, "check", "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration test to check the checkElementHasAttribute method")
    public void checkElementHasAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().attribute(Locator.ID, "car_list", "name");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().attribute(Locator.ID, "car_list", 0, "class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().attribute(Locator.ID, "non-existent-element", "class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().attribute(Locator.ID, "check", "class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().text(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Centro comer");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().text(Locator.ID, "check", "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().text(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0, "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().text(Locator.ID, "non-existent-element", "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareTextValueContains method")
    public void compareValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(Locator.ID, "textarea_input", "Pretty");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration test to check the compareTextValueContains method")
    public void compareValueContainsDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(Locator.ID, "check", "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(Locator.ID, "textarea_input", 0, "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueNotInputContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(Locator.ID, "overlay_span", "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueContainsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(Locator.ID, "non-existent-element", "Anders");
        // verify 1 issue
        finish(1);
    }
}