package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Assert;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class AssertStateIT extends TestBase {

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

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform the verification
        asserts.compareTitle("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementChecked method")
    public void checkElementCheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "this");
        asserts.state().checked(Locator.ID, "this");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().checked(Locator.ID, "this", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().checked(Locator.ID, "non-existent-element");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementChecked method")
    public void negativeCheckElementCheckedDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().checked(Locator.ID, "check", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementDisplayed method")
    public void checkElementDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayed(Locator.ID, "alert_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayed method")
    public void negativeCheckElementDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayed(Locator.ID, "non_existent", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayed method")
    public void negativeCheckElementDisplayedHiddenTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayed(Locator.ID, "hidden_div");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementDisplayed method")
    public void checkElementDisplayedDelayed() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "delayed_display_button");
        asserts.state().displayed(Locator.ID, "delayed_hide_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementDisplayedAndChecked method")
    public void checkElementDisplayedAndCheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "this");
        asserts.state().displayedAndChecked(Locator.ID, "this");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndChecked method")
    public void negativeCheckElementDisplayedAndCheckedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndChecked(Locator.ID, "this", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndChecked method")
    public void negativeCheckElementDisplayedAndCheckedNotDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndChecked(Locator.ID, "non-existent-element");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementDisplayedAndChecked method")
    public void negativeCheckElementDisplayedAndCheckedDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndChecked(Locator.ID, "check");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementDisplayedAndEditable method")
    public void checkElementDisplayedAndEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndEditable(Locator.ID, "input_box");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndEditable method")
    public void negativeCheckElementDisplayedAndEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndEditable(Locator.ID, "non_existent", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementDisplayedAndEditable method")
    public void checkElementDisplayedAndEditableDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndEditable(Locator.ID, "check");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementDisplayedAndNotEditable method")
    public void checkElementDisplayedAndNotEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndNotEditable(Locator.ID, "table");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
    public void negativeCheckElementDisplayedAndNotEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndNotEditable(Locator.ID, "input_box", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
    public void negativeCheckElementDisplayedAndNotEditableNotDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndNotEditable(Locator.ID, "non-existent-element");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementDisplayedAndNotEditable method")
    public void negativeCheckElementDisplayedAndNotEditableDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndNotEditable(Locator.ID, "check");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementDisplayedAndUnchecked method")
    public void checkElementDisplayedAndUncheckedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndUnchecked(Locator.ID, "this");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
    public void negativeCheckElementDisplayedAndUncheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "this");
        asserts.state().displayedAndUnchecked(Locator.ID, "this", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
    public void negativeCheckElementDisplayedAndUncheckedNotDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndUnchecked(Locator.ID, "non-existent-element");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementDisplayedAndUnchecked method")
    public void checkElementDisplayedAndUncheckedDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().displayedAndUnchecked(Locator.ID, "check");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementEditable method")
    public void checkElementEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().editable(Locator.ID, "input_box");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().editable(Locator.ID, "non_existent", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableNotInputTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().editable(Locator.ID, "table");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementEditable method")
    public void negativeCheckElementEditableNotEnabledTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().editable(Locator.ID, "alert_button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementEditable method")
    public void checkElementEditableDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().editable(Locator.ID, "check");
        // verify 0 issue
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementNotChecked method")
    public void checkElementNotCheckedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notChecked(Locator.ID, "that");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementNotChecked method")
    public void negativeCheckElementNotCheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "that");
        asserts.state().notChecked(Locator.ID, "that", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementNotChecked method")
    public void negativeCheckElementNotCheckedNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notChecked(Locator.ID, "non-existent-element");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementNotChecked method")
    public void checkElementNotCheckedDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notChecked(Locator.ID, "check");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementNotDisplayed method")
    public void checkElementNotDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notDisplayed(Locator.ID, "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementNotDisplayed method")
    public void negativeCheckElementNotDisplayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notDisplayed(Locator.ID, "scroll_button", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementNotDisplayed method")
    public void checkElementNotDisplayedDelayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "delayed_display_button");
        actions.waitFor().elementDisplayed(Locator.ID, "delayed_hide_button");
        actions.click(Locator.ID, "delayed_hide_button");
        asserts.state().notDisplayed(Locator.ID, "delayed_hide_button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration test to check the checkElementNotEditable method")
    public void checkElementNotEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notEditable(Locator.ID, "table");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notEditable(Locator.ID, "that", 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "state",
            "virtual" }, description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableNotPresentTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notEditable(Locator.ID, "non-existent-element");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "state" }, description = "An integration negative test to check the checkElementNotEditable method")
    public void negativeCheckElementNotEditableDelayedTest() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.state().notEditable(Locator.ID, "check");
        // verify 1 issue
        finish(1);
    }
}