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
            description = "An integration test to check the checkElementNotEditable method")
    public void checkElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().notEditable();
        // verify no issues
        finish();
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
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notPresent();
        // verify no issues
        finish();
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
            description = "An integration test to check the notEnabled method")
    public void checkElementNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertState().notEnabled();
        // verify no issues
        finish();
    }
}