package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.WebDriverException;
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
        new App(new Capabilities(new Browser("htmlunIT")), null);
        System.clearProperty("hub");
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void checkElementTypeBadURLTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty("hub", "myurl");
        new App(new Capabilities(new Browser("andrOID")), null);
        System.clearProperty("hub");
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void nullCapabilitiesTest() throws InvalidBrowserException, MalformedURLException {
        new App(null, null);
    }
}