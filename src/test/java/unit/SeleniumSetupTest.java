package unit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import selenified.exceptions.InvalidBrowserException;
import tools.selenium.SeleniumHelper.Browsers;
import tools.selenium.SeleniumSetup;

public class SeleniumSetupTest {

	private DesiredCapabilities capabilities = new DesiredCapabilities();

	@Test
	public void setupProxyTest() {
		DesiredCapabilities capability = SeleniumSetup.setupProxy(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.PROXY));

		System.setProperty("proxy", "localhost");
		capability = SeleniumSetup.setupProxy(capabilities);
		Proxy export = (Proxy) capability.getCapability(CapabilityType.PROXY);
		Assert.assertEquals(export.getHttpProxy(), "localhost");
	}

	@Test
	public void areBrowserDetailsSetTest() {
		Assert.assertFalse(SeleniumSetup.areBrowserDetailsSet());

		System.setProperty("browser", "Chrome");
		Assert.assertFalse(SeleniumSetup.areBrowserDetailsSet());

		System.setProperty("browser", "browserName=Chrome");
		Assert.assertTrue(SeleniumSetup.areBrowserDetailsSet());

		System.clearProperty("browser");
	}

	@Test
	public void setBrowser() {
		Assert.assertNull(SeleniumSetup.setBrowser());

		System.setProperty("browser", "Chrome");
		Assert.assertEquals(SeleniumSetup.setBrowser(), Browsers.Chrome);

		System.setProperty("browser", "browserName=Chrome");
		Assert.assertEquals(SeleniumSetup.setBrowser(), Browsers.Chrome);

		System.clearProperty("browser");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setBrowserIllegalBrowserTest() {
		System.setProperty("browser", "BadBrowser");
		SeleniumSetup.setBrowser();

		System.clearProperty("browser");
	}

	@Test
	public void setupBrowserDetailsBrowserNameTest() {
		DesiredCapabilities capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.BROWSER_NAME));
		Assert.assertNull(capability.getCapability(CapabilityType.BROWSER_NAME));

		System.setProperty("browser", "Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.BROWSER_NAME));
		Assert.assertNull(capability.getCapability(CapabilityType.BROWSER_NAME));

		System.setProperty("browser", "browserName=Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getBrowserName(), "Chrome");
		Assert.assertEquals(capability.getCapability(CapabilityType.BROWSER_NAME), "Chrome");

		System.clearProperty("browser");
	}

	@Test
	public void setupBrowserDetailsBrowserVersionTest() {
		DesiredCapabilities capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.VERSION));
		Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

		System.setProperty("browser", "Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.VERSION));
		Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

		System.setProperty("browser", "browserName=Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.VERSION));
		Assert.assertNull(capability.getCapability(CapabilityType.VERSION));

		System.setProperty("browser", "browserVersion=50");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getVersion(), "50");
		Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "50");

		System.setProperty("browser", "browserVersion=49.1.1");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getVersion(), "49.1.1");
		Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "49.1.1");

		System.setProperty("browser", "browserVersion=latest");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getVersion(), "latest");
		Assert.assertEquals(capability.getCapability(CapabilityType.VERSION), "latest");

		System.clearProperty("browser");
	}

	@Test
	public void setupBrowserDetailsDeviceNameTest() {
		DesiredCapabilities capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is("deviceName"));
		Assert.assertNull(capability.getCapability("deviceName"));

		System.setProperty("browser", "Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is("deviceName"));
		Assert.assertNull(capability.getCapability("deviceName"));

		System.setProperty("browser", "browserName=Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is("deviceName"));
		Assert.assertNull(capability.getCapability("deviceName"));

		System.setProperty("browser", "deviceName=Android");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getCapability("deviceName"), "Android");

		System.clearProperty("browser");
	}

	@Test
	public void setupBrowserDetailsDeviceOrientationTest() {
		DesiredCapabilities capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is("device-orientation"));
		Assert.assertNull(capability.getCapability("device-orientation"));

		System.setProperty("browser", "Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is("device-orientation"));
		Assert.assertNull(capability.getCapability("device-orientation"));

		System.setProperty("browser", "browserName=Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is("device-orientation"));
		Assert.assertNull(capability.getCapability("device-orientation"));

		System.setProperty("browser", "deviceOrientation=Landscape");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getCapability("device-orientation"), "Landscape");

		System.clearProperty("browser");
	}

	@Test
	public void setupBrowserDetailsDevicePlatformTest() {
		DesiredCapabilities capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
		Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

		System.setProperty("browser", "Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
		Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

		System.setProperty("browser", "browserName=Chrome");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertFalse(capability.is(CapabilityType.PLATFORM));
		Assert.assertNull(capability.getCapability(CapabilityType.PLATFORM));

		System.setProperty("browser", "devicePlatform=Windows 10");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getPlatform().toString(), "WIN10");
		Assert.assertEquals(capability.getCapability(CapabilityType.PLATFORM).toString(), "WIN10");

		System.setProperty("browser", "devicePlatform=Linux");
		capability = SeleniumSetup.setupBrowserDetails(capabilities);
		Assert.assertEquals(capability.getPlatform().toString(), "LINUX");
		Assert.assertEquals(capability.getCapability(CapabilityType.PLATFORM).toString(), "LINUX");

		System.clearProperty("browser");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void setupBrowserDetailsIllegalDevicePlatformTest() {
		System.setProperty("browser", "devicePlatform=Sun");
		SeleniumSetup.setupBrowserDetails(capabilities).getPlatform();

		System.clearProperty("browser");
	}

	@Test
	public void setupBrowserCapabilityAndroidTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Android).getBrowserName(), "android");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Android).getCapability(CapabilityType.BROWSER_NAME),
				"android");
	}

	@Test
	public void setupBrowserCapabilityChromeTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Chrome).getBrowserName(), "chrome");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Chrome).getCapability(CapabilityType.BROWSER_NAME),
				"chrome");
	}

	@Test
	public void setupBrowserCapabilityEdgeTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Edge).getBrowserName(), "MicrosoftEdge");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Edge).getCapability(CapabilityType.BROWSER_NAME),
				"MicrosoftEdge");
	}

	@Test
	public void setupBrowserCapabilityFirefoxTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Firefox).getBrowserName(), "firefox");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Firefox).getCapability(CapabilityType.BROWSER_NAME),
				"firefox");
	}

	@Test
	public void setupBrowserCapabilityHtmlUnitTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.HtmlUnit).getBrowserName(), "htmlunit");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.HtmlUnit).getCapability(CapabilityType.BROWSER_NAME),
				"htmlunit");
	}

	@Test
	public void setupBrowserCapabilityInternetExplorerTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.InternetExplorer).getBrowserName(),
				"internet explorer");
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.InternetExplorer)
				.getCapability(CapabilityType.BROWSER_NAME), "internet explorer");
	}

	@Test
	public void setupBrowserCapabilityIpadTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Ipad).getBrowserName(), "iPad");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Ipad).getCapability(CapabilityType.BROWSER_NAME), "iPad");
	}

	@Test
	public void setupBrowserCapabilityIphoneTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Iphone).getBrowserName(), "iPhone");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Iphone).getCapability(CapabilityType.BROWSER_NAME),
				"iPhone");
	}

	@Test
	public void setupBrowserCapabilityMaronetteTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Marionette).getBrowserName(), "firefox");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Marionette).getCapability(CapabilityType.BROWSER_NAME),
				"firefox");
		Assert.assertTrue(
				(Boolean) SeleniumSetup.setupBrowserCapability(Browsers.Marionette).getCapability("marionette"));
	}

	@Test
	public void setupBrowserCapabilityOperaTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Opera).getBrowserName(), "operablink");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Opera).getCapability(CapabilityType.BROWSER_NAME),
				"operablink");
	}

	@Test
	public void setupBrowserCapabilityPhantomJSTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.PhantomJS).getBrowserName(), "phantomjs");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.PhantomJS).getCapability(CapabilityType.BROWSER_NAME),
				"phantomjs");
	}

	@Test
	public void setupBrowserCapabilitySafariTest() throws InvalidBrowserException {
		Assert.assertEquals(SeleniumSetup.setupBrowserCapability(Browsers.Safari).getBrowserName(), "safari");
		Assert.assertEquals(
				SeleniumSetup.setupBrowserCapability(Browsers.Safari).getCapability(CapabilityType.BROWSER_NAME),
				"safari");
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupBrowserCapabilityIllegalBrowserTest() throws InvalidBrowserException {
		SeleniumSetup.setupBrowserCapability(Browsers.None);
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverAndroidTest() throws InvalidBrowserException {
		SeleniumSetup.setupDriver(Browsers.Android, capabilities);
	}

	@Test
	public void setupDriverChromeTest() throws InvalidBrowserException {
		WebDriver driver = SeleniumSetup.setupDriver(Browsers.Chrome, capabilities);
		Assert.assertEquals(driver.getClass().getSimpleName(), "ChromeDriver");
		driver.quit();
	}

//	@Test
//	public void setupDriverEdgeTest() throws InvalidBrowserException {
//		WebDriver driver = SeleniumSetup.setupDriver(Browsers.Edge, capabilities);
//		Assert.assertEquals(driver.getClass().getSimpleName(), "MicrosoftEdgeDriver");
//		driver.quit();
//	}

//	@Test
//	public void setupDriverFirefoxTest() throws InvalidBrowserException {
//		WebDriver driver = SeleniumSetup.setupDriver(Browsers.Firefox, capabilities);
//		Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
//		driver.quit();
//	}

	@Test
	public void setupDriverHtmlUnitTest() throws InvalidBrowserException {
		WebDriver driver = SeleniumSetup.setupDriver(Browsers.HtmlUnit, capabilities);
		Assert.assertEquals(driver.getClass().getSimpleName(), "HtmlUnitDriver");
		driver.quit();
	}

//	@Test
//	public void setupDriverInternetExplorerTest() throws InvalidBrowserException {
//		WebDriver driver = SeleniumSetup.setupDriver(Browsers.InternetExplorer, capabilities);
//		Assert.assertEquals(driver.getClass().getSimpleName(), "InternetExplorerDriver");
//		driver.quit();
//	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverIpadTest() throws InvalidBrowserException {
		SeleniumSetup.setupDriver(Browsers.Ipad, capabilities);
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverIphoneTest() throws InvalidBrowserException {
		SeleniumSetup.setupDriver(Browsers.Iphone, capabilities);
	}

//	@Test
//	public void setupDriverMarionetteTest() throws InvalidBrowserException {
//		WebDriver driver = SeleniumSetup.setupDriver(Browsers.Marionette, capabilities);
//		Assert.assertEquals(driver.getClass().getSimpleName(), "FirefoxDriver");
//		driver.quit();
//	}

//	@Test
//	public void setupDriverOperaTest() throws InvalidBrowserException {
//		WebDriver driver = SeleniumSetup.setupDriver(Browsers.Opera, capabilities);
//		Assert.assertEquals(driver.getClass().getSimpleName(), "OperaDriver");
//		driver.quit();
//	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverPhantomJSTest() throws InvalidBrowserException {
		WebDriver driver = SeleniumSetup.setupDriver(Browsers.PhantomJS, capabilities);
		Assert.assertEquals(driver.getClass().getSimpleName(), "PhantomDriver");
		driver.quit();
	}

//	@Test
//	public void setupDriverSafariTest() throws InvalidBrowserException {
//		WebDriver driver = SeleniumSetup.setupDriver(Browsers.Safari, capabilities);
//		Assert.assertEquals(driver.getClass().getSimpleName(), "SafariDriver");
//		driver.quit();
//	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupDriverIllegalBrowserTest() throws InvalidBrowserException {
		SeleniumSetup.setupDriver(Browsers.None, capabilities);
	}
}