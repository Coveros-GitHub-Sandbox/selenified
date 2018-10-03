package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertIT extends WebBase {

    @Test(groups = {"integration", "asserts"}, description = "An integration test to check a title")
    public void compareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleEquals("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(groups = {"integration", "asserts"}, description = "An integration negative test to check a title")
    public void negativeCompareTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().titleEquals("Yahoo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
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

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckAlertPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
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

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkAlertPresent method")
    public void negativeCheckConfirmationPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkAlertPresent method")
    public void checkAlertNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkAlertPresent method")
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

    @Test(groups = {"integration", "asserts"}, description = "An integration test to check the checkAlert method")
    public void checkAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertPresent("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"}, description = "An integration test to check the checkAlert method")
    public void checkAlertRegexTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertPresent("([A-Z])\\w+!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertNoAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().alertPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkAlert method")
    public void negativeCheckAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().alertPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkConfirmation method")
    public void checkConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationPresent("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().confirmationPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkConfirmation method")
    public void negativeCheckConfirmationNoConfirmationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkConfirmationNotPresent method")
    public void checkConfirmationNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().confirmationNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkConfirmationNotPresent method")
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

    @Test(groups = {"integration", "asserts"}, description = "An integration test to check the checkPrompt method")
    public void checkPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptPresent("Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptNoPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptPresent("Enabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkPrompt method")
    public void negativeCheckPromptTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "disable_click").click();
        app.newElement(Locator.ID, "alert_button").click();
        app.azzert().promptPresent("Disabled!");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
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

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkPromptNotPresent method")
    public void negativeCheckPromptPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptPresent();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkPromptNotPresent method")
    public void checkPromptNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().promptNotPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkPromptNotPresent method")
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

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkTextNotVisible method")
    public void checkTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textNotPresent("No such text on the app");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkTextNotVisible method")
    public void negativeCheckTextNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textNotPresent("Click me to Disable/Enable a html button");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"}, description = "An integration test to check the checkTextVisible method")
    public void checkTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textPresent("Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkTextVisible method")
    public void negativeCheckTextVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().textPresent("No such text on the app");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"}, description = "An integration test to check the checkCookie method")
    public void checkCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("cookie", "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("cookie", "negativecookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkCookie method")
    public void negativeCheckCookieWrongNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("wrongcookie", "cookietest");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkCookieNotPresent method")
    public void checkCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieNotExists("wrongcookie");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkCookieNotPresent method")
    public void negativeCheckCookieNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieNotExists("cookie");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration test to check the checkCookiePresent method")
    public void checkCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("cookie");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts"},
            description = "An integration negative test to check the checkCookiePresent method")
    public void negativeCheckCookiePresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.azzert().cookieExists("wrongcookie");
        // verify 1 issue
        finish(1);
    }
}