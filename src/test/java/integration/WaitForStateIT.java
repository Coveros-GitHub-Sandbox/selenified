package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.testng.annotations.Test;

public class WaitForStateIT extends WebBase {

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check changing the default wait method")
    public void setDefaultWaitElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.ID, "five_second_button");
        element.waitFor().changeDefaultWait(0.5);
        element.click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitFor().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresent2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitFor().present(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresent3Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitFor().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementPresent method")
    public void negativeWaitForElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitFor().present(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name").waitFor().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name").waitFor().notPresent(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent3Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitFor().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent4Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name").waitFor().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementNotPresent method")
    public void negativeWaitForElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitFor().notPresent(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitFor().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedPresenceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitFor().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedDelayedDisplayTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitFor().displayed(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitFor().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitFor().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitFor().notDisplayed(5.0);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button", 0).waitFor().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedDeletedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitFor().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementNotDisplayed method")
    public void negativeWaitForElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // use this object to verify the app looks as expected
        app.newElement(Locator.NAME, "car_list", 0).waitFor().notDisplayed(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementChecked method")
    public void waitForElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        app.newElement(Locator.NAME, "that").waitFor().checked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementChecked method")
    public void waitForElementCheckedDelayedPresenceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitFor().checked();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementChecked method")
    public void waitForElementCheckedDelayedCheckTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button").waitFor().checked(5.0);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementChecked method")
    public void negativeWaitForElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitFor().checked(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementChecked method")
    public void negativeWaitForElementCheckedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitFor().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitFor().notChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "that").waitFor().notChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "that").click();
        app.newElement(Locator.NAME, "that").waitFor().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitFor().notChecked(5.0);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button").waitFor().checked();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button", 0).waitFor().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitFor().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedPresenceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitFor().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledDelayedEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input").waitFor().enabled(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementEnabled method")
    public void negativeWaitForElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button", 0).waitFor().enabled(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitFor().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabled2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitFor().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitFor().notEnabled(5.0);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input").waitFor().enabled();
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input", 0).waitFor().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementNotEnabled method")
    public void negativeWaitForNotElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitFor().notEnabled(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEditable method")
    public void waitForElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitFor().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEditable method")
    public void waitForElementEditableBlurTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").waitFor().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEditable method")
    public void waitForElementEditableDelayedPresenceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitFor().editable();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEditable method")
    public void waitForElementEditableDelayedEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input").waitFor().editable(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementEditable method")
    public void negativeWaitForElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button", 0).waitFor().editable(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementEditable method")
    public void negativeWaitForElementEditableNoElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existant-element", 0).waitFor().editable(2.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitFor().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditable2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitFor().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditableNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitFor().notEditable(5.0);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditableDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input").waitFor().editable();
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input", 0).waitFor().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditableDeletedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "delayed_display_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button").waitFor().displayed();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitFor().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementNotEditable method")
    public void negativeWaitForNotElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitFor().notEditable(5.0);
        // verify 1 issue
        finish(1);
    }
}