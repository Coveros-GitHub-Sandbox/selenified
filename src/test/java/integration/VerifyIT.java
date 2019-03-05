package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class VerifyIT extends WebBase {

    @Test(groups = {"integration", "verify"}, description = "An integration test to check the url")
    public void compareUrlTest(ITestContext test) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.verify().urlEquals(getTestSite(this.getClass().getName(), test));
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "verify"}, description = "An integration negative test to check the url")
    public void negativeCompareUrlTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().urlEquals("Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"}, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.verify().titleEquals("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "verify"}, description = "An integration negative test to check a title")
    public void negativeCompareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().titleEquals("Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"}, description = "An integration test to check a title")
    public void compareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.verify().titleMatches("Selenified(.*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"}, description = "An integration negative test to check a title")
    public void negativeCompareTitleMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().titleMatches("([A-Z]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().alertPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().alertPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().confirmationPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().confirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckNotAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().alertNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().alertEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "alert"}, description = "An integration test to check the checkAlert method")
    public void checkAlertMatchTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().alertMatches("([A-Z])\\w+!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "alert"}, description = "An integration negative test to check the checkAlert method")
    public void checkAlertMatchesNegativeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().alertMatches("([a-z])\\w+!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"}, description = "An integration negative test to check the checkAlert method")
    public void checkAlertMatchesNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().alertMatches("([a-z])\\w+!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().alertEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().alertEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().confirmationEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().confirmationEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().confirmationEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().confirmationMatches("[E|e]nabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().confirmationMatches("([a-z]+)!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationMatchesNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().confirmationMatches("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration negative test to check the checkConfirmationNotPresent method")
    public void negativeCheckConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().confirmationNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().promptEquals("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().promptEquals("Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().promptEquals("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().promptMatches("[E|e]nabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptMatchesNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().promptMatches("Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckMatchesPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().promptMatches("([a-z]+)!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "alert"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().promptPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().promptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.verify().promptNotPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().textNotPresent("No such text on the app");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkTextNotVisible method")
    public void negativeCheckTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().textNotPresent("Click me to Disable/Enable a html button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"}, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().textPresent("Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkTextVisible method")
    public void negativeCheckTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().textPresent("No such text on the app");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "verify", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieEquals("cookie", "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieEquals("cookie", "negativecookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieEquals("wrongcookie", "cookietest");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "verify", "cookie", "no-edge"}, description = "An integration test to check the checkCookie method")
    public void checkCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieMatches("cookie", "([a-z]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieMatchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieMatches("cookie", "[a-z]");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieMatchesWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieMatches("wrongcookie", "c[o]{2}kietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieNotExists("wrongcookie");
        // verify no issues
        finish();
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "verify", "cookie", "no-edge"},
            description = "An integration negative test to check the checkCookieNotPresent method")
    public void negativeCheckCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieNotExists("cookie");
        // verify 1 issue
        finish(1);
    }

    // skipping edge as retrieving cookies isn't working: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/
    @Test(groups = {"integration", "verify", "cookie", "no-edge"},
            description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieExists("cookie");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify"},
            description = "An integration negative test to check the checkCookiePresent method")
    public void negativeCheckCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.verify().cookieExists("wrongcookie");
        // verify 1 issue
        finish(1);
    }
}