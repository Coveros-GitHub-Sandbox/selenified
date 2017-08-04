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

package com.coveros.selenified.output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.Locator;

/**
 * A custom reporting class, which provides logging and screenshots for all
 * verifications. Meant to provide additional traceability on top of TestNG
 * Asserts
 *
 * @author Max Saperstone
 * @version 2.0.1
 * @lastupdate 7/20/2017
 */
public class Assert {

    private Action action;
    private OutputFile outputFile;

    private LocatorAssert locatorAssert;

    // constants
    private static final String ONPAGE = "</b> on the page";
    private static final String NOALERT = "No alert is present on the page";
    private static final String ALERTTEXT = "An alert with text <b>";
    private static final String NOCONFIRMATION = "No confirmation is present on the page";
    private static final String CONFIRMATIONTEXT = "A confirmation with text <b>";
    private static final String NOPROMPT = "No prompt is present on the page";
    private static final String PROMPTTEXT = "A prompt with text <b>";

    private static final String STORED = "</b> is stored for the page";
    private static final String VALUE = "</b> and a value of <b>";
    private static final String COOKIE = "A cookie with the name <b>";
    private static final String NOCOOKIE = "No cookie with the name <b>";

    private static final String TEXT = "The text <b>";
    private static final String PRESENT = "</b> is present on the page";
    private static final String VISIBLE = "</b> is visible on the page";

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * the detailed test results constructor that will setup the our test output
     * file for all documentation and information for browser based testing
     *
     * @param testsName
     *            the name of the file we will write out to
     * @param browser
     *            the browser we are running the tests with
     * @param outputDir
     *            the output directory to store the results
     */
    public Assert(String outputDir, String testsName, Browser browser) {
        outputFile = new OutputFile(outputDir, testsName, browser);
    }

    public Assert(String outputDir, String testsName, String serviceURL) {
        outputFile = new OutputFile(outputDir, testsName, serviceURL);
    }

    public void setSeleniumHelper(Action thisSelHelper) {
        action = thisSelHelper;
        locatorAssert = new LocatorAssert(action, outputFile);
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
     * @param expectedAlert
     *            the expected text of the alert
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkAlert(String expectedAlert) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find alert with the text <b>" + expectedAlert + ONPAGE);
        // check for the object to the visible
        String alert = "";
        boolean isAlertPresent = action.is().alertPresent();
        if (isAlertPresent) {
            alert = action.get().alert();
        }
        if (!isAlertPresent) {
            outputFile.recordActual(NOALERT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        Pattern patt = Pattern.compile(expectedAlert);
        Matcher m = patt.matcher(alert);
        boolean isCorrect;
        if (expectedAlert.contains("\\")) {
            isCorrect = m.matches();
        } else {
            isCorrect = alert.equals(expectedAlert);
        }
        if (!isCorrect) {
            outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an alert is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkAlertPresent() {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find an alert on the page");
        // check for the object to the visible
        String alert = "";
        boolean isAlertPresent = action.is().alertPresent();
        if (isAlertPresent) {
            alert = action.get().alert();
        }
        if (!isAlertPresent) {
            outputFile.recordActual(NOALERT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
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
        // outputFile.record the action
        outputFile.recordExpected("Expected not to find an alert on the page");
        // check for the object to the visible
        boolean isAlertPresent = action.is().alertPresent();
        if (isAlertPresent) {
            outputFile.recordActual("An alert is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(NOALERT, Success.PASS);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // a bunch of methods to negatively check for objects using selenium calls
    // ///////////////////////////////////////////////////////////////////////

    /**
     * checks to see if a confirmation is correct on the page
     *
     * @param expectedConfirmation
     *            the expected text of the confirmation
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkConfirmation(String expectedConfirmation) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmation + ONPAGE);
        // check for the object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = action.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = action.get().confirmation();
        }
        if (!isConfirmationPresent) {
            outputFile.recordActual(NOCONFIRMATION, Success.FAIL);
            outputFile.addError();
            outputFile.addError();
            return 1;
        }
        if (!expectedConfirmation.equals(confirmation)) {
            outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkConfirmationPresent() {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find a confirmation on the page");
        // check for the object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = action.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = action.get().confirmation();
        }
        if (!isConfirmationPresent) {
            outputFile.recordActual(NOCONFIRMATION, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkConfirmationNotPresent() {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find a confirmation on the page");
        // check for the object to the visible
        boolean isConfirmationPresent = action.is().confirmationPresent();
        if (isConfirmationPresent) {
            outputFile.recordActual("A confirmation is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(NOCONFIRMATION, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is correct on the page
     *
     * @param expectedPrompt
     *            the expected text of the confirmation
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkPrompt(String expectedPrompt) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find prompt with the text <b>" + expectedPrompt + ONPAGE);
        // check for the object to the visible
        String prompt = "";
        boolean isPromptPresent = action.is().promptPresent();
        if (isPromptPresent) {
            prompt = action.get().prompt();
        }
        if (!isPromptPresent) {
            outputFile.recordActual(NOPROMPT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!expectedPrompt.equals(prompt)) {
            outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkPromptPresent() {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find prompt on the page");
        // check for the object to the visible
        String prompt = "";
        boolean isPromptPresent = action.is().promptPresent();
        if (isPromptPresent) {
            prompt = action.get().prompt();
        }
        if (!isPromptPresent) {
            outputFile.recordActual(NOPROMPT, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkPromptNotPresent() {
        // outputFile.record the action
        outputFile.recordExpected("Expected not to find prompt on the page");
        // check for the object to the visible
        boolean isPromptPresent = action.is().promptPresent();
        if (isPromptPresent) {
            outputFile.recordActual("A prompt is present on the page", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(NOPROMPT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is correct for the page
     *
     * @param cookieName
     *            the name of the cookie
     * @param expectedCookieValue
     *            the expected value of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkCookie(String cookieName, String expectedCookieValue) {
        // outputFile.record the action
        outputFile.recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
        // check for the object to the visible
        String cookieValue = "";
        boolean isCookiePresent = action.is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = action.get().cookieValue(cookieName);
        }
        if (!isCookiePresent) {
            outputFile.recordActual(NOCOOKIE + cookieName + STORED, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            outputFile.recordActual(COOKIE + cookieName + "</b> is stored for the page, but the value "
                    + "of the cookie is " + cookieValue, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is present on the page
     *
     * @param expectedCookieName
     *            the name of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkCookiePresent(String expectedCookieName) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
        // check for the object to the visible
        String cookieValue = "";
        boolean isCookiePresent = action.is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = action.get().cookieValue(expectedCookieName);
        }
        if (!isCookiePresent) {
            outputFile.recordActual(NOCOOKIE + expectedCookieName + STORED, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(COOKIE + expectedCookieName + VALUE + cookieValue + STORED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is not present on the page
     *
     * @param unexpectedCookieName
     *            the name of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkCookieNotPresent(String unexpectedCookieName) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
        // check for the object to the visible
        boolean isCookiePresent = action.is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            outputFile.recordActual(COOKIE + unexpectedCookieName + STORED, Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual(NOCOOKIE + unexpectedCookieName + STORED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is visible on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayed(Element element) {
        return checkElementDisplayed(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an element is visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayed(Locator type, String locator) {
        return checkElementDisplayed(type, locator, 0);
    }

    /**
     * checks to see if an element is visible on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayed(Element element, int elementMatch) {
        return checkElementDisplayed(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayed(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementDisplayed(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotDisplayed(Element element) {
        return checkElementNotDisplayed(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotDisplayed(Locator type, String locator) {
        return checkElementNotDisplayed(type, locator, 0);
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotDisplayed(Element element, int elementMatch) {
        return checkElementNotDisplayed(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotDisplayed(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementNotDisplayed(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementChecked(Element element) {
        return checkElementChecked(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementChecked(Locator type, String locator) {
        return checkElementChecked(type, locator, 0);

    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementChecked(Element element, int elementMatch) {
        return checkElementChecked(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementChecked(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementChecked(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;

    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotChecked(Element element) {
        return checkElementNotChecked(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotChecked(Locator type, String locator) {
        return checkElementNotChecked(type, locator, 0);
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotChecked(Element element, int elementMatch) {
        return checkElementNotChecked(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotChecked(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementNotChecked(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndChecked(Element element) {
        return checkElementDisplayedAndChecked(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndChecked(Locator type, String locator) {
        return checkElementDisplayedAndChecked(type, locator, 0);
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndChecked(Element element, int elementMatch) {
        return checkElementDisplayedAndChecked(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndChecked(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementDisplayedAndChecked(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndUnchecked(Element element) {
        return checkElementDisplayedAndUnchecked(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndUnchecked(Locator type, String locator) {
        return checkElementDisplayedAndUnchecked(type, locator, 0);
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndUnchecked(Element element, int elementMatch) {
        return checkElementDisplayedAndUnchecked(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndUnchecked(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementDisplayedAndUnchecked(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementEditable(Element element) {
        return checkElementEditable(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementEditable(Locator type, String locator) {
        return checkElementEditable(type, locator, 0);
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementEditable(Element element, int elementMatch) {
        return checkElementEditable(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementEditable(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementEditable(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotEditable(Element element) {
        return checkElementNotEditable(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotEditable(Locator type, String locator) {
        return checkElementNotEditable(type, locator, 0);
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotEditable(Element element, int elementMatch) {
        return checkElementNotEditable(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementNotEditable(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementNotEditable(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndEditable(Element element) {
        return checkElementDisplayedAndEditable(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndEditable(Locator type, String locator) {
        return checkElementDisplayedAndEditable(type, locator, 0);
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndEditable(Element element, int elementMatch) {
        return checkElementDisplayedAndEditable(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndEditable(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementDisplayedAndEditable(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndNotEditable(Element element) {
        return checkElementDisplayedAndNotEditable(element.getType(), element.getLocator(), 0);
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndNotEditable(Locator type, String locator) {
        return checkElementDisplayedAndNotEditable(type, locator, 0);
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndNotEditable(Element element, int elementMatch) {
        return checkElementDisplayedAndNotEditable(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDisplayedAndNotEditable(Locator type, String locator, int elementMatch) {
        int errors = locatorAssert.checkElementDisplayedAndNotEditable(type, locator, elementMatch);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param element
     *            - the element to be waited for
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasAttribute(Element element, String attribute) {
        return checkElementHasAttribute(element.getType(), element.getLocator(), 0, attribute);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasAttribute(Locator type, String locator, String attribute) {
        return checkElementHasAttribute(type, locator, 0, attribute);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasAttribute(Element element, int elementMatch, String attribute) {
        return checkElementHasAttribute(element.getType(), element.getLocator(), elementMatch, attribute);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasAttribute(Locator type, String locator, int elementMatch, String attribute) {
        int errors = locatorAssert.checkElementHasAttribute(type, locator, elementMatch, attribute);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param element
     *            - the element to be waited for
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntHaveAttribute(Element element, String attribute) {
        return checkElementDoesntHaveAttribute(element.getType(), element.getLocator(), 0, attribute);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntHaveAttribute(Locator type, String locator, String attribute) {
        return checkElementDoesntHaveAttribute(type, locator, 0, attribute);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntHaveAttribute(Element element, int elementMatch, String attribute) {
        return checkElementDoesntHaveAttribute(element.getType(), element.getLocator(), elementMatch, attribute);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntHaveAttribute(Locator type, String locator, int elementMatch, String attribute) {
        int errors = locatorAssert.checkElementDoesntHaveAttribute(type, locator, elementMatch, attribute);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param element
     *            - the element to be waited for
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasClass(Element element, String expectedClass) {
        return checkElementHasClass(element.getType(), element.getLocator(), 0, expectedClass);
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasClass(Locator type, String locator, String expectedClass) {
        return checkElementHasClass(type, locator, 0, expectedClass);
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasClass(Element element, int elementMatch, String expectedClass) {
        return checkElementHasClass(element.getType(), element.getLocator(), elementMatch, expectedClass);
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementHasClass(Locator type, String locator, int elementMatch, String expectedClass) {
        int errors = locatorAssert.checkElementHasClass(type, locator, elementMatch, expectedClass);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param element
     *            - the element to be waited for
     * @param expectedClass
     *            - the expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementContainsClass(Element element, String expectedClass) {
        return checkElementContainsClass(element.getType(), element.getLocator(), 0, expectedClass);
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedClass
     *            - the expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementContainsClass(Locator type, String locator, String expectedClass) {
        return checkElementContainsClass(type, locator, 0, expectedClass);
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedClass
     *            - the expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementContainsClass(Element element, int elementMatch, String expectedClass) {
        return checkElementContainsClass(element.getType(), element.getLocator(), elementMatch, expectedClass);
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedClass
     *            - the expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementContainsClass(Locator type, String locator, int elementMatch, String expectedClass) {
        int errors = locatorAssert.checkElementContainsClass(type, locator, elementMatch, expectedClass);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param element
     *            - the element to be waited for
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntContainClass(Element element, String unexpectedClass) {
        return checkElementDoesntContainClass(element.getType(), element.getLocator(), 0, unexpectedClass);
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntContainClass(Locator type, String locator, String unexpectedClass) {
        return checkElementDoesntContainClass(type, locator, 0, unexpectedClass);
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntContainClass(Element element, int elementMatch, String unexpectedClass) {
        return checkElementDoesntContainClass(element.getType(), element.getLocator(), elementMatch, unexpectedClass);
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkElementDoesntContainClass(Locator type, String locator, int elementMatch, String unexpectedClass) {
        int errors = locatorAssert.checkElementDoesntContainClass(type, locator, elementMatch, unexpectedClass);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param element
     *            - the element to be waited for
     * @param option
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionInSelect(Element element, String option) {
        return checkIfOptionInSelect(element.getType(), element.getLocator(), 0, option);
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param option
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionInSelect(Locator type, String locator, String option) {
        return checkIfOptionInSelect(type, locator, 0, option);
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param option
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionInSelect(Element element, int elementMatch, String option) {
        return checkIfOptionInSelect(element.getType(), element.getLocator(), elementMatch, option);
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param option
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionInSelect(Locator type, String locator, int elementMatch, String option) {
        int errors = locatorAssert.checkIfOptionInSelect(type, locator, elementMatch, option);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param element
     *            - the element to be waited for
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionNotInSelect(Element element, String option) {
        return checkIfOptionNotInSelect(element.getType(), element.getLocator(), 0, option);
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionNotInSelect(Locator type, String locator, String option) {
        return checkIfOptionNotInSelect(type, locator, 0, option);
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionNotInSelect(Element element, int elementMatch, String option) {
        return checkIfOptionNotInSelect(element.getType(), element.getLocator(), elementMatch, option);
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkIfOptionNotInSelect(Locator type, String locator, int elementMatch, String option) {
        int errors = locatorAssert.checkIfOptionNotInSelect(type, locator, elementMatch, option);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if text is visible on the page
     *
     * @param expectedTexts
     *            the expected text to be visible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkTextVisible(String... expectedTexts) {
        // outputFile.record the action
        int errors = 0;
        for (String expectedText : expectedTexts) {
            outputFile.recordExpected("Expected to find text <b>" + expectedText + "</b> visible on the page");
            // check for the object to the visible
            boolean isPresent = action.is().textPresent(expectedText);
            if (!isPresent) {
                outputFile.recordActual(TEXT + expectedText + "</b> is not visible on the page", Success.FAIL);
                outputFile.addError();
                errors++;
            } else {
                outputFile.recordActual(TEXT + expectedText + VISIBLE, Success.PASS);
            }
        }
        return errors;
    }

    /**
     * checks to see if text is not visible on the page
     *
     * @param expectedTexts
     *            the expected text to be invisible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkTextNotVisible(String... expectedTexts) {
        // outputFile.record the action
        int errors = 0;
        for (String expectedText : expectedTexts) {
            outputFile.recordExpected("Expected not to find text <b>" + expectedText + "</b> visible on the page");
            // check for the object to the visible
            boolean isPresent = action.is().textPresent(expectedText);
            if (isPresent) {
                outputFile.recordActual(TEXT + expectedText + VISIBLE, Success.FAIL);
                outputFile.addError();
                errors++;
            } else {
                outputFile.recordActual(TEXT + expectedText + "</b> is not visible on the page", Success.PASS);
            }
        }
        return errors;
    }

    /**
     * checks to see if text is visible on the page
     *
     * @param expectedTexts
     *            the expected text to be visible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkTextVisibleOR(String... expectedTexts) {
        // outputFile.record the action
        int errors = 0;
        boolean isPresent = false;
        String foundText = "";
        StringBuilder allTexts = new StringBuilder();
        for (String expectedText : expectedTexts) {
            allTexts.append("<b>" + expectedText + "</b> or ");
        }
        allTexts.setLength(allTexts.length() - 4);
        outputFile.recordExpected("Expected to find text " + allTexts + " visible on the page");
        // check for the object to the visible
        for (String expectedText : expectedTexts) {
            isPresent = action.is().textPresent(expectedText);
            if (isPresent) {
                foundText = expectedText;
                break;
            }
        }
        if (!isPresent) {
            outputFile.recordActual(
                    "None of the texts " + allTexts.toString().replace(" or ", ", ") + " are visible on the page",
                    Success.FAIL);
            outputFile.addError();
            errors++;
            return errors;
        }
        outputFile.recordActual(TEXT + foundText + VISIBLE, Success.PASS);
        return errors;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValue(Element element, String expectedValue) {
        return compareTextValue(element.getType(), element.getLocator(), 0, expectedValue);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValue(Locator type, String locator, String expectedValue) {
        return compareTextValue(type, locator, 0, expectedValue);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValue(Element element, int elementMatch, String expectedValue) {
        return compareTextValue(element.getType(), element.getLocator(), elementMatch, expectedValue);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValue(Locator type, String locator, int elementMatch, String expectedValue) {
        int errors = locatorAssert.compareTextValue(type, locator, elementMatch, expectedValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValueContains(Element element, String expectedValue) {
        return compareTextValueContains(element.getType(), element.getLocator(), 0, expectedValue);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValueContains(Locator type, String locator, String expectedValue) {
        return compareTextValueContains(type, locator, 0, expectedValue);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValueContains(Element element, int elementMatch, String expectedValue) {
        return compareTextValueContains(element.getType(), element.getLocator(), elementMatch, expectedValue);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTextValueContains(Locator type, String locator, int elementMatch, String expectedValue) {
        int errors = locatorAssert.compareTextValueContains(type, locator, elementMatch, expectedValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the actual title a page is on to the expected title
     *
     * @param expectedTitle
     *            the friendly name of the page
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTitle(String expectedTitle) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
        String actualTitle = action.get().title();
        if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
            outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the actual URL a page is on to the expected URL
     *
     * @param expectedURL
     *            the URL of the page
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareURL(String expectedURL) {
        // outputFile.record the action
        outputFile.recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
        String actualURL = action.get().location();
        if (!actualURL.equalsIgnoreCase(expectedURL)) {
            outputFile.recordActual("The page URL  reads <b>" + actualURL + "</b>", Success.FAIL);
            outputFile.addError();
            return 1;
        }
        outputFile.recordActual("The page URL reads <b>" + actualURL + "</b>", Success.PASS);
        return 0;
    }

    /**
     * 
     * @param element
     *            - the element to be matched
     * @param expectedMatchedElements
     *            - the expected number of elements on the page that match the
     *            module
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareElementMatches(Element element, int expectedMatchedElements) {
        // TODO
        return 1;
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareInputValue(Element element, String expectedValue) {
        return compareInputValue(element.getType(), element.getLocator(), 0, expectedValue);
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareInputValue(Locator type, String locator, String expectedValue) {
        return compareInputValue(type, locator, 0, expectedValue);
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareInputValue(Element element, int elementMatch, String expectedValue) {
        return compareInputValue(element.getType(), element.getLocator(), elementMatch, expectedValue);
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareInputValue(Locator type, String locator, int elementMatch, String expectedValue) {
        int errors = locatorAssert.compareInputValue(type, locator, elementMatch, expectedValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param element
     *            - the element to be waited for
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareCssValue(Element element, String attribute, String expectedValue) {
        return compareCssValue(element.getType(), element.getLocator(), 0, attribute, expectedValue);
    }

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareCssValue(Locator type, String locator, String attribute, String expectedValue) {
        return compareCssValue(type, locator, 0, attribute, expectedValue);
    }

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareCssValue(Element element, int elementMatch, String attribute, String expectedValue) {
        return compareCssValue(element.getType(), element.getLocator(), elementMatch, attribute, expectedValue);
    }

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareCssValue(Locator type, String locator, int elementMatch, String attribute, String expectedValue) {
        int errors = locatorAssert.compareCssValue(type, locator, elementMatch, attribute, expectedValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element select value exists
     *
     * @param element
     *            - the element to be waited for
     * @param selectValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValuePresent(Element element, String selectValue) {
        return checkSelectValuePresent(element.getType(), element.getLocator(), 0, selectValue);
    }

    /**
     * checks to see if an element select value exists
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param selectValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValuePresent(Locator type, String locator, String selectValue) {
        return checkSelectValuePresent(type, locator, 0, selectValue);
    }

    /**
     * checks to see if an element select value exists
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param selectValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValuePresent(Element element, int elementMatch, String selectValue) {
        return checkSelectValuePresent(element.getType(), element.getLocator(), elementMatch, selectValue);
    }

    /**
     * checks to see if an element select value exists
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param selectValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValuePresent(Locator type, String locator, int elementMatch, String selectValue) {
        int errors = locatorAssert.checkSelectValuePresent(type, locator, elementMatch, selectValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param element
     *            - the element to be waited for
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValueNotPresent(Element element, String selectValue) {
        return checkSelectValueNotPresent(element.getType(), element.getLocator(), 0, selectValue);
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValueNotPresent(Locator type, String locator, String selectValue) {
        return checkSelectValueNotPresent(type, locator, 0, selectValue);
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValueNotPresent(Element element, int elementMatch, String selectValue) {
        return checkSelectValueNotPresent(element.getType(), element.getLocator(), elementMatch, selectValue);
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checkSelectValueNotPresent(Locator type, String locator, int elementMatch, String selectValue) {
        int errors = locatorAssert.checkSelectValueNotPresent(type, locator, elementMatch, selectValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValue(Element element, String expectedValue) {
        return compareSelectedValue(element.getType(), element.getLocator(), 0, expectedValue);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValue(Locator type, String locator, String expectedValue) {
        return compareSelectedValue(type, locator, 0, expectedValue);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValue(Element element, int elementMatch, String expectedValue) {
        return compareSelectedValue(element.getType(), element.getLocator(), elementMatch, expectedValue);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValue(Locator type, String locator, int elementMatch, String expectedValue) {
        int errors = locatorAssert.compareSelectedValue(type, locator, elementMatch, expectedValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedText(Element element, String expectedText) {
        return compareSelectedText(element.getType(), element.getLocator(), 0, expectedText);
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedText(Locator type, String locator, String expectedText) {
        return compareSelectedText(type, locator, 0, expectedText);
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedText(Element element, int elementMatch, String expectedText) {
        return compareSelectedText(element.getType(), element.getLocator(), elementMatch, expectedText);
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedText(Locator type, String locator, int elementMatch, String expectedText) {
        int errors = locatorAssert.compareSelectedText(type, locator, elementMatch, expectedText);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValueNotEqual(Element element, String expectedValue) {
        return compareSelectedValueNotEqual(element.getType(), element.getLocator(), 0, expectedValue);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValueNotEqual(Locator type, String locator, String expectedValue) {
        return compareSelectedValueNotEqual(type, locator, 0, expectedValue);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValueNotEqual(Element element, int elementMatch, String expectedValue) {
        return compareSelectedValueNotEqual(element.getType(), element.getLocator(), elementMatch, expectedValue);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectedValueNotEqual(Locator type, String locator, int elementMatch, String expectedValue) {
        int errors = locatorAssert.compareSelectedValueNotEqual(type, locator, elementMatch, expectedValue);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param element
     *            - the element to be waited for
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectValues(Element element, String... expectedValues) {
        return compareSelectValues(element.getType(), element.getLocator(), 0, expectedValues);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectValues(Locator type, String locator, String... expectedValues) {
        return compareSelectValues(type, locator, 0, expectedValues);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectValues(Element element, int elementMatch, String... expectedValues) {
        return compareSelectValues(element.getType(), element.getLocator(), elementMatch, expectedValues);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareSelectValues(Locator type, String locator, int elementMatch, String... expectedValues) {
        int errors = locatorAssert.compareSelectValues(type, locator, elementMatch, expectedValues);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param element
     *            - the element to be waited for
     * @param numOfOptions
     *            the expected number of options in the select element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfSelectOptions(Element element, int numOfOptions) {
        return compareNumOfSelectOptions(element.getType(), element.getLocator(), 0, numOfOptions);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param numOfOptions
     *            the expected number of options in the select element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfSelectOptions(Locator type, String locator, int numOfOptions) {
        return compareNumOfSelectOptions(type, locator, 0, numOfOptions);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfOptions
     *            the expected number of options in the select element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfSelectOptions(Element element, int elementMatch, int numOfOptions) {
        return compareNumOfSelectOptions(element.getType(), element.getLocator(), elementMatch, numOfOptions);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfOptions
     *            the expected number of options in the select element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfSelectOptions(Locator type, String locator, int elementMatch, int numOfOptions) {
        int errors = locatorAssert.compareNumOfSelectOptions(type, locator, elementMatch, numOfOptions);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the number of expected rows with the actual number of rows of a
     * table with from a table element
     *
     * @param element
     *            - the element to be waited for
     * @param numOfRows
     *            the number of rows in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableRows(Element element, int numOfRows) {
        return compareNumOfTableRows(element.getType(), element.getLocator(), 0, numOfRows);
    }

    /**
     * compares the number of expected rows with the actual number of rows of a
     * table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param numOfRows
     *            the number of rows in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableRows(Locator type, String locator, int numOfRows) {
        return compareNumOfTableRows(type, locator, 0, numOfRows);
    }

    /**
     * compares the number of expected rows with the actual number of rows of a
     * table with from a table element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfRows
     *            the number of rows in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableRows(Element element, int elementMatch, int numOfRows) {
        return compareNumOfTableRows(element.getType(), element.getLocator(), elementMatch, numOfRows);
    }

    /**
     * compares the number of expected rows with the actual number of rows of a
     * table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfRows
     *            the number of rows in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableRows(Locator type, String locator, int elementMatch, int numOfRows) {
        int errors = locatorAssert.compareNumOfTableRows(type, locator, elementMatch, numOfRows);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the number of expected columns with the actual number of columns
     * of a table with from a table element
     *
     * @param element
     *            - the element to be waited for
     * @param numOfColumns
     *            the number of columns in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableColumns(Element element, int numOfColumns) {
        return compareNumOfTableColumns(element.getType(), element.getLocator(), 0, numOfColumns);
    }

    /**
     * compares the number of expected columns with the actual number of columns
     * of a table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param numOfColumns
     *            the number of columns in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableColumns(Locator type, String locator, int numOfColumns) {
        return compareNumOfTableColumns(type, locator, 0, numOfColumns);
    }

    /**
     * compares the number of expected columns with the actual number of columns
     * of a table with from a table element
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfColumns
     *            the number of columns in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableColumns(Element element, int elementMatch, int numOfColumns) {
        return compareNumOfTableColumns(element.getType(), element.getLocator(), elementMatch, numOfColumns);
    }

    /**
     * compares the number of expected columns with the actual number of columns
     * of a table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfColumns
     *            the number of columns in a table
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareNumOfTableColumns(Locator type, String locator, int elementMatch, int numOfColumns) {
        int errors = locatorAssert.compareNumOfTableColumns(type, locator, elementMatch, numOfColumns);
        outputFile.addErrors(errors);
        return errors;
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
     *
     * @param element
     *            - the element to be waited for
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTableCellText(Element element, int row, int col, String text) {
        return compareTableCellText(element.getType(), element.getLocator(), 0, row, col, text);
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
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
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTableCellText(Locator type, String locator, int row, int col, String text) {
        return compareTableCellText(type, locator, 0, row, col, text);
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
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
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTableCellText(Element element, int elementMatch, int row, int col, String text) {
        return compareTableCellText(element.getType(), element.getLocator(), elementMatch, row, col, text);
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
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
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int compareTableCellText(Locator type, String locator, int elementMatch, int row, int col, String text) {
        int errors = locatorAssert.compareTableCellText(type, locator, elementMatch, row, col, text);
        outputFile.addErrors(errors);
        return errors;
    }

    ///////////////////////////////////////////////////////////////////
    // this enum will be for a pass/fail
    ///////////////////////////////////////////////////////////////////

    /**
     * An enumeration used to determine if the tests pass or fail
     * 
     * @author Max Saperstone
     *
     */
    public enum Success {
        PASS, FAIL;

        protected int errors;

        /**
         * Are errors associated with the enumeration
         */
        static {
            PASS.errors = 0;
            FAIL.errors = 1;
        }

        /**
         * Retrieve the errors associated with the enumeration
         * 
         * @return Integer: the errors associated with the enumeration
         */
        public int getErrors() {
            return this.errors;
        }
    }

    /**
     * An enumeration used to give status for each test step
     * 
     * @author Max Saperstone
     *
     */
    public enum Result {
        WARNING, SUCCESS, FAILURE, SKIPPED
    }
}