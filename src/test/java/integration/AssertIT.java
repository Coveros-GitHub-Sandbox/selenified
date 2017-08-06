package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Assert;
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
        asserts.alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckAlertPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.alertPresent();
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
        asserts.confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckConfirmationPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.confirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.alertNotPresent();
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
        asserts.alertNotPresent();
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
        asserts.alertPresent("Enabled!");
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
        asserts.alertPresent("([A-Z])\\w+!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertNoAlertTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.alertPresent("Disabled!");
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
        asserts.alertPresent("Disabled!");
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
        asserts.confirmationPresent("Enabled!");
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
        asserts.confirmationPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationNoConfirmationTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.confirmationPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.confirmationNotPresent();
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
        asserts.confirmationNotPresent();
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
        asserts.promptPresent("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptNoPromptTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.promptPresent("Enabled!");
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
        asserts.promptPresent("Disabled!");
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
        asserts.promptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.promptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration",
            "asserts" }, description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.promptNotPresent();
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
        asserts.promptNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.textNotPresent("No such text on the page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkTextNotVisible method")
    public void negativeCheckTextNotVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.textNotPresent("Click me to Disable/Enable a html button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.textPresent("Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkTextVisible method")
    public void negativeCheckTextVisibleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.textPresent("No such text on the page");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieExists("cookie", "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieExists("cookie", "negativecookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieWrongNameTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieExists("wrongcookie", "cookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieNotExists("wrongcookie");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookieNotPresent method")
    public void negativeCheckCookieNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieNotExists("cookie");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieExists("cookie");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts",
            "virtual" }, description = "An integration negative test to check the checkCookiePresent method")
    public void negativeCheckCookiePresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.cookieExists("wrongcookie");
        // verify 1 issue
        finish(1);
    }
}