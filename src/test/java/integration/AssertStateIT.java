package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertStateIT extends WebBase {

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the checkElementChecked method")
    public void checkElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").click();
        app.newElement(Locator.ID, "this").assertState().checked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementChecked method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this", 0).assertState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementChecked method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the checkElementDisplayed method")
    public void checkElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementDisplayed method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).assertState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementDisplayed method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementDisplayedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the checkElementEditable method")
    public void checkElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").assertState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementEditable method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementEditable method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementEditableNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementEditable method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementEditableNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the checkElementNotChecked method")
    public void checkElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").assertState().notChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementNotChecked method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        app.newElement(Locator.ID, "that", 0).assertState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementNotChecked method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the checkElementNotDisplayed method")
    public void checkElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertState().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementNotDisplayed method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button", 0).assertState().notDisplayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the checkElementNotEditable method")
    public void checkElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementNotEditable method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotEditableNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the checkElementNotEditable method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"}, description = "An integration test to check the present method")
    public void checkElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertState().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the present method", expectedExceptions = AssertionError.class)
    public void checkElementPresentNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().present();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the present method", expectedExceptions = AssertionError.class)
    public void checkElementNotPresentDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "nocheck").assertState().notPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the present method", expectedExceptions = AssertionError.class)
    public void checkElementNotPresentExistsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertState().notPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the present method", expectedExceptions = AssertionError.class)
    public void checkElementNotDisplayedNotExistsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notDisplayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"}, description = "An integration test to editabled method")
    public void checkElementEditabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").assertState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to editabled method", expectedExceptions = AssertionError.class)
    public void checkElementEditabledNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to editabled method", expectedExceptions = AssertionError.class)
    public void checkElementEditabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"}, description = "An integration test to notEditabled method")
    public void checkElementNotEditabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to notEditabled method", expectedExceptions = AssertionError.class)
    public void checkElementNotEditabledEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").assertState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to notEditabled method")
    public void checkElementNotEditabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").assertState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"}, description = "An integration test to check the enabled method")
    public void checkElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").assertState().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the enabled method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).assertState().enabled();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the enabled method")
    public void negativeCheckElementEnabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().enabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the enabled method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementEnabledNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertState().enabled();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration test to check the notEnabled method")
    public void checkElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertState().notEnabled();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the notEnabled method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotEnabledNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notEnabled();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "state"},
            description = "An integration negative test to check the notEnabled method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertState().notEnabled();
        // verify 1 issue
        finish(1);
    }
}