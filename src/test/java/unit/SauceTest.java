package unit;

import com.coveros.selenified.utilities.Sauce;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class SauceTest {

    private String hub = null;

    @BeforeClass(alwaysRun = true)
    public void saveHub() {
        if (System.getProperty("hub") != null) {
            hub = System.getProperty("hub");
        }
    }

    @AfterClass(alwaysRun = true)
    public void restoreHub() {
        if (hub != null) {
            System.setProperty("hub", hub);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @AfterMethod(alwaysRun = true)
    public void clearHub() {
        System.clearProperty("hub");
    }

    @Test
    public void isSauceNoHubTest() {
        assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceNotSauceTest() {
        System.setProperty("hub", "Hello World");
        assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceNotSauceURLTest() {
        System.setProperty("hub", "http://localhost:4444");
        assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceSauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertTrue(Sauce.isSauce());
    }

    @Test
    public void getUserNoHubTest() {
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserNoUserTest() {
        System.setProperty("hub", "https://ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserOnlyUserTest() {
        System.setProperty("hub", "https://sauceusername@ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserBadURLTest() {
        System.setProperty("hub", "https:///sauceaccesskey@ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserSauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(Sauce.getSauceUser(), "sauceusername");
    }

    @Test
    public void getKeyNoHubTest() {
        assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeyNoKeyTest() {
        System.setProperty("hub", "https://ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeyOnlyKeyTest() {
        System.setProperty("hub", "https://sauceaccesskey@ondemand.saucelabs.com:443");
        assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeySauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(Sauce.getSauceKey(), "sauceaccesskey");
    }
}