package integration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.openqa.selenium.Cookie;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;

public class ActionGoIT extends Selenified {

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

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goBackOnePage method")
    public void goBackOnePageTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "submit_button").submit();
        app.azzert().textPresent("You're on the next page");
        app.goBack();
        app.azzert().textNotPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goBackOnePage method")
    public void goBackOnePageNoBackTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.goBack();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goBackOnePage method")
    public void goBackOnePageErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.goBack();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goForwardOnePage method")
    public void goForwardOnePageTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "submit_button").submit();
        app.azzert().textPresent("You're on the next page");
        app.goBack();
        app.azzert().textNotPresent("You're on the next page");
        app.goForward();
        app.azzert().textPresent("You're on the next page");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goForwardOnePage method")
    public void goForwardOnePageNoForwardTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.goForward();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the goForwardOnePage method")
    public void goForwardOnePageErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.goForward();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPage method")
    public void refreshPageTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.refresh();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPage method")
    public void refreshPageErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.refresh();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPageHard method")
    public void refreshPageHardTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.refreshHard();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the refreshPageHard method")
    public void refreshPageHardErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.refreshHard();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the setCookie method")
    public void setCookieTest(ITestContext context) throws IOException, ParseException {
        String dateval = "2011-11-17T09:52:13";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Cookie cookie = new Cookie("new_cookie", "this_cookie",
                getTestSite(this.getClass().getName(), context).split("/")[2].split(":")[0], "/", df.parse(dateval));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.setCookie(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the setCookie method")
    public void setCookieErrorTest(ITestContext context) throws IOException, ParseException {
        String dateval = "2011-11-17T09:52:13";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Cookie cookie = new Cookie("new_cookie", "this_cookie",
                getTestSite(this.getClass().getName(), context).split("/")[2].split(":")[0], "/", df.parse(dateval));
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.setCookie(cookie);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteCookie method")
    public void deleteCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.deleteCookie("cookie");
        Cookie cookie = app.get().cookie("cookie");
        org.testng.Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteCookie method")
    public void deleteNonExistentCookieTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.deleteCookie("new_cookie");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteAllCookies method")
    public void deleteAllCookiesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.deleteAllCookies();
        Cookie cookie = app.get().cookie("cookie");
        org.testng.Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteAllCookies method")
    public void deleteAllCookiesTwiceTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.deleteAllCookies();
        app.deleteAllCookies();
        Cookie cookie = app.get().cookie("cookie");
        org.testng.Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the deleteAllCookies method")
    public void deleteAllCookiesErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.deleteAllCookies();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the maximizeScreen method")
    public void maximizeScreenTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.maximize();
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "go",
            "virtual" }, description = "An integration test to check the maximizeScreen method")
    public void maximizeScreenErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.killDriver();
        app.maximize();
        // verify 1 issue
        finish(1);
    }
}