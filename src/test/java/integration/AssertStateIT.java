package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AssertStateIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "file://" + System.getProperty("user.dir") + "/public/index.html");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = {"integration", "asserts", "state"}, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleEquals("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
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

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this", 0).assertState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().checked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementDisplayed method")
    public void checkElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayed method")
    public void negativeCheckElementDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).assertState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayed method")
    public void negativeCheckElementDisplayedHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertState().displayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementDisplayedAndChecked method")
    public void checkElementDisplayedAndCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").click();
        app.newElement(Locator.ID, "this").assertState().displayedAndChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndChecked method")
    public void negativeCheckElementDisplayedAndCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this", 0).assertState().displayedAndChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndChecked method")
    public void negativeCheckElementDisplayedAndCheckedNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().displayedAndChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndChecked method")
    public void negativeCheckElementDisplayedAndCheckedNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "displayed_when_hovered").assertState().displayedAndChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementDisplayedAndEditable method")
    public void checkElementDisplayedAndEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box").assertState().displayedAndEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndEditable method")
    public void negativeCheckElementDisplayedAndEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).assertState().displayedAndEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndEditable method")
    public void negativeCheckElementDisplayedAndEditableNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "displayed_when_hovered", 0).assertState().displayedAndEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementDisplayedAndNotEditable method")
    public void checkElementDisplayedAndNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().displayedAndNotEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
    public void negativeCheckElementDisplayedAndNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box", 0).assertState().displayedAndNotEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
    public void negativeCheckElementDisplayedAndNotEditableNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().displayedAndNotEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
    public void negativeCheckElementDisplayedAndNotEditableNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "displayed_when_hovered").assertState().displayedAndNotEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementDisplayedAndUnchecked method")
    public void checkElementDisplayedAndUncheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").assertState().displayedAndUnchecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
    public void negativeCheckElementDisplayedAndUncheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").click();
        app.newElement(Locator.ID, "this", 0).assertState().displayedAndUnchecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
    public void negativeCheckElementDisplayedAndUncheckedNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().displayedAndUnchecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
    public void negativeCheckElementDisplayedAndUncheckedNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "displayed_when_hovered").assertState().displayedAndUnchecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementEditable method")
    public void checkElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box").assertState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non_existent", 0).assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementNotChecked method")
    public void checkElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").assertState().notChecked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementNotChecked method")
    public void negativeCheckElementNotCheckedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").click();
        app.newElement(Locator.ID, "that", 0).assertState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementNotChecked method")
    public void negativeCheckElementNotCheckedNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notChecked();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementNotDisplayed method")
    public void checkElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertState().notDisplayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementNotDisplayed method")
    public void negativeCheckElementNotDisplayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button", 0).assertState().notDisplayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration test to check the checkElementNotEditable method")
    public void checkElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"}, description = "An integration test to check the present method")
    public void checkElementPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertState().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"}, description = "An integration test to check the present method")
    public void checkElementPresentDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertState().present();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementPresentNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().present();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "nocheck").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotPresentExistsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertState().notPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to check the present method")
    public void checkElementNotDisplayedNotExistsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertState().notDisplayed();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"}, description = "An integration test to editabled method")
    public void checkElementEditabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box").assertState().editable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to editabled method")
    public void checkElementEditabledNotEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to editabled method")
    public void checkElementEditabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").assertState().editable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"}, description = "An integration test to notEditabled method")
    public void checkElementNotEditabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertState().notEditable();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to notEditabled method")
    public void checkElementNotEditabledEditableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box").assertState().notEditable();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "state"},
            description = "An integration negative test to notEditabled method")
    public void checkElementNotEditabledNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").assertState().notEditable();
        // verify no issues
        finish();
    }
}