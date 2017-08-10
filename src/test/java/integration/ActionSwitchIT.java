package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class ActionSwitchIT extends Selenified {

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

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameIntTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "message").assertState().notDisplayed();
        page.selectFrame(0);
        page.newElement(Locator.ID, "message").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameIntNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "message").assertState().notDisplayed();
        page.selectFrame(2);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameNameTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "message").assertState().notDisplayed();
        page.selectFrame("some_frame");
        page.newElement(Locator.ID, "message").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameNameNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "message").assertState().notDisplayed();
        page.selectFrame("some_non_existent_frame");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "message").assertState().notDisplayed();
        page.newElement(Locator.ID, "some_frame").selectFrame();
        page.newElement(Locator.ID, "message").assertState().displayed();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration negative test to check the frame method")
    public void selectFrameNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "non-existent-element").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration negative test to check the frame method")
    public void selectFrameNotVisibleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "some_other_frame").selectFrame();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration negative test to check the frame method")
    public void selectFrameNotFrameTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "scroll_button").selectFrame();
        // verify no issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the openTab method")
    public void openTabTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.openTab("https://www.google.com/");
        page.urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the openTab method")
    public void openEmptyTabTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.openTab();
        page.urlEquals(getTestSite());
        // verify no issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the switchTab method")
    public void switchTabTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.openTab("https://www.google.com/");
        page.switchNextTab();
        String site = getTestSite().endsWith("/") ? getTestSite() : getTestSite() + "/";
        page.urlEquals(site);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the closeTab method")
    public void closeTabTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.openTab("https://www.google.com/");
        page.closeTab();
        String site = getTestSite().endsWith("/") ? getTestSite() : getTestSite() + "/";
        page.urlEquals(site);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the closeTab method")
    public void closeFirstTabTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.openTab("https://www.google.com/");
        page.switchPreviousTab();
        page.closeTab();
        page.urlEquals("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void switchToNewWindowTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "new_window").click();
        page.switchToNewWindow();
        page.textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void switchToParentWindowTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "new_window").click();
        page.switchToNewWindow();
        page.textPresent("You're on the next page");
        page.switchToParentWindow();
        page.textNotPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void closeCurrentWindowTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "new_window").click();
        page.switchToNewWindow();
        page.textPresent("You're on the next page");
        page.closeCurrentWindow();
        page.textNotPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void closeCurrentWindowNoWindowTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "new_window").click();
        page.switchToNewWindow();
        page.textPresent("You're on the next page");
        page.closeCurrentWindow();
        page.closeCurrentWindow();
        // verify 1 issue
        finish(1);
    }
}