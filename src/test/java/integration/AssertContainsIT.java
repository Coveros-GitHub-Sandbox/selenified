package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
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
        asserts.contains().selectOptions(new Element(Locator.NAME, "car_list"), 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOptions(new Element(Locator.NAME, "alert_button"), 0);
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
        asserts.contains().rows(new Element(Locator.ID, "table"), 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().rows(new Element(Locator.ID, "non-existent-element"), 0);
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
        asserts.contains().columns(new Element(Locator.ID, "table"), 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsNotPresetTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().columns(new Element(Locator.ID, "non-existent-element"), 0);
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
        asserts.contains().selectOption(new Element(Locator.ID, "car_list"), "Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectOption(new Element(Locator.ID, "alert_button"), "Ford");
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
    public void negativeCheckIfValueInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectValue(new Element(Locator.ID, "car_list"), "ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().selectValue(new Element(Locator.ID, "alert_button"), "ford");
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
        asserts.contains().classs(new Element(Locator.ID, "hidden_div"), "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().classs(new Element(Locator.ID, "non-existent-element"), "wrong_class");
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
        asserts.contains().attribute(new Element(Locator.ID, "car_list"), "class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "contains" }, description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().attribute(new Element(Locator.ID, "non-existent-element"), "class");
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
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().text(new Element(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]"), "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().text(new Element(Locator.ID, "non-existent-element"), "Anders");
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
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(new Element(Locator.ID, "textarea_input"), "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueNotInputContainsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(new Element(Locator.ID, "overlay_span"), "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueContainsNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.contains().value(new Element(Locator.ID, "non-existent-element"), "Anders");
        // verify 1 issue
        finish(1);
    }
}