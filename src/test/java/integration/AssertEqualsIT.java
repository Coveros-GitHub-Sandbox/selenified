package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class AssertEqualsIT extends TestBase {

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

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectOptions method")
    public void compareSelectOptionsTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectValues(Locator.NAME, "car_list", new String[] { "volvo", "saab", "mercedes", "audi" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectValues(Locator.NAME, "non-existent-element", 0,
                new String[] { "volvo", "ford", "mercedes", "audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectValues(Locator.NAME, "car_list",
                new String[] { "volvo", "ford", "mercedes", "audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectValues method")
    public void negativeCompareSelectValuesExtraTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectValues(Locator.NAME, "car_list",
                new String[] { "volvo", "saab", "mercedes", "audi", "chevrolet" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesMissingTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectValues(Locator.NAME, "car_list", new String[] { "volvo", "saab", "mercedes" });
        // verify no issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().text(Locator.ID, "table", 2, 2, "Francisco Chang");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().text(Locator.ID, "table", 0, 1, 1, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().text(Locator.ID, "non-existent-element", 0, 0, "");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the checkElementHasClass method")
    public void checkElementHasClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().classs(Locator.ID, "hidden_div", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().classs(Locator.ID, "hidden_div", 0, "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().classs(Locator.ID, "non-existent-element", "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().classs(Locator.ID, "check", "wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration test to check the compareCssValue method")
    public void compareCssValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().cssValue(Locator.ID, "hidden_div", "display", "none");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().cssValue(Locator.ID, "hidden_div", 0, "display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().cssValue(Locator.ID, "non-existent-element", "display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "that", "That");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "that", 0, "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedValue(Locator.ID, "car_list", "volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedValue(Locator.ID, "car_list", 0, "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedValue(Locator.ID, "enabled_button", "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotInputTest() throws Exception {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedValue(Locator.ID, "table", "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().text(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Centro comercial Moctezuma");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().text(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0, "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().text(Locator.ID, "non-existent-element", "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "textarea_input", "A Pretty Text Area");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "textarea_input", 0, "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotInputTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "hover_over_me", "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "non-existent-element", "Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedOption(Locator.ID, "car_list", "Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedOption(Locator.ID, "car_list", 0, "wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().selectedOption(Locator.ID, "alert_button", "wrong value");
        // verify 1 issue
        finish(1);
    }
}