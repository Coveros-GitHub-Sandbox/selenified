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

package com.coveros.selenified.selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.output.Assert.Result;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.tools.General;
import com.coveros.selenified.tools.TestSetup;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * A custom Selenium wrapper to handle exceptions that might be thrown as a
 * result of the test step. Slow loading elements are waited for, and any errors
 * thrown are properly recorded and put into the reports, instead of having the
 * tests stop and crash.
 *
 * @author Max Saperstone
 * @version 2.0.1
 * @lastupdate 7/20/2017
 */
public class Action {

    private static final Logger log = Logger.getLogger(General.class);

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // what browsers are we interested in implementing
    // this is the browser that we are using
    private Browser browser;
    private DesiredCapabilities capabilities;

    // if we're not using a browser, what services will we be interacting with
    private HTTP http;

    private LocatorAction locatorAction;
    private String parentWindow;

    // constants
    private static final String SECONDS = " seconds";
    private static final String WAITED = "Waited ";
    private static final String UPTO = "Wait up to ";
    private static final String AFTER = "After waiting ";
    private static final String FRAME = "Frame <b>";
    private static final String AVAILABLE = "</b> is available and selected";
    private static final String NOTSELECTED = "</b> was unable to be selected";
    private static final double WAITFOR = 5;

    /**
     * the constructor, determining which browser use and how to run the
     * browser: either grid or standalone
     *
     * @param browser
     *            - the Browser we are running the test on
     * @param capabilities
     *            - what browser capabilities are desired
     * @param file
     *            - the TestOutput file. This is provided by the
     *            SeleniumTestBase functionality
     * @throws InvalidBrowserException
     *             If a browser that is not one specified in the
     *             Selenium.Browser class is used, this exception will be thrown
     * @throws MalformedURLException
     *             If the provided hub address isn't a URL, this exception will
     *             be thrown
     */
    public Action(Browser browser, DesiredCapabilities capabilities, OutputFile file)
            throws InvalidBrowserException, MalformedURLException {
        this.browser = browser;
        this.capabilities = capabilities;
        this.file = file;

        // if we want to test remotely
        if (System.getProperty("hub") != null) {
            driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), capabilities);
        } else {
            capabilities.setJavascriptEnabled(true);
            driver = TestSetup.setupDriver(browser, capabilities);
        }

        this.locatorAction = new LocatorAction(driver, file);
    }

    /**
     * the detailed test results constructor that will setup the our test output
     * file for all documentation and information for services based testing
     * 
     * @param serviceBaseUrl
     *            the base url for access services; e.g.
     *            'http://my.domain.com/services/'
     * @param username
     *            the username needed for basic header authorization - leave
     *            null if not needed
     * @param password
     *            the username needed for basic header authorization - leave
     *            null if not needed
     */
    public Action(String serviceBaseUrl, String username, String password, OutputFile file) {
        http = new HTTP(serviceBaseUrl, username, password);
        this.file = file;
    }

    /**
     * a method to allow retrieving the driver instance
     *
     * @return WebDriver: access to the driver controlling the browser via
     *         webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * a method to allow modifying the driver instance. Use with extreme
     * caution!
     * 
     * @param driver
     *            - the updated webdriver. Be sure not to create a fresh driver,
     *            but instead, retrieve the currently instantated instance via
     *            getDriver, and modify that
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * a method to allow retrieving the set browser
     *
     * @return Browser: the enum of the browser
     */
    public Browser getBrowser() {
        return browser;
    }

    /**
     * a method to allow retreiving the set capabilities
     *
     * @return capabilities: the listing of set capabilities
     */
    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * a method to end the driver instance
     */
    public void killDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /**
     * a method for navigating to a new url
     *
     * @param url
     *            - the URL to navigate to
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int goToURL(String url) {
        String action = "Loading " + url;
        String expected = "Loaded " + url;
        double start = System.currentTimeMillis();
        try {
            driver.get(url);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Fail to Load " + url + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return 1;
        }
        double timetook = System.currentTimeMillis() - start;
        timetook = timetook / 1000;
        file.recordAction(action, expected, "Loaded " + url + " in " + timetook + SECONDS, Result.SUCCESS);
        return 0;
    }

    // ///////////////////////////////////////
    // waiting functionality
    // ///////////////////////////////////////

    /**
     * a method for allowing Selenium to pause for a set amount of time
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int wait(double seconds) {
        String action = "Wait " + seconds + SECONDS;
        String expected = WAITED + seconds + SECONDS;
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            log.error(e);
            file.recordAction(action, expected, "Failed to wait " + seconds + SECONDS + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            Thread.currentThread().interrupt();
            return 1;
        }
        file.recordAction(action, expected, WAITED + seconds + SECONDS, Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     * 
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Element element) {
        return locatorAction.waitForElementPresent(element.getType(), element.getLocator(), 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is present
     * 
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Element element, double seconds) {
        return locatorAction.waitForElementPresent(element.getType(), element.getLocator(), 0, seconds);
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Locator type, String locator) {
        return locatorAction.waitForElementPresent(type, locator, 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Locator type, String locator, double seconds) {
        return locatorAction.waitForElementPresent(type, locator, 0, seconds);
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     * 
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Element element, int elementMatch) {
        return locatorAction.waitForElementPresent(element.getType(), element.getLocator(), elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is present
     * 
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Element element, double seconds, int elementMatch) {
        return locatorAction.waitForElementPresent(element.getType(), element.getLocator(), elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Locator type, String locator, int elementMatch) {
        return locatorAction.waitForElementPresent(type, locator, elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementPresent(Locator type, String locator, int elementMatch, double seconds) {
        return locatorAction.waitForElementPresent(type, locator, elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Element element) {
        return locatorAction.waitForElementNotPresent(element.getType(), element.getLocator(), 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Element element, double seconds) {
        return locatorAction.waitForElementNotPresent(element.getType(), element.getLocator(), 0, seconds);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Locator type, String locator) {
        return locatorAction.waitForElementNotPresent(type, locator, 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Locator type, String locator, double seconds) {
        return locatorAction.waitForElementNotPresent(type, locator, 0, seconds);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Element element, int elementMatch) {
        return locatorAction.waitForElementNotPresent(element.getType(), element.getLocator(), elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Element element, int elementMatch, double seconds) {
        return locatorAction.waitForElementNotPresent(element.getType(), element.getLocator(), elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Locator type, String locator, int elementMatch) {
        return locatorAction.waitForElementNotPresent(type, locator, elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotPresent(Locator type, String locator, int elementMatch, double seconds) {
        return locatorAction.waitForElementNotPresent(type, locator, elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Element element) {
        return locatorAction.waitForElementDisplayed(element.getType(), element.getLocator(), 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Element element, double seconds) {
        return locatorAction.waitForElementDisplayed(element.getType(), element.getLocator(), 0, seconds);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Locator type, String locator) {
        return locatorAction.waitForElementDisplayed(type, locator, 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Locator type, String locator, double seconds) {
        return locatorAction.waitForElementDisplayed(type, locator, 0, seconds);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Element element, int elementMatch) {
        return locatorAction.waitForElementDisplayed(element.getType(), element.getLocator(), elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Element element, int elementMatch, double seconds) {
        return locatorAction.waitForElementDisplayed(element.getType(), element.getLocator(), elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Locator type, String locator, int elementMatch) {
        return locatorAction.waitForElementDisplayed(type, locator, elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementDisplayed(Locator type, String locator, int elementMatch, double seconds) {
        return locatorAction.waitForElementDisplayed(type, locator, elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Element element) {
        return locatorAction.waitForElementNotDisplayed(element.getType(), element.getLocator(), 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Element element, double seconds) {
        return locatorAction.waitForElementNotDisplayed(element.getType(), element.getLocator(), 0, seconds);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Locator type, String locator) {
        return locatorAction.waitForElementNotDisplayed(type, locator, 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Locator type, String locator, double seconds) {
        return locatorAction.waitForElementNotDisplayed(type, locator, 0, seconds);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Element element, int elementMatch) {
        return locatorAction.waitForElementNotDisplayed(element.getType(), element.getLocator(), elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Element element, int elementMatch, double seconds) {
        return locatorAction.waitForElementNotDisplayed(element.getType(), element.getLocator(), elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Locator type, String locator, int elementMatch) {
        return locatorAction.waitForElementNotDisplayed(type, locator, elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotDisplayed(Locator type, String locator, int elementMatch, double seconds) {
        return locatorAction.waitForElementNotDisplayed(type, locator, elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Element element) {
        return locatorAction.waitForElementEnabled(element.getType(), element.getLocator(), 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Element element, double seconds) {
        return locatorAction.waitForElementEnabled(element.getType(), element.getLocator(), 0, seconds);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Locator type, String locator) {
        return locatorAction.waitForElementEnabled(type, locator, 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Locator type, String locator, double seconds) {
        return locatorAction.waitForElementEnabled(type, locator, 0, seconds);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Element element, int elementMatch) {
        return locatorAction.waitForElementEnabled(element.getType(), element.getLocator(), elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Element element, int elementMatch, double seconds) {
        return locatorAction.waitForElementEnabled(element.getType(), element.getLocator(), elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Locator type, String locator, int elementMatch) {
        return locatorAction.waitForElementEnabled(type, locator, elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementEnabled(Locator type, String locator, int elementMatch, double seconds) {
        return locatorAction.waitForElementEnabled(type, locator, elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Element element) {
        return locatorAction.waitForElementNotEnabled(element.getType(), element.getLocator(), 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Element element, double seconds) {
        return locatorAction.waitForElementNotEnabled(element.getType(), element.getLocator(), 0, seconds);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Locator type, String locator) {
        return locatorAction.waitForElementNotEnabled(type, locator, 0, WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Locator type, String locator, double seconds) {
        return locatorAction.waitForElementNotEnabled(type, locator, 0, seconds);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Element element, int elementMatch) {
        return locatorAction.waitForElementNotEnabled(element.getType(), element.getLocator(), elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Element element, int elementMatch, double seconds) {
        return locatorAction.waitForElementNotEnabled(element.getType(), element.getLocator(), elementMatch, seconds);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Locator type, String locator, int elementMatch) {
        return locatorAction.waitForElementNotEnabled(type, locator, elementMatch, WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForElementNotEnabled(Locator type, String locator, int elementMatch, double seconds) {
        return locatorAction.waitForElementNotEnabled(type, locator, elementMatch, seconds);
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * a method for checking if an element is present
     *
     * @param element
     *            - the element to be waited for
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Element element) {
        return locatorAction.isElementPresent(element.getType(), element.getLocator(), 0, false);
    }

    /**
     * a method for checking if an element is present
     *
     * @param element
     *            - the element to be waited for
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Element element, boolean print) {
        return locatorAction.isElementPresent(element.getType(), element.getLocator(), 0, print);
    }

    /**
     * a method for checking if an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Locator type, String locator) {
        return locatorAction.isElementPresent(type, locator, 0, false);
    }

    /**
     * a method for checking if an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Locator type, String locator, boolean print) {
        return locatorAction.isElementPresent(type, locator, 0, print);
    }

    /**
     * a method for checking if an element is present
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Element element, int elementMatch) {
        return locatorAction.isElementPresent(element.getType(), element.getLocator(), elementMatch, false);
    }

    /**
     * a method for checking if an element is present
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Element element, int elementMatch, boolean print) {
        return locatorAction.isElementPresent(element.getType(), element.getLocator(), elementMatch, print);
    }

    /**
     * a method for checking if an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Locator type, String locator, int elementMatch) {
        return locatorAction.isElementPresent(type, locator, elementMatch, false);
    }

    /**
     * a method for checking if an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementPresent(Locator type, String locator, int elementMatch, boolean print) {
        return locatorAction.isElementPresent(type, locator, elementMatch, print);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param element
     *            - the element to be waited for
     * @return boolean: whether the element is an input or not
     */
    public boolean isElementInput(Element element) {
        return locatorAction.isElementInput(element.getType(), element.getLocator(), 0, false);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param element
     *            - the element to be waited for
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementInput(Element element, boolean print) {
        return locatorAction.isElementInput(element.getType(), element.getLocator(), 0, print);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is an input or not
     */
    public boolean isElementInput(Locator type, String locator) {
        return locatorAction.isElementInput(type, locator, 0, false);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementInput(Locator type, String locator, boolean print) {
        return locatorAction.isElementInput(type, locator, 0, print);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is an input or not
     */
    public boolean isElementInput(Element element, int elementMatch) {
        return locatorAction.isElementInput(element.getType(), element.getLocator(), elementMatch, false);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementInput(Element element, int elementMatch, boolean print) {
        return locatorAction.isElementInput(element.getType(), element.getLocator(), elementMatch, print);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is an input or not
     */
    public boolean isElementInput(Locator type, String locator, int elementMatch) {
        return locatorAction.isElementInput(type, locator, elementMatch, false);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementInput(Locator type, String locator, int elementMatch, boolean print) {
        return locatorAction.isElementInput(type, locator, elementMatch, print);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Element element) {
        return locatorAction.isElementEnabled(element.getType(), element.getLocator(), 0, false);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Element element, boolean print) {
        return locatorAction.isElementEnabled(element.getType(), element.getLocator(), 0, print);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Locator type, String locator) {
        return locatorAction.isElementEnabled(type, locator, 0, false);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Locator type, String locator, boolean print) {
        return locatorAction.isElementEnabled(type, locator, 0, print);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Element element, int elementMatch) {
        return locatorAction.isElementEnabled(element.getType(), element.getLocator(), elementMatch, false);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Element element, int elementMatch, boolean print) {
        return locatorAction.isElementEnabled(element.getType(), element.getLocator(), elementMatch, print);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Locator type, String locator, int elementMatch) {
        return locatorAction.isElementEnabled(type, locator, elementMatch, false);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean isElementEnabled(Locator type, String locator, int elementMatch, boolean print) {
        return locatorAction.isElementEnabled(type, locator, elementMatch, print);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param element
     *            - the element to be waited for
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Element element) {
        return locatorAction.isElementChecked(element.getType(), element.getLocator(), 0, false);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param element
     *            - the element to be waited for
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Element element, boolean print) {
        return locatorAction.isElementChecked(element.getType(), element.getLocator(), 0, print);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Locator type, String locator) {
        return locatorAction.isElementChecked(type, locator, 0, false);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Locator type, String locator, boolean print) {
        return locatorAction.isElementChecked(type, locator, 0, print);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Element element, int elementMatch) {
        return locatorAction.isElementChecked(element.getType(), element.getLocator(), elementMatch, false);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Element element, int elementMatch, boolean print) {
        return locatorAction.isElementChecked(element.getType(), element.getLocator(), elementMatch, print);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Locator type, String locator, int elementMatch) {
        return locatorAction.isElementChecked(type, locator, elementMatch, false);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is checked or not
     */
    public boolean isElementChecked(Locator type, String locator, int elementMatch, boolean print) {
        return locatorAction.isElementChecked(type, locator, elementMatch, print);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Element element) {
        return locatorAction.isElementDisplayed(element.getType(), element.getLocator(), 0, false);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Element element, boolean print) {
        return locatorAction.isElementDisplayed(element.getType(), element.getLocator(), 0, print);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Locator type, String locator) {
        return locatorAction.isElementDisplayed(type, locator, 0, false);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Locator type, String locator, boolean print) {
        return locatorAction.isElementDisplayed(type, locator, 0, print);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Element element, int elementMatch) {
        return locatorAction.isElementDisplayed(element.getType(), element.getLocator(), elementMatch, false);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Element element, int elementMatch, boolean print) {
        return locatorAction.isElementDisplayed(element.getType(), element.getLocator(), elementMatch, print);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Locator type, String locator, int elementMatch) {
        return locatorAction.isElementDisplayed(type, locator, elementMatch, false);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is displayed or not
     */
    public boolean isElementDisplayed(Locator type, String locator, int elementMatch, boolean print) {
        return locatorAction.isElementDisplayed(type, locator, elementMatch, print);
    }

    /////////////////////////////////
    // retrieve information about the page
    /////////////////////////////////

    /**
     * a method to obtain screenshots
     *
     * @param imageName
     *            - the name of the image typically generated via functions from
     *            TestOutput.generateImageName
     */
    public void takeScreenshot(String imageName) {
        if (browser == null || browser == Browser.HTMLUNIT || browser == Browser.NONE) {
            return;
        }
        try {
            // take a screenshot
            File srcFile;
            if (System.getProperty("hubAddress") != "LOCAL") {
                WebDriver augemented = new Augmenter().augment(driver);
                srcFile = ((TakesScreenshot) augemented).getScreenshotAs(OutputType.FILE);
            } else {
                srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            }
            // now we need to save the file
            FileUtils.copyFile(srcFile, new File(imageName));
        } catch (Exception e) {
            log.error("Error taking screenshot: " + e);
        }
    }

    /**
     * a method to determine how many elements match the selector
     * 
     * @param element
     *            - the element to be waited for
     * @return Integer: how many element match the selector
     */
    public int getElementMatchCount(Element element) {
        return locatorAction.getElementMatchCount(element.getType(), element.getLocator());
    }

    /**
     * a method to determine how many elements match the selector
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: how many element match the selector
     */
    public int getElementMatchCount(Locator type, String locator) {
        return locatorAction.getElementMatchCount(type, locator);
    }

    /**
     * get the number of options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of select options
     */
    public int getNumOfSelectOptions(Element element) {
        return locatorAction.getNumOfSelectOptions(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the number of options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of select options
     */
    public int getNumOfSelectOptions(Locator type, String locator) {
        return locatorAction.getNumOfSelectOptions(type, locator, 0);
    }

    /**
     * get the number of options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of select options
     */
    public int getNumOfSelectOptions(Element element, int elementMatch) {
        return locatorAction.getNumOfSelectOptions(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the number of options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of select options
     */
    public int getNumOfSelectOptions(Locator type, String locator, int elementMatch) {
        return locatorAction.getNumOfSelectOptions(type, locator, elementMatch);
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @return String[]: the options from the select element
     */
    public String[] getSelectOptions(Element element) {
        return locatorAction.getSelectOptions(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     */
    public String[] getSelectOptions(Locator type, String locator) {
        return locatorAction.getSelectOptions(type, locator, 0);
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] getSelectOptions(Element element, int elementMatch) {
        return locatorAction.getSelectOptions(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] getSelectOptions(Locator type, String locator, int elementMatch) {
        return locatorAction.getSelectOptions(type, locator, elementMatch);
    }

    /**
     * get the rows of a table
     *
     * @param element
     *            - the element to be waited for
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> getTableRows(Element element) {
        return locatorAction.getTableRows(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> getTableRows(Locator type, String locator) {
        return locatorAction.getTableRows(type, locator, 0);
    }

    /**
     * get the rows of a table
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> getTableRows(Element element, int elementMatch) {
        return locatorAction.getTableRows(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> getTableRows(Locator type, String locator, int elementMatch) {
        return locatorAction.getTableRows(type, locator, elementMatch);
    }

    /**
     * get the number of rows of a table
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of table rows
     */
    public int getNumOfTableRows(Element element) {
        return getNumOfTableRows(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the number of rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of table rows
     */
    public int getNumOfTableRows(Locator type, String locator) {
        return getNumOfTableRows(type, locator, 0);
    }

    /**
     * get the number of rows of a table
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of table rows
     */
    public int getNumOfTableRows(Element element, int elementMatch) {
        return getNumOfTableRows(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the number of rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of table rows
     */
    public int getNumOfTableRows(Locator type, String locator, int elementMatch) {
        List<WebElement> rows = getTableRows(type, locator, elementMatch);
        return rows.size();
    }

    /**
     * get the columns of a table
     *
     * @param element
     *            - the element to be waited for
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> getTableColumns(Element element) {
        return locatorAction.getTableColumns(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> getTableColumns(Locator type, String locator) {
        return locatorAction.getTableColumns(type, locator, 0);
    }

    /**
     * get the columns of a table
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> getTableColumns(Element element, int elementMatch) {
        return locatorAction.getTableColumns(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> getTableColumns(Locator type, String locator, int elementMatch) {
        return locatorAction.getTableColumns(type, locator, elementMatch);
    }

    /**
     * get the number of columns of a table
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of table columns
     */
    public int getNumOfTableColumns(Element element) {
        return getNumOfTableColumns(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the number of columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of table columns
     */
    public int getNumOfTableColumns(Locator type, String locator) {
        return getNumOfTableColumns(type, locator, 0);
    }

    /**
     * get the number of columns of a table
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of table columns
     */
    public int getNumOfTableColumns(Element element, int elementMatch) {
        return getNumOfTableColumns(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the number of columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of table columns
     */
    public int getNumOfTableColumns(Locator type, String locator, int elementMatch) {
        List<List<WebElement>> columns = getTableColumns(type, locator, elementMatch);
        return columns.size();
    }

    /**
     * get a specific column from a table
     *
     * @param element
     *            - the element to be waited for
     * @param rowNum
     *            - the row number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableRow(Element element, int rowNum) {
        return locatorAction.getTableRow(element.getType(), element.getLocator(), 0, rowNum);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param rowNum
     *            - the row number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableRow(Locator type, String locator, int rowNum) {
        return locatorAction.getTableRow(type, locator, 0, rowNum);
    }

    /**
     * get a specific column from a table
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param rowNum
     *            - the row number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableRow(Element element, int elementMatch, int rowNum) {
        return locatorAction.getTableRow(element.getType(), element.getLocator(), elementMatch, rowNum);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param rowNum
     *            - the row number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableRow(Locator type, String locator, int elementMatch, int rowNum) {
        return locatorAction.getTableRow(type, locator, elementMatch, rowNum);
    }

    /**
     * get a specific column from a table
     *
     * @param element
     *            - the element to be waited for
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableColumn(Element element, int colNum) {
        return locatorAction.getTableColumn(element.getType(), element.getLocator(), 0, colNum);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableColumn(Locator type, String locator, int colNum) {
        return locatorAction.getTableColumn(type, locator, 0, colNum);
    }

    /**
     * get a specific column from a table
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableColumn(Element element, int elementMatch, int colNum) {
        return locatorAction.getTableColumn(element.getType(), element.getLocator(), elementMatch, colNum);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> getTableColumn(Locator type, String locator, int elementMatch, int colNum) {
        return locatorAction.getTableColumn(type, locator, elementMatch, colNum);
    }

    /**
     * get the contents of a specific cell
     *
     * @param element
     *            - the element to be waited for
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement getTableCell(Element element, int row, int col) {
        return locatorAction.getTableCell(element.getType(), element.getLocator(), 0, row, col);
    }

    /**
     * get the contents of a specific cell
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement getTableCell(Locator type, String locator, int row, int col) {
        return locatorAction.getTableCell(type, locator, 0, row, col);
    }

    /**
     * get the contents of a specific cell
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement getTableCell(Element element, int elementMatch, int row, int col) {
        return locatorAction.getTableCell(element.getType(), element.getLocator(), elementMatch, row, col);
    }

    /**
     * get the contents of a specific cell
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement getTableCell(Locator type, String locator, int elementMatch, int row, int col) {
        return locatorAction.getTableCell(type, locator, elementMatch, row, col);
    }

    //////////////////////////////////
    // override the SE actions
    //////////////////////////////////

    /**
     * the generic selenium click functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int click(Element element) {
        return locatorAction.click(element.getType(), element.getLocator(), 0);
    }

    /**
     * the generic selenium click functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int click(Locator type, String locator) {
        return locatorAction.click(type, locator, 0);
    }

    /**
     * the generic selenium click functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int click(Element element, int elementMatch) {
        return locatorAction.click(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * the generic selenium click functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int click(Locator type, String locator, int elementMatch) {
        return locatorAction.click(type, locator, elementMatch);
    }

    /**
     * the generic selenium submit functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int submit(Element element) {
        return locatorAction.submit(element.getType(), element.getLocator(), 0);
    }

    /**
     * the generic selenium submit functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int submit(Locator type, String locator) {
        return locatorAction.submit(type, locator, 0);
    }

    /**
     * the generic selenium submit functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int submit(Element element, int elementMatch) {
        return locatorAction.submit(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * the generic selenium submit functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int submit(Locator type, String locator, int elementMatch) {
        return locatorAction.submit(type, locator, elementMatch);
    }

    /**
     * a method to simulate the mouse hovering over an element
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int hover(Element element) {
        return locatorAction.hover(element.getType(), element.getLocator(), 0);
    }

    /**
     * a method to simulate the mouse hovering over an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int hover(Locator type, String locator) {
        return locatorAction.hover(type, locator, 0);
    }

    /**
     * a method to simulate the mouse hovering over an element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int hover(Element element, int elementMatch) {
        return locatorAction.hover(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * a method to simulate the mouse hovering over an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int hover(Locator type, String locator, int elementMatch) {
        return locatorAction.hover(type, locator, elementMatch);
    }

    /**
     * a custom selenium functionality to apply a blur to an element
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int blur(Element element) {
        return locatorAction.blur(element.getType(), element.getLocator(), 0);
    }

    /**
     * a custom selenium functionality to apply a blur to an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int blur(Locator type, String locator) {
        return locatorAction.blur(type, locator, 0);
    }

    /**
     * a custom selenium functionality to apply a blur to an element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int blur(Element element, int elementMatch) {
        return locatorAction.blur(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * a custom selenium functionality to apply a blur to an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int blur(Locator type, String locator, int elementMatch) {
        return locatorAction.blur(type, locator, elementMatch);
    }

    /**
     * the generic selenium type functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param text
     *            - the text to be typed in
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Element element, String text) {
        return locatorAction.type(element.getType(), element.getLocator(), 0, text);
    }

    /**
     * the generic selenium type functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param text
     *            - the text to be typed in
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Locator type, String locator, String text) {
        return locatorAction.type(type, locator, 0, text);
    }

    /**
     * the generic selenium type functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param text
     *            - the text to be typed in
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Element element, int elementMatch, String text) {
        return locatorAction.type(element.getType(), element.getLocator(), elementMatch, text);
    }

    /**
     * the generic selenium type functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param text
     *            - the text to be typed in
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Locator type, String locator, int elementMatch, String text) {
        return locatorAction.type(type, locator, elementMatch, text);
    }

    /**
     * the generic selenium type functionality implemented for specific keys
     *
     * @param element
     *            - the element to be waited for
     * @param key
     *            - the key to be pressed
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Element element, Keys key) {
        return locatorAction.type(element.getType(), element.getLocator(), 0, key);
    }

    /**
     * the generic selenium type functionality implemented for specific keys
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param key
     *            - the key to be pressed
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Locator type, String locator, Keys key) {
        return locatorAction.type(type, locator, 0, key);
    }

    /**
     * the generic selenium type functionality implemented for specific keys
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param key
     *            - the key to be pressed
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Element element, int elementMatch, Keys key) {
        return locatorAction.type(element.getType(), element.getLocator(), elementMatch, key);
    }

    /**
     * the generic selenium type functionality implemented for specific keys
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param key
     *            - the key to be pressed
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int type(Locator type, String locator, int elementMatch, Keys key) {
        return locatorAction.type(type, locator, elementMatch, key);
    }

    /**
     * the generic selenium clear functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int clear(Element element) {
        return locatorAction.clear(element.getType(), element.getLocator(), 0);
    }

    /**
     * the generic selenium clear functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int clear(Locator type, String locator) {
        return locatorAction.clear(type, locator, 0);
    }

    /**
     * the generic selenium clear functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int clear(Element element, int elementMatch) {
        return locatorAction.clear(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * the generic selenium clear functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int clear(Locator type, String locator, int elementMatch) {
        return locatorAction.clear(type, locator, elementMatch);
    }

    /**
     * the generic select selenium functionality
     *
     * @param element
     *            - the element to be waited for
     * @param value
     *            - the select option to be selected - note, row numbering
     *            starts at 0
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Element element, int value) {
        return select(element.getType(), element.getLocator(), value);
    }

    /**
     * the generic select selenium functionality
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param value
     *            - the select option to be selected - note, row numbering
     *            starts at 0
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Locator type, String locator, int value) {
        String[] options = getSelectOptions(type, locator);
        if (value > options.length) {
            String action = "Selecting " + value + " in " + type + " " + locator;
            String expected = type + " " + locator + " is present, displayed, and enabled to have the value " + value
                    + " selected";
            file.recordAction(action, expected, "Unable to select the <i>" + value
                    + "</i> option, as there are only <i>" + options.length + "</i> available.", Result.FAILURE);
            file.addError();
            return 1;
        }
        return locatorAction.select(type, locator, 0, options[value]);
    }

    /**
     * the generic select selenium functionality
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param value
     *            - the select option to be selected - note, row numbering
     *            starts at 0
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Element element, int value, int elementMatch) {
        return select(element.getType(), element.getLocator(), elementMatch, value);
    }

    /**
     * the generic select selenium functionality
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param value
     *            - the select option to be selected - note, row numbering
     *            starts at 0
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Locator type, String locator, int elementMatch, int value) {
        String[] options = getSelectOptions(type, locator, elementMatch);
        if (value > options.length) {
            String action = "Selecting " + value + " in " + type + " " + locator;
            String expected = type + " " + locator + " is present, displayed, and enabled to have the value " + value
                    + " selected";
            file.recordAction(action, expected, "Unable to select the <i>" + value
                    + "</i> option, as there are only <i>" + options.length + "</i> available.", Result.FAILURE);
            file.addError();
            return 1;
        }
        return locatorAction.select(type, locator, elementMatch, options[value]);
    }

    /**
     * the generic select selenium functionality
     *
     * @param element
     *            - the element to be waited for
     * @param value
     *            - the select option to be selected
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Element element, String value) {
        return locatorAction.select(element.getType(), element.getLocator(), 0, value);
    }

    /**
     * the generic select selenium functionality
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param value
     *            - the select option to be selected
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Locator type, String locator, String value) {
        return locatorAction.select(type, locator, 0, value);
    }

    /**
     * the generic select selenium functionality
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param value
     *            - the select option to be selected
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Element element, String value, int elementMatch) {
        return locatorAction.select(element.getType(), element.getLocator(), elementMatch, value);
    }

    /**
     * the generic select selenium functionality
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param value
     *            - the select option to be selected
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Locator type, String locator, int elementMatch, String value) {
        return locatorAction.select(type, locator, elementMatch, value);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Element element) {
        return locatorAction.move(element.getType(), element.getLocator(), 0);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Locator type, String locator) {
        return locatorAction.move(type, locator, 0);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Element element, int elementMatch) {
        return locatorAction.move(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Locator type, String locator, int elementMatch) {
        return locatorAction.move(type, locator, elementMatch);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param element
     *            - the element to be waited for
     * @param position
     *            - how many pixels above the element to scroll to
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Element element, long position) {
        return locatorAction.move(element.getType(), element.getLocator(), 0, position);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param position
     *            - how many pixels above the element to scroll to
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Locator type, String locator, long position) {
        return locatorAction.move(type, locator, 0, position);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param position
     *            - how many pixels above the element to scroll to
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Element element, int elementMatch, long position) {
        return locatorAction.move(element.getType(), element.getLocator(), elementMatch, position);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param position
     *            - how many pixels above the element to scroll to
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int move(Locator type, String locator, int elementMatch, long position) {
        return locatorAction.move(type, locator, elementMatch, position);
    }

    /**
     * An custom script to scroll to a given position on the page
     *
     * @param desiredPosition
     *            - the position on the page to scroll to
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int scroll(int desiredPosition) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Long initialPosition = (Long) jse.executeScript("return window.scrollY;");

        String action = "Scrolling page from " + initialPosition + " to " + desiredPosition;
        String expected = "Page is now set at position " + desiredPosition;

        jse.executeScript("window.scrollBy(0, " + desiredPosition + ")");

        Long newPosition = (Long) jse.executeScript("return window.scrollY;");

        if (newPosition != desiredPosition) {
            file.recordAction(action, expected, "Page is set at position " + newPosition, Result.FAILURE);
            file.addError();
            return 1; // indicates page didn't scroll properly
        }
        file.recordAction(action, expected, "Page is now set at position " + newPosition, Result.SUCCESS);
        return 0; // indicates page scrolled properly
    }

    /**
     * a function to switch windows.
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int switchToNewWindow() {
        String action = "Switching to the new window";
        String expected = "New window is available and selected";
        try {
            parentWindow = driver.getWindowHandle();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        } catch (Exception e) {
            file.recordAction(action, expected, "New window was unable to be selected. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a function to switch back to the parent window.
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int switchToParentWindow() {
        String action = "Switching back to parent window";
        String expected = "Parent window is available and selected";
        try {
            driver.switchTo().window(parentWindow);
        } catch (Exception e) {
            file.recordAction(action, expected, "Parent window was unable to be selected. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a function to close the currently selected window. After this window is
     * closed, ensure that focus is shifted to the next window using
     * switchToXxxWindow methods
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int closeCurrentWindow() {
        String action = "Closing currently selected window";
        String expected = "Current window is closed";
        try {
            driver.close();
        } catch (Exception e) {
            file.recordAction(action, expected, "Current window was unable to be closed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a way to select a frame within a page. Select a frame by its (zero-based)
     * index. That is, if a page has three frames, the first frame would be at
     * index 0, the second at index 1 and the third at index 2. Once the frame
     * has been selected, all subsequent calls on the WebDriver interface are
     * made to that frame.
     * 
     * @param frameNumber
     *            - the frame number, starts at 0
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(int frameNumber) {
        String action = "Switching to frame <b>" + frameNumber + "</b>";
        String expected = FRAME + frameNumber + AVAILABLE;
        try {
            driver.switchTo().frame(frameNumber);
        } catch (Exception e) {
            file.recordAction(action, expected, FRAME + frameNumber + NOTSELECTED + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a way to select a frame within a page. Select a frame by its name or ID.
     * Frames located by matching name attributes are always given precedence
     * over those matched by ID.
     * 
     * @param frameIdentifier
     *            - the frame name or ID
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(String frameIdentifier) {
        String action = "Switching to frame <b>" + frameIdentifier + "</b>";
        String expected = FRAME + frameIdentifier + AVAILABLE;
        try {
            driver.switchTo().frame(frameIdentifier);
        } catch (Exception e) {
            file.recordAction(action, expected, FRAME + frameIdentifier + NOTSELECTED + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a way to select a frame within a page using an element
     * 
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(Element element) {
        return locatorAction.selectFrame(element.getType(), element.getLocator(), 0);
    }

    /**
     * a way to select a frame within a page using an element
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(Locator type, String locator) {
        return locatorAction.selectFrame(type, locator, 0);
    }

    /**
     * a way to select a frame within a page using an element
     * 
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(Element element, int elementMatch) {
        return locatorAction.selectFrame(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * a way to select a frame within a page using an element
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(Locator type, String locator, int elementMatch) {
        return locatorAction.selectFrame(type, locator, elementMatch);
    }

    /**
     * A private method to send a key combination both as control and command
     * (PC and Mac compatibile)
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param fail
     *            - the failed result
     * @param key
     *            - what key to send along with control and/or command
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    private int sendControlAndCommand(String action, String expected, String fail, String key) {
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + key);
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + key);
        } catch (Exception e) {
            file.recordAction(action, expected, fail + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a way to open a new tab, and have it selected. Note, no content will be
     * present on this new tab, use the goToURL method to open load some content
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int openTab() {
        return sendControlAndCommand("Opening new tab", "New tab is opened", "New tab was unable to be opened. ", "t");
    }

    /**
     * a way to open a new tab, and have it selected. The page provided will be
     * loaded
     * 
     * @param url
     *            - the url to load once the new tab is opened and selected
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int openTab(String url) {
        if (openTab() != 0) {
            return 1;
        }
        return goToURL(url);
    }

    /**
     * a function to switch tabs. this method will switch to the next available
     * tab. if the last tab is already selected, it will loop back to the first
     * tab
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int switchNextTab() {
        String action = "Switching to next tab ";
        String expected = "Next tab <b>" + AVAILABLE;
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.PAGE_DOWN));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.PAGE_DOWN));
        } catch (Exception e) {
            file.recordAction(action, expected, "Next tab <b>" + NOTSELECTED + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a function to switch tabs. this method will switch to the previous
     * available tab. if the first tab is already selected, it will loop back to
     * the last tab
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int switchPreviousTab() {
        String action = "Switching to previous tab ";
        String expected = "Previous tab <b>" + AVAILABLE;
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.PAGE_UP));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.PAGE_UP));
        } catch (Exception e) {
            file.recordAction(action, expected, "Previous tab <b>" + NOTSELECTED + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * a function to close tabs. note that if this is the only tab open, you
     * will end the session
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int closeTab() {
        return sendControlAndCommand("Closing currently open tab", "Tab is closed", "Tab was unable to be closed. ",
                "w");
    }

    /**
     * some functionality to go back one page in browser history
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int goBackOnePage() {
        String action = "Going back one page";
        String expected = "Previous page from browser history is loaded";
        try {
            driver.navigate().back();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to go back one page. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to go forward one page in browser history
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int goForwardOnePage() {
        String action = "Going forward one page";
        String expected = "Next page from browser history is loaded";
        try {
            driver.navigate().forward();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to go forward one page. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to reload the current page
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int refreshPage() {
        String action = "Reloading current page";
        String expected = "Page is refreshed";
        try {
            driver.navigate().refresh();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to be refreshed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to reload the current page while clearing the cache.
     * This only works for certain browsers, as the command/control and F5 key
     * combination is used to perform the action
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int refreshPageHard() {
        String action = "Reloading current page while clearing the cache";
        String expected = "Cache is cleared, and the page is refreshed";
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.F5));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.F5));
        } catch (Exception e) {
            file.recordAction(action, expected,
                    "There was a problem clearing the cache and reloading the page. " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to add a specific cookie to the page
     * 
     * @param cookie
     *            - the details of the cookie to set
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int setCookie(Cookie cookie) {
        String domain = cookie.getDomain();
        Date expiry = cookie.getExpiry();
        String name = cookie.getName();
        String path = cookie.getPath();
        String value = cookie.getValue();

        String action = "Setting up cookie with attributes:<div><table><tbody><tr><td>Domain</td><td>" + domain
                + "</tr><tr><td>Expiration</td><td>" + expiry.toString() + "</tr><tr><td>Name</td><td>" + name
                + "</tr><tr><td>Path</td><td>" + path + "</tr><tr><td>Value</td><td>" + value
                + "</tr></tbody></table></div><br/>";
        String expected = "Cookie is added";
        try {
            driver.manage().addCookie(cookie);
        } catch (Exception e) {
            file.recordAction(action, expected, "Unable to add cookie. " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to delete a specifically stored cookie
     * 
     * @param cookieName
     *            - the name of the cookie to delete
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int deleteCookie(String cookieName) {
        String action = "Deleting cookie <i>" + cookieName + "</i>";
        String expected = "Cookie <i>" + cookieName + "</i> is removed";
        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        if (cookie == null) {
            file.recordAction(action, expected,
                    "Unable to remove cookie <i>" + cookieName + "</i> as it doesn't exist.", Result.FAILURE);
            file.addError();
            return 1;
        }
        try {
            driver.manage().deleteCookieNamed(cookieName);
        } catch (Exception e) {
            file.recordAction(action, expected, "Unable to remove cookie <i>" + cookieName + "</i>. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to delete all currently stored cookies
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int deleteAllCookies() {
        String action = "Deleting all cookies";
        String expected = "All cookies are removed";
        try {
            driver.manage().deleteAllCookies();
        } catch (Exception e) {
            file.recordAction(action, expected, "Unable to remove all cookies. " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * some functionality to maximize the current window
     * 
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int maximizeScreen() {
        String action = "Maximizing browser";
        String expected = "Browser is maximized";
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to be maximized. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return 1;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return 0;
    }

    /**
     * A private method to accept (click 'OK' on) whatever popup is present on
     * the page
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param popup
     *            - the element we are interacting with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    private int accept(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Unable to click 'OK' on the " + popup + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Clicked 'OK' on the " + popup, Result.SUCCESS);
        return 0;
    }

    /**
     * A private method to dismiss (click 'Cancel' on) whatever popup is present
     * on the page
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param popup
     *            - the element we are interacting with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    private int dismiss(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Unable to click 'Cancel' on the " + popup + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Clicked 'Cancel' on the " + popup, Result.SUCCESS);
        return 0;
    }

    /**
     * A private method to determine if a confirmation is present or not, and
     * can be interacted with
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    private int isConfirmation(String action, String expected) {
        // wait for element to be present
        if (!isConfirmationPresent(false)) {
            waitForConfirmationPresent();
        }
        if (!isConfirmationPresent(false)) {
            file.recordAction(action, expected, "Unable to click confirmation as it is not present", Result.FAILURE);
            file.addError();
            return 1; // indicates element not present
        }
        return 0;
    }

    /**
     * A private method to determine if a prompt is present or not, and can be
     * interacted with
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param perform
     *            - the action occurring to the prompt
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    private int isPrompt(String action, String expected, String perform) {
        // wait for element to be present
        if (!isPromptPresent(false)) {
            waitForPromptPresent();
        }
        if (!isPromptPresent(false)) {
            file.recordAction(action, expected, "Unable to " + perform + " prompt as it is not present",
                    Result.FAILURE);
            file.addError();
            return 1; // indicates element not present
        }
        return 0;
    }

    /**
     * Some basic functionality for clicking 'OK' on an alert box
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int acceptAlert() {
        String action = "Clicking 'OK' on an alert";
        String expected = "Alert is present to be clicked";
        // wait for element to be present
        if (!isAlertPresent(false)) {
            waitForAlertPresent();
        }
        if (!isAlertPresent(false)) {
            file.recordAction(action, expected, "Unable to click alert as it is not present", Result.FAILURE);
            file.addError();
            return 1; // indicates element not present
        }
        return accept(action, expected, "alert");
    }

    /**
     * Some basic functionality for clicking 'OK' on a confirmation box
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int acceptConfirmation() {
        String action = "Clicking 'OK' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        if (isConfirmation(action, expected) != 0) {
            return 1;
        }
        return accept(action, expected, "confirmation");
    }

    /**
     * Some basic functionality for clicking 'Cancel' on a confirmation box
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int dismissConfirmation() {
        String action = "Clicking 'Cancel' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        if (isConfirmation(action, expected) != 0) {
            return 1;
        }
        return dismiss(action, expected, "confirmation");
    }

    /**
     * Some basic functionality for clicking 'OK' on a prompt box
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int acceptPrompt() {
        String action = "Clicking 'OK' on a prompt";
        String expected = "Prompt is present to be clicked";
        if (isPrompt(action, expected, "click") != 0) {
            return 1;
        }
        return accept(action, expected, "prompt");
    }

    /**
     * Some basic functionality for clicking 'Cancel' on a prompt box
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int dismissPrompt() {
        String action = "Clicking 'Cancel' on a prompt";
        String expected = "Prompt is present to be clicked";
        if (isPrompt(action, expected, "click") != 0) {
            return 1;
        }
        return dismiss(action, expected, "prompt");
    }

    /**
     * Some basic functionality for typing text into a prompt box
     * 
     * @param text
     *            - the text to type into the prompt
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int typeIntoPrompt(String text) {
        String action = "Typing text '" + text + "' into prompt";
        String expected = "Prompt is present and enabled to have text " + text + " typed in";
        if (isPrompt(action, expected, "type into") != 0) {
            return 1;
        }
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Unable to type into prompt. " + e.getMessage(), Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Typed text '" + text + "' into prompt", Result.SUCCESS);
        return 0;
    }

    // //////////////////////////////////
    // extra base selenium functionality
    // //////////////////////////////////

    /**
     * waits for a popup to be present, and then returns the amount of time it
     * waited
     * 
     * @param seconds
     *            - maximum time to wait in seconds
     * @return double - the total time waited
     */
    private double waitForPopup(double seconds) {
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                    // a drop down.
                driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e) {
                log.error(e);
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        return timetook / 1000;
    }

    /**
     * a method to check if an alert is present
     *
     * @return boolean - is an alert present
     */
    public boolean isAlertPresent() {
        return isAlertPresent(false);
    }

    /**
     * a method to check if an alert is present
     *
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean - is an alert present
     */
    public boolean isAlertPresent(boolean print) {
        boolean isPresent = false;
        try {
            driver.switchTo().alert();
            isPresent = true;
        } catch (NoAlertPresentException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected("Checking for alert to be present");
        }
        return isPresent;
    }

    // /////////////////////////////////////////////
    // a set of selenium checking functionality, used for building logic
    // ///////////////////////////////////////////

    /**
     * a method for waiting up to 5 seconds for an alert is present
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForAlertPresent() {
        return waitForAlertPresent(WAITFOR);
    }

    /**
     * a method for waiting until an alert is present
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForAlertPresent(double seconds) {
        String action = UPTO + seconds + " seconds for an alert to be present";
        String expected = "An alert is present";
        double timetook = waitForPopup(seconds);
        if (!isAlertPresent(false)) {
            file.recordAction(action, expected, AFTER + timetook + " seconds, an alert is not present", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for an alert to be present", Result.SUCCESS);
        return 0;
    }

    /**
     * a method to return the content of an alert
     *
     * @return String - the content of an alert
     */
    public String getAlert() {
        if (!isAlertPresent(false)) {
            waitForAlertPresent();
        }
        if (!isAlertPresent(false)) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to check if a confirmation is present
     *
     * @return boolean - is a confirmation present
     */
    public boolean isConfirmationPresent() {
        return isConfirmationPresent(false);
    }

    /**
     * a method to check if a confirmation is present
     *
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean - is a confirmation present
     */
    public boolean isConfirmationPresent(boolean print) {
        boolean isPresent = false;
        try {
            driver.switchTo().alert();
            isPresent = true;
        } catch (NoAlertPresentException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected("Checking for confirmation to be present");
        }
        return isPresent;
    }

    /**
     * a method for waiting up to 5 seconds for a confirmation is present
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForConfirmationPresent() {
        return waitForConfirmationPresent(WAITFOR);
    }

    /**
     * a method for waiting until a confirmation is present
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForConfirmationPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a confirmation to be present";
        String expected = "A confirmation is present";
        double timetook = waitForPopup(seconds);
        if (!isConfirmationPresent(false)) {
            file.recordAction(action, expected, AFTER + timetook + " seconds, a confirmation is not present",
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for a confirmation to be present",
                Result.SUCCESS);
        return 0;
    }

    /**
     * a method to return the content of a confirmation
     *
     * @return String - the content of the confirmation
     */
    public String getConfirmation() {
        if (!isConfirmationPresent(false)) {
            waitForConfirmationPresent();
        }
        if (!isConfirmationPresent(false)) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to check if a prompt is present
     *
     * @return boolean - is a prompt present
     */
    public boolean isPromptPresent() {
        return isPromptPresent(false);
    }

    /**
     * a method to check if a prompt is present
     *
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean - is a prompt present
     */
    public boolean isPromptPresent(boolean print) {
        boolean isPresent = false;
        try {
            driver.switchTo().alert();
            isPresent = true;
        } catch (NoAlertPresentException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected("Checking for prompt to be present");
        }
        return isPresent;
    }

    /**
     * a method for waiting up to 5 seconds for a prompt is present
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForPromptPresent() {
        return waitForPromptPresent(WAITFOR);
    }

    /**
     * a method for waiting until a prompt is present
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int waitForPromptPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a prompt to be present";
        String expected = "A prompt is present";
        double timetook = waitForPopup(seconds);
        if (!isPromptPresent(false)) {
            file.recordAction(action, expected, AFTER + timetook + " seconds, a prompt is not present", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for a prompt to be present", Result.SUCCESS);
        return 0;
    }

    /**
     * a method to return the content of a prompt
     *
     * @return String - the content of the prompt
     */
    public String getPrompt() {
        if (!isPromptPresent(false)) {
            waitForPromptPresent();
        }
        if (!isPromptPresent(false)) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to determine if a cookie by a particular name is present or not
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return boolean - if the cookie is present
     */
    public boolean isCookiePresent(String expectedCookieName) {
        boolean isCookiePresent = false;
        if (getCookie(expectedCookieName) != null) {
            isCookiePresent = true;
        }
        return isCookiePresent;
    }

    /**
     * a method to get the full cookie by a particular name
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return Cookie - the cookie
     */
    public Cookie getCookie(String expectedCookieName) {
        try {
            return driver.manage().getCookieNamed(expectedCookieName);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to get the value of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the value of the cookie
     */
    public String getCookieValue(String expectedCookieName) {
        Cookie cookie = getCookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * a method to get the path of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the path of the cookie
     */
    public String getCookiePath(String expectedCookieName) {
        Cookie cookie = getCookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getPath();
        }
        return null;
    }

    /**
     * a method to get the domain of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the domain of the cookie
     */
    public String getCookieDomain(String expectedCookieName) {
        Cookie cookie = getCookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getDomain();
        }
        return null;
    }

    /**
     * a method to get the expriation of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the expiration of the cookie
     */
    public Date getCookieExpiration(String expectedCookieName) {
        Cookie cookie = getCookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getExpiry();
        }
        return null;
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param element
     *            - the element to be waited for
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Element element) {
        return locatorAction.isSomethingSelected(element.getType(), element.getLocator(), 0, false);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param element
     *            - the element to be waited for
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Element element, boolean print) {
        return locatorAction.isSomethingSelected(element.getType(), element.getLocator(), 0, print);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * 
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Locator type, String locator) {
        return locatorAction.isSomethingSelected(type, locator, 0, false);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Locator type, String locator, boolean print) {
        return locatorAction.isSomethingSelected(type, locator, 0, print);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Element element, int elementMatch) {
        return locatorAction.isSomethingSelected(element.getType(), element.getLocator(), elementMatch, false);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Element element, int elementMatch, boolean print) {
        return locatorAction.isSomethingSelected(element.getType(), element.getLocator(), elementMatch, print);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Locator type, String locator, int elementMatch) {
        return locatorAction.isSomethingSelected(type, locator, elementMatch, false);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param print
     *            - whether or not to print out this wait statement
     * @return boolean: is something selected or not
     */
    public boolean isSomethingSelected(Locator type, String locator, int elementMatch, boolean print) {
        return locatorAction.isSomethingSelected(type, locator, elementMatch, print);
    }

    /**
     * get the option from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @return String: the option from the select element
     */
    public String getSelectedText(Element element) {
        return locatorAction.getSelectedText(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String: the option from the select element
     */
    public String getSelectedText(Locator type, String locator) {
        return locatorAction.getSelectedText(type, locator, 0);
    }

    /**
     * get the option from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String: the option from the select element
     */
    public String getSelectedText(Element element, int elementMatch) {
        return locatorAction.getSelectedText(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String: the option from the select element
     */
    public String getSelectedText(Locator type, String locator, int elementMatch) {
        return locatorAction.getSelectedText(type, locator, elementMatch);
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @return String[]: the options from the select element
     */
    public String[] getSelectedTexts(Element element) {
        return locatorAction.getSelectedTexts(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     */
    public String[] getSelectedTexts(Locator type, String locator) {
        return locatorAction.getSelectedTexts(type, locator, 0);
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] getSelectedTexts(Element element, int elementMatch) {
        return locatorAction.getSelectedTexts(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] getSelectedTexts(Locator type, String locator, int elementMatch) {
        return locatorAction.getSelectedTexts(type, locator, elementMatch);
    }

    /**
     * get the option from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @return String: the option from the select element
     */
    public String getSelectedValue(Element element) {
        return locatorAction.getSelectedValue(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String: the option from the select element
     */
    public String getSelectedValue(Locator type, String locator) {
        return locatorAction.getSelectedValue(type, locator, 0);
    }

    /**
     * get the option from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String: the option from the select element
     */
    public String getSelectedValue(Element element, int elementMatch) {
        return locatorAction.getSelectedValue(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String: the option from the select element
     */
    public String getSelectedValue(Locator type, String locator, int elementMatch) {
        return locatorAction.getSelectedValue(type, locator, elementMatch);
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @return String[]: the options from the select element
     */
    public String[] getSelectedValues(Element element) {
        return locatorAction.getSelectedValues(element.getType(), element.getLocator(), 0);
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     */
    public String[] getSelectedValues(Locator type, String locator) {
        return locatorAction.getSelectedValues(type, locator, 0);
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] getSelectedValues(Element element, int elementMatch) {
        return locatorAction.getSelectedValues(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] getSelectedValues(Locator type, String locator, int elementMatch) {
        return locatorAction.getSelectedValues(type, locator, elementMatch);
    }

    /**
     * a specialized selenium is text present in the page source functionality
     *
     * @param expectedText
     *            - the text we are expecting to be present on the page
     * @return boolean - whether or not the text is present
     */
    public boolean isTextPresentInSource(String expectedText) {
        try {
            return driver.getPageSource().contains(expectedText);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * the generic selenium is text present functionality implemented
     *
     * @param expectedText
     *            - the text we are expecting to be present on the page
     * @return boolean - whether or not the text is present
     */
    public boolean isTextPresent(String expectedText) {
        try {
            String bodyText = driver.findElement(By.tagName("body")).getText();
            return bodyText.contains(expectedText);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @return String - the text of the element
     */
    public String getText(Element element) {
        return locatorAction.getText(element.getType(), element.getLocator(), 0);
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String - the text of the element
     */
    public String getText(Locator type, String locator) {
        return locatorAction.getText(type, locator, 0);
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the text of the element
     */
    public String getText(Element element, int elementMatch) {
        return locatorAction.getText(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the text of the element
     */
    public String getText(Locator type, String locator, int elementMatch) {
        return locatorAction.getText(type, locator, elementMatch);
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @return String - the text of the element
     */
    public String getValue(Element element) {
        return locatorAction.getValue(element.getType(), element.getLocator(), 0);
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String - the text of the element
     */
    public String getValue(Locator type, String locator) {
        return locatorAction.getValue(type, locator, 0);
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the text of the element
     */
    public String getValue(Element element, int elementMatch) {
        return locatorAction.getValue(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the text of the element
     */
    public String getValue(Locator type, String locator, int elementMatch) {
        return locatorAction.getValue(type, locator, elementMatch);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param element
     *            - the element to be waited for
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getCss(Element element, String attribute) {
        return locatorAction.getCss(element.getType(), element.getLocator(), 0, attribute);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getCss(Locator type, String locator, String attribute) {
        return locatorAction.getCss(type, locator, 0, attribute);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getCss(Element element, int elementMatch, String attribute) {
        return locatorAction.getCss(element.getType(), element.getLocator(), elementMatch, attribute);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getCss(Locator type, String locator, int elementMatch, String attribute) {
        return locatorAction.getCss(type, locator, elementMatch, attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param element
     *            - the element to be waited for
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getAttribute(Element element, String attribute) {
        return locatorAction.getAttribute(element.getType(), element.getLocator(), 0, attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getAttribute(Locator type, String locator, String attribute) {
        return locatorAction.getAttribute(type, locator, 0, attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getAttribute(Element element, int elementMatch, String attribute) {
        return locatorAction.getAttribute(element.getType(), element.getLocator(), elementMatch, attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String getAttribute(Locator type, String locator, int elementMatch, String attribute) {
        return locatorAction.getAttribute(type, locator, elementMatch, attribute);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param element
     *            - the element to be waited for
     * @return String - the value of the css attribute
     */
    public Map<String, String> getAllAttributes(Element element) {
        return locatorAction.getAllAttributes(element.getType(), element.getLocator(), 0);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String - the value of the css attribute
     */
    public Map<String, String> getAllAttributes(Locator type, String locator) {
        return locatorAction.getAllAttributes(type, locator, 0);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the value of the css attribute
     */
    public Map<String, String> getAllAttributes(Element element, int elementMatch) {
        return locatorAction.getAllAttributes(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the value of the css attribute
     */
    public Map<String, String> getAllAttributes(Locator type, String locator, int elementMatch) {
        return locatorAction.getAllAttributes(type, locator, elementMatch);
    }

    /**
     * An extension of the selenium functionality to retrieve the current url of
     * the application
     *
     * @return String - current url
     */
    public String getLocation() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * An extension of the selenium functionality to retrieve the current title
     * of the application
     *
     * @return String - title
     */
    public String getTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * An extension of the selenium functionality to retrieve the html source of
     * the application
     *
     * @return String - page source
     */
    public String getHtmlSource() {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object getEval(String javascriptFunction) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javascriptFunction);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param element
     *            - the element to be waited for
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object getEval(Element element, String javascriptFunction) {
        return locatorAction.getEval(element.getType(), element.getLocator(), 0, javascriptFunction);
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object getEval(Locator type, String locator, String javascriptFunction) {
        return locatorAction.getEval(type, locator, 0, javascriptFunction);
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object getEval(Element element, int elementMatch, String javascriptFunction) {
        return locatorAction.getEval(element.getType(), element.getLocator(), elementMatch, javascriptFunction);
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object getEval(Locator type, String locator, int elementMatch, String javascriptFunction) {
        return locatorAction.getEval(type, locator, elementMatch, javascriptFunction);
    }

    ///////////////////////////////////////////////////////////////////
    // some simple actions for our services
    ///////////////////////////////////////////////////////////////////
    
    public Response makeGetCall(String endpoint, Request params) {
        return makeGetCall(endpoint, params, true);
    }
    
    public Response makeGetCall(String endpoint, Request params, boolean print) {
        String action = "Making <i>GET</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>GET</i> call was made successfully";
        Response response;
        try {
            response = http.get(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>GET</i> call failed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }
    
    public Response makePostCall(String endpoint, Request params) {
        return makePostCall(endpoint, params, true);
    }
    
    public Response makePostCall(String endpoint, Request params, boolean print) {
        String action = "Making <i>POST</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>POST</i> call was made successfully";
        Response response;
        try {
            response = http.post(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>POST</i> call failed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }
    
    public Response makePutCall(String endpoint, Request params) {
        return makePutCall(endpoint, params, true);
    }
    
    public Response makePutCall(String endpoint, Request params, boolean print) {
        String action = "Making <i>PUT</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>PUT</i> call was made successfully";
        Response response;
        try {
            response = http.put(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>PUT</i> call failed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }
    
    public Response makeDeleteCall(String endpoint, Request params) {
        return makeDeleteCall(endpoint, params, true);
    }
    
    public Response makeDeleteCall(String endpoint, Request params, boolean print) {
        String action = "Making <i>DELETE</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>DELETE</i> call was made successfully";
        Response response;
        try {
            response = http.delete(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>DELETE</i> call failed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }
}