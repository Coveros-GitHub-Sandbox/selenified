/*
 * Copyright 2017 Coveros, Inc.
 * 
 * This file is part of Selenified.
 * 
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy 
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

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
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.log4testng.Logger;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import selenified.exceptions.InvalidBrowserException;
import tools.output.Selenium.Browsers;

public class TestSetup {
	
	private static final Logger log = Logger.getLogger(General.class);

	// constants
	private static final String PROXY_INPUT = "proxy";
	private static final String BROWSER_INPUT = "browser";
	private static final String BROWSER_NAME_INPUT = "browserName";
	private static final String BROWSER_VERSION_INPUT = "browserVersion";
	private static final String DEVICE_NAME_INPUT = "deviceName";
	private static final String DEVICE_ORIENTATION_INPUT = "deviceOrientation";
	private static final String DEVICE_PLATFORM_INPUT = "devicePlatform";

	private DesiredCapabilities capabilities;

	/**
	 * A constructor which sets up the default empty desired capabilities
	 */
	public TestSetup() {
		capabilities = new DesiredCapabilities();
	}

	/**
	 * returns the classes defined desired capabilities
	 * 
	 * @return DesiredCapabilities
	 */
	public DesiredCapabilities getDesiredCapabilities() {
		return capabilities;
	}

	/**
	 * Obtains the set system values for the proxy, and adds them to the desired
	 * capabilities
	 * 
	 */
	public void setupProxy() {
		// are we running through a proxy
		if (System.getProperty(PROXY_INPUT) != null) {
			// set our proxy information
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(System.getProperty(PROXY_INPUT));
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}
	}

	/**
	 * determines if the browser information provided has details, or just the
	 * browser name
	 * 
	 * @return Boolean: are there details associated with the browser, such as
	 *         version, os, etc
	 */
	public static boolean areBrowserDetailsSet() {
		return System.getProperty(BROWSER_INPUT) != null && !System.getProperty(BROWSER_INPUT).matches("^[a-zA-Z,]+$");
	}

	/**
	 * looks at the browser information passed in, and loads that data into a
	 * list
	 * 
	 * @return List: a list of all browsers
	 * @throws InvalidBrowserException 
	 */
	public static List<Browsers> setBrowser() throws InvalidBrowserException {
		List<Browsers> browsers = new ArrayList<>();
		// null input check
		if (System.getProperty(BROWSER_INPUT) == null) {
			return browsers;
		}

		// are we looking for all details or not
		if (!areBrowserDetailsSet()) {
			try {
			browsers = Arrays.asList(System.getProperty(BROWSER_INPUT).split(",")).stream().map(Browsers::valueOf)
					.collect(Collectors.toList());
			} catch( Exception e ) {
				log.error( e );
				throw new InvalidBrowserException(e.getMessage());
			}
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

	/**
	 * sets the browser details (name, version, device, orientation, os) into
	 * the device capabilities
	 * 
	 * @param browserDetails
	 *            - a map containing all of the browser details
	 */
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

	/**
	 * Sets the device capabilities based on the browser selection
	 * 
	 * @param browser
	 *            - which browser are we running with
	 * @throws InvalidBrowserException
	 */
	public void setupBrowserCapability(Browsers browser) throws InvalidBrowserException {
		switch (browser) { // check our browser
		case HtmlUnit:
			capabilities = DesiredCapabilities.htmlUnitWithJs();
			break;
		case Firefox:
			capabilities = DesiredCapabilities.firefox();
			break;
		case Marionette:
			setMarionetteCapability();
			break;
		case Chrome:
			capabilities = DesiredCapabilities.chrome();
			break;
		case InternetExplorer:
			capabilities = DesiredCapabilities.internetExplorer();
			break;
		case Edge:
			capabilities = DesiredCapabilities.edge();
			break;
		case Android:
			capabilities = DesiredCapabilities.android();
			break;
		case Iphone:
			capabilities = DesiredCapabilities.iphone();
			break;
		case Ipad:
			capabilities = DesiredCapabilities.ipad();
			break;
		case Safari:
			capabilities = DesiredCapabilities.safari();
			break;
		case Opera:
			capabilities = DesiredCapabilities.operaBlink();
			break;
		case PhantomJS:
			capabilities = DesiredCapabilities.phantomjs();
			break;
		// if our browser is not listed, throw an error
		default:
			throw new InvalidBrowserException("The selected browser " + browser);
		}
	}

	/**
	 * if trying to run marionette, be sure to set it up that way in device
	 * capabilities
	 */
	private void setMarionetteCapability() {
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
	}

	/**
	 * this creates the webdriver object, which will be used to interact with
	 * for all browser web tests
	 * 
	 * @param browser
	 *            - what browser is being tested on
	 * @param capabilities
	 *            - what capabilities are being tested with
	 * @return WebDriver: the driver to interact with for the test
	 * @throws InvalidBrowserException
	 */
	public static WebDriver setupDriver(Browsers browser, DesiredCapabilities capabilities)
			throws InvalidBrowserException {
		WebDriver driver;
		// check our browser
		switch (browser) {
		case HtmlUnit:
			driver = new CustomHtmlUnitDriver(capabilities);
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