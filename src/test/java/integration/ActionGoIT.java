package integration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class ActionGoIT extends Selenified {

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

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goBackOnePage method")
    public void goBackOnePageTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "submit_button").submit();
        page.textPresent("You're on the next page");
        page.goBack();
        page.textNotPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goBackOnePage method")
    public void goBackOnePageNoBackTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.goBack();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goBackOnePage method")
    public void goBackOnePageErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.goBack();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goForwardOnePage method")
    public void goForwardOnePageTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "submit_button").submit();
        page.textPresent("You're on the next page");
        page.goBack();
        page.textNotPresent("You're on the next page");
        page.goForward();
        page.textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goForwardOnePage method")
    public void goForwardOnePageNoForwardTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.goForward();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goForwardOnePage method")
    public void goForwardOnePageErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.goForward();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPage method")
    public void refreshPageTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.refresh();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPage method")
    public void refreshPageErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.refresh();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPageHard method")
    public void refreshPageHardTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.refreshHard();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPageHard method")
    public void refreshPageHardErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.refreshHard();
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
        Page page = this.pages.get();
        // perform some actions
        page.setCookie(cookie);
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
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.setCookie(cookie);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteCookie method")
    public void deleteCookieTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.deleteCookie("cookie");
        Cookie cookie = page.get().cookie("cookie");
        org.testng.Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteCookie method")
    public void deleteNonExistentCookieTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.deleteCookie("new_cookie");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteAllCookies method")
    public void deleteAllCookiesTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.deleteAllCookies();
        Cookie cookie = page.get().cookie("cookie");
        org.testng.Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteAllCookies method")
    public void deleteAllCookiesTwiceTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.deleteAllCookies();
        page.deleteAllCookies();
        Cookie cookie = page.get().cookie("cookie");
        org.testng.Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteAllCookies method")
    public void deleteAllCookiesErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.deleteAllCookies();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the maximizeScreen method")
    public void maximizeScreenTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.maximize();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the maximizeScreen method")
    public void maximizeScreenErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.maximize();
        // verify 1 issue
        finish(1);
    }
}