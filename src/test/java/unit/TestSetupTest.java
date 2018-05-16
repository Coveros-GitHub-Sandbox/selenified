package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
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
import java.util.List;
import java.util.Map;
import java.util.*;

public class TestSetupTest {

    private final DesiredCapabilities capabilities = new DesiredCapabilities();
    private String setBrowser = null;
    private String setHub = null;
    private String setProxy = null;
    private String setHeadless = null;
    private String setOptions = null;

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
        if (System.getProperty("headless") != null) {
            setHeadless = System.getProperty("headless");
        }
        if (System.getProperty("options") != null) {
            setOptions = System.getProperty("options");
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
        if (setHeadless != null) {
            System.setProperty("headless", setHeadless);
        }
        if (setOptions != null) {
            System.setProperty("headless", setOptions);
        }
    }

    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("browser");
        System.clearProperty("hub");
        System.clearProperty("proxy");
        System.clearProperty("headless");
        System.clearProperty("options");
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
        Browser browser = browsers.get(0);
        Assert.assertEquals(browser.getName(), BrowserName.CHROME);

        System.setProperty("browser", "browserName=CHROME");
        browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 1);
        browser = browsers.get(0);
        Assert.assertEquals(browser.getName(), BrowserName.CHROME);
    }

    @Test
    public void setBrowserMultipleBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "CHROME,FIREFOX");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 2);
        Browser chrome = browsers.get(0);
        Assert.assertEquals(chrome.getName(), BrowserName.CHROME);
        Browser firefox = browsers.get(1);
        Assert.assertEquals(firefox.getName(), BrowserName.FIREFOX);

        System.setProperty("browser", "browserName=CHROME,browserName=FIREFOX");
        browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 2);
        chrome = browsers.get(0);
        Assert.assertEquals(chrome.getName(), BrowserName.CHROME);
        firefox = browsers.get(1);
        Assert.assertEquals(firefox.getName(), BrowserName.FIREFOX);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setBrowserBadBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "browserNaMe=CHROME");
        List<Browser> browsers = TestSetup.setBrowser();
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setBrowserIllegalBrowserTest() throws InvalidBrowserException {
        System.setProperty("browser", "BadBrowser");
        TestSetup.setBrowser();
    }

    @Test
    public void setBrowserSingleBrowserDetailsTest() throws InvalidBrowserException {
        System.setProperty("browser", "browserName=CHROME");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 1);
        Browser browser = browsers.get(0);
        Assert.assertEquals(browser.getName(), BrowserName.CHROME);
        Assert.assertNull(browser.getVersion());
        Assert.assertNull(browser.getDevice());
        Assert.assertNull(browser.getOrientation());
        Assert.assertNull(browser.getPlatform());
    }

    @Test
    public void setBrowserSingleBrowserMultipleDetailsTest() throws InvalidBrowserException {
        System.setProperty("browser", "browserName=CHROME&browserVersion=15.1&deviceName=Android&deviceOrientation" +
                "=Landscape&devicePlatform=Windows 10");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 1);
        Browser browser = browsers.get(0);
        Assert.assertEquals(browser.getName(), BrowserName.CHROME);
        Assert.assertEquals(browser.getVersion(), "15.1");
        Assert.assertEquals(browser.getDevice(), "Android");
        Assert.assertEquals(browser.getOrientation(), "Landscape");
        Assert.assertEquals(browser.getPlatform(), "Windows 10");
    }

    @Test
    public void setBrowserMultipleBrowserDetailsTest() throws InvalidBrowserException {
        System.setProperty("browser", "browserName=CHROME,browserName=FIREFOX");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 2);
        Browser chrome = browsers.get(0);
        Assert.assertEquals(chrome.getName(), BrowserName.CHROME);
        Browser firefox = browsers.get(1);
        Assert.assertEquals(firefox.getName(), BrowserName.FIREFOX);
    }

    @Test
    public void setBrowserMultipleBrowserMultipleDetailsTest() throws InvalidBrowserException {
        System.setProperty("browser",
                "browserName=CHROME&browserVersion=12," + "browserName=FIREFOX&devicePlatform=Windows 10");
        List<Browser> browsers = TestSetup.setBrowser();
        Assert.assertEquals(browsers.size(), 2);
        Browser chrome = browsers.get(0);
        Assert.assertEquals(chrome.getName(), BrowserName.CHROME);
        Assert.assertEquals(chrome.getVersion(), "12");
        Assert.assertNull(chrome.getDevice());
        Assert.assertNull(chrome.getOrientation());
        Assert.assertNull(chrome.getPlatform());
        Browser firefox = browsers.get(1);
        Assert.assertEquals(firefox.getName(), BrowserName.FIREFOX);
        Assert.assertNull(firefox.getVersion());
        Assert.assertNull(firefox.getDevice());
        Assert.assertNull(firefox.getOrientation());
        Assert.assertEquals(firefox.getPlatform(), "Windows 10");
    }

    @Test
    public void setupBrowserDetailsBrowserNameTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.BROWSER_NAME));
        Assert.assertNull(capability.getCapability(CapabilityType.BROWSER_NAME));

        setup.setupBrowserDetails(new Browser(BrowserName.CHROME));
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "chrome");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "chrome");
    }

    @Test
    public void setupBrowserDetailsBrowserVersionTest() {
        TestSetup setup = new TestSetup();
        setup.setupBrowserDetails(null);
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.VERSION));
        Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

        Browser chrome = new Browser(BrowserName.CHROME);
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.VERSION));
        Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

        chrome.setVersion("50");
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getVersion(), "50");
        Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "50");

        chrome.setVersion("49.1.1");
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getVersion(), "49.1.1");
        Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "49.1.1");

        chrome.setVersion("latest");
        setup.setupBrowserDetails(chrome);
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

        Browser chrome = new Browser(BrowserName.CHROME);
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("deviceName"));
        Assert.assertNull(capability.getCapability("deviceName"));

        chrome.setDevice("Android");
        setup.setupBrowserDetails(chrome);
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

        Browser chrome = new Browser(BrowserName.CHROME);
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is("device-orientation"));
        Assert.assertNull(capability.getCapability("device-orientation"));

        chrome.setOrientation("Landscape");
        setup.setupBrowserDetails(chrome);
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

        Browser chrome = new Browser(BrowserName.CHROME);
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
        Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

        chrome.setPlatform("Windows 10");
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getPlatform().toString(), "WIN10");
        Assert.assertEquals(capability.getCapability(CapabilityType.PLATFORM).toString(), "WIN10");

        chrome.setPlatform("Linux");
        setup.setupBrowserDetails(chrome);
        capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getPlatform().toString(), "LINUX");
        Assert.assertEquals(capability.getCapability(CapabilityType.PLATFORM).toString(), "LINUX");
    }

    @Test
    public void setupBrowserDetailsNonStandardDevicePlatformTest() {
        TestSetup setup = new TestSetup();
        Browser chrome = new Browser(BrowserName.CHROME);
        chrome.setPlatform("Sun");
        setup.setupBrowserDetails(chrome);
        setup.getDesiredCapabilities().getPlatform();
        Assert.assertNull(setup.getDesiredCapabilities().getPlatform());
    }

    @Test
    public void setupBrowserCapabilityAndroidTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.ANDROID));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "android");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "android");
    }

    @Test
    public void setupBrowserCapabilityCHROMETest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.CHROME));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "chrome");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "chrome");
    }

    @Test
    public void setupBrowserCapabilityEdgeTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.EDGE));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "MicrosoftEdge");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "MicrosoftEdge");
    }

    @Test
    public void setupBrowserCapabilityFIREFOXTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.FIREFOX));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "firefox");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "firefox");
    }

    @Test
    public void setupBrowserCapabilityHtmlUnitTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.HTMLUNIT));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "htmlunit");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "htmlunit");
    }

    @Test
    public void setupBrowserCapabilityInternetExplorerTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.INTERNETEXPLORER));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "internet explorer");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "internet explorer");
    }

    @Test
    public void setupBrowserCapabilityIpadTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.IPAD));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "iPad");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "iPad");
    }

    @Test
    public void setupBrowserCapabilityIphoneTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.IPHONE));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "iPhone");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "iPhone");
    }

    @Test
    public void setupBrowserCapabilityOperaTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.OPERA));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "operablink");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "operablink");
    }

    @Test
    public void setupBrowserCapabilityPhantomJSTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.PHANTOMJS));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "phantomjs");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "phantomjs");
    }

    @Test
    public void setupBrowserCapabilitySafariTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.SAFARI));
        DesiredCapabilities capability = setup.getDesiredCapabilities();
        Assert.assertEquals(capability.getBrowserName(), "safari");
        Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "safari");
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupBrowserCapabilityIllegalBrowserTest() throws InvalidBrowserException {
        TestSetup setup = new TestSetup();
        setup.setupBrowserCapability(new Browser(BrowserName.NONE));
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverAndroidTest() throws InvalidBrowserException {
        TestSetup.setupDriver(new Browser(BrowserName.ANDROID), capabilities);
    }

    @Test
    public void setupDriverHtmlUnitTest() throws InvalidBrowserException {
        WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.HTMLUNIT), capabilities);
        Assert.assertEquals(driver.getClass().getSimpleName(), "HtmlUnitDriver");
        driver.quit();
    }

    @Test
    public void setupDriverFirefoxTest() throws InvalidBrowserException {
        System.setProperty("headless", "true");
        WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.FIREFOX), capabilities);
        Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
        driver.quit();
    }

    @Test
    public void setupDriverChromeTest() throws InvalidBrowserException {
        System.setProperty("headless", "true");
        WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.CHROME), capabilities);
        Assert.assertEquals(driver.getClass().getSimpleName(), "ChromeDriver");
        driver.quit();
    }

//     @Test(expectedExceptions = NoSuchMethodError.class)
//     public void setupDriverEdgeTest() throws InvalidBrowserException {
//         WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.EDGE), capabilities);
//         Assert.assertEquals(driver.getClass().getSimpleName(), "MicrosoftEdgeDriver");
//         driver.quit();
//     }

//     @Test(expectedExceptions = NoSuchMethodError.class)
//     public void setupDriverInternetExplorerTest() throws InvalidBrowserException {
//         WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.INTERNETEXPLORER), capabilities);
//         Assert.assertEquals(driver.getClass().getSimpleName(), "InternetExplorerDriver");
//         driver.quit();
//     }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverIpadTest() throws InvalidBrowserException {
        TestSetup.setupDriver(new Browser(BrowserName.IPAD), capabilities);
    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverIphoneTest() throws InvalidBrowserException {
        TestSetup.setupDriver(new Browser(BrowserName.IPHONE), capabilities);
    }

//    @Test(expectedExceptions = NoSuchMethodError.class)
//    public void setupDriverOperaTest() throws InvalidBrowserException {
//        WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.OPERA), capabilities);
//        Assert.assertEquals(driver.getClass().getSimpleName(), "OperaDriver");
//        driver.quit();
//    }

//     @Test(expectedExceptions = NoSuchMethodError.class)
//     public void setupDriverSafariTest() throws InvalidBrowserException {
//         WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.SAFARI), capabilities);
//         Assert.assertEquals(driver.getClass().getSimpleName(), "SafariDriver");
//         driver.quit();
//     }

//    @Test(expectedExceptions = NoSuchMethodError.class)
//    public void setupDriverPhantomJSTest() throws InvalidBrowserException {
//        WebDriver driver = TestSetup.setupDriver(new Browser(BrowserName.PHANTOMJS), capabilities);
//        Assert.assertEquals(driver.getClass().getSimpleName(), "PhantomDriver");
//        driver.quit();
//    }

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void setupDriverIllegalBrowserTest() throws InvalidBrowserException {
        TestSetup.setupDriver(new Browser(BrowserName.NONE), capabilities);
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
        Assert.assertEquals(TestSetup
                        .getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                                "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                                "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                                "Gosu", "Powershell", "Squeak", "Gambas"),
                "UnitTests_helloWorldWithOptionPythonPerlBashJavaRubyGroovyJavascriptPHPScalaFortanLispCOBOLErlangPacalHaskellSwiftElixirBASICTclRustVisualBasicCeylonCobraForthCurryCOMOLGosuPowershellSqueakGambas");
        String testName = TestSetup
                .getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                        "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        Assert.assertTrue(testName.matches("^UnitTests_helloWorld@[0-9a-f]+$"));
        testName = TestSetup
                .getTestName("unit", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
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

    @Test
    public void runHeadlessDefaultTest() {
        Assert.assertFalse(TestSetup.runHeadless());
    }

    @Test
    public void runHeadlessFalseTest() {
        System.setProperty("headless", "false");
        Assert.assertFalse(TestSetup.runHeadless());
    }

    @Test
    public void runHeadlessTrueTest() {
        System.setProperty("headless", "true");
        Assert.assertTrue(TestSetup.runHeadless());
    }

    @Test
    public void getBrowserOptionsDefaultTest() {
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.FIREFOX), new ArrayList<>());
    }

    @Test
    public void getBrowserOptionsDisableGPUTest() {
        System.setProperty("options", "--disable-gpu");
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.FIREFOX), Arrays.asList("--disable-gpu"));
    }

    @Test
    public void getBrowserOptionsTwoTest() {
        System.setProperty("options", "--disable-gpu,--hi-there");
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.FIREFOX), Arrays.asList("--disable-gpu","--hi-there"));
        Assert.assertFalse(TestSetup.runHeadless());
    }

    @Test
    public void getBrowserOptionsChromeHeadlessTest() {
        System.setProperty("options", "--headless");
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.CHROME), new ArrayList<>());
        Assert.assertTrue(TestSetup.runHeadless());
    }

    @Test
    public void getBrowserOptionsFirefoxHeadlessTest() {
        System.setProperty("options", "-headless");
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.FIREFOX), new ArrayList<>());
        Assert.assertTrue(TestSetup.runHeadless());
    }

    @Test
    public void getBrowserOptionsChromeFullTest() {
        System.setProperty("options", "--disable-gpu,--headless,--hi-there");
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.CHROME), Arrays.asList("--disable-gpu","--hi-there"));
        Assert.assertTrue(TestSetup.runHeadless());
    }

    @Test
    public void getBrowserOptionsFirefoxFullTest() {
        System.setProperty("options", "--disable-gpu,-headless,--hi-there");
        Assert.assertEquals(TestSetup.getBrowserOptions(BrowserName.FIREFOX), Arrays.asList("--disable-gpu","--hi-there"));
        Assert.assertTrue(TestSetup.runHeadless());
    }
}