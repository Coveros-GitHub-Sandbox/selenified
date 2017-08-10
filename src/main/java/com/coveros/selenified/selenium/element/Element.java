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

package com.coveros.selenified.selenium.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.coveros.selenified.exceptions.InvalidLocatorTypeException;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.element.Get;
import com.coveros.selenified.selenium.element.Is;
import com.coveros.selenified.selenium.element.WaitFor;
import com.coveros.selenified.output.OutputFile.Result;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.General;

public class Element {

	private static final Logger log = Logger.getLogger(General.class);

	private Locator type;
	private String locator;
	private int match = 0;

	// this will be the name of the file we write all commands out to
	private OutputFile file;

	// what locator actions are available in webdriver
	// this is the driver that will be used for all selenium actions
	private WebDriver driver;

	// the is class to determine if something exists
	private Is is;
	// the wait class to determine if we need to wait for something
	private WaitFor waitFor;
	// the wait class to retrieve information about the element
	private Get get;
	// the is class to determine the state of an element
	private State state;
	// the is class to determine if an element contains something
	private Contains contains;
	// the is class to determine if an element doesn't contain something
	private Excludes excludes;
	// the is class to determine if an element has attributes equal to something
	private Equals equals;

	// constants
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

	public Element(Locator type, String locator) {
		this.setType(type);
		this.setLocator(locator);
	}

	public Element(Locator type, String locator, int match) {
		this.setType(type);
		this.setLocator(locator);
		this.setMatch(match);
	}

	public void init(WebDriver driver, OutputFile file) {
		this.driver = driver;
		this.file = file;

		is = new Is(this, file);
		waitFor = new WaitFor(this, file);
		get = new Get(driver, this);
		state = new State(this, file);
		contains = new Contains(this, file);
		excludes = new Excludes(this, file);
		equals = new Equals(this, file);
	}

	public void setType(Locator type) {
		this.type = type;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public void setMatch(int match) {
		this.match = match;
	}

	public Locator getType() {
		return type;
	}

	public String getLocator() {
		return locator;
	}

	public int getMatch() {
		return match;
	}

	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * A nice output to identify the element by locator and selector
	 * 
	 * @return String: text identifying how the element was located
	 */
	public String prettyOutputStart() {
		return "Element with <i>" + type.toString() + "</i> of <i>" + locator + "</i>";
	}

	/**
	 * A nice output to identify the element by locator and selector
	 * 
	 * @return String: text identifying how the element was located
	 */
	public String prettyOutputLowercase() {
		String output = prettyOutputStart();
		return Character.toLowerCase(output.charAt(0)) + output.substring(1);
	}

	/**
	 * A nice output to identify the element by locator and selector
	 * 
	 * @return String: text identifying how the element was located
	 */
	public String prettyOutput() {
		return " " + prettyOutputLowercase() + " ";
	}

	/**
	 * A nice output to identify the element by locator and selector
	 * 
	 * @return String: text identifying how the element was located
	 */
	public String prettyOutputEnd() {
		return prettyOutputLowercase() + ".";
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

	public State assertState() {
		return state;
	}

	public Contains assertContains() {
		return contains;
	}

	public Excludes assertExcludes() {
		return excludes;
	}

	public Equals assertEquals() {
		return equals;
	}

	//////////////////////////////////////////////////////
	// setup element values
	//////////////////////////////////////////////////////

	/**
	 * a method to determine selenium's By object using selenium webdriver
	 *
	 * @return By: the selenium object
	 * @throws InvalidLocatorTypeException
	 */
	private By defineByElement() throws InvalidLocatorTypeException {
		// consider adding strengthening
		By byElement;
		switch (type) { // determine which locator type we are interested in
		case XPATH:
			byElement = By.xpath(locator);
			break;
		case ID:
			byElement = By.id(locator);
			break;
		case NAME:
			byElement = By.name(locator);
			break;
		case CLASSNAME:
			byElement = By.className(locator);
			break;
		case CSS:
			byElement = By.cssSelector(locator);
			break;
		case LINKTEXT:
			byElement = By.linkText(locator);
			break;
		case PARTIALLINKTEXT:
			byElement = By.partialLinkText(locator);
			break;
		case TAGNAME:
			byElement = By.tagName(locator);
			break;
		default:
			throw new InvalidLocatorTypeException();
		}
		return byElement;
	}

	/**
	 * a method to grab the identified matching web element using selenium
	 * webdriver
	 *
	 * @return WebElement: the element object, and all associated values with it
	 * @throws InvalidLocatorTypeException
	 */
	protected WebElement getWebElement() {
		List<WebElement> elements = getWebElements();
		if (elements.size() > match) {
			return elements.get(match);
		}
		try {
			return driver.findElement(defineByElement());
		} catch (InvalidLocatorTypeException e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * a method to grab all matching web elements using selenium webdriver
	 *
	 * @return List<WebElement>: a list of element objects, and all associated
	 *         values with them
	 * @throws InvalidLocatorTypeException
	 */
	protected List<WebElement> getWebElements() {
		try {
			return driver.findElements(defineByElement());
		} catch (InvalidLocatorTypeException e) {
			log.error(e);
			return new ArrayList<>();
		}
	}

	//////////////////////////////////
	// override the SE actions
	//////////////////////////////////

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
		if (!is.present()) {
			waitFor.present();
		}
		if (!is.present()) {
			file.recordAction(action, expected, extra + element.prettyOutput() + NOTPRESENT, Result.FAILURE);
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
		if (!is.displayed()) {
			waitFor.displayed();
		}
		if (!is.displayed()) {
			file.recordAction(action, expected, extra + element.prettyOutput() + NOTDISPLAYED, Result.FAILURE);
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
		if (!is.enabled()) {
			waitFor.enabled();
		}
		if (!is.enabled()) {
			file.recordAction(action, expected, extra + element.prettyOutput() + NOTENABLED, Result.FAILURE);
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
		if (!is.input()) {
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
		if (!is.displayed()) {
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
		if (!is.displayed()) {
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
		String[] options = get.selectOptions();
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
		if (!Arrays.asList(get.selectOptions()).contains(value)) {
			file.recordAction(action, expected,
					cantSelect + value + " in " + element.prettyOutput()
							+ " as that option isn't present. Available options are:<i><br/>" + "&nbsp;&nbsp;&nbsp;"
							+ String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectOptions()) + "</i>",
					Result.FAILURE);
			file.addError();
			return 1;
		}
		// do the select
		try {
			WebElement webElement = element.getWebElement();
			Select dropdown = new Select(webElement);
			dropdown.selectByVisibleText(value);
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
		if (!is.displayed()) {
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