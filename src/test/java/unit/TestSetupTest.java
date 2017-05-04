package unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import selenified.exceptions.InvalidBrowserException;
import tools.TestSetup;
import tools.output.Action.Browsers;

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

		System.setProperty("browser", "Chrome");
		Assert.assertFalse(TestSetup.areBrowserDetailsSet());

		System.setProperty("browser", "browserName=Chrome");
		Assert.assertTrue(TestSetup.areBrowserDetailsSet());
	}

	@Test
	public void areBrowserDetailsSetMultipleBrowserTest() {
		System.setProperty("browser", "Chrome,Firefox");
		Assert.assertFalse(TestSetup.areBrowserDetailsSet());

		System.setProperty("browser", "browserName=Chrome,browserName=Firefox");
		Assert.assertTrue(TestSetup.areBrowserDetailsSet());
	}

	@Test
	public void setBrowserNoBrowserTest() {
		Assert.assertTrue(TestSetup.setBrowser().isEmpty());
		Assert.assertEquals(TestSetup.setBrowser(), new ArrayList<Browsers>());
	}

	@Test
	public void setBrowserSingleBrowserTest() {
		System.setProperty("browser", "Chrome");
		List<Browsers> browsers = TestSetup.setBrowser();
		Assert.assertEquals(browsers.size(), 1);
		Assert.assertTrue(browsers.contains(Browsers.Chrome));

		System.setProperty("browser", "browserName=Chrome");
		browsers = TestSetup.setBrowser();
		Assert.assertEquals(browsers.size(), 1);
		Assert.assertTrue(browsers.contains(Browsers.Chrome));
	}

	@Test
	public void setBrowserMultipleBrowserTest() {
		System.setProperty("browser", "Chrome,Firefox");
		List<Browsers> browsers = TestSetup.setBrowser();
		Assert.assertEquals(browsers.size(), 2);
		Assert.assertTrue(browsers.contains(Browsers.Chrome));
		Assert.assertTrue(browsers.contains(Browsers.Firefox));

		System.setProperty("browser", "browserName=Chrome,browserName=Firefox");
		browsers = TestSetup.setBrowser();
		Assert.assertEquals(browsers.size(), 2);
		Assert.assertTrue(browsers.contains(Browsers.Chrome));
		Assert.assertTrue(browsers.contains(Browsers.Firefox));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setBrowserIllegalBrowserTest() {
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
		browserDetails.put("browserName", "Chrome");
		setup.setupBrowserDetails(browserDetails);
		capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "Chrome");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "Chrome");
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
		browserDetails.put("browserName", "Chrome");
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
		browserDetails.put("browserName", "Chrome");
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
		browserDetails.put("browserName", "Chrome");
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
		browserDetails.put("browserName", "Chrome");
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
		setup.setupBrowserCapability(Browsers.Android);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "android");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "android");
	}

	@Test
	public void setupBrowserCapabilityChromeTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Chrome);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "chrome");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "chrome");
	}

	@Test
	public void setupBrowserCapabilityEdgeTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Edge);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "MicrosoftEdge");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "MicrosoftEdge");
	}

	@Test
	public void setupBrowserCapabilityFirefoxTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Firefox);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "firefox");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "firefox");
	}

	@Test
	public void setupBrowserCapabilityHtmlUnitTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.HtmlUnit);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "htmlunit");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "htmlunit");
	}

	@Test
	public void setupBrowserCapabilityInternetExplorerTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.InternetExplorer);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "internet explorer");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "internet explorer");
	}

	@Test
	public void setupBrowserCapabilityIpadTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Ipad);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "iPad");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "iPad");
	}

	@Test
	public void setupBrowserCapabilityIphoneTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Iphone);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "iPhone");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "iPhone");
	}

	@Test
	public void setupBrowserCapabilityMaronetteTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Marionette);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "firefox");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "firefox");
		Assert.assertTrue((Boolean) capability.getCapability("marionette"));
	}

	@Test
	public void setupBrowserCapabilityOperaTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Opera);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "operablink");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "operablink");
	}

	@Test
	public void setupBrowserCapabilityPhantomJSTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.PhantomJS);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "phantomjs");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "phantomjs");
	}

	@Test
	public void setupBrowserCapabilitySafariTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.Safari);
		DesiredCapabilities capability = setup.getDesiredCapabilities();
		Assert.assertEquals(capability.getBrowserName(), "safari");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "safari");
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupBrowserCapabilityIllegalBrowserTest() throws InvalidBrowserException {
		TestSetup setup = new TestSetup();
		setup.setupBrowserCapability(Browsers.None);
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverAndroidTest() throws InvalidBrowserException {
		TestSetup.setupDriver(Browsers.Android, capabilities);
	}

	// @Test
	// public void setupDriverChromeTest() throws InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.Chrome,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(), "ChromeDriver");
	// driver.quit();
	// }

	// @Test
	// public void setupDriverEdgeTest() throws InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.Edge,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(),
	// "MicrosoftEdgeDriver");
	// driver.quit();
	// }

	// @Test
	// public void setupDriverFirefoxTest() throws InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.Firefox,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
	// driver.quit();
	// }

	@Test
	public void setupDriverHtmlUnitTest() throws InvalidBrowserException {
		WebDriver driver = TestSetup.setupDriver(Browsers.HtmlUnit, capabilities);
		Assert.assertEquals(driver.getClass().getSimpleName(), "HtmlUnitDriver");
		driver.quit();
	}

	// @Test
	// public void setupDriverInternetExplorerTest() throws
	// InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.InternetExplorer,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(),
	// "InternetExplorerDriver");
	// driver.quit();
	// }

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverIpadTest() throws InvalidBrowserException {
		TestSetup.setupDriver(Browsers.Ipad, capabilities);
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverIphoneTest() throws InvalidBrowserException {
		TestSetup.setupDriver(Browsers.Iphone, capabilities);
	}

	// @Test
	// public void setupDriverMarionetteTest() throws InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.Marionette,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
	// driver.quit();
	// }

	// @Test
	// public void setupDriverOperaTest() throws InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.Opera,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(), "OperaDriver");
	// driver.quit();
	// }

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverPhantomJSTest() throws InvalidBrowserException {
		WebDriver driver = TestSetup.setupDriver(Browsers.PhantomJS, capabilities);
		Assert.assertEquals(driver.getClass().getSimpleName(), "PhantomDriver");
		driver.quit();
	}

	// @Test
	// public void setupDriverSafariTest() throws InvalidBrowserException {
	// WebDriver driver = SeleniumSetup.setupDriver(Browsers.Safari,
	// capabilities);
	// Assert.assertEquals(driver.getClass().getSimpleName(), "SafariDriver");
	// driver.quit();
	// }

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverIllegalBrowserTest() throws InvalidBrowserException {
		TestSetup.setupDriver(Browsers.None, capabilities);
	}
}