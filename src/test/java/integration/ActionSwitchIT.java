package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;

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

	@Test(groups = { "integration", "actions", "do",
			"tab" }, description = "An integration test to check the switchTab method")
	public void switchTabTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.openTab("https://www.google.com/");
		actions.switchNextTab();
		asserts.compareURL(getTestSite() + "/");
		// verify 1 issue
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"tab" }, description = "An integration test to check the closeTab method")
	public void closeTabTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.openTab("https://www.google.com/");
		actions.closeTab();
		asserts.compareURL(getTestSite() + "/");
		// verify 1 issue
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
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
}