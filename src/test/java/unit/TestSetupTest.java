package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.utilities.TestSetup;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSetupTest {

    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private String setBrowser = null;
    private String setHub = null;
    private String setProxy = null;

    @BeforeClass
    public void saveBrowser() {
        if (System.getProperty("browser") != null) {
            setBrowser = System.getProperty("browser");
        }
        if (System.getProperty("hub") != null) {
            setHub = System.getProperty("hub");
        }
        if (System.getProperty("proxy") != null) {
            setProxy = System.getProperty("proxy");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (setBrowser != null) {
            System.setProperty("browser", setBrowser);
        }
        if (setHub != null) {
            System.setProperty("hub", setHub);
        }
        if (setProxy != null) {
            System.setProperty("proxy", setProxy);
        }
    }

    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("browser");
        System.clearProperty("hub");
        System.clearProperty("proxy");
    }

    @Test
    public void setupProxyTest() {
        TestSetup setup = new TestSetup();
        setup.setupProxy();
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.PROXY));

        System.setProperty("proxy", "localhost");
        setup.setupProxy();
        capability = setup.getDesiredCapabilities();
        Proxy export = (Proxy) capability.getCapability(CapabilityType.PROXY);
        Assert.assertEquals(export.getHttpProxy(), "localhost");
    }

    @Test
    public void areBrowserDetailsSetNoBrowserTest() {
        Assert.assertFalse(TestSetup.areBrowserDetailsSet());
    }

    @Test
    public void areBrowserDetailsSetSingleBrowserTest() {
        Assert.assertFalse(TestSetup.areBrowserDetailsSet());

        System.setProperty("browser", "CHROME");
        Assert.assertFalse(TestSetup.areBrowserDetailsSet());

        System.setProperty("browser", "browserName=CHROME");
        Assert.assertTrue(TestSetup.areBrowserDetailsSet());
    }

    @Test
    public void areBrowserDetailsSetMultipleBrowserTest() {
        System.setProperty("browser", "CHROME,FIREFOX");
        Assert.assertFalse(TestSetup.areBrowserDetailsSet());

        System.setProperty("browser", "browserName=CHROME,browserName=FIREFOX");
        Assert.assertTrue(TestSetup.areBrowserDetailsSet());
    }

    @Test
    public void setBrowserNoBrowserTest() throws InvalidBrowserException {
        Assert.assertTrue(TestSetup.setBrowser().isEmpty());
        Assert.assertEquals(TestSetup.setBrowser(), new ArrayList<Browser>());
    }

    @Test
    public void setBrowserSingleBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "CHROME");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 1);
        Assert.assertTrue(browsers.contains(Browser.CHROME));

        System.setProperty("browser", "browserName=CHROME");
        browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 1);
        Assert.assertTrue(browsers.contains(Browser.CHROME));
    }

    @Test
    public void setBrowserBadBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "browserNaMe=CHROME");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 1);
        Assert.assertNull(browsers.get(0));
    }

    @Test
    public void setBrowserMultipleBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "CHROME,FIREFOX");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 2);
        Assert.assertTrue(browsers.contains(Browser.CHROME));
        Assert.assertTrue(browsers.contains(Browser.FIREFOX));

        System.setProperty("browser", "browserName=CHROME,browserName=FIREFOX");
        browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 2);
        Assert.assertTrue(browsers.contains(Browser.CHROME));
        Assert.assertTrue(browsers.contains(Browser.FIREFOX));
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setBrowserIllegalBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "BadBrowser");
        TestSetup.setBrowser();
    }

    @Test
    public void setupBrowserDetailsBrowserNameTest() {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.BROWSER_NAME));
        Assert.assertNull(capability.getCapability(CapabilityType.BROWSER_NAME));

        setup.setupBrowserDetails(new HashMap<>());
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.BROWSER_NAME));
        Assert.assertNull(capability.getCapability(CapabilityType.BROWSER_NAME));

        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("browserName", "CHROME");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "CHROME");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "CHROME");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setupBrowserDetailsBrowserNameIllegalBrowserTest() {
        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("browserName", "BadBrowser");
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(browserDetails);
    }

    @Test
    public void setupBrowserDetailsBrowserVersionTest() {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.VERSION));
        Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

        setup.setupBrowserDetails(new HashMap<>());
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.VERSION));
        Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("browserName", "CHROME");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.VERSION));
        Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

        browserDetails.put("browserVersion", "50");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getVersion(), "50");
        Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "50");

        browserDetails.put("browserVersion", "49.1.1");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getVersion(), "49.1.1");
        Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "49.1.1");

        browserDetails.put("browserVersion", "latest");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getVersion(), "latest");
        Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "latest");
    }

    @Test
    public void setupBrowserDetailsDeviceNameTest() {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("deviceName"));
        Assert.assertNull(capability.getCapability("deviceName"));

        setup.setupBrowserDetails(new HashMap<>());
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("deviceName"));
        Assert.assertNull(capability.getCapability("deviceName"));

        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("browserName", "CHROME");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("deviceName"));
        Assert.assertNull(capability.getCapability("deviceName"));

        browserDetails.put("deviceName", "Android");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getCapability("deviceName"), "Android");
    }

    @Test
    public void setupBrowserDetailsDeviceOrientationTest() {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("device-orientation"));
        Assert.assertNull(capability.getCapability("device-orientation"));

        setup.setupBrowserDetails(new HashMap<>());
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("device-orientation"));
        Assert.assertNull(capability.getCapability("device-orientation"));

        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("browserName", "CHROME");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("device-orientation"));
        Assert.assertNull(capability.getCapability("device-orientation"));

        browserDetails.put("deviceOrientation", "Landscape");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getCapability("device-orientation"), "Landscape");
    }

    @Test
    public void setupBrowserDetailsDevicePlatformTest() {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
        Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

        setup.setupBrowserDetails(new HashMap<>());
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
        Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("browserName", "CHROME");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
        Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

        browserDetails.put("devicePlatform", "Windows 10");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getPlatform().toString(), "WIN10");
        Assert.assertEquals(capability.getCapability(CapabilityType.PLATFORM).toString(), "WIN10");

        browserDetails.put("devicePlatform", "Linux");
        setup.setupBrowserDetails(browserDetails);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getPlatform().toString(), "LINUX");
        Assert.assertEquals(capability.getCapability(CapabilityType.PLATFORM).toString(), "LINUX");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setupBrowserDetailsIllegalDevicePlatformTest() {
        Map<String, String> browserDetails = new HashMap<>();
        browserDetails.put("devicePlatform", "Sun");
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(browserDetails);
        setup.getDesiredCapabilities().getPlatform();
    }

    @Test
    public void setupBrowserCapabilityAndroidTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.ANDROID);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "android");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "android");
    }

    @Test
    public void setupBrowserCapabilityCHROMETest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.CHROME);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "chrome");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "chrome");
    }

    @Test
    public void setupBrowserCapabilityEdgeTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.EDGE);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "MicrosoftEdge");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "MicrosoftEdge");
    }

    @Test
    public void setupBrowserCapabilityFIREFOXTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.FIREFOX);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "firefox");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "firefox");
    }

    @Test
    public void setupBrowserCapabilityHtmlUnitTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.HTMLUNIT);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "htmlunit");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "htmlunit");
    }

    @Test
    public void setupBrowserCapabilityInternetExplorerTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.INTERNETEXPLORER);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "internet explorer");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "internet explorer");
    }

    @Test
    public void setupBrowserCapabilityIpadTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.IPAD);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "iPad");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "iPad");
    }

    @Test
    public void setupBrowserCapabilityIphoneTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.IPHONE);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "iPhone");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "iPhone");
    }

    @Test
    public void setupBrowserCapabilityMaronetteTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.MARIONETTE);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "firefox");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "firefox");
        Assert.assertTrue((Boolean) capability.getCapability("marionette"));
    }

    @Test
    public void setupBrowserCapabilityOperaTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.OPERA);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "operablink");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "operablink");
    }

    @Test
    public void setupBrowserCapabilityPhantomJSTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.PHANTOMJS);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "phantomjs");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "phantomjs");
    }

    @Test
    public void setupBrowserCapabilitySafariTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.SAFARI);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "safari");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "safari");
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupBrowserCapabilityIllegalBrowserTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(Browser.NONE);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverAndroidTest() throws InvalidBrowserException {
        TestSetup.setupDriver(Browser.ANDROID, capabilities);
    }

    // @Test
    // public void setupDriverCHROMETest() throws InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.CHROME,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(), "ChromeDriver");
    // driver.quit();
    // }

    // @Test
    // public void setupDriverEdgeTest() throws InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.Edge,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(),
    // "MicrosoftEdgeDriver");
    // driver.quit();
    // }

    // @Test
    // public void setupDriverFIREFOXTest() throws InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.FIREFOX,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
    // driver.quit();
    // }

    @Test
    public void setupDriverHtmlUnitTest() throws InvalidBrowserException {
        WebDriver driver = TestSetup.setupDriver(Browser.HTMLUNIT, capabilities);
        Assert.assertEquals(driver.getClass().getSimpleName(), "CustomHtmlUnitDriver");
        driver.quit();
    }

    // @Test
    // public void setupDriverInternetExplorerTest() throws
    // InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.InternetExplorer,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(),
    // "InternetExplorerDriver");
    // driver.quit();
    // }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverIpadTest() throws InvalidBrowserException {
        TestSetup.setupDriver(Browser.IPAD, capabilities);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverIphoneTest() throws InvalidBrowserException {
        TestSetup.setupDriver(Browser.IPAD, capabilities);
    }

    // @Test
    // public void setupDriverMarionetteTest() throws InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.Marionette,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
    // driver.quit();
    // }

    // @Test
    // public void setupDriverOperaTest() throws InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.Opera,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(), "OperaDriver");
    // driver.quit();
    // }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverPhantomJSTest() throws InvalidBrowserException {
        WebDriver driver = TestSetup.setupDriver(Browser.PHANTOMJS, capabilities);
        Assert.assertEquals(driver.getClass().getSimpleName(), "PhantomDriver");
        driver.quit();
    }

    // @Test
    // public void setupDriverSafariTest() throws InvalidBrowserException {
    // WebDriver driver = SeleniumSetup.setupDriver(Browser.Safari,
    // capabilities);
    // Assert.assertEquals(driver.getClass().getSimpleName(), "SafariDriver");
    // driver.quit();
    // }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverIllegalBrowserTest() throws InvalidBrowserException {
        TestSetup.setupDriver(Browser.NONE, capabilities);
    }

    @Test
    public void getRandomStringLengthTest() {
        Assert.assertEquals(TestSetup.getRandomString(0).length(), 0);
        Assert.assertEquals(TestSetup.getRandomString(0), "");
        Assert.assertEquals(TestSetup.getRandomString(999).length(), 999);
        Assert.assertTrue(TestSetup.getRandomString(999).matches("^[A-Za-z0-9]{999}$"));
        Assert.assertEquals(TestSetup.getRandomString(-1).length(), 0);
        Assert.assertEquals(TestSetup.getRandomString(-1), "");
    }

    @Test
    public void removeNonWordCharactersTest() {
        Assert.assertEquals(TestSetup.removeNonWordCharacters(null), null);
        Assert.assertEquals(TestSetup.removeNonWordCharacters(""), "");
        Assert.assertEquals(TestSetup.removeNonWordCharacters("hello world"), "helloworld");
        Assert.assertEquals(TestSetup.removeNonWordCharacters("hello-world"), "helloworld");
        Assert.assertEquals(TestSetup.removeNonWordCharacters("hello_world"), "helloworld");
        Assert.assertEquals(TestSetup.removeNonWordCharacters("hello`~!@#$%^&*()world"), "helloworld");
        Assert.assertEquals(TestSetup.removeNonWordCharacters("hello[]\\{}|;':\",./<>?world"), "helloworld");
    }

    @Test
    public void capitalizeFirstLettersTest() {
        Assert.assertEquals(TestSetup.capitalizeFirstLetters(null), null);
        Assert.assertEquals(TestSetup.capitalizeFirstLetters("hello world"), "Hello World");
        Assert.assertEquals(TestSetup.capitalizeFirstLetters("helloWorld"), "HelloWorld");
        Assert.assertEquals(TestSetup.capitalizeFirstLetters("hello_world"), "Hello_World");
        Assert.assertEquals(TestSetup.capitalizeFirstLetters("123helloWorld"), "123HelloWorld");
        Assert.assertEquals(TestSetup.capitalizeFirstLetters("hello123world"), "Hello123World");
        Assert.assertEquals(TestSetup.capitalizeFirstLetters("helloWorld123"), "HelloWorld123");
    }

    @Test
    public void getTestNameTest(Method method) {
        Assert.assertEquals(TestSetup.getTestName(method), "unit_TestSetupTest_getTestNameTest");
        Object[] options = new Object[]{"Python", "public"};
        Assert.assertEquals(TestSetup.getTestName(method, options),
                "unit_TestSetupTest_getTestNameTestWithOptionPython");
        options = new Object[]{"Python", null};
        Assert.assertEquals(TestSetup.getTestName(method, options),
                "unit_TestSetupTest_getTestNameTestWithOptionPython");
        Assert.assertEquals(TestSetup.getTestName("", "UnitTests", "helloWorld"), "UnitTests_helloWorld");
        Assert.assertEquals(TestSetup.getTestName("", "UnitTests", "helloWorld", "python"),
                "UnitTests_helloWorldWithOptionPython");
        Assert.assertEquals(TestSetup.getTestName("", "UnitTests", "helloWorld", "visual basic"),
                "UnitTests_helloWorldWithOptionVisualbasic");
        Assert.assertEquals(TestSetup.getTestName("", "UnitTests", "helloWorld", "Python"),
                "UnitTests_helloWorldWithOptionPython");
        Assert.assertEquals(TestSetup.getTestName("", "UnitTests", "helloWorld", "Python", "Perl"),
                "UnitTests_helloWorldWithOptionPythonPerl");
        Assert.assertEquals(
                TestSetup.getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                        "Gosu", "Powershell", "Squeak", "Gambas"),
                "UnitTests_helloWorldWithOptionPythonPerlBashJavaRubyGroovyJavascriptPHPScalaFortanLispCOBOLErlangPacalHaskellSwiftElixirBASICTclRustVisualBasicCeylonCobraForthCurryCOMOLGosuPowershellSqueakGambas");
        String testName = TestSetup.getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby",
                "Groovy", "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell",
                "Swift", "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        Assert.assertTrue(testName.matches("^UnitTests_helloWorld@[0-9a-f]+$"));
        testName = TestSetup.getTestName("unit", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby",
                "Groovy", "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell",
                "Swift", "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        Assert.assertTrue(testName.matches("^unit_UnitTests_helloWorld@[0-9a-f]+$"));
    }

    @Test
    public void parseMapTest() {
        Map<String, String> map = TestSetup.parseMap("A=B&C=D&E=F");
        Assert.assertTrue(map.containsKey("A"));
        Assert.assertEquals("B", map.get("A"));

        Assert.assertTrue(map.containsKey("C"));
        Assert.assertEquals("D", map.get("C"));

        Assert.assertTrue(map.containsKey("E"));
        Assert.assertEquals("F", map.get("E"));
    }
}