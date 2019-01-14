package integration;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.utilities.Point;
import com.coveros.selenified.utilities.Sauce;
import org.openqa.selenium.Keys;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ActionDoIT extends WebBase {

    @DataProvider(name = "car list options", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{new Object[]{0, "Volvo"}, new Object[]{1, "Saab"}, new Object[]{2, "Mercedes"}};
    }

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetItems() {
        return new Object[][]{new Object[]{"Volvo"}, new Object[]{"Saab"}, new Object[]{"Mercedes"}};
    }

    @Test(groups = {"integration", "action", "do"},
            description = "An integration negative test to check the goToURL method")
    public void killDriverErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.killDriver();
        // verify no issues
        finish();
    }

    @Test(expectedExceptions = NullPointerException.class, groups = {"integration", "action", "do"},
            description = "An integration test to verify we can't define an element with a bad locator")
    public void badLocatorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(null, "element").click();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "url"},
            description = "An integration test to check the goToURL method")
    public void goToURLTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.goToURL("https://www.google.com/");
        app.azzert().urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "url"},
            description = "An integration negative test to check the goToURL method")
    public void negativeGoToURLTest(ITestContext test) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.goToURL("https://www.bing.com/");
        app.azzert().urlEquals(getTestSite(this.getClass().getName(), test));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "url"},
            description = "An integration negative test to check the goToURL method")
    public void negativeInvalidGoToURLTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.goToURL("https:///");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration test to check the acceptAlert method")
    public void acceptAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").click();
        app.newElement(Locator.CSS, "input#alert_button").click();
        app.acceptAlert();
        app.azzert().alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration negative test to check the acceptAlert method")
    public void negativeAcceptAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.acceptAlert();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration test to check the acceptConfirmation method")
    public void acceptConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#confirm_button").click();
        app.acceptConfirmation();
        app.azzert().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration negative test to check the acceptConfirmation method")
    public void negativeAcceptConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.acceptConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration test to check the dismissConfirmation method")
    public void dismissConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#confirm_button").click();
        app.dismissConfirmation();
        app.azzert().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration negative test to check the dismissConfirmation method")
    public void negativeDismissConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.dismissConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration test to check the acceptPrompt method")
    public void acceptPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#prompt_button").click();
        app.acceptPrompt();
        app.azzert().promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration negative test to check the acceptPrompt method")
    public void negativeAcceptPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.acceptPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration test to check the dismissPrompt method")
    public void dismissPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#prompt_button").click();
        app.dismissPrompt();
        app.azzert().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration negative test to check the dismissPrompt method")
    public void negativeDismissPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.dismissPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration test to check the typePrompt method")
    public void typePromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#prompt_button").click();
        app.typeIntoPrompt("yes!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "alert", "do"},
            description = "An integration negative test to check the typePrompt method")
    public void negativeTypePromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.typeIntoPrompt("yes!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "click", "alert"},
            description = "An integration test to check the click method")
    public void clickTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.LINKTEXT, "I'M A LINK").click();
        app.azzert().confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "click", "browser", "alert"},
            description = "An integration negative test to check the click method")
    public void clickAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.LINKTEXT, "I'M A LINK").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "click", "do"},
            description = "An integration negative test to check the click method")
    public void clickDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "click"},
            description = "An integration negative test to check the click method")
    public void clickNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "click"},
            description = "An integration negative test to check the click method")
    public void clickHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "click", "browser"},
            description = "An integration negative test to check the click method")
    public void clickUnderlayTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "underlay_span").click();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "submit"},
            description = "An integration test to check the submit method")
    public void submitTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "submit_button").submit();
        app.azzert().textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "submit", "browser"},
            description = "An integration negative test to check the submit method")
    public void submitAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "submit_button").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "submit", "do"},
            description = "An integration negative test to check the submit method")
    public void submitDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "submit"},
            description = "An integration negative test to check the submit method")
    public void submitNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "submit"},
            description = "An integration negative test to check the submit method")
    public void submitHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "submit"},
            description = "An integration negative test to check the submit method")
    public void submitNonFormTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button").submit();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "hover"},
            description = "An integration test to check the hover method")
    public void hoverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").hover();
        app.newElement(Locator.ID, "displayed_when_hovered").assertState().displayed();
        // verify 2 issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "hover"},
            description = "An integration test to check the hover method")
    public void hoverOffTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").hover();
        app.newElement(Locator.ID, "scroll_button").hover();
        app.newElement(Locator.ID, "displayed_when_hovered").assertState().notDisplayed();
        // verify 2 issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "hover"},
            description = "An integration negative test to check the hover method")
    public void hoverNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").hover();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "hover"},
            description = "An integration negative test to check the hover method")
    public void hoverHiddenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").hover();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "hover", "browser", "alert"},
            description = "An integration negative test to check the hover method")
    public void hoverAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "hover_over_me").hover();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "blur", "browser"},
            description = "An integration test to check the blur method")
    public void blurTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box").blur();
        app.waitFor().alertPresent();
        app.azzert().alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "blur", "browser"},
            description = "An integration negative test to check the blur method")
    public void blurAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "input_box").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "blur"},
            description = "An integration negative test to check the blur method")
    public void blurNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "blur", "do"},
            description = "An integration negative test to check the blur method")
    public void blurDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "blur", "browser"},
            description = "An integration negative test to check the blur method")
    public void blurNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "transparent_input").blur();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "blur"},
            description = "An integration negative test to check the blur method")
    public void blurNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").blur();
        // verify 2 issues
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration test to check the type method")
    public void typeInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box").type("This is a test");
        app.newElement(Locator.ID, "input_box").assertEquals().value("This is a test");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration test to check the type method")
    public void typeTextAreaTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").type(" With More Text");
        app.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Area With More Text");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration test to check the type method")
    public void typeCheckboxTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        //TODO fix conditional logic
        app.newElement(Locator.ID, "this").type(" ");
//        if (app.getBrowser().getName() == BrowserName.CHROME) {  //test only applicable for Chrome
            app.newElement(Locator.ID, "this").assertState().checked();
//        }
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration test to check the type method")
    public void typeSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").type("A");
        app.newElement(Locator.ID, "car_list").assertEquals().selectedValue("audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "non-existent-element").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "type", "do"},
            description = "An integration negative test to check the type method")
    public void typeDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    // TODO fix this to make it useful across everywhere
    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        File file = new File("public/index.html");
        app.newElement(Locator.ID, "transparent_input").type(file.getAbsolutePath());
        // if on sauce, file won't exist, and an error will be thrown
        if (Sauce.isSauce()) {
            finish(1);
        } else {
            finish();
        }

    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeNotVisible2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_input").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration negative test to check the type method")
    public void typeAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "input_box").type("This is a test");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration test to check the type method")
    public void typeKeysInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "input_box", 0).type(Keys.TAB);
        app.azzert().alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration test to check the type method")
    public void typeKeysTextAreaTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").type(Keys.BACK_SPACE);
        app.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Are");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration test to check the type method")
    public void typeKeysCheckboxTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").type(Keys.SPACE);
        app.newElement(Locator.ID, "this").assertState().checked();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration test to check the type method")
    public void typeKeysSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").type(Keys.DOWN);
        app.newElement(Locator.ID, "car_list").assertEquals().selectedValue("saab");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeKeysNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "type", "do"},
            description = "An integration negative test to check the type method")
    public void typeKeysDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration negative test to check the type method")
    public void typeKeysNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "transparent_input").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeKeysNotVisible2Test() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_input").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type"},
            description = "An integration negative test to check the type method")
    public void typeKeysNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").type(Keys.SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "type", "browser"},
            description = "An integration negative test to check the type method")
    public void typeKeysAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "input_box").type(Keys.BACK_SPACE);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "clear"},
            description = "An integration test to check the clear method")
    public void clearInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_enable_button").click();
        app.wait(5.0);
        app.newElement(Locator.ID, "delayed_input").type("Text");
        app.newElement(Locator.ID, "delayed_input").assertEquals().value("Text");
        app.newElement(Locator.ID, "delayed_input").clear();
        app.newElement(Locator.ID, "delayed_input").assertEquals().value("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "clear"},
            description = "An integration test to check the clear method")
    public void clearTextAreaTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Area");
        app.newElement(Locator.ID, "textarea_input").clear();
        app.newElement(Locator.ID, "textarea_input").assertEquals().value("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "clear", "browser"},
            description = "An integration test to check the clear method")
    public void clearCheckboxTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "this").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "clear", "browser"},
            description = "An integration test to check the clear method")
    public void clearSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "clear"},
            description = "An integration negative test to check the clear method")
    public void clearNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "clear", "do"},
            description = "An integration negative test to check the clear method")
    public void clearDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "clear"},
            description = "An integration negative test to check the clear method")
    public void clearNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "clear"},
            description = "An integration negative test to check the clear method")
    public void clearNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").clear();
        // verify 2 issues
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "clear", "browser"},
            description = "An integration test to check the clear method")
    public void clearAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "input_box").clear();
        // verify 1 issue
        finish(1);
    }

    @Test(dataProvider = "car list options", groups = {"integration", "action", "do", "select"},
            description = "An integration test using a data provider to perform searches")
    public void selectValueTest(int listItem, String listValue) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").select(listItem);
        app.newElement(Locator.ID, "car_list").assertEquals().selectedOption(listValue);
        // close out the test
        finish();
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectBadValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").select(9);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select", "browser"},
            description = "An integration negative test to check the select method")
    public void selectValueAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "car_list").select(1);
        // verify 1 issue
        finish(1);
    }

    @Test(dataProvider = "car list items", groups = {"integration", "action", "do", "select"},
            description = "An integration test using a data provider to perform searches")
    public void selectOptionTest(String listItem) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").selectOption(listItem);
        app.newElement(Locator.ID, "car_list").assertEquals().selectedOption(listItem);
        // close out the test
        finish();
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectOptionBadOptionTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").selectOption("option");
        // verify 2 issues
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectOptionNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.PARTIALLINKTEXT, "non-existent-element").selectOption("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "select", "do"},
            description = "An integration negative test to check the select method")
    public void selectOptionDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").selectOption("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectOptionNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").selectOption("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectOptionNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").selectOption("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectOptionNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button").selectOption("option");
        // verify 1 issue
        finish(1);
    }

    @Test(dataProvider = "car list items", groups = {"integration", "action", "do", "select", "browser"},
            description = "An integration negative test using a data provider to perform searches")
    public void selectOptionAlertTest(String listItem) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "car_list").selectOption(listItem);
        // verify 1 issue
        finish(1);
    }

    @Test(dataProvider = "car list items", groups = {"integration", "action", "do", "select"},
            description = "An integration test using a data provider to perform searches")
    public void selectValueTest(String listItem) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").selectValue(listItem.toLowerCase());
        app.newElement(Locator.ID, "car_list").assertEquals().selectedValue(listItem.toLowerCase());
        // close out the test
        finish();
    }

    @Test(dataProvider = "car list items", groups = {"integration", "action", "do", "select", "browser"},
            description = "An integration test using a data provider to perform searches")
    public void selectValueAlertTest(String listItem) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "car_list").selectValue(listItem.toLowerCase());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectValueBadOptionTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").selectValue("option");
        // verify 2 issues
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectValueNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.PARTIALLINKTEXT, "non-existent-element").selectValue("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "select", "do"},
            description = "An integration negative test to check the select method")
    public void selectValueDisabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").selectValue("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectValueNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").selectValue("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").selectValue("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectValueNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button").selectValue("option");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "select"},
            description = "An integration negative test to check the select method")
    public void selectNotInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CLASSNAME, "click").select(1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move"},
            description = "An integration test to check the move method")
    public void moveTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").move();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "move", "browser"},
            description = "An integration test to check the move method")
    public void moveAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "table_no_header").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move"},
            description = "An integration negative test to check the move method")
    public void moveNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move"},
            description = "An integration negative test to check the move method")
    public void moveNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move", "browser"},
            description = "An integration negative test to check the move method")
    public void moveOffscreenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "offscreen_div").move();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move"},
            description = "An integration test to check the move method")
    public void moveAtTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table_no_header").move(10);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "move", "browser"},
            description = "An integration negative test to check the move method")
    public void moveAtAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "table_no_header").move(10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move"},
            description = "An integration negative test to check the move method")
    public void moveAtNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").move(10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move"},
            description = "An integration negative test to check the move method")
    public void moveAtNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").move(10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "move", "browser"},
            description = "An integration negative test to check the move method")
    public void moveAtOffscreenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "offscreen_div").move(-10);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "scroll", "browser"},
            description = "An integration test to check the scroll method")
    public void scrollTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "scroll_button").click();
        app.scroll(50);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "scroll", "do"},
            description = "An integration negative test to check the scroll method")
    public void negativeScrollTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.scroll(500);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "scroll", "do"},
            description = "An integration negative test to check the scroll method")
    public void scrollBadDriverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.scroll(50);
    }

    @Test(groups = {"integration", "action", "do", "draw", "browser"},
            description = "An integration negative test to check the draw method")
    public void drawTest() {
        List<Point<Integer, Integer>> points = new ArrayList<>();
        points.add(new Point<>(10, 10));
        points.add(new Point<>(100, 10));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "can").draw(points);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "draw", "browser"},
            description = "An integration test to check the draw method")
    public void drawTestNotCanvas() {
        List<Point<Integer, Integer>> points = new ArrayList<>();
        points.add(new Point<>(10, 10));
        points.add(new Point<>(100, 10));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "overlay_span").draw(points);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "do", "draw"},
            description = "An integration negative test to check the draw method")
    public void drawTestNoPoints() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.LINKTEXT, "I'M A LINK").draw(new ArrayList<>());
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "draw", "alert"},
            description = "An integration negative test to check the draw method")
    public void drawAlertTest() {
        List<Point<Integer, Integer>> points = new ArrayList<>();
        points.add(new Point<>(10, 10));
        points.add(new Point<>(100, 10));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.LINKTEXT, "I'M A LINK").draw(points);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "draw", "do"},
            description = "An integration negative test to check the draw method")
    public void drawDisabledTest() {
        List<Point<Integer, Integer>> points = new ArrayList<>();
        points.add(new Point<>(10, 10));
        points.add(new Point<>(100, 10));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.CSS, "input#alert_button").draw(points);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "draw"},
            description = "An integration negative test to check the draw method")
    public void drawNotExistTest() {
        List<Point<Integer, Integer>> points = new ArrayList<>();
        points.add(new Point<>(10, 10));
        points.add(new Point<>(100, 10));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").draw(points);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "do", "draw"},
            description = "An integration negative test to check the draw method")
    public void drawHiddenTest() {
        List<Point<Integer, Integer>> points = new ArrayList<>();
        points.add(new Point<>(10, 10));
        points.add(new Point<>(100, 10));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").draw(points);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "action", "screenshot", "do", "local"},
            description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotFirefoxLocalTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the app
        App app = new App(new Capabilities(new Browser("Firefox")), null);
        System.setProperty("hubAddress", "LOCAL");
        // perform some actions
        app.takeScreenshot("firefoxLocalScreenshot");
        new File("firefoxLocalScreenshot").delete();
        app.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "screenshot", "do", "local"},
            description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotFirefoxHubTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the app
        App app = new App(new Capabilities(new Browser("Firefox")), null);
        System.setProperty("hubAddress", "HUB");
        // perform some actions
        app.takeScreenshot("firefoxHubScreenshot");
        new File("firefoxHubScreenshot").delete();
        app.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "screenshot", "do", "local"},
            description = "An integration test to check the takeScreenshot method")
    public void takeScreenshotHtmlUnitTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the app
        App app = new App(new Capabilities(new Browser("HtmlUnit")), null);
        // perform some actions
        app.takeScreenshot("somefile");
        app.killDriver();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "action", "screenshot", "do", "local"},
            description = "An integration negative test to check the takeScreenshot method")
    public void takeScreenshotBadDriverTest() throws InvalidBrowserException, MalformedURLException {
        // use this object to manipulate the app
        App app = new App(new Capabilities(new Browser("Firefox")), null);
        // perform some actions
        app.killDriver();
        app.takeScreenshot("somefile");
        // verify no issues
        finish();
    }
}
