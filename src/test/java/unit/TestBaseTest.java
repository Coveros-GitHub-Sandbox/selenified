package unit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import selenified.exceptions.InvalidBrowserException;
import tools.TestBase;
import tools.output.Selenium.Browsers;

public class TestBaseTest extends TestBase {

	private String setAppURL = null;
	private String setBrowser = null;
	private String setHub = null;
	private String setProxy = null;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws InvalidBrowserException {
		// add some extra capabilities
		extraCapabilities = new DesiredCapabilities();
		extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
		extraCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
		// save our passed in information
		if (System.getProperty("appURL") != null) {
			setAppURL = System.getProperty("appURL");
			System.clearProperty("appURL");
		}
		if (System.getProperty("browser") != null) {
			setBrowser = System.getProperty("browser");
			System.clearProperty("browser");
		}
		if (System.getProperty("hub") != null) {
			setHub = System.getProperty("hub");
			System.clearProperty("hub");
		}
		if (System.getProperty("proxy") != null) {
			setProxy = System.getProperty("proxy");
			System.clearProperty("proxy");
		}
		super.beforeSuite();
	}

	@AfterClass
	public void restoreBrowser() {
		if (setAppURL != null) {
			System.setProperty("appURL", setAppURL);
		}
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
		System.clearProperty("appURL");
		System.clearProperty("browser");
		System.clearProperty("hub");
		System.clearProperty("proxy");
	}
	
	@Override
	public void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
		// do nothing
	}

	@Override
	public void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
		// do nothing
	}

	@Test
	public void initializeSystemTest() {
		TestBase.initializeSystem();
		Assert.assertEquals(System.getProperty("browser"), "HTMLUNIT");
		Assert.assertEquals(getTestSite(), "http://www.google.com/");

		System.setProperty("browser", "Chrome");
		System.setProperty("appURL", "http://www.yahoo.com");
		TestBase.initializeSystem();
		Assert.assertEquals(System.getProperty("browser"), "Chrome");
		Assert.assertEquals(getTestSite(), "http://www.yahoo.com");
	}

	@Test(expectedExceptions = InvalidBrowserException.class)
	public void setupTestParametersBadBrowserTest() throws InvalidBrowserException {
		System.setProperty("browser", "BadBrowser");
		TestBase.setupTestParameters();
	}

	@Test
	public void setupTestParametersSingleBrowserTest() throws InvalidBrowserException {
		List<Browsers> expectedBrowsers = new ArrayList<Browsers>();
		expectedBrowsers.add(Browsers.CHROME);
		System.setProperty("browser", "CHROME");

		TestBase.setupTestParameters();
		Assert.assertEquals(TestBase.browsers, expectedBrowsers);
	}

	@Test
	public void setupTestParametersMultipleBrowserTest() throws InvalidBrowserException {
		List<Browsers> expectedBrowsers = new ArrayList<Browsers>();
		expectedBrowsers.add(Browsers.CHROME);
		expectedBrowsers.add(Browsers.EDGE);
		System.setProperty("browser", "CHROME,EDGE");

		TestBase.setupTestParameters();
		Assert.assertEquals(TestBase.browsers, expectedBrowsers);
	}

	@Test
	public void extraCapabilitiesTest() {
		DesiredCapabilities capability = capabilities.get(0);
		Assert.assertTrue((boolean) capability.getCapability("ignoreProtectedModeSettings"));
		Assert.assertEquals(capability.getCapability("unexpectedAlertBehaviour"), "ignore");
	}
}