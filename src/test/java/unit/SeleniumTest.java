package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.selenium.Assert.Success;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.DriverSetup;

public class SeleniumTest {

    @Test
    public void driverSetupTest() {
        Assert.assertFalse(DriverSetup.FALSE.useBrowser());
        Assert.assertTrue(DriverSetup.OPEN.useBrowser());
        Assert.assertTrue(DriverSetup.LOAD.useBrowser());

        Assert.assertFalse(DriverSetup.FALSE.loadPage());
        Assert.assertFalse(DriverSetup.OPEN.loadPage());
        Assert.assertTrue(DriverSetup.LOAD.loadPage());
    }

    @Test
    public void browsersTest() throws InvalidBrowserException {
        Assert.assertEquals(Browser.lookup("CHROME"), Browser.CHROME);
        Assert.assertEquals(Browser.lookup("chrome"), Browser.CHROME);
        Assert.assertEquals(Browser.lookup("cHroMe"), Browser.CHROME);
    }

    @Test
    public void eachBrowsersTest() throws InvalidBrowserException {
        Assert.assertEquals(Browser.lookup("NONE"), Browser.NONE);
        Assert.assertEquals(Browser.lookup("HTMLUNIT"), Browser.HTMLUNIT);
        Assert.assertEquals(Browser.lookup("FIREFOX"), Browser.FIREFOX);
        Assert.assertEquals(Browser.lookup("MARIONETTE"), Browser.MARIONETTE);
        Assert.assertEquals(Browser.lookup("CHROME"), Browser.CHROME);
        Assert.assertEquals(Browser.lookup("INTERNETEXPLORER"), Browser.INTERNETEXPLORER);
        Assert.assertEquals(Browser.lookup("EDGE"), Browser.EDGE);
        Assert.assertEquals(Browser.lookup("ANDROID"), Browser.ANDROID);
        Assert.assertEquals(Browser.lookup("IPAD"), Browser.IPAD);
        Assert.assertEquals(Browser.lookup("IPHONE"), Browser.IPHONE);
        Assert.assertEquals(Browser.lookup("OPERA"), Browser.OPERA);
        Assert.assertEquals(Browser.lookup("SAFARI"), Browser.SAFARI);
        Assert.assertEquals(Browser.lookup("PHANTOMJS"), Browser.PHANTOMJS);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void browsersInvalidTest() throws InvalidBrowserException {
        Browser.lookup("HELLOWORLD");
    }

    @Test
    public void errorsForPassTest() {
        Assert.assertEquals(Success.PASS.getErrors(), 0);
    }

    @Test
    public void errorsForFailTest() {
        Assert.assertEquals(Success.FAIL.getErrors(), 1);
    }
}