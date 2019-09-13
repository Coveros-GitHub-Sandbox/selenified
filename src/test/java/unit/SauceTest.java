package unit;

import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidSauceException;
import com.coveros.selenified.utilities.Sauce;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static com.coveros.selenified.utilities.Property.HUB;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
}