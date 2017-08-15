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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coveros.selenified.tools.OutputFile;
import com.coveros.selenified.tools.OutputFile.Success;

/**
 * Assert will handle all verifications performed on the actual application
 * itself. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests.
 * 
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Assert {

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private App app;

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

    public Assert(App app, OutputFile file) {
        this.app = app;
        this.file = file;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Verifies that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL
     *            the URL of the page
     */
    public void urlEquals(String expectedURL) {
        // file.record the action
        file.recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
        String actualURL = app.get().location();
        if (!actualURL.equalsIgnoreCase(expectedURL)) {
            file.recordActual("The page URL  reads <b>" + actualURL + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual("The page URL reads <b>" + actualURL + "</b>", Success.PASS);
    }

    /**
     * Verifies the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitle
     *            the friendly name of the page
     */
    public void titleEquals(String expectedTitle) {
        // file.record the action
        file.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
        String actualTitle = app.get().title();
        if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
            file.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
    }

    /**
     * Verifies that provided text(s) are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedTexts
     *            the expected text to be present
     */
    public void textPresent(String... expectedTexts) {
        // file.record the action
        for (String expectedText : expectedTexts) {
            file.recordExpected("Expected to find text <b>" + expectedText + "</b> present on the page");
            // check for the object to the visible
            boolean isPresent = app.is().textPresent(expectedText);
            if (!isPresent) {
                file.recordActual(TEXT + expectedText + "</b> is not present on the page", Success.FAIL);
                file.addError();
            } else {
                file.recordActual(TEXT + expectedText + PRESENT, Success.PASS);
            }
        }
    }

    /**
     * Verifies that provided text(s) are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedTexts
     *            the expected text to be not present
     */
    public void textNotPresent(String... expectedTexts) {
        // file.record the action
        for (String expectedText : expectedTexts) {
            file.recordExpected("Expected not to find text <b>" + expectedText + "</b> present on the page");
            // check for the object to the visible
            boolean isPresent = app.is().textPresent(expectedText);
            if (isPresent) {
                file.recordActual(TEXT + expectedText + PRESENT, Success.FAIL);
                file.addError();
            } else {
                file.recordActual(TEXT + expectedText + "</b> is not present on the page", Success.PASS);
            }
        }
    }

    ///////////////////////////////////////////////////////
    // assertions about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * Verifies that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void alertPresent() {
        // file.record the action
        file.recordExpected("Expected to find an alert on the page");
        // check for the object to the visible
        String alert = "";
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            alert = app.get().alert();
        }
        if (!isAlertPresent) {
            file.recordActual(NOALERT, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
    }

    /**
     * Verifies that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void alertNotPresent() {
        // file.record the action
        file.recordExpected("Expected not to find an alert on the page");
        // check for the object to the visible
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            file.recordActual("An alert is present on the page", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(NOALERT, Success.PASS);
    }

    /**
     * Verifies that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     * 
     * @param expectedAlertText
     *            the expected text of the alert
     */
    public void alertPresent(String expectedAlertText) {
        // file.record the action
        file.recordExpected("Expected to find alert with the text <b>" + expectedAlertText + ONPAGE);
        // check for the object to the visible
        String alert = "";
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            alert = app.get().alert();
        }
        if (!isAlertPresent) {
            file.recordActual(NOALERT, Success.FAIL);
            file.addError();
            return;
        }
        Pattern patt = Pattern.compile(expectedAlertText);
        Matcher m = patt.matcher(alert);
        boolean isCorrect;
        if (expectedAlertText.contains("\\")) {
            isCorrect = m.matches();
        } else {
            isCorrect = alert.equals(expectedAlertText);
        }
        if (!isCorrect) {
            file.recordActual(ALERTTEXT + alert + PRESENT, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
    }

    /**
     * Verifies that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void confirmationPresent() {
        // file.record the action
        file.recordExpected("Expected to find a confirmation on the page");
        // check for the object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = app.get().confirmation();
        }
        if (!isConfirmationPresent) {
            file.recordActual(NOCONFIRMATION, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
    }

    /**
     * Verifies that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void confirmationNotPresent() {
        // file.record the action
        file.recordExpected("Expected to find a confirmation on the page");
        // check for the object to the visible
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            file.recordActual("A confirmation is present on the page", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(NOCONFIRMATION, Success.PASS);
    }

    /**
     * Verifies that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     * 
     * @param expectedConfirmationText
     *            the expected text of the confirmation
     */
    public void confirmationPresent(String expectedConfirmationText) {
        // file.record the action
        file.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationText + ONPAGE);
        // check for the object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = app.get().confirmation();
        }
        if (!isConfirmationPresent) {
            file.recordActual(NOCONFIRMATION, Success.FAIL);
            file.addError();
            return;
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            file.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
    }

    /**
     * Verifies that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void promptPresent() {
        // file.record the action
        file.recordExpected("Expected to find prompt on the page");
        // check for the object to the visible
        String prompt = "";
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            prompt = app.get().prompt();
        }
        if (!isPromptPresent) {
            file.recordActual(NOPROMPT, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
    }

    /**
     * Verifies that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void promptNotPresent() {
        // file.record the action
        file.recordExpected("Expected not to find prompt on the page");
        // check for the object to the visible
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            file.recordActual("A prompt is present on the page", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(NOPROMPT, Success.PASS);
    }

    /**
     * Verifies that a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     * 
     * @param expectedPromptText
     *            the expected text of the prompt
     */
    public void promptPresent(String expectedPromptText) {
        // file.record the action
        file.recordExpected("Expected to find prompt with the text <b>" + expectedPromptText + ONPAGE);
        // check for the object to the visible
        String prompt = "";
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            prompt = app.get().prompt();
        }
        if (!isPromptPresent) {
            file.recordActual(NOPROMPT, Success.FAIL);
            file.addError();
            return;
        }
        if (!expectedPromptText.equals(prompt)) {
            file.recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
    }

    ///////////////////////////////////////////////////////
    // assertions about cookies
    ///////////////////////////////////////////////////////

    /**
     * Verifies that a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedCookieName
     *            the name of the cookie
     */
    public void cookieExists(String expectedCookieName) {
        // file.record the action
        file.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
        // check for the object to the visible
        String cookieValue = "";
        boolean isCookiePresent = app.is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = app.get().cookieValue(expectedCookieName);
        }
        if (!isCookiePresent) {
            file.recordActual(NOCOOKIE + expectedCookieName + STORED, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(COOKIE + expectedCookieName + VALUE + cookieValue + STORED, Success.PASS);
    }

    /**
     * Verifies that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName
     *            the name of the cookie
     */
    public void cookieNotExists(String unexpectedCookieName) {
        // file.record the action
        file.recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
        // check for the object to the visible
        boolean isCookiePresent = app.is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            file.recordActual(COOKIE + unexpectedCookieName + STORED, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(NOCOOKIE + unexpectedCookieName + STORED, Success.PASS);
    }

    /**
     * Verifies that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName
     *            the name of the cookie
     * @param expectedCookieValue
     *            the expected value of the cookie
     */
    public void cookieExists(String cookieName, String expectedCookieValue) {
        // file.record the action
        file.recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
        // check for the object to the visible
        String cookieValue = "";
        boolean isCookiePresent = app.is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = app.get().cookieValue(cookieName);
        }
        if (!isCookiePresent) {
            file.recordActual(NOCOOKIE + cookieName + STORED, Success.FAIL);
            file.addError();
            return;
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            file.recordActual(COOKIE + cookieName + "</b> is stored for the page, but the value " + "of the cookie is "
                    + cookieValue, Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
    }
}