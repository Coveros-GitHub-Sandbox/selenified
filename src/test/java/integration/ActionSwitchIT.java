package integration;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class ActionSwitchIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws IOException {
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
    public void selectFrameIntTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementNotDisplayed(Locator.ID, "message");
        actions.selectFrame(0);
        asserts.checkElementDisplayed(Locator.ID, "message");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameIntNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementNotDisplayed(Locator.ID, "message");
        actions.selectFrame(2);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameNameTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementNotDisplayed(Locator.ID, "message");
        actions.selectFrame("some_frame");
        asserts.checkElementDisplayed(Locator.ID, "message");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameNameNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementNotDisplayed(Locator.ID, "message");
        actions.selectFrame("some_non_existent_frame");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration test to check the frame method")
    public void selectFrameTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.checkElementNotDisplayed(Locator.ID, "message");
        actions.selectFrame(Locator.ID, "some_frame");
        asserts.checkElementDisplayed(Locator.ID, "message");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration negative test to check the frame method")
    public void selectFrameNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.selectFrame(new Element(Locator.ID, "non-existent-element"));
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration negative test to check the frame method")
    public void selectFrameNotVisibleTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.selectFrame(new Element(Locator.ID, "some_other_frame"));
        // verify 2 issues
        finish(2);
    }

    @Test(groups = { "integration", "actions", "switch", "frame",
            "virtual" }, description = "An integration negative test to check the frame method")
    public void selectFrameNotFrameTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.selectFrame(new Element(Locator.ID, "scroll_button"));
        // verify no issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the openTab method")
    public void openTabTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.openTab("https://www.google.com/");
        asserts.compareURL("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the openTab method")
    public void openTabErrorTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebDriver driver = actions.getDriver();
        actions.setDriver(null);
        actions.openTab("https://www.google.com/");
        actions.setDriver(driver);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the openTab method")
    public void openEmptyTabTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.openTab();
        asserts.compareURL(getTestSite());
        // verify no issues
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the switchTab method")
    public void switchTabTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.openTab("https://www.google.com/");
        actions.switchNextTab();
        String site = getTestSite().endsWith("/") ? getTestSite() : getTestSite() + "/";
        asserts.compareURL(site);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the switchTab method")
    public void switchTabErrorTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebDriver driver = actions.getDriver();
        actions.setDriver(null);
        actions.switchNextTab();
        actions.setDriver(driver);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the closeTab method")
    public void closeTabTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.openTab("https://www.google.com/");
        actions.closeTab();
        String site = getTestSite().endsWith("/") ? getTestSite() : getTestSite() + "/";
        asserts.compareURL(site);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the closeTab method")
    public void closeTabErrorTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebDriver driver = actions.getDriver();
        actions.setDriver(null);
        actions.closeTab();
        actions.setDriver(driver);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the closeTab method")
    public void closeFirstTabTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.openTab("https://www.google.com/");
        actions.switchPreviousTab();
        actions.closeTab();
        asserts.compareURL("https://www.google.com/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "tab" }, description = "An integration test to check the closeTab method")
    public void closeFirstTabErrorTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebDriver driver = actions.getDriver();
        actions.setDriver(null);
        actions.switchPreviousTab();
        actions.setDriver(driver);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void switchToNewWindowTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(Locator.ID, "new_window");
        actions.switchToNewWindow();
        asserts.checkTextVisible("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void switchToNewWindowNoNewTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebDriver driver = actions.getDriver();
        actions.setDriver(null);
        actions.switchToNewWindow();
        actions.setDriver(driver);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void switchToParentWindowTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "new_window"));
        actions.switchToNewWindow();
        asserts.checkTextVisible("You're on the next page");
        actions.switchToParentWindow();
        asserts.checkTextNotVisible("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void switchToParentWindowNoParentTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebDriver driver = actions.getDriver();
        actions.setDriver(null);
        actions.switchToParentWindow();
        actions.setDriver(driver);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void closeCurrentWindowTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "new_window"));
        actions.switchToNewWindow();
        asserts.checkTextVisible("You're on the next page");
        actions.closeCurrentWindow();
        asserts.checkTextNotVisible("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "switch",
            "window" }, description = "An integration test to check the switch method")
    public void closeCurrentWindowNoWindowTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "new_window"));
        actions.switchToNewWindow();
        asserts.checkTextVisible("You're on the next page");
        actions.closeCurrentWindow();
        actions.closeCurrentWindow();
        // verify 1 issue
        finish(1);
    }
}