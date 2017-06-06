package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locators;
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
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "message");
		actions.selectFrame(0);
		asserts.checkElementDisplayed(Locators.ID, "message");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration test to check the frame method")
	public void selectFrameIntNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "message");
		actions.selectFrame(2);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration test to check the frame method")
	public void selectFrameNameTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "message");
		actions.selectFrame("some_frame");
		asserts.checkElementDisplayed(Locators.ID, "message");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration test to check the frame method")
	public void selectFrameNameNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "message");
		actions.selectFrame("some_non_existent_frame");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration test to check the frame method")
	public void selectFrameTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "message");
		actions.selectFrame(Locators.ID, "some_frame");
		asserts.checkElementDisplayed(Locators.ID, "message");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration negative test to check the frame method")
	public void selectFrameNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.selectFrame(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration negative test to check the frame method")
	public void selectFrameNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.selectFrame(new Element(Locators.ID, "some_other_frame"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "switch", "frame",
			"virtual" }, description = "An integration negative test to check the frame method")
	public void selectFrameNotFrameTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.selectFrame(new Element(Locators.ID, "scroll_button"));
		// verify no issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "window",
			"tab" }, description = "An integration test to check the maximizeScreen method")
	public void maximizeScreenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.maximizeScreen();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "switch",
			"tab" }, description = "An integration test to check the openTab method")
	public void openTabTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.openTab("https://www.google.com/");
		asserts.compareURL("https://www.google.com/");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "switch",
			"tab" }, description = "An integration test to check the openTab method")
	public void openEmptyTabTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
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
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.openTab("https://www.google.com/");
		actions.switchNextTab();
		String site = getTestSite().endsWith("/") ? getTestSite() : getTestSite() + "/"; 
		asserts.compareURL(site);
		// verify 1 issue
		finish();
	}

	@Test(groups = { "integration", "actions", "switch",
			"tab" }, description = "An integration test to check the closeTab method")
	public void closeTabTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.openTab("https://www.google.com/");
		actions.closeTab();
		String site = getTestSite().endsWith("/") ? getTestSite() : getTestSite() + "/"; 
		asserts.compareURL(site);
		// verify 1 issue
		finish();
	}

	@Test(groups = { "integration", "actions", "switch",
			"tab" }, description = "An integration test to check the closeTab method")
	public void closeFirstTabTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.openTab("https://www.google.com/");
		actions.switchPreviousTab();
		actions.closeTab();
		asserts.compareURL("https://www.google.com/");
		// verify 1 issue
		finish();
	}

	@Test(groups = { "integration", "actions", "switch",
			"window" }, description = "An integration test to check the switch method")
	public void switchToNewWindowTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "new_window");
		actions.switchToNewWindow();
		asserts.checkTextVisible("You're on the next page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "switch",
			"window" }, description = "An integration test to check the switch method")
	public void switchToParentWindowTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(new Element(Locators.ID, "new_window"));
		actions.switchToNewWindow();
		asserts.checkTextVisible("You're on the next page");
		actions.switchToParentWindow();
		asserts.checkTextNotVisible("You're on the next page");
		// verify no issues
		finish();
	}
}