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

package tools.selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;
import selenified.exceptions.InvalidActionException;
import selenified.exceptions.InvalidBrowserException;
import selenified.exceptions.InvalidLocatorTypeException;
import tools.General;
import tools.logging.TestOutput;
import tools.logging.TestOutput.Result;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Selenium Webdriver Before each action is performed a screenshot is taken
 * After each check is performed a screenshot is taken These are all placed into
 * the output file
 *
 * @author Max Saperstone
 * @version 1.0.5
 * @lastupdate 8/29/2016
 */
public class SeleniumHelper {

    private static final Logger log = Logger.getLogger(General.class);

    // this will be the name of the file we write all commands out to
    private TestOutput output;

    // what locator actions are available in webdriver
    // this is our driver that will be used for all selenium actions
    private WebDriver driver;

    // what browsers are we interested in implementing
    // this is the browser that we are using
    private Browsers browser;
    private DesiredCapabilities capabilities;
    /**
     * our constructor, determining which browser use and how to run the
     * browser: either grid or standalone
     *
     * @param output - the TestOutput file. This is provided by the
     *               SeleniumTestBase functionality
     * @throws InvalidBrowserException
     * @throws MalformedURLException
     */
    public SeleniumHelper(Browsers browser, DesiredCapabilities capabilities, TestOutput output) throws InvalidBrowserException, MalformedURLException {
        this.browser = browser;
        this.capabilities = capabilities;
        this.output = output;

        // if we want to test remotely
        if (System.getProperty("hub") != null) {
            driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), capabilities);
        } else {
            capabilities.setJavascriptEnabled(true);
            driver = SeleniumSetup.setupDriver(browser, capabilities);
        }
        output.setSelHelper(this);
    }

    /**
     * a method to allow retrieving our driver instance
     *
     * @return WebDriver: access to the driver controlling our browser via
     * webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * a method to allow retrieving our set browser
     *
     * @return Browsers: the enum of the browser
     */
    public Browsers getBrowser() {
        return browser;
    }

    /**
     * a method to allow retreiving our set capabilities
     *
     * @return capabilities: the listing of set capabilities
     */
    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * a method to end our driver instance
     */
    public void killDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * a method for navigating to a new url
     *
     * @param URL : the URL to navigate to
     */
    public int goToURL(String URL) {
        String action = "Loading " + URL;
        String expected = "Loaded " + URL;
        double start = System.currentTimeMillis();
        try {
            driver.get(URL);
        } catch (Exception e) {
            log.error(e);
            output.recordAction(action, expected, "Fail to Load " + URL, Result.FAILURE);
            output.addError();
            return 1;
        }
        double timetook = System.currentTimeMillis() - start;
        timetook = timetook / 1000;
        output.recordAction(action, expected, "Loaded " + URL + " in " + timetook + " seconds", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for allowing Selenium to pause for a set amount of time
     *
     * @param seconds : the number of seconds to wait
     */
    public int wait(int seconds) {
        return wait(Double.valueOf(seconds));
    }

    // ///////////////////////////////////////
    // generic navigational functionality
    // ///////////////////////////////////////

    /**
     * a method for allowing Selenium to pause for a set amount of time
     *
     * @param seconds : the number of seconds to wait
     */
    public int wait(double seconds) {
        String action = "Wait " + seconds + " seconds";
        String expected = "Waited " + seconds + " seconds";
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            log.error(e);
            output.recordAction(action, expected, "Failed to wait " + seconds + " seconds", Result.FAILURE);
            output.addError();
            Thread.currentThread().interrupt();
            return 1;
        }
        output.recordAction(action, expected, "Waited " + seconds + " seconds", Result.SUCCESS);
        return 0;
    }

    // ///////////////////////////////////////
    // waiting functionality
    // ///////////////////////////////////////

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int waitForElementPresent(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return waitForElementPresent(type, locator, 5);
    }

    /**
     * a method for waiting until an element is present
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int waitForElementPresent(Locators type, String locator, long seconds)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Wait up to " + seconds + " seconds for " + type + " " + locator + " to be present";
        String expected = type + " " + locator + " is present";
        // wait for up to XX seconds for our error message
        long end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                // a drop down.
                getWebElement(type, locator).getText();
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                log.error(e);
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        timetook = timetook / 1000;
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds for " + type + " " + locator + " is not present",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected,
                "Waited " + timetook + " seconds for " + type + " " + locator + " to be present", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int waitForElementNotPresent(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return waitForElementNotPresent(type, locator, 5);
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int waitForElementNotPresent(Locators type, String locator, long seconds)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Wait up to " + seconds + " seconds for " + type + " " + locator + " to not be present";
        String expected = type + " " + locator + " is not present";
        // wait for up to XX seconds for our error message
        long end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            if (!isElementPresent(type, locator)) {
                break;
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        timetook = timetook / 1000;
        if (isElementPresent(type, locator)) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds for " + type + " " + locator + " is still present",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected,
                "Waited " + timetook + " seconds for " + type + " " + locator + " to not be present", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementDisplayed(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return waitForElementDisplayed(type, locator, 5);
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementDisplayed(Locators type, String locator, int seconds)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Wait up to " + seconds + " seconds for " + type + " " + locator + " to be displayed";
        String expected = type + " " + locator + " is displayed";
        double start = System.currentTimeMillis();
        if (!isElementPresent(type, locator, false)) {
            int success = waitForElementPresent(type, locator, seconds);
            if (success == 1) {
                return success;
            }
        }
        WebElement element = getWebElement(type, locator);
        if (!element.isDisplayed()) {
            // wait for up to XX seconds
            long end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (element.isDisplayed()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (!element.isDisplayed()) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds for " + type + " " + locator + " is not displayed",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected,
                "Waited " + timetook + " seconds for " + type + " " + locator + " to be displayed", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementNotDisplayed(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return waitForElementNotDisplayed(type, locator, 5);
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementNotDisplayed(Locators type, String locator, int seconds)
            throws InvalidActionException, InvalidLocatorTypeException {
        // TODO - this might fail if the element disappears completely
        String action = "Wait up to " + seconds + " seconds for " + type + " " + locator + " to not be displayed";
        String expected = type + " " + locator + " is not displayed";
        double start = System.currentTimeMillis();
        WebElement element = getWebElement(type, locator);
        if (element.isDisplayed()) {
            // wait for up to XX seconds
            long end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (!element.isDisplayed()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (element.isDisplayed()) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds for " + type + " " + locator + " is still displayed",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected,
                "Waited " + timetook + " seconds for " + type + " " + locator + " to not be displayed", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementEnabled(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return waitForElementEnabled(type, locator, 5);
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementEnabled(Locators type, String locator, int seconds)
            throws InvalidLocatorTypeException, InvalidActionException {
        String action = "Wait up to " + seconds + " seconds for " + type + " " + locator + " to be enabled";
        String expected = type + " " + locator + " is enabled";
        double start = System.currentTimeMillis();
        if (!isElementEnabled(type, locator, false)) {
            if (!isElementPresent(type, locator, false)) {
                waitForElementPresent(type, locator, seconds);
            }
            if (!isElementEnabled(type, locator, false)) {
                WebElement element = getWebElement(type, locator);
                // wait for up to XX seconds for our error message
                long end = System.currentTimeMillis() + (seconds * 1000);
                while (System.currentTimeMillis() < end) {
                    // If results have been returned, the results are displayed
                    // in a drop down.
                    if (element.isEnabled()) {
                        break;
                    }
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds for " + type + " " + locator + " is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected,
                "Waited " + timetook + " seconds for " + type + " " + locator + " to be enabled", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementNotEnabled(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return waitForElementNotEnabled(type, locator, 5);
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int waitForElementNotEnabled(Locators type, String locator, int seconds)
            throws InvalidLocatorTypeException, InvalidActionException {
        // TODO - this might fail if the element is no longer present
        String action = "Wait up to " + seconds + " seconds for " + type + " " + locator + " to not be enabled";
        String expected = type + " " + locator + " is not enabled";
        double start = System.currentTimeMillis();
        WebElement element = getWebElement(type, locator);
        if (element.isEnabled()) {
            // wait for up to XX seconds
            long end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (!element.isEnabled()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (element.isDisplayed()) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds for " + type + " " + locator + " is still enabled",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected,
                "Waited " + timetook + " seconds for " + type + " " + locator + " to not be enabled", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for checking if an element is present
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is present or not
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public boolean isElementPresent(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        return isElementPresent(type, locator, false);
    }

    /**
     * a method for checking if an element is present
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param print   : whether or not to printout the action
     * @return boolean: whether the element is present or not
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public boolean isElementPresent(Locators type, String locator, boolean print)
            throws InvalidLocatorTypeException, InvalidActionException {
        boolean isPresent = false;
        try {
            getWebElement(type, locator).getText();
            isPresent = true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.error(e);
        }
        if (print) {
            output.recordExpected("Checking for " + type + " " + locator + " to be present");
        }
        return isPresent;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is an input or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementInput(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return isElementInput(type, locator, false);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param print   : whether or not to printout the action
     * @return boolean: whether the element is present or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementInput(Locators type, String locator, boolean print)
            throws InvalidActionException, InvalidLocatorTypeException {
        boolean isInput = false;
        try {
            WebElement element = getWebElement(type, locator);
            if (element.getTagName().equalsIgnoreCase("input") || element.getTagName().equalsIgnoreCase("textarea")
                    || element.getTagName().equalsIgnoreCase("select")) {
                isInput = true;
            }
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            output.recordExpected("Checking for " + type + " " + locator + " to be an input element");
        }
        return isInput;
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is present or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementEnabled(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return isElementEnabled(type, locator, false);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param print   : whether or not to printout the action
     * @return boolean: whether the element is present or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementEnabled(Locators type, String locator, boolean print)
            throws InvalidActionException, InvalidLocatorTypeException {
        boolean isEnabled = false;
        try {
            isEnabled = getWebElement(type, locator).isEnabled();
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            output.recordExpected("Checking for " + type + " " + locator + " to be enabled");
        }
        return isEnabled;
    }

    /**
     * a method for checking if an element is checked
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is checked or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementChecked(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return isElementChecked(type, locator, false);
    }

    /**
     * a method for checking if an element is checked
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param print   : whether or not to printout the action
     * @return boolean: whether the element is checked or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementChecked(Locators type, String locator, boolean print)
            throws InvalidActionException, InvalidLocatorTypeException {
        boolean isChecked = false;
        try {
            isChecked = getWebElement(type, locator).isSelected();
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            output.recordExpected("Checking for " + type + " " + locator + " to be checked");
        }
        return isChecked;
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return boolean: whether the element is displayed or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementDisplayed(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        return isElementDisplayed(type, locator, false);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param print   : whether or not to printout the action
     * @return boolean: whether the element is displayed or not
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public boolean isElementDisplayed(Locators type, String locator, boolean print)
            throws InvalidActionException, InvalidLocatorTypeException {
        boolean isDisplayed = false;
        try {
            isDisplayed = getWebElement(type, locator).isDisplayed();
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            output.recordExpected("Checking for " + type + " " + locator + " to be displayed");
        }
        return isDisplayed;
    }

    /**
     * get the number of options from the select drop down
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of select options
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int getNumOfSelectOptions(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return 0;
        }
        WebElement element = getWebElement(type, locator);
        List<WebElement> allOptions = element.findElements(By.tagName("option"));
        return allOptions.size();
    }

    /**
     * get the options from the select drop down
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public String[] getSelectOptions(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return new String[0];
        }
        WebElement element = getWebElement(type, locator);
        List<WebElement> allOptions = element.findElements(By.tagName("option"));
        String[] options = new String[allOptions.size()];
        for (int i = 0; i < allOptions.size(); i++) {
            options[i] = allOptions.get(i).getAttribute("value");
        }
        return options;
    }

    // ///////////////////////////////////
    // selenium retreval functions
    // ///////////////////////////////////

    /**
     * get the rows of a table
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return List: a list of the table rows as WebElements
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public List<WebElement> getTableRows(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return new ArrayList<WebElement>();
        }
        WebElement element = getWebElement(type, locator);
        // TODO - this locator may need to be updated
        return element.findElements(By.tagName("tr"));
    }

    /**
     * get the number of rows of a table
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of table rows
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int getNumOfTableRows(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        List<WebElement> rows = getTableRows(type, locator);
        return rows.size();
    }

    /**
     * get the columns of a table
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return List: a list of the table columns as WebElements
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public List<WebElement> getTableColumns(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return new ArrayList<WebElement>();
        }
        WebElement element = getWebElement(type, locator);
        // TODO - this locator may need to be updated
        return element.findElements(By.xpath(".//tr[1]/th"));
    }

    /**
     * get the number of columns of a table
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of table columns
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int getNumOfTableColumns(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        List<WebElement> columns = getTableColumns(type, locator);
        return columns.size();
    }

    /**
     * a method to retrieve the row number in a table that has a header (th) of
     * the indicated value
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param header  : the full text value expected in a th cell
     * @return Integer: the row number containing the header
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int getTableRowWHeader(Locators type, String locator, String header)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return 0; // indicates table not found
        }
        List<WebElement> tables = getWebElements(type, locator);
        for (WebElement table : tables) {
            // TODO - might want to redo this logical check
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            int counter = 1;
            for (WebElement row : rows) {
                // TODO - might want to redo this logical check
                if (row.findElement(By.xpath(".//td[1]|.//th[1]")).getText().equals(header)) {
                    return counter;
                }
                counter++;
            }
        }
        return 0; // indicates header not found
    }

    /**
     * get a specific column from a table
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param colNum  : the column number of the table to obtain - note, column
     *                numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public List<WebElement> getTableColumn(Locators type, String locator, int colNum)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return new ArrayList<WebElement>(); // indicates table not found
        }
        List<WebElement> tables = getWebElements(type, locator);
        List<WebElement> column = tables.get(0).findElements(By.className("NONEEXISTS")); // cludge
        // to
        // initialize
        for (WebElement table : tables) {
            // TODO - this locator may need to be updated
            List<WebElement> cells = table.findElements(By.xpath(".//th[" + colNum + "]|.//td[" + colNum + "]"));
            column.addAll(cells);
        }
        return column;
    }

    /**
     * get the contents of a specific cell
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param row     : the number of the row in the table - note, row numbering
     *                starts at 1, NOT 0
     * @param col     : the number of the column in the table - note, column
     *                numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     * with it
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public WebElement getTableCell(Locators type, String locator, int row, int col)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return null; // indicates table not found
        }
        List<WebElement> tables = getWebElements(type, locator);
        for (WebElement table : tables) {
            // TODO - this locator may need to be updated
            return table.findElement(By.xpath(".//tr[" + row + "]/td[" + col + "]"));
        }
        return null; // indicates cell not present
    }

    /**
     * our generic selenium click functionality implemented
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int click(Locators type, String locator) throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Clicking " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to be clicked";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to click " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected, "Unable to click " + type + " " + locator + " as it is not displayed",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected, "Unable to click " + type + " " + locator + " as it is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        WebElement element = getWebElement(type, locator);
        // element.click();
        Actions selAction = new Actions(driver);
        selAction.click(element).perform();
        output.recordAction(action, expected, "Clicked " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    /**
     * our generic selenium submit functionality implemented
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int submit(Locators type, String locator) throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Submitting " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to be submitted	";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to submit " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to submit " + type + " " + locator + " as it is not displayed", Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected, "Unable to submit " + type + " " + locator + " as it is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        WebElement element = getWebElement(type, locator);
        element.submit();
        output.recordAction(action, expected, "Submitted " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    // ///////////////////////////////////
    // selenium actions functionality
    // ///////////////////////////////////

    /**
     * a method to simulate the mouse hovering over an element
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int hover(Locators type, String locator) throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Hovering over " + type + " " + locator;
        String expected = type + " " + locator + " is present, and displayed to be hovered over";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to hover over " + type + " " + locator + " as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to hover over " + type + " " + locator + " as it is not displayed", Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        Actions selAction = new Actions(driver);
        WebElement element = getWebElement(type, locator);
        selAction.moveToElement(element).perform();
        output.recordAction(action, expected, "Hovered over " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    /**
     * a custom selenium functionality to apply a blur to an element
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int blur(Locators type, String locator) throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Focusing, then unfocusing (blurring) on " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to be blurred";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to focus on " + type + " " + locator + " as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to focus on " + type + " " + locator + " as it is not displayed", Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to focus on " + type + " " + locator + " as it is not enabled", Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        WebElement element = getWebElement(type, locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus(); arguments[0].blur(); return true", element);
        // element.click();
        output.recordAction(action, expected, "Focused, then unfocused (blurred) on " + type + " " + locator,
                Result.SUCCESS);
        return 0;
    }

    /**
     * our generic selenium type functionality implemented
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param text    : the text to be typed in
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int type(Locators type, String locator, String text)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Typing text '" + text + "' in " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to have text " + text
                + " typed in";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to type in " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to type in " + type + " " + locator + " as it is not displayed", Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected, "Unable to type in " + type + " " + locator + " as it is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        WebElement element = getWebElement(type, locator);
        element.sendKeys(text);
        output.recordAction(action, expected, "Typed text '" + text + "' in " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    /**
     * our generic selenium type functionality implemented for specific keys
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param key     : the key to be pressed
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int type(Locators type, String locator, Keys key)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Typing text '" + key + "' in " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to have text " + key
                + " typed in";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to type in " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to type in " + type + " " + locator + " as it is not displayed", Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected, "Unable to type in " + type + " " + locator + " as it is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        WebElement element = getWebElement(type, locator);
        element.sendKeys(key);
        output.recordAction(action, expected, "Typed text '" + key + "' in " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    /**
     * our generic selenium clear functionality implemented
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int clear(Locators type, String locator) throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Clearing text in " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to have text cleared";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to clear " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected, "Unable to clear " + type + " " + locator + " as it is not displayed",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected, "Unable to clear " + type + " " + locator + " as it is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        WebElement element = getWebElement(type, locator);
        element.clear();
        output.recordAction(action, expected, "Cleared text in " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    /**
     * our generic select selenium functionality
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param value   : the select option to be selected - note, row numbering
     *                starts at 0
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int select(Locators type, String locator, int value)
            throws InvalidActionException, InvalidLocatorTypeException {
        String[] options = getSelectOptions(type, locator);
        return select(type, locator, options[value]);
    }

    /**
     * our generic select selenium functionality
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param value   : the select option to be selected
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException , InvalidLocatorTypeException
     */
    public int select(Locators type, String locator, String value)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Selecting " + value + " in " + type + " " + locator;
        String expected = type + " " + locator + " is present, displayed, and enabled to have the value " + value
                + " selected";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to select " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        // wait for element to be displayed
        if (!isElementDisplayed(type, locator, false)) {
            waitForElementDisplayed(type, locator);
        }
        if (!isElementDisplayed(type, locator, false)) {
            output.recordAction(action, expected,
                    "Unable to select " + type + " " + locator + " as it is not displayed", Result.FAILURE);
            output.addError();
            return 1; // indicates element not displayed
        }
        // wait for element to be enabled
        if (!isElementEnabled(type, locator, false)) {
            waitForElementEnabled(type, locator);
        }
        if (!isElementEnabled(type, locator, false)) {
            output.recordAction(action, expected, "Unable to select " + type + " " + locator + " as it is not enabled",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not enabled
        }
        // ensure the option exists
        if (!Arrays.asList(getSelectOptions(type, locator)).contains(value)) {
            output.recordAction(action, expected,
                    "Unable to select " + value + " in " + type + " " + locator
                            + " as that option isn't present. Available options are:<i><br/>" + "&nbsp;&nbsp;&nbsp;"
                            + String.join("<br/>&nbsp;&nbsp;&nbsp;", getSelectOptions(type, locator)) + "</i>",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        // do the select
        WebElement element = getWebElement(type, locator);
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
        output.recordAction(action, expected, "Selected " + value + " in " + type + " " + locator, Result.SUCCESS);
        return 0;
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int move(Locators type, String locator) throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Moving screen to " + type + " " + locator;
        String expected = type + " " + locator + " is now present on the visible page";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to move to " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        WebElement element = getWebElement(type, locator);
        Actions builder = new Actions(driver);
        builder.moveToElement(element);

        if (!isElementDisplayed(type, locator)) {
            output.recordAction(action, expected, type + " " + locator + " is not present on visible page",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not visible
        }
        output.recordAction(action, expected, type + " " + locator + " is present on visible page", Result.SUCCESS);
        return 0; // indicates element successfully moved to
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param type     - the locator type e.g. Locators.id, Locators.xpath
     * @param locator  - the locator string e.g. login, //input[@id='login']
     * @param position - how many pixels above the element to scroll to
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int move(Locators type, String locator, int position)
            throws InvalidActionException, InvalidLocatorTypeException {
        String action = "Moving screen to " + position + " pixels above " + type + " " + locator;
        String expected = type + " " + locator + " is now present on the visible page";
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            output.recordAction(action, expected, "Unable to move to " + type + " " + locator + " as it is not present",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement element = getWebElement(type, locator);
        int elementPosition = element.getLocation().getY();
        int newPosition = elementPosition - position;
        jse.executeScript("window.scrollBy(0, " + newPosition + ")");

        if (!isElementDisplayed(type, locator)) {
            output.recordAction(action, expected, type + " " + locator + " is not present on visible page",
                    Result.FAILURE);
            output.addError();
            return 1; // indicates element not visible
        }
        output.recordAction(action, expected, type + " " + locator + " is present on visible page", Result.SUCCESS);
        return 0; // indicates element successfully moved to
    }

    /**
     * An custom script to scroll to a given position on the page
     *
     * @param position - the position on the page to scroll to
     * @return Integer - the number of errors encountered while executing these
     * steps
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int scroll(int position) throws InvalidActionException, InvalidLocatorTypeException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Long initialPosition = (Long) jse.executeScript("return window.scrollY;");

        String action = "Scrolling page from " + initialPosition + " to " + position;
        String expected = "Page is now set at position " + position;

        jse.executeScript("window.scrollBy(0, " + position + ")");

        Long newPosition = (Long) jse.executeScript("return window.scrollY;");

        output.recordAction(action, expected, "Page is now set at position " + newPosition, Result.FAILURE);
        if (newPosition != position) {
            output.addError();
            return 1; // indicates page didn't scroll properly
        }
        return 0; // indicates page didn't scroll properly
    }

    /**
     * Some basic functionality for clicking 'OK' on an alert box
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int acceptAlert() {
        String action = "Clicking 'OK' on an alert";
        String expected = "Alert is present to be clicked";
        // wait for element to be present
        if (!isAlertPresent(false)) {
            waitForAlertPresent();
        }
        if (!isAlertPresent(false)) {
            output.recordAction(action, expected, "Unable to click alert as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        Alert alert = driver.switchTo().alert();
        alert.accept();
        // driver.switchTo().defaultContent();
        output.recordAction(action, expected, "Clicked 'OK' on the alert", Result.SUCCESS);
        return 0;
    }

    /**
     * Some basic functionality for clicking 'OK' on a confirmation box
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int acceptConfirmation() {
        String action = "Clicking 'OK' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        // wait for element to be present
        if (!isConfirmationPresent(false)) {
            waitForConfirmationPresent();
        }
        if (!isConfirmationPresent(false)) {
            output.recordAction(action, expected, "Unable to click confirmation as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        Alert alert = driver.switchTo().alert();
        alert.accept();
        // driver.switchTo().defaultContent();
        output.recordAction(action, expected, "Clicked 'OK' on the confirmation", Result.SUCCESS);
        return 0;
    }

    /**
     * Some basic functionality for clicking 'Cancel' on a confirmation box
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int dismissConfirmation() {
        String action = "Clicking 'Cancel' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        // wait for element to be present
        if (!isConfirmationPresent(false)) {
            waitForConfirmationPresent();
        }
        if (!isConfirmationPresent(false)) {
            output.recordAction(action, expected, "Unable to click confirmation as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        // driver.switchTo().defaultContent();
        output.recordAction(action, expected, "Clicked 'Cancel' on the confirmation", Result.SUCCESS);
        return 0;
    }

    /**
     * Some basic functionality for clicking 'OK' on a prompt box
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int acceptPrompt() {
        String action = "Clicking 'OK' on a prompt";
        String expected = "Prompt is present to be clicked";
        // wait for element to be present
        if (!isPromptPresent(false)) {
            waitForPromptPresent();
        }
        if (!isPromptPresent(false)) {
            output.recordAction(action, expected, "Unable to click prompt as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        Alert alert = driver.switchTo().alert();
        alert.accept();
        // driver.switchTo().defaultContent();
        output.recordAction(action, expected, "Clicked 'OK' on the prompt", Result.SUCCESS);
        return 0;
    }

    /**
     * Some basic functionality for clicking 'Cancel' on a prompt box
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int dismissPrompt() {
        String action = "Clicking 'Cancel' on a prompt";
        String expected = "Prompt is present to be clicked";
        // wait for element to be present
        if (!isPromptPresent(false)) {
            waitForPromptPresent();
        }
        if (!isPromptPresent(false)) {
            output.recordAction(action, expected, "Unable to click prompt as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        // driver.switchTo().defaultContent();
        output.recordAction(action, expected, "Clicked 'Cancel' on the prompt", Result.SUCCESS);
        return 0;
    }

    /**
     * Some basic functionality for typing text into a prompt box
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int typeIntoPrompt(String text) {
        String action = "Typing text '" + text + "' into prompt";
        String expected = "Prompt is present and enabled to have text " + text + " typed in";
        // wait for element to be present
        if (!isPromptPresent(false)) {
            waitForPromptPresent();
        }
        if (!isPromptPresent(false)) {
            output.recordAction(action, expected, "Unable to type in prompt as it is not present", Result.FAILURE);
            output.addError();
            return 1; // indicates element not present
        }
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
        // driver.switchTo().defaultContent();
        output.recordAction(action, expected, "Typed text '" + text + "' into prompt", Result.SUCCESS);
        return 0;
    }

    /**
     * a method to determine selenium's By object using selenium webdriver
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return By: the selenium object
     * @throws InvalidLocatorTypeException
     */
    private By defineByElement(Locators type, String locator) throws InvalidLocatorTypeException {
        // TODO - consider adding strengthening
        By byElement;
        switch (type) { // determine which locator type we are interested in
            case xpath: {
                byElement = By.xpath(locator);
                break;
            }
            case id: {
                byElement = By.id(locator);
                break;
            }
            case name: {
                byElement = By.name(locator);
                break;
            }
            case classname: {
                byElement = By.className(locator);
                break;
            }
            case linktext: {
                byElement = By.linkText(locator);
                break;
            }
            case paritallinktext: {
                byElement = By.partialLinkText(locator);
                break;
            }
            case tagname: {
                byElement = By.tagName(locator);
                break;
            }
            default: {
                throw new InvalidLocatorTypeException(type + " is not a valid locator type");
            }
        }
        return byElement;
    }

    /**
     * a method to grab the first matching web element using selenium webdriver
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return WebElement: the element object, and all associated values with it
     * @throws InvalidLocatorTypeException
     */
    public WebElement getWebElement(Locators type, String locator) throws InvalidLocatorTypeException {
        By byElement = defineByElement(type, locator);
        WebElement query = driver.findElement(byElement); // grab our element
        // based on the
        // locator
        return query; // return our query
    }

    // //////////////////////////////////
    // extra base selenium functionality
    // //////////////////////////////////

    /**
     * a method to grab all matching web elements using selenium webdriver
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return List<WebElement>: a list of element objects, and all associated
     * values with them
     * @throws InvalidLocatorTypeException
     */
    private List<WebElement> getWebElements(Locators type, String locator) throws InvalidLocatorTypeException {
        By byElement = defineByElement(type, locator);
        List<WebElement> query = driver.findElements(byElement); // grab our
        // element
        // based on
        // the
        // locator
        return query; // return our query
    }

    /**
     * a method to obtain screenshots
     *
     * @param imageName : the name of the image typically generated via functions from
     *                  TestOutput.generateImageName
     * @throws InvalidActionException
     */
    public void takeScreenshot(String imageName) throws InvalidActionException {
        if (browser == Browsers.HtmlUnit) {
            return;
        }
        try {
            // take a screenshot
            File srcFile = new File("");
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
     * @param print - whether or not to print out this wait statement
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
            output.recordExpected("Checking for alert to be present");
        }
        // driver.switchTo().defaultContent();
        return isPresent;
    }

    // /////////////////////////////////////////////
    // a set of selenium checking functionality, used for building logic
    // ///////////////////////////////////////////

    /**
     * a method for waiting up to 5 seconds for an alert is present
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int waitForAlertPresent() {
        return waitForAlertPresent(5);
    }

    /**
     * a method for waiting until an alert is present
     *
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int waitForAlertPresent(long seconds) {
        String action = "Wait up to " + seconds + " seconds for an alert to be present";
        String expected = "An alert is present";
        // wait for up to XX seconds for our error message
        long end = System.currentTimeMillis() + (seconds * 1000);
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
        timetook = timetook / 1000;
        // driver.switchTo().defaultContent();
        if (!isAlertPresent(false)) {
            output.recordAction(action, expected, "After waiting " + timetook + " seconds, an alert is not present",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected, "Waited " + timetook + " seconds for an alert to be present",
                Result.SUCCESS);
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
            return "";
        }
        Alert alert = driver.switchTo().alert();
        // driver.switchTo().defaultContent();
        return alert.getText();
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
     * @param print - whether or not to print out this wait statement
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
            output.recordExpected("Checking for confirmation to be present");
        }
        // driver.switchTo().defaultContent();
        return isPresent;
    }

    /**
     * a method for waiting up to 5 seconds for a confirmation is present
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int waitForConfirmationPresent() {
        return waitForConfirmationPresent(5);
    }

    /**
     * a method for waiting until a confirmation is present
     *
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int waitForConfirmationPresent(long seconds) {
        String action = "Wait up to " + seconds + " seconds for a confirmation to be present";
        String expected = "An alert is present";
        // wait for up to XX seconds for our error message
        long end = System.currentTimeMillis() + (seconds * 1000);
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
        timetook = timetook / 1000;
        // driver.switchTo().defaultContent();
        if (!isConfirmationPresent(false)) {
            output.recordAction(action, expected,
                    "After waiting " + timetook + " seconds, a confirmation is not present", Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected, "Waited " + timetook + " seconds for a confirmation to be present",
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
            return "";
        }
        Alert alert = driver.switchTo().alert();
        // driver.switchTo().defaultContent();
        return alert.getText();
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
     * @param print - whether or not to print out this wait statement
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
            output.recordExpected("Checking for prompt to be present");
        }
        // driver.switchTo().defaultContent();
        return isPresent;
    }

    /**
     * a method for waiting up to 5 seconds for a prompt is present
     *
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int waitForPromptPresent() {
        return waitForPromptPresent(5);
    }

    /**
     * a method for waiting until a prompt is present
     *
     * @param seconds : the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     * steps
     */
    public int waitForPromptPresent(long seconds) {
        String action = "Wait up to " + seconds + " seconds for a prompt to be present";
        String expected = "An alert is present";
        // wait for up to XX seconds for our error message
        long end = System.currentTimeMillis() + (seconds * 1000);
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
        timetook = timetook / 1000;
        // driver.switchTo().defaultContent();
        if (!isPromptPresent(false)) {
            output.recordAction(action, expected, "After waiting " + timetook + " seconds, a prompt is not present",
                    Result.FAILURE);
            output.addError();
            return 1;
        }
        output.recordAction(action, expected, "Waited " + timetook + " seconds for a prompt to be present",
                Result.SUCCESS);
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
            return "";
        }
        Alert alert = driver.switchTo().alert();
        // driver.switchTo().defaultContent();
        return alert.getText();
    }

    /**
     * a method to determine if a cookie by a particular name is present or not
     *
     * @param expectedCookieName - the name of the cookie
     * @return boolean - if the cookie is present
     */
    public boolean isCookiePresent(String expectedCookieName) {
        boolean isCookiePresent = false;
        if (driver.manage().getCookieNamed(expectedCookieName) != null) {
            isCookiePresent = true;
        }
        return isCookiePresent;
    }

    /**
     * a method to get the full cookie by a particular name
     *
     * @param expectedCookieName - the name of the cookie
     * @return Cookie - the cookie
     */
    public Cookie getCookie(String expectedCookieName) {
        return driver.manage().getCookieNamed(expectedCookieName);
    }

    /**
     * a method to get the value of a particular cookie
     *
     * @param expectedCookieName - the name of the cookie
     * @return String - the value of the cookie
     */
    public String getCookieValue(String expectedCookieName) {
        return getCookie(expectedCookieName).getValue();
    }

    /**
     * a method to get the path of a particular cookie
     *
     * @param expectedCookieName - the name of the cookie
     * @return String - the path of the cookie
     */
    public String getCookiePath(String expectedCookieName) {
        return getCookie(expectedCookieName).getPath();
    }

    /**
     * a method to get the domain of a particular cookie
     *
     * @param expectedCookieName - the name of the cookie
     * @return String - the domain of the cookie
     */
    public String getCookieDomain(String expectedCookieName) {
        return getCookie(expectedCookieName).getDomain();
    }

    /**
     * a method to get the expriation of a particular cookie
     *
     * @param expectedCookieName - the name of the cookie
     * @return String - the expiration of the cookie
     */
    public Date getCookieExpiration(String expectedCookieName) {
        return getCookie(expectedCookieName).getExpiry();
    }

    public boolean isSomethingSelected(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        WebElement element = getWebElement(type, locator);
        if (element.getTagName().equalsIgnoreCase("input") && element.isSelected()) {
            return true;
        }
        if (element.getTagName().equalsIgnoreCase("select") && getSelectedValues(type, locator).length > 0) {
            return true;
        }
        return false;
    }

    /**
     * get the options from the select drop down
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException      , InvalidLocatorTypeException
     */
    public String getSelectedText(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return "";
        }
        WebElement element = getWebElement(type, locator);
        Select dropdown = new Select(element);
        WebElement option = dropdown.getFirstSelectedOption();
        return option.getText();
    }

    // public boolean isOrdered(String firstObject, String secondObject) {
    // Auto-generated method stub
    // return false;
    // }

    public String[] getSelectedTexts(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return new String[0];
        }
        WebElement element = getWebElement(type, locator);
        Select dropdown = new Select(element);
        List<WebElement> options = dropdown.getAllSelectedOptions();
        String[] s_options = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            s_options[i] = options.get(i).getText();
        }
        return s_options;
    }

    /**
     * get the options from the select drop down
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException      , InvalidLocatorTypeException
     */
    public String getSelectedValue(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return "";
        }
        WebElement element = getWebElement(type, locator);
        Select dropdown = new Select(element);
        WebElement option = dropdown.getFirstSelectedOption();
        return option.getAttribute("value");
    }

    public String[] getSelectedValues(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for element to be present
        if (!isElementPresent(type, locator, false)) {
            waitForElementPresent(type, locator);
        }
        if (!isElementPresent(type, locator, false)) {
            return new String[0];
        }
        WebElement element = getWebElement(type, locator);
        Select dropdown = new Select(element);
        List<WebElement> options = dropdown.getAllSelectedOptions();
        String[] s_options = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            s_options[i] = options.get(i).getAttribute("value");
        }
        return s_options;
    }

    /**
     * a specialized selenium is text present in the page source functionality
     *
     * @param expectedText - the text we are expecting to be present on the page
     * @return boolean - whether or not the text is present
     */
    public boolean isTextPresentInSource(String expectedText) {
        return driver.getPageSource().contains(expectedText);
    }

    /**
     * our generic selenium is text present functionality implemented
     *
     * @param expectedText - the text we are expecting to be present on the page
     * @return boolean - whether or not the text is present
     */
    public boolean isTextPresent(String expectedText) {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        return bodyText.contains(expectedText);
    }

    /**
     * our generic selenium get text from an element functionality implemented
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return String - the text of the element
     */
    public String getText(Locators type, String locator) throws InvalidLocatorTypeException {
        WebElement element = getWebElement(type, locator);
        return element.getText();
    }

    /**
     * our generic selenium get value from an element functionality implemented
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return String - the text of the element
     */
    public String getValue(Locators type, String locator) throws InvalidLocatorTypeException {
        WebElement element = getWebElement(type, locator);
        return element.getAttribute("value");
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param type      - the locator type e.g. Locators.id, Locators.xpath
     * @param locator   - the locator string e.g. login, //input[@id='login']
     * @param attribute - the css attribute to be returned
     * @return String - the value of the css attribute
     * @throws InvalidLocatorTypeException
     */
    public String getCss(Locators type, String locator, String attribute) throws InvalidLocatorTypeException {
        WebElement element = getWebElement(type, locator);
        return element.getCssValue(attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param type      - the locator type e.g. Locators.id, Locators.xpath
     * @param locator   - the locator string e.g. login, //input[@id='login']
     * @param attribute - the css attribute to be returned
     * @return String - the value of the css attribute
     * @throws InvalidLocatorTypeException
     */
    public String getAttribute(Locators type, String locator, String attribute) throws InvalidLocatorTypeException {
        WebElement element = getWebElement(type, locator);
        return element.getAttribute(attribute);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return String - the value of the css attribute
     * @throws InvalidLocatorTypeException
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getAllAttributes(Locators type, String locator) throws InvalidLocatorTypeException {
        WebElement element = getWebElement(type, locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Map<String, String>) js.executeScript(
                "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                element);
    }

    /**
     * An extension of the selenium functionality to retrieve the current url of
     * the application
     *
     * @return String - current url
     */
    public String getLocation() {
        return driver.getCurrentUrl();
    }

    /**
     * An extension of the selenium functionality to retrieve the current title
     * of the application
     *
     * @return String - title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * An extension of the selenium functionality to retrieve the html source of
     * the application
     *
     * @return String - page source
     */
    public String getHtmlSource() {
        return driver.getPageSource();
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param javascriptFunction
     */
    public void getEval(String javascriptFunction) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(javascriptFunction);
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param javascriptFunction
     * @throws InvalidLocatorTypeException
     */
    public void getEval(Locators type, String locator, String javascriptFunction) throws InvalidLocatorTypeException {
        WebElement element = getWebElement(type, locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(javascriptFunction, element);
    }

    /**
     * Select a Locator for the element we are interacting with Available
     * options are: xpath, id, name, classname, paritallinktext, linktext,
     * tagname
     */
    public enum Locators {
        xpath, id, name, classname, paritallinktext, linktext, tagname
    }

    /**
     * Select a browser to run Available options are: HtmlUnit (only locally -
     * not on grid), Firefox, Chrome, InternetExplorer, Android, Ipad (only
     * locally - not on grid), Iphone (only locally, not on grid, Opera, Safari
     */
    public enum Browsers {
        None, HtmlUnit, Firefox, Marionette, Chrome, InternetExplorer, Edge, Android, Ipad, Iphone, Opera, Safari, PhantomJS
    }
}