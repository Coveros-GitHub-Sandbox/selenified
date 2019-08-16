package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import static com.coveros.selenified.utilities.Property.HUB;
import static com.coveros.selenified.utilities.Property.PROXY;
import static org.testng.Assert.*;

public class CapabilitiesTest extends SaveProperties {

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverNullTest() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
        new Capabilities(null);
        fail("Expected an InvalidBrowserException");
    }

    @Test
    public void setupBrowserCapabilityNone() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
        Capabilities capabilities = new Capabilities(new Browser("None"));
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupBrowserCapabilityBadBrowser() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        new Capabilities(new Browser("Android"));
        fail("Expected an InvalidBrowserException");
    }

    @Test
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
        Capabilities capabilities = new Capabilities(new Browser("CHROME"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityEdge() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("MicrosoftEdge");
        expectedDesiredCapabilities.setPlatform(Platform.WIN10);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("edGE"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityFirefox() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("firefox");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("firefox"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityHtmlUnit() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("htmlunit");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("HtmlUnit"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityIE() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("internet explorer");
        expectedDesiredCapabilities.setPlatform(Platform.WINDOWS);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("InternetEXPLORER"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityOpera() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("opera");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("opera"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilitySafari() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("safari");
        expectedDesiredCapabilities.setPlatform(Platform.MOJAVE);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("sAFARi"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilitySafari12() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("safari");
        expectedDesiredCapabilities.setPlatform(Platform.MOJAVE);
        expectedDesiredCapabilities.setVersion("12");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(false);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("name=sAFARi&version=12"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilitySafari10() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("safari");
        expectedDesiredCapabilities.setPlatform(Platform.MOJAVE);
        expectedDesiredCapabilities.setVersion("10");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("name=sAFARi&version=10"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityPhantom() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("phantomjs");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(true);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("PHANTOMJS"));
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test(expectedExceptions = InvalidProxyException.class)
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
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void addExtraCapabilitiesNullTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
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
        capabilities.addExtraCapabilities(null);
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void addExtraCapabilitiesNoBrowserTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        Capabilities capabilities = new Capabilities(new Browser("None"));
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
    }

    @Test
    public void addExtraCapabilitiesEmptyTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
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
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test
    public void addExtraCapabilitiesFullTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
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
        DesiredCapabilities extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
        extraCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        capabilities.addExtraCapabilities(extraCapabilities);
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities.merge(extraCapabilities));
    }

    @Test
    public void addExtraCapabilitiesJSTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        // what we expect
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities();
        expectedDesiredCapabilities.setBrowserName("chrome");
        expectedDesiredCapabilities.setPlatform(Platform.ANY);
        expectedDesiredCapabilities.setVersion("");
        expectedDesiredCapabilities.setJavascriptEnabled(false);
        expectedDesiredCapabilities.setAcceptInsecureCerts(true);
        expectedDesiredCapabilities.setCapability("ensureCleanSession", true);
        // what we're getting
        Capabilities capabilities = new Capabilities(new Browser("Chrome"));
        DesiredCapabilities extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setJavascriptEnabled(false);
        capabilities.addExtraCapabilities(extraCapabilities);
        assertEquals(capabilities.getDesiredCapabilities(), expectedDesiredCapabilities);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverNoneTest() throws InvalidProxyException, InvalidBrowserException, InvalidHubException {
        new Capabilities(new Browser("None")).setupDriver();
        fail("Expected an InvalidBrowserException");
    }
}