package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.*;

import java.net.MalformedURLException;

import static org.testng.Assert.fail;
import static unit.PropertyTest.HUB;

public class AppTest {

    private String setHub = null;

    @BeforeClass(alwaysRun = true)
    public void saveHub() {
        if (System.getProperty(HUB) != null) {
            setHub = System.getProperty(HUB);
        }
    }

    @AfterClass(alwaysRun = true)
    public void restoreHub() {
        if (setHub != null) {
            System.setProperty(HUB, setHub);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @AfterMethod(alwaysRun = true)
    public void clearHub() {
        System.clearProperty(HUB);
    }

    @Test(expectedExceptions = WebDriverException.class)
    public void checkElementTypeTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty(HUB, "http://myurl");
        new App(new Capabilities(new Browser("htmlunIT")), null);
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void checkElementTypeBadURLTest() throws InvalidBrowserException, MalformedURLException {
        System.setProperty(HUB, "myurl");
        new App(new Capabilities(new Browser("htmlunit")), null);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void nullCapabilitiesTest() throws InvalidBrowserException, MalformedURLException {
        new App(null, null);
        fail("Expected an InvalidBrowserException");
    }
}