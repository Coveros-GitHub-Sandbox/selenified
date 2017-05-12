package unit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tools.TestBase;

public class TestBaseTest {
	
	private String setAppURL = null;
	private String setBrowser = null;
	private String setHub = null;
	private String setProxy = null;

	@BeforeClass
	public void saveBrowser() {
		if (System.getProperty("appURL") != null) {
			setAppURL = System.getProperty("appURL");
		}
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

	@Test
	public void initializeSystemTest() {
		TestBase.initializeSystem();
		Assert.assertEquals( System.getProperty("browser"), "HtmlUnit");
		Assert.assertEquals( TestBase.testSite, "http://www.google.com/");

		System.setProperty("browser", "Chrome");
		System.setProperty("appURL", "www.yahoo.com");
		TestBase.initializeSystem();
		Assert.assertEquals( System.getProperty("browser"), "Chrome");
		Assert.assertEquals( TestBase.testSite, "www.yahoo.com");
	}
}
