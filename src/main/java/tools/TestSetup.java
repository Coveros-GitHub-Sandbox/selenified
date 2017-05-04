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

	private DesiredCapabilities capabilities;

	public TestSetup() {
		capabilities = new DesiredCapabilities();
	}

	public DesiredCapabilities getDesiredCapabilities() {
		return capabilities;
	}

	public void setupProxy() {
		// are we running through a proxy
		if (System.getProperty(PROXY_INPUT) != null) {
			// set our proxy information
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(System.getProperty(PROXY_INPUT));
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}
	}

	public static boolean areBrowserDetailsSet() {
		return System.getProperty(BROWSER_INPUT) != null && !System.getProperty(BROWSER_INPUT).matches("^[a-zA-Z,]+$");
	}

	public static List<Browsers> setBrowser() {
		List<Browsers> browsers = new ArrayList<>();
		// null input check
		if (System.getProperty(BROWSER_INPUT) == null) {
			return browsers;
		}

		// are we looking for all details or not
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
		return browsers;
	}

	public void setupBrowserDetails(Map<String, String> browserDetails) {
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
	}

	public void setupBrowserCapability(Browsers browser) throws InvalidBrowserException {
		switch (browser) { // check our browser
		case HtmlUnit:
			setHtmlUnitCapability();
			break;
		case Firefox:
			setFirefoxCapability();
			break;
		case Marionette:
			setMarionetteCapability();
			break;
		case Chrome:
			setChromeCapability();
			break;
		case InternetExplorer:
			setInternetExplorerCapability();
			break;
		case Edge:
			setEdgeCapability();
			break;
		case Android:
			setAndroidCapability();
			break;
		case Iphone:
			setIphoneCapability();
			break;
		case Ipad:
			setIpadCapability();
			break;
		case Safari:
			setSafariCapability();
			break;
		case Opera:
			setOperaCapability();
			break;
		case PhantomJS:
			setPhantomJSCapability();
			break;
		// if our browser is not listed, throw an error
		default:
			throw new InvalidBrowserException("The selected browser " + browser);
		}
	}

	private void setPhantomJSCapability() {
		capabilities = DesiredCapabilities.phantomjs();
	}

	private void setOperaCapability() {
		capabilities = DesiredCapabilities.operaBlink();
	}

	private void setSafariCapability() {
		capabilities = DesiredCapabilities.safari();
	}

	private void setIpadCapability() {
		capabilities = DesiredCapabilities.ipad();
	}

	private void setIphoneCapability() {
		capabilities = DesiredCapabilities.iphone();
	}

	private void setAndroidCapability() {
		capabilities = DesiredCapabilities.android();
	}

	private void setEdgeCapability() {
		capabilities = DesiredCapabilities.edge();
	}

	private void setInternetExplorerCapability() {
		capabilities = DesiredCapabilities.internetExplorer();
	}

	private void setChromeCapability() {
		capabilities = DesiredCapabilities.chrome();
	}

	private void setMarionetteCapability() {
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
	}

	private void setFirefoxCapability() {
		capabilities = DesiredCapabilities.firefox();
	}

	private void setHtmlUnitCapability() {
		capabilities = DesiredCapabilities.htmlUnitWithJs();
	}

	public static WebDriver setupDriver(Browsers browser, DesiredCapabilities capabilities)
			throws InvalidBrowserException {
		WebDriver driver;
		// check our browser
		switch (browser) {
		case HtmlUnit:
			driver = new HtmlUnitDriver(capabilities);
			break;
		case Firefox:
			FirefoxDriverManager.getInstance().forceCache().setup();
			driver = new FirefoxDriver(capabilities);
			break;
		case Marionette:
			FirefoxDriverManager.getInstance().forceCache().setup();
			driver = new MarionetteDriver(capabilities);
			break;
		case Chrome:
			ChromeDriverManager.getInstance().forceCache().setup();
			driver = new ChromeDriver(capabilities);
			break;
		case InternetExplorer:
			InternetExplorerDriverManager.getInstance().forceCache().setup();
			driver = new InternetExplorerDriver(capabilities);
			break;
		case Edge:
			EdgeDriverManager.getInstance().forceCache().setup();
			driver = new EdgeDriver(capabilities);
			break;
		case Safari:
			driver = new SafariDriver(capabilities);
			break;
		case Opera:
			OperaDriverManager.getInstance().forceCache().setup();
			driver = new OperaDriver(capabilities);
			break;
		// if our browser is not listed, throw an error
		default:
			throw new InvalidBrowserException("The selected browser " + browser + " is not an applicable choice");
		}
		return driver;
	}
}