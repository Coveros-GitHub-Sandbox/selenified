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
        element.waitForState().changeDefaultWait(0.5);
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
        app.newElement(Locator.NAME, "car_list").waitForState().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresent2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForState().present(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementPresent method")
    public void waitForElementPresent3Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitForState().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementPresent method")
    public void negativeWaitForElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitForState().present(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name").waitForState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name").waitForState().notPresent(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent3Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitForState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementNotPresent method")
    public void waitForElementNotPresent4Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name").waitForState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementNotPresent method")
    public void negativeWaitForElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitForState().notPresent(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the waitForElementDisplayed method")
    public void waitForElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForState().displayed();
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
        app.newElement(Locator.NAME, "added_div", 0).waitForState().displayed();
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
        app.newElement(Locator.NAME, "delayed_hide_button").waitForState().displayed(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitForState().displayed(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementDisplayed method")
    public void negativeWaitForElementDisplayedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitForState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitForState().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotDisplayed method")
    public void waitForElementNotDisplayedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitForState().notDisplayed(5.0);
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
        app.newElement(Locator.NAME, "delayed_hide_button").waitForState().displayed();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button", 0).waitForState().notDisplayed();
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
        app.newElement(Locator.NAME, "delayed_hide_button").waitForState().displayed();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitForState().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementNotDisplayed method")
    public void negativeWaitForElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // use this object to verify the app looks as expected
        app.newElement(Locator.NAME, "car_list", 0).waitForState().notDisplayed(5.0);
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
        app.newElement(Locator.NAME, "that").waitForState().checked();
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
        app.newElement(Locator.NAME, "added_div", 0).waitForState().checked();
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
        app.newElement(Locator.NAME, "delayed_hide_button").waitForState().checked(5.0);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementChecked method")
    public void negativeWaitForElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-name", 0).waitForState().checked(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the waitForElementChecked method")
    public void negativeWaitForElementCheckedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitForState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "hidden_div").waitForState().notChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "that").waitForState().notChecked();
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
        app.newElement(Locator.NAME, "that").waitForState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotChecked method")
    public void waitForElementNotCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitForState().notChecked(5.0);
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
        app.newElement(Locator.NAME, "delayed_hide_button").waitForState().checked();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "delayed_hide_button", 0).waitForState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEnabled method")
    public void waitForElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForState().enabled();
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
        app.newElement(Locator.NAME, "added_div", 0).waitForState().enabled();
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
        app.newElement(Locator.NAME, "delayed_input").waitForState().enabled(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementEnabled method")
    public void negativeWaitForElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button", 0).waitForState().enabled(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitForState().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabled2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitForState().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEnabled method")
    public void waitForElementNotEnabledNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitForState().notEnabled(5.0);
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
        app.newElement(Locator.NAME, "delayed_input").waitForState().enabled();
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input", 0).waitForState().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementNotEnabled method")
    public void negativeWaitForNotElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitForState().notEnabled(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEditable method")
    public void waitForElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementEditable method")
    public void waitForElementEditableBlurTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").waitForState().editable();
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
        app.newElement(Locator.NAME, "added_div", 0).waitForState().editable();
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
        app.newElement(Locator.NAME, "delayed_input").waitForState().editable(5.0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementEditable method")
    public void negativeWaitForElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button", 0).waitForState().editable(5.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementEditable method")
    public void negativeWaitForElementEditableNoElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existant-element", 0).waitForState().editable(2.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitForState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditable2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").waitForState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check the WaitForElementNotEditable method")
    public void waitForElementNotEditableNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non_existent").waitForState().notEditable(5.0);
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
        app.newElement(Locator.NAME, "delayed_input").waitForState().editable();
        app.newElement(Locator.NAME, "delayed_enable_button").click();
        app.newElement(Locator.NAME, "delayed_input", 0).waitForState().notEditable();
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
        app.newElement(Locator.NAME, "delayed_hide_button").waitForState().displayed();
        app.newElement(Locator.NAME, "delayed_hide_button").click();
        app.newElement(Locator.NAME, "added_div", 0).waitForState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration negative test to check the WaitForElementNotEditable method")
    public void negativeWaitForNotElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).waitForState().notEditable(5.0);
        // verify 1 issue
        finish(1);
    }
}