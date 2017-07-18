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
    public void beforeClass() throws IOException {
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
    public void waitForElementPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementPresent(Locator.NAME, "car_list");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementPresent method")
    public void negativeWaitForElementPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementPresent(new Element(Locator.NAME, "non-existent-name"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotPresent(Locator.NAME, "non-existent-name");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementNotPresent method")
    public void negativeWaitForElementNotPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotPresent(new Element(Locator.NAME, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementDisplayed(Locator.NAME, "car_list");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedPresenceTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.waitForElementDisplayed(Locator.NAME, "added_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedDisplayTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.waitForElementDisplayed(Locator.NAME, "delayed_hide_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementDisplayed(new Element(Locator.NAME, "non-existent-name"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedHiddenTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementDisplayed(new Element(Locator.NAME, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotDisplayed(Locator.NAME, "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedDelayedTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.waitForElementDisplayed(Locator.NAME, "delayed_hide_button");
        actions.click(Locator.NAME, "delayed_hide_button");
        actions.waitForElementNotDisplayed(Locator.NAME, "delayed_hide_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the WaitForElementNotDisplayed method")
    public void negativeWaitForElementNotDisplayedTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        actions.waitForElementNotDisplayed(new Element(Locator.NAME, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementEnabled(Locator.NAME, "car_list");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedPresenceTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_display_button");
        actions.waitForElementEnabled(Locator.NAME, "added_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedEnabledTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_enable_button");
        actions.waitForElementEnabled(Locator.NAME, "delayed_input");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the WaitForElementEnabled method")
    public void negativeWaitForElementEnabledTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementEnabled(new Element(Locator.NAME, "alert_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotEnabled(Locator.NAME, "alert_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledDelayedTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "delayed_enable_button");
        actions.waitForElementEnabled(Locator.NAME, "delayed_input");
        actions.click(Locator.NAME, "delayed_enable_button");
        actions.waitForElementNotEnabled(Locator.NAME, "delayed_input");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the WaitForElementNotEnabled method")
    public void negativeWaitForNotElementEnabledTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotEnabled(new Element(Locator.NAME, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForPromptPresent method")
    public void waitForPromptPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "prompt_button");
        actions.waitForPromptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "A integration negative test to check the waitForPromptPresent method")
    public void negativeWaitForPromptPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForPromptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForConfirmationPresent method")
    public void waitForConfirmationPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "confirm_button");
        actions.waitForConfirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the waitForConfirmationPresent method")
    public void negativeWaitForConfirmationPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForConfirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForAlertPresent method")
    public void waitForAlertPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        actions.waitForAlertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the waitForAlertPresent method")
    public void negativeWaitForAlertPresentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForAlertPresent();
        // verify 1 issue
        finish(1);
    }
}