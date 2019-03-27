package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidHTTPException;
import com.coveros.selenified.utilities.Property;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class AssertIT extends WebBase {

    @Test(groups = {"integration", "assert"}, description = "An integration test to check the url")
    public void compareUrlTest(ITestContext test) throws InvalidHTTPException {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().urlEquals(Property.getAppURL(this.getClass().getName(), test));
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "assert"}, description = "An integration negative test to check the url", expectedExceptions = AssertionError.class)
    public void negativeCompareUrlTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().urlEquals("Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"}, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleEquals("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "assert"}, description = "An integration negative test to check a title", expectedExceptions = AssertionError.class)
    public void negativeCompareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().titleEquals("Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"}, description = "An integration test to check a title")
    public void compareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleMatches("Selenified(.*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"}, description = "An integration negative test to check a title", expectedExceptions = AssertionError.class)
    public void negativeCompareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().titleMatches("([A-Z]*)");
        // verify 1 issue
        finish(1);
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
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkAlertPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertPresent();
        // verify 1 issue
        finish(1);
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
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkAlertPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkAlertPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckNotAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertMatches("([A-Z])\\w+!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration negative test to check the checkAlert method", expectedExceptions = AssertionError.class)
    public void checkAlertMatchesNegativeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertMatches("([a-z])\\w+!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration negative test to check the checkAlert method", expectedExceptions = AssertionError.class)
    public void checkAlertMatchesNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertMatches("([a-z])\\w+!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkAlert method", expectedExceptions = AssertionError.class)
    public void negativeCheckAlertNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration negative test to check the checkAlert method", expectedExceptions = AssertionError.class)
    public void negativeCheckAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertEquals("Disabled!");
        // verify 1 issue
        finish(1);
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
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration negative test to check the checkConfirmation method", expectedExceptions = AssertionError.class)
    public void negativeCheckConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkConfirmation method", expectedExceptions = AssertionError.class)
    public void negativeCheckConfirmationNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationEquals("Disabled!");
        // verify 1 issue
        finish(1);
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
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration negative test to check the checkConfirmation method", expectedExceptions = AssertionError.class)
    public void negativeCheckConfirmationMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationMatches("([a-z]+)!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkConfirmation method", expectedExceptions = AssertionError.class)
    public void negativeCheckConfirmationMatchesNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationMatches("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration negative test to check the checkConfirmationNotPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkPrompt method", expectedExceptions = AssertionError.class)
    public void negativeCheckPromptNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptEquals("Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration negative test to check the checkPrompt method", expectedExceptions = AssertionError.class)
    public void negativeCheckPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptMatches("[E|e]nabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkPrompt method", expectedExceptions = AssertionError.class)
    public void negativeCheckPromptMatchesNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptMatches("Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "alert"},
            description = "An integration negative test to check the checkPrompt method", expectedExceptions = AssertionError.class)
    public void negativeCheckMatchesPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptMatches("([a-z]+)!");
        // verify 1 issue
        finish(1);
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
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkPromptNotPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkPromptNotPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textNotPresent("No such text on the app");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkTextNotVisible method", expectedExceptions = AssertionError.class)
    public void negativeCheckTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textNotPresent("Click me to Disable/Enable a html button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"}, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textPresent("Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkTextVisible method", expectedExceptions = AssertionError.class)
    public void negativeCheckTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textPresent("No such text on the app");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieEquals("cookie", "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkCookie method", expectedExceptions = AssertionError.class)
    public void negativeCheckCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieEquals("cookie", "negativecookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkCookie method", expectedExceptions = AssertionError.class)
    public void negativeCheckCookieWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieEquals("wrongcookie", "cookietest");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieMatches("cookie", "([a-z]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkCookie method", expectedExceptions = AssertionError.class)
    public void negativeCheckCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieMatches("cookie", "[a-z]");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkCookie method", expectedExceptions = AssertionError.class)
    public void negativeCheckCookieMatchesWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieMatches("wrongcookie", "c[o]{2}kietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieNotExists("wrongcookie");
        // verify no issues
        finish();
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"},
            description = "An integration negative test to check the checkCookieNotPresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieNotExists("cookie");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "assert", "cookie", "no-edge"},
            description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("cookie");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert"},
            description = "An integration negative test to check the checkCookiePresent method", expectedExceptions = AssertionError.class)
    public void negativeCheckCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("wrongcookie");
        // verify 1 issue
        finish(1);
    }
}