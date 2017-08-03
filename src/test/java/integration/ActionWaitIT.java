package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class ActionWaitIT extends TestBase {

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

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the wait method")
    public void negativeWaitTest() throws IOException, InterruptedException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.wait(6.0);
        actions.click(new Element(Locator.ID, "five_second_button"));
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the wait method")
    public void negativeWaitErrorTest() throws IOException, InterruptedException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Thread.currentThread().interrupt();
        actions.wait(6.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementPresent(Locator.NAME, "car_list");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementPresent method")
    public void negativeWaitForElementPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementPresent(new Element(Locator.NAME, "non-existent-name"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementNotPresent(Locator.NAME, "non-existent-name");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementNotPresent method")
    public void negativeWaitForElementNotPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementNotPresent(new Element(Locator.NAME, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementDisplayed(Locator.NAME, "car_list");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedPresenceTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.driverWait().forElementDisplayed(Locator.NAME, "added_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedDisplayTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.driverWait().forElementDisplayed(Locator.NAME, "delayed_hide_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementDisplayed(new Element(Locator.NAME, "non-existent-name"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedHiddenTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementDisplayed(new Element(Locator.NAME, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementNotDisplayed(Locator.NAME, "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedDelayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.driverWait().forElementDisplayed(Locator.NAME, "delayed_hide_button");
        actions.click(Locator.NAME, "delayed_hide_button");
        actions.driverWait().forElementNotDisplayed(Locator.NAME, "delayed_hide_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the WaitForElementNotDisplayed method")
    public void negativeWaitForElementNotDisplayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        actions.driverWait().forElementNotDisplayed(new Element(Locator.NAME, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementEnabled(Locator.NAME, "car_list");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedPresenceTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.driverWait().forElementEnabled(Locator.NAME, "added_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedEnabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_enable_button");
        actions.driverWait().forElementEnabled(Locator.NAME, "delayed_input");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the WaitForElementEnabled method")
    public void negativeWaitForElementEnabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementEnabled(new Element(Locator.NAME, "alert_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementNotEnabled(Locator.NAME, "alert_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledDelayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_enable_button");
        actions.driverWait().forElementEnabled(Locator.NAME, "delayed_input");
        actions.click(Locator.NAME, "delayed_enable_button");
        actions.driverWait().forElementNotEnabled(Locator.NAME, "delayed_input");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the WaitForElementNotEnabled method")
    public void negativeWaitForNotElementEnabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forElementNotEnabled(new Element(Locator.NAME, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForPromptPresent method")
    public void waitForPromptPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "prompt_button");
        actions.driverWait().forPromptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "A integration negative test to check the waitForPromptPresent method")
    public void negativeWaitForPromptPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forPromptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForConfirmationPresent method")
    public void waitForConfirmationPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "confirm_button");
        actions.driverWait().forConfirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the waitForConfirmationPresent method")
    public void negativeWaitForConfirmationPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forConfirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForAlertPresent method")
    public void waitForAlertPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        actions.driverWait().forAlertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the waitForAlertPresent method")
    public void negativeWaitForAlertPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.driverWait().forAlertPresent();
        // verify 1 issue
        finish(1);
    }
}