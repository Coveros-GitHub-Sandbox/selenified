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

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.output.Assert.Result;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.tools.General;
import com.coveros.selenified.tools.TestSetup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

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

    // the is class to determine if something exists
    private Is is;
    // the wait class to determine if we need to wait for something
    private WaitFor waitFor;
    // the wait class to retrieve information about the element
    private Get get;
    // the page class to perform general actions on the page
    private Page page;
    // the call class to make services calls
    private Call call;

    // our constants
    private static final String SECONDS = " seconds";
    private static final String WAITED = "Waited ";

    private static final String FRAME = "Frame <b>";
    private static final String AVAILABLE = "</b> is available and selected";
    private static final String NOTSELECTED = "</b> was unable to be selected";

    private static final String IN = "' in ";

    private static final String TYPTED = "Typed text '";

    private static final String NOTPRESENT = " as it is not present";
    private static final String NOTDISPLAYED = " as it is not displayed";
    private static final String NOTENABLED = " as it is not enabled";
    private static final String NOTINPUT = " as it is not an input";

    private static final String CANTTYPE = "Unable to type in ";
    private static final String CANTMOVE = "Unable to move to ";

    private static final String SELECTING = "Selecting ";
    private static final String SELECTED = " selected";
    private static final String PRESDISEN = " is present, displayed, and enabled to have the value ";

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

        is = new Is(driver, file);
        waitFor = new WaitFor(driver, file);
        get = new Get(driver, file);
        page = new Page(driver, browser, file);
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
        this.file = file;
        HTTP http = new HTTP(serviceBaseUrl, username, password);
        call = new Call(http, file);
    }

    ///////////////////////////////////////////////////////
    // instantiating our additional action classes for further use
    ///////////////////////////////////////////////////////

    public Is is() {
        return is;
    }

    public WaitFor waitFor() {
        return waitFor;
    }

    public Get get() {
        return get;
    }

    public Page page() {
        return page;
    }

    public Call call() {
        return call;
    }

    ///////////////////////////////////////////////////////
    // some nicely wrapped selenium actions
    ///////////////////////////////////////////////////////

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

    //////////////////////////////////
    // override the SE actions
    //////////////////////////////////

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
        return click(new Element(type, locator));
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
        return click(new Element(type, locator, elementMatch));
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
        return submit(new Element(type, locator));
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
        return submit(new Element(type, locator, elementMatch));
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
        return hover(new Element(type, locator));
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
        return hover(new Element(type, locator, elementMatch));
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
        return blur(new Element(type, locator));
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
        return blur(new Element(type, locator, elementMatch));
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
        return type(new Element(type, locator), text);
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
        return type(new Element(type, locator, elementMatch), text);
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
        return type(new Element(type, locator), key);
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
        return type(new Element(type, locator, elementMatch), key);
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
        return clear(new Element(type, locator));
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
        return clear(new Element(type, locator, elementMatch));
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
        return select(new Element(type, locator), value);
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
        return select(new Element(type, locator, elementMatch), value);
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
    public int select(Locator type, String locator, int value) {
        return select(new Element(type, locator), value);
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
    public int select(Locator type, String locator, int elementMatch, int value) {
        return select(new Element(type, locator, elementMatch), value);
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
        return move(new Element(type, locator));
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
        return move(new Element(type, locator, elementMatch));
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
        return move(new Element(type, locator), position);
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
        return move(new Element(type, locator, elementMatch), position);
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
        return selectFrame(new Element(type, locator));
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
        return selectFrame(new Element(type, locator, elementMatch));
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    //////////////////////////
    // pop-up interactions
    /////////////////////////

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
        if (!is.confirmationPresent()) {
            waitFor.confirmationPresent();
        }
        if (!is.confirmationPresent()) {
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
        if (!is.promptPresent()) {
            waitFor.promptPresent();
        }
        if (!is.promptPresent()) {
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
        if (!is.alertPresent()) {
            waitFor.alertPresent();
        }
        if (!is.alertPresent()) {
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

    /**
     * checks to see if the element is present. If it isn't, it'll wait for 5
     * seconds for the element to be present
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element present?
     */
    private boolean isPresent(Element element, String action, String expected, String extra) {
        // wait for element to be present
        if (!is.elementPresent(element)) {
            waitFor.elementPresent(element);
        }
        if (!is.elementPresent(element)) {
            file.recordAction(action, expected, extra + element.prettyOutput() + NOTPRESENT, Result.FAILURE);
            file.addError();
            // indicates element not present
            return false;
        }
        return true;
    }

    /**
     * checks to see if the element is displayed. If it isn't, it'll wait for 5
     * seconds for the element to be displayed
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element displayed?
     */
    private boolean isDisplayed(Element element, String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.elementDisplayed(element)) {
            waitFor.elementDisplayed(element);
        }
        if (!is.elementDisplayed(element)) {
            file.recordAction(action, expected, extra + element.prettyOutput() + NOTDISPLAYED, Result.FAILURE);
            file.addError();
            // indicates element not displayed
            return false;
        }
        return true;
    }

    /**
     * checks to see if the element is enabled. If it isn't, it'll wait for 5
     * seconds for the element to be enabled
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    private boolean isEnabled(Element element, String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.elementEnabled(element)) {
            waitFor.elementEnabled(element);
        }
        if (!is.elementEnabled(element)) {
            file.recordAction(action, expected, extra + element.prettyOutput() + NOTENABLED, Result.FAILURE);
            file.addError();
            // indicates element not enabled
            return false;
        }
        return true;
    }

    /**
     * checks to see if the element is an input.
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    private boolean isInput(Element element, String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.elementInput(element)) {
            file.recordAction(action, expected, extra + element.prettyOutput() + NOTINPUT, Result.FAILURE);
            file.addError();
            // indicates element not an input
            return false;
        }
        return true;
    }

    /**
     * does a check to see if something is present, displayed, and enabled. This
     * returns true if all three are true, otherwise, it returns false
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element present, displayed, and enabled?
     */
    private boolean isPresentDisplayedEnabled(Element element, String action, String expected, String extra) {
        // wait for element to be present
        if (!isPresent(element, action, expected, extra)) {
            return false;
        }
        // wait for element to be displayed
        if (!isDisplayed(element, action, expected, extra)) {
            return false;
        }
        // wait for element to be enabled
        return isEnabled(element, action, expected, extra);
    }

    /**
     * does a check to see if something is present, enabled, and an input. This
     * returns true if all three are true, otherwise, it returns false
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element present, enabled, and an input?
     */
    private boolean isPresentEnabledInput(Element element, String action, String expected, String extra) {
        // wait for element to be present
        if (!isPresent(element, action, expected, extra)) {
            return false;
        }
        // wait for element to be enabled
        if (!isEnabled(element, action, expected, extra)) {
            return false;
        }
        return isInput(element, action, expected, extra);
    }

    /**
     * does a check to see if something is present, displayed, enabled, and an
     * input. This returns true if all four are true, otherwise, it returns
     * false
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what action is occurring
     * @param expected
     *            - what is the expected result
     * @param extra
     *            - what actually is occurring
     * @return Boolean: is the element present, displayed, enabled, and an
     *         input?
     */
    private boolean isPresentDisplayedEnabledInput(Element element, String action, String expected, String extra) {
        // wait for element to be present
        if (!isPresent(element, action, expected, extra)) {
            return false;
        }
        // wait for element to be displayed
        if (!isDisplayed(element, action, expected, extra)) {
            return false;
        }
        // wait for element to be enabled
        if (!isEnabled(element, action, expected, extra)) {
            return false;
        }
        return isInput(element, action, expected, extra);
    }

    // ///////////////////////////////////
    // basic actions functionality
    // ///////////////////////////////////

    /**
     * the generic selenium click functionality implemented
     *
     * @param element
     *            - the element to be acted on
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int click(Element element) {
        String cantClick = "Unable to click ";
        String action = "Clicking " + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, displayed, and enabled to be clicked";
        if (!isPresentDisplayedEnabled(element, action, expected, cantClick)) {
            return 1;
        }
        try {
            WebElement webElement = element.getWebElement();
            Actions selAction = new Actions(driver);
            selAction.click(webElement).perform();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantClick + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Clicked " + element.prettyOutput(), Result.SUCCESS);
        return 0;
    }

    /**
     * the generic selenium submit functionality implemented
     *
     * @param element
     *            - the element to be acted on
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int submit(Element element) {
        String cantSubmit = "Unable to submit ";
        String action = "Submitting " + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, displayed, and enabled to be submitted    ";
        if (!isPresentDisplayedEnabled(element, action, expected, cantSubmit)) {
            return 1;
        }
        WebElement webElement = element.getWebElement();
        try {
            webElement.submit();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantSubmit + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Submitted " + element.prettyOutput(), Result.SUCCESS);
        return 0;
    }

    /**
     * a method to simulate the mouse hovering over an element
     *
     * @param element
     *            - the element to be acted on
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int hover(Element element) {
        String cantHover = "Unable to hover over ";
        String action = "Hovering over " + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, and displayed to be hovered over";
        // wait for element to be present
        if (!isPresent(element, action, expected, cantHover)) {
            return 1;
        }
        // wait for element to be displayed
        if (!isDisplayed(element, action, expected, cantHover)) {
            return 1;
        }
        try {
            Actions selAction = new Actions(driver);
            WebElement webElement = element.getWebElement();
            selAction.moveToElement(webElement).perform();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantHover + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Hovered over " + element.prettyOutput(), Result.SUCCESS);
        return 0;
    }

    /**
     * a custom selenium functionality to apply a blur to an element
     *
     * @param element
     *            - the element to be acted on
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int blur(Element element) {
        String cantFocus = "Unable to focus on ";
        String action = "Focusing, then unfocusing (blurring) on " + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, displayed, and enabled to be blurred";
        if (!isPresentDisplayedEnabledInput(element, action, expected, cantFocus)) {
            return 1;
        }
        try {
            WebElement webElement = element.getWebElement();
            webElement.sendKeys("\t");
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantFocus + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Focused, then unfocused (blurred) on " + element.prettyOutput(),
                Result.SUCCESS);
        return 0;
    }

    /**
     * type functionality implemented
     *
     * @param element
     *            - the element to be acted on
     * @param text
     *            - the text to be typed in
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int type(Element element, String text) {
        String action = "Typing text '" + text + IN + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, displayed, and enabled to have text " + text
                + " typed in";
        if (!isPresentEnabledInput(element, action, expected, CANTTYPE)) {
            return 1;
        }
        Boolean warning = false;
        if (!is.elementDisplayed(element)) {
            warning = true;
        }
        try {
            WebElement webElement = element.getWebElement();
            webElement.sendKeys(text);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, CANTTYPE + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        if (warning) {
            file.recordAction(action, expected, TYPTED + text + IN + element.prettyOutput()
                    + ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
        } else {
            file.recordAction(action, expected, TYPTED + text + IN + element.prettyOutput(), Result.SUCCESS);
        }
        return 0;
    }

    /**
     * the generic selenium type functionality implemented for specific keys
     *
     * @param element
     *            - the element to be acted on
     * @param key
     *            - the key to be pressed
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int type(Element element, Keys key) {
        String action = "Typing key '" + key + IN + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, displayed, and enabled to have text " + key
                + " entered";
        if (!isPresentEnabledInput(element, action, expected, CANTTYPE)) {
            return 1;
        }
        Boolean warning = false;
        if (!is.elementDisplayed(element)) {
            warning = true;
        }
        try {
            WebElement webElement = element.getWebElement();
            webElement.sendKeys(key);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, CANTTYPE + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        if (warning) {
            file.recordAction(action, expected, TYPTED + key + IN + element.prettyOutput()
                    + ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
        } else {
            file.recordAction(action, expected, TYPTED + key + IN + element.prettyOutput(), Result.SUCCESS);
        }
        return 0;
    }

    /**
     * the generic selenium clear functionality implemented
     *
     * @param element
     *            - the element to be acted on
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int clear(Element element) {
        String cantClear = "Unable to clear ";
        String action = "Clearing text in " + element.prettyOutput();
        String expected = element.prettyOutput() + " is present, displayed, and enabled to have text cleared";
        if (!isPresentDisplayedEnabledInput(element, action, expected, cantClear)) {
            return 1;
        }
        WebElement webElement = element.getWebElement();
        try {
            webElement.clear();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantClear + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Cleared text in " + element.prettyOutput(), Result.SUCCESS);
        return 0;
    }

    /**
     * the generic select selenium functionality
     *
     * @param element
     *            - the element to be acted on
     * @param value
     *            - the select option to be selected - note, row numbering
     *            starts at 0
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int select(Element element, int value) {
        String[] options = get.selectOptions(element);
        if (value > options.length) {
            String action = SELECTING + value + " in " + element.prettyOutput();
            String expected = element.prettyOutput() + PRESDISEN + value + SELECTED;
            file.recordAction(action, expected, "Unable to select the <i>" + value
                    + "</i> option, as there are only <i>" + options.length + "</i> available.", Result.FAILURE);
            file.addError();
            return 1;
        }
        return select(element, options[value]);
    }

    /**
     * the generic select selenium functionality
     *
     * @param element
     *            - the element to be acted on
     * @param value
     *            - the select option to be selected
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int select(Element element, String value) {
        String cantSelect = "Unable to select ";
        String action = SELECTING + value + " in " + element.prettyOutput();
        String expected = element.prettyOutput() + PRESDISEN + value + SELECTED;
        if (!isPresentDisplayedEnabledInput(element, action, expected, cantSelect)) {
            return 1;
        }
        // ensure the option exists
        if (!Arrays.asList(get.selectOptions(element)).contains(value)) {
            file.recordAction(action, expected,
                    cantSelect + value + " in " + element.prettyOutput()
                            + " as that option isn't present. Available options are:<i><br/>" + "&nbsp;&nbsp;&nbsp;"
                            + String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectOptions(element)) + "</i>",
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        // do the select
        try {
            WebElement webElement = element.getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantSelect + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Selected " + value + " in " + element.prettyOutput(), Result.SUCCESS);
        return 0;
    }

    /**
     * A private method to throw an error if moving to an element was
     * unsuccessful
     * 
     * @param element
     *            - the element to be acted on
     * @param e
     *            - the exception that was thrown
     * @param action
     *            - what is the action occurring
     * @param expected
     *            - what is the expected outcome of said action
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    private int cantMove(Element element, Exception e, String action, String expected) {
        log.error(e);
        file.recordAction(action, expected, CANTMOVE + element.prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
        file.addError();
        return 1;
    }

    /**
     * A private method to determine if the element moved towards is now
     * currently visible on the screen
     * 
     * @param element
     *            - the element to be acted on
     * @param action
     *            - what is the action occurring
     * @param expected
     *            - what is the expected outcome of said action
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    private int isMoved(Element element, String action, String expected) {
        if (!is.elementDisplayed(element)) {
            file.recordAction(action, expected, element.prettyOutput() + " is not present on visible page",
                    Result.FAILURE);
            file.addError();
            return 1; // indicates element not visible
        }
        file.recordAction(action, expected, element.prettyOutput() + " is present on visible page", Result.SUCCESS);
        return 0; // indicates element successfully moved to
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param element
     *            - the element to be acted on
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int move(Element element) {
        String action = "Moving screen to " + element.prettyOutput();
        String expected = element.prettyOutput() + " is now present on the visible page";
        // wait for element to be present
        if (!isPresent(element, action, expected, CANTMOVE)) {
            return 1;
        }
        try {
            WebElement webElement = element.getWebElement();
            Actions builder = new Actions(driver);
            builder.moveToElement(webElement);
        } catch (Exception e) {
            return cantMove(element, e, action, expected);
        }
        return isMoved(element, action, expected);
    }

    /**
     * An extension of the basic Selenium action of 'moveToElement' This will
     * scroll or move the page to ensure the element is visible
     *
     * @param element
     *            - the element to be acted on
     * @param position
     *            - how many pixels above the element to scroll to
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int move(Element element, long position) {
        String action = "Moving screen to " + position + " pixels above " + element.prettyOutput();
        String expected = element.prettyOutput() + " is now present on the visible page";
        // wait for element to be present
        if (!isPresent(element, action, expected, CANTMOVE)) {
            return 1;
        }

        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            WebElement webElement = element.getWebElement();
            long elementPosition = webElement.getLocation().getY();
            long newPosition = elementPosition - position;
            jse.executeScript("window.scrollBy(0, " + newPosition + ")");
        } catch (Exception e) {
            return cantMove(element, e, action, expected);
        }
        return isMoved(element, action, expected);
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
     * a function to switch to a frame using the element
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int selectFrame(Element element) {
        String cantSelect = "Unable to focus on frame ";
        String action = "Focusing on frame " + element.prettyOutput();
        String expected = "Frame " + element.prettyOutput() + " is present, displayed, and focused";
        // wait for element to be present
        if (!isPresent(element, action, expected, cantSelect)) {
            return 1;
        }
        // wait for element to be displayed
        if (!isDisplayed(element, action, expected, cantSelect)) {
            return 1;
        }
        WebElement webElement = element.getWebElement();
        try {
            driver.switchTo().frame(webElement);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, cantSelect + element.prettyOutput() + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, "Focused on frame " + element.prettyOutput(), Result.SUCCESS);
        return 0;
    }
}