package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class ActionWaitIT extends Selenified {

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
    public void waitTest() throws IOException, InterruptedException {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.wait(5.0);
        page.newElement(Locator.ID, "five_second_button").click();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check changing the default wait method")
    public void setDefaultWaitTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.waitFor().changeDefaultWait(3.0);
        page.newElement(Locator.ID, "five_second_button").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the wait method")
    public void negativeWaitTest() throws IOException, InterruptedException {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.wait(6.0);
        page.newElement(Locator.ID, "five_second_button").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the wait method")
    public void negativeWaitErrorTest() throws IOException, InterruptedException {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Thread.currentThread().interrupt();
        page.wait(6.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list").waitFor().present();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresent2Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list").waitFor().present(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresent3Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list", 0).waitFor().present();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementPresent method")
    public void negativeWaitForElementPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non-existent-name", 0).waitFor().present(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non-existent-name").waitFor().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent2Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non-existent-name").waitFor().notPresent(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent3Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non-existent-name", 0).waitFor().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent4Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non-existent-name").waitFor().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementNotPresent method")
    public void negativeWaitForElementNotPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list", 0).waitFor().notPresent(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list").waitFor().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedPresenceTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_display_button").click();
        page.newElement(Locator.NAME, "added_div", 0).waitFor().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedDisplayTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_display_button").click();
        page.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non-existent-name", 0).waitFor().displayed(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedHiddenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "hidden_div").waitFor().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "hidden_div").waitFor().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedNotPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non_existent").waitFor().notDisplayed(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedDelayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_display_button").click();
        page.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed();
        page.newElement(Locator.NAME, "delayed_hide_button").click();
        page.newElement(Locator.NAME, "delayed_hide_button", 0).waitFor().notDisplayed();
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "wait" },
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedDeletedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_display_button").click();
        page.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed();
        page.newElement(Locator.NAME, "delayed_hide_button").click();
        page.newElement(Locator.NAME, "added_div", 0).waitFor().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the WaitForElementNotDisplayed method")
    public void negativeWaitForElementNotDisplayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // use this object to verify the page looks as expected
        page.newElement(Locator.NAME, "car_list", 0).waitFor().notDisplayed(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list").waitFor().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedPresenceTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_display_button").click();
        page.newElement(Locator.NAME, "added_div", 0).waitFor().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedEnabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_enable_button").click();
        page.newElement(Locator.NAME, "delayed_input").waitFor().enabled(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the WaitForElementEnabled method")
    public void negativeWaitForElementEnabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "alert_button", 0).waitFor().enabled(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "alert_button").waitFor().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabled2Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "alert_button").waitFor().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "non_existent").waitFor().notEnabled(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledDelayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_enable_button").click();
        page.newElement(Locator.NAME, "delayed_input").waitFor().enabled();
        page.newElement(Locator.NAME, "delayed_enable_button").click();
        page.newElement(Locator.NAME, "delayed_input", 0).waitFor().notEnabled();
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "wait" },
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledDeletedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "delayed_display_button").click();
        page.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed();
        page.newElement(Locator.NAME, "delayed_hide_button").click();
        page.newElement(Locator.NAME, "added_div", 0).waitFor().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "wait",
            "virtual" }, description = "An integration negative test to check the WaitForElementNotEnabled method")
    public void negativeWaitForNotElementEnabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "car_list", 0).waitFor().notEnabled(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForPromptPresent method")
    public void waitForPromptPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "prompt_button").click();
        page.waitFor().promptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "A integration negative test to check the waitForPromptPresent method")
    public void negativeWaitForPromptPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.waitFor().promptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForConfirmationPresent method")
    public void waitForConfirmationPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "confirm_button").click();
        page.waitFor().confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the waitForConfirmationPresent method")
    public void negativeWaitForConfirmationPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.waitFor().confirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration test to check the waitForAlertPresent method")
    public void waitForAlertPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "disable_click").click();
        page.newElement(Locator.ID, "alert_button").click();
        page.waitFor().alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "wait" }, description = "An integration negative test to check the waitForAlertPresent method")
    public void negativeWaitForAlertPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.waitFor().alertPresent();
        // verify 1 issue
        finish(1);
    }
}