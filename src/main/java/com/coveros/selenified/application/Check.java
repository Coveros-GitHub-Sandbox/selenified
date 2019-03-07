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

import static com.coveros.selenified.element.check.Constants.*;

/**
 * Assert will handle all verifications performed on the actual application
 * itself. These checks are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 3/5/2019
 */
public interface Check {

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
     * Checks that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL - the URL of the page
     */
    void urlEquals(String expectedURL);

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
    default String checkUrlEquals(String expectedURL, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to be on page with the URL of <b>" + expectedURL + "</b>", waitFor);
        String actualURL = getApp().get().url();
        if (!actualURL.equals(expectedURL)) {
            getOutputFile().recordActual("The page URL reads <b>" + actualURL + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual("The page URL reads <b>" + actualURL + "</b>", timeTook, Success.PASS);
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
    void titleEquals(String expectedTitle);

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
    default String checkTitleEquals(String expectedTitle, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to be on page with the title of <b>" + expectedTitle + "</b>", waitFor);
        String actualTitle = getApp().get().title();
        if (!actualTitle.equals(expectedTitle)) {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", timeTook, Success.PASS);
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
    void titleMatches(String expectedTitlePattern);

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
    default String checkTitleMatches(String expectedTitlePattern, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to be on page with the title matching pattern <b>" + expectedTitlePattern + "</b>", waitFor);
        String actualTitle = getApp().get().title();
        if (!actualTitle.matches(expectedTitlePattern)) {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual("The page title reads <b>" + actualTitle + "</b>", timeTook, Success.PASS);
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
    void textPresent(String expectedText);

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
    default boolean checkTextPresent(String expectedText, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to find text <b>" + expectedText + "</b> present on the page", waitFor);
        // check for the object to be present
        boolean isPresent = getApp().is().textPresent(expectedText);
        if (!isPresent) {
            getOutputFile().recordActual(TEXT_B + expectedText + "</b> is not present on the page", timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(TEXT_B + expectedText + B_PRESENT, timeTook, Success.PASS);
        return true;
    }

    /**
     * Checks that provided text are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText - the expected text to be not present
     */
    void textNotPresent(String expectedText);

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
    default boolean checkTextNotPresent(String expectedText, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("not to find text <b>" + expectedText + "</b> present on the page", waitFor);
        // check for the object to be present
        boolean isPresent = getApp().is().textPresent(expectedText);
        if (isPresent) {
            getOutputFile().recordActual(TEXT_B + expectedText + B_PRESENT, timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(TEXT_B + expectedText + "</b> is not present on the page", timeTook, Success.PASS);
        return true;
    }

    ///////////////////////////////////////////////////////
    // checks about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * Checks that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void alertPresent();

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
    default boolean checkAlertPresent(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to find an alert on the page", waitFor);
        // check for the object to be present
        String alert;
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(NO_ALERT, timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(ALERT_TEXT + alert + B_PRESENT, timeTook, Success.PASS);
        return true;
    }

    /**
     * Checks that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void alertNotPresent();

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
    default boolean checkAlertNotPresent(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("not to find an alert on the page", waitFor);
        // check for the object to be present
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            getOutputFile().recordActual("An alert is present on the page", timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(NO_ALERT, timeTook, Success.PASS);
        return true;
    }

    /**
     * Checks that an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText - the expected text of the alert
     */
    void alertEquals(String expectedAlertText);

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
    default String checkAlertEquals(String expectedAlertText, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to find alert with the text <b>" + expectedAlertText + ON_PAGE, waitFor);
        // check for the object to be present
        String alert;
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(NO_ALERT, timeTook, Success.FAIL);
            return "";
        }
        if (!alert.equals(expectedAlertText)) {
            getOutputFile().recordActual(ALERT_TEXT + alert + B_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(ALERT_TEXT + alert + B_PRESENT, timeTook, Success.PASS);
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
    void alertMatches(String expectedAlertPattern);

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
    default String checkAlertMatches(String expectedAlertPattern, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction("to find alert with the text matching pattern <b>" + expectedAlertPattern + ON_PAGE, waitFor);
        // check for the object to be present
        String alert;
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(NO_ALERT, timeTook, Success.FAIL);
            return "";
        }
        if (!alert.matches(expectedAlertPattern)) {
            getOutputFile().recordActual(ALERT_TEXT + alert + B_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(ALERT_TEXT + alert + B_PRESENT, timeTook, Success.PASS);
        }
        return alert;
    }

    /**
     * Checks that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void confirmationPresent();

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
    default boolean checkConfirmationPresent(double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find a confirmation on the page", waitFor);
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(NO_CONFIRMATION, timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook, Success.PASS);
        return true;
    }

    /**
     * Checks that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void confirmationNotPresent();

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
    default boolean checkConfirmationNotPresent(double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find a confirmation on the page", waitFor);
        // check for the object to be present
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            getOutputFile().recordActual(" confirmation is present on the page", timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(NO_CONFIRMATION, timeTook, Success.PASS);
        return true;
    }

    /**
     * Checks that a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText - the expected text of the confirmation
     */
    void confirmationEquals(String expectedConfirmationText);

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
    default String checkConfirmationEquals(String expectedConfirmationText, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find confirmation with the text <b>" + expectedConfirmationText + ON_PAGE, waitFor);
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(NO_CONFIRMATION, timeTook, Success.FAIL);
            return "";
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            getOutputFile().recordActual(CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook, Success.PASS);
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
    void confirmationMatches(String expectedConfirmationPattern);

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
    default String checkConfirmationMatches(String expectedConfirmationPattern, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find confirmation with the text matching pattern <b>" + expectedConfirmationPattern + ON_PAGE, waitFor);
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(NO_CONFIRMATION, timeTook, Success.FAIL);
            return "";
        }
        if (!confirmation.matches(expectedConfirmationPattern)) {
            getOutputFile().recordActual(CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(CONFIRMATION_TEXT + confirmation + B_PRESENT, timeTook, Success.PASS);
        }
        return confirmation;
    }

    /**
     * Checks that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void promptPresent();

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
    default boolean checkPromptPresent(double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find prompt on the page", waitFor);
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(NO_PROMPT, timeTook, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(PROMPT_TEXT + prompt + B_PRESENT, timeTook, Success.PASS);
        return true;
    }

    /**
     * Checks that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void promptNotPresent();

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
    default boolean checkPromptNotPresent(double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("not to find prompt on the page", waitFor);
        // check for the object to be present
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            getOutputFile().recordActual(" prompt is present on the page", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(NO_PROMPT, timeTook, Success.PASS);
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
    void promptEquals(String expectedPromptText);

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
    default String checkPromptEquals(String expectedPromptText, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find prompt with the text <b>" + expectedPromptText + ON_PAGE, waitFor);
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(NO_PROMPT, timeTook, Success.FAIL);
            return "";
        }
        if (!expectedPromptText.equals(prompt)) {
            getOutputFile().recordActual(PROMPT_TEXT + prompt + B_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(PROMPT_TEXT + prompt + B_PRESENT, timeTook, Success.PASS);
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
    void promptMatches(String expectedPromptPattern);

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
    default String checkPromptMatches(String expectedPromptPattern, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find prompt with the text matching pattern <b>" + expectedPromptPattern + ON_PAGE, waitFor);
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(NO_PROMPT, timeTook, Success.FAIL);
            return "";
        }
        if (!prompt.matches(expectedPromptPattern)) {
            getOutputFile().recordActual(PROMPT_TEXT + prompt + B_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(PROMPT_TEXT + prompt + B_PRESENT, timeTook, Success.PASS);
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
    void cookieExists(String expectedCookieName);

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
    default boolean checkCookieExists(String expectedCookieName, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find cookie with the name <b>" + expectedCookieName + STORED, waitFor);
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = getApp().is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(expectedCookieName);
            getOutputFile().recordActual(COOKIE + expectedCookieName + VALUE_OF + cookieValue + STORED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(COOKIE + expectedCookieName + NOT_STORED, timeTook, Success.FAIL);
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
    void cookieNotExists(String unexpectedCookieName);

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
    default boolean checkCookieNotExists(String unexpectedCookieName, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction("to find no cookie with the name <b>" + unexpectedCookieName + STORED, waitFor);
        // check for the object to be present
        boolean isCookiePresent = getApp().is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            getOutputFile().recordActual(COOKIE + unexpectedCookieName + STORED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(COOKIE + unexpectedCookieName + NOT_STORED, timeTook, Success.PASS);
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
    void cookieEquals(String cookieName, String expectedCookieValue);

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
    default String checkCookieEquals(String cookieName, String expectedCookieValue, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction(
                "to find cookie with the name <b>" + cookieName + VALUE_OF + expectedCookieValue + STORED, waitFor);
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = getApp().is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(cookieName);
        } else {
            getOutputFile().recordActual(COOKIE + cookieName + NOT_STORED, timeTook, Success.FAIL);
            return "";
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            getOutputFile().recordActual(
                    COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue,
                    timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(COOKIE + cookieName + VALUE_OF + cookieValue + STORED, timeTook, Success.PASS);
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
    void cookieMatches(String cookieName, String expectedCookiePattern);

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
    default String checkCookieMatches(String cookieName, String expectedCookiePattern, double waitFor, double timeTook) {
        //record the action
        getOutputFile().recordAction(
                "to find cookie with the name <b>" + cookieName + "</b> and a value matching pattern of <b>" + expectedCookiePattern + STORED, waitFor);
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = getApp().is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(cookieName);
        } else {
            getOutputFile().recordActual(COOKIE + cookieName + NOT_STORED, timeTook, Success.FAIL);
            return "";
        }
        if (!cookieValue.matches(expectedCookiePattern)) {
            getOutputFile().recordActual(
                    COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue,
                    timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(COOKIE + cookieName + VALUE_OF + cookieValue + STORED, timeTook, Success.PASS);
        }
        return cookieValue;
    }
}