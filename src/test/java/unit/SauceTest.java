package unit;

<<<<<<< HEAD
import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.exceptions.InvalidSauceException;
import com.coveros.selenified.utilities.Sauce;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
=======
import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidSauceException;
import com.coveros.selenified.utilities.Sauce;
>>>>>>> master
import org.testng.annotations.Test;

import java.net.MalformedURLException;

<<<<<<< HEAD
import static com.coveros.selenified.utilities.Property.HUB;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static unit.CapabilitiesTest.getBasicDesiredCapabilities;

=======
>>>>>>> master
public class SauceTest extends SaveProperties {

    @Test
    public void isSauceNoHubTest() {
        assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceNotSauceTest() {
        System.setProperty(HUB, "Hello World");
        assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceNotSauceURLTest() {
        System.setProperty(HUB, "http://localhost:4444");
        assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceSauceTest() {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertTrue(Sauce.isSauce());
    }

<<<<<<< HEAD
    @Test(expectedExceptions = InvalidHubException.class)
    public void getSauceConnectionEmptyTest() throws MalformedURLException {
        new Sauce().getSauceConnection();
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getSauceConnectionBadTest() throws MalformedURLException {
        System.setProperty(HUB, "https://saucelabs.com:443");
        new Sauce().getSauceConnection();
    }

    @Test
    public void getSauceConnectionTest() throws MalformedURLException {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        new Sauce().getSauceConnection();
    }

    @Test
    public void setupSauceCapabilitiesNoSauceTest() throws InvalidProxyException, InvalidBrowserException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        Sauce.setupSauceCapabilities(capabilities.getDesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupSauceCapabilitiesChromeTest() throws InvalidProxyException, InvalidBrowserException {
        System.setProperty(HUB, "ondemand.saucelabs.com");
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        expectedDesiredCapabilities.setCapability("seleniumVersion", "3.141.59");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        Sauce.setupSauceCapabilities(capabilities.getDesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupSauceCapabilitiesIETest() throws InvalidProxyException, InvalidBrowserException {
        System.setProperty(HUB, "ondemand.saucelabs.com");
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("internet explorer");
        expectedDesiredCapabilities.setPlatform(Platform.WINDOWS);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        expectedDesiredCapabilities.setCapability("seleniumVersion", "3.141.59");
        expectedDesiredCapabilities.setCapability("iedriverVersion", "3.141.59");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("internetExplorer"));
        Sauce.setupSauceCapabilities(capabilities.getDesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
=======
    @Test(expectedExceptions = InvalidSauceException.class)
    public void getUserNoHubTest() throws InvalidHubException {
        Sauce.getSauceUser();
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getUserNoUserTest() throws InvalidHubException {
        System.setProperty(HUB, "https://ondemand.saucelabs.com:443");
        Sauce.getSauceUser();
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getUserOnlyUserTest() throws InvalidHubException {
        System.setProperty(HUB, "https://sauceusername@ondemand.saucelabs.com:443");
        Sauce.getSauceUser();
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getUserBadURLTest() throws InvalidHubException {
        System.setProperty(HUB, "https:///sauceaccesskey@ondemand.saucelabs.com:443");
        Sauce.getSauceUser();
    }

    @Test
    public void getUserSauceTest() throws InvalidHubException {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(Sauce.getSauceUser(), "sauceusername");
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getKeyNoHubTest() throws InvalidHubException {
        Sauce.getSauceKey();
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getKeyNoKeyTest() throws InvalidHubException {
        System.setProperty(HUB, "https://ondemand.saucelabs.com:443");
        Sauce.getSauceKey();
    }

    @Test(expectedExceptions = InvalidSauceException.class)
    public void getKeyOnlyKeyTest() throws InvalidHubException {
        System.setProperty(HUB, "https://sauceaccesskey@ondemand.saucelabs.com:443");
        Sauce.getSauceKey();
    }

    @Test
    public void getKeySauceTest() throws InvalidHubException {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(Sauce.getSauceKey(), "sauceaccesskey");
>>>>>>> master
    }
}