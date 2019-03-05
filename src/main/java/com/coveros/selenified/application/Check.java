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
    String NOALERT = "n alert is not present on the page";
    String ALERTTEXT = "n alert with text <b>";
    String NOCONFIRMATION = " confirmation is not present on the page";
    String CONFIRMATIONTEXT = " confirmation with text <b>";
    String NOPROMPT = " prompt is not present on the page";
    String PROMPTTEXT = " prompt with text <b>";

    String STORED = "</b> is stored for the page";
    String NOTSTORED = "</b> is not stored for the page";
    String VALUE = "</b> and a value of <b>";
    String COOKIE = " cookie with the name <b>";

    String TEXT = "he text <b>";
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

    default void recordAction(String check, double waitFor) {
        String action = "";
        if( waitFor > 0 ) {
            action = "Waiting up to " + waitFor + " seconds " + check;
        }
        getOutputFile().recordAction(action, "Expected " + check);
    }

    default void recordActual(String check, double timeTook, Success success) {
        String actual = check;
        if( timeTook > 0) {
            String lowercase = actual.substring(0,1).toLowerCase();
            actual = "After waiting for " + timeTook + " seconds, " + lowercase + actual.substring(1);
        }
        getOutputFile().recordActual(actual, success);
    }

    /**
     * Verifies that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL the URL of the page
     */
    void urlEquals(String expectedURL);

    default String checkUrlEquals(String expectedURL, double waitFor, double timeTook) {
        // record the action
        recordAction("to be on page with the URL of <b>" + expectedURL + "</b>", waitFor);
        String actualURL = getApp().get().location();
        if (!actualURL.equals(expectedURL)) {
            recordActual("The page URL reads <b>" + actualURL + "</b>", timeTook, Success.FAIL);
        } else {
            recordActual("The page URL reads <b>" + actualURL + "</b>", timeTook, Success.PASS);
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

    default String checkTitleEquals(String expectedTitle, String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected to be on page with the title of <b>" + expectedTitle + "</b>");
        String actualTitle = getApp().get().title();
        if (!actualTitle.equals(expectedTitle)) {
            getOutputFile().recordActual(waitFor + "he page title reads <b>" + actualTitle + "</b>", Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + "he page title reads <b>" + actualTitle + "</b>", Success.PASS);
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

    default String checkTitleMatches(String expectedTitlePattern, String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected to be on page with the title matching pattern <b>" + expectedTitlePattern + "</b>");
        String actualTitle = getApp().get().title();
        if (!actualTitle.matches(expectedTitlePattern)) {
            getOutputFile().recordActual(waitFor + "he page title reads <b>" + actualTitle + "</b>", Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + "he page title reads <b>" + actualTitle + "</b>", Success.PASS);
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

    default boolean checkTextPresent(String expectedText, String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected to find text <b>" + expectedText + "</b> present on the page");
        // check for the object to be present
        boolean isPresent = getApp().is().textPresent(expectedText);
        if (!isPresent) {
            getOutputFile().recordActual(waitFor + TEXT + expectedText + "</b> is not present on the page", Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + TEXT + expectedText + PRESENT, Success.PASS);
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

    default boolean checkTextNotPresent(String expectedText, String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected not to find text <b>" + expectedText + "</b> present on the page");
        // check for the object to be present
        boolean isPresent = getApp().is().textPresent(expectedText);
        if (isPresent) {
            getOutputFile().recordActual(waitFor + TEXT + expectedText + PRESENT, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + TEXT + expectedText + "</b> is not present on the page", Success.PASS);
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

    default boolean checkAlertPresent(String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected to find an alert on the page");
        // check for the object to be present
        String alert;
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(waitFor + NOALERT, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + ALERTTEXT + alert + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void alertNotPresent();

    default boolean checkAlertNotPresent(String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected not to find an alert on the page");
        // check for the object to be present
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            getOutputFile().recordActual(waitFor + "n alert is present on the page", Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + NOALERT, Success.PASS);
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

    default String checkAlertEquals(String expectedAlertText, String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected to find alert with the text <b>" + expectedAlertText + ONPAGE);
        // check for the object to be present
        String alert;
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(waitFor + NOALERT, Success.FAIL);
            return "";
        }
        if (!alert.equals(expectedAlertText)) {
            getOutputFile().recordActual(waitFor + ALERTTEXT + alert + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + ALERTTEXT + alert + PRESENT, Success.PASS);
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

    default String checkAlertMatches(String expectedAlertPattern, String waitFor) {
        // record the action
        getOutputFile().recordExpected("Expected to find alert with the text matching pattern <b>" + expectedAlertPattern + ONPAGE);
        // check for the object to be present
        String alert;
        boolean isAlertPresent = getApp().is().alertPresent();
        if (isAlertPresent) {
            alert = getApp().get().alert();
        } else {
            getOutputFile().recordActual(waitFor + NOALERT, Success.FAIL);
            return "";
        }
        if (!alert.matches(expectedAlertPattern)) {
            getOutputFile().recordActual(waitFor + ALERTTEXT + alert + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + ALERTTEXT + alert + PRESENT, Success.PASS);
        }
        return alert;
    }

    /**
     * Verifies that a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void confirmationPresent();

    default boolean checkConfirmationPresent(String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find a confirmation on the page");
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(waitFor + NOCONFIRMATION, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void confirmationNotPresent();

    default boolean checkConfirmationNotPresent(String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find a confirmation on the page");
        // check for the object to be present
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            getOutputFile().recordActual(waitFor + " confirmation is present on the page", Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + NOCONFIRMATION, Success.PASS);
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

    default String checkConfirmationEquals(String expectedConfirmationText, String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationText + ONPAGE);
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(waitFor + NOCONFIRMATION, Success.FAIL);
            return "";
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            getOutputFile().recordActual(waitFor + CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
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

    default String checkConfirmationMatches(String expectedConfirmationPattern, String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find confirmation with the text matching pattern <b>" + expectedConfirmationPattern + ONPAGE);
        // check for the object to be present
        String confirmation;
        boolean isConfirmationPresent = getApp().is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = getApp().get().confirmation();
        } else {
            getOutputFile().recordActual(waitFor + NOCONFIRMATION, Success.FAIL);
            return "";
        }
        if (!confirmation.matches(expectedConfirmationPattern)) {
            getOutputFile().recordActual(waitFor + CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        }
        return confirmation;
    }

    /**
     * Verifies that a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void promptPresent();

    default boolean checkPromptPresent(String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find prompt on the page");
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(waitFor + NOPROMPT, Success.FAIL);
            return false;
        }
        getOutputFile().recordActual(waitFor + PROMPTTEXT + prompt + PRESENT, Success.PASS);
        return true;
    }

    /**
     * Verifies that a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void promptNotPresent();

    default boolean checkPromptNotPresent(String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected not to find prompt on the page");
        // check for the object to be present
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            getOutputFile().recordActual(waitFor + " prompt is present on the page", Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + NOPROMPT, Success.PASS);
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

    default String checkPromptEquals(String expectedPromptText, String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find prompt with the text <b>" + expectedPromptText + ONPAGE);
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(waitFor + NOPROMPT, Success.FAIL);
            return "";
        }
        if (!expectedPromptText.equals(prompt)) {
            getOutputFile().recordActual(waitFor + PROMPTTEXT + prompt + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + PROMPTTEXT + prompt + PRESENT, Success.PASS);
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

    default String checkPromptMatches(String expectedPromptPattern, String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find prompt with the text matching pattern <b>" + expectedPromptPattern + ONPAGE);
        // check for the object to be present
        String prompt;
        boolean isPromptPresent = getApp().is().promptPresent();
        if (isPromptPresent) {
            prompt = getApp().get().prompt();
        } else {
            getOutputFile().recordActual(waitFor + NOPROMPT, Success.FAIL);
            return "";
        }
        if (!prompt.matches(expectedPromptPattern)) {
            getOutputFile().recordActual(waitFor + PROMPTTEXT + prompt + PRESENT, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + PROMPTTEXT + prompt + PRESENT, Success.PASS);
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

    default boolean checkCookieExists(String expectedCookieName, String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = getApp().is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(expectedCookieName);
            getOutputFile().recordActual(waitFor + COOKIE + expectedCookieName + VALUE + cookieValue + STORED, Success.PASS);
        } else {
            getOutputFile().recordActual(waitFor + COOKIE + expectedCookieName + NOTSTORED, Success.FAIL);
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

    default boolean checkCookieNotExists(String unexpectedCookieName, String waitFor) {
        //record the action
        getOutputFile().recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
        // check for the object to be present
        boolean isCookiePresent = getApp().is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            getOutputFile().recordActual(waitFor + COOKIE + unexpectedCookieName + STORED, Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + COOKIE + unexpectedCookieName + NOTSTORED, Success.PASS);
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

    default String checkCookieEquals(String cookieName, String expectedCookieValue, String waitFor) {
        //record the action
        getOutputFile().recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = getApp().is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(cookieName);
        } else {
            getOutputFile().recordActual(waitFor + COOKIE + cookieName + NOTSTORED, Success.FAIL);
            return "";
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            getOutputFile().recordActual(
                    waitFor + COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue,
                    Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
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

    default String checkCookieMatches(String cookieName, String expectedCookiePattern, String waitFor) {
        //record the action
        getOutputFile().recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + "</b> and a value matching pattern of <b>" + expectedCookiePattern + STORED);
        // check for the object to be present
        String cookieValue;
        boolean isCookiePresent = getApp().is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = getApp().get().cookieValue(cookieName);
        } else {
            getOutputFile().recordActual(waitFor + COOKIE + cookieName + NOTSTORED, Success.FAIL);
            return "";
        }
        if (!cookieValue.matches(expectedCookiePattern)) {
            getOutputFile().recordActual(
                    waitFor + COOKIE + cookieName + "</b> is stored for the page, but the value of the cookie is " + cookieValue,
                    Success.FAIL);
        } else {
            getOutputFile().recordActual(waitFor + COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
        }
        return cookieValue;
    }
}