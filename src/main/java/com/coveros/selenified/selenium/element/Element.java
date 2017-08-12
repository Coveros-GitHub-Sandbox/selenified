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

	public Element(WebDriver driver, OutputFile file, Locator type, String locator) {
		this.type = type;
		this.locator = locator;
		init(driver, file);
	}

	public Element(WebDriver driver, OutputFile file, Locator type, String locator, int match) {
		this.type = type;
		this.locator = locator;
		this.setMatch(match);
		init(driver, file);
	}

	private void init(WebDriver driver, OutputFile file) {
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
	 * @return List: a list of WebElement objects, and all associated values
	 *         with them
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
	 * checks to see if the element is present. If it isn't, it'll wait for 5
	 * seconds for the element to be present
	 * 
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present?
	 */
	private boolean isPresent(String action, String expected, String extra) {
		// wait for element to be present
		if (!is.present()) {
			waitFor.present();
		}
		if (!is.present()) {
			file.recordAction(action, expected, extra + prettyOutput() + NOTPRESENT, Result.FAILURE);
			// indicates element not present
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the element is displayed. If it isn't, it'll wait for 5
	 * seconds for the element to be displayed
	 * 
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element displayed?
	 */
	private boolean isDisplayed(String action, String expected, String extra) {
		// wait for element to be displayed
		if (!is.displayed()) {
			waitFor.displayed();
		}
		if (!is.displayed()) {
			file.recordAction(action, expected, extra + prettyOutput() + NOTDISPLAYED, Result.FAILURE);
			// indicates element not displayed
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the element is enabled. If it isn't, it'll wait for 5
	 * seconds for the element to be enabled
	 * 
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element enabled?
	 */
	private boolean isEnabled(String action, String expected, String extra) {
		// wait for element to be displayed
		if (!is.enabled()) {
			waitFor.enabled();
		}
		if (!is.enabled()) {
			file.recordAction(action, expected, extra + prettyOutput() + NOTENABLED, Result.FAILURE);
			// indicates element not enabled
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the element is an input.
	 * 
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element enabled?
	 */
	private boolean isInput(String action, String expected, String extra) {
		// wait for element to be displayed
		if (!is.input()) {
			file.recordAction(action, expected, extra + prettyOutput() + NOTINPUT, Result.FAILURE);
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
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present, displayed, and enabled?
	 */
	private boolean isPresentDisplayedEnabled(String action, String expected, String extra) {
		// wait for element to be present
		if (!isPresent(action, expected, extra)) {
			return false;
		}
		// wait for element to be displayed
		if (!isDisplayed(action, expected, extra)) {
			return false;
		}
		// wait for element to be enabled
		return isEnabled(action, expected, extra);
	}

	/**
	 * does a check to see if something is present, enabled, and an input. This
	 * returns true if all three are true, otherwise, it returns false
	 * 
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present, enabled, and an input?
	 */
	private boolean isPresentEnabledInput(String action, String expected, String extra) {
		// wait for element to be present
		if (!isPresent(action, expected, extra)) {
			return false;
		}
		// wait for element to be enabled
		if (!isEnabled(action, expected, extra)) {
			return false;
		}
		return isInput(action, expected, extra);
	}

	/**
	 * does a check to see if something is present, displayed, enabled, and an
	 * input. This returns true if all four are true, otherwise, it returns
	 * false
	 * 
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present, displayed, enabled, and an
	 *         input?
	 */
	private boolean isPresentDisplayedEnabledInput(String action, String expected, String extra) {
		// wait for element to be present
		if (!isPresent(action, expected, extra)) {
			return false;
		}
		// wait for element to be displayed
		if (!isDisplayed(action, expected, extra)) {
			return false;
		}
		// wait for element to be enabled
		if (!isEnabled(action, expected, extra)) {
			return false;
		}
		return isInput(action, expected, extra);
	}

	// ///////////////////////////////////
	// basic actions functionality
	// ///////////////////////////////////

	/**
	 * the generic selenium click functionality implemented
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void click() {
		String cantClick = "Unable to click ";
		String action = "Clicking " + prettyOutput();
		String expected = prettyOutput() + " is present, displayed, and enabled to be clicked";
		if (!isPresentDisplayedEnabled(action, expected, cantClick)) {
			return;
		}
		try {
			WebElement webElement = getWebElement();
			Actions selAction = new Actions(driver);
			selAction.click(webElement).perform();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantClick + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Clicked " + prettyOutput(), Result.SUCCESS);
	}

	/**
	 * the generic selenium submit functionality implemented
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void submit() {
		String cantSubmit = "Unable to submit ";
		String action = "Submitting " + prettyOutput();
		String expected = prettyOutput() + " is present, displayed, and enabled to be submitted    ";
		if (!isPresentDisplayedEnabled(action, expected, cantSubmit)) {
			return;
		}
		WebElement webElement = getWebElement();
		try {
			webElement.submit();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSubmit + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Submitted " + prettyOutput(), Result.SUCCESS);
	}

	/**
	 * a method to simulate the mouse hovering over an element
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void hover() {
		String cantHover = "Unable to hover over ";
		String action = "Hovering over " + prettyOutput();
		String expected = prettyOutput() + " is present, and displayed to be hovered over";
		// wait for element to be present
		if (!isPresent(action, expected, cantHover)) {
			return;
		}
		// wait for element to be displayed
		if (!isDisplayed(action, expected, cantHover)) {
			return;
		}
		try {
			Actions selAction = new Actions(driver);
			WebElement webElement = getWebElement();
			selAction.moveToElement(webElement).perform();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantHover + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Hovered over " + prettyOutput(), Result.SUCCESS);
	}

	/**
	 * a custom selenium functionality to apply a blur to an element
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void blur() {
		String cantFocus = "Unable to focus on ";
		String action = "Focusing, then unfocusing (blurring) on " + prettyOutput();
		String expected = prettyOutput() + " is present, displayed, and enabled to be blurred";
		if (!isPresentDisplayedEnabledInput(action, expected, cantFocus)) {
			return;
		}
		try {
			WebElement webElement = getWebElement();
			webElement.sendKeys("\t");
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantFocus + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Focused, then unfocused (blurred) on " + prettyOutput(), Result.SUCCESS);
	}

	/**
	 * type functionality implemented
	 *
	 * @param text
	 *            - the text to be typed in
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void type(String text) {
		String action = "Typing text '" + text + IN + prettyOutput();
		String expected = prettyOutput() + " is present, displayed, and enabled to have text " + text + " typed in";
		if (!isPresentEnabledInput(action, expected, CANTTYPE)) {
			return;
		}
		Boolean warning = false;
		if (!is.displayed()) {
			warning = true;
		}
		try {
			WebElement webElement = getWebElement();
			webElement.sendKeys(text);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, CANTTYPE + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		if (warning) {
			file.recordAction(action, expected, TYPTED + text + IN + prettyOutput()
					+ ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
		} else {
			file.recordAction(action, expected, TYPTED + text + IN + prettyOutput(), Result.SUCCESS);
		}
	}

	/**
	 * the generic selenium type functionality implemented for specific keys
	 *
	 * @param key
	 *            - the key to be pressed
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void type(Keys key) {
		String action = "Typing key '" + key + IN + prettyOutput();
		String expected = prettyOutput() + " is present, displayed, and enabled to have text " + key + " entered";
		if (!isPresentEnabledInput(action, expected, CANTTYPE)) {
			return;
		}
		Boolean warning = false;
		if (!is.displayed()) {
			warning = true;
		}
		try {
			WebElement webElement = getWebElement();
			webElement.sendKeys(key);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, CANTTYPE + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		if (warning) {
			file.recordAction(action, expected, TYPTED + key + IN + prettyOutput()
					+ ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
		} else {
			file.recordAction(action, expected, TYPTED + key + IN + prettyOutput(), Result.SUCCESS);
		}
	}

	/**
	 * the generic selenium clear functionality implemented
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void clear() {
		String cantClear = "Unable to clear ";
		String action = "Clearing text in " + prettyOutput();
		String expected = prettyOutput() + " is present, displayed, and enabled to have text cleared";
		if (!isPresentDisplayedEnabledInput(action, expected, cantClear)) {
			return;
		}
		WebElement webElement = getWebElement();
		try {
			webElement.clear();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantClear + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Cleared text in " + prettyOutput(), Result.SUCCESS);
	}

	/**
	 * the generic select selenium functionality
	 *
	 * @param value
	 *            - the select option to be selected - note, row numbering
	 *            starts at 0
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public void select(int value) {
		String[] options = get.selectOptions();
		if (value > options.length) {
			String action = SELECTING + value + " in " + prettyOutput();
			String expected = prettyOutput() + PRESDISEN + value + SELECTED;
			file.recordAction(action, expected, "Unable to select the <i>" + value
					+ "</i> option, as there are only <i>" + options.length + "</i> available.", Result.FAILURE);
			file.addError();
			return;
		}
		select(options[value]);
	}

	/**
	 * the generic select selenium functionality
	 *
	 * @param value
	 *            - the select option to be selected
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void select(String value) {
		String cantSelect = "Unable to select ";
		String action = SELECTING + value + " in " + prettyOutput();
		String expected = prettyOutput() + PRESDISEN + value + SELECTED;
		if (!isPresentDisplayedEnabledInput(action, expected, cantSelect)) {
			return;
		}
		// ensure the option exists
		if (!Arrays.asList(get.selectOptions()).contains(value)) {
			file.recordAction(action, expected,
					cantSelect + value + " in " + prettyOutput()
							+ " as that option isn't present. Available options are:<i><br/>" + "&nbsp;&nbsp;&nbsp;"
							+ String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectOptions()) + "</i>",
					Result.FAILURE);
			file.addError();
			return;
		}
		// do the select
		try {
			WebElement webElement = getWebElement();
			Select dropdown = new Select(webElement);
			dropdown.selectByVisibleText(value);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Selected " + value + " in " + prettyOutput(), Result.SUCCESS);
	}

	/**
	 * A private method to throw an error if moving to an element was
	 * unsuccessful
	 * 
	 * @param e
	 *            - the exception that was thrown
	 * @param action
	 *            - what is the action occurring
	 * @param expected
	 *            - what is the expected outcome of said action
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	private void cantMove(Exception e, String action, String expected) {
		log.error(e);
		file.recordAction(action, expected, CANTMOVE + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
		file.addError();
	}

	/**
	 * A private method to determine if the element moved towards is now
	 * currently visible on the screen
	 * 
	 * @param action
	 *            - what is the action occurring
	 * @param expected
	 *            - what is the expected outcome of said action
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	private void isMoved(String action, String expected) {
		if (!is.displayed()) {
			file.recordAction(action, expected, prettyOutput() + " is not present on visible page", Result.FAILURE);
			file.addError();
			return; // indicates element not visible
		}
		file.recordAction(action, expected, prettyOutput() + " is present on visible page", Result.SUCCESS);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void move() {
		String action = "Moving screen to " + prettyOutput();
		String expected = prettyOutput() + " is now present on the visible page";
		// wait for element to be present
		if (!isPresent(action, expected, CANTMOVE)) {
			return;
		}
		try {
			WebElement webElement = getWebElement();
			Actions builder = new Actions(driver);
			builder.moveToElement(webElement);
		} catch (Exception e) {
			cantMove(e, action, expected);
			return;
		}
		isMoved(action, expected);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param position
	 *            - how many pixels above the element to scroll to
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void move(long position) {
		String action = "Moving screen to " + position + " pixels above " + prettyOutput();
		String expected = prettyOutput() + " is now present on the visible page";
		// wait for element to be present
		if (!isPresent(action, expected, CANTMOVE)) {
			return;
		}

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement webElement = getWebElement();
			long elementPosition = webElement.getLocation().getY();
			long newPosition = elementPosition - position;
			jse.executeScript("window.scrollBy(0, " + newPosition + ")");
		} catch (Exception e) {
			cantMove(e, action, expected);
			return;
		}
		isMoved(action, expected);
	}

	/**
	 * a function to switch to a frame using the element
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public void selectFrame() {
		String cantSelect = "Unable to focus on frame ";
		String action = "Focusing on frame " + prettyOutput();
		String expected = "Frame " + prettyOutput() + " is present, displayed, and focused";
		// wait for element to be present
		if (!isPresent(action, expected, cantSelect)) {
			return;
		}
		// wait for element to be displayed
		if (!isDisplayed(action, expected, cantSelect)) {
			return;
		}
		WebElement webElement = getWebElement();
		try {
			driver.switchTo().frame(webElement);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
			file.addError();
			return;
		}
		file.recordAction(action, expected, "Focused on frame " + prettyOutput(), Result.SUCCESS);
	}
}