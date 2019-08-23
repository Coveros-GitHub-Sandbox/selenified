package unit;

import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.utilities.Hub;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.coveros.selenified.utilities.Property.HUB;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertNull;

@PrepareForTest({System.class})
public class HubTest extends SaveProperties {

    @Test
    public void defaultIsHubTest() {
        assertFalse(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubSystemEmptyTest() {
        System.setProperty(HUB, "");
        assertFalse(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubSystemTest() {
        System.setProperty(HUB, "somehub");
        assertTrue(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubFilePartialTest() throws IOException {
        createPropertiesFile(HUB);
        assertFalse(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubFileUnsetTest() throws IOException {
        createPropertiesFile(HUB + "=");
        assertFalse(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubFileTrueTest() throws IOException {
        createPropertiesFile(HUB + "=somehub");
        assertTrue(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubOverrideEmptyTest() throws IOException {
        System.setProperty(HUB, "");
        createPropertiesFile(HUB + "=somehub");
        assertFalse(Hub.isHubSet());
    }

    @Test
    public void defaultIsHubOverrideTrueTest() throws IOException {
        System.setProperty(HUB, "somehub");
        createPropertiesFile(HUB + "=");
        assertTrue(Hub.isHubSet());
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubTest() throws MalformedURLException {
        new Hub().getHubURL();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubSystemEmptyTest() throws MalformedURLException {
        System.setProperty(HUB, "");
        new Hub().getHubURL();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubSystemBadTest() throws MalformedURLException {
        System.setProperty(HUB, "somehub");
        new Hub().getHubURL();
    }

    @Test
    public void defaultGetHubSystemTest() throws MalformedURLException {
        System.setProperty(HUB, "https://somehub");
        assertEquals(new Hub().getHubURL(), new URL("https://somehub/wd/hub"));
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFileEmptyTest() throws IOException {
        createPropertiesFile("");
        new Hub().getHubURL();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFilePartialTest() throws IOException {
        createPropertiesFile(HUB);
        new Hub().getHubURL();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFileUnsetTest() throws IOException {
        createPropertiesFile(HUB + "=");
        new Hub().getHubURL();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFileBadTest() throws IOException {
        createPropertiesFile(HUB + "=somehub");
        new Hub().getHubURL();
    }


    @Test
    public void defaultGetHubFileTrueTest() throws IOException {
        createPropertiesFile(HUB + "=https://somehub");
        assertEquals(new Hub().getHubURL(), new URL("https://somehub/wd/hub"));
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubOverrideEmptyTest() throws IOException {
        System.setProperty(HUB, "");
        createPropertiesFile(HUB + "=somehub");
        new Hub().getHubURL();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubOverrideBadTest() throws IOException {
        System.setProperty(HUB, "somehub");
        createPropertiesFile(HUB + "=");
        new Hub().getHubURL();
    }

    @Test
    public void defaultGetHubOverrideTrueTest() throws IOException {
        System.setProperty(HUB, "http://somehub");
        createPropertiesFile(HUB + "=");
        assertEquals(new Hub().getHubURL(), new URL("http://somehub/wd/hub"));
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void getUserNoHubTest() throws MalformedURLException {
        new Hub().getUsername();
    }

    @Test
    public void getUserNoUserTest() throws MalformedURLException {
        System.setProperty(HUB, "https://ondemand.saucelabs.com:443");
        assertEquals(new Hub().getHubURL(), new URL("https://ondemand.saucelabs.com:443/wd/hub"));
        assertNull(new Hub().getUsername());
        assertNull(new Hub().getPassword());
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void getUserOnlyUserTest() throws MalformedURLException {
        System.setProperty(HUB, "https://sauceusername@ondemand.saucelabs.com:443");
        new Hub().getUsername();
    }

    @Test
    public void getUserBadURLTest() throws MalformedURLException {
        System.setProperty(HUB, "https:///sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(new Hub().getHubURL(), new URL("https:/wd/hub"));
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void getKeyNoHubTest() throws MalformedURLException {
        new Hub().getPassword();
    }

    @Test
    public void getKeyNoKeyTest() throws MalformedURLException {
        System.setProperty(HUB, "https://ondemand.saucelabs.com:443");
        assertNull(new Hub().getPassword(), null);
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void getKeyOnlyKeyTest() throws MalformedURLException {
        System.setProperty(HUB, "https://sauceaccesskey@ondemand.saucelabs.com:443");
        new Hub().getPassword();
    }

    @Test
    public void getCredsUrlSauceTest() throws MalformedURLException {
        System.setProperty(HUB, "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        assertEquals(new Hub().getUsername(), "sauceusername");
        assertEquals(new Hub().getPassword(), "sauceaccesskey");
    }
}
