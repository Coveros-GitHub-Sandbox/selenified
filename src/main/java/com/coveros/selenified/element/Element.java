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

package com.coveros.selenified.element;

import com.coveros.selenified.Locator;
import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Result;
import com.coveros.selenified.exceptions.InvalidLocatorTypeException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Element an object representative of a web element on a particular page that
 * is under test.
 * <p>
 * Elements should be directly interacted with, with actions performed on them,
 * and assertions make about their current state
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Element {

    private static final Logger log = Logger.getLogger(Element.class);

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
    private static final String INN = "</b> in ";
    private static final String TYPTED = "Typed text '";

    private static final String NOTPRESENT = " as it is not present";
    private static final String NOTDISPLAYED = " as it is not displayed";
    private static final String NOTENABLED = " as it is not enabled";
    private static final String NOTINPUT = " as it is not an input";
    private static final String NOTSELECT = " as it is not a select";

    private static final String CANTTYPE = "Unable to type in ";
    private static final String CANTMOVE = "Unable to move to ";
    private static final String CANTSELECT = "Unable to select ";

    private static final String SELECTING = "Selecting ";
    private static final String SELECTED = " selected";
    private static final String PRESDISEN = " is present, displayed, and enabled to have the value ";

    /**
     * Sets up the element object. Driver, and Output are defined here, which
     * will control actions and all logging and records. Additionally,
     * information about the element, the locator type, and the actual selector
     * are defined to indicate which element to interact with on the current
     * page
     *
     * @param driver  - the selenium web driver, the underlying way all actions and
     *                assertions are controlled
     * @param file    - the TestOutput file. This is provided by the
     *                SeleniumTestBase functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     */
    public Element(WebDriver driver, OutputFile file, Locator type, String locator) {
        this.type = type;
        this.locator = locator;
        init(driver, file);
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
     * @param file    - the TestOutput file. This is provided by the
     *                SeleniumTestBase functionality
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     */
    public Element(WebDriver driver, OutputFile file, Locator type, String locator, int match) {
        this.type = type;
        this.locator = locator;
        this.setMatch(match);
        init(driver, file);
    }

    /**
     * A private method to finish setting up each element
     *
     * @param driver - the selenium web driver, the underlying way all actions and
     *               assertions are controlled
     * @param file   - the TestOutput file. This is provided by the
     *               SeleniumTestBase functionality
     */
    private void init(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.file = file;

        is = new Is(this);
        waitFor = new WaitFor(this, file);
        get = new Get(driver, this);
        state = new State(this, file);
        contains = new Contains(this, file);
        excludes = new Excludes(this, file);
        equals = new Equals(this, file);
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
        return "Element with <i>" + type.toString() + "</i> of <i>" + locator + "</i>";
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
     * Performs dyanamic waits on a particular element, until a particular
     * condition is met. Nothing is ever returned. The default wait is 5
     * seconds, but can be overridden. If the condition is not met in the
     * allotted time, still nothing is returned, but an error is logged
     */
    public WaitFor waitFor() {
        return waitFor;
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
    public State assertState() {
        return state;
    }

    /**
     * Verifies that the element has a particular value contained within it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Contains assertContains() {
        return contains;
    }

    /**
     * Verifies that the element doesn't have a particular value contained
     * within it. These asserts are custom to the framework, and in addition to
     * providing easy object oriented capabilities, they take screenshots with
     * each verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Excludes assertExcludes() {
        return excludes;
    }

    /**
     * Verifies that the element has a particular value associated with it.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Equals assertEquals() {
        return equals;
    }

    //////////////////////////////////////////////////////
    // setup element values
    //////////////////////////////////////////////////////

    /**
     * Determines Selenium's 'By' object using Webdriver
     *
     * @return By: the Selenium object
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
        try {
            return driver.findElement(defineByElement());
        } catch (InvalidLocatorTypeException e) {
            log.warn(e);
            return null;
        }
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
        try {
            return driver.findElements(defineByElement());
        } catch (InvalidLocatorTypeException e) {
            log.warn(e);
            return new ArrayList<>();
        }
    }

    /**
     * Searches for a child element within the element, and creates and returns
     * this new child element
     *
     * @param child - the child element to search for within the element
     * @return Element: the full reference to the child element element
     */
    public Element findChild(Element child) {
        try {
            WebElement webElement = getWebElement();
            WebElement childElement = webElement.findElement(child.defineByElement());
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String xPath = (String) js.executeScript(
                    "gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();",
                    childElement);
            return new Element(driver, file, Locator.XPATH, xPath);
        } catch (InvalidLocatorTypeException e) {
            log.warn(e);
            return null;
        }
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
     * Determines if the element is displayed. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be displayed
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
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
     * Determines if the element is displayed. If it isn't, it'll wait up to the
     * default time (5 seconds) for the element to be displayed
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
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
     * Determines if the element is an input.
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
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
     * Determines if the element is a select.
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element enabled?
     */
    private boolean isSelect(String action, String expected, String extra) {
        // wait for element to be displayed
        if (!is.select()) {
            file.recordAction(action, expected, extra + prettyOutput() + NOTSELECT, Result.FAILURE);
            file.addError();
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
     * Determines if something is present, enabled, and an input. This returns
     * true if all three are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
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
     * Determines if something is present, displayed, enabled, and an input.
     * This returns true if all four are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present, displayed, enabled, and an
     * input?
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

    /**
     * Determines if something is present, displayed, enabled, and a select.
     * This returns true if all four are true, otherwise, it returns false
     *
     * @param action   - what action is occurring
     * @param expected - what is the expected result
     * @param extra    - what actually is occurring
     * @return Boolean: is the element present, displayed, enabled, and an
     * input?
     */
    private boolean isPresentDisplayedEnabledSelect(String action, String expected, String extra) {
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
        return isSelect(action, expected, extra);
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
            if (!isPresentDisplayedEnabled(action, expected, cantClick)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.click();
        } catch (Exception e) {
            file.recordAction(action, expected, cantClick + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.warn(e);
            return;
        }
        file.recordAction(action, expected, "Clicked " + prettyOutputEnd(), Result.SUCCESS);
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
            if (!isPresentDisplayedEnabled(action, expected, cantSubmit)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.submit();
        } catch (Exception e) {
            file.recordAction(action, expected, cantSubmit + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.warn(e);
            return;
        }
        file.recordAction(action, expected, "Submitted " + prettyOutputEnd(), Result.SUCCESS);
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
            if (!isPresent(action, expected, cantHover)) {
                return;
            }
            // wait for element to be displayed
            if (!isDisplayed(action, expected, cantHover)) {
                return;
            }
            Actions selAction = new Actions(driver);
            WebElement webElement = getWebElement();
            selAction.moveToElement(webElement).perform();
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, cantHover + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Hovered over " + prettyOutputEnd(), Result.SUCCESS);
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
            if (!isPresentDisplayedEnabledInput(action, expected, cantFocus)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.sendKeys(Keys.TAB);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, cantFocus + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Focused, then unfocused (blurred) on " + prettyOutputEnd(),
                Result.SUCCESS);
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
        Boolean warning = false;
        try {
            if (!isPresentEnabledInput(action, expected, CANTTYPE)) {
                return;
            }
            if (!is.displayed()) {
                warning = true;
            }
            WebElement webElement = getWebElement();
            webElement.sendKeys(text);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, CANTTYPE + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        if (warning) {
            file.recordAction(action, expected, TYPTED + text + IN + prettyOutput() +
                    ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
        } else {
            file.recordAction(action, expected, TYPTED + text + IN + prettyOutputEnd(), Result.SUCCESS);
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
        Boolean warning = false;
        try {
            if (!isPresentEnabledInput(action, expected, CANTTYPE)) {
                return;
            }
            if (!is.displayed()) {
                warning = true;
            }
            WebElement webElement = getWebElement();
            webElement.sendKeys(key);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, CANTTYPE + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        if (warning) {
            file.recordAction(action, expected, TYPTED + key + IN + prettyOutput() +
                    ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
        } else {
            file.recordAction(action, expected, TYPTED + key + IN + prettyOutputEnd(), Result.SUCCESS);
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
            if (!isPresentDisplayedEnabledInput(action, expected, cantClear)) {
                return;
            }
            WebElement webElement = getWebElement();
            webElement.clear();
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, cantClear + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Cleared text in " + prettyOutputEnd(), Result.SUCCESS);
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
        String expected = prettyOutput() + PRESDISEN + index + SELECTED;
        try {
            if (!isPresentDisplayedEnabledSelect(action, expected, CANTSELECT)) {
                return;
            }
            String[] options = get.selectOptions();
            if (index > options.length) {
                file.recordAction(action, expected,
                        "Unable to select the <i>" + index + "</i> option, as there are only <i>" + options.length +
                                "</i> available.", Result.FAILURE);
                file.addError();
                return;
            }
            // do the select
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, CANTSELECT + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Selected option <b>" + index + INN + prettyOutputEnd(), Result.SUCCESS);
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
        String expected = prettyOutput() + PRESDISEN + option + SELECTED;
        try {
            if (!isPresentDisplayedEnabledSelect(action, expected, CANTSELECT)) {
                return;
            }
            // ensure the option exists
            if (!Arrays.asList(get.selectOptions()).contains(option)) {
                file.recordAction(action, expected, CANTSELECT + option + " in " + prettyOutput() +
                        " as that option isn't present. Available options are:<i><br/>" + "&nbsp;&nbsp;&nbsp;" +
                        String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectOptions()) + "</i>", Result.FAILURE);
                file.addError();
                return;
            }
            // do the select
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByVisibleText(option);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, CANTSELECT + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Selected <b>" + option + INN + prettyOutputEnd(), Result.SUCCESS);
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
        String expected = prettyOutput() + PRESDISEN + value + SELECTED;
        try {
            if (!isPresentDisplayedEnabledSelect(action, expected, CANTSELECT)) {
                return;
            }
            // ensure the value exists
            if (!Arrays.asList(get.selectValues()).contains(value)) {
                file.recordAction(action, expected, CANTSELECT + value + " in " + prettyOutput() +
                        " as that value isn't present. Available values are:<i><br/>" + "&nbsp;&nbsp;&nbsp;" +
                        String.join("<br/>&nbsp;&nbsp;&nbsp;", get.selectValues()) + "</i>", Result.FAILURE);
                file.addError();
                return;
            }
            // do the select
            WebElement webElement = getWebElement();
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, CANTSELECT + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Selected <b>" + value + INN + prettyOutputEnd(), Result.SUCCESS);
    }

    /**
     * Generates and logs an error (with a screenshot), stating that the element
     * was unable to me moved to
     *
     * @param e        - the exception that was thrown
     * @param action   - what is the action occurring
     * @param expected - what is the expected outcome of said action
     */
    private void cantMove(Exception e, String action, String expected) {
        log.warn(e);
        file.recordAction(action, expected, CANTMOVE + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
        file.addError();
    }

    /**
     * Determines if the element moved towards is now currently displayed on the
     * screen
     *
     * @param action   - what is the action occurring
     * @param expected - what is the expected outcome of said action
     */
    private void isMoved(String action, String expected) {
        if (!is.displayed()) {
            file.recordAction(action, expected, prettyOutputStart() + " is not displayed within the current viewport",
                    Result.FAILURE);
            file.addError();
            return; // indicates element not on displayed screen
        }
        file.recordAction(action, expected, prettyOutputStart() + " is displayed within the current viewport",
                Result.SUCCESS);
    }

    /**
     * Scrolls the page to the element, making it displayed on the current
     * viewport, but only if the element is present. If that condition is not
     * met, the move action will be logged, but skipped and the test will
     * continue.
     */
    public void move() {
        String action = "Moving screen to " + prettyOutput();
        String expected = prettyOutput() + " is now displayed within the current viewport";
        try {
            // wait for element to be present
            if (!isPresent(action, expected, CANTMOVE)) {
                return;
            }
            // perform the move action
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
     * Scrolls the page to the element, leaving X pixels at the top of the
     * viewport above it, making it displayed on the current viewport, but only
     * if the element is present. If that condition is not met, the move action
     * will be logged, but skipped and the test will continue.
     *
     * @param position - how many pixels above the element to scroll to
     */
    public void move(long position) {
        String action = "Moving screen to " + position + " pixels above " + prettyOutput();
        String expected = prettyOutput() + " is now displayed within the current viewport";
        try {
            // wait for element to be present
            if (!isPresent(action, expected, CANTMOVE)) {
                return;
            }
            // perform the move action
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
            if (!isPresent(action, expected, cantSelect)) {
                return;
            }
            // wait for element to be displayed
            if (!isDisplayed(action, expected, cantSelect)) {
                return;
            }
            // select the actual frame
            WebElement webElement = getWebElement();
            driver.switchTo().frame(webElement);
        } catch (Exception e) {
            log.warn(e);
            file.recordAction(action, expected, cantSelect + prettyOutput() + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Focused on frame " + prettyOutputEnd(), Result.SUCCESS);
    }
}