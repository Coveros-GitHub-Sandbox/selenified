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
public class Verify implements Check {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // this is the driver that will be used for all selenium actions
    private final App app;

    public Verify(App app, OutputFile file) {
        this.app = app;
        this.file = file;
    }

    /**
     * Retrieves the output file that we write all details out to
     *
     * @return OutputFile
     */
    @Override
    public OutputFile getOutputFile() {
        return file;
    }

    /**
     * Retrieves the driver that is used for all selenium actions
     *
     * @return App
     */
    @Override
    public App getApp() {
        return app;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Verifies that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL the URL of the page
     */
    @Override
    public void urlEquals(String expectedURL) {
        if (!expectedURL.equals(checkUrlEquals(expectedURL, 0, 0))) {
            file.addError();
        }
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
        if (!expectedTitle.equals(checkTitleEquals(expectedTitle, 0, 0))) {
            file.addError();
        }
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
        if (!checkTitleMatches(expectedTitlePattern, 0, 0).matches(expectedTitlePattern)) {
            file.addError();
        }
    }

    /**
     * Verifies that provided text(s) are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedText the expected text to be present
     */
    @Override
    public void textPresent(String expectedText) {
        if (!checkTextPresent(expectedText, 0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that provided text(s) are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText the expected text to be not present
     */
    @Override
    public void textNotPresent(String expectedText) {
        if (!checkTextNotPresent(expectedText, 0, 0)) {
            file.addError();
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
    @Override
    public void alertPresent() {
        if (!checkAlertPresent(0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void alertNotPresent() {
        if (!checkAlertNotPresent(0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText the expected text of the alert
     */
    @Override
    public void alertEquals(String expectedAlertText) {
        if (!expectedAlertText.equals(checkAlertEquals(expectedAlertText, 0, 0))) {
            file.addError();
        }
    }

    /**
     * Verifies that an alert present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertPattern the expected text of the alert
     */
    @Override
    public void alertMatches(String expectedAlertPattern) {
        if (!checkAlertMatches(expectedAlertPattern, 0, 0).matches(expectedAlertPattern)) {
            file.addError();
        }
    }

    /**
     * Verifies that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void confirmationPresent() {
        if (!checkConfirmationPresent(0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void confirmationNotPresent() {
        if (!checkConfirmationNotPresent(0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText the expected text of the confirmation
     */
    @Override
    public void confirmationEquals(String expectedConfirmationText) {
        if (!expectedConfirmationText.equals(checkConfirmationEquals(expectedConfirmationText, 0, 0))) {
            file.addError();
        }
    }

    /**
     * Verifies that a confirmation present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationPattern the expected text of the confirmation
     */
    @Override
    public void confirmationMatches(String expectedConfirmationPattern) {
        if (!checkConfirmationMatches(expectedConfirmationPattern, 0, 0).matches(expectedConfirmationPattern)) {
            file.addError();
        }
    }

    /**
     * Verifies that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void promptPresent() {
        if (!checkPromptPresent(0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void promptNotPresent() {
        if (!checkPromptNotPresent(0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptText the expected text of the prompt
     */
    @Override
    public void promptEquals(String expectedPromptText) {
        if (!expectedPromptText.equals(checkPromptEquals(expectedPromptText, 0, 0))) {
            file.addError();
        }
    }

    /**
     * Verifies that a prompt present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptPattern the expected text of the prompt
     */
    @Override
    public void promptMatches(String expectedPromptPattern) {
        if (!checkPromptMatches(expectedPromptPattern, 0, 0).matches(expectedPromptPattern)) {
            file.addError();
        }
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
    @Override
    public void cookieExists(String expectedCookieName) {
        if (!checkCookieExists(expectedCookieName, 0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName the name of the cookie
     */
    @Override
    public void cookieNotExists(String unexpectedCookieName) {
        if (!checkCookieNotExists(unexpectedCookieName, 0, 0)) {
            file.addError();
        }
    }

    /**
     * Verifies that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     */
    @Override
    public void cookieEquals(String cookieName, String expectedCookieValue) {
        if (!expectedCookieValue.equals(checkCookieEquals(cookieName, expectedCookieValue, 0, 0))) {
            file.addError();
        }
    }

    /**
     * Verifies that a cookies with the provided name has a value matching the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookiePattern the expected value of the cookie
     */
    @Override
    public void cookieMatches(String cookieName, String expectedCookiePattern) {
        if (!checkCookieMatches(cookieName, expectedCookiePattern, 0, 0).matches(expectedCookiePattern)) {
            file.addError();
        }
    }
}