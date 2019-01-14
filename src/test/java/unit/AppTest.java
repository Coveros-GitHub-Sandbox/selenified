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

import static org.testng.Assert.fail;

public class AppTest {

    private String setHub = null;

    @BeforeClass (alwaysRun = true)
    public void saveBrowser() {
        if (System.getProperty("hub") != null) {
            setHub = System.getProperty("hub");
        }
    }

    @AfterClass (alwaysRun = true)
    public void restoreBrowser() {
        System.clearProperty("hub");
        if (setHub != null) {
            System.setProperty("hub", setHub);
        }
    }

    @BeforeMethod (alwaysRun = true)
    public void clearBrowser() {
        System.clearProperty("hub");
    }

    @Test(expectedExceptions = WebDriverException.class)
    public void checkElementTypeTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty("hub", "http://myurl");
        new App(new Capabilities(new Browser("htmlunIT")), null);
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void checkElementTypeBadURLTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty("hub", "myurl");
        new App(new Capabilities(new Browser("htmlunit")), null);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void nullCapabilitiesTest() throws InvalidBrowserException, MalformedURLException {
        new App(null, null);
        fail("Expected an InvalidBrowserException");
    }
}