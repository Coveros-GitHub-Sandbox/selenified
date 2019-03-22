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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.coveros.selenified.element.check.Constants.DEFAULT_POLLING_INTERVAL;

/**
 * WaitFor performs dynamic waits on the app in general, until a particular
 * condition of the application is met, not one for a particular page or
 * element. Nothing is ever returned. The default wait is 5 seconds, but can be
 * overridden. If the condition is not met in the allotted time, still nothing
 * is returned, but an error is logged
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/19/2019
 */
public class WaitFor implements Check {

    // this will be the name of the file we write all commands out to
    private final Reporter reporter;

    // this is the driver that will be used for all selenium actions
    private final App app;

    // the default wait for elements
    private double defaultWait = 5.0;

    /**
     * The default constructor passing in the app and output file
     *
     * @param app      - the application under test
     * @param reporter - the file to write all logging out to
     */
    public WaitFor(App app, Reporter reporter) {
        this.app = app;
        this.reporter = reporter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public App getApp() {
        return app;
    }

    /**
     * Changes the default wait time from 5.0 seconds to some custom number.
     *
     * @param seconds - how many seconds should WaitFor wait for the condition to be
     *                met
     */
    public void changeDefaultWait(double seconds) {
        defaultWait = seconds;
    }

    // ///////////////////////////////////////
    // waiting functionality
    // ///////////////////////////////////////

    /**
     * Waits up to the default wait time (5 seconds unless changed) for the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedURL the URL of the page
     */
    @Override
    public void urlEquals(String expectedURL) {
        urlEquals(defaultWait, expectedURL);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitle the friendly name of the page
     */
    @Override
    public void titleEquals(String expectedTitle) {
        titleEquals(defaultWait, expectedTitle);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for the provided title matches the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param expectedTitlePattern the friendly name of the page
     */
    @Override
    public void titleMatches(String expectedTitlePattern) {
        titleMatches(defaultWait, expectedTitlePattern);
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
        textPresent(defaultWait, expectedText);
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
        textNotPresent(defaultWait, expectedText);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void alertPresent() {
        alertPresent(defaultWait);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void alertNotPresent() {
        alertNotPresent(defaultWait);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText the expected text of the alert
     */
    @Override
    public void alertEquals(String expectedAlertText) {
        alertEquals(defaultWait, expectedAlertText);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for an alert present on the page has content matching the
     * expected patten. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertPattern the expected text of the alert
     */
    @Override
    public void alertMatches(String expectedAlertPattern) {
        alertMatches(defaultWait, expectedAlertPattern);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void confirmationPresent() {
        confirmationPresent(defaultWait);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void confirmationNotPresent() {
        confirmationNotPresent(defaultWait);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText the expected text of the confirmation
     */
    @Override
    public void confirmationEquals(String expectedConfirmationText) {
        confirmationEquals(defaultWait, expectedConfirmationText);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a confirmation present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationPattern the expected text of the confirmation
     */
    @Override
    public void confirmationMatches(String expectedConfirmationPattern) {
        confirmationMatches(defaultWait, expectedConfirmationPattern);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void promptPresent() {
        promptPresent(defaultWait);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    @Override
    public void promptNotPresent() {
        promptNotPresent(defaultWait);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptText the expected text of the prompt
     */
    @Override
    public void promptEquals(String expectedPromptText) {
        promptEquals(defaultWait, expectedPromptText);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a prompt present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptPattern the expected text of the prompt
     */
    @Override
    public void promptMatches(String expectedPromptPattern) {
        promptMatches(defaultWait, expectedPromptPattern);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedCookieName the name of the cookie
     */
    @Override
    public void cookieExists(String expectedCookieName) {
        cookieExists(defaultWait, expectedCookieName);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName the name of the cookie
     */
    @Override
    public void cookieNotExists(String unexpectedCookieName) {
        cookieNotExists(defaultWait, unexpectedCookieName);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     */
    @Override
    public void cookieEquals(String cookieName, String expectedCookieValue) {
        cookieEquals(defaultWait, cookieName, expectedCookieValue);
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for a cookies with the provided name has a value matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName            the name of the cookie
     * @param expectedCookiePattern the expected value of the cookie
     */
    @Override
    public void cookieMatches(String cookieName, String expectedCookiePattern) {
        cookieMatches(defaultWait, cookieName, expectedCookiePattern);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * Asserts that the provided URL equals the actual URL the application is
     * currently on. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param seconds     the number of seconds to wait
     * @param expectedURL - the expectedURL to wait for
     */
    public void urlEquals(double seconds, String expectedURL) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.urlToBe(expectedURL));
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkUrlEquals(expectedURL, seconds, timeTook);
        } catch (TimeoutException e) {
            checkUrlEquals(expectedURL, seconds, seconds);
        }
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for the provided title equals the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param seconds       the number of seconds to wait
     * @param expectedTitle - the title to wait for
     */
    public void titleEquals(double seconds, String expectedTitle) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.titleIs(expectedTitle));
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkTitleEquals(expectedTitle, seconds, timeTook);
        } catch (TimeoutException e) {
            checkTitleEquals(expectedTitle, seconds, seconds);
        }
    }

    /**
     * Waits up to the default wait time (5 seconds unless changed) for the provided title matches the actual title of the current page
     * the application is on. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param seconds       the number of seconds to wait
     * @param expectedTitle - the title to wait for
     */
    public void titleMatches(double seconds, String expectedTitle) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.get().title().matches(expectedTitle) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkTitleMatches(expectedTitle, seconds, timeTook);
    }

    /**
     * Wait for a popup to be present, and
     * then returns the amount of time that was waited
     *
     * @param seconds - maximum time to wait in seconds
     * @return double - the total time waited
     */
    private double popup(double seconds) {
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
        wait.until(ExpectedConditions.alertIsPresent());
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Wait for a popup to have the expected text, and then returns the
     * amount of time that was waited
     *
     * @param seconds           - maximum amount of time to wait in seconds
     * @param expectedPopupText - the expected text to wait for
     * @return double: the total time waited
     */
    private double popupEquals(double seconds, String expectedPopupText) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.get().alert().equals(expectedPopupText) && System.currentTimeMillis() < end) ;
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Wait for a popup to have the expected text, and then returns the
     * amount of time that was waited
     *
     * @param seconds              - maximum amount of time to wait in seconds
     * @param expectedPopupPattern - the expected pattern to wait for
     * @return double: the total time waited
     */
    private double popupMatches(double seconds, String expectedPopupPattern) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.get().alert().matches(expectedPopupPattern) && System.currentTimeMillis() < end) ;
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Wait for a popup to be present and
     * then returns the amount of time that was waited
     *
     * @param seconds - maximum time to wait in seconds
     * @return double - the total time waited
     */
    private double noPopup(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
        wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Asserts that an alert is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param seconds the number of seconds to wait
     */
    public void alertPresent(double seconds) {
        try {
            double timeTook = popup(seconds);
            checkAlertPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkAlertPresent(seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for an alert is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param seconds the number of seconds to wait
     */
    public void alertNotPresent(double seconds) {
        try {
            double timeTook = noPopup(seconds);
            checkAlertNotPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkAlertNotPresent(seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for an alert present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertText the expected text of the alert
     * @param seconds           the number of seconds to wait
     */
    public void alertEquals(double seconds, String expectedAlertText) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupEquals(seconds - timeTook, expectedAlertText);
            checkAlertEquals(expectedAlertText, seconds, timeTook);
        } catch (TimeoutException e) {
            checkAlertEquals(expectedAlertText, seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for an alert present on the page has content matching the
     * expected patten. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAlertPattern the expected text of the alert
     * @param seconds              the number of seconds to wait
     */
    public void alertMatches(double seconds, String expectedAlertPattern) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupMatches(seconds - timeTook, expectedAlertPattern);
            checkAlertMatches(expectedAlertPattern, seconds, timeTook);
        } catch (TimeoutException e) {
            checkAlertMatches(expectedAlertPattern, seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a confirmation is present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param seconds the number of seconds to wait
     */
    public void confirmationPresent(double seconds) {
        try {
            double timeTook = popup(seconds);
            checkConfirmationPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkConfirmationPresent(seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a confirmation is not present on the page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param seconds the number of seconds to wait
     */
    public void confirmationNotPresent(double seconds) {
        try {
            double timeTook = noPopup(seconds);
            checkConfirmationNotPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkConfirmationNotPresent(seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a confirmation present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationText the expected text of the confirmation
     * @param seconds                  the number of seconds to wait
     */
    public void confirmationEquals(double seconds, String expectedConfirmationText) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupEquals(seconds - timeTook, expectedConfirmationText);
            checkConfirmationEquals(expectedConfirmationText, seconds, timeTook);
        } catch (TimeoutException e) {
            checkConfirmationEquals(expectedConfirmationText, seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a confirmation present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedConfirmationPattern the expected text of the confirmation
     * @param seconds                     the number of seconds to wait
     */
    public void confirmationMatches(double seconds, String expectedConfirmationPattern) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupMatches(seconds - timeTook, expectedConfirmationPattern);
            checkConfirmationMatches(expectedConfirmationPattern, seconds, timeTook);
        } catch (TimeoutException e) {
            checkConfirmationMatches(expectedConfirmationPattern, seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a prompt is present on the page. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param seconds the number of seconds to wait
     */
    public void promptPresent(double seconds) {
        try {
            double timeTook = popup(seconds);
            checkPromptPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkPromptPresent(seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a prompt is not present on the page. This information will
     * be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param seconds the number of seconds to wait
     */
    public void promptNotPresent(double seconds) {
        try {
            double timeTook = noPopup(seconds);
            checkPromptNotPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkPromptNotPresent(seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a prompt present on the page has content equal to the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptText the expected text of the prompt
     * @param seconds            the number of seconds to wait
     */
    public void promptEquals(double seconds, String expectedPromptText) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupEquals(seconds - timeTook, expectedPromptText);
            checkPromptEquals(expectedPromptText, seconds, timeTook);
        } catch (TimeoutException e) {
            checkPromptEquals(expectedPromptText, seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for a prompt present on the page has content matching the
     * expected text. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPromptPattern the expected text of the prompt
     * @param seconds               the number of seconds to wait
     */
    public void promptMatches(double seconds, String expectedPromptPattern) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupMatches(seconds - timeTook, expectedPromptPattern);
            checkPromptMatches(expectedPromptPattern, seconds, timeTook);
        } catch (TimeoutException e) {
            checkPromptMatches(expectedPromptPattern, seconds, seconds);
        }
    }

    /**
     * Waits up to the provided wait time for provided text(s) are on the current page. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param expectedText the expected text to be present
     * @param seconds      the number of seconds to wait
     */
    public void textPresent(double seconds, String expectedText) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.is().textPresent(expectedText) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkTextPresent(expectedText, seconds, timeTook);
    }

    /**
     * Waits up to the provided wait time for provided text(s) are not on the current page. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param expectedText the expected text to be not present
     * @param seconds      the number of seconds to wait
     */
    public void textNotPresent(double seconds, String expectedText) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().textPresent(expectedText) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkTextNotPresent(expectedText, seconds, timeTook);
    }

    /**
     * Waits up to the provided wait time for a cookie exists in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedCookieName the name of the cookie
     * @param seconds            the number of seconds to wait
     */
    public void cookieExists(double seconds, String expectedCookieName) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.is().cookiePresent(expectedCookieName) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieExists(expectedCookieName, seconds, timeTook);
    }

    /**
     * Waits up to the provided wait time for a cookie doesn't exist in the application with the provided
     * cookieName. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedCookieName the name of the cookie
     * @param seconds              the number of seconds to wait
     */
    public void cookieNotExists(double seconds, String unexpectedCookieName) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().cookiePresent(unexpectedCookieName) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieNotExists(unexpectedCookieName, seconds, timeTook);
    }

    /**
     * Waits up to the provided wait time for a cookies with the provided name has a value equal to the
     * expected value. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName          the name of the cookie
     * @param expectedCookieValue the expected value of the cookie
     * @param seconds             the number of seconds to wait
     */
    public void cookieEquals(double seconds, String cookieName, String expectedCookieValue) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().cookiePresent(cookieName) && System.currentTimeMillis() < end) ;
        if (app.is().cookiePresent(cookieName)) {
            while (!app.get().cookieValue(cookieName).equals(expectedCookieValue) && System.currentTimeMillis() < end) ;
        }
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieEquals(cookieName, expectedCookieValue, seconds, timeTook);
    }

    /**
     * Waits up to the provided wait time for a cookies with the provided name has a value matching the
     * expected pattern. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param cookieName            the name of the cookie
     * @param expectedCookiePattern the expected value of the cookie
     * @param seconds               the number of seconds to wait
     */
    public void cookieMatches(double seconds, String cookieName, String expectedCookiePattern) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().cookiePresent(cookieName) && System.currentTimeMillis() < end) ;
        if (app.is().cookiePresent(cookieName)) {
            while (!app.get().cookieValue(cookieName).matches(expectedCookiePattern) && System.currentTimeMillis() < end) ;
        }
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieMatches(cookieName, expectedCookiePattern, seconds, timeTook);
    }

}