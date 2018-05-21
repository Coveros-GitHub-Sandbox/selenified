package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BrowserTest {

    @Test
    public void browsersTest() throws InvalidBrowserException {
        Assert.assertEquals(Browser.lookup("CHROME"), Browser.BrowserName.CHROME);
        Assert.assertEquals(Browser.lookup("chrome"), Browser.BrowserName.CHROME);
        Assert.assertEquals(Browser.lookup("cHroMe"), Browser.BrowserName.CHROME);
    }

    @Test
    public void eachBrowsersTest() throws InvalidBrowserException {
        Assert.assertEquals(Browser.lookup("NONE"), Browser.BrowserName.NONE);
        Assert.assertEquals(Browser.lookup("HTMLUNIT"), Browser.BrowserName.HTMLUNIT);
        Assert.assertEquals(Browser.lookup("FIREFOX"), Browser.BrowserName.FIREFOX);
        Assert.assertEquals(Browser.lookup("CHROME"), Browser.BrowserName.CHROME);
        Assert.assertEquals(Browser.lookup("INTERNETEXPLORER"), Browser.BrowserName.INTERNETEXPLORER);
        Assert.assertEquals(Browser.lookup("EDGE"), Browser.BrowserName.EDGE);
        Assert.assertEquals(Browser.lookup("ANDROID"), Browser.BrowserName.ANDROID);
        Assert.assertEquals(Browser.lookup("IPAD"), Browser.BrowserName.IPAD);
        Assert.assertEquals(Browser.lookup("IPHONE"), Browser.BrowserName.IPHONE);
        Assert.assertEquals(Browser.lookup("OPERA"), Browser.BrowserName.OPERA);
        Assert.assertEquals(Browser.lookup("SAFARI"), Browser.BrowserName.SAFARI);
        Assert.assertEquals(Browser.lookup("PHANTOMJS"), Browser.BrowserName.PHANTOMJS);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void browsersInvalidTest() throws InvalidBrowserException {
        Browser.lookup("HELLOWORLD");
    }

    @Test
    public void checkDefaultBrowserTest() {
        Browser browser = new Browser(Browser.BrowserName.FIREFOX);
        Assert.assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        Assert.assertNull(browser.getVersion());
        Assert.assertNull(browser.getDevice());
        Assert.assertNull(browser.getOrientation());
        Assert.assertNull(browser.getPlatform());
    }

    @Test
    public void checkBrowserNameTest() {
        Browser browser = new Browser(Browser.BrowserName.FIREFOX);
        browser.setName(Browser.BrowserName.CHROME);
        Assert.assertEquals(browser.getName(), Browser.BrowserName.CHROME);
        Assert.assertNull(browser.getVersion());
        Assert.assertNull(browser.getDevice());
        Assert.assertNull(browser.getOrientation());
        Assert.assertNull(browser.getPlatform());
    }

    @Test
    public void checkBrowserVersionTest() {
        Browser browser = new Browser(Browser.BrowserName.FIREFOX);
        browser.setVersion("1.0.5");
        Assert.assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        Assert.assertEquals(browser.getVersion(), "1.0.5");
        Assert.assertNull(browser.getDevice());
        Assert.assertNull(browser.getOrientation());
        Assert.assertNull(browser.getPlatform());
    }

    @Test
    public void checkBrowserDeviceTest() {
        Browser browser = new Browser(Browser.BrowserName.FIREFOX);
        browser.setDevice("1.0.5");
        Assert.assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        Assert.assertNull(browser.getVersion());
        Assert.assertEquals(browser.getDevice(), "1.0.5");
        Assert.assertNull(browser.getOrientation());
        Assert.assertNull(browser.getPlatform());
    }

    @Test
    public void checkBrowserOrientationTest() {
        Browser browser = new Browser(Browser.BrowserName.FIREFOX);
        browser.setOrientation("1.0.5");
        Assert.assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        Assert.assertNull(browser.getVersion());
        Assert.assertNull(browser.getDevice());
        Assert.assertEquals(browser.getOrientation(), "1.0.5");
        Assert.assertNull(browser.getPlatform());
    }

    @Test
    public void checkBrowserPlatformTest() {
        Browser browser = new Browser(Browser.BrowserName.FIREFOX);
        browser.setPlatform("1.0.5");
        Assert.assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        Assert.assertNull(browser.getVersion());
        Assert.assertNull(browser.getDevice());
        Assert.assertNull(browser.getOrientation());
        Assert.assertEquals(browser.getPlatform(), "1.0.5");
    }
}