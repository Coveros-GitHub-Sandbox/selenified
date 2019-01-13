package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class CapabilitiesTest {

    private final DesiredCapabilities capabilities = new DesiredCapabilities();
    private String setHub = null;
    private String setProxy = null;
    private String setHeadless = null;
    private String setOptions = null;

    @BeforeClass
    public void saveBrowser() {
        if (System.getProperty("hub") != null) {
            setHub = System.getProperty("hub");
        }
        if (System.getProperty("proxy") != null) {
            setProxy = System.getProperty("proxy");
        }
        if (System.getProperty("headless") != null) {
            setHeadless = System.getProperty("headless");
        }
        if (System.getProperty("options") != null) {
            setOptions = System.getProperty("options");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (setHub != null) {
            System.setProperty("hub", setHub);
        }
        if (setProxy != null) {
            System.setProperty("proxy", setProxy);
        }
        if (setHeadless != null) {
            System.setProperty("headless", setHeadless);
        }
        if (setOptions != null) {
            System.setProperty("headless", setOptions);
        }
    }

    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("hub");
        System.clearProperty("proxy");
        System.clearProperty("headless");
        System.clearProperty("options");
    }

    @Test
    public void setupProxyTest() throws InvalidBrowserException {
        Capabilities capabilities = new Capabilities(new Browser("Android"));
        capabilities.setupProxy();
        DesiredCapabilities capability = capabilities.getDesiredCapabilities();
        assertFalse(capability.is(CapabilityType.PROXY));

        System.setProperty("proxy", "localhost");
        capabilities.setupProxy();
        capability = capabilities.getDesiredCapabilities();
        Proxy export = (Proxy) capability.getCapability(CapabilityType.PROXY);
        assertEquals(export.getHttpProxy(), "localhost");
    }

    @Test
    public void setupBrowserCapabilityNoHub() throws InvalidBrowserException {
        Capabilities capabilities = new Capabilities(new Browser("Android"));
        capabilities.setupBrowserCapability();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("android");
        assertEquals(capabilities.getDesiredCapabilities(), desiredCapabilities);
    }

    @Test
    public void setupBrowserCapabilityAndroid() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("Android"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.android());
    }

    @Test
    public void setupBrowserCapabilityChrome() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("CHROME"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.chrome());
    }

    @Test
    public void setupBrowserCapabilityEdge() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("edGE"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.edge());
    }

    @Test
    public void setupBrowserCapabilityFirefox() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("firefox"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.firefox());
    }

    @Test
    public void setupBrowserCapabilityHtmlUnit() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("HtmlUnit"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.htmlUnit());
    }

    @Test
    public void setupBrowserCapabilityIE() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("InternetEXPLORER"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.internetExplorer());
    }

    @Test
    public void setupBrowserCapabilityIpad() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("IpaD"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.ipad());
    }

    @Test
    public void setupBrowserCapabilityIphone() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("IPHONE"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.iphone());
    }

    @Test
    public void setupBrowserCapabilityOpera() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("opera"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.operaBlink());
    }

    @Test
    public void setupBrowserCapabilitySafari() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("sAFARi"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.safari());
    }

    @Test
    public void setupBrowserCapabilityPhantom() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("PHANTOMJS"));
        capabilities.setupBrowserCapability();
        assertEquals(capabilities.getDesiredCapabilities(), DesiredCapabilities.phantomjs());
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverNullTest() throws InvalidBrowserException {
        Capabilities capabilities = new Capabilities(null);
        capabilities.setupDriver();
    }

    @Test
    public void addExtraCapabilitiesNoneTest() {
        Capabilities capabilities = new Capabilities(null);
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
        capabilities.addExtraCapabilities(new DesiredCapabilities());
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
    }

    @Test
    public void addExtraCapabilitiesNullTest() {
        Capabilities capabilities = new Capabilities(null);
        capabilities.addExtraCapabilities(null);
        assertEquals(capabilities.getDesiredCapabilities(), new DesiredCapabilities());
    }

    @Test
    public void addExtraCapabilitiesEmptyTest() {
        Capabilities capabilities = new Capabilities(null);
        DesiredCapabilities extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
        extraCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        capabilities.addExtraCapabilities(extraCapabilities);
        assertEquals(capabilities.getDesiredCapabilities(), extraCapabilities);
    }

    @Test
    public void addExtraCapabilitiesFullTest() throws InvalidBrowserException {
        System.setProperty("hub", "somehub");
        Capabilities capabilities = new Capabilities(new Browser("Android"));
        DesiredCapabilities extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
        extraCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        capabilities.setupBrowserCapability();
        capabilities.addExtraCapabilities(extraCapabilities);
        assertEquals(capabilities.getDesiredCapabilities(), extraCapabilities.merge(DesiredCapabilities.android()));
    }

    //TODO - run headless
    //TODO - get browser options

}