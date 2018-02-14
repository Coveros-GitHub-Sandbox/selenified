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
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration test to check the frame method")
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

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration test to check the frame method")
    public void selectFrameIntNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectFrame(2);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration test to check the frame method")
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

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration test to check the frame method")
    public void selectFrameNameNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "message").assertState().notPresent();
        app.selectFrame("some_non_existent_frame");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration test to check the frame method")
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

    @Test(groups = {"integration", "actions", "switch",
            "frame"}, description = "An integration test to check the frame method")
    public void selectFrameAlertTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "confirm_button").click();
        app.newElement(Locator.ID, "some_frame").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration negative test to check the frame method")
    public void selectFrameNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration negative test to check the frame method")
    public void selectFrameNotVisibleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "some_other_frame").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch", "frame",
            "virtual"}, description = "An integration negative test to check the frame method")
    public void selectFrameNotFrameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "scroll_button").selectFrame();
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch",
            "tab"}, description = "An integration test to check the openTab method")
    public void openTabTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.openTab("https://www.google.com/");
        app.azzert().urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch",
            "tab"}, description = "An integration test to check the openTab method")
    public void openEmptyTabTest(ITestContext context) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.openTab();
        app.azzert().urlEquals(getTestSite(this.getClass().getName(), context));
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "actions", "switch",
            "tab"}, description = "An integration test to check the switchTab method")
    public void switchTabTest(ITestContext context) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.openTab("https://www.google.com/");
        app.switchNextTab();
        String testSite = getTestSite(this.getClass().getName(), context);
        String site = testSite.endsWith("/") ? testSite : testSite + "/";
        app.azzert().urlEquals(site);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch",
            "tab"}, description = "An integration test to check the closeTab method")
    public void closeTabTest(ITestContext context) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.openTab("https://www.google.com/");
        app.closeTab();
        String testSite = getTestSite(this.getClass().getName(), context);
        String site = testSite.endsWith("/") ? testSite : testSite + "/";
        app.azzert().urlEquals(site);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch",
            "tab"}, description = "An integration test to check the closeTab method")
    public void closeFirstTabTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.openTab("https://www.google.com/");
        app.switchPreviousTab();
        app.closeTab();
        app.azzert().urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "actions", "switch",
            "window"}, description = "An integration test to check the switch method")
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

    @Test(groups = {"integration", "actions", "switch",
            "window"}, description = "An integration test to check the switch method")
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

    @Test(groups = {"integration", "actions", "switch",
            "window"}, description = "An integration test to check the switch method")
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

    @Test(groups = {"integration", "actions", "switch",
            "window"}, description = "An integration test to check the switch method")
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