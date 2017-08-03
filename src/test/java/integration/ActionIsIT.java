package integration;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class ActionIsIT extends TestBase {

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

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is present")
    public void isElementPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().elementPresent(Locator.ID, "submit_button"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is present")
    public void isElementPresentNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementPresent(new Element(Locator.ID, "non-existent-name"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().elementInput(Locator.ID, "input_box"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().elementInput(Locator.ID, "car_list"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementTextAreaTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().elementInput(Locator.ID, "textarea_input"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementInput(new Element(Locator.ID, "disable_click"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementInputNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementInput(new Element(Locator.ID, "non-existent-name")));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is enabled")
    public void isElementEnabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().elementEnabled(Locator.ID, "scroll_button"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is enabled")
    public void isElementEnabledNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementEnabled(new Element(Locator.ID, "non-existent-name"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check if an element is checked")
    public void isElementCheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "that");
        Assert.assertTrue(actions.is().elementChecked(Locator.ID, "that"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check if an element is checked")
    public void isElementCheckedNotTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementChecked(new Element(Locator.ID, "that")));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is checked")
    public void isElementCheckedNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementChecked(new Element(Locator.ID, "non-existent-name"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is displayed")
    public void isElementDisplayedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().elementDisplayed(Locator.ID, "that"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is displayed")
    public void isElementDisplayedNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().elementDisplayed(new Element(Locator.ID, "non-existent-name"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingSelectedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertTrue(actions.is().somethingSelected(Locator.ID, "car_list"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingSelectedMultipleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.ID, "car_list_multiple"), 1);
        Assert.assertTrue(actions.is().somethingSelected(Locator.ID, "car_list_multiple", true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingNotSelectedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().somethingSelected(new Element(Locator.ID, "car_list_multiple"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a checkbox")
    public void isSomethingCheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "that"));
        Assert.assertTrue(actions.is().somethingSelected(Locator.ID, "that"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a checkbox")
    public void isSomethingNotCheckedTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().somethingSelected(new Element(Locator.ID, "that"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a non-existant element")
    public void isSomethingSelectedNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().somethingSelected(new Element(Locator.ID, "non-existent-name")));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from an div")
    public void isSomethingSelectedTextAreaTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().somethingSelected(new Element(Locator.ID, "textarea_input"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from an div")
    public void isSomethingSelectedNotSelectOrInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().somethingSelected(new Element(Locator.ID, "disable_click"), true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check the isAlertPresent method")
    public void isAlertPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        Assert.assertTrue(actions.is().alertPresent());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration negative test to check the isAlertPresent method")
    public void negativeIsAlertPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().alertPresent(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check the isConfirmationPresent method")
    public void isConfirmationPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "confirm_button");
        Assert.assertTrue(actions.is().confirmationPresent());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration negative test to check the isConfirmationPresent method")
    public void negativeIsConfirmationPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().confirmationPresent(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check the isPromptPresent method")
    public void isPromptPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "prompt_button");
        Assert.assertTrue(actions.is().promptPresent());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "A integration negative test to check the isPromptPresent method")
    public void negativeIsPromptPresentTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertFalse(actions.is().promptPresent(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check the isTextPresentInSource method")
    public void isTextPresentInSourceTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "submit_button"));
        Assert.assertTrue(actions.is().textPresentInSource("You're on the next page"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration negative test to check the isTextPresentInSource method")
    public void negativeIsTextPresentInSourceTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "submit_button"));
        Assert.assertFalse(actions.is().textPresentInSource("Hello World"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration negative test to check the isTextPresentInSource method")
    public void negativeIsTextPresentInSourceErrorTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.killDriver();
        actions.is().textPresentInSource("Hello World");
        // verify no issues
        finish();
    }
}