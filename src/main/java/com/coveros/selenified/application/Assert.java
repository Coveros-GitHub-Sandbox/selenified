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

import com.coveros.selenified.utilities.Reporter;

import static com.coveros.selenified.utilities.Constants.DOES_NOT_MATCH_PATTERN;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Assert will handle all verifications performed on the actual application
 * itself. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 6/25/2019
 */
public class Assert extends Check {

    /**
     * The default constructor passing in the app and output file
     *
     * @param app      - the application under test
     * @param reporter - the file to write all logging out to
     */
    public Assert(App app, Reporter reporter) {
        this.app = app;
        this.reporter = reporter;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Asserts that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL the URL of the page
     */
    @Override
    public void urlEquals(String expectedURL) {
        assertEquals("URL Mismatch", expectedURL, checkUrlEquals(expectedURL, 0, 0));
    }

    /**
     * Verifies the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitle the friendly name of the page
     */
    @Override
    public void titleEquals(String expectedTitle) {
        assertEquals("Title Mismatch", expectedTitle, checkTitleEquals(expectedTitle, 0, 0));
    }

    /**
     * Verifies the provided title matches the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitlePattern the friendly name of the page
     */
    @Override
    public void titleMatches(String expectedTitlePattern) {
        String title = checkTitleMatches(expectedTitlePattern, 0, 0);
        assertTrue("Title Mismatch: title of '" + title + DOES_NOT_MATCH_PATTERN + expectedTitlePattern + "'", title.matches(expectedTitlePattern));
    }

    /**
     * Asserts that provided text(s) are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedText the expected text to be present
     */
    @Override
    public void textPresent(String expectedText) {
        assertTrue("Expected to find text '" + expectedText + "'", checkTextPresent(expectedText, 0, 0));
    }

    /**
     * Asserts that provided text(s) are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText the expected text to be not present
     */
    @Override
    public void textNotPresent(String expectedText) {
        assertTrue("Expected not to find text '" + expectedText + "'", checkTextNotPresent(expectedText, 0, 0));
    }

    ///////////////////////////////////////////////////////
    // assertions about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * Asserts that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void alertPresent() {
        assertTrue("Expected to find an alert", checkAlertPresent(0, 0));
    }

    /**
     * Asserts that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void alertNotPresent() {
        assertTrue("Expected to not find an alert", checkAlertNotPresent(0, 0));
    }

    /**
     * Asserts that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText the expected text of the alert
     */
    @Override
    public void alertEquals(String expectedAlertText) {
        assertEquals("Alert Text Mismatch", expectedAlertText, checkAlertEquals(expectedAlertText, 0, 0));
    }

    /**
     * Asserts that an alert present on the page has content matching the
     * expected patten. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertPattern the expected text of the alert
     */
    @Override
    public void alertMatches(String expectedAlertPattern) {
        String alert = checkAlertMatches(expectedAlertPattern, 0, 0);
        assertTrue("Alert Text Mismatch: alert text of '" + alert + DOES_NOT_MATCH_PATTERN + expectedAlertPattern + "'", alert.matches(expectedAlertPattern));
    }

    /**
     * Asserts that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void confirmationPresent() {
        assertTrue("Expected to find a confirmation", checkConfirmationPresent(0, 0));
    }

    /**
     * Asserts that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void confirmationNotPresent() {
        assertTrue("Expected to not find a confirmation", checkConfirmationNotPresent(0, 0));
    }

    /**
     * Asserts that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText the expected text of the confirmation
     */
    @Override
    public void confirmationEquals(String expectedConfirmationText) {
        assertEquals("Confirmation Text Mismatch", expectedConfirmationText, checkConfirmationEquals(expectedConfirmationText, 0, 0));
    }

    /**
     * Asserts that a confirmation present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationPattern the expected text of the confirmation
     */
    @Override
    public void confirmationMatches(String expectedConfirmationPattern) {
        String confirmation = checkConfirmationMatches(expectedConfirmationPattern, 0, 0);
        assertTrue("Confirmation Text Mismatch: confirmation text of '" + confirmation + DOES_NOT_MATCH_PATTERN + expectedConfirmationPattern + "'", confirmation.matches(expectedConfirmationPattern));
    }

    /**
     * Asserts that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void promptPresent() {
        assertTrue("Expected to find a prompt", checkPromptPresent(0, 0));
    }

    /**
     * Asserts that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void promptNotPresent() {
        assertTrue("Expected to not find a prompt", checkPromptNotPresent(0, 0));
    }

    /**
     * Asserts that a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptText the expected text of the prompt
     */
    @Override
    public void promptEquals(String expectedPromptText) {
        assertEquals("Prompt Text Mismatch", expectedPromptText, checkPromptEquals(expectedPromptText, 0, 0));
    }

    /**
     * Asserts that a prompt present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptPattern the expected text of the prompt
     */
    @Override
    public void promptMatches(String expectedPromptPattern) {
        String prompt = checkPromptMatches(expectedPromptPattern, 0, 0);
        assertTrue("Prompt Text Mismatch: prompt text of '" + prompt + DOES_NOT_MATCH_PATTERN + expectedPromptPattern + "'", prompt.matches(expectedPromptPattern));
    }

    ///////////////////////////////////////////////////////
    // assertions about cookies
    ///////////////////////////////////////////////////////

    /**
     * Asserts that a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedCookieName the name of the cookie
     */
    @Override
    public void cookieExists(String expectedCookieName) {
        assertTrue("Expected to find cookie", checkCookieExists(expectedCookieName, 0, 0));
    }

    /**
     * Asserts that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName the name of the cookie
     */
    @Override
    public void cookieNotExists(String unexpectedCookieName) {
        assertTrue("Expected to not find cookie", checkCookieNotExists(unexpectedCookieName, 0, 0));
    }

    /**
     * Asserts that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     */
    @Override
    public void cookieEquals(String cookieName, String expectedCookieValue) {
        assertEquals("Cookie Value Mismatch", expectedCookieValue, checkCookieEquals(cookieName, expectedCookieValue, 0, 0));
    }

    /**
     * Asserts that a cookies with the provided name has a value matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName            the name of the cookie
     * @param expectedCookiePattern the expected value of the cookie
     */
    @Override
    public void cookieMatches(String cookieName, String expectedCookiePattern) {
        String cookie = checkCookieMatches(cookieName, expectedCookiePattern, 0, 0);
        assertTrue("Cookie Value Mismatch: cookie value of '" + cookie + DOES_NOT_MATCH_PATTERN + expectedCookiePattern + "'", cookie.matches(expectedCookiePattern));
    }
}