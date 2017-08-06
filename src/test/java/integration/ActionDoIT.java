package integration;

import java.net.MalformedURLException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Assert;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class ActionDoIT extends TestBase {

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

    @DataProvider(name = "car list options", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][] { new Object[] { 0, "Volvo" }, new Object[] { 1, "Saab" },
                new Object[] { 2, "Mercedes" } };
    }

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetItems() {
        return new Object[][] { new Object[] { "Volvo" }, new Object[] { "Saab" }, new Object[] { "Mercedes" } };
    }

    @Test(groups = { "integration", "actions", "do",
            "virtual" }, description = "An integration negative test to check the goToURL method")
    public void killDriverErrorTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.killDriver();
        actions.killDriver();
        // verify no issues
        finish();
    }

    @Test(expectedExceptions = NullPointerException.class, groups = { "integration", "actions", "do",
            "virtual" }, description = "An integration test to verify we can't define an element with a bad locator")
    public void badLocatorTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(null, "element");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "url",
            "virtual" }, description = "An integration test to check the goToURL method")
    public void goToURLTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.page().goToURL("https://www.google.com/");
        asserts.compareURL("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "url",
            "virtual" }, description = "An integration negative test to check the goToURL method")
    public void negativeGoToURLTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.page().goToURL("https://www.yahoo.com/");
        asserts.compareURL("https://www.google.com/");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "url",
            "virtual" }, description = "An integration negative test to check the goToURL method")
    public void negativeInvalidGoToURLTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.page().goToURL("https:///");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the acceptAlert method")
    public void acceptAlertTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.CLASSNAME, "click");
        actions.click(Locator.CSS, "input#alert_button");
        actions.acceptAlert();
        asserts.alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the acceptAlert method")
    public void negativeAcceptAlertTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.acceptAlert();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the acceptConfirmation method")
    public void acceptConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.CSS, "input#confirm_button");
        actions.acceptConfirmation();
        asserts.confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the acceptConfirmation method")
    public void negativeAcceptConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.acceptConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the dismissConfirmation method")
    public void dismissConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.CSS, "input#confirm_button");
        actions.dismissConfirmation();
        asserts.confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the dismissConfirmation method")
    public void negativeDismissConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.dismissConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the acceptPrompt method")
    public void acceptPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.CSS, "input#prompt_button");
        actions.acceptPrompt();
        asserts.promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the acceptPrompt method")
    public void negativeAcceptPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.acceptPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the dismissPrompt method")
    public void dismissPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.CSS, "input#prompt_button");
        actions.dismissPrompt();
        asserts.confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the dismissPrompt method")
    public void negativeDismissPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.dismissPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the typePrompt method")
    public void typePromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.CSS, "input#prompt_button");
        actions.typeIntoPrompt("yes!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the typePrompt method")
    public void negativeTypePromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.typeIntoPrompt("yes!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "click" }, description = "An integration test to check the click method")
    public void clickTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.LINKTEXT, "I'M A LINK");
        asserts.confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "click",
            "do" }, description = "An integration negative test to check the click method")
    public void clickDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.CSS, "input#alert_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "click",
            "virtual" }, description = "An integration negative test to check the click method")
    public void clickNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "non-existent-element"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "click",
            "virtual" }, description = "An integration negative test to check the click method")
    public void clickHiddenTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "click" }, description = "An integration negative test to check the click method")
    public void clickUnderlayTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "underlay_span"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "submit" }, description = "An integration test to check the submit method")
    public void submitTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.submit(Locator.ID, "submit_button");
        asserts.textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "submit",
            "do" }, description = "An integration negative test to check the submit method")
    public void submitDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.submit(new Element(Locator.CSS, "input#alert_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "submit",
            "virtual" }, description = "An integration negative test to check the submit method")
    public void submitNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.submit(new Element(Locator.ID, "non-existent-element"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "submit",
            "virtual" }, description = "An integration negative test to check the submit method")
    public void submitHiddenTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.submit(new Element(Locator.ID, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "submit",
            "virtual" }, description = "An integration negative test to check the submit method")
    public void submitNonFormTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.submit(new Element(Locator.ID, "scroll_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "hover" }, description = "An integration test to check the hover method")
    public void hoverTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.hover(Locator.ID, "hover_over_me");
        asserts.state().displayed(new Element(Locator.ID, "displayed_when_hovered"));
        // verify 2 issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "hover" }, description = "An integration test to check the hover method")
    public void hoverOffTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.hover(Locator.ID, "hover_over_me");
        actions.hover(Locator.ID, "scroll_button");
        asserts.state().notDisplayed(new Element(Locator.ID, "displayed_when_hovered"));
        // verify 2 issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "hover",
            "virtual" }, description = "An integration negative test to check the hover method")
    public void hoverNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.hover(new Element(Locator.ID, "non-existent-element"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "hover",
            "virtual" }, description = "An integration negative test to check the hover method")
    public void hoverHiddenTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.hover(new Element(Locator.ID, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "blur" }, description = "An integration test to check the blur method")
    public void blurTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.blur(Locator.ID, "input_box");
        asserts.alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "blur",
            "virtual" }, description = "An integration negative test to check the blur method")
    public void blurNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.blur(new Element(Locator.ID, "non-existent-element"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "blur",
            "do" }, description = "An integration negative test to check the blur method")
    public void blurDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.blur(new Element(Locator.CSS, "input#alert_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "blur" }, description = "An integration negative test to check the blur method")
    public void blurNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.blur(new Element(Locator.ID, "transparent_input"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "blur",
            "virtual" }, description = "An integration negative test to check the blur method")
    public void blurNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.blur(new Element(Locator.CLASSNAME, "click"));
        // verify 2 issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration test to check the type method")
    public void typeInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "input_box", "This is a test");
        asserts.equals().value(Locator.ID, "input_box", "This is a test");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration test to check the type method")
    public void typeTextAreaTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "textarea_input", " With More Text");
        asserts.equals().value(Locator.ID, "textarea_input", "A Pretty Text Area With More Text");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeCheckboxTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "this", " ");
        asserts.state().checked(Locator.ID, "this");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "car_list", "A");
        asserts.equals().selectedValue(Locator.ID, "car_list", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.TAGNAME, "non-existent-element"), "This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "type",
            "do" }, description = "An integration negative test to check the type method")
    public void typeDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.CSS, "input#alert_button"), "This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.ID, "transparent_input"), "This is a test");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotVisible2Test() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.ID, "hidden_input"), "This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.CLASSNAME, "click"), "This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "input_box", Keys.TAB);
        asserts.alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysTextAreaTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "textarea_input", Keys.BACK_SPACE);
        asserts.equals().value(Locator.ID, "textarea_input", "A Pretty Text Are");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysCheckboxTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "this", Keys.SPACE);
        asserts.state().checked(Locator.ID, "this");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "car_list", Keys.DOWN);
        asserts.equals().selectedValue(Locator.ID, "car_list", "saab");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.ID, "non-existent-element"), Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "type",
            "do" }, description = "An integration negative test to check the type method")
    public void typeKeysDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.CSS, "input#alert_button"), Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.ID, "transparent_input"), Keys.SPACE);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotVisible2Test() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.ID, "hidden_input"), Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(new Element(Locator.CLASSNAME, "click"), Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration test to check the clear method")
    public void clearInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locator.ID, "input_box", "Text");
        asserts.equals().value(Locator.ID, "input_box", "Text");
        actions.clear(Locator.ID, "input_box");
        asserts.equals().value(Locator.ID, "input_box", "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration test to check the clear method")
    public void clearTextAreaTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.equals().value(Locator.ID, "textarea_input", "A Pretty Text Area");
        actions.clear(Locator.ID, "textarea_input");
        asserts.equals().value(Locator.ID, "textarea_input", "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "clear" }, description = "An integration test to check the clear method")
    public void clearCheckboxTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.clear(new Element(Locator.ID, "this"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "clear" }, description = "An integration test to check the clear method")
    public void clearSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.clear(new Element(Locator.ID, "car_list"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration negative test to check the clear method")
    public void clearNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.clear(new Element(Locator.ID, "non-existent-element"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "clear",
            "do" }, description = "An integration negative test to check the clear method")
    public void clearDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.clear(new Element(Locator.CSS, "input#alert_button"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration negative test to check the clear method")
    public void clearNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.clear(new Element(Locator.ID, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration negative test to check the clear method")
    public void clearNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.clear(new Element(Locator.CLASSNAME, "click"));
        // verify 2 issues
        finish(1);
    }

    @Test(dataProvider = "car list options", groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration test using a data provider to perform searches")
    public void selectValueTest(int listItem, String listValue) {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(Locator.ID, "car_list", listItem);
        asserts.equals().selectedOption(Locator.ID, "car_list", listValue);
        // close out the test
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectBadValueTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.ID, "car_list"), 9);
        // verify 2 issues
        finish(1);
    }

    @Test(dataProvider = "car list items", groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration test using a data provider to perform searches")
    public void selectTest(String listItem) {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(Locator.ID, "car_list", listItem);
        asserts.equals().selectedOption(Locator.ID, "car_list", listItem);
        // close out the test
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectBadOptionTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.ID, "car_list"), "option");
        // verify 2 issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.PARTIALLINKTEXT, "non-existent-element"), "option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "select",
            "do" }, description = "An integration negative test to check the select method")
    public void selectDisabledTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.CSS, "input#alert_button"), "option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.ID, "hidden_div"), "option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.CLASSNAME, "click"), "option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(new Element(Locator.ID, "scroll_button"), "option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "move",
            "virtual" }, description = "An integration test to check the move method")
    public void moveTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(Locator.ID, "table_no_header");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "move",
            "virtual" }, description = "An integration negative test to check the move method")
    public void moveNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(new Element(Locator.ID, "non-existent-element"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "move",
            "virtual" }, description = "An integration negative test to check the move method")
    public void moveNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(new Element(Locator.ID, "hidden_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveOffscreenTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(new Element(Locator.ID, "offscreen_div"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration test to check the move method")
    public void moveAtTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(Locator.ID, "table_no_header", (long) 10);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveAtNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(new Element(Locator.ID, "non-existent-element"), (long) 10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveAtNotVisibleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(new Element(Locator.ID, "hidden_div"), (long) 10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveAtOffscreenTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.move(new Element(Locator.ID, "offscreen_div"), (long) -10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "scroll" }, description = "An integration test to check the scroll method")
    public void scrollTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.NAME, "scroll_button");
        actions.page().scroll(50);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "scroll",
            "do" }, description = "An integration negative test to check the scroll method")
    public void negativeScrollTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.page().scroll(500);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "screenshot",
            "do" }, description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotFirefoxLocalTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the page
        Action actions = new Action(Browser.FIREFOX, new DesiredCapabilities(), null);
        System.setProperty("hubAddress", "LOCAL");
        // perform some actions
        actions.page().takeScreenshot("somefile");
        actions.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "screenshot",
            "do" }, description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotFirefoxHubTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the page
        Action actions = new Action(Browser.FIREFOX, new DesiredCapabilities(), null);
        System.setProperty("hubAddress", "HUB");
        // perform some actions
        actions.page().takeScreenshot("somefile");
        actions.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "screenshot",
            "do" }, description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotHtmlUnitTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the page
        Action actions = new Action(Browser.HTMLUNIT, new DesiredCapabilities(), null);
        // perform some actions
        actions.page().takeScreenshot("somefile");
        actions.killDriver();
        // verify no issues
        finish();
    }
}
