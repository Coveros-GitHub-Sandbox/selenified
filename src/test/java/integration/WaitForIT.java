package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidHTTPException;
import com.coveros.selenified.utilities.Property;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class WaitForIT extends WebBase {

    @Test(groups = {"integration", "wait"}, description = "An integration test to check the url")
    public void compareUrlTest(ITestContext test) throws InvalidHTTPException {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.waitFor().urlEquals(Property.getAppURL(this.getClass().getName(), test));
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "wait"}, description = "An integration negative test to check the url")
    public void negativeCompareUrlTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().urlEquals(3, "Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"}, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.waitFor().titleEquals("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "wait"}, description = "An integration negative test to check a title")
    public void negativeCompareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().titleEquals(3, "Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"}, description = "An integration test to check a title")
    public void compareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.waitFor().titleMatches("Selenified(.*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"}, description = "An integration negative test to check a title")
    public void negativeCompareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().titleMatches(3, "([A-Z]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().alertPresent(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().confirmationPresent(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckNotAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertNotPresent(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertMatches("([A-Z])\\w+!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "alert"}, description = "An integration negative test to check the checkAlert method")
    public void checkAlertMatchesNegativeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertMatches(3, "([a-z])\\w+!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"}, description = "An integration negative test to check the checkAlert method")
    public void checkAlertMatchesNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().alertMatches("([a-z])\\w+!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().alertEquals(3, "Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertEquals(3, "Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().confirmationEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().confirmationEquals(3, "Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().confirmationEquals(3, "Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().confirmationMatches("[E|e]nabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().confirmationMatches(3, "([a-z]+)!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationMatchesNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().confirmationMatches(3, "Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration negative test to check the checkConfirmationNotPresent method")
    public void negativeCheckConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().confirmationNotPresent(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().promptEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().promptEquals(3, "Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().promptEquals(3, "Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().promptMatches("[E|e]nabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptMatchesNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().promptMatches(3, "Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckMatchesPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().promptMatches(3, "([a-z]+)!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().promptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().promptPresent(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().promptNotPresent(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textNotPresent("No such text on the app");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkTextNotVisible method")
    public void negativeCheckTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textNotPresent(3, "Click me to Disable/Enable a html button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"}, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textPresent("Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkTextVisible method")
    public void negativeCheckTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textPresent(3, "No such text on the app");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "wait", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieEquals("cookie", "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieEquals(3, "cookie", "negativecookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieEquals(3, "wrongcookie", "cookietest");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "wait", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieMatches("cookie", "([a-z]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieMatches(3, "cookie", "[a-z]");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieMatchesWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieMatches(3, "wrongcookie", "c[o]{2}kietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieNotExists("wrongcookie");
        // verify no issues
        finish();
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "wait", "cookie", "no-edge"},
            description = "An integration negative test to check the checkCookieNotPresent method")
    public void negativeCheckCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieNotExists(3, "cookie");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "wait", "cookie", "no-edge"},
            description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieExists("cookie");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the checkCookiePresent method")
    public void negativeCheckCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().cookieExists(3, "wrongcookie");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check changing the default wait method")
    public void setDefaultWaitAppNegativeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().changeDefaultWait(0.5);
        app.newElement(Locator.ID, "delayed_alert_button").click();
        app.waitFor().alertPresent();
        app.azzert().alertNotPresent();
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check changing the default wait method")
    public void setDefaultWaitAppTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_alert_button").click();
        app.waitFor().alertPresent();
        app.azzert().alertPresent();
    }

    @Test(groups = {"integration", "wait", "browser"},
            description = "An integration negative test to check the wait method")
    public void negativeWaitTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.wait(6.0);
        app.newElement(Locator.ID, "five_second_button").click();
        // verify 2 issues
        finish(2);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the wait method")
    public void negativeWaitErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Thread.currentThread().interrupt();
        app.wait(6.0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "location"},
            description = "An integration test to check the wait for location method")
    public void waitLocationTest(ITestContext context) throws InvalidHTTPException {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().urlEquals(Property.getAppURL(this.getClass().getName(), context));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "location"},
            description = "An integration negative test to check the wait for location method")
    public void negativeWaitLocationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().urlEquals("http://hellourl.io");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "title"},
            description = "An integration test to check the wait for title method")
    public void waitTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().titleEquals("Selenified Test Page");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "title"},
            description = "An integration negative test to check the wait for title method")
    public void negativeWaitTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().titleEquals("Selenium TST pg");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the wait for text present method")
    public void waitTextPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textPresent("President");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the wait for text present method")
    public void waitTextPresentFailTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textPresent(3, "President Roosevelt");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration test to check the wait for text not present method")
    public void waitTextNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textNotPresent("President Roosevelt");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the wait for text not present method")
    public void waitTextNotPresentFailTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().textNotPresent(3, "President");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the wait method")
    public void waitTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.wait(4.0);
        app.newElement(Locator.ID, "nocheck").assertState().notPresent();
        // verify no issues
        finish();
    }


    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the waitForPromptPresent method")
    public void waitForPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "prompt_button").click();
        app.waitFor().promptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "A integration negative test to check the waitForPromptPresent method")
    public void negativeWaitForPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().promptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the waitForConfirmationPresent method")
    public void waitForConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.waitFor().confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the waitForConfirmationPresent method")
    public void negativeWaitForConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().confirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "alert"},
            description = "An integration test to check the waitForAlertPresent method")
    public void waitForAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.waitFor().alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait"},
            description = "An integration negative test to check the waitForAlertPresent method")
    public void negativeWaitForAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.waitFor().alertPresent();
        // verify 1 issue
        finish(1);
    }
}