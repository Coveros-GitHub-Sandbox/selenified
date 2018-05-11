package unit;

import com.coveros.selenified.utilities.Sauce;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SauceTest {

    private String hub;

    @BeforeClass
    public void setupArrays() {
        if (System.getProperty("hub") != null) {
            hub = System.getProperty("hub");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (hub != null) {
            System.setProperty("hub", hub);
        }
    }

    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("hub");
    }

    @Test
    public void isSauceNoHubTest() {
        Assert.assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceNotSauceTest() {
        System.setProperty("hub", "Hello World");
        Assert.assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceNotSauceURLTest() {
        System.setProperty("hub", "http://localhost:4444");
        Assert.assertFalse(Sauce.isSauce());
    }

    @Test
    public void isSauceSauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertTrue(Sauce.isSauce());
    }

    @Test
    public void getUserNoHubTest() {
        Assert.assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserNoUserTest() {
        System.setProperty("hub", "https://ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserOnlyUserTest() {
        System.setProperty("hub", "https://sauceusername@ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserBadURLTest() {
        System.setProperty("hub", "https:///sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getSauceUser());
    }

    @Test
    public void getUserSauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertEquals(Sauce.getSauceUser(), "sauceusername");
    }

    @Test
    public void getKeyNoHubTest() {
        Assert.assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeyNoKeyTest() {
        System.setProperty("hub", "https://ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeyOnlyKeyTest() {
        System.setProperty("hub", "https://sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertNull(Sauce.getSauceKey());
    }

    @Test
    public void getKeySauceTest() {
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        Assert.assertEquals(Sauce.getSauceKey(), "sauceaccesskey");
    }
}