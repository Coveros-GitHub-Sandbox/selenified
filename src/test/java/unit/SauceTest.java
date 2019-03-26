package unit;

import com.coveros.selenified.utilities.Sauce;
import org.testng.annotations.*;

import static org.testng.Assert.*;
import static unit.PropertyTest.HUB;

public class SauceTest extends PropertyTest {

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

    @Test
    public void getUserNoHubTest() {
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserNoUserTest() {
        System.setProperty(HUB, "https://ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserOnlyUserTest() {
        System.setProperty(HUB, "https://sauceusername@ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserBadURLTest() {
        System.setProperty(HUB, "https:///sauceaccesskey@ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserSauceTest() {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(Sauce.getSauceUser(), "sauceusername");
    }

    @Test
    public void getKeyNoHubTest() {
        assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeyNoKeyTest() {
        System.setProperty(HUB, "https://ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeyOnlyKeyTest() {
        System.setProperty(HUB, "https://sauceaccesskey@ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeySauceTest() {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(Sauce.getSauceKey(), "sauceaccesskey");
    }
}