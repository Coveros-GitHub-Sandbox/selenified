/*
 * Copyright 2019 Coveros, Inc.
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
import com.coveros.selenified.utilities.Sauce;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Assists with Selenified class in setting up proxy, hub, and browser details
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 3/4/2019
 */
public class Capabilities {

    // constants
    private static final String PROXY_INPUT = "proxy";
    private static final String OPTIONS_INPUT = "options";
    private static final String HEADLESS_INPUT = "headless";

    private Browser browser;
    private int instance;
    private DesiredCapabilities desiredCapabilities;

    /**
     * A constructor which sets up the browser, and default desiredCapabilities, based on the browser, and information
     * passed in from the command line
     */
    public Capabilities(Browser browser) throws InvalidBrowserException {
        if (browser == null) {
            throw new InvalidBrowserException("A valid browser was not provided");
        }
        this.browser = browser;
        this.desiredCapabilities = new DesiredCapabilities();
        setDesiredCapabilities();
    }

    private void setDesiredCapabilities() {
        if (browser.getName() == BrowserName.NONE) {
            return;
        }
        // by default, accept insecure sites - there are some exceptions for browsers
        this.desiredCapabilities.setAcceptInsecureCerts(true);
        // setup our browser name based on the enum provided - there are a few special cases
        this.desiredCapabilities.setBrowserName(browser.getName().toString().toLowerCase());
        // the default platform is any, unless the browser is platform specific - this can be overridden
        switch (browser.getName()) {
            case INTERNETEXPLORER:
                this.desiredCapabilities.setBrowserName("internet explorer");
                this.desiredCapabilities.setPlatform(Platform.WINDOWS);
                this.desiredCapabilities.setAcceptInsecureCerts(false);
                break;
            case EDGE:
                this.desiredCapabilities.setBrowserName("MicrosoftEdge");
                this.desiredCapabilities.setPlatform(Platform.WIN10);
                break;
            case SAFARI:
                this.desiredCapabilities.setPlatform(Platform.MOJAVE);
                // Safari 12 doesn't support setAcceptInsecureCerts, and there is no current workaround
                if ("12".equals(browser.getVersion()) || browser.getVersion() == null) {
                    this.desiredCapabilities.setAcceptInsecureCerts(false);
                }
                break;
            default:
                this.desiredCapabilities.setPlatform(Platform.ANY);
        }
        if (browser.getPlatform() != null) {
            this.desiredCapabilities.setPlatform(browser.getPlatform());
        }
        // the default version is empty - this can be overridden
        this.desiredCapabilities.setVersion("");
        if (browser.getVersion() != null) {
            this.desiredCapabilities.setVersion(browser.getVersion());
        }
        // only include the screen resolution is in width x height format
        if (browser.getScreensize() != null && browser.getScreensize().matches("(\\d+)x(\\d+)")) {
            this.desiredCapabilities.setCapability("screenResolution", browser.getScreensize());
        }
        // always enable javascript, accept certs, and start with a clean session
        this.desiredCapabilities.setJavascriptEnabled(true);
        this.desiredCapabilities.setCapability("ensureCleanSession", true);
        // setup additional non-browser capabilities
        setupProxy();
        setupSauceCapabilities();
    }

    /**
     * returns the classes defined desired desiredCapabilities
     *
     * @return DesiredCapabilities
     */
    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    /**
     * returns the classes defined all browser details
     *
     * @return Browser - the browser with details
     */
    public Browser getBrowser() {
        return browser;
    }

    /**
     * Sets the instances of the test running. This references the invocation count from TestNG, allowing looping, and
     * specifies which test run this is
     *
     * @param instance - the number instance of the test being run, to track capabilities
     */
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
     * desiredCapabilities
     */
    public void setupProxy() {
        // are we running through a proxy
        if (System.getProperty(PROXY_INPUT) != null) {
            // set the proxy information
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(System.getProperty(PROXY_INPUT));
            desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
        }
    }

    /**
     * Sauce labs has specific capabilities to manage the selenium version used. The version is obtained from the
     * POM (or could be passed in via CMD to override) and then set so that Sauce sets the specific selenium version,
     * instead of their default: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options#TestConfigurationOptions-SeleniumVersion
     * Additionally, the iedriverVersion is set to match the selenium version as suggested, if ie is the chosen browser
     * Finally, the default platform for edge is set to windows 10
     */
    public void setupSauceCapabilities() {
        if (Sauce.isSauce()) {
            // set the selenium version
            desiredCapabilities.setCapability("seleniumVersion", System.getProperty("selenium.version"));
            // set the ie driver if needed
            if (desiredCapabilities.getBrowserName().equals("internet explorer")) {
                desiredCapabilities.setCapability("iedriverVersion", System.getProperty("selenium.version"));
            }
        }
    }

    /**
     * this creates the webdriver object, which will be used to interact with
     * for all browser web tests
     *
     * @return WebDriver: the driver to interact with for the test
     */
    public WebDriver setupDriver() throws InvalidBrowserException {
        WebDriver driver;
        // check the browser
        switch (browser.getName()) {
            case HTMLUNIT:
                System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal");
                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);
                driver = new HtmlUnitDriver(desiredCapabilities);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().forceCache().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions(desiredCapabilities);
                firefoxOptions.addArguments(getBrowserOptions());
                if (runHeadless()) {
                    firefoxOptions.setHeadless(true);
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case CHROME:
                WebDriverManager.chromedriver().forceCache().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions = chromeOptions.merge(desiredCapabilities);
                chromeOptions.addArguments(getBrowserOptions());
                if (runHeadless()) {
                    chromeOptions.setHeadless(true);
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case INTERNETEXPLORER:
                WebDriverManager.iedriver().forceCache().setup();
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions(desiredCapabilities);
                driver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            case EDGE:
                WebDriverManager.edgedriver().forceCache().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions = edgeOptions.merge(desiredCapabilities);
                driver = new EdgeDriver(edgeOptions);
                break;
            case SAFARI:
                SafariOptions safariOptions = new SafariOptions(desiredCapabilities);
                driver = new SafariDriver(safariOptions);
                break;
            case OPERA:
                WebDriverManager.operadriver().forceCache().setup();
                OperaOptions operaOptions = new OperaOptions();
                operaOptions = operaOptions.merge(desiredCapabilities);
                driver = new OperaDriver(operaOptions);
                break;
            case PHANTOMJS:
                WebDriverManager.phantomjs().forceCache().setup();
                driver = new PhantomJSDriver(desiredCapabilities);
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

    /**
     * If additional capabilities are provided in the test case, add them in
     *
     * @param extraCapabilities any additional parameters to set for selenium
     */
    public void addExtraCapabilities(DesiredCapabilities extraCapabilities) {
        if (extraCapabilities != null && browser.getName() != BrowserName.NONE) {
            desiredCapabilities = desiredCapabilities.merge(extraCapabilities);
        }
    }
}