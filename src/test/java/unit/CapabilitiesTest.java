package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
<<<<<<< HEAD
=======
import com.coveros.selenified.exceptions.InvalidHubException;
>>>>>>> master
import com.coveros.selenified.exceptions.InvalidProxyException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

<<<<<<< HEAD
import java.util.logging.Level;

import static com.coveros.selenified.utilities.Property.PROXY;
import static com.coveros.selenified.utilities.Reporter.ENABLED_LOGS;
=======
>>>>>>> master
import static org.testng.Assert.*;

public class CapabilitiesTest extends SaveProperties {

    @Test(expectedExceptions = InvalidBrowserException.class)
<<<<<<< HEAD
    public void setupDriverNullTest() throws InvalidBrowserException, InvalidProxyException {
=======
    public void setupDriverNullTest() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
>>>>>>> master
        new Capabilities(null);
        fail("Expected an InvalidBrowserException");
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityNone() throws InvalidBrowserException, InvalidProxyException {
=======
    public void setupBrowserCapabilityNone() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
>>>>>>> master
        Capabilities capabilities = new Capabilities(new Browser("None"));
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
<<<<<<< HEAD
    public void setupBrowserCapabilityBadBrowser() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilityBadBrowser() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        new Capabilities(new Browser("Android"));
        fail("Expected an InvalidBrowserException");
    }

    static DesiredCapabilities getBasicDesiredCapabilities(String browserName) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(browserName);
        desiredCapabilities.setPlatform(Platform.ANY);
        desiredCapabilities.setVersion("");
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setAcceptInsecureCerts(true);
        desiredCapabilities.setCapability("ensureCleanSession", true);
        //turn on browser logging
        LoggingPreferences logPrefs = new LoggingPreferences();
        for (String logType : ENABLED_LOGS) {
            logPrefs.enable(logType, Level.ALL);
        }
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        return desiredCapabilities;
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityChrome() throws InvalidBrowserException, InvalidProxyException {
=======
    public void setupBrowserCapabilityChrome() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("chrome");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
>>>>>>> master
        Capabilities capabilities = new Capabilities(new Browser("CHROME"));
        assertEquals(capabilities.getDesiredCapabilities(), getBasicDesiredCapabilities("chrome"));
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityEdge() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilityEdge() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("MicrosoftEdge");
        expectedDesiredCapabilities.setPlatform(Platform.WIN10);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("edGE"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityFirefox() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilityFirefox() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("firefox");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("firefox"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityHtmlUnit() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilityHtmlUnit() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("htmlunit");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("HtmlUnit"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityInternetExplorer() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilityIE() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("internet explorer");
        expectedDesiredCapabilities.setPlatform(Platform.WINDOWS);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("InternetEXPLORER"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityIE() throws InvalidProxyException, InvalidBrowserException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("internet explorer");
        expectedDesiredCapabilities.setPlatform(Platform.WINDOWS);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("iE"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityOpera() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilityOpera() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("opera");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("opera"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilitySafari() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilitySafari() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("safari");
        expectedDesiredCapabilities.setPlatform(Platform.MOJAVE);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("sAFARi"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilitySafari12() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilitySafari12() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("safari");
        expectedDesiredCapabilities.setPlatform(Platform.MOJAVE);
        expectedDesiredCapabilities.setVersion("12");
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("name=sAFARi&version=12"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilitySafari10() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupBrowserCapabilitySafari10() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("safari");
        expectedDesiredCapabilities.setPlatform(Platform.MOJAVE);
        expectedDesiredCapabilities.setVersion("10");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("name=sAFARi&version=10"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void setupBrowserCapabilityPhantom() throws InvalidBrowserException, InvalidProxyException {
=======
    public void setupBrowserCapabilityPhantom() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("phantomjs");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("PHANTOMJS"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test(expectedExceptions = InvalidProxyException.class)
<<<<<<< HEAD
    public void setupInvalidProxyTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupInvalidProxyTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.setupProxy();
        DesiredCapabilities capability = capabilities.getDesiredCapabilities();
        assertFalse(capability.is(CapabilityType.PROXY));

        System.setProperty(PROXY, "localhost");
        capabilities.setupProxy();
    }

    @Test
    public void setupProxyTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.setupProxy();
        DesiredCapabilities capability = capabilities.getDesiredCapabilities();
        assertFalse(capability.is(CapabilityType.PROXY));

<<<<<<< HEAD
        System.setProperty(PROXY, "localhost");
        capabilities.setupProxy();
    }

    @Test
    public void setupProxyTest() throws InvalidProxyException, InvalidBrowserException {
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.setupProxy();
        DesiredCapabilities capability = capabilities.getDesiredCapabilities();
        assertFalse(capability.is(CapabilityType.PROXY));

        System.setProperty(PROXY, "localhost:8080");
        capabilities.setupProxy();
        capability = capabilities.getDesiredCapabilities();
        Proxy export = (Proxy) capability.getCapability(CapabilityType.PROXY);
        assertEquals(export.getHttpProxy(), "localhost:8080");
    }

    @Test
    public void addExtraCapabilitiesNoneTest() throws InvalidProxyException, InvalidBrowserException {
=======
        System.setProperty(PROXY, "localhost:8080");
        capabilities.setupProxy();
        capability = capabilities.getDesiredCapabilities();
        Proxy export = (Proxy) capability.getCapability(CapabilityType.PROXY);
        assertEquals(export.getHttpProxy(), "localhost:8080");
    }

    @Test
    public void setupSauceCapabilitiesNoSauceTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("chrome");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.setupSauceCapabilities();
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupSauceCapabilitiesChromeTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        System.setProperty(HUB, "ondemand.saucelabs.com");
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("chrome");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        expectedDesiredCapabilities.setCapability("seleniumVersion", "3.141.59");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.setupSauceCapabilities();
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupSauceCapabilitiesIETest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        System.setProperty(HUB, "ondemand.saucelabs.com");
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("internet explorer");
        expectedDesiredCapabilities.setPlatform(Platform.WINDOWS);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        expectedDesiredCapabilities.setCapability("seleniumVersion", "3.141.59");
        expectedDesiredCapabilities.setCapability("iedriverVersion", "3.141.59");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("internetExplorer"));
        capabilities.setupSauceCapabilities();
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void addExtraCapabilitiesNoneTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void addExtraCapabilitiesNullTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void addExtraCapabilitiesNullTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.addExtraCapabilities(null);
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void addExtraCapabilitiesNoBrowserTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void addExtraCapabilitiesNoBrowserTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        Capabilities capabilities = new Capabilities(new Browser("None"));
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
    }

    @Test
<<<<<<< HEAD
    public void addExtraCapabilitiesEmptyTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void addExtraCapabilitiesEmptyTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
<<<<<<< HEAD
    public void addExtraCapabilitiesFullTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void addExtraCapabilitiesFullTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        DesiredCapabilities extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
        extraCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        capabilities.addExtraCapabilities(extraCapabilities);
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities.merge(extraCapabilities));
    }

    @Test
<<<<<<< HEAD
    public void addExtraCapabilitiesJSTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void addExtraCapabilitiesJSTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = getBasicDesiredCapabilities("chrome");
        expectedDesiredCapabilities.setJavascriptEnabled(false);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        DesiredCapabilities extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setJavascriptEnabled(false);
        capabilities.addExtraCapabilities(extraCapabilities);
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
<<<<<<< HEAD
    public void setupDriverNoneTest() throws InvalidProxyException, InvalidBrowserException {
=======
    public void setupDriverNoneTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
>>>>>>> master
        new Capabilities(new Browser("None")).setupDriver();
        fail("Expected an InvalidBrowserException");
    }
}