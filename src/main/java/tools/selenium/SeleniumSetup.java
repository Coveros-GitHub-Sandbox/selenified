package tools.selenium;

import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import selenified.exceptions.InvalidBrowserException;
import tools.General;
import tools.selenium.SeleniumHelper.Browsers;

public class SeleniumSetup {

	public static DesiredCapabilities setupProxy(DesiredCapabilities capabilities) {
		// are we running through a proxy
		if (System.getProperty("proxy") != null && !System.getProperty("proxy").equals("${proxy}")) {
			// set our proxy information
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(System.getProperty("proxy"));
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}
		return capabilities;
	}

	public static boolean areBrowserDetailsSet() {
		return !System.getProperty("browser").chars().allMatch(Character::isLetter);

	}

	public static Browsers setBrowser() {
		Browsers browser = null;
		if (!areBrowserDetailsSet()) {
			browser = Browsers.valueOf(System.getProperty("browser"));
		} else {
			Map<String, String> browserDetails = General.parseMap(System.getProperty("browser"));
			if (browserDetails.containsKey("browserName")) {
				browser = Browsers.valueOf(browserDetails.get("browserName"));
			}
		}
		return browser;
	}

	public static DesiredCapabilities setupBrowserDetails(DesiredCapabilities capabilities) {
		// determine our browser information
		if (areBrowserDetailsSet()) {
			Map<String, String> browserDetails = General.parseMap(System.getProperty("browser"));
			if (browserDetails.containsKey("browserName")) {
				capabilities.setCapability(CapabilityType.BROWSER_NAME, browserDetails.get("browserName"));
			}
			if (browserDetails.containsKey("browserVersion")) {
				capabilities.setCapability(CapabilityType.VERSION, browserDetails.get("browserVersion"));
			}
			if (browserDetails.containsKey("deviceName")) {
				capabilities.setCapability("deviceName", browserDetails.get("deviceName"));
			}
			if (browserDetails.containsKey("deviceOrientation")) {
				capabilities.setCapability("device-orientation", browserDetails.get("deviceOrientation"));
			}
			if (browserDetails.containsKey("devicePlatform")) {
				capabilities.setCapability(CapabilityType.PLATFORM, browserDetails.get("devicePlatform"));
			}
		}
		return capabilities;
	}

	public static DesiredCapabilities setupBrowserCapability(Browsers browser) throws InvalidBrowserException {
		DesiredCapabilities capabilities;
		switch (browser) { // check our browser
		case HtmlUnit: {
			capabilities = DesiredCapabilities.htmlUnitWithJs();
			break;
		}
		case Firefox: {
			capabilities = DesiredCapabilities.firefox();
			break;
		}
		case Marionette: {
			capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			break;
		}
		case Chrome: {
			capabilities = DesiredCapabilities.chrome();
			break;
		}
		case InternetExplorer: {
			capabilities = DesiredCapabilities.internetExplorer();
			break;
		}
		case Edge: {
			capabilities = DesiredCapabilities.edge();
			break;
		}
		case Android: {
			capabilities = DesiredCapabilities.android();
			break;
		}
		case Iphone: {
			capabilities = DesiredCapabilities.iphone();
			break;
		}
		case Ipad: {
			capabilities = DesiredCapabilities.ipad();
			break;
		}
		case Safari: {
			capabilities = DesiredCapabilities.safari();
			break;
		}
		case Opera: {
			capabilities = DesiredCapabilities.operaBlink();
			break;
		}
		case PhantomJS: {
			capabilities = DesiredCapabilities.phantomjs();
			break;
		}
		// if our browser is not listed, throw an error
		default: {
			throw new InvalidBrowserException("The selected browser " + browser);
		}
		}
		return capabilities;
	}

	public static WebDriver setupDriver(Browsers browser, DesiredCapabilities capabilities)
			throws InvalidBrowserException {
		WebDriver driver;
		// check our browser
		switch (browser) {
		case HtmlUnit: {
			driver = new HtmlUnitDriver(capabilities);
			break;
		}
		case Firefox: {
			FirefoxDriverManager.getInstance().setup();
			driver = new FirefoxDriver(capabilities);
			break;
		}
		case Marionette: {
			FirefoxDriverManager.getInstance().setup();
			driver = new MarionetteDriver(capabilities);
			break;
		}
		case Chrome: {
			ChromeDriverManager.getInstance().setup();
			driver = new ChromeDriver(capabilities);
			break;
		}
		case InternetExplorer: {
			InternetExplorerDriverManager.getInstance().setup();
			driver = new InternetExplorerDriver(capabilities);
			break;
		}
		case Edge: {
			EdgeDriverManager.getInstance().setup();
			driver = new EdgeDriver(capabilities);
			break;
		}
		case Safari: {
			driver = new SafariDriver(capabilities);
			break;
		}
		case Opera: {
			OperaDriverManager.getInstance().setup();
			driver = new OperaDriver(capabilities);
			break;
		}
		// if our browser is not listed, throw an error
		default: {
			throw new InvalidBrowserException("The selected browser " + browser);
		}
		}
		return driver;
	}

}
