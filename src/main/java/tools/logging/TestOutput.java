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

package tools.logging;

import selenified.exceptions.InvalidActionException;
import selenified.exceptions.InvalidLocatorTypeException;
import tools.General;
import tools.selenium.SeleniumHelper;
import tools.selenium.SeleniumHelper.Browsers;
import tools.selenium.SeleniumHelper.Locators;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test Output A custom generated output file outputFile.recording all actions taken
 *
 * @author Max Saperstone
 * @version 1.0.4
 * @lastupdate 8/29/2016
 */
public class TestOutput {

    private SeleniumHelper selHelper;
    private OutputFile outputFile;

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * the complex test out constructor taking in all test document information
     *
     * @param testsName       the name of the file we will write out to
     * @param browser         the browser we are running our tests with
     * @param outputDir       the output directory to store the results
     */
    public TestOutput(String testsName, Browsers browser, String outputDir) {
        outputFile = new OutputFile(outputDir, testsName, browser);
    }

    public void setSeleniumHelper(SeleniumHelper thisSelHelper) {
        selHelper = thisSelHelper;
    }
    
    public void setOutputFile(OutputFile thisOutputFile) {
    	outputFile = thisOutputFile;
    }
    
    public OutputFile getOutputFile() {
    	return outputFile;
    }

    /**
     * checks to see if an alert is correct on the page
     *
     * @param expectedAlert the expected text of the alert
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkAlert(String expectedAlert) {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find alert with the text <b>" + expectedAlert + "</b> on the page");
        // check for our object to the visible
        String alert = "";
        boolean isAlertPresent = selHelper.isAlertPresent();
        if (isAlertPresent) {
            alert = selHelper.getAlert();
        }
        if (!isAlertPresent) {
            outputFile.recordActual("No alert is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        Pattern patt = Pattern.compile(expectedAlert);
        Matcher m = patt.matcher(alert);
        boolean isCorrect = false;
        if (expectedAlert.contains("\\")) {
            isCorrect = m.matches();
        } else {
            isCorrect = alert.equals(expectedAlert);
        }
        if (!isCorrect) {
            outputFile.recordActual("An alert with text <b>" + alert + "</b> is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("An alert with text <b>" + alert + "</b> is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an alert is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkAlertPresent() {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find an alert on the page");
        // check for our object to the visible
        String alert = "";
        boolean isAlertPresent = selHelper.isAlertPresent();
        if (isAlertPresent) {
            alert = selHelper.getAlert();
        }
        if (!isAlertPresent) {
            outputFile.recordActual("No alert is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("An alert with text <b>" + alert + "</b> is present on the page", Success.PASS);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // a bunch of methods to positively check for objects using selenium calls
    // ///////////////////////////////////////////////////////////////////////

    /**
     * checks to see if an alert is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkAlertNotPresent() {
        // outputFile.record our action
        outputFile.recordExpected("Expected not to find an alert on the page");
        // check for our object to the visible
        boolean isAlertPresent = selHelper.isAlertPresent();
        if (isAlertPresent) {
            outputFile.recordActual("An alert is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("No alert is present on the page", Success.PASS);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // a bunch of methods to negatively check for objects using selenium calls
    // ///////////////////////////////////////////////////////////////////////

    /**
     * checks to see if a confirmation is correct on the page
     *
     * @param expectedConfirmation the expected text of the confirmation
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkConfirmation(String expectedConfirmation) {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmation + "</b> on the page");
        // check for our object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = selHelper.isConfirmationPresent();
        if (isConfirmationPresent) {
            confirmation = selHelper.getConfirmation();
        }
        if (!isConfirmationPresent) {
            outputFile.recordActual("No confirmation is present on the page", Success.FAIL);
            outputFile.addError();
            outputFile.addError();
            return 1;
        }
        if (!expectedConfirmation.equals(confirmation)) {
            outputFile.recordActual("A confirmation with text <b>" + confirmation + "</b> is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("A confirmation with text <b>" + confirmation + "</b> is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkConfirmationPresent() {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find a confirmation on the page");
        // check for our object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = selHelper.isConfirmationPresent();
        if (isConfirmationPresent) {
            confirmation = selHelper.getConfirmation();
        }
        if (!isConfirmationPresent) {
            outputFile.recordActual("No confirmation is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("A confirmation with text <b>" + confirmation + "</b> is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkConfirmationNotPresent() {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find a confirmation on the page");
        // check for our object to the visible
        boolean isConfirmationPresent = selHelper.isConfirmationPresent();
        if (isConfirmationPresent) {
            outputFile.recordActual("A confirmation is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("No confirmation is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is correct on the page
     *
     * @param expectedPrompt the expected text of the confirmation
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkPrompt(String expectedPrompt) {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find prompt with the text <b>" + expectedPrompt + "</b> on the page");
        // check for our object to the visible
        String prompt = "";
        boolean isPromptPresent = selHelper.isPromptPresent();
        if (isPromptPresent) {
            prompt = selHelper.getPrompt();
        }
        if (!isPromptPresent) {
            outputFile.recordActual("No prompt is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!expectedPrompt.equals(prompt)) {
            outputFile.recordActual("A prompt with text <b>" + prompt + "</b> is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("A prompt with text <b>" + prompt + "</b> is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkPromptPresent() {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find prompt on the page");
        // check for our object to the visible
        String prompt = "";
        boolean isPromptPresent = selHelper.isPromptPresent();
        if (isPromptPresent) {
            prompt = selHelper.getPrompt();
        }
        if (!isPromptPresent) {
            outputFile.recordActual("No prompt is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("A prompt with text <b>" + prompt + "</b> is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkPromptNotPresent() {
        // outputFile.record our action
        outputFile.recordExpected("Expected not to find prompt on the page");
        // check for our object to the visible
        boolean isPromptPresent = selHelper.isPromptPresent();
        if (isPromptPresent) {
            outputFile.recordActual("A prompt is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("No prompt is present on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is correct for the page
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkCookie(String cookieName, String expectedCookieValue) {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find cookie with the name <b>" + cookieName + "</b> and a value of <b>"
                + expectedCookieValue + "</b> stored for the page");
        // check for our object to the visible
        String cookieValue = "";
        boolean isCookiePresent = selHelper.isCookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = selHelper.getCookieValue(cookieName);
        }
        if (!isCookiePresent) {
            outputFile.recordActual("No cookie with the name <b>" + cookieName + "</b> is stored for the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            outputFile.recordActual("A cookie with the name <b>" + cookieName + "</b> is stored for the page, but the value "
                    + "of the cookie is " + cookieValue, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("A cookie with the name <b>" + cookieName + "</b> and a value of <b>" + cookieValue
                + "</b> is stored for the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is present on the page
     *
     * @param expectedCookieName the name of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkCookiePresent(String expectedCookieName) {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + "</b> stored for the page");
        // check for our object to the visible
        String cookieValue = "";
        boolean isCookiePresent = selHelper.isCookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = selHelper.getCookieValue(expectedCookieName);
        }
        if (!isCookiePresent) {
            outputFile.recordActual("No cookie with the name <b>" + expectedCookieName + "</b> is stored for the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("A cookie with the name <b>" + expectedCookieName + "</b> and a value of <b>" + cookieValue
                + "</b> is stored for the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is not present on the page
     *
     * @param unexpectedCookieName the name of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkCookieNotPresent(String unexpectedCookieName) {
        // outputFile.record our action
        outputFile.recordExpected(
                "Expected to find no cookie with the name <b>" + unexpectedCookieName + "</b> stored for the page");
        // check for our object to the visible
        boolean isCookiePresent = selHelper.isCookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            outputFile.recordActual("A cookie with the name <b>" + unexpectedCookieName + "</b> is stored for the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("No cookie with the name <b>" + unexpectedCookieName + "</b> is stored for the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is visible on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDisplayed(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementDisplayed(type, locator) && selHelper.waitForElementDisplayed(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> visible on the page");
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present and visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementNotDisplayed(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (selHelper.isElementDisplayed(type, locator) && selHelper.waitForElementNotDisplayed(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator
                + "</i> present, but not visible on the page");
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present and not visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int checkElementChecked(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> checked on the page");
        // check for our object to the visible
        if (!selHelper.isElementChecked(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is not checked on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is checked on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int checkElementNotChecked(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> unchecked on the page");
        // check for our object to the visible
        if (selHelper.isElementChecked(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is checked on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is unchecked on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDisplayedAndChecked(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementDisplayed(type, locator) && selHelper.waitForElementDisplayed(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> checked on the page");
        // check for our object to the visible
        if (!selHelper.isElementChecked(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is not checked on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is checked and visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDisplayedAndUnchecked(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementDisplayed(type, locator) && selHelper.waitForElementDisplayed(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> unchecked on the page");
        // check for our object to the visible
        if (selHelper.isElementChecked(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is checked on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is unchecked and visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int checkElementEditable(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> editable on the page");
        // check for our object to the editable
        if (!selHelper.isElementInput(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present but not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!selHelper.isElementEnabled(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present but not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present and editable on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int checkElementNotEditable(Locators type, String locator)
            throws InvalidLocatorTypeException, InvalidActionException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> not editable on the page");
        // check for our object to the editable
        boolean isElementEnabled = false;
        boolean isInput = selHelper.isElementInput(type, locator);
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (isElementEnabled) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present but editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is present and not editable on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDisplayedAndEditable(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementDisplayed(type, locator) && selHelper.waitForElementDisplayed(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected(
                "Expected to find element with " + type + " <i>" + locator + "</i> visible and editable on the page");
        // check for our object to the editable
        if (!selHelper.isElementInput(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is visible but not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!selHelper.isElementEnabled(type, locator)) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is visible but not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is visible and editable on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDisplayedAndNotEditable(Locators type, String locator)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementDisplayed(type, locator) && selHelper.waitForElementDisplayed(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator
                + "</i> visible and not editable on the page");
        // check for our object to the editable
        boolean isElementEnabled = false;
        boolean isInput = selHelper.isElementInput(type, locator);
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (isElementEnabled) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is visible but editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is visible and not editable on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type      - the locator type e.g. Locators.id, Locators.xpath
     * @param locator   - the locator string e.g. login, //input[@id='login']
     * @param attribute - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementHasAttribute(Locators type, String locator, String attribute)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with attribute <b>"
                + attribute + "</b>");
        Map<String, String> attributes = selHelper.getAllAttributes(type, locator);
        Set<String> keys = attributes.keySet();
        String[] array = keys.toArray(new String[keys.size()]);
        // outputFile.record our action
        if (General.doesArrayContain(array, attribute)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> contains the attribute of <b>"
                    + attribute + "</b>", Success.PASS);
            return 0;
        }
        outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
                + attribute + "</b>" + ", only the values <b>" + Arrays.toString(array) + "</b>", Success.FAIL);
        outputFile.addError();
        return 1;
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type      - the locator type e.g. Locators.id, Locators.xpath
     * @param locator   - the locator string e.g. login, //input[@id='login']
     * @param attribute - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDoesntHaveAttribute(Locators type, String locator, String attribute)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without attribute <b>"
                + attribute + "</b>");
        Map<String, String> attributes = selHelper.getAllAttributes(type, locator);
        Set<String> keys = attributes.keySet();
        String[] array = keys.toArray(new String[keys.size()]);
        // outputFile.record our action
        if (General.doesArrayContain(array, attribute)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> contains the attribute of <b>"
                    + attribute + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
                + attribute + "</b>" + ", only the values <b>" + Arrays.toString(array) + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedClass - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementHasClass(Locators type, String locator, String expectedClass)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with class <b>"
                + expectedClass + "</b>");
        String actualClass = selHelper.getAttribute(type, locator, "class");
        // outputFile.record our action
        if (actualClass.equals(expectedClass)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has a class value of <b>"
                    + expectedClass + "</b>", Success.PASS);
            return 0;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass + "</b>",
                Success.FAIL);
        outputFile.addError();
        return 1;
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedClass - the expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementContainsClass(Locators type, String locator, String expectedClass)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> containing class <b>"
                + expectedClass + "</b>");
        String actualClass = selHelper.getAttribute(type, locator, "class");
        // outputFile.record our action
        if (actualClass.contains(expectedClass)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass
                    + "</b>, which contains <b>" + expectedClass + "</b>", Success.PASS);
            return 0;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass + "</b>",
                Success.FAIL);
        outputFile.addError();
        return 1;
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param type            - the locator type e.g. Locators.id, Locators.xpath
     * @param locator         - the locator string e.g. login, //input[@id='login']
     * @param unexpectedClass - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkElementDoesntContainClass(Locators type, String locator, String unexpectedClass)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementPresent(type, locator) && selHelper.waitForElementPresent(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without class <b>"
                + unexpectedClass + "</b>");
        String actualClass = selHelper.getAttribute(type, locator, "class");
        // outputFile.record our action
        if (actualClass.contains(unexpectedClass)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass
                    + "</b>, which contains <b>" + unexpectedClass + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain a class value of <b>"
                + unexpectedClass + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param option  the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkIfOptionInSelect(Locators type, String locator, String option)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementEnabled(type, locator) && selHelper.waitForElementEnabled(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with the option <b>" + option
                + "</b> available to be" + " selected on the page");
        // check for our object to the editable
        String[] allOptions = selHelper.getSelectOptions(type, locator);
        if (!Arrays.asList(allOptions).contains(option)) {
            outputFile.recordActual(
                    "The element with " + type + " <i>" + locator
                            + "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element with " + type + " <i>" + locator
                + "</i> is editable and present and contains the option " + "<b>" + option + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param type    - the locator type e.g. Locators.id, Locators.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param option  the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidLocatorTypeException
     * @throws InvalidActionException
     */
    public int checkIfOptionNotInSelect(Locators type, String locator, String option)
            throws InvalidActionException, InvalidLocatorTypeException {
        // wait for the element
        if (!selHelper.isElementEnabled(type, locator) && selHelper.waitForElementEnabled(type, locator) == 1) {
            outputFile.addError();
            return 1;
        }
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without the option <b>"
                + option + "</b> available to be" + " selected on the page");
        // check for our object to the editable
        String[] allOptions = selHelper.getSelectedValues(type, locator);
        if (Arrays.asList(allOptions).contains(option)) {
            outputFile.recordActual(
                    "The element with " + type + " <i>" + locator
                            + "</i> is editable and present and contains the option " + "<b>" + option + "</b>",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element with " + type + " <i>" + locator
                        + "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if text is visible on the page
     *
     * @param expectedTexts the expected text to be visible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkTextVisible(String... expectedTexts) {
        // outputFile.record our action
        int errors = 0;
        for (String expectedText : expectedTexts) {
            outputFile.recordExpected("Expected to find text <b>" + expectedText + "</b> visible on the page");
            // check for our object to the visible
            boolean isPresent = selHelper.isTextPresent(expectedText);
            if (!isPresent) {
                outputFile.recordActual("The text <b>" + expectedText + "</b> is not visible on the page", Success.FAIL);
                outputFile.addError();
                errors++;
            } else {
                outputFile.recordActual("The text <b>" + expectedText + "</b> is visible on the page", Success.PASS);
            }
        }
        return errors;
    }

    /**
     * checks to see if text is not visible on the page
     *
     * @param expectedTexts the expected text to be invisible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkTextNotVisible(String... expectedTexts) {
        // outputFile.record our action
        int errors = 0;
        for (String expectedText : expectedTexts) {
            outputFile.recordExpected("Expected not to find text <b>" + expectedText + "</b> visible on the page");
            // check for our object to the visible
            boolean isPresent = selHelper.isTextPresent(expectedText);
            if (isPresent) {
                outputFile.recordActual("The text <b>" + expectedText + "</b> is visible on the page", Success.FAIL);
                outputFile.addError();
                errors++;
            } else {
                outputFile.recordActual("The text <b>" + expectedText + "</b> is not visible on the page", Success.PASS);
            }
        }
        return errors;
    }

    /**
     * checks to see if text is visible on the page
     *
     * @param expectedTexts the expected text to be visible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkTextVisibleOR(String... expectedTexts) {
        // outputFile.record our action
        int errors = 0;
        boolean isPresent = false;
        String foundText = "";
        String allTexts = "";
        for (String expectedText : expectedTexts) {
            allTexts += "<b>" + expectedText + "</b> or ";
        }
        allTexts = allTexts.substring(0, allTexts.length() - 4);
        outputFile.recordExpected("Expected to find text " + allTexts + " visible on the page");
        // check for our object to the visible
        for (String expectedText : expectedTexts) {
            isPresent = selHelper.isTextPresent(expectedText);
            if (isPresent) {
                foundText = expectedText;
                break;
            }
        }
        if (!isPresent) {
            outputFile.recordActual("None of the texts " + allTexts.replace(" or ", ", ") + " are visible on the page",
                    Success.FAIL);
            outputFile.addError();
            errors++;
            return errors;
        }
        outputFile.recordActual("The text <b>" + foundText + "</b> is visible on the page", Success.PASS);
        return errors;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedValue the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareTextValue(Locators type, String locator, String expectedValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a value of <b>"
                + expectedValue + "</b>");
        // check for our object to the present on the page
        String elementValue = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        if (isPresent) {
            elementValue = selHelper.getText(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!elementValue.equals(expectedValue)) {
            outputFile.recordActual(
                    "The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedValue the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareTextValueContains(Locators type, String locator, String expectedValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> contains the value of <b>"
                + expectedValue + "</b>");
        // check for our object to the present on the page
        String elementValue = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        if (isPresent) {
            elementValue = selHelper.getText(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!elementValue.contains(expectedValue)) {
            outputFile.recordActual(
                    "The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the actual URL a page is on to the expected URL
     *
     * @param expectedTitle the friendly name of the page
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTitle(String expectedTitle) {
        // outputFile.record our action
        outputFile.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
        String actualTitle = selHelper.getTitle();
        if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
            outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedValue the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareInputValue(Locators type, String locator, String expectedValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a value of <b>"
                + expectedValue + "</b>");
        // check for our object to the present on the page
        String elementValue = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        if (isPresent) {
            elementValue = selHelper.getValue(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!elementValue.equals(expectedValue)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue
                    + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareCssValue(Locators type, String locator, String attribute, String expectedValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a css attribute of <i>"
                + attribute + "</i> with a value of <b>" + expectedValue + "</b>");
        // check for our object to the present on the page
        String elementCssValue = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        if (isPresent) {
            elementCssValue = selHelper.getCss(type, locator, attribute);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!elementCssValue.equals(expectedValue)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
                    + "</i> with the value of <b>" + elementCssValue + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
                + "</i> with the value of <b>" + elementCssValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element select value exists
     *
     * @param type        - the locator type e.g. Locators.id, Locators.xpath
     * @param locator     - the locator string e.g. login, //input[@id='login']
     * @param selectValue the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int checkSelectValuePresent(Locators type, String locator, String selectValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a select value of <b>"
                + selectValue + "</b>");
        // check for our object to the present on the page
        String[] elementValues = null;
        boolean isPresent = selHelper.isElementPresent(type, locator);
        boolean isInput = false;
        boolean isElementEnabled = false;
        if (isPresent) {
            isInput = selHelper.isElementInput(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (!isInput) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isElementEnabled) {
            elementValues = selHelper.getSelectedValues(type, locator);
        }
        if (!isElementEnabled) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (General.doesArrayContain(elementValues, selectValue)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> contains the value of <b>" + selectValue
                    + "</b>", Success.PASS);
            return 0;
        }
        outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the value of <b>"
                        + selectValue + "</b>" + ", only the values <b>" + Arrays.toString(elementValues) + "</b>",
                Success.FAIL);
        outputFile.addError();
        return 1;
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param type        - the locator type e.g. Locators.id, Locators.xpath
     * @param locator     - the locator string e.g. login, //input[@id='login']
     * @param selectValue the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int checkSelectValueNotPresent(Locators type, String locator, String selectValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without a select value of <b>"
                + selectValue + "</b>");
        // check for our object to the present on the page
        String[] elementValues = null;
        boolean isPresent = selHelper.isElementPresent(type, locator);
        boolean isInput = false;
        boolean isElementEnabled = false;
        if (isPresent) {
            isInput = selHelper.isElementInput(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (!isInput) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isElementEnabled) {
            elementValues = selHelper.getSelectedValues(type, locator);
        }
        if (!isElementEnabled) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (General.doesArrayContain(elementValues, selectValue)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> contains the value of <b>" + selectValue
                    + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the value of <b>"
                + selectValue + "</b>, only the values <b>" + Arrays.toString(elementValues) + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedValue the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareSelectedValue(Locators type, String locator, String expectedValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator
                + "</i> having a selected value of <b>" + expectedValue + "</b>");
        // check for our object to the present on the page
        String elementValue = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        boolean isInput = false;
        boolean isElementEnabled = false;
        if (isPresent) {
            isInput = selHelper.isElementInput(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (!isInput) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isElementEnabled) {
            elementValue = selHelper.getSelectedValue(type, locator);
        }
        if (!isElementEnabled) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!elementValue.equals(expectedValue)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue
                    + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param type         - the locator type e.g. Locators.id, Locators.xpath
     * @param locator      - the locator string e.g. login, //input[@id='login']
     * @param expectedText the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareSelectedText(Locators type, String locator, String expectedText)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a selected text of <b>"
                + expectedText + "</b>");
        // check for our object to the present on the page
        String elementText = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        boolean isInput = false;
        boolean isElementEnabled = false;
        if (isPresent) {
            isInput = selHelper.isElementInput(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (!isInput) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isElementEnabled) {
            elementText = selHelper.getSelectedText(type, locator);
        }
        if (!isElementEnabled) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!elementText.equals(expectedText)) {
            outputFile.recordActual(
                    "The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementText + "</b>",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementText + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type          - the locator type e.g. Locators.id, Locators.xpath
     * @param locator       - the locator string e.g. login, //input[@id='login']
     * @param expectedValue the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareSelectedValueNotEqual(Locators type, String locator, String expectedValue)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator
                + "</i> not having a selected value of <b>" + expectedValue + "</b>");
        // check for our object to the present on the page
        String elementValue = "";
        boolean isPresent = selHelper.isElementPresent(type, locator);
        boolean isInput = false;
        boolean isElementEnabled = false;
        if (isPresent) {
            isInput = selHelper.isElementInput(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (!isInput) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isElementEnabled) {
            elementValue = selHelper.getSelectedValue(type, locator);
        }
        if (!isElementEnabled) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (elementValue.equals(expectedValue)) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue
                    + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type           - the locator type e.g. Locators.id, Locators.xpath
     * @param locator        - the locator string e.g. login, //input[@id='login']
     * @param expectedValues the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws InvalidActionException
     * @throws InvalidLocatorTypeException
     */
    public int compareSelectValues(Locators type, String locator, String... expectedValues)
            throws InvalidLocatorTypeException, InvalidActionException {
        // outputFile.record our action
        int errors = 0;
        outputFile.recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with select values of <b>"
                + expectedValues + "</b>");
        // check for our object to the present on the page
        String[] elementValues = null;
        boolean isPresent = selHelper.isElementPresent(type, locator);
        boolean isInput = false;
        boolean isElementEnabled = false;
        if (isPresent) {
            isInput = selHelper.isElementInput(type, locator);
        }
        if (!isPresent) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isInput) {
            isElementEnabled = selHelper.isElementEnabled(type, locator);
        }
        if (!isInput) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (isElementEnabled) {
            elementValues = selHelper.getSelectOptions(type, locator);
        }
        if (!isElementEnabled) {
            outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
                    Success.FAIL);
            outputFile.addError();
            return 1;
        }
        for (String entry : expectedValues) {
            if (!Arrays.asList(elementValues).contains(entry)) {
                outputFile.recordActual("The element  with " + type + " <i>" + locator
                        + "</i> does not have the select value of <b>" + entry + "</b>", Success.FAIL);
                outputFile.addError();
                errors++;
            }
        }
        for (String entry : elementValues) {
            if (!Arrays.asList(expectedValues).contains(entry)) {
                outputFile.recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + entry
                        + "</b> which was not expected", Success.FAIL);
                outputFile.addError();
                errors++;
            }
        }
        if (errors > 0) {
            return errors;
        }
        outputFile.recordActual(
                "The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValues + "</b>",
                Success.PASS);
        return 0;
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    // this enum will be for a pass/fail
    public enum Success {
        PASS, FAIL
    }

    public enum Result {
        WARNING, SUCCESS, FAILURE, SKIPPED
    }
}