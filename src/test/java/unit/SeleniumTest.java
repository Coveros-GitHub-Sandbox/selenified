package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.OutputFile.Success;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertEquals(Browser.lookup("CHROME"), BrowserName.CHROME);
        Assert.assertEquals(Browser.lookup("chrome"), BrowserName.CHROME);
        Assert.assertEquals(Browser.lookup("cHroMe"), BrowserName.CHROME);
    }

    @Test
    public void eachBrowsersTest() throws InvalidBrowserException {
        Assert.assertEquals(Browser.lookup("NONE"), BrowserName.NONE);
        Assert.assertEquals(Browser.lookup("HTMLUNIT"), BrowserName.HTMLUNIT);
        Assert.assertEquals(Browser.lookup("FIREFOX"), BrowserName.FIREFOX);
        Assert.assertEquals(Browser.lookup("CHROME"), BrowserName.CHROME);
        Assert.assertEquals(Browser.lookup("INTERNETEXPLORER"), BrowserName.INTERNETEXPLORER);
        Assert.assertEquals(Browser.lookup("EDGE"), BrowserName.EDGE);
        Assert.assertEquals(Browser.lookup("ANDROID"), BrowserName.ANDROID);
        Assert.assertEquals(Browser.lookup("IPAD"), BrowserName.IPAD);
        Assert.assertEquals(Browser.lookup("IPHONE"), BrowserName.IPHONE);
        Assert.assertEquals(Browser.lookup("OPERA"), BrowserName.OPERA);
        Assert.assertEquals(Browser.lookup("SAFARI"), BrowserName.SAFARI);
        Assert.assertEquals(Browser.lookup("PHANTOMJS"), BrowserName.PHANTOMJS);
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