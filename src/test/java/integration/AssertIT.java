package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class AssertIT extends WebBase {

    @Test(groups = {"integration", "assert"}, description = "An integration test to check the url")
    public void compareUrlTest(ITestContext test) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().urlEquals(getTestSite(this.getClass().getName(), test));
    }

    @Test(groups = {"integration", "assert"}, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleEquals("Selenified Test Page");
    }

    @Test(groups = {"integration", "assert"}, description = "An integration test to check a title")
    public void compareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleMatches("Selenified(.*)");
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertPresent();
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationPresent();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertNotPresent();
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertEquals("Enabled!");
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertMatches("([A-Z])\\w+!");
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationEquals("Enabled!");
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationMatches("[E|e]nabled!");
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationNotPresent();
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptEquals("Enabled!");
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptMatches("[E|e]nabled!");
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptPresent();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptNotPresent();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textNotPresent("No such text on the app");
    }

    @Test(groups = {"integration", "assert"}, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textPresent("Click me to Disable/Enable a html button");
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieEquals("cookie", "cookietest");
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieMatches("cookie", "([a-z]*)");
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieNotExists("wrongcookie");
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"},
            description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("cookie");
    }
}