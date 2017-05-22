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

package tools.output;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;
import selenified.exceptions.InvalidActionException;
import selenified.exceptions.InvalidBrowserException;
import selenified.exceptions.InvalidLocatorTypeException;
import tools.General;
import tools.TestSetup;
import tools.output.Assert.Result;
import tools.output.Selenium.Browsers;
import tools.output.Selenium.Locators;
import tools.output.Element;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Selenium Webdriver Before each action is performed a screenshot is taken
 * After each check is performed a screenshot is taken These are all placed into
 * the output file
 *
 * @author Max Saperstone
 * @version 2.0.0
 * @lastupdate 5/15/2017
 */
public class Action {

	private static final Logger log = Logger.getLogger(General.class);

	// this will be the name of the file we write all commands out to
	private OutputFile file;

	// what locator actions are available in webdriver
	// this is our driver that will be used for all selenium actions
	private WebDriver driver;

	// what browsers are we interested in implementing
	// this is the browser that we are using
	private Browsers browser;
	private DesiredCapabilities capabilities;

	private LocatorAction locatorAction;
	private String parentWindow;

	// constants
	private static final String SECONDS = " seconds";
	private static final String WAITED = "Waited ";
	private static final String UPTO = "Wait up to ";
	private static final String AFTER = "After waiting ";
	private static final long WAITFOR = 5;

	/**
	 * our constructor, determining which browser use and how to run the
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
	 * @throws MalformedURLException
	 */
	public Action(Browsers browser, DesiredCapabilities capabilities, OutputFile file)
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
	 * a method to allow retrieving our driver instance
	 *
	 * @return WebDriver: access to the driver controlling our browser via
	 *         webdriver
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
	 * @param url
	 *            - the URL to navigate to
	 */
	public int goToURL(String url) {
		String action = "Loading " + url;
		String expected = "Loaded " + url;
		double start = System.currentTimeMillis();
		try {
			driver.get(url);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, "Fail to Load " + url, Result.FAILURE);
			file.addError();
			return 1;
		}
		double timetook = System.currentTimeMillis() - start;
		timetook = timetook / 1000;
		file.recordAction(action, expected, "Loaded " + url + " in " + timetook + SECONDS, Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for allowing Selenium to pause for a set amount of time
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 */
	public int wait(int seconds) {
		return wait((double) seconds);
	}

	// ///////////////////////////////////////
	// generic navigational functionality
	// ///////////////////////////////////////

	/**
	 * a method for allowing Selenium to pause for a set amount of time
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 */
	public int wait(double seconds) {
		String action = "Wait " + seconds + SECONDS;
		String expected = WAITED + seconds + SECONDS;
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			log.error(e);
			file.recordAction(action, expected, "Failed to wait " + seconds + SECONDS, Result.FAILURE);
			file.addError();
			Thread.currentThread().interrupt();
			return 1;
		}
		file.recordAction(action, expected, WAITED + seconds + SECONDS, Result.SUCCESS);
		return 0;
	}

	// ///////////////////////////////////////
	// waiting functionality
	// ///////////////////////////////////////

	/**
	 * a method for waiting until an element is present for a maximum of 5
	 * seconds
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Element element) throws IOException {
		return waitForElementPresent(element.getType(), element.getLocator());
	}

	/**
	 * a method for waiting until an element is present
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Element element, long seconds) throws IOException {
		return waitForElementPresent(element.getType(), element.getLocator(), seconds);
	}

	/**
	 * a method for waiting until an element is present for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Locators type, String locator) throws IOException {
		return waitForElementPresent(type, locator, WAITFOR);
	}

	/**
	 * a method for waiting until an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Locators type, String locator, long seconds) throws IOException {
		return waitForElementPresent(type, locator, 0, seconds);
	}

	/**
	 * a method for waiting until an element is present for a maximum of 5
	 * seconds
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Element element, int elementMatch) throws IOException {
		return waitForElementPresent(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for waiting until an element is present
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Element element, int elementMatch, long seconds) throws IOException {
		return waitForElementPresent(element.getType(), element.getLocator(), elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is present for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Locators type, String locator, int elementMatch) throws IOException {
		return waitForElementPresent(type, locator, elementMatch, WAITFOR);
	}

	/**
	 * a method for waiting until an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Locators type, String locator, int elementMatch, long seconds) throws IOException {
		return locatorAction.waitForElementPresent(type, locator, elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is no longer present for a maximum
	 * of 5 seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Element element) throws IOException {
		return waitForElementNotPresent(element.getType(), element.getLocator());
	}

	/**
	 * a method for waiting until an element is no longer present
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Element element, long seconds) throws IOException {
		return waitForElementNotPresent(element.getType(), element.getLocator(), seconds);
	}

	/**
	 * a method for waiting until an element is no longer present for a maximum
	 * of 5 seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Locators type, String locator) throws IOException {
		return waitForElementNotPresent(type, locator, WAITFOR);
	}

	/**
	 * a method for waiting until an element is no longer present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Locators type, String locator, long seconds) throws IOException {
		return waitForElementNotPresent(type, locator, 0, seconds);
	}

	/**
	 * a method for waiting until an element is no longer present for a maximum
	 * of 5 seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Element element, int elementMatch) throws IOException {
		return waitForElementNotPresent(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for waiting until an element is no longer present
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Element element, int elementMatch, long seconds) throws IOException {
		return waitForElementNotPresent(element.getType(), element.getLocator(), elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is no longer present for a maximum
	 * of 5 seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Locators type, String locator, int elementMatch) throws IOException {
		return waitForElementNotPresent(type, locator, elementMatch, WAITFOR);
	}

	/**
	 * a method for waiting until an element is no longer present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Locators type, String locator, int elementMatch, long seconds)
			throws IOException {
		return locatorAction.waitForElementNotPresent(type, locator, elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is displayed for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Element element) throws IOException {
		return waitForElementDisplayed(element.getType(), element.getLocator());
	}

	/**
	 * a method for waiting until an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Element element, long seconds) throws IOException {
		return waitForElementDisplayed(element.getType(), element.getLocator(), seconds);
	}

	/**
	 * a method for waiting until an element is displayed for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Locators type, String locator) throws IOException {
		return waitForElementDisplayed(type, locator, WAITFOR);
	}

	/**
	 * a method for waiting until an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Locators type, String locator, long seconds) throws IOException {
		return waitForElementDisplayed(type, locator, 0, seconds);
	}

	/**
	 * a method for waiting until an element is displayed for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Element element, int elementMatch) throws IOException {
		return waitForElementDisplayed(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for waiting until an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Element element, int elementMatch, long seconds) throws IOException {
		return waitForElementDisplayed(element.getType(), element.getLocator(), elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is displayed for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Locators type, String locator, int elementMatch) throws IOException {
		return waitForElementDisplayed(type, locator, elementMatch, WAITFOR);
	}

	/**
	 * a method for waiting until an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Locators type, String locator, int elementMatch, long seconds)
			throws IOException {
		return locatorAction.waitForElementDisplayed(type, locator, elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is not displayed for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Element element) throws IOException {
		return waitForElementNotDisplayed(element.getType(), element.getLocator());
	}

	/**
	 * a method for waiting until an element is not displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Element element, long seconds) throws IOException {
		return waitForElementNotDisplayed(element.getType(), element.getLocator(), seconds);
	}

	/**
	 * a method for waiting until an element is not displayed for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Locators type, String locator) throws IOException {
		return waitForElementNotDisplayed(type, locator, WAITFOR);
	}

	/**
	 * a method for waiting until an element is not displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Locators type, String locator, long seconds) throws IOException {
		return waitForElementNotDisplayed(type, locator, 0, seconds);
	}

	/**
	 * a method for waiting until an element is not displayed for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Element element, int elementMatch) throws IOException {
		return waitForElementNotDisplayed(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for waiting until an element is not displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Element element, int elementMatch, long seconds) throws IOException {
		return waitForElementNotDisplayed(element.getType(), element.getLocator(), elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is not displayed for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Locators type, String locator, int elementMatch) throws IOException {
		return waitForElementNotDisplayed(type, locator, elementMatch, WAITFOR);
	}

	/**
	 * a method for waiting until an element is not displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Locators type, String locator, int elementMatch, long seconds)
			throws IOException {
		return locatorAction.waitForElementNotDisplayed(type, locator, elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is enabled for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Element element) throws IOException {
		return waitForElementEnabled(element.getType(), element.getLocator());
	}

	/**
	 * a method for waiting until an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Element element, long seconds) throws IOException {
		return waitForElementEnabled(element.getType(), element.getLocator(), seconds);
	}

	/**
	 * a method for waiting until an element is enabled for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Locators type, String locator) throws IOException {
		return waitForElementEnabled(type, locator, WAITFOR);
	}

	/**
	 * a method for waiting until an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Locators type, String locator, long seconds) throws IOException {
		return waitForElementEnabled(type, locator, 0, seconds);
	}

	/**
	 * a method for waiting until an element is enabled for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Element element, int elementMatch) throws IOException {
		return waitForElementEnabled(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for waiting until an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Element element, int elementMatch, long seconds) throws IOException {
		return waitForElementEnabled(element.getType(), element.getLocator(), elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is enabled for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Locators type, String locator, int elementMatch) throws IOException {
		return waitForElementEnabled(type, locator, elementMatch, WAITFOR);
	}

	/**
	 * a method for waiting until an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Locators type, String locator, int elementMatch, long seconds) throws IOException {
		return locatorAction.waitForElementEnabled(type, locator, elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is not enabled for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Element element) throws IOException {
		return waitForElementNotEnabled(element.getType(), element.getLocator());
	}

	/**
	 * a method for waiting until an element is not enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Element element, long seconds) throws IOException {
		return waitForElementNotEnabled(element.getType(), element.getLocator(), seconds);
	}

	/**
	 * a method for waiting until an element is not enabled for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Locators type, String locator) throws IOException {
		return waitForElementNotEnabled(type, locator, WAITFOR);
	}

	/**
	 * a method for waiting until an element is not enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Locators type, String locator, long seconds) throws IOException {
		return waitForElementNotEnabled(type, locator, 0, seconds);
	}

	/**
	 * a method for waiting until an element is not enabled for a maximum of 5
	 * seconds
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Element element, int elementMatch) throws IOException {
		return waitForElementNotEnabled(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for waiting until an element is not enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Element element, int elementMatch, long seconds) throws IOException {
		return waitForElementNotEnabled(element.getType(), element.getLocator(), elementMatch, seconds);
	}

	/**
	 * a method for waiting until an element is not enabled for a maximum of 5
	 * seconds
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Locators type, String locator, int elementMatch) throws IOException {
		return waitForElementNotEnabled(type, locator, elementMatch, WAITFOR);
	}

	/**
	 * a method for waiting until an element is not enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Locators type, String locator, int elementMatch, long seconds)
			throws IOException {
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
	 * @throws IOException
	 */
	public boolean isElementPresent(Element element) throws IOException {
		return isElementPresent(element.getType(), element.getLocator());
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Element element, boolean print) throws IOException {
		return isElementPresent(element.getType(), element.getLocator(), print);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Locators type, String locator) throws IOException {
		return isElementPresent(type, locator, false);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Locators type, String locator, boolean print) throws IOException {
		return isElementPresent(type, locator, 0, print);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Element element, int elementMatch) throws IOException {
		return isElementPresent(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Element element, int elementMatch, boolean print) throws IOException {
		return isElementPresent(element.getType(), element.getLocator(), elementMatch, print);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Locators type, String locator, int elementMatch) throws IOException {
		return isElementPresent(type, locator, elementMatch, false);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Locators type, String locator, int elementMatch, boolean print) throws IOException {
		return locatorAction.isElementPresent(type, locator, elementMatch, print);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is an input or not
	 * @throws IOException
	 */
	public boolean isElementInput(Element element) throws IOException {
		return isElementInput(element.getType(), element.getLocator());
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
	 * @throws IOException
	 */
	public boolean isElementInput(Element element, boolean print) throws IOException {
		return isElementInput(element.getType(), element.getLocator(), print);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is an input or not
	 * @throws IOException
	 */
	public boolean isElementInput(Locators type, String locator) throws IOException {
		return isElementInput(type, locator, false);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementInput(Locators type, String locator, boolean print) throws IOException {
		return isElementInput(type, locator, 0, print);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is an input or not
	 * @throws IOException
	 */
	public boolean isElementInput(Element element, int elementMatch) throws IOException {
		return isElementInput(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementInput(Element element, int elementMatch, boolean print) throws IOException {
		return isElementInput(element.getType(), element.getLocator(), elementMatch, print);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is an input or not
	 * @throws IOException
	 */
	public boolean isElementInput(Locators type, String locator, int elementMatch) throws IOException {
		return isElementInput(type, locator, elementMatch, false);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementInput(Locators type, String locator, int elementMatch, boolean print) throws IOException {
		return locatorAction.isElementInput(type, locator, elementMatch, print);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Element element) throws IOException {
		return isElementEnabled(element.getType(), element.getLocator());
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Element element, boolean print) throws IOException {
		return isElementEnabled(element.getType(), element.getLocator(), print);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Locators type, String locator) throws IOException {
		return isElementEnabled(type, locator, false);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Locators type, String locator, boolean print) throws IOException {
		return isElementEnabled(type, locator, 0, print);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Element element, int elementMatch) throws IOException {
		return isElementEnabled(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Element element, int elementMatch, boolean print) throws IOException {
		return isElementEnabled(element.getType(), element.getLocator(), elementMatch, print);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Locators type, String locator, int elementMatch) throws IOException {
		return isElementEnabled(type, locator, elementMatch, false);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Locators type, String locator, int elementMatch, boolean print) throws IOException {
		return locatorAction.isElementEnabled(type, locator, elementMatch, print);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Element element) throws IOException {
		return isElementChecked(element.getType(), element.getLocator());
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Element element, boolean print) throws IOException {
		return isElementChecked(element.getType(), element.getLocator(), print);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Locators type, String locator) throws IOException {
		return isElementChecked(type, locator, false);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Locators type, String locator, boolean print) throws IOException {
		return isElementChecked(type, locator, 0, print);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Element element, int elementMatch) throws IOException {
		return isElementChecked(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Element element, int elementMatch, boolean print) throws IOException {
		return isElementChecked(element.getType(), element.getLocator(), elementMatch, print);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Locators type, String locator, int elementMatch) throws IOException {
		return isElementChecked(type, locator, elementMatch, false);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Locators type, String locator, int elementMatch, boolean print) throws IOException {
		return locatorAction.isElementChecked(type, locator, elementMatch, print);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Element element) throws IOException {
		return isElementDisplayed(element.getType(), element.getLocator());
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Element element, boolean print) throws IOException {
		return isElementDisplayed(element.getType(), element.getLocator(), print);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Locators type, String locator) throws IOException {
		return isElementDisplayed(type, locator, false);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Locators type, String locator, boolean print) throws IOException {
		return isElementDisplayed(type, locator, 0, print);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Element element, int elementMatch) throws IOException {
		return isElementDisplayed(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Element element, int elementMatch, boolean print) throws IOException {
		return isElementDisplayed(element.getType(), element.getLocator(), elementMatch, print);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Locators type, String locator, int elementMatch) throws IOException {
		return isElementDisplayed(type, locator, elementMatch, false);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Locators type, String locator, int elementMatch, boolean print)
			throws IOException {
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
	 * @throws InvalidActionException
	 */
	public void takeScreenshot(String imageName) throws InvalidActionException {
		if (browser == Browsers.HTMLUNIT) {
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
	 * @throws InvalidLocatorTypeException
	 */
	public int getElementMatchCount(Element element) throws InvalidLocatorTypeException {
		return getElementMatchCount(element.getType(), element.getLocator());
	}

	/**
	 * a method to determine how many elements match the selector
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: how many element match the selector
	 * @throws InvalidLocatorTypeException
	 */
	public int getElementMatchCount(Locators type, String locator) throws InvalidLocatorTypeException {
		return locatorAction.getElementMatchCount(type, locator);
	}

	/**
	 * get the number of options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: the number of select options
	 * @throws IOException
	 */
	public int getNumOfSelectOptions(Element element) throws IOException {
		return getNumOfSelectOptions(element.getType(), element.getLocator());
	}

	/**
	 * get the number of options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: the number of select options
	 * @throws IOException
	 */
	public int getNumOfSelectOptions(Locators type, String locator) throws IOException {
		return getNumOfSelectOptions(type, locator, 0);
	}

	/**
	 * get the number of options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer: the number of select options
	 * @throws IOException
	 */
	public int getNumOfSelectOptions(Element element, int elementMatch) throws IOException {
		return getNumOfSelectOptions(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the number of options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer: the number of select options
	 * @throws IOException
	 */
	public int getNumOfSelectOptions(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getNumOfSelectOptions(type, locator, elementMatch);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectOptions(Element element) throws IOException {
		return getSelectOptions(element.getType(), element.getLocator());
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectOptions(Locators type, String locator) throws IOException {
		return getSelectOptions(type, locator, 0);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectOptions(Element element, int elementMatch) throws IOException {
		return getSelectOptions(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectOptions(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getSelectOptions(type, locator, elementMatch);
	}

	/**
	 * get the rows of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return List: a list of the table rows as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableRows(Element element) throws IOException {
		return getTableRows(element.getType(), element.getLocator());
	}

	/**
	 * get the rows of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return List: a list of the table rows as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableRows(Locators type, String locator) throws IOException {
		return getTableRows(type, locator, 0);
	}

	/**
	 * get the rows of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return List: a list of the table rows as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableRows(Element element, int elementMatch) throws IOException {
		return getTableRows(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the rows of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return List: a list of the table rows as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableRows(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getTableRows(type, locator, elementMatch);
	}

	/**
	 * get the number of rows of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: the number of table rows
	 * @throws IOException
	 */
	public int getNumOfTableRows(Element element) throws IOException {
		return getNumOfTableRows(element.getType(), element.getLocator());
	}

	/**
	 * get the number of rows of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: the number of table rows
	 * @throws IOException
	 */
	public int getNumOfTableRows(Locators type, String locator) throws IOException {
		List<WebElement> rows = getTableRows(type, locator, 0);
		return rows.size();
	}

	/**
	 * get the number of rows of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer: the number of table rows
	 * @throws IOException
	 */
	public int getNumOfTableRows(Element element, int elementMatch) throws IOException {
		return getNumOfTableRows(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the number of rows of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer: the number of table rows
	 * @throws IOException
	 */
	public int getNumOfTableRows(Locators type, String locator, int elementMatch) throws IOException {
		List<WebElement> rows = getTableRows(type, locator, elementMatch);
		return rows.size();
	}

	/**
	 * get the columns of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return List: a list of the table columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumns(Element element) throws IOException {
		return getTableColumns(element.getType(), element.getLocator());
	}

	/**
	 * get the columns of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return List: a list of the table columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumns(Locators type, String locator) throws IOException {
		return getTableColumns(type, locator, 0);
	}

	/**
	 * get the columns of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return List: a list of the table columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumns(Element element, int elementMatch) throws IOException {
		return getTableColumns(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the columns of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return List: a list of the table columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumns(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getTableColumns(type, locator, elementMatch);
	}

	/**
	 * get the number of columns of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: the number of table columns
	 * @throws IOException
	 */
	public int getNumOfTableColumns(Element element) throws IOException {
		return getNumOfTableColumns(element.getType(), element.getLocator());
	}

	/**
	 * get the number of columns of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: the number of table columns
	 * @throws IOException
	 */
	public int getNumOfTableColumns(Locators type, String locator) throws IOException {
		List<WebElement> columns = getTableColumns(type, locator);
		return columns.size();
	}

	/**
	 * get the number of columns of a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer: the number of table columns
	 * @throws IOException
	 */
	public int getNumOfTableColumns(Element element, int elementMatch) throws IOException {
		return getNumOfTableColumns(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the number of columns of a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer: the number of table columns
	 * @throws IOException
	 */
	public int getNumOfTableColumns(Locators type, String locator, int elementMatch) throws IOException {
		List<WebElement> columns = getTableColumns(type, locator, elementMatch);
		return columns.size();
	}

	/**
	 * a method to retrieve the row number in a table that has a header (th) of
	 * the indicated value
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param header
	 *            - the full text value expected in a th cell
	 * @return Integer: the row number containing the header
	 * @throws IOException
	 */
	public int getTableRowHeader(Element element, String header) throws IOException {
		return getTableRowHeader(element.getType(), element.getLocator(), header);
	}

	/**
	 * a method to retrieve the row number in a table that has a header (th) of
	 * the indicated value
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param header
	 *            - the full text value expected in a th cell
	 * @return Integer: the row number containing the header
	 * @throws IOException
	 */
	public int getTableRowHeader(Locators type, String locator, String header) throws IOException {
		return getTableRowHeader(type, locator, 0, header);
	}

	/**
	 * a method to retrieve the row number in a table that has a header (th) of
	 * the indicated value
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param header
	 *            - the full text value expected in a th cell
	 * @return Integer: the row number containing the header
	 * @throws IOException
	 */
	public int getTableRowHeader(Element element, int elementMatch, String header) throws IOException {
		return getTableRowHeader(element.getType(), element.getLocator(), elementMatch, header);
	}

	/**
	 * a method to retrieve the row number in a table that has a header (th) of
	 * the indicated value
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param header
	 *            - the full text value expected in a th cell
	 * @return Integer: the row number containing the header
	 * @throws IOException
	 */
	public int getTableRowHeader(Locators type, String locator, int elementMatch, String header) throws IOException {
		return locatorAction.getTableRowHeader(type, locator, elementMatch, header);
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
	 * @throws IOException
	 */
	public List<WebElement> getTableColumn(Element element, int colNum) throws IOException {
		return getTableColumn(element.getType(), element.getLocator(), colNum);
	}

	/**
	 * get a specific column from a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param colNum
	 *            - the column number of the table to obtain - note, column
	 *            numbering starts at 1, NOT 0
	 * @return List: a list of the table cells in the columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumn(Locators type, String locator, int colNum) throws IOException {
		return getTableColumn(type, locator, 0, colNum);
	}

	/**
	 * get a specific column from a table
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param colNum
	 *            - the column number of the table to obtain - note, column
	 *            numbering starts at 1, NOT 0
	 * @return List: a list of the table cells in the columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumn(Element element, int elementMatch, int colNum) throws IOException {
		return getTableColumn(element.getType(), element.getLocator(), elementMatch, colNum);
	}

	/**
	 * get a specific column from a table
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param colNum
	 *            - the column number of the table to obtain - note, column
	 *            numbering starts at 1, NOT 0
	 * @return List: a list of the table cells in the columns as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumn(Locators type, String locator, int elementMatch, int colNum)
			throws IOException {
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
	 * @throws IOException
	 */
	public WebElement getTableCell(Element element, int row, int col) throws IOException {
		return getTableCell(element.getType(), element.getLocator(), row, col);
	}

	/**
	 * get the contents of a specific cell
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
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
	 * @throws IOException
	 */
	public WebElement getTableCell(Locators type, String locator, int row, int col) throws IOException {
		return getTableCell(type, locator, 0, row, col);
	}

	/**
	 * get the contents of a specific cell
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param row
	 *            - the number of the row in the table - note, row numbering
	 *            starts at 1, NOT 0
	 * @param col
	 *            - the number of the column in the table - note, column
	 *            numbering starts at 1, NOT 0
	 * @return WebElement: the cell element object, and all associated values
	 *         with it
	 * @throws IOException
	 */
	public WebElement getTableCell(Element element, int elementMatch, int row, int col) throws IOException {
		return getTableCell(element.getType(), element.getLocator(), elementMatch, row, col);
	}

	/**
	 * get the contents of a specific cell
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param row
	 *            - the number of the row in the table - note, row numbering
	 *            starts at 1, NOT 0
	 * @param col
	 *            - the number of the column in the table - note, column
	 *            numbering starts at 1, NOT 0
	 * @return WebElement: the cell element object, and all associated values
	 *         with it
	 * @throws IOException
	 */
	public WebElement getTableCell(Locators type, String locator, int elementMatch, int row, int col)
			throws IOException {
		return locatorAction.getTableCell(type, locator, elementMatch, row, col);
	}

	//////////////////////////////////
	// override our SE actions
	//////////////////////////////////

	/**
	 * our generic selenium click functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int click(Element element) throws IOException {
		return click(element.getType(), element.getLocator());
	}

	/**
	 * our generic selenium click functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int click(Locators type, String locator) throws IOException {
		return click(type, locator, 0);
	}

	/**
	 * our generic selenium click functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int click(Element element, int elementMatch) throws IOException {
		return click(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * our generic selenium click functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int click(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.click(type, locator, elementMatch);
	}

	/**
	 * our generic selenium submit functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int submit(Element element) throws IOException {
		return submit(element.getType(), element.getLocator());
	}

	/**
	 * our generic selenium submit functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int submit(Locators type, String locator) throws IOException {
		return submit(type, locator, 0);
	}

	/**
	 * our generic selenium submit functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int submit(Element element, int elementMatch) throws IOException {
		return submit(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * our generic selenium submit functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int submit(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.submit(type, locator, elementMatch);
	}

	/**
	 * a method to simulate the mouse hovering over an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int hover(Element element) throws IOException {
		return hover(element.getType(), element.getLocator());
	}

	/**
	 * a method to simulate the mouse hovering over an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int hover(Locators type, String locator) throws IOException {
		return hover(type, locator, 0);
	}

	/**
	 * a method to simulate the mouse hovering over an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int hover(Element element, int elementMatch) throws IOException {
		return hover(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a method to simulate the mouse hovering over an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int hover(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.hover(type, locator, elementMatch);
	}

	/**
	 * a custom selenium functionality to apply a blur to an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int blur(Element element) throws IOException {
		return blur(element.getType(), element.getLocator());
	}

	/**
	 * a custom selenium functionality to apply a blur to an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int blur(Locators type, String locator) throws IOException {
		return blur(type, locator, 0);
	}

	/**
	 * a custom selenium functionality to apply a blur to an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int blur(Element element, int elementMatch) throws IOException {
		return blur(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a custom selenium functionality to apply a blur to an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int blur(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.blur(type, locator, elementMatch);
	}

	/**
	 * our generic selenium type functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param text
	 *            - the text to be typed in
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Element element, String text) throws IOException {
		return type(element.getType(), element.getLocator(), text);
	}

	/**
	 * our generic selenium type functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param text
	 *            - the text to be typed in
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Locators type, String locator, String text) throws IOException {
		return type(type, locator, 0, text);
	}

	/**
	 * our generic selenium type functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param text
	 *            - the text to be typed in
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Element element, int elementMatch, String text) throws IOException {
		return type(element.getType(), element.getLocator(), elementMatch, text);
	}

	/**
	 * our generic selenium type functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param text
	 *            - the text to be typed in
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Locators type, String locator, int elementMatch, String text) throws IOException {
		return locatorAction.type(type, locator, elementMatch, text);
	}

	/**
	 * our generic selenium type functionality implemented for specific keys
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param key
	 *            - the key to be pressed
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Element element, Keys key) throws IOException {
		return type(element.getType(), element.getLocator(), key);
	}

	/**
	 * our generic selenium type functionality implemented for specific keys
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param key
	 *            - the key to be pressed
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Locators type, String locator, Keys key) throws IOException {
		return type(type, locator, 0, key);
	}

	/**
	 * our generic selenium type functionality implemented for specific keys
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param key
	 *            - the key to be pressed
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Element element, int elementMatch, Keys key) throws IOException {
		return type(element.getType(), element.getLocator(), elementMatch, key);
	}

	/**
	 * our generic selenium type functionality implemented for specific keys
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param key
	 *            - the key to be pressed
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Locators type, String locator, int elementMatch, Keys key) throws IOException {
		return locatorAction.type(type, locator, elementMatch, key);
	}

	/**
	 * our generic selenium clear functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int clear(Element element) throws IOException {
		return clear(element.getType(), element.getLocator());
	}

	/**
	 * our generic selenium clear functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int clear(Locators type, String locator) throws IOException {
		return clear(type, locator, 0);
	}

	/**
	 * our generic selenium clear functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int clear(Element element, int elementMatch) throws IOException {
		return clear(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * our generic selenium clear functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int clear(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.clear(type, locator, elementMatch);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param value
	 *            - the select option to be selected - note, row numbering
	 *            starts at 0
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Element element, int value) throws IOException {
		return select(element.getType(), element.getLocator(), value);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param value
	 *            - the select option to be selected - note, row numbering
	 *            starts at 0
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Locators type, String locator, int value) throws IOException {
		String[] options = getSelectOptions(type, locator);
		return select(type, locator, 0, options[value]);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param value
	 *            - the select option to be selected - note, row numbering
	 *            starts at 0
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Element element, int value, int elementMatch) throws IOException {
		return select(element.getType(), element.getLocator(), elementMatch, value);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param value
	 *            - the select option to be selected - note, row numbering
	 *            starts at 0
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Locators type, String locator, int elementMatch, int value) throws IOException {
		String[] options = getSelectOptions(type, locator, elementMatch);
		return select(type, locator, elementMatch, options[value]);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param value
	 *            - the select option to be selected
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Element element, String value) throws IOException {
		return select(element.getType(), element.getLocator(), value);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param value
	 *            - the select option to be selected
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Locators type, String locator, String value) throws IOException {
		return select(type, locator, 0, value);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param value
	 *            - the select option to be selected
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Element element, String value, int elementMatch) throws IOException {
		return select(element.getType(), element.getLocator(), elementMatch, value);
	}

	/**
	 * our generic select selenium functionality
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param value
	 *            - the select option to be selected
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Locators type, String locator, int elementMatch, String value) throws IOException {
		return locatorAction.select(type, locator, elementMatch, value);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Element element) throws IOException {
		return move(element.getType(), element.getLocator());
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Locators type, String locator) throws IOException {
		return move(type, locator, 0);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Element element, int elementMatch) throws IOException {
		return move(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Locators type, String locator, int elementMatch) throws IOException {
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
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Element element, long position) throws IOException {
		return move(element.getType(), element.getLocator(), position);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param position
	 *            - how many pixels above the element to scroll to
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Locators type, String locator, long position) throws IOException {
		return locatorAction.move(type, locator, 0, position);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param position
	 *            - how many pixels above the element to scroll to
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Element element, int elementMatch, long position) throws IOException {
		return move(element.getType(), element.getLocator(), elementMatch, position);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param position
	 *            - how many pixels above the element to scroll to
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Locators type, String locator, int elementMatch, long position) throws IOException {
		return locatorAction.move(type, locator, elementMatch, position);
	}

	/**
	 * An custom script to scroll to a given position on the page
	 *
	 * @param desiredPosition
	 *            - the position on the page to scroll to
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int scroll(int desiredPosition) throws IOException {
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
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int switchToNewWindow() {
		String cantSelect = "Unable to switch to new window";
		String action = "Switching to new window";
		String expected = "New window is now selected";
		try {
			parentWindow = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect, Result.FAILURE);
			return 1;
		}
		file.recordAction(action, expected, expected, Result.SUCCESS);
		return 0;
	}

	/**
	 * a function to switch back to your parent window.
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int switchToParentWindow() {
		String cantSelect = "Unable to switch to parent window";
		String action = "Switching to parent window";
		String expected = "Parent window is now selected";
		try {
			driver.switchTo().window(parentWindow);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect, Result.FAILURE);
			return 1;
		}
		file.recordAction(action, expected, expected, Result.SUCCESS);
		return 0;
	}

	/**
	 * a function to switch tabs. note that tab numbering starts at 0
	 * 
	 * @param tabNumber
	 *            - the tab number, starts at 0
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int switchTab(int tabNumber) {
		String cantSelect = "Unable to switch to new tab";
		String action = "Switching to new tab";
		String expected = "New tab is now selected";
		try {
			ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tabNumber));
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect, Result.FAILURE);
			return 1;
		}
		file.recordAction(action, expected, expected, Result.SUCCESS);
		return 0;
	}

	/**
	 * a function to switch to a parent frame
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int selectParentFrame() {
		String cantSelect = "Unable to focus on parent frame ";
		String action = "Focusing on parent frame";
		String expected = "Parent frame is present, displayed, and focused";
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + " parent frame", Result.FAILURE);
			return 1;
		}
		file.recordAction(action, expected, "Focused on frame parent frame", Result.SUCCESS);
		return 0;
	}

	/**
	 * a function to switch to a frame using the zero index frame id
	 * 
	 * @param frameNumber
	 *            - the frame number, starts at 0
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int selectFrame(int frameNumber) {
		String cantSelect = "Unable to focus on frame ";
		String action = "Focusing on frame " + frameNumber;
		String expected = "Frame " + frameNumber + " is present, displayed, and focused";
		try {
			driver.switchTo().frame(frameNumber);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + frameNumber, Result.FAILURE);
			return 1;
		}
		file.recordAction(action, expected, "Focused on frame " + frameNumber, Result.SUCCESS);
		return 0;
	}

	/**
	 * a function to switch to a frame using the frame name/id
	 * 
	 * @param frameID
	 *            - the frame name or ID
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int selectFrame(String frameID) {
		String cantSelect = "Unable to focus on frame ";
		String action = "Focusing on frame " + frameID;
		String expected = "Frame " + frameID + " is present, displayed, and focused";
		try {
			driver.switchTo().frame(frameID);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + frameID, Result.FAILURE);
			return 1;
		}
		file.recordAction(action, expected, "Focused on frame " + frameID, Result.SUCCESS);
		return 0;
	}

	/**
	 * a function to switch to a frame
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int selectFrame(Element element) throws IOException {
		return selectFrame(element.getType(), element.getLocator());
	}

	/**
	 * a function to switch to a frame
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int selectFrame(Locators type, String locator) throws IOException {
		return selectFrame(type, locator, 0);
	}

	/**
	 * a function to switch to a frame
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int selectFrame(Element element, int elementMatch) throws IOException {
		return selectFrame(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a function to switch to a frame
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int selectFrame(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.selectFrame(type, locator, elementMatch);
	}

	/**
	 * a function to close windows. note that if this is the only window open,
	 * you will end your session
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int closeWindow() {
		return closeTab();
	}

	/**
	 * a function to close tabs. note that if this is the only tab open, you
	 * will end your session
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int closeTab() {
		try {
			driver.close();
		} catch (Exception e) {
			log.error(e);
			return 1;
		}
		return 0;
	}

	/**
	 * some functionality to maximize the current window
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int maximizeScreen() {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			log.error(e);
			return 1;
		}
		return 0;
	}

	/**
	 * Some basic functionality for clicking 'OK' on an alert box
	 *
	 * @return Integer - the number of errors encountered while executing these
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
		Alert alert = driver.switchTo().alert();
		alert.accept();
		file.recordAction(action, expected, "Clicked 'OK' on the alert", Result.SUCCESS);
		return 0;
	}

	/**
	 * Some basic functionality for clicking 'OK' on a confirmation box
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int acceptConfirmation() {
		String action = "Clicking 'OK' on a confirmation";
		String expected = "Confirmation is present to be clicked";
		// wait for element to be present
		if (!isConfirmationPresent(false)) {
			waitForConfirmationPresent();
		}
		if (!isConfirmationPresent(false)) {
			file.recordAction(action, expected, "Unable to click confirmation as it is not present", Result.FAILURE);
			file.addError();
			return 1; // indicates element not present
		}
		Alert alert = driver.switchTo().alert();
		alert.accept();
		file.recordAction(action, expected, "Clicked 'OK' on the confirmation", Result.SUCCESS);
		return 0;
	}

	/**
	 * Some basic functionality for clicking 'Cancel' on a confirmation box
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int dismissConfirmation() {
		String action = "Clicking 'Cancel' on a confirmation";
		String expected = "Confirmation is present to be clicked";
		// wait for element to be present
		if (!isConfirmationPresent(false)) {
			waitForConfirmationPresent();
		}
		if (!isConfirmationPresent(false)) {
			file.recordAction(action, expected, "Unable to click confirmation as it is not present", Result.FAILURE);
			file.addError();
			return 1; // indicates element not present
		}
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		file.recordAction(action, expected, "Clicked 'Cancel' on the confirmation", Result.SUCCESS);
		return 0;
	}

	/**
	 * Some basic functionality for clicking 'OK' on a prompt box
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int acceptPrompt() {
		String action = "Clicking 'OK' on a prompt";
		String expected = "Prompt is present to be clicked";
		// wait for element to be present
		if (!isPromptPresent(false)) {
			waitForPromptPresent();
		}
		if (!isPromptPresent(false)) {
			file.recordAction(action, expected, "Unable to click prompt as it is not present", Result.FAILURE);
			file.addError();
			return 1; // indicates element not present
		}
		Alert alert = driver.switchTo().alert();
		alert.accept();
		file.recordAction(action, expected, "Clicked 'OK' on the prompt", Result.SUCCESS);
		return 0;
	}

	/**
	 * Some basic functionality for clicking 'Cancel' on a prompt box
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int dismissPrompt() {
		String action = "Clicking 'Cancel' on a prompt";
		String expected = "Prompt is present to be clicked";
		// wait for element to be present
		if (!isPromptPresent(false)) {
			waitForPromptPresent();
		}
		if (!isPromptPresent(false)) {
			file.recordAction(action, expected, "Unable to click prompt as it is not present", Result.FAILURE);
			file.addError();
			return 1; // indicates element not present
		}
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		file.recordAction(action, expected, "Clicked 'Cancel' on the prompt", Result.SUCCESS);
		return 0;
	}

	/**
	 * Some basic functionality for typing text into a prompt box
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int typeIntoPrompt(String text) {
		String action = "Typing text '" + text + "' into prompt";
		String expected = "Prompt is present and enabled to have text " + text + " typed in";
		// wait for element to be present
		if (!isPromptPresent(false)) {
			waitForPromptPresent();
		}
		if (!isPromptPresent(false)) {
			file.recordAction(action, expected, "Unable to type in prompt as it is not present", Result.FAILURE);
			file.addError();
			return 1; // indicates element not present
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(text);
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
	private double waitForPopup(long seconds) {
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
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int waitForAlertPresent() {
		return waitForAlertPresent(5);
	}

	/**
	 * a method for waiting until an alert is present
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int waitForAlertPresent(long seconds) {
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
			return "";
		}
		Alert alert = driver.switchTo().alert();
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
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int waitForConfirmationPresent() {
		return waitForConfirmationPresent(5);
	}

	/**
	 * a method for waiting until a confirmation is present
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int waitForConfirmationPresent(long seconds) {
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
			return "";
		}
		Alert alert = driver.switchTo().alert();
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
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int waitForPromptPresent() {
		return waitForPromptPresent(5);
	}

	/**
	 * a method for waiting until a prompt is present
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int waitForPromptPresent(long seconds) {
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
			return "";
		}
		Alert alert = driver.switchTo().alert();
		return alert.getText();
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
		if (driver.manage().getCookieNamed(expectedCookieName) != null) {
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
		return driver.manage().getCookieNamed(expectedCookieName);
	}

	/**
	 * a method to get the value of a particular cookie
	 *
	 * @param expectedCookieName
	 *            - the name of the cookie
	 * @return String - the value of the cookie
	 */
	public String getCookieValue(String expectedCookieName) {
		return getCookie(expectedCookieName).getValue();
	}

	/**
	 * a method to get the path of a particular cookie
	 *
	 * @param expectedCookieName
	 *            - the name of the cookie
	 * @return String - the path of the cookie
	 */
	public String getCookiePath(String expectedCookieName) {
		return getCookie(expectedCookieName).getPath();
	}

	/**
	 * a method to get the domain of a particular cookie
	 *
	 * @param expectedCookieName
	 *            - the name of the cookie
	 * @return String - the domain of the cookie
	 */
	public String getCookieDomain(String expectedCookieName) {
		return getCookie(expectedCookieName).getDomain();
	}

	/**
	 * a method to get the expriation of a particular cookie
	 *
	 * @param expectedCookieName
	 *            - the name of the cookie
	 * @return String - the expiration of the cookie
	 */
	public Date getCookieExpiration(String expectedCookieName) {
		return getCookie(expectedCookieName).getExpiry();
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: is something selected or not
	 * @throws IOException
	 */
	public boolean isSomethingSelected(Element element) throws IOException {
		return isSomethingSelected(element.getType(), element.getLocator());
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: is something selected or not
	 * @throws IOException
	 */
	public boolean isSomethingSelected(Locators type, String locator) throws IOException {
		return isSomethingSelected(type, locator, 0);
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: is something selected or not
	 * @throws IOException
	 */
	public boolean isSomethingSelected(Element element, int elementMatch) throws IOException {
		return isSomethingSelected(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return boolean: is something selected or not
	 * @throws IOException
	 */
	public boolean isSomethingSelected(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.isSomethingSelected(type, locator, elementMatch);
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedText(Element element) throws IOException {
		return getSelectedText(element.getType(), element.getLocator());
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedText(Locators type, String locator) throws IOException {
		return getSelectedText(type, locator, 0);
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedText(Element element, int elementMatch) throws IOException {
		return getSelectedText(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedText(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getSelectedText(type, locator, elementMatch);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedTexts(Element element) throws IOException {
		return getSelectedTexts(element.getType(), element.getLocator());
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedTexts(Locators type, String locator) throws IOException {
		return getSelectedTexts(type, locator, 0);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedTexts(Element element, int elementMatch) throws IOException {
		return getSelectedTexts(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedTexts(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getSelectedTexts(type, locator, elementMatch);
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedValue(Element element) throws IOException {
		return getSelectedValue(element.getType(), element.getLocator());
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedValue(Locators type, String locator) throws IOException {
		return getSelectedValue(type, locator, 0);
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedValue(Element element, int elementMatch) throws IOException {
		return getSelectedValue(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the option from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String: the option from the select element
	 * @throws IOException
	 */
	public String getSelectedValue(Locators type, String locator, int elementMatch) throws IOException {
		return locatorAction.getSelectedValue(type, locator, elementMatch);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedValues(Element element) throws IOException {
		return getSelectedValues(element.getType(), element.getLocator());
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedValues(Locators type, String locator) throws IOException {
		return getSelectedValues(type, locator, 0);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedValues(Element element, int elementMatch) throws IOException {
		return getSelectedValues(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * get the options from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedValues(Locators type, String locator, int elementMatch) throws IOException {
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
		return driver.getPageSource().contains(expectedText);
	}

	/**
	 * our generic selenium is text present functionality implemented
	 *
	 * @param expectedText
	 *            - the text we are expecting to be present on the page
	 * @return boolean - whether or not the text is present
	 */
	public boolean isTextPresent(String expectedText) {
		String bodyText = driver.findElement(By.tagName("body")).getText();
		return bodyText.contains(expectedText);
	}

	/**
	 * our generic selenium get text from an element functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String - the text of the element
	 */
	public String getText(Element element) throws InvalidLocatorTypeException {
		return getText(element.getType(), element.getLocator());
	}

	/**
	 * our generic selenium get text from an element functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String - the text of the element
	 */
	public String getText(Locators type, String locator) throws InvalidLocatorTypeException {
		return getText(type, locator, 0);
	}

	/**
	 * our generic selenium get text from an element functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String - the text of the element
	 */
	public String getText(Element element, int elementMatch) throws InvalidLocatorTypeException {
		return getText(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * our generic selenium get text from an element functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String - the text of the element
	 */
	public String getText(Locators type, String locator, int elementMatch) throws InvalidLocatorTypeException {
		return locatorAction.getText(type, locator, elementMatch);
	}

	/**
	 * our generic selenium get value from an element functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String - the text of the element
	 */
	public String getValue(Element element) throws InvalidLocatorTypeException {
		return getValue(element.getType(), element.getLocator());
	}

	/**
	 * our generic selenium get value from an element functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String - the text of the element
	 */
	public String getValue(Locators type, String locator) throws InvalidLocatorTypeException {
		return getValue(type, locator, 0);
	}

	/**
	 * our generic selenium get value from an element functionality implemented
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String - the text of the element
	 */
	public String getValue(Element element, int elementMatch) throws InvalidLocatorTypeException {
		return getValue(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * our generic selenium get value from an element functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String - the text of the element
	 */
	public String getValue(Locators type, String locator, int elementMatch) throws InvalidLocatorTypeException {
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
	 * @throws InvalidLocatorTypeException
	 */
	public String getCss(Element element, String attribute) throws InvalidLocatorTypeException {
		return getCss(element.getType(), element.getLocator(), attribute);
	}

	/**
	 * a function to return one css attribute of the provided element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the css attribute to be returned
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public String getCss(Locators type, String locator, String attribute) throws InvalidLocatorTypeException {
		return getCss(type, locator, 0, attribute);
	}

	/**
	 * a function to return one css attribute of the provided element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param attribute
	 *            - the css attribute to be returned
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public String getCss(Element element, int elementMatch, String attribute) throws InvalidLocatorTypeException {
		return getCss(element.getType(), element.getLocator(), elementMatch, attribute);
	}

	/**
	 * a function to return one css attribute of the provided element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param attribute
	 *            - the css attribute to be returned
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public String getCss(Locators type, String locator, int elementMatch, String attribute)
			throws InvalidLocatorTypeException {
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
	 * @throws InvalidLocatorTypeException
	 */
	public String getAttribute(Element element, String attribute) throws InvalidLocatorTypeException {
		return getAttribute(element.getType(), element.getLocator(), attribute);
	}

	/**
	 * a function to return one attribute of the provided element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the css attribute to be returned
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public String getAttribute(Locators type, String locator, String attribute) throws InvalidLocatorTypeException {
		return getAttribute(type, locator, attribute);
	}

	/**
	 * a function to return one attribute of the provided element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param attribute
	 *            - the css attribute to be returned
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public String getAttribute(Element element, int elementMatch, String attribute) throws InvalidLocatorTypeException {
		return getAttribute(element.getType(), element.getLocator(), elementMatch, attribute);
	}

	/**
	 * a function to return one attribute of the provided element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param attribute
	 *            - the css attribute to be returned
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public String getAttribute(Locators type, String locator, int elementMatch, String attribute)
			throws InvalidLocatorTypeException {
		return locatorAction.getAttribute(type, locator, elementMatch, attribute);
	}

	/**
	 * a function to return all attributes of the provided element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public Map<String, String> getAllAttributes(Element element) throws InvalidLocatorTypeException {
		return getAllAttributes(element.getType(), element.getLocator());
	}

	/**
	 * a function to return all attributes of the provided element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public Map<String, String> getAllAttributes(Locators type, String locator) throws InvalidLocatorTypeException {
		return getAllAttributes(type, locator, 0);
	}

	/**
	 * a function to return all attributes of the provided element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public Map<String, String> getAllAttributes(Element element, int elementMatch) throws InvalidLocatorTypeException {
		return getAllAttributes(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * a function to return all attributes of the provided element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @return String - the value of the css attribute
	 * @throws InvalidLocatorTypeException
	 */
	public Map<String, String> getAllAttributes(Locators type, String locator, int elementMatch)
			throws InvalidLocatorTypeException {
		return locatorAction.getAllAttributes(type, locator, elementMatch);
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
	 * @return Object: any resultant output from the javascript command
	 */
	public Object getEval(String javascriptFunction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javascriptFunction);
	}

	/**
	 * a way to execute custom javascript functions
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param javascriptFunction
	 * @return Object: any resultant output from the javascript command
	 * @throws InvalidLocatorTypeException
	 */
	public Object getEval(Element element, String javascriptFunction) throws InvalidLocatorTypeException {
		return getEval(element.getType(), element.getLocator(), javascriptFunction);
	}

	/**
	 * a way to execute custom javascript functions
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param javascriptFunction
	 * @return Object: any resultant output from the javascript command
	 * @throws InvalidLocatorTypeException
	 */
	public Object getEval(Locators type, String locator, String javascriptFunction) throws InvalidLocatorTypeException {
		return getEval(type, locator, 0, javascriptFunction);
	}

	/**
	 * a way to execute custom javascript functions
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param javascriptFunction
	 * @return Object: any resultant output from the javascript command
	 * @throws InvalidLocatorTypeException
	 */
	public Object getEval(Element element, int elementMatch, String javascriptFunction)
			throws InvalidLocatorTypeException {
		return getEval(element.getType(), element.getLocator(), elementMatch, javascriptFunction);
	}

	/**
	 * a way to execute custom javascript functions
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * 
	 * @param elementMatch
	 *            - there might be multiple elements that match the given
	 *            selector, which match do you want to perform the actions on
	 * @param javascriptFunction
	 * @return Object: any resultant output from the javascript command
	 * @throws InvalidLocatorTypeException
	 */
	public Object getEval(Locators type, String locator, int elementMatch, String javascriptFunction)
			throws InvalidLocatorTypeException {
		return locatorAction.getEval(type, locator, elementMatch, javascriptFunction);
	}
}