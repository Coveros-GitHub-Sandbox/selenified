package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class VerifyStateIT extends WebBase {

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the checkElementChecked method")
    public void checkElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").click();
        app.newElement(Locator.ID, "this").verifyState().checked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this", 0).verifyState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the checkElementDisplayed method")
    public void checkElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementDisplayed method")
    public void negativeCheckElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).verifyState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementDisplayed method")
    public void negativeCheckElementDisplayedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the checkElementEditable method")
    public void checkElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").verifyState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).verifyState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the checkElementNotChecked method")
    public void checkElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").verifyState().notChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementNotChecked method")
    public void negativeCheckElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        app.newElement(Locator.ID, "that", 0).verifyState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementNotChecked method")
    public void negativeCheckElementNotCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the checkElementNotDisplayed method")
    public void checkElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyState().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementNotDisplayed method")
    public void negativeCheckElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button", 0).verifyState().notDisplayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the checkElementNotEditable method")
    public void checkElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"}, description = "An integration test to check the present method")
    public void checkElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyState().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementPresentNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().present();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "nocheck").verifyState().notPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentExistsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyState().notPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotDisplayedNotExistsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().notDisplayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"}, description = "An integration test to editabled method")
    public void checkElementEditabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").verifyState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to editabled method")
    public void checkElementEditabledNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").verifyState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to editabled method")
    public void checkElementEditabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").verifyState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"}, description = "An integration test to notEditabled method")
    public void checkElementNotEditabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").verifyState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to notEditabled method")
    public void checkElementNotEditabledEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").verifyState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to notEditabled method")
    public void checkElementNotEditabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").verifyState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"}, description = "An integration test to check the enabled method")
    public void checkElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").verifyState().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the enabled method")
    public void negativeCheckElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).verifyState().enabled();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the enabled method")
    public void negativeCheckElementEnabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyState().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the enabled method")
    public void negativeCheckElementEnabledNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyState().enabled();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration test to check the notEnabled method")
    public void checkElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").verifyState().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the notEnabled method")
    public void negativeCheckElementNotEnabledNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyState().notEnabled();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "state"},
            description = "An integration negative test to check the notEnabled method")
    public void negativeCheckElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyState().notEnabled();
        // verify 1 issue
        finish(1);
    }
}