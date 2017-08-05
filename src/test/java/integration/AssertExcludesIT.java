package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class AssertExcludesIT extends TestBase {

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

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration test to check the checkSelectValueNotPresent method")
    public void checkSelectValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().selectValue(Locator.ID, "car_list", "ford");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().selectValue(new Element(Locator.ID, "car_list"), "volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().selectValue(new Element(Locator.ID, "alert_button"), "volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration test to check the checkIfOptionNotInSelect method")
    public void checkIfOptionNotInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().selectOption(Locator.ID, "car_list", "ford");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().selectOption(new Element(Locator.ID, "car_list"), "Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().selectOption(new Element(Locator.ID, "alert_button"), "audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().classs(Locator.ID, "hidden_div", "wrong_class");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().classs(new Element(Locator.ID, "hidden_div"), "hidden_div");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes",
            "virtual" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().classs(new Element(Locator.ID, "non-existent-element"), "hidden_div");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().classs(Locator.ID, "check", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().attribute(Locator.ID, "car_list", "class");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().attribute(new Element(Locator.ID, "car_list"), "name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().attribute(new Element(Locator.ID, "non-existent-element"), "name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "excludes" }, description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().attribute(Locator.ID, "check", "name");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration test to check the compareTextValueExcludes method")
    public void compareTextValueExcludesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().text(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", "Anders");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareTextValueExcludesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().text(new Element(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]"), "Centro comer");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareTextValueExcludesNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().text(new Element(Locator.ID, "non-existent-element"), "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration test to check the compareTextValueExcludes method")
    public void compareValueExcludesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().value(Locator.ID, "textarea_input", "Anders");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueExcludesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().value(new Element(Locator.ID, "textarea_input"), "Pretty");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueNotInputExcludesTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().value(new Element(Locator.ID, "overlay_span"), "Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "excludes", "contains",
            "virtual" }, description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueExcludesNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.excludes().value(new Element(Locator.ID, "non-existent-element"), "Anders");
        // verify 1 issue
        finish(1);
    }
}