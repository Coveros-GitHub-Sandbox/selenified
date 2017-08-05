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