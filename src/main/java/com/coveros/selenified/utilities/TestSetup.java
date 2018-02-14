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

package com.coveros.selenified.utilities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.log4testng.Logger;

import com.coveros.selenified.Browser;
import com.coveros.selenified.exceptions.InvalidBrowserException;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;

/**
 * Assists with Selenified class in setting up proxy, hub, and browser details
 * 
 * @author Max Saperstone
 */
public class TestSetup {

    private static final Logger log = Logger.getLogger(TestSetup.class);

    // constants
    private static final String PROXY_INPUT = "proxy";
    private static final String BROWSER_INPUT = "browser";
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
     *             If a browser that is not one specified in the
     *             Selenium.Browser class is used, this exception will be thrown
     */
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
                    browsers.add(Browser.lookup(browserString));
                }
            } catch (InvalidBrowserException e) {
                log.error(e);
                throw new InvalidBrowserException(e.getMessage());
            }
        } else {
            String[] allDetails = System.getProperty(BROWSER_INPUT).split(",");
            for (String details : allDetails) {
                Map<String, String> browserDetails = parseMap(details);
                if (browserDetails.containsKey(BROWSER_NAME_INPUT)) {
                    browsers.add(Browser.lookup(browserDetails.get(BROWSER_NAME_INPUT)));
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
            // determine the browser information
            if (browserDetails.containsKey(BROWSER_NAME_INPUT)) {
                capabilities.setCapability(CapabilityType.BROWSER_NAME,
                        Browser.valueOf(browserDetails.get(BROWSER_NAME_INPUT)).toString());
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
     *             If a browser that is not one specified in the
     *             Selenium.Browser class is used, this exception will be thrown
     */
    public void setupBrowserCapability(Browser browser) throws InvalidBrowserException {
        switch (browser) { // check the browser
        case HTMLUNIT:
            capabilities = DesiredCapabilities.htmlUnitWithJs();
            break;
        case FIREFOX:
            capabilities = DesiredCapabilities.firefox();
            break;
        case MARIONETTE:
            setMarionetteCapability();
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
     *             If a browser that is not one specified in the
     *             Selenium.Browser class is used, this exception will be thrown
     */
    public static WebDriver setupDriver(Browser browser, DesiredCapabilities capabilities)
            throws InvalidBrowserException {
        WebDriver driver;
        // check the browser
        switch (browser) {
        case HTMLUNIT:
            driver = new CustomHtmlUnitDriver(capabilities);
            break;
        case FIREFOX:
            FirefoxDriverManager.getInstance().forceCache().setup();
            driver = new FirefoxDriver(capabilities);
            break;
        case MARIONETTE:
            FirefoxDriverManager.getInstance().forceCache().setup();
            driver = new MarionetteDriver(capabilities);
            break;
        case CHROME:
            ChromeDriverManager.getInstance().forceCache().setup();
            if (System.getProperty("headless") != null && "true".equals(System.getProperty("headless"))) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--mute-audio");
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            }
            driver = new ChromeDriver(capabilities);
            break;
        case INTERNETEXPLORER:
            InternetExplorerDriverManager.getInstance().forceCache().setup();
            driver = new InternetExplorerDriver(capabilities);
            break;
        case EDGE:
            EdgeDriverManager.getInstance().forceCache().setup();
            driver = new EdgeDriver(capabilities);
            break;
        case SAFARI:
            driver = new SafariDriver(capabilities);
            break;
        case OPERA:
            OperaDriverManager.getInstance().forceCache().setup();
            driver = new OperaDriver(capabilities);
            break;
        // if the browser is not listed, throw an error
        default:
            throw new InvalidBrowserException("The selected browser " + browser + " is not an applicable choice");
        }
        return driver;
    }

    /**
     * Generates a random string of alpha-numeric characters
     * 
     * @param length
     *            the length of the random string
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
     * @param value
     *            - the string to cleanup
     * @return String: the provided string with all alphanumeric characters
     *         removed
     */
    public static String removeNonWordCharacters(String value) {
        if (value == null) {
            return value;
        }
        return value.replaceAll("[^a-zA-Z0-9]+", "");
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     * 
     * @param method
     *            - the method under test to extract the name from
     * @param dataProvider
     *            - an array of objects being passed to the test as data
     *            providers
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
     * @param dataProvider
     *            - the object array to check - is it truly a data provider?
     * @return Boolean: is the provided object array a data provider?
     */
    private static boolean isRealDataProvider(Object... dataProvider) {
        return dataProvider != null && dataProvider.length > 0 && dataProvider[0] != null
                && !dataProvider[0].toString().startsWith(PUBLIC);
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     * 
     * @param packageName
     *            - the package name of the test method as a string
     * @param className
     *            - the class name of the test method as a string
     * @param methodName
     *            - the method name of the test as a string
     * @param dataProvider
     *            - an array of objects being passed to the test as data
     *            providers
     * @return String: a unique name
     */
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
                    currentName = className + "_" + methodName + dataProvider.toString().split(";")[1]; // NOSONAR
                                                                                                        // -
                                                                                                        // purposefully
                                                                                                        // using
                                                                                                        // toString
                                                                                                        // on
                                                                                                        // object
                                                                                                        // to
                                                                                                        // obtain
                                                                                                        // unique
                                                                                                        // random
                                                                                                        // hash
                } else {
                    currentName = packageName + "_" + className + "_" + methodName
                            + dataProvider.toString().split(";")[1]; // NOSONAR
                                                                        // -
                                                                        // purposefully
                                                                        // using
                                                                        // toString
                                                                        // on
                                                                        // object
                                                                        // to
                                                                        // obtain
                                                                        // unique
                                                                        // random
                                                                        // hash
                }
            }
        }
        return currentName;
    }

    /**
     * Capitalizes the first letter of each word in the provided string
     * 
     * @param word
     *            - the string to be capitalized on
     * @return String: the new string
     */
    public static String capitalizeFirstLetters(String word) {
        if (word == null) {
            return word;
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
     * @param input
     *            - a string, with key and values separated by an equals (=) and
     *            pairs separated by an ampersand (&)
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
}