package integration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class ActionGoIT extends TestBase {

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

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the goBackOnePage method")
	public void goBackOnePageTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// use this object to verify the page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.submit(Locator.ID, "submit_button");
		asserts.checkTextVisible("You're on the next page");
		actions.goBackOnePage();
		asserts.checkTextNotVisible("You're on the next page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the goBackOnePage method")
	public void goBackOnePageNoBackTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.goBackOnePage();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the goBackOnePage method")
	public void goBackOnePageErrorTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.goBackOnePage();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the goForwardOnePage method")
	public void goForwardOnePageTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// use this object to verify the page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.submit(Locator.ID, "submit_button");
		asserts.checkTextVisible("You're on the next page");
		actions.goBackOnePage();
		asserts.checkTextNotVisible("You're on the next page");
		actions.goForwardOnePage();
		asserts.checkTextVisible("You're on the next page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the goForwardOnePage method")
	public void goForwardOnePageNoForwardTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.goForwardOnePage();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the goForwardOnePage method")
	public void goForwardOnePageErrorTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.goForwardOnePage();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the refreshPage method")
	public void refreshPageTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.refreshPage();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the refreshPage method")
	public void refreshPageErrorTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.refreshPage();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the refreshPageHard method")
	public void refreshPageHardTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.refreshPageHard();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the refreshPageHard method")
	public void refreshPageHardErrorTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.refreshPageHard();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the setCookie method")
	public void setCookieTest() throws IOException, ParseException {
		String dateval = "2011-11-17T09:52:13";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Cookie cookie = new Cookie("new_cookie", "this_cookie", getTestSite().split("/")[2].split(":")[0], "/",
				df.parse(dateval));
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.setCookie(cookie);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the setCookie method")
	public void setCookieErrorTest() throws IOException, ParseException {
		String dateval = "2011-11-17T09:52:13";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Cookie cookie = new Cookie("new_cookie", "this_cookie", getTestSite().split("/")[2].split(":")[0], "/",
				df.parse(dateval));
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.setCookie(cookie);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the deleteCookie method")
	public void deleteCookieTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.deleteCookie("cookie");
		Cookie cookie = actions.getCookie("cookie");
		org.testng.Assert.assertNull(cookie);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the deleteCookie method")
	public void deleteNonExistentCookieTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.deleteCookie("new_cookie");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the deleteAllCookies method")
	public void deleteAllCookiesTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.deleteAllCookies();
		Cookie cookie = actions.getCookie("cookie");
		org.testng.Assert.assertNull(cookie);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the deleteAllCookies method")
	public void deleteAllCookiesTwiceTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.deleteAllCookies();
		actions.deleteAllCookies();
		Cookie cookie = actions.getCookie("cookie");
		org.testng.Assert.assertNull(cookie);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the deleteAllCookies method")
	public void deleteAllCookiesErrorTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.deleteAllCookies();
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the maximizeScreen method")
	public void maximizeScreenTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.maximizeScreen();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "go",
			"virtual" }, description = "An integration test to check the maximizeScreen method")
	public void maximizeScreenErrorTest() throws IOException {
		// use this object to manipulate the page
		Action actions = this.actions.get();
		// perform some actions
		actions.killDriver();
		actions.maximizeScreen();
		// verify 1 issue
		finish(1);
	}
}