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

package com.coveros.selenified.element;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.check.*;
import com.coveros.selenified.element.check.azzert.*;
import com.coveros.selenified.element.check.verify.*;
import com.coveros.selenified.element.check.wait.WaitForEquals;
import com.coveros.selenified.element.check.wait.WaitForState;
import com.coveros.selenified.utilities.Point;
import com.coveros.selenified.utilities.Reporter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Element an object representative of a web element on a particular page that
 * is under test.
 *
 * Elements should be directly interacted with, with actions performed on them,
 * and assertions make about their current state
 *
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 3/7/2019
 */
public class Element {

    private static final Logger log = Logger.getLogger(Element.class);

    private final Locator type;
    private final String locator;
    private int match = 0;

    // is there a parent element
    private Element parent = null;

    // this will be the name of the file we write all commands out to
    private Reporter reporter;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // the is class to determine if something exists
    private Is is;
    // the wait class to retrieve information about the element
    private Get get;
    // the is class to determine the state of an element
    private State verifyState;
    private State assertState;
    private WaitForState waitForState;
    // the is class to determine if an element contains something
    private Contains verifyContains;
    private Contains assertContains;
    // the is class to determine if an element doesn't contain something
    private Excludes verifyExcludes;
    private Excludes assertExcludes;
    // the is class to determine if an element has attributes equal to something
    private Equals verifyEquals;
    private Equals assertEquals;
    private WaitForEquals waitForEquals;
    // the is class to determine if an element has attributes matching to something
    private Matches verifyMatches;
    private Matches assertMatches;


    // constants
    private static final String IN = "' in ";
    private static final String INN = "</b> in ";
    private static final String TYPED = "Typed text '";

    private static final String NOT_PRESENT = " as it is not present";
    private static final String NOT_DISPLAYED = " as it is not displayed";
    private static final String NOT_ENABLED = " as it is not enabled";
    private static final String NOT_AN_INPUT = " as it is not an input";
    private static final String NOT_A_SELECT = " as it is not a select";

    private static final String CANT_TYPE = "Unable to type in ";
    private static final String CANT_SCROLL = "Unable to scroll to ";
    private static final String CANT_SELECT = "Unable to select ";

    private static final String SELECTING = "Selecting ";
    private static final String SELECTED = " selected";
    private static final String PRESENT_DISPLAYED_AND_ENABLED = " is present, displayed, and enabled to have the value ";

    /**
     * Sets up the element object. Driver, and Output are defined here, which
     * will control actions and all logging and records. Additionally,
     * information about the element, the locator type, and the actual selector
     * are defined to indicate which element to interact with on the current
     * page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param reporter    - the TestOutput file. This is provided by the
     *                SeleniumTestBase functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     */
    public Element(WebDriver driver, Reporter reporter, Locator type, String locator) {
        this.type = type;
        this.locator = locator;
        init(driver, reporter);
    }

    /**
     * Sets up the element object. Driver, and Output are defined here, which
     * will control actions and all logging and records. Additionally,
     * information about the element, the locator type, and the actual selector
     * are defined to indicate which element to interact with on the current
     * page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param reporter    - the TestOutput file. This is provided by the
     *                SeleniumTestBase functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param parent  - the parent element to the searched for element
     */
    public Element(WebDriver driver, Reporter reporter, Locator type, String locator, Element parent) {
        this.type = type;
        this.locator = locator;
        this.parent = parent;
        init(driver, reporter);
    }

    /**
     * Sets up the element object. Driver, and Output are defined here, which
     * will control actions and all logging and records. Additionally,
     * information about the element, the locator type, the actual selector, and
     * the element's uniqueness match are defined to indicate which element to
     * interact with on the current page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param reporter    - the TestOutput file. This is provided by the
     *                SeleniumTestBase functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     */
    public Element(WebDriver driver, Reporter reporter, Locator type, String locator, int match) {
        this.type = type;
        this.locator = locator;
        this.setMatch(match);
        init(driver, reporter);
    }

    /**
     * Sets up the element object. Driver, and Output are defined here, which
     * will control actions and all logging and records. Additionally,
     * information about the element, the locator type, the actual selector, and
     * the element's uniqueness match are defined to indicate which element to
     * interact with on the current page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param reporter    - the TestOutput file. This is provided by the
     *                SeleniumTestBase functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     * @param parent  - the parent element to the searched for element
     */
    public Element(WebDriver driver, Reporter reporter, Locator type, String locator, int match, Element parent) {
        this.type = type;
        this.locator = locator;
        this.setMatch(match);
        this.parent = parent;
        init(driver, reporter);
    }

    /**
     * A private method to finish setting up each element
     *
     * @param driver - the selenium web driver, the underlying way all actions and
     *               assertions are controlled
     * @param reporter   - the TestOutput file. This is provided by the
     *               SeleniumTestBase functionality
     */
    private void init(WebDriver driver, Reporter reporter) {
        this.driver = driver;
        this.reporter = reporter;

        App app = null;
        if (reporter != null) {
            app = reporter.getApp();
        }

        is = new Is(this);
        get = new Get(app, driver, this);
        verifyState = new VerifyState(this, reporter);
        assertState = new AssertState(this, reporter);
        waitForState = new WaitForState(this, reporter);
        verifyContains = new VerifyContains(this, reporter);
        assertContains = new AssertContains(this, reporter);
        verifyExcludes = new VerifyExcludes(this, reporter);
        assertExcludes = new AssertExcludes(this, reporter);
        verifyEquals = new VerifyEquals(this, reporter);
        assertEquals = new AssertEquals(this, reporter);
        waitForEquals = new WaitForEquals(this, reporter);
        verifyMatches = new VerifyMatches(this, reporter);
        assertMatches = new AssertMatches(this, reporter);
    }

    /**
     * Indicates the uniqueness match of the element to interact with. By
     * default this is set to 0, meaning the first element matching the locator
     * and selector assumed
     *
     * @param match - if there are multiple matches of the selector, this is which
     *              match (starting at 0) to interact with
     */
    public void setMatch(int match) {
        this.match = match;
    }

    public Element get(int match) {
        setMatch(match);
        return this;
    }

    /**
     * Retrieves the Locator set for this element
     *
     * @return Locator: the locator for the element
     */
    public Locator getType() {
        return type;
    }

    /**
     * Retrieves the selector set for this element
     *
     * @return String: the selector for the element
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Retrieves the uniqueness set for this element
     *
     * @return Integer: the uniqueness match for the element
     */
    public int getMatch() {
        return match;
    }

    /**
     * Retrieves the Selenium driver instance
     *
     * @return WebDriver: access to the driver controlling the browser via
     * webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, which should be used at the beginning of a sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutputStart() {
        return prettyOutputStart("Element with ");
    }

    /**
     * Builds the nicely HTML formatted output of the element by retrieving parent
     * element information
     *
     * @param initialString - the starting string, typically describing the element,
     *                      or initial parent element
     * @return String: text identifying how the element was located
     */
    private String prettyOutputStart(String initialString) {
        initialString += "<i>" + type.toString() + "</i> of <i>" + locator + "</i>";
        if (parent != null) {
            initialString = parent.prettyOutputStart(initialString + " and parent of ");
        }
        return initialString;
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, which can be used anywhere in a sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutputLowercase() {
        String output = prettyOutputStart();
        return Character.toLowerCase(output.charAt(0)) + output.substring(1);
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, framed with spaces, which can be used anywhere in a
     * sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutput() {
        return " " + prettyOutputLowercase() + " ";
    }

    /**
     * Retrieves a nicely HTML formatted output which identifies the element by
     * locator and selector, which should be used to end a sentence
     *
     * @return String: text identifying how the element was located
     */
    public String prettyOutputEnd() {
        return prettyOutputLowercase() + ".";
    }

    ///////////////////////////////////////////////////////
    // instantiating our additional action classes for further use
    ///////////////////////////////////////////////////////

    /**
     * Retrieves information about a particular element. A boolean is always
     * returning, indicating if an object is present or not
     */
    public Is is() {
        return is;
    }

    /**
     * Retrieves information about a particular element. If an object isn't
     * present, null will be returned
     */
    public Get get() {
        return get;
    }

    /**
     * Verifies that the element has a particular state associated to it. These
     * asserts are custom to the framework, and in addition to providing easy
     * object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public State verifyState() {
        return verifyState;
    }

    /**
     * Asserts that the element has a particular state associated to it. These
     * asserts are custom to the framework, and in addition to providing easy
     * object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public State assertState() {
        return assertState;
    }

    /**
     * Waits for the element to have a particular state associated to it. These
     * asserts are custom to the framework, and in addition to providing easy
     * object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public WaitForState waitForState() {
        return waitForState;
    }

    /**
     * Verifies that the element has a particular value contained within it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Contains verifyContains() {
        return verifyContains;
    }

    /**
     * Asserts that the element has a particular value contained within it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Contains assertContains() {
        return assertContains;
    }

    /**
     * Verifies that the element doesn't have a particular value contained
     * within it. These asserts are custom to the framework, and in addition to
     * providing easy object oriented capabilities, they take screenshots with
     * each verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Excludes verifyExcludes() {
        return verifyExcludes;
    }

    /**
     * Asserts that the element doesn't have a particular value contained
     * within it. These asserts are custom to the framework, and in addition to
     * providing easy object oriented capabilities, they take screenshots with
     * each verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Excludes assertExcludes() {
        return assertExcludes;
    }

    /**
     * Verifies that the element has a particular value associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Equals verifyEquals() {
        return verifyEquals;
    }

    /**
     * Asserts that the element has a particular value associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Equals assertEquals() {
        return assertEquals;
    }

    /**
     * Wait for the element to have a particular value associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public WaitForEquals waitForEquals() {
        return waitForEquals;
    }

    /**
     * Verifies that the element has a particular value pattern associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Matches verifyMatches() {
        return verifyMatches;
    }

    /**
     * Asserts that the element has a particular value pattern associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Matches assertMatches() {
        return assertMatches;
    }

    //////////////////////////////////////////////////////
    // setup element values
    //////////////////////////////////////////////////////

    /**
     * Determines Selenium's 'By' object using Webdriver
     *
     * @return By: the Selenium object
     */
    public By defineByElement() {
        // consider adding strengthening
        By byElement = null;
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
        }
        return byElement;
    }

    /**
     * Retrieves the identified matching web element using Webdriver. Use this
     * sparingly, only when the action you want to perform on the element isn't
     * available, as commands from it won't be checked, logged, caught, or
     * screenshotted.
     *
     * @return WebElement: the element object, and all associated values with it
     */
    public WebElement getWebElement() {
        List<WebElement> elements = getWebElements();
        if (elements.size() > match) {
            return elements.get(match);
        }
        if (parent != null) {
            return parent.getWebElement().findElement(defineByElement());
        }
        return driver.findElement(defineByElement());
    }

    /**
     * Retrieves all matching web elements using Webdriver. Use this sparingly,
     * only when the action you want to perform on the element isn't available,
     * as commands from it won't be checked, logged, caught, or screenshotted.
     *
     * @return List: a list of WebElement objects, and all associated values
     * with them
     */
    public List<WebElement> getWebElements() {
        if (parent != null) {
            return parent.getWebElement().findElements(defineByElement());
        }
        return driver.findElements(defineByElement());
    }

    /**
     * Searches for a child element within the element, and creates and returns
     * this new child element
     *
     * @param child - the child element to search for within the element
     * @return Element: the full reference to the child element element
     */
    public Element findChild(Element child) {
        return new Element(child.getDriver(), reporter, child.getType(), child.getLocator(), child.getMatch(), this);
    }

    //////////////////////////////////
    // override the SE actions
    //////////////////////////////////

    /**
     * Determines if the element is present. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be present
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present?
     */
    private boolean isNotPresent(String action, String expected, String extra) {
        // wait for element to be present
        if (!is.present()) {
            waitForState.present();
        }
        if (!is.present()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOT_PRESENT);
            // indicates element not present
            return true;
        }
        return false;
    }

    /**
     * Determines if the element is displayed. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be displayed
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element displayed?
     */
    private boolean isNotDisplayed(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.displayed()) {
            waitForState.displayed();
        }
        if (!is.displayed()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOT_DISPLAYED);
            // indicates element not displayed
            return true;
        }
        return false;
    }

    /**
     * Determines if the element is displayed. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be displayed
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    private boolean isNotEnabled(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.enabled()) {
            waitForState.enabled();
        }
        if (!is.enabled()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOT_ENABLED);
            // indicates element not enabled
            return true;
        }
        return false;
    }

    /**
     * Determines if the element is an input.
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    private boolean isNotInput(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.input()) {
            reporter.fail(action, expected, extra + prettyOutput() + NOT_AN_INPUT);
            // indicates element not an input
            return true;
        }
        return false;
    }

    /**
     * Determines if the element is a select.
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @return Boolean: is the element enabled?
     */
    private boolean isSelect(String action, String expected) {
        // wait for element to be displayed
        if (!is.select()) {
            reporter.fail(action, expected, Element.CANT_SELECT + prettyOutput() + NOT_A_SELECT);
            // indicates element not an input
            return false;
        }
        return true;
    }

    /**
     * Determines if something is present, displayed, and enabled. This returns
     * true if all three are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present, displayed, and enabled?
     */
    private boolean isNotPresentDisplayedEnabled(String action, String expected, String extra) {
        // wait for element to be present
        if (isNotPresent(action, expected, extra)) {
            return true;
        }
        // wait for element to be displayed
        if (isNotDisplayed(action, expected, extra)) {
            return true;
        }
        // wait for element to be enabled
        return isNotEnabled(action, expected, extra);
    }

    /**
     * Determines if something is present, enabled, and an input. This returns
     * true if all three are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @return Boolean: is the element present, enabled, and an input?
     */
    private boolean isNotPresentEnabledInput(String action, String expected) {
        // wait for element to be present
        if (isNotPresent(action, expected, Element.CANT_TYPE)) {
            return true;
        }
        // wait for element to be enabled
        return isNotEnabled(action, expected, Element.CANT_TYPE) || isNotInput(action, expected, Element.CANT_TYPE);
    }

    /**
     * Determines if something is present, displayed, enabled, and an input.
     * This returns true if all four are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present, displayed, enabled, and an
     * input?
     */
    private boolean isNotPresentDisplayedEnabledInput(String action, String expected, String extra) {
        // wait for element to be present
        if (isNotPresent(action, expected, extra)) {
            return true;
        }
        // wait for element to be displayed
        if (isNotDisplayed(action, expected, extra)) {
            return true;
        }
        // wait for element to be enabled
        return isNotEnabled(action, expected, extra) || isNotInput(action, expected, extra);
    }

    /**
     * Determines if something is present, displayed, enabled, and a select.
     * This returns true if all four are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @return Boolean: is the element present, displayed, enabled, and an
     * input?
     */
    private boolean isNotPresentDisplayedEnabledSelect(String action, String expected) {
        // wait for element to be present
        if (isNotPresent(action, expected, Element.CANT_SELECT)) {
            return true;
        }
        // wait for element to be displayed
        if (isNotDisplayed(action, expected, Element.CANT_SELECT)) {
            return true;
        }
        // wait for element to be enabled
        return isNotEnabled(action, expected, Element.CANT_SELECT) || !isSelect(action, expected);
    }

    // ///////////////////////////////////
    // basic actions functionality
    // ///////////////////////////////////

    /**
     * Clicks on the element, but only if the element is present, displayed and
     * enabled. If those conditions are not met, the click action will be
     * logged, but skipped and the test will continue.
     */
    public void click() {
        String cantClick = "Unable to click ";
        String action = "Clicking " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to be clicked";
        try {
            if (isNotPresentDisplayedEnabled(action, expected, cantClick)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.click();
        } catch (Exception e) {
            reporter.fail(action, expected, cantClick + prettyOutput() + ". " + e.getMessage());
            log.warn(e);
            return;
        }
        reporter.pass(action, expected, "Clicked " + prettyOutputEnd());
    }

    /**
     * Submits the element, but only if the element is present, displayed and
     * enabled. If those conditions are not met, the submit action will be
     * logged, but skipped and the test will continue.
     */
    public void submit() {
        String cantSubmit = "Unable to submit ";
        String action = "Submitting " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to be submitted    ";
        try {
            if (isNotPresentDisplayedEnabled(action, expected, cantSubmit)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.submit();
        } catch (Exception e) {
            reporter.fail(action, expected, cantSubmit + prettyOutput() + ". " + e.getMessage());
            log.warn(e);
            return;
        }
        reporter.pass(action, expected, "Submitted " + prettyOutputEnd());
    }

    /**
     * Hovers over the element, but only if the element is present and
     * displayed. If those conditions are not met, the hover action will be
     * logged, but skipped and the test will continue.
     */
    public void hover() {
        String cantHover = "Unable to hover over ";
        String action = "Hovering over " + prettyOutput();
        String expected = prettyOutput() + " is present, and displayed to be hovered over";
        try {
            // wait for element to be present
            if (isNotPresent(action, expected, cantHover)) {
                return;
            }
            // wait for element to be displayed
            if (isNotDisplayed(action, expected, cantHover)) {
                return;
            }
            Actions selAction = new Actions(driver);
            WebElement webElement = getWebElement();
            selAction.moveToElement(webElement).perform();
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, cantHover + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Hovered over " + prettyOutputEnd());
    }

    /**
     * Focuses on the element, but only if the element
     * is present, displayed, enabled, and an input. If those conditions are not
     * met, the focus action will be logged, but skipped and the test will
     * continue.
     */
    public void focus() {
        String cantFocus = "Unable to focus on ";
        String action = "Focusing on " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to be focused";
        try {
            if (isNotPresentDisplayedEnabledInput(action, expected, cantFocus)) {
                return;
            }
            WebElement webElement = getWebElement();
            new Actions(driver).moveToElement(webElement).perform();
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, cantFocus + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Focused on " + prettyOutputEnd());
    }

    /**
     * Blurs (focuses and then unfocuses) the element, but only if the element
     * is present, displayed, enabled, and an input. If those conditions are not
     * met, the blur action will be logged, but skipped and the test will
     * continue.
     */
    public void blur() {
        String cantFocus = "Unable to focus on ";
        String action = "Focusing, then unfocusing (blurring) on " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to be blurred";
        try {
            if (isNotPresentDisplayedEnabledInput(action, expected, cantFocus)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.sendKeys(Keys.TAB);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, cantFocus + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Focused, then unfocused (blurred) on " + prettyOutputEnd());
    }

    /**
     * Type the supplied text into the element, but only if the element is
     * present, enabled, and an input. If those conditions are not met, the type
     * action will be logged, but skipped and the test will continue. If the
     * element is not displayed, a warning will be written in the log, to
     * indicate this is not a normal action as could be performed by the user.
     *
     * @param text - the text to be typed in
     */
    public void type(String text) {
        String action = "Typing text '" + text + IN + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to have text " + text + " typed in";
        boolean warning = false;
        try {
            if (isNotPresentEnabledInput(action, expected)) {
                return;
            }
            if (!is.displayed()) {
                warning = true;
            }
            WebElement webElement = getWebElement();
            webElement.sendKeys(text);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, CANT_TYPE + prettyOutput() + ". " + e.getMessage());
            return;
        }
        if (warning) {
            reporter.check(action, expected, TYPED + text + IN + prettyOutput() +
                    ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>");
        } else {
            reporter.pass(action, expected, TYPED + text + IN + prettyOutputEnd());
        }
    }

    /**
     * Type the supplied key into the element, but only if the element is
     * present, enabled, and an input. If those conditions are not met, the type
     * action will be logged, but skipped and the test will continue. If the
     * element is not displayed, a warning will be written in the log, to
     * indicate this is not a normal action as could be performed by the user.
     *
     * @param key - the key to be pressed
     */
    public void type(Keys key) {
        String action = "Typing key '" + key + IN + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to have text " + key + " entered";
        boolean warning = false;
        try {
            if (isNotPresentEnabledInput(action, expected)) {
                return;
            }
            if (!is.displayed()) {
                warning = true;
            }
            WebElement webElement = getWebElement();
            webElement.sendKeys(key);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, CANT_TYPE + prettyOutput() + ". " + e.getMessage());
            return;
        }
        if (warning) {
            reporter.check(action, expected, TYPED + key + IN + prettyOutput() +
                    ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>");
        } else {
            reporter.pass(action, expected, TYPED + key + IN + prettyOutputEnd());
        }
    }

    /**
     * Clears text from the element, but only if the element is present,
     * displayed, enabled, and an input. If those conditions are not met, the
     * clear action will be logged, but skipped and the test will continue.
     */
    public void clear() {
        String cantClear = "Unable to clear ";
        String action = "Clearing text in " + prettyOutput();
        String expected = prettyOutput() + " is present, displayed, and enabled to have text cleared";
        try {
            if (isNotPresentDisplayedEnabledInput(action, expected, cantClear)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.clear();
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, cantClear + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Cleared text in " + prettyOutputEnd());
    }

    /**
     * Selects the Nth option from the element, starting from 0, but only if the
     * element is present, displayed, enabled, and an input. If those conditions
     * are not met, the select action will be logged, but skipped and the test
     * will continue.
     *
     * @param index - the select option to be selected - note, row numbering
     *              starts at 0
     */
    public void select(int index) {
        String action = SELECTING + index + " in " + prettyOutput();
        String expected = prettyOutput() + PRESENT_DISPLAYED_AND_ENABLED + index + SELECTED;
        try {
            if (isNotPresentDisplayedEnabledSelect(action, expected)) {
                return;
            }
            String[] options = get.selectOptions();
            if (index > options.length) {
                reporter.fail(action, expected,
                        "Unable to select the <i>" + index + "</i> option, as there are only <i>" + options.length +
                                "</i> available.");
                return;
            }
            // do the select
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, CANT_SELECT + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Selected option <b>" + index + INN + prettyOutputEnd());
    }

    /**
     * Selects the option from the dropdown matching the provided value, but
     * only if the element is present, displayed, enabled, and an input. If
     * those conditions are not met, the select action will be logged, but
     * skipped and the test will continue.
     *
     * @param option - the select option to be selected
     */
    public void selectOption(String option) {
        String action = SELECTING + option + " in " + prettyOutput();
        String expected = prettyOutput() + PRESENT_DISPLAYED_AND_ENABLED + option + SELECTED;
        try {
            if (isNotPresentDisplayedEnabledSelect(action, expected)) {
                return;
            }
            // ensure the option exists
            if (!Arrays.asList(get.selectOptions()).contains(option)) {
                reporter.fail(action, expected, CANT_SELECT + option + " in " + prettyOutput() +
                        " as that option isn't present. Available options are:<i><br/>&nbsp;&nbsp;&nbsp;" +
                        String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectOptions()) + "</i>");
                return;
            }
            // do the select
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByVisibleText(option);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, CANT_SELECT + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Selected <b>" + option + INN + prettyOutputEnd());
    }

    /**
     * Selects the value from the dropdown matching the provided value, but only
     * if the element is present, displayed, enabled, and an input. If those
     * conditions are not met, the select action will be logged, but skipped and
     * the test will continue.
     *
     * @param value - the select value to be selected
     */
    public void selectValue(String value) {
        String action = SELECTING + value + " in " + prettyOutput();
        String expected = prettyOutput() + PRESENT_DISPLAYED_AND_ENABLED + value + SELECTED;
        try {
            if (isNotPresentDisplayedEnabledSelect(action, expected)) {
                return;
            }
            // ensure the value exists
            if (!Arrays.asList(get.selectValues()).contains(value)) {
                reporter.fail(action, expected, CANT_SELECT + value + " in " + prettyOutput() +
                        " as that value isn't present. Available values are:<i><br/>&nbsp;&nbsp;&nbsp;" +
                        String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectValues()) + "</i>");
                return;
            }
            // do the select
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, CANT_SELECT + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Selected <b>" + value + INN + prettyOutputEnd());
    }

    /**
     * Generates and logs an error (with a screenshot), stating that the element
     * was unable to be scrolled to
     *
     * @param e        - the exception that was thrown
     * @param action   - what is the action occurring
     * @param expected - what is the expected outcome of said action
     */
    private void cantScroll(Exception e, String action, String expected) {
        log.warn(e);
        reporter.fail(action, expected, CANT_SCROLL + prettyOutput() + ". " + e.getMessage());
    }

    /**
     * Determines if the element scrolled towards is now currently displayed on the
     * screen
     *
     * @param action   - what is the action occurring
     * @param expected - what is the expected outcome of said action
     */
    private void isScrolledTo(String action, String expected, long offset) {
        WebElement webElement = getWebElement();
        long elementPosition = webElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int scrollHeight = ((Number) js.executeScript("return document.documentElement.scrollTop || document.body.scrollTop;")).intValue();
        int viewportHeight = ((Number) js.executeScript("return Math.max(document.documentElement.clientHeight, window.innerHeight || 0);")).intValue();

        if (elementPosition < scrollHeight || elementPosition > viewportHeight + scrollHeight) {
            reporter.fail(action, expected, prettyOutputStart() + " was scrolled to, but is not within the current viewport");
        } else {
            reporter.pass(action, expected, prettyOutputStart() + " is properly scrolled to and within the current viewport");
        }
    }

    /**
     * Scrolls the page to the element, making it displayed on the current
     * viewport, but only if the element is present. If that condition is not
     * met, the scroll action will be logged, but skipped and the test will
     * continue.
     */
    public void scrollTo() {
        String action = "Scrolling screen to " + prettyOutput();
        String expected = prettyOutput() + " is now within the current viewport";
        try {
            // wait for element to be present
            if (isNotPresent(action, expected, CANT_SCROLL)) {
                return;
            }
            // perform the move action
            WebElement webElement = getWebElement();
            Actions builder = new Actions(driver);
            builder.moveToElement(webElement);
        } catch (Exception e) {
            cantScroll(e, action, expected);
            return;
        }
        isScrolledTo(action, expected, 0);
    }

    /**
     * Scrolls the page to the element, leaving X pixels at the top of the
     * viewport above it, making it displayed on the current viewport, but only
     * if the element is present. If that condition is not met, the scroll action
     * will be logged, but skipped and the test will continue.
     *
     * @param position - how many pixels above the element to scroll to
     */
    public void scrollTo(long position) {
        String action = "Scrolling screen to " + position + " pixels above " + prettyOutput();
        String expected = prettyOutput() + " is now within the current viewport";
        try {
            // wait for element to be present
            if (isNotPresent(action, expected, CANT_SCROLL)) {
                return;
            }
            // perform the move action
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            WebElement webElement = getWebElement();
            long elementPosition = webElement.getLocation().getY();
            long newPosition = elementPosition - position;
            jse.executeScript("window.scrollBy(0, " + newPosition + ")");
        } catch (Exception e) {
            cantScroll(e, action, expected);
            return;
        }
        isScrolledTo(action, expected, position);
    }

    /**
     * Simulates moving the mouse around while the cursor is pressed. Can be
     * used for drawing on canvases, or swiping on certain elements. Note, this is not supported in HTMLUNIT
     *
     * @param points - a list of points to connect. At least one point must be
     *               provided in the list
     */
    public void draw(List<Point<Integer, Integer>> points) {
        if (points.isEmpty()) {
            reporter.fail("Drawing object in " + prettyOutput(), "Drew object in " + prettyOutput(),
                    "Unable to draw in " + prettyOutput() + " as no points were supplied");
            return;
        }
        StringBuilder pointString = new StringBuilder();
        String prefix = "";
        for (Point<Integer, Integer> point : points) {
            pointString.append(prefix);
            prefix = " to ";
            pointString.append("<i>").append(point.getX()).append("x").append(point.getY()).append("</i>");
        }
        String action = "Drawing object from " + pointString.toString() + " in " + prettyOutput();
        String expected = prettyOutput() + " now has object drawn on it from " + pointString.toString();
        try {
            // wait for element to be present, displayed, and enabled
            if (isNotPresentDisplayedEnabled(action, expected, "Unable to drawn in ")) {
                return;
            }
            WebElement webElement = getWebElement();
            // do our actions
            Actions builder = new Actions(driver);
            Point<Integer, Integer> firstPoint = points.get(0);
            points.remove(0);
            builder.moveToElement(webElement, firstPoint.getX(), firstPoint.getY()).clickAndHold();
            for (Point<Integer, Integer> point : points) {
                builder.moveByOffset(point.getX(), point.getY());
            }
            Action drawAction = builder.release().build();
            drawAction.perform();
        } catch (Exception e) {
            log.error(e);
            reporter.fail(action, expected, "Unable to draw in " + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Drew object in " + prettyOutput() + getScreenshot());
    }

    /**
     * Selects the frame represented by the element, but only if the element is
     * present and displayed. If these conditions are not met, the move action
     * will be logged, but skipped and the test will continue.
     */
    public void selectFrame() {
        String cantSelect = "Unable to focus on frame ";
        String action = "Focusing on frame " + prettyOutput();
        String expected = "Frame " + prettyOutput() + " is present, displayed, and focused";
        try {
            // wait for element to be present
            if (isNotPresent(action, expected, cantSelect)) {
                return;
            }
            // wait for element to be displayed
            if (isNotDisplayed(action, expected, cantSelect)) {
                return;
            }
            // select the actual frame
            WebElement webElement = getWebElement();
            driver.switchTo().frame(webElement);
        } catch (Exception e) {
            log.warn(e);
            reporter.fail(action, expected, cantSelect + prettyOutput() + ". " + e.getMessage());
            return;
        }
        reporter.pass(action, expected, "Focused on frame " + prettyOutputEnd());
    }

    /**
     * Captures an image of the element, and returns the html friendly link of
     * it for use in the logging file. If there is a problem capturing the
     * image, an error message is returned instead.
     *
     * @return String the location of the screenshot
     */
    private String getScreenshot() {
        WebElement webElement = getWebElement();
        String imageLink = "<b><font class='fail'>No Image Preview</font></b>";
        // capture an image of it
        try {
            imageLink = reporter.captureEntirePageScreenshot();
            File image = new File(reporter.getDirectory(), imageLink.split("\"")[1]);
            BufferedImage fullImg = ImageIO.read(image);
            // Get the location of element on the page
            org.openqa.selenium.Point point = webElement.getLocation();
            // Get width and height of the element
            int eleWidth = webElement.getSize().getWidth();
            int eleHeight = webElement.getSize().getHeight();
            // Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", image);
        } catch (WebDriverException | RasterFormatException | IOException e) {
            log.error(e);
        }
        return imageLink;
    }
}