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

package com.coveros.selenified.application;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Success;

/**
 * Assert will handle all verifications performed on the actual application
 * itself. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.0.5
 * @lastupdate 5/4/2018
 */
public interface Check {

    // constants
    String ONPAGE = "</b> on the page";
    String NOALERT = "No alert is present on the page";
    String ALERTTEXT = "An alert with text <b>";
    String NOCONFIRMATION = "No confirmation is present on the page";
    String CONFIRMATIONTEXT = "A confirmation with text <b>";
    String NOPROMPT = "No prompt is present on the page";
    String PROMPTTEXT = "A prompt with text <b>";

    String STORED = "</b> is stored for the page";
    String VALUE = "</b> and a value of <b>";
    String COOKIE = "A cookie with the name <b>";
    String NOCOOKIE = "No cookie with the name <b>";

    String TEXT = "The text <b>";
    String PRESENT = "</b> is present on the page";


    /**
     * Retrieves the output file that we write all details out to
     *
     * @return OutputFile
     */
    OutputFile getOutputFile();

    /**
     * Retrieves the driver that is used for all selenium actions
     *
     * @return App
     */
    App getApp();

    ///////////////////////////////////////////////////////
    // checks about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Verifies that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL the URL of the page
     */
    void urlEquals(String expectedURL);

    default String checkUrlEquals(String expectedURL) {
        // record the action
        getOutputFile().recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
        String actualURL = getApp().get().location();
        if (!actualURL.equals(expectedURL)) {
            getOutputFile().recordActual("The page URL  reads <b>" + actualURL + "</b>", Success.FAIL);
        } else {
            getOutputFile().recordActual("The page URL reads <b>" + actualURL + "</b>", Success.PASS);
        }
        return actualURL;
    }

    /**
     * Verifies the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitle the friendly name of the page
     */
    void titleEquals(String expectedTitle);

    default String checkTitleEquals(String expectedTitle) {
        // record the action
        getOutputFile().recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
        String actualTitle = getApp().get().title();
        if (!actualTitle.equals(expectedTitle)) {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
        } else {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
        }
        return actualTitle;
    }

    /**
     * Verifies the provided title matches the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitlePattern the friendly name of the page
     */
    void titleMatches(String expectedTitlePattern);

    default String checkTitleMatches(String expectedTitlePattern) {
        // record the action
        getOutputFile().recordExpected("Expected to be on page with the title of <i>" + expectedTitlePattern + "</i>");
        String actualTitle = getApp().get().title();
        if (!actualTitle.matches(expectedTitlePattern)) {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
        } else {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
        }
        return actualTitle;
    }

    /**
     * Verifies that provided text(s) are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedText the expected text to be present
     */
    void textPresent(String expectedText);

    default boolean checkTextPresent(String expectedText) {
        // record the action
        getOutputFile().recordExpected("Expected to find text <b>" + expectedText + "</b> present on the page");
        // check for the object to be present
        boolean isPresent = getApp().is().textPresent(expectedText);
        if (!isPresent) {
            getOutputFile().recordActual(TEXT + expectedText + "</b> is not present on the page", Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(TEXT + expectedText + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that provided text(s) are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText the expected text to be not present
     */
    void textNotPresent(String expectedText);

    default boolean checkTextNotPresent(String expectedText) {
        // record the action
        getOutputFile().recordExpected("Expected not to find text <b>" + expectedText + "</b> present on the page");
        // check for the object to be present
        boolean isPresent = getApp().is().textPresent(expectedText);
        if (isPresent) {
            getOutputFile().recordActual(TEXT + expectedText + PRESENT, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(TEXT + expectedText + "</b> is not present on the page", Success.PASS);
        return true;
    }

    ///////////////////////////////////////////////////////
    // assertions about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * Verifies that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void alertPresent();

    default boolean checkAlertPresent() {
        // record the action
        getOutputFile().recordExpected("Expected to find an alert on the page");
        // check for the object to be present
        String alert = "";
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        }
        if (!isAlertPresent) {
            getOutputFile().recordActual(NOALERT, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void alertNotPresent();

    default boolean checkAlertNotPresent() {
        // record the action
        getOutputFile().recordExpected("Expected not to find an alert on the page");
        // check for the object to be present
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            getOutputFile().recordActual("An alert is present on the page", Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(NOALERT, Success.PASS);
        return true;
    }

    /**
     * Verifies that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText the expected text of the alert
     */
    void alertEquals(String expectedAlertText);

    default String checkAlertEquals(String expectedAlertText) {
        // record the action
        getOutputFile().recordExpected("Expected to find alert with the text <b>" + expectedAlertText + ONPAGE);
        // check for the object to be present
        String alert = "";
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(NOALERT, Success.FAIL);
            return "";
        }
        if (!alert.equals(expectedAlertText)) {
            getOutputFile().recordActual(ALERTTEXT + alert + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
        }
        return alert;
    }

    /**
     * Verifies that an alert present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertPattern the expected text of the alert
     */
    void alertMatches(String expectedAlertPattern);

    default String checkAlertMatches(String expectedAlertPattern) {
        // record the action
        getOutputFile().recordExpected("Expected to find alert with the text <b>" + expectedAlertPattern + ONPAGE);
        // check for the object to be present
        String alert = "";
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(NOALERT, Success.FAIL);
            return "";
        }
        if (!alert.matches(expectedAlertPattern)) {
            getOutputFile().recordActual(ALERTTEXT + alert + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
        }
        return alert;
    }

    /**
     * Verifies that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void confirmationPresent();

    default boolean checkConfirmationPresent() {
        //record the action
        getOutputFile().recordExpected("Expected to find a confirmation on the page");
        // check for the object to be present
        String confirmation = "";
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(NOCONFIRMATION, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void confirmationNotPresent();

    default boolean checkConfirmationNotPresent() {
        //record the action
        getOutputFile().recordExpected("Expected to find a confirmation on the page");
        // check for the object to be present
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            getOutputFile().recordActual("A confirmation is present on the page", Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(NOCONFIRMATION, Success.PASS);
        return true;
    }

    /**
     * Verifies that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText the expected text of the confirmation
     */
    void confirmationEquals(String expectedConfirmationText);

    default String checkConfirmationEquals(String expectedConfirmationText) {
        //record the action
        getOutputFile().recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationText + ONPAGE);
        // check for the object to be present
        String confirmation = "";
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(NOCONFIRMATION, Success.FAIL);
            return "";
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            getOutputFile().recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        }
        return confirmation;
    }

    /**
     * Verifies that a confirmation present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationPattern the expected text of the confirmation
     */
    void confirmationMatches(String expectedConfirmationPattern);

    default String checkConfirmationMatches(String expectedConfirmationPattern) {
        //record the action
        getOutputFile().recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationPattern + ONPAGE);
        // check for the object to be present
        String confirmation = "";
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(NOCONFIRMATION, Success.FAIL);
            return "";
        }
        if (!confirmation.matches(expectedConfirmationPattern)) {
            getOutputFile().recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        }
        return confirmation;
    }

    /**
     * Verifies that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void promptPresent();

    default boolean checkPromptPresent() {
        //record the action
        getOutputFile().recordExpected("Expected to find prompt on the page");
        // check for the object to be present
        String prompt = "";
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(NOPROMPT, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void promptNotPresent();

    default boolean checkPromptNotPresent() {
        //record the action
        getOutputFile().recordExpected("Expected not to find prompt on the page");
        // check for the object to be present
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            getOutputFile().recordActual("A prompt is present on the page", Success.FAIL);
        } else {
            getOutputFile().recordActual(NOPROMPT, Success.PASS);
        }
        return !isPromptPresent;
    }

    /**
     * Verifies that a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptText the expected text of the prompt
     */
    void promptEquals(String expectedPromptText);

    default String checkPromptEquals(String expectedPromptText) {
        //record the action
        getOutputFile().recordExpected("Expected to find prompt with the text <b>" + expectedPromptText + ONPAGE);
        // check for the object to be present
        String prompt = "";
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(NOPROMPT, Success.FAIL);
            return "";
        }
        if (!expectedPromptText.equals(prompt)) {
            getOutputFile().recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        }
        return prompt;
    }

    /**
     * Verifies that a prompt present on the page has content matches the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptPattern the expected text of the prompt
     */
    void promptMatches(String expectedPromptPattern);

    default String checkPromptMatches(String expectedPromptPattern) {
        //record the action
        getOutputFile().recordExpected("Expected to find prompt with the text <b>" + expectedPromptPattern + ONPAGE);
        // check for the object to be present
        String prompt = "";
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(NOPROMPT, Success.FAIL);
            return "";
        }
        if (!prompt.matches(expectedPromptPattern)) {
            getOutputFile().recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        }
        return prompt;
    }

    ///////////////////////////////////////////////////////
    // assertions about cookies
    ///////////////////////////////////////////////////////

    /**
     * Verifies that a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedCookieName the name of the cookie
     */
    void cookieExists(String expectedCookieName);

    default boolean checkCookieExists(String expectedCookieName) {
        //record the action
        getOutputFile().recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
        // check for the object to be present
        String cookieValue = "";
        boolean isCookiePresent = getApp().is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(expectedCookieName);
        }
        if (!isCookiePresent) {
            getOutputFile().recordActual(NOCOOKIE + expectedCookieName + STORED, Success.FAIL);
        } else {
            getOutputFile().recordActual(COOKIE + expectedCookieName + VALUE + cookieValue + STORED, Success.PASS);
        }
        return isCookiePresent;
    }

    /**
     * Verifies that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName the name of the cookie
     */
    void cookieNotExists(String unexpectedCookieName);

    default boolean checkCookieNotExists(String unexpectedCookieName) {
        //record the action
        getOutputFile().recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
        // check for the object to be present
        boolean isCookiePresent = getApp().is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            getOutputFile().recordActual(COOKIE + unexpectedCookieName + STORED, Success.FAIL);
        } else {
            getOutputFile().recordActual(NOCOOKIE + unexpectedCookieName + STORED, Success.PASS);
        }
        return !isCookiePresent;
    }

    /**
     * Verifies that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     */
    void cookieEquals(String cookieName, String expectedCookieValue);

    default String checkCookieEquals(String cookieName, String expectedCookieValue) {
        //record the action
        getOutputFile().recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
        // check for the object to be present
        String cookieValue = "";
        boolean isCookiePresent = getApp().is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(cookieName);
        }
        if (!isCookiePresent) {
            getOutputFile().recordActual(NOCOOKIE + cookieName + STORED, Success.FAIL);
            return "";
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            getOutputFile().recordActual(
                    COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue,
                    Success.FAIL);
        } else {
            getOutputFile().recordActual(COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
        }
        return cookieValue;
    }

    /**
     * Verifies that a cookies with the provided name has a value matching the
     * expected value pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName            the name of the cookie
     * @param expectedCookiePattern the expected value of the cookie
     */
    void cookieMatches(String cookieName, String expectedCookiePattern);

    default String checkCookieMatches(String cookieName, String expectedCookiePattern) {
        //record the action
        getOutputFile().recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookiePattern + STORED);
        // check for the object to be present
        String cookieValue = "";
        boolean isCookiePresent = getApp().is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(cookieName);
        }
        if (!isCookiePresent) {
            getOutputFile().recordActual(NOCOOKIE + cookieName + STORED, Success.FAIL);
            return "";
        }
        if (!cookieValue.matches(expectedCookiePattern)) {
            getOutputFile().recordActual(
                    COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue,
                    Success.FAIL);
        } else {
            getOutputFile().recordActual(COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
        }
        return cookieValue;
    }
}