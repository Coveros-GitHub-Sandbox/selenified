/*
 * Copyright 2018 Coveros, Inc.
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

package com.coveros.selenified.utilities;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.log4testng.Logger;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

/**
 * Assists with Selenified class in setting up proxy, hub, and browser details
 *
 * @author Max Saperstone
 * @version 3.0.4
 * @lastupdate 9/13/2018
 */
public class TestSetup {

    private static final Logger log = Logger.getLogger(TestSetup.class);

    // constants
    private static final String PROXY_INPUT = "proxy";
    private static final String SCREEN_SIZE = "screensize";
    private static final String BROWSER_INPUT = "browser";
    private static final String BROWSER_OPTIONS = "options";
    private static final String HEADLESS_INPUT = "headless";
    private static final String BROWSER_NAME_INPUT = "browserName";
    private static final String BROWSER_VERSION_INPUT = "browserVersion";
    private static final String DEVICE_NAME_INPUT = "deviceName";
    private static final String DEVICE_ORIENTATION_INPUT = "deviceOrientation";
    private static final String DEVICE_PLATFORM_INPUT = "devicePlatform";

    private static final int MAXFILENAMELENGTH = 200;
    private static final String PUBLIC = "public";

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
     */

    public void setupProxy() {
        // are we running through a proxy
        if (System.getProperty(PROXY_INPUT) != null) {
            // set the proxy information
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
     * version, os, etc
     */
    public static boolean areBrowserDetailsSet() {
        return System.getProperty(BROWSER_INPUT) != null && !System.getProperty(BROWSER_INPUT).matches("^[a-zA-Z,]+$");
    }

    /**
     * looks at the browser information passed in, and loads that data into a
     * list
     *
     * @return List: a list of all browsers
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    @SuppressWarnings("squid:S3776")
    public static List<Browser> setBrowser() throws InvalidBrowserException {
        List<Browser> browsers = new ArrayList<>();
        // null input check
        if (System.getProperty(BROWSER_INPUT) == null) {
            return browsers;
        }

        // are we looking for all details or not
        if (!areBrowserDetailsSet()) {
            try {
                String[] browserStrings = System.getProperty(BROWSER_INPUT).split(",");
                for (String browserString : browserStrings) {
                    browsers.add(new Browser(Browser.lookup(browserString)));
                }
            } catch (InvalidBrowserException e) {
                log.error(e);
                throw new InvalidBrowserException(e.getMessage());
            }
        } else {
            String[] allDetails = System.getProperty(BROWSER_INPUT).split(",");
            for (String details : allDetails) {
                Map<String, String> browserDetails = parseMap(details);
                if (!browserDetails.containsKey(BROWSER_NAME_INPUT)) {
                    throw new InvalidBrowserException("browserName must be included in browser details");
                }
                Browser browser = new Browser(Browser.lookup(browserDetails.get(BROWSER_NAME_INPUT)));
                if (browserDetails.containsKey(BROWSER_VERSION_INPUT)) {
                    browser.setVersion(browserDetails.get(BROWSER_VERSION_INPUT));
                }
                if (browserDetails.containsKey(DEVICE_NAME_INPUT)) {
                    browser.setDevice(browserDetails.get(DEVICE_NAME_INPUT));
                }
                if (browserDetails.containsKey(DEVICE_ORIENTATION_INPUT)) {
                    browser.setOrientation(browserDetails.get(DEVICE_ORIENTATION_INPUT));
                }
                if (browserDetails.containsKey(DEVICE_PLATFORM_INPUT)) {
                    browser.setPlatform(browserDetails.get(DEVICE_PLATFORM_INPUT));
                }
                browsers.add(browser);
            }
        }
        return browsers;
    }

    /**
     * sets the browser details (name, version, device, orientation, os) into
     * the device capabilities
     *
     * @param browser - the browser object, with details included
     */
    public void setupBrowserDetails(Browser browser) {
        if (browser != null) {
            // determine the browser information
            if (browser.getName() != null &&
                    (capabilities.getBrowserName() == null || "".equals(capabilities.getBrowserName()))) {
                capabilities.setCapability(CapabilityType.BROWSER_NAME, browser.getName().toString().toLowerCase());
            }
            if (browser.getVersion() != null) {
                capabilities.setCapability(CapabilityType.VERSION, browser.getVersion());
            }
            if (browser.getDevice() != null) {
                capabilities.setCapability(DEVICE_NAME_INPUT, browser.getDevice());
            }
            if (browser.getOrientation() != null) {
                capabilities.setCapability("device-orientation", browser.getOrientation());
            }
            if (browser.getPlatform() != null) {
                capabilities.setCapability(CapabilityType.PLATFORM, browser.getPlatform());
            }
        }
    }

    /**
     * Sets the device capabilities based on the browser selection
     *
     * @param browser - which browser are we running with
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    public void setupBrowserCapability(Browser browser) throws InvalidBrowserException {
        switch (browser.getName()) { // check the browser
            case HTMLUNIT:
                capabilities = DesiredCapabilities.htmlUnit();
                break;
            case FIREFOX:
                capabilities = DesiredCapabilities.firefox();
                break;
            case CHROME:
                capabilities = DesiredCapabilities.chrome();
                break;
            case INTERNETEXPLORER:
                capabilities = DesiredCapabilities.internetExplorer();
                break;
            case EDGE:
                capabilities = DesiredCapabilities.edge();
                break;
            case ANDROID:
                capabilities = DesiredCapabilities.android();
                break;
            case IPHONE:
                capabilities = DesiredCapabilities.iphone();
                break;
            case IPAD:
                capabilities = DesiredCapabilities.ipad();
                break;
            case SAFARI:
                capabilities = DesiredCapabilities.safari();
                break;
            case OPERA:
                capabilities = DesiredCapabilities.operaBlink();
                break;
            case PHANTOMJS:
                capabilities = DesiredCapabilities.phantomjs();
                break;
            // if the browser is not listed, throw an error
            default:
                throw new InvalidBrowserException("The selected browser " + browser);
        }
    }

    /**
     * this creates the webdriver object, which will be used to interact with
     * for all browser web tests
     *
     * @param browser      - what browser is being tested on
     * @param capabilities - what capabilities are being tested with
     * @return WebDriver: the driver to interact with for the test
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    public static WebDriver setupDriver(Browser browser,
                                        DesiredCapabilities capabilities) throws InvalidBrowserException {
        WebDriver driver;
        if (browser == null || browser.getName() == null) {
            throw new InvalidBrowserException("No browser was selected");
        }
        // check the browser
        switch (browser.getName()) {
            case HTMLUNIT:
                capabilities.setBrowserName("htmlunit");
                capabilities.setJavascriptEnabled(true);
                System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal");
                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);
                driver = new HtmlUnitDriver(capabilities);
                break;
            case FIREFOX:
                FirefoxDriverManager.getInstance().forceCache().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
                firefoxOptions.addArguments(getBrowserOptions(browser));
                if (runHeadless()) {
                    firefoxOptions.setHeadless(true);
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case CHROME:
                ChromeDriverManager.getInstance().forceCache().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions = chromeOptions.merge(capabilities);
                chromeOptions.addArguments(getBrowserOptions(browser));
                if (runHeadless()) {
                    chromeOptions.setHeadless(true);
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case INTERNETEXPLORER:
                InternetExplorerDriverManager.getInstance().forceCache().setup();
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions(capabilities);
                driver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            case EDGE:
                EdgeDriverManager.getInstance().forceCache().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions = edgeOptions.merge(capabilities);
                driver = new EdgeDriver(edgeOptions);
                break;
            case SAFARI:
                SafariOptions safariOptions = new SafariOptions(capabilities);
                driver = new SafariDriver(safariOptions);
                break;
            case OPERA:
                OperaDriverManager.getInstance().forceCache().setup();
                OperaOptions operaOptions = new OperaOptions();
                operaOptions = operaOptions.merge(capabilities);
                driver = new OperaDriver(operaOptions);
                break;
            case PHANTOMJS:
                PhantomJsDriverManager.getInstance().forceCache().setup();
                driver = new PhantomJSDriver(capabilities);
                break;
            // if the browser is not listed, throw an error
            default:
                throw new InvalidBrowserException(
                        "The selected browser " + browser.getName() + " is not an applicable choice");
        }
        return driver;
    }

    public static Boolean runHeadless() {
        return System.getProperty(HEADLESS_INPUT) != null && "true".equals(System.getProperty(HEADLESS_INPUT));
    }

    public static List<String> getBrowserOptions(Browser browser) {
        ArrayList<String> browserOptions = new ArrayList<>();
        if (System.getProperty(BROWSER_OPTIONS) != null) {
            browserOptions = new ArrayList(Arrays.asList(System.getProperty(BROWSER_OPTIONS).split("\\s*,\\s*")));
        }
        if (browser.getName() == BrowserName.CHROME && browserOptions.contains("--headless")) {
            browserOptions.remove("--headless");
            System.setProperty(HEADLESS_INPUT, "true");
        }
        if (browser.getName() == BrowserName.FIREFOX && browserOptions.contains("-headless")) {
            browserOptions.remove("-headless");
            System.setProperty(HEADLESS_INPUT, "true");
        }
        return browserOptions;
    }

    /**
     * Sets up the initial size of the browser. Checks for the passed in parameter of screensize. If set to width
     * x height, sets the browser to that size; if set to maximum, maximizes the browser.
     *
     * @param app - the application to be tested, contains all control elements
     */
    public static void setupScreenSize(App app) {
        if (System.getProperty(SCREEN_SIZE) != null && !"".equals(System.getProperty(SCREEN_SIZE))) {
            String screensize = System.getProperty(SCREEN_SIZE);
            if (screensize.matches("(\\d+)x(\\d+)")) {
                int width = Integer.parseInt(System.getProperty(SCREEN_SIZE).split("x")[0]);
                int height = Integer.parseInt(System.getProperty(SCREEN_SIZE).split("x")[1]);
                app.resize(width, height);
            } else if ("maximum".equals(screensize)) {
                app.maximize();
            } else {
                log.error("Provided screensize does not match expected pattern");
            }
        }
    }

    /**
     * Generates a random string of alpha-numeric characters
     *
     * @param length the length of the random string
     * @return String: random string of characters
     */
    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        String stringChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(stringChars.charAt(rnd.nextInt(stringChars.length())));
        }
        return sb.toString();
    }

    /**
     * Removes all non alphanumeric characters from a provided string
     *
     * @param value - the string to cleanup
     * @return String: the provided string with all alphanumeric characters
     * removed
     */
    public static String removeNonWordCharacters(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("[^a-zA-Z0-9]+", "");
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     *
     * @param method       - the method under test to extract the name from
     * @param dataProvider - an array of objects being passed to the test as data
     *                     providers
     * @return String: a unique name
     */
    public static String getTestName(Method method, Object... dataProvider) {
        String className;
        String packageName = "";
        if (method.getDeclaringClass().getPackage() != null) {
            className = method.getDeclaringClass().toString().substring(6).split("\\.")[1];
            packageName = method.getDeclaringClass().toString().substring(6).split("\\.")[0];
        } else {
            className = method.getDeclaringClass().toString().substring(6);
        }
        return getTestName(packageName, className, method.getName(), dataProvider);
    }

    /**
     * Determines if a dataProvider was actually provided, or just ITestContext
     * or method data is present
     *
     * @param dataProvider - the object array to check - is it truly a data provider?
     * @return Boolean: is the provided object array a data provider?
     */
    private static boolean isRealDataProvider(Object... dataProvider) {
        return dataProvider != null && dataProvider.length > 0 && dataProvider[0] != null &&
                !dataProvider[0].toString().startsWith(PUBLIC);
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     *
     * @param packageName  - the package name of the test method as a string
     * @param className    - the class name of the test method as a string
     * @param methodName   - the method name of the test as a string
     * @param dataProvider - an array of objects being passed to the test as data
     *                     providers
     * @return String: a unique name
     */
    @SuppressWarnings("squid:S2116")
    public static String getTestName(String packageName, String className, String methodName, Object... dataProvider) {
        StringBuilder testName;
        if ("".equals(packageName)) {
            testName = new StringBuilder(className + "_" + methodName);
        } else {
            testName = new StringBuilder(packageName + "_" + className + "_" + methodName);
        }
        String currentName = testName.toString();
        if (isRealDataProvider(dataProvider)) {
            testName.append("WithOption");
            for (Object data : dataProvider) {
                if (data == null || data.toString().startsWith(PUBLIC)) {
                    break;
                }
                testName.append(TestSetup.capitalizeFirstLetters(removeNonWordCharacters(data.toString())));
            }
            currentName = testName.toString();
            if (currentName.length() > MAXFILENAMELENGTH) {
                if ("".equals(packageName)) {
                    currentName = className + "_" + methodName + dataProvider.toString().split(";")[1];
                    // purposefully using toString on object to obtain unique random hash
                } else {
                    currentName =
                            packageName + "_" + className + "_" + methodName + dataProvider.toString().split(";")[1];
                    // purposefully using toString on object to obtain unique random hash
                }
            }
        }
        return currentName;
    }

    /**
     * Capitalizes the first letter of each word in the provided string
     *
     * @param word - the string to be capitalized on
     * @return String: the new string
     */
    public static String capitalizeFirstLetters(String word) {
        if (word == null) {
            return null;
        }
        String out = "";
        for (int i = 0; i < word.length(); i++) {
            if (i == 0) {
                // Capitalize the first letter of the string.
                out = String.format("%s%s", Character.toUpperCase(word.charAt(0)), word.substring(1));
            }
            // Is this character a non-letter? If so
            // then this is probably a word boundary so let's capitalize
            // the next character in the sequence.
            if (!Character.isLetter(out.charAt(i)) && (i + 1) < out.length()) {
                out = String.format("%s%s%s", out.subSequence(0, i + 1), Character.toUpperCase(out.charAt(i + 1)),
                        out.substring(i + 2));
            }
        }
        return out;
    }

    /**
     * Breaks up a string, and places it into a map. ampersands (&) are used to
     * split into key value pairs, while equals (=) are used to assign key vs
     * values
     *
     * @param input - a string, with key and values separated by an equals (=) and
     *              pairs separated by an ampersand (&)
     * @return Map: a map with values
     */
    public static Map<String, String> parseMap(final String input) {
        final Map<String, String> map = new HashMap<>();
        for (String pair : input.split("&")) {
            String[] kv = pair.split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    /**
     * Sauce labs has specific capabilities to manage the selenium version used. The version is obtained from the
     * POM (or could be passed in via CMD to override) and then set so that Sauce sets the specific selenium version,
     * instead of their default: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options#TestConfigurationOptions-SeleniumVersion
     */
    public void setupSauceCapabilities() {
        capabilities.setCapability("seleniumVersion", System.getProperty("selenium.version"));
    }
}