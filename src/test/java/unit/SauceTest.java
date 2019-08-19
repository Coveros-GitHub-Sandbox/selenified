package unit;

import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidSauceException;
import com.coveros.selenified.utilities.Sauce;
import org.testng.annotations.Test;

import static com.coveros.selenified.utilities.Property.HUB;
import static org.testng.Assert.*;

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
    }
}