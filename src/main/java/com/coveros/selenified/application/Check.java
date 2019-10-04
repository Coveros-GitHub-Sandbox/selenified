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

import static com.coveros.selenified.utilities.Constants.*;

/**
 * Assert will handle all verifications performed on the actual application
 * itself. These checks are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 6/25/2019
 */
abstract class Check {

    static final String FIND_CONFIRMATION = "to find a confirmation on the page";
    public static final String COOKIE_WITH_NAME = "to find cookie with the name <b>";
    public static final String PAGE_TITLE = "The page title reads <b>";

    // this will be the name of the file we write all commands out to
    Reporter reporter;

    // this is the driver that will be used for all selenium actions
    App app;

    ///////////////////////////////////////////////////////
    // checks about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Checks that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL - the URL of the page
     */
    abstract void urlEquals(String expectedURL);

    /**
     * Checks that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedURL -  the URL of the page
     * @param waitFor     - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook    - the amount of time it took for wait for something (assuming we had to wait)
     * @return String - the actual URL
     */
    String checkUrlEquals(String expectedURL, double waitFor, double timeTook) {
        String actualURL = this.app.get().url();
        if (!actualURL.equals(expectedURL)) {
            this.reporter.fail("to be on page with the URL of <b>" + expectedURL + ENDB, waitFor,
                    "The page URL reads <b>" + actualURL + ENDB, timeTook);
        } else {
            this.reporter.pass("to be on page with the URL of <b>" + expectedURL + ENDB, waitFor,
                    "The page URL reads <b>" + actualURL + ENDB, timeTook);
        }
        return actualURL;
    }

    /**
     * Checks the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitle the friendly name of the page
     */
    abstract void titleEquals(String expectedTitle);

    /**
     * Checks the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedTitle - the friendly name of the page
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual title
     */
    String checkTitleEquals(String expectedTitle, double waitFor, double timeTook) {
        String actualTitle = this.app.get().title();
        if (!actualTitle.equals(expectedTitle)) {
            this.reporter.fail("to be on page with the title of <b>" + expectedTitle + ENDB, waitFor, PAGE_TITLE + actualTitle + ENDB, timeTook);
        } else {
            this.reporter.pass("to be on page with the title of <b>" + expectedTitle + ENDB, waitFor, PAGE_TITLE + actualTitle + ENDB, timeTook);
        }
        return actualTitle;
    }

    /**
     * Checks the provided title matches the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitlePattern - the friendly name of the page
     */
    abstract void titleMatches(String expectedTitlePattern);

    /**
     * Checks the provided title matches the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedTitlePattern - the friendly name of the page
     * @param waitFor              - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook             - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual title
     */
    String checkTitleMatches(String expectedTitlePattern, double waitFor, double timeTook) {
        String actualTitle = this.app.get().title();
        if (!actualTitle.matches(expectedTitlePattern)) {
            this.reporter.fail("to be on page with the title matching pattern <b>" + expectedTitlePattern + ENDB, waitFor, PAGE_TITLE + actualTitle + ENDB, timeTook);
        } else {
            this.reporter.pass("to be on page with the title matching pattern <b>" + expectedTitlePattern + ENDB, waitFor, PAGE_TITLE + actualTitle + ENDB, timeTook);
        }
        return actualTitle;
    }

    /**
     * Checks that provided text are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedText - the expected text to be present
     */
    abstract void textPresent(String expectedText);

    /**
     * Checks that provided text are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedText - the expected text to be present
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the text is actually present
     */
    boolean checkTextPresent(String expectedText, double waitFor, double timeTook) {
        // check for the object to be present
        boolean isPresent = this.app.is().textPresent(expectedText);
        if (!isPresent) {
            this.reporter.fail("to find text <b>" + expectedText + B_PRESENT, waitFor, TEXT_B + expectedText + "</b> is not present on the page", timeTook);
            return false;
        } else {
            this.reporter.pass("to find text <b>" + expectedText + B_PRESENT, waitFor, TEXT_B + expectedText + B_PRESENT, timeTook);
            return true;
        }
    }

    /**
     * Checks that provided text are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText - the expected text to be not present
     */
    abstract void textNotPresent(String expectedText);

    /**
     * Checks that provided text are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedText - the expected text to be not present
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the text is actually present
     */
    boolean checkTextNotPresent(String expectedText, double waitFor, double timeTook) {
        // check for the object to be present
        boolean isPresent = this.app.is().textPresent(expectedText);
        if (isPresent) {
            this.reporter.fail("not to find text <b>" + expectedText + B_PRESENT, waitFor, TEXT_B + expectedText + B_PRESENT, timeTook);
            return false;
        } else {
            this.reporter.pass("not to find text <b>" + expectedText + B_PRESENT, waitFor, TEXT_B + expectedText + "</b> is not present on the page", timeTook);
            return true;
        }
    }

    ///////////////////////////////////////////////////////
    // checks about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * Checks that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void alertPresent();

    /**
     * Checks that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the alert is actually present
     */
    boolean checkAlertPresent(double waitFor, double timeTook) {
        // check for the object to be present
        String alert;
        boolean isAlertPresent = this.app.is().alertPresent();
        if (isAlertPresent) {
            alert = this.app.get().alert();
            this.reporter.pass("to find an alert on the page", waitFor, ALERT_TEXT + alert + B_PRESENT, timeTook);
            return true;
        } else {
            this.reporter.fail("to find an alert on the page", waitFor, NO_ALERT, timeTook);
            return false;
        }
    }

    /**
     * Checks that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void alertNotPresent();

    /**
     * Checks that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the alert is actually present
     */
    boolean checkAlertNotPresent(double waitFor, double timeTook) {
        // check for the object to be present
        boolean isAlertPresent = this.app.is().alertPresent();
        if (isAlertPresent) {
            this.reporter.fail("not to find an alert on the page", waitFor, "An alert is present on the page", timeTook);
            return false;
        } else {
            this.reporter.pass("not to find an alert on the page", waitFor, NO_ALERT, timeTook);
            return true;
        }
    }

    /**
     * Checks that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText - the expected text of the alert
     */
    abstract void alertEquals(String expectedAlertText);

    /**
     * Checks that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedAlertText - the expected text of the alert
     * @param waitFor           - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook          - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the alert is present, the text of the alert
     */
    String checkAlertEquals(String expectedAlertText, double waitFor, double timeTook) {
        String alertWithText = "to find alert with the text <b>";
        // check for the object to be present
        String alert;
        boolean isAlertPresent = this.app.is().alertPresent();
        if (isAlertPresent) {
            alert = this.app.get().alert();
        } else {
            this.reporter.fail(alertWithText + expectedAlertText + ON_PAGE, waitFor, NO_ALERT, timeTook);
            return "";
        }
        if (!alert.equals(expectedAlertText)) {
            this.reporter.fail(alertWithText + expectedAlertText + ON_PAGE, waitFor, ALERT_TEXT + alert + B_PRESENT, timeTook);
        } else {
            this.reporter.pass(alertWithText + expectedAlertText + ON_PAGE, waitFor, ALERT_TEXT + alert + B_PRESENT, timeTook);
        }
        return alert;
    }

    /**
     * Checks that an alert present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertPattern - the expected text of the alert
     */
    abstract void alertMatches(String expectedAlertPattern);

    /**
     * Checks that an alert present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedAlertPattern - the expected text of the alert
     * @param waitFor              - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook             - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the alert is present, the text of the alert
     */
    String checkAlertMatches(String expectedAlertPattern, double waitFor, double timeTook) {
        String alertMatchingPattern = "to find alert with the text matching pattern <b>";
        // check for the object to be present
        String alert;
        boolean isAlertPresent = this.app.is().alertPresent();
        if (isAlertPresent) {
            alert = this.app.get().alert();
        } else {
            this.reporter.fail(alertMatchingPattern + expectedAlertPattern + ON_PAGE, waitFor, NO_ALERT, timeTook);
            return "";
        }
        if (!alert.matches(expectedAlertPattern)) {
            this.reporter.fail(alertMatchingPattern + expectedAlertPattern + ON_PAGE, waitFor, ALERT_TEXT + alert + B_PRESENT, timeTook);
        } else {
            this.reporter.pass(alertMatchingPattern + expectedAlertPattern + ON_PAGE, waitFor, ALERT_TEXT + alert + B_PRESENT, timeTook);
        }
        return alert;
    }

    /**
     * Checks that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void confirmationPresent();

    /**
     * Checks that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the confirmation is actually present
     */
    boolean checkConfirmationPresent(double waitFor, double timeTook) {
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = this.app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = this.app.get().confirmation();
        } else {
            this.reporter.fail(FIND_CONFIRMATION, waitFor, NO_CONFIRMATION, timeTook);
            return false;
        }
        this.reporter.pass(FIND_CONFIRMATION, waitFor, CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook);
        return true;
    }

    /**
     * Checks that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void confirmationNotPresent();

    /**
     * Checks that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the confirmation is actually present
     */
    boolean checkConfirmationNotPresent(double waitFor, double timeTook) {
        // check for the object to be present
        boolean isConfirmationPresent = this.app.is().confirmationPresent();
        if (isConfirmationPresent) {
            this.reporter.fail(FIND_CONFIRMATION, waitFor, " confirmation is present on the page", timeTook);
            return false;
        }
        this.reporter.pass(FIND_CONFIRMATION, waitFor, NO_CONFIRMATION, timeTook);
        return true;
    }

    /**
     * Checks that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText - the expected text of the confirmation
     */
    abstract void confirmationEquals(String expectedConfirmationText);

    /**
     * Checks that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedConfirmationText - the expected text of the confirmation
     * @param waitFor                  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook                 - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the confirmation is present, the text of the confirmation
     */
    String checkConfirmationEquals(String expectedConfirmationText, double waitFor, double timeTook) {
        String findConfirmationTest = "to find confirmation with the text <b>";
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = this.app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = this.app.get().confirmation();
        } else {
            this.reporter.fail(findConfirmationTest + expectedConfirmationText + ON_PAGE, waitFor, NO_CONFIRMATION, timeTook);
            return "";
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            this.reporter.fail(findConfirmationTest + expectedConfirmationText + ON_PAGE, waitFor, CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook);
        } else {
            this.reporter.pass(findConfirmationTest + expectedConfirmationText + ON_PAGE, waitFor, CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook);
        }
        return confirmation;
    }

    /**
     * Checks that a confirmation present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationPattern - the expected text of the confirmation
     */
    abstract void confirmationMatches(String expectedConfirmationPattern);

    /**
     * Checks that a confirmation present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedConfirmationPattern - the expected text of the confirmation
     * @param waitFor                     - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook                    - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the confirmation is present, the text of the confirmation
     */
    String checkConfirmationMatches(String expectedConfirmationPattern, double waitFor, double timeTook) {
        String confirmationMatchingPattern = "to find confirmation with the text matching pattern <b>";
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = this.app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = this.app.get().confirmation();
        } else {
            this.reporter.fail(confirmationMatchingPattern + expectedConfirmationPattern + ON_PAGE, waitFor, NO_CONFIRMATION, timeTook);
            return "";
        }
        if (!confirmation.matches(expectedConfirmationPattern)) {
            this.reporter.fail(confirmationMatchingPattern + expectedConfirmationPattern + ON_PAGE, waitFor, CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook);
        } else {
            this.reporter.pass(confirmationMatchingPattern + expectedConfirmationPattern + ON_PAGE, waitFor, CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook);
        }
        return confirmation;
    }

    /**
     * Checks that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void promptPresent();

    /**
     * Checks that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the prompt is actually present
     */
    boolean checkPromptPresent(double waitFor, double timeTook) {
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = this.app.is().promptPresent();
        if (isPromptPresent) {
            prompt = this.app.get().prompt();
        } else {
            this.reporter.fail("to find prompt on the page", waitFor, NO_PROMPT, timeTook);
            return false;
        }
        this.reporter.pass("to find prompt on the page", waitFor, PROMPT_TEXT + prompt + B_PRESENT, timeTook);
        return true;
    }

    /**
     * Checks that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void promptNotPresent();

    /**
     * Checks that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether or not the prompt is actually present
     */
    boolean checkPromptNotPresent(double waitFor, double timeTook) {
        // check for the object to be present
        boolean isPromptPresent = this.app.is().promptPresent();
        if (isPromptPresent) {
            this.reporter.fail("not to find prompt on the page", waitFor, " prompt is present on the page", timeTook);
        } else {
            this.reporter.pass("not to find prompt on the page", waitFor, NO_PROMPT, timeTook);
        }
        return !isPromptPresent;
    }

    /**
     * Checks that a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptText - the expected text of the prompt
     */
    abstract void promptEquals(String expectedPromptText);

    /**
     * Checks that a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedPromptText - the expected text of the prompt
     * @param waitFor            - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook           - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the prompt is present, the text of the prompt
     */
    String checkPromptEquals(String expectedPromptText, double waitFor, double timeTook) {
        String promptWithText = "to find prompt with the text <b>";
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = this.app.is().promptPresent();
        if (isPromptPresent) {
            prompt = this.app.get().prompt();
        } else {
            this.reporter.fail(promptWithText + expectedPromptText + ON_PAGE, waitFor, NO_PROMPT, timeTook);
            return "";
        }
        if (!expectedPromptText.equals(prompt)) {
            this.reporter.fail(promptWithText + expectedPromptText + ON_PAGE, waitFor, PROMPT_TEXT + prompt + B_PRESENT, timeTook);
        } else {
            this.reporter.pass(promptWithText + expectedPromptText + ON_PAGE, waitFor, PROMPT_TEXT + prompt + B_PRESENT, timeTook);
        }
        return prompt;
    }

    /**
     * Checks that a prompt present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptPattern - the expected text of the prompt
     */
    abstract void promptMatches(String expectedPromptPattern);

    /**
     * Checks that a prompt present on the page has content matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedPromptPattern - the expected text of the prompt
     * @param waitFor               - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook              - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the prompt is present, the text of the prompt
     */
    String checkPromptMatches(String expectedPromptPattern, double waitFor, double timeTook) {
        String promptTextMatching = "to find prompt with the text matching pattern <b>";
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = this.app.is().promptPresent();
        if (isPromptPresent) {
            prompt = this.app.get().prompt();
        } else {
            this.reporter.fail(promptTextMatching + expectedPromptPattern + ON_PAGE, waitFor, NO_PROMPT, timeTook);
            return "";
        }
        if (!prompt.matches(expectedPromptPattern)) {
            this.reporter.fail(promptTextMatching + expectedPromptPattern + ON_PAGE, waitFor, PROMPT_TEXT + prompt + B_PRESENT, timeTook);
        } else {
            this.reporter.pass(promptTextMatching + expectedPromptPattern + ON_PAGE, waitFor, PROMPT_TEXT + prompt + B_PRESENT, timeTook);
        }
        return prompt;
    }

    ///////////////////////////////////////////////////////
    // checks about cookies
    ///////////////////////////////////////////////////////

    /**
     * Checks that a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedCookieName - the name of the cookie
     */
    abstract void cookieExists(String expectedCookieName);

    /**
     * Checks that a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param expectedCookieName - the name of the cookie
     * @param waitFor            - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook           - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether the cookie is present or not
     */
    boolean checkCookieExists(String expectedCookieName, double waitFor, double timeTook) {
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = this.app.is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = this.app.get().cookieValue(expectedCookieName);
            this.reporter.pass(COOKIE_WITH_NAME + expectedCookieName + STORED, waitFor, COOKIE + expectedCookieName + VALUE_OF + cookieValue + STORED, timeTook);
        } else {
            this.reporter.fail(COOKIE_WITH_NAME + expectedCookieName + STORED, waitFor, COOKIE + expectedCookieName + NOT_STORED, timeTook);
        }
        return isCookiePresent;
    }

    /**
     * Checks that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName - the name of the cookie
     */
    abstract void cookieNotExists(String unexpectedCookieName);

    /**
     * Checks that a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param unexpectedCookieName - the name of the cookie
     * @param waitFor              - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook             - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: whether the cookie is present or not
     */
    boolean checkCookieNotExists(String unexpectedCookieName, double waitFor, double timeTook) {
        // check for the object to be present
        boolean isCookiePresent = this.app.is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            this.reporter.fail("to find no cookie with the name <b>" + unexpectedCookieName + STORED, waitFor, COOKIE + unexpectedCookieName + STORED, timeTook);
        } else {
            this.reporter.pass("to find no cookie with the name <b>" + unexpectedCookieName + STORED, waitFor, COOKIE + unexpectedCookieName + NOT_STORED, timeTook);
        }
        return !isCookiePresent;
    }

    /**
     * Checks that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          - the name of the cookie
     * @param expectedCookieValue - the expected value of the cookie
     */
    abstract void cookieEquals(String cookieName, String expectedCookieValue);

    /**
     * Checks that a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param cookieName          - the name of the cookie
     * @param expectedCookieValue - the expected value of the cookie
     * @param waitFor             - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook            - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the cookie is present, the value of the cookie, and null if the cookie isn't present
     */
    String checkCookieEquals(String cookieName, String expectedCookieValue, double waitFor, double timeTook) {
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = this.app.is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = this.app.get().cookieValue(cookieName);
        } else {
            this.reporter.fail(COOKIE_WITH_NAME + cookieName + VALUE_OF + expectedCookieValue + STORED, waitFor, COOKIE + cookieName + NOT_STORED, timeTook);
            return "";
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            this.reporter.fail(COOKIE_WITH_NAME + cookieName + VALUE_OF + expectedCookieValue + STORED, waitFor,
                    COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue, timeTook);
        } else {
            this.reporter.pass(COOKIE_WITH_NAME + cookieName + VALUE_OF + expectedCookieValue + STORED, waitFor, COOKIE + cookieName + VALUE_OF + cookieValue + STORED, timeTook);
        }
        return cookieValue;
    }

    /**
     * Checks that a cookies with the provided name has a value matching the
     * expected value pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName            - the name of the cookie
     * @param expectedCookiePattern - the expected value of the cookie
     */
    abstract void cookieMatches(String cookieName, String expectedCookiePattern);

    /**
     * Checks that a cookies with the provided name has a value matching the
     * expected value pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support. Handles the actual
     * logging for the verify, assert, and waitFor implementations
     *
     * @param cookieName            - the name of the cookie
     * @param expectedCookiePattern - the expected value of the cookie
     * @param waitFor               - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook              - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: if the cookie is present, the value of the cookie, and null if the cookie isn't present
     */
    String checkCookieMatches(String cookieName, String expectedCookiePattern, double waitFor, double timeTook) {
        String valueMatchingPattern = "</b> and a value matching pattern of <b>";
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = this.app.is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = this.app.get().cookieValue(cookieName);
        } else {
            this.reporter.fail(COOKIE_WITH_NAME + cookieName + valueMatchingPattern + expectedCookiePattern + STORED,
                    waitFor, COOKIE + cookieName + NOT_STORED, timeTook);
            return "";
        }
        if (!cookieValue.matches(expectedCookiePattern)) {
            this.reporter.fail(COOKIE_WITH_NAME + cookieName + valueMatchingPattern + expectedCookiePattern + STORED,
                    waitFor, COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue, timeTook);
        } else {
            this.reporter.pass(COOKIE_WITH_NAME + cookieName + valueMatchingPattern + expectedCookiePattern + STORED,
                    waitFor, COOKIE + cookieName + VALUE_OF + cookieValue + STORED, timeTook);
        }
        return cookieValue;
    }
}