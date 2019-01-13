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

package com.coveros.selenified;

import com.coveros.selenified.Browser.BrowserName;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Assists with Selenified class in setting up proxy, hub, and browser details
 *
 * @author Max Saperstone
 * @version 3.0.4
 * @lastupdate 1/12/2019
 */
public class Capabilities {

    private static final Logger log = Logger.getLogger(Capabilities.class);

    // constants
    private static final String PROXY_INPUT = "proxy";
    private static final String OPTIONS_INPUT = "options";
    private static final String HEADLESS_INPUT = "headless";

    private Browser browser;
    private int instance;
    private DesiredCapabilities capabilities;

    /**
     * A constructor which sets up the default empty desired capabilities
     */
    public Capabilities(Browser browser) {
        this.browser = browser;
        this.capabilities = new DesiredCapabilities();
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
     * returns the classes defined all browser details
     *
     * @return Browser
     */
    public Browser getBrowser() {
        return browser;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    /**
     * Retrieves the instance of the test running. This references the invocation count from TestNG, allowing looping,
     * and specifies which test run this is
     *
     * @return Integer
     */
    public int getInstance() {
        return instance;
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
     * Sets the device capabilities based on the browser selection
     *
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    public void setupBrowserCapability() throws InvalidBrowserException {
        if (System.getProperty("hub") != null) {
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
        capabilities = browser.setBrowserCapabilities(capabilities);
    }

    /**
     * this creates the webdriver object, which will be used to interact with
     * for all browser web tests
     *
     * @return WebDriver: the driver to interact with for the test
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    public WebDriver setupDriver() throws InvalidBrowserException {
        WebDriver driver;
        if (browser == null) {
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
                firefoxOptions.addArguments(getBrowserOptions());
                if (runHeadless()) {
                    firefoxOptions.setHeadless(true);
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case CHROME:
                ChromeDriverManager.getInstance().forceCache().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions = chromeOptions.merge(capabilities);
                chromeOptions.addArguments(getBrowserOptions());
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

    private Boolean runHeadless() {
        return System.getProperty(HEADLESS_INPUT) != null && "true".equals(System.getProperty(HEADLESS_INPUT));
    }

    private List<String> getBrowserOptions() {
        ArrayList<String> browserOptions = new ArrayList<>();
        if (System.getProperty(OPTIONS_INPUT) != null) {
            browserOptions = new ArrayList(Arrays.asList(System.getProperty(OPTIONS_INPUT).split("\\s*,\\s*")));
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

    public void addExtraCapabilities(DesiredCapabilities extraCapabilities) {
        if (extraCapabilities != null) {
            capabilities = capabilities.merge(extraCapabilities);
        }
    }
}