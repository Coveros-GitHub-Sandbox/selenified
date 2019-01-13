package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.security.krb5.internal.crypto.Des;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class BrowserTest {

    @Test
    public void browsersTest() throws InvalidBrowserException {
        assertEquals(Browser.lookup("CHROME"), Browser.BrowserName.CHROME);
        assertEquals(Browser.lookup("chrome"), Browser.BrowserName.CHROME);
        assertEquals(Browser.lookup("cHroMe"), Browser.BrowserName.CHROME);
    }

    @Test
    public void eachBrowsersTest() throws InvalidBrowserException {
        assertEquals(Browser.lookup("NONE"), Browser.BrowserName.NONE);
        assertEquals(Browser.lookup("HTMLUNIT"), Browser.BrowserName.HTMLUNIT);
        assertEquals(Browser.lookup("FIREFOX"), Browser.BrowserName.FIREFOX);
        assertEquals(Browser.lookup("CHROME"), Browser.BrowserName.CHROME);
        assertEquals(Browser.lookup("INTERNETEXPLORER"), Browser.BrowserName.INTERNETEXPLORER);
        assertEquals(Browser.lookup("EDGE"), Browser.BrowserName.EDGE);
        assertEquals(Browser.lookup("ANDROID"), Browser.BrowserName.ANDROID);
        assertEquals(Browser.lookup("IPAD"), Browser.BrowserName.IPAD);
        assertEquals(Browser.lookup("IPHONE"), Browser.BrowserName.IPHONE);
        assertEquals(Browser.lookup("OPERA"), Browser.BrowserName.OPERA);
        assertEquals(Browser.lookup("SAFARI"), Browser.BrowserName.SAFARI);
        assertEquals(Browser.lookup("PHANTOMJS"), Browser.BrowserName.PHANTOMJS);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void browsersInvalidTest() throws InvalidBrowserException {
        Browser.lookup("HELLOWORLD");
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void checkInvalidBrowserTest() throws InvalidBrowserException {
        new Browser("HELLOWORLD");
    }

    @Test
    public void checkBrowserNameTest() throws InvalidBrowserException {
        Browser browser = new Browser("FIREFOX");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertNull(browser.getVersion());
        assertNull(browser.getPlatform());
        assertNull(browser.getScreensize());
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void checkBrowserDetailsBadNameTest() throws InvalidBrowserException {
        new Browser("name=HELLOWORLD");
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void checkBrowserDeviceNoNameTest() throws InvalidBrowserException {
        new Browser("version=1.0.5");
    }

    @Test
    public void checkBrowserVersionTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&version=1.0.5");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertEquals(browser.getVersion(), "1.0.5");
        assertNull(browser.getPlatform());
        assertNull(browser.getScreensize());
    }

    @Test
    public void checkBrowserPlatformTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&platform=Windows 10");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertNull(browser.getVersion());
        assertEquals(browser.getPlatform(), "Windows 10");
        assertNull(browser.getScreensize());
    }

    @Test
    public void checkBrowserScreensizeTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=100x200");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertNull(browser.getVersion());
        assertNull(browser.getPlatform());
        assertEquals(browser.getScreensize(),"100x200");
    }

    @Test
    public void checkBrowserScreensizeMaximumTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=maximum");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertNull(browser.getVersion());
        assertNull(browser.getPlatform());
        assertEquals(browser.getScreensize(),"maximum");
    }

    @Test
    public void checkBrowserScreensizeMaximumCaseTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=maxImum");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertNull(browser.getVersion());
        assertNull(browser.getPlatform());
        assertEquals(browser.getScreensize(),"maxImum");
    }

    @Test
    public void checkBrowserScreensizeBadTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=large");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertNull(browser.getVersion());
        assertNull(browser.getPlatform());
        assertNull(browser.getScreensize());
    }

    @Test
    public void checkBrowserAllDetailsTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&version=1.0.5&platform=Windows 10&screensize=maximum");
        assertEquals(browser.getName(), Browser.BrowserName.FIREFOX);
        assertEquals(browser.getVersion(), "1.0.5");
        assertEquals(browser.getPlatform(), "Windows 10");
        assertEquals(browser.getScreensize(), "maximum");
    }

    //TODO - setBrowserCapabilities

    @Test
    public void checkBrowserCapabilitiesNameTest() throws InvalidBrowserException {
        Browser browser = new Browser("FIREFOX");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }

    @Test
    public void checkBrowserCapabilitiesVersionTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&version=1.0.5");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        desiredCapabilities.setVersion("1.0.5");
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }

    @Test
    public void checkBrowserCapabilitiesPlatformTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&platform=Windows 10");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        desiredCapabilities.setPlatform(Platform.WIN10);
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }

    @Test
    public void checkBrowserCapabilitiesScreensizeTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=100x200");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        desiredCapabilities.setCapability("screenResolution", "100x200");
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }

    @Test
    public void checkBrowserCapabilitiesScreensizeMaximumTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=maximum");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }

    @Test
    public void checkBrowserCapabilitiesScreensizeBadTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&screensize=large");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }

    @Test
    public void checkBrowserCapabilitiesAllDetailsTest() throws InvalidBrowserException {
        Browser browser = new Browser("name=Firefox&version=1.0.5&platform=Windows 10&screensize=100x200");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("firefox");
        desiredCapabilities.setVersion("1.0.5");
        desiredCapabilities.setPlatform(Platform.WIN10);
        desiredCapabilities.setCapability("screenResolution", "100x200");
        assertEquals(browser.setBrowserCapabilities(new DesiredCapabilities()), desiredCapabilities);
    }
}