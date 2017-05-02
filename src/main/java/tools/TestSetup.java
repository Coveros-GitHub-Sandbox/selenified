package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import tools.output.Action.Browsers;

public class TestSetup {
	
	// constants
	private static final String PROXY_INPUT = "proxy";
	private static final String BROWSER_INPUT = "browser";
	private static final String BROWSER_NAME_INPUT = "browserName";
	private static final String BROWSER_VERSION_INPUT = "browserVersion";
	private static final String DEVICE_NAME_INPUT = "deviceName";
	private static final String DEVICE_ORIENTATION_INPUT = "deviceOrientation";
	private static final String DEVICE_PLATFORM_INPUT = "devicePlatform";

	
	private TestSetup() {}

	public static DesiredCapabilities setupProxy(DesiredCapabilities capabilities) {
		// are we running through a proxy
		if (System.getProperty(PROXY_INPUT) != null) {
			// set our proxy information
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(System.getProperty(PROXY_INPUT));
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}
		return capabilities;
	}

	public static boolean areBrowserDetailsSet() {
		return System.getProperty(BROWSER_INPUT) != null && !System.getProperty(BROWSER_INPUT).matches("^[a-zA-Z,]+$");
	}

	public static List<Browsers> setBrowser() {
		List<Browsers> browsers = new ArrayList<>();
		if (System.getProperty(BROWSER_INPUT) != null) {
			if (!areBrowserDetailsSet()) {
				browsers = Arrays.asList(System.getProperty(BROWSER_INPUT).split(",")).stream().map(Browsers::valueOf)
						.collect(Collectors.toList());
			} else {
				String[] allDetails = System.getProperty(BROWSER_INPUT).split(",");
				for (String details : allDetails) {
					Map<String, String> browserDetails = General.parseMap(details);
					if (browserDetails.containsKey(BROWSER_NAME_INPUT)) {
						browsers.add(Browsers.valueOf(browserDetails.get(BROWSER_NAME_INPUT)));
					} else {
						browsers.add(null);
					}
				}
			}
		}
		return browsers;
	}

	public static DesiredCapabilities setupBrowserDetails(DesiredCapabilities capabilities,
			Map<String, String> browserDetails) {
		if (browserDetails != null) {
			// determine our browser information
			if (browserDetails.containsKey(BROWSER_NAME_INPUT)) {
				capabilities.setCapability(CapabilityType.BROWSER_NAME,
						Browsers.valueOf(browserDetails.get(BROWSER_NAME_INPUT)).toString());
			}
			if (browserDetails.containsKey(BROWSER_VERSION_INPUT)) {
				capabilities.setCapability(CapabilityType.VERSION, browserDetails.get(BROWSER_VERSION_INPUT));
			}
			if (browserDetails.containsKey(DEVICE_NAME_INPUT)) {
				capabilities.setCapability(DEVICE_NAME_INPUT, browserDetails.get(DEVICE_NAME_INPUT));
			}
			if (browserDetails.containsKey(DEVICE_ORIENTATION_INPUT)) {
				capabilities.setCapability("device-orientation", browserDetails.get(DEVICE_ORIENTATION_INPUT));
			}
			if (browserDetails.containsKey(DEVICE_PLATFORM_INPUT)) {
				capabilities.setCapability(CapabilityType.PLATFORM, browserDetails.get(DEVICE_PLATFORM_INPUT));
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
			throw new InvalidBrowserException("The selected browser " + browser + " is not an applicable choice");
		}
		}
		return driver;
	}
}