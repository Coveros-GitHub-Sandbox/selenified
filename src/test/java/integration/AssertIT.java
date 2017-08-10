package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class AssertIT extends Selenified {

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

	@Test(groups = { "integration", "asserts", "virtual" }, description = "An integration test to check a title")
	public void compareTitleTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform the verification
		page.titleEquals("Selenified Test Page");
		// perform the verification
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check a title")
	public void negativeCompareTitleTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.titleEquals("Yahoo");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkAlertPresent method")
	public void checkAlertPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.alertPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckAlertPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.alertPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkAlertPresent method")
	public void checkConfirmationPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.confirmationPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckConfirmationPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.confirmationPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkAlertPresent method")
	public void checkAlertNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.alertNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlertPresent method")
	public void negativeCheckNotAlertPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.alertNotPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkAlert method")
	public void checkAlertTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.alertPresent("Enabled!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkAlert method")
	public void checkAlertRegexTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.alertPresent("([A-Z])\\w+!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlert method")
	public void negativeCheckAlertNoAlertTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.alertPresent("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkAlert method")
	public void negativeCheckAlertTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.alertPresent("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkConfirmation method")
	public void checkConfirmationTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.confirmationPresent("Enabled!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkConfirmation method")
	public void negativeCheckConfirmationTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.confirmationPresent("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkConfirmation method")
	public void negativeCheckConfirmationNoConfirmationTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.confirmationPresent("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkConfirmationNotPresent method")
	public void checkConfirmationNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.confirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkConfirmationNotPresent method")
	public void negativeCheckConfirmationNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.confirmationNotPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts" }, description = "An integration test to check the checkPrompt method")
	public void checkPromptTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.promptPresent("Enabled!");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPrompt method")
	public void negativeCheckPromptNoPromptTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.promptPresent("Enabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPrompt method")
	public void negativeCheckPromptTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.promptPresent("Disabled!");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkPromptNotPresent method")
	public void checkPromptPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.promptPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
	public void negativeCheckPromptPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.promptPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration test to check the checkPromptNotPresent method")
	public void checkPromptNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.promptNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"asserts" }, description = "An integration negative test to check the checkPromptNotPresent method")
	public void negativeCheckPromptNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.newElement(Locator.ID, "disable_click").click();
		page.newElement(Locator.ID, "alert_button").click();
		page.promptNotPresent();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkTextNotVisible method")
	public void checkTextNotVisibleTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.textNotPresent("No such text on the page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkTextNotVisible method")
	public void negativeCheckTextNotVisibleTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.textNotPresent("Click me to Disable/Enable a html button");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkTextVisible method")
	public void checkTextVisibleTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.textPresent("Click me to Disable/Enable a html button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkTextVisible method")
	public void negativeCheckTextVisibleTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.textPresent("No such text on the page");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkCookie method")
	public void checkCookieTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieExists("cookie", "cookietest");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookie method")
	public void negativeCheckCookieTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieExists("cookie", "negativecookietest");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookie method")
	public void negativeCheckCookieWrongNameTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieExists("wrongcookie", "cookietest");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkCookieNotPresent method")
	public void checkCookieNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieNotExists("wrongcookie");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookieNotPresent method")
	public void negativeCheckCookieNotPresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieNotExists("cookie");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration test to check the checkCookiePresent method")
	public void checkCookiePresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieExists("cookie");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "asserts",
			"virtual" }, description = "An integration negative test to check the checkCookiePresent method")
	public void negativeCheckCookiePresentTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		page.cookieExists("wrongcookie");
		// verify 1 issue
		finish(1);
	}
}