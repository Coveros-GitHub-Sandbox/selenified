package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class AppTest {

    private String setHub = null;

    @BeforeClass
    public void saveBrowser() {
        if (System.getProperty("hub") != null) {
            setHub = System.getProperty("hub");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (setHub != null) {
            System.setProperty("hub", setHub);
        }
    }

    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("hub");
    }

    @Test(expectedExceptions = WebDriverException.class)
    public void checkElementTypeTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty("hub", "http://myurl");
        new App(new Browser(BrowserName.HTMLUNIT), DesiredCapabilities.firefox(), null);
        System.clearProperty("hub");
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void checkElementTypeBadURLTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty("hub", "myurl");
        new App(new Browser(BrowserName.ANDROID), DesiredCapabilities.firefox(), null);
        System.clearProperty("hub");
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void nullBrowserTest() throws InvalidBrowserException, MalformedURLException {
        new App(null, null, null);
    }

    @Test
    public void nullDeviceCapbilitiesTest() throws InvalidBrowserException, MalformedURLException {
        new App(new Browser(BrowserName.HTMLUNIT), null, null);
        // just ensuring we don't throw an error
    }
}