package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ActionIsIT extends WebBase {

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is present")
    public void isElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "submit_button").is().present());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is present")
    public void isElementPresentMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "submit_button", 0).is().present());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is present")
    public void isElementPresentNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name", 0).is().present());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "input_box").is().input());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "car_list", 0).is().input());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementTextAreaTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "textarea_input").is().input());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "disable_click", 0).is().input());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementInputNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name").is().input());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementSelectInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "input_box").is().select());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementSelectSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "car_list", 0).is().select());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementSelectTextAreaTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "textarea_input").is().select());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementSelectNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "disable_click", 0).is().select());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is input")
    public void isElementSelectNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name").is().select());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is a table")
    public void isElementTableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        assertTrue(app.newElement(Locator.ID, "table").is().table());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is a table")
    public void isElementTableNotTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "that", 0).is().table());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is a table")
    public void isElementTableNotPrintMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "that", 0).is().table());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is a table")
    public void isElementTableNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name").is().table());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is enabled")
    public void isElementEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "scroll_button").is().enabled());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is enabled")
    public void isElementEnabledMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "scroll_button", 0).is().enabled());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is enabled")
    public void isElementEnabledNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name", 0).is().enabled());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is checked")
    public void isElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        assertTrue(app.newElement(Locator.ID, "that").is().checked());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is checked")
    public void isElementCheckedNotTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "that", 0).is().checked());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is checked")
    public void isElementCheckedNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name").is().checked());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is displayed")
    public void isElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "that").is().displayed());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is displayed")
    public void isElementDisplayedMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "that", 0).is().displayed());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if an element is displayed")
    public void isElementDisplayedNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name", 0).is().displayed());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingSelectedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.newElement(Locator.ID, "car_list").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingSelectedMultipleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list_multiple").select(1);
        assertTrue(app.newElement(Locator.ID, "car_list_multiple", 0).is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingNotSelectedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "car_list_multiple").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from a checkbox")
    public void isSomethingCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        assertTrue(app.newElement(Locator.ID, "that", 0).is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from a checkbox")
    public void isSomethingNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "that").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from a non-existant element")
    public void isSomethingSelectedNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "non-existent-name").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from an div")
    public void isSomethingSelectedTextAreaTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "textarea_input").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check if something is selected from an div")
    public void isSomethingSelectedNotSelectOrInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.newElement(Locator.ID, "disable_click").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check the isAlertPresent method")
    public void isAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        assertTrue(app.is().alertPresent());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration negative test to check the isAlertPresent method")
    public void negativeIsAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.is().alertPresent());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check the isConfirmationPresent method")
    public void isConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        assertTrue(app.is().confirmationPresent());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration negative test to check the isConfirmationPresent method")
    public void negativeIsConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.is().confirmationPresent());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check the isPromptPresent method")
    public void isPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "prompt_button").click();
        assertTrue(app.is().promptPresent());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "A integration negative test to check the isPromptPresent method")
    public void negativeIsPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.is().promptPresent());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"}, description = "An integration test to check the isLocation method")
    public void isLocationTest(ITestContext test) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertTrue(app.is().location(getTestSite(this.getClass().getName(), test)));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "A negative integration test to check the isLocation method")
    public void negativeIsLocationTest(ITestContext test) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        assertFalse(app.is().location("Some Made Up URL"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration test to check the isTextPresentInSource method")
    public void isTextPresentInSourceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "submit_button").click();
        assertTrue(app.is().textPresentInSource("You're on the next page"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration negative test to check the isTextPresentInSource method")
    public void negativeIsTextPresentInSourceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "submit_button").click();
        assertFalse(app.is().textPresentInSource("Hello World"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "is"},
            description = "An integration negative test to check the isTextPresentInSource method")
    public void negativeIsTextPresentInSourceErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.is().textPresentInSource("Hello World");
        // verify no issues
        finish();
    }
}