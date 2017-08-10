package integration;

import java.net.MalformedURLException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class ActionDoIT extends Selenified {

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
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.killDriver();
        // verify no issues
        finish();
    }

    @Test(expectedExceptions = NullPointerException.class, groups = { "integration", "actions", "do",
            "virtual" }, description = "An integration test to verify we can't define an element with a bad locator")
    public void badLocatorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(null, "element").click();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "url",
            "virtual" }, description = "An integration test to check the goToURL method")
    public void goToURLTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.goToURL("https://www.google.com/");
        page.urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "url",
            "virtual" }, description = "An integration negative test to check the goToURL method")
    public void negativeGoToURLTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.goToURL("https://www.yahoo.com/");
        page.urlEquals("https://www.google.com/");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "url",
            "virtual" }, description = "An integration negative test to check the goToURL method")
    public void negativeInvalidGoToURLTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.goToURL("https:///");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the acceptAlert method")
    public void acceptAlertTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CLASSNAME, "click").click();
        page.newElement(Locator.CSS, "input#alert_button").click();
        page.acceptAlert();
        page.alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the acceptAlert method")
    public void negativeAcceptAlertTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.acceptAlert();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the acceptConfirmation method")
    public void acceptConfirmationTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#confirm_button").click();
        page.acceptConfirmation();
        page.confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the acceptConfirmation method")
    public void negativeAcceptConfirmationTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.acceptConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the dismissConfirmation method")
    public void dismissConfirmationTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#confirm_button").click();
        page.dismissConfirmation();
        page.confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the dismissConfirmation method")
    public void negativeDismissConfirmationTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.dismissConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the acceptPrompt method")
    public void acceptPromptTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#prompt_button").click();
        page.acceptPrompt();
        page.promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the acceptPrompt method")
    public void negativeAcceptPromptTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.acceptPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the dismissPrompt method")
    public void dismissPromptTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#prompt_button").click();
        page.dismissPrompt();
        page.confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the dismissPrompt method")
    public void negativeDismissPromptTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.dismissPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration test to check the typePrompt method")
    public void typePromptTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#prompt_button").click();
        page.typeIntoPrompt("yes!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "alert",
            "do" }, description = "An integration negative test to check the typePrompt method")
    public void negativeTypePromptTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.typeIntoPrompt("yes!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "click" }, description = "An integration test to check the click method")
    public void clickTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.LINKTEXT, "I'M A LINK").click();
        page.confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "click",
            "do" }, description = "An integration negative test to check the click method")
    public void clickDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "click",
            "virtual" }, description = "An integration negative test to check the click method")
    public void clickNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "click",
            "virtual" }, description = "An integration negative test to check the click method")
    public void clickHiddenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "click" }, description = "An integration negative test to check the click method")
    public void clickUnderlayTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "underlay_span").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "submit" }, description = "An integration test to check the submit method")
    public void submitTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "submit_button").submit();
        page.textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "submit",
            "do" }, description = "An integration negative test to check the submit method")
    public void submitDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "submit",
            "virtual" }, description = "An integration negative test to check the submit method")
    public void submitNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "submit",
            "virtual" }, description = "An integration negative test to check the submit method")
    public void submitHiddenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "submit",
            "virtual" }, description = "An integration negative test to check the submit method")
    public void submitNonFormTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "scroll_button").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "hover" }, description = "An integration test to check the hover method")
    public void hoverTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hover_over_me").hover();
        page.newElement(Locator.ID, "displayed_when_hovered").assertState().displayed();
        // verify 2 issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "hover" }, description = "An integration test to check the hover method")
    public void hoverOffTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hover_over_me").hover();
        page.newElement(Locator.ID, "scroll_button").hover();
        page.newElement(Locator.ID, "displayed_when_hovered").assertState().notDisplayed();
        // verify 2 issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "hover",
            "virtual" }, description = "An integration negative test to check the hover method")
    public void hoverNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").hover();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "hover",
            "virtual" }, description = "An integration negative test to check the hover method")
    public void hoverHiddenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").hover();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "blur" }, description = "An integration test to check the blur method")
    public void blurTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "input_box").blur();
        page.alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "blur",
            "virtual" }, description = "An integration negative test to check the blur method")
    public void blurNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "blur",
            "do" }, description = "An integration negative test to check the blur method")
    public void blurDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "blur" }, description = "An integration negative test to check the blur method")
    public void blurNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "transparent_input").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "blur",
            "virtual" }, description = "An integration negative test to check the blur method")
    public void blurNotInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CLASSNAME, "click").blur();
        // verify 2 issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration test to check the type method")
    public void typeInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "input_box").type("This is a test");
        page.newElement(Locator.ID, "input_box").assertEquals().value("This is a test");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration test to check the type method")
    public void typeTextAreaTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "textarea_input").type(" With More Text");
        page.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Area With More Text");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeCheckboxTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "this").type(" ");
        page.newElement(Locator.ID, "this").assertState().checked();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").type("A");
        page.newElement(Locator.ID, "car_list").assertEquals().selectedValue("audi");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.TAGNAME, "non-existent-element").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "type",
            "do" }, description = "An integration negative test to check the type method")
    public void typeDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "transparent_input").type("This is a test");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotVisible2Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_input").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeNotInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CLASSNAME, "click").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "input_box", 0).type(Keys.TAB);
        page.alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysTextAreaTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "textarea_input").type(Keys.BACK_SPACE);
        page.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Are");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysCheckboxTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "this").type(Keys.SPACE);
        page.newElement(Locator.ID, "this").assertState().checked();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "type" }, description = "An integration test to check the type method")
    public void typeKeysSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").type(Keys.DOWN);
        page.newElement(Locator.ID, "car_list").assertEquals().selectedValue("saab");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "type",
            "do" }, description = "An integration negative test to check the type method")
    public void typeKeysDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "transparent_input").type(Keys.SPACE);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotVisible2Test() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_input").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "type",
            "virtual" }, description = "An integration negative test to check the type method")
    public void typeKeysNotInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CLASSNAME, "click").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration test to check the clear method")
    public void clearInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "input_box").type("Text");
        page.newElement(Locator.ID, "input_box").assertEquals().value("Text");
        page.newElement(Locator.ID, "input_box").clear();
        page.newElement(Locator.ID, "input_box").assertEquals().value("");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration test to check the clear method")
    public void clearTextAreaTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Area");
        page.newElement(Locator.ID, "textarea_input").clear();
        page.newElement(Locator.ID, "textarea_input").assertEquals().value("");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "clear" }, description = "An integration test to check the clear method")
    public void clearCheckboxTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "this").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "clear" }, description = "An integration test to check the clear method")
    public void clearSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration negative test to check the clear method")
    public void clearNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "clear",
            "do" }, description = "An integration negative test to check the clear method")
    public void clearDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration negative test to check the clear method")
    public void clearNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "clear",
            "virtual" }, description = "An integration negative test to check the clear method")
    public void clearNotInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CLASSNAME, "click").clear();
        // verify 2 issues
        finish(1);
    }

    @Test(dataProvider = "car list options", groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration test using a data provider to perform searches")
    public void selectValueTest(int listItem, String listValue) {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").select(listItem);
        page.newElement(Locator.ID, "car_list").assertEquals().selectedOption(listValue);
        // close out the test
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectBadValueTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").select(9);
        // verify 2 issues
        finish(1);
    }

    @Test(dataProvider = "car list items", groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration test using a data provider to perform searches")
    public void selectTest(String listItem) {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").select(listItem);
        page.newElement(Locator.ID, "car_list").assertEquals().selectedOption(listItem);
        // close out the test
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectBadOptionTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list").select("option");
        // verify 2 issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.PARTIALLINKTEXT, "non-existent-element").select("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "select",
            "do" }, description = "An integration negative test to check the select method")
    public void selectDisabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CSS, "input#alert_button").select("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").select("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.CLASSNAME, "click").select("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "select",
            "virtual" }, description = "An integration negative test to check the select method")
    public void selectNotSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "scroll_button").select("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "move",
            "virtual" }, description = "An integration test to check the move method")
    public void moveTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "table_no_header").move();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do", "move",
            "virtual" }, description = "An integration negative test to check the move method")
    public void moveNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do", "move",
            "virtual" }, description = "An integration negative test to check the move method")
    public void moveNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveOffscreenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "offscreen_div").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration test to check the move method")
    public void moveAtTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "table_no_header").move(10);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveAtNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").move(10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveAtNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "hidden_div").move(10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "move" }, description = "An integration negative test to check the move method")
    public void moveAtOffscreenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "offscreen_div").move( -10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "do",
            "scroll" }, description = "An integration test to check the scroll method")
    public void scrollTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.NAME, "scroll_button").click();
        page.scroll(50);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "scroll",
            "do" }, description = "An integration negative test to check the scroll method")
    public void negativeScrollTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.scroll(500);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "screenshot",
            "do" }, description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotFirefoxLocalTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the page
        Page page = new Page(Browser.FIREFOX, new DesiredCapabilities(), null);
        System.setProperty("hubAddress", "LOCAL");
        // perform some actions
        page.takeScreenshot("somefile");
        page.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "screenshot",
            "do" }, description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotFirefoxHubTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the page
    	Page page = new Page(Browser.FIREFOX, new DesiredCapabilities(), null);
        System.setProperty("hubAddress", "HUB");
        // perform some actions
        page.takeScreenshot("somefile");
        page.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "screenshot",
            "do" }, description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotHtmlUnitTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the page
    	Page page = new Page(Browser.HTMLUNIT, new DesiredCapabilities(), null);
        // perform some actions
        page.takeScreenshot("somefile");
        page.killDriver();
        // verify no issues
        finish();
    }
}