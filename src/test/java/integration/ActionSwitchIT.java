package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ActionSwitchIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://34.233.135.10/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectFrameIntTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectFrame(0);
        app.newElement(Locator.ID, "message").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectFrameIntNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectFrame(2);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectFrameNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectFrame("some_frame");
        app.newElement(Locator.ID, "message").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectFrameNameNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectFrame("some_non_existent_frame");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectFrameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.newElement(Locator.ID, "some_frame").selectFrame();
        app.newElement(Locator.ID, "message").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame", "browser"},
            description = "An integration test to check the frame method")
    public void selectFrameAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "some_frame").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration negative test to check the frame method")
    public void selectFrameNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration negative test to check the frame method")
    public void selectFrameNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "some_other_frame").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration negative test to check the frame method")
    public void selectFrameNotFrameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button").selectFrame();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectMainWindowTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.selectFrame("some_frame");
        app.newElement(Locator.ID, "message").assertState().displayed();
        app.selectMainWindow();
        app.newElement(Locator.ID, "message").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectMainWindowNoFrameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectMainWindow();
        app.newElement(Locator.ID, "message").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration negative test to check the frame method")
    public void selectMainWindowBadDriverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.selectMainWindow();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectParentFrameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.selectFrame("some_frame");
        app.newElement(Locator.ID, "message").assertState().displayed();
        app.selectParentFrame();
        app.newElement(Locator.ID, "message").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration test to check the frame method")
    public void selectParentFrameNoFrameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectParentFrame();
        app.newElement(Locator.ID, "message").assertState().notPresent();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "frame"},
            description = "An integration negative test to check the frame method")
    public void selectParentFrameBadDriverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.selectParentFrame();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "tab", "browser"},
            description = "An integration test to check the openWindow method")
    public void openWindowTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.openNewWindow("https://www.google.com/");
        app.azzert().urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "tab", "browser"},
            description = "An integration negative test to check the openWindow method")
    public void openWindowBadDriverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.openNewWindow("https://www.google.com/");
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "window"},
            description = "An integration test to check the switch method")
    public void switchToNewWindowTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "new_window").click();
        app.switchToNewWindow();
        app.azzert().textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "window"},
            description = "An integration negative test to check the switch method")
    public void switchToNewWindowBadDriverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "new_window").click();
        app.killDriver();
        app.switchToNewWindow();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "window", "browser"},
            description = "An integration test to check the switch method")
    public void switchToParentWindowTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "new_window").click();
        app.switchToNewWindow();
        app.azzert().textPresent("You're on the next page");
        app.switchToParentWindow();
        app.azzert().textNotPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "window", "browser"},
            description = "An integration negative test to check the switch method")
    public void switchToParentWindowNoParentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.switchToParentWindow();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "window", "browser"},
            description = "An integration negative test to check the switch method")
    public void switchToParentWindowBadDriverTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "new_window").click();
        app.switchToNewWindow();
        app.azzert().textPresent("You're on the next page");
        app.killDriver();
        app.switchToParentWindow();
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "window"},
            description = "An integration test to check the switch method")
    public void closeCurrentWindowTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "new_window").click();
        app.switchToNewWindow();
        app.azzert().textPresent("You're on the next page");
        app.closeCurrentWindow();
        app.azzert().textNotPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch", "window"},
            description = "An integration test to check the switch method")
    public void closeCurrentWindowNoWindowTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "new_window").click();
        app.switchToNewWindow();
        app.azzert().textPresent("You're on the next page");
        app.closeCurrentWindow();
        app.closeCurrentWindow();
        // verify 1 issue
        finish(1);
    }
}