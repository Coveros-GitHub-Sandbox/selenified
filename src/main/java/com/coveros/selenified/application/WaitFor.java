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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WaitFor performs dynamic waits on the app in general, until a particular
 * condition of the application is met, not one for a particular page or
 * element. Nothing is ever returned. The default wait is 5 seconds, but can be
 * overridden. If the condition is not met in the allotted time, still nothing
 * is returned, but an error is logged
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 3/3/2019
 */
public class WaitFor implements Check {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // this is the driver that will be used for all selenium actions
    private final App app;

    // constants
    private double defaultWait = 5.0;

    public WaitFor(App app, OutputFile file) {
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
     * Wait up to the default time (5 seconds) for a location to show in url
     */
    @Override
    public void urlEquals(String location) {
        urlEquals(defaultWait, location);
    }

    /**
     * Wait up to the default time (5 seconds) for a title to show in the app
     */
    @Override
    public void titleEquals(String title) {
        titleEquals(defaultWait, title);
    }

    /**
     * Wait up to the default time (5 seconds) for a title to show in the app
     */
    @Override
    public void titleMatches(String title) {
        titleMatches(defaultWait, title);
    }

    /**
     * Wait up to the default time (5 seconds) for text to be present in the app
     *
     * @param expectedText the expected text to be present
     */
    @Override
    public void textPresent(String expectedText) {
        textPresent(defaultWait, expectedText);
    }

    /**
     * Wait up to the default time (5 seconds) for text to not be present in the app
     *
     * @param expectedText the expected text to be not present
     */
    @Override
    public void textNotPresent(String expectedText) {
        textNotPresent(defaultWait, expectedText);
    }

    /**
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    @Override
    public void alertPresent() {
        alertPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    @Override
    public void alertNotPresent() {
        alertNotPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    @Override
    public void alertEquals(String expectedAlertText) {
        alertEquals(defaultWait, expectedAlertText);
    }

    /**
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    @Override
    public void alertMatches(String expectedAlertPattern) {
        alertMatches(defaultWait, expectedAlertPattern);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to be present
     */
    @Override
    public void confirmationPresent() {
        confirmationPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to not be present
     */
    @Override
    public void confirmationNotPresent() {
        confirmationNotPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to be present
     */
    @Override
    public void confirmationEquals(String expectedConfirmationText) {
        confirmationEquals(defaultWait, expectedConfirmationText);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to not be present
     */
    @Override
    public void confirmationMatches(String expectedConfirmationPattern) {
        confirmationMatches(defaultWait, expectedConfirmationPattern);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    @Override
    public void promptPresent() {
        promptPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    @Override
    public void promptNotPresent() {
        promptNotPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    @Override
    public void promptEquals(String expectedPromptText) {
        promptEquals(defaultWait, expectedPromptText);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    @Override
    public void promptMatches(String expectedPromptPattern) {
        promptMatches(defaultWait, expectedPromptPattern);
    }

    @Override
    public void cookieExists(String expectedCookieName) {
        cookieExists(defaultWait, expectedCookieName);
    }

    @Override
    public void cookieNotExists(String unexpectedCookieName) {
        cookieNotExists(defaultWait, unexpectedCookieName);
    }

    @Override
    public void cookieEquals(String cookieName, String expectedCookieValue) {
        cookieEquals(defaultWait, cookieName, expectedCookieValue);
    }

    @Override
    public void cookieMatches(String cookieName, String expectedCookiePattern) {
        cookieMatches(defaultWait, cookieName, expectedCookiePattern);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * Wait up to a specified time for the url to show a particular expectedURL
     *
     * @param seconds     - the number of seconds to wait
     * @param expectedURL - the expectedURL to wait for
     */
    public void urlEquals(double seconds, String expectedURL) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds);
            wait.until(ExpectedConditions.urlToBe(expectedURL));
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkUrlEquals(expectedURL, seconds, timeTook);
        } catch (TimeoutException e) {
            checkUrlEquals(expectedURL, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for the title to show up in the application
     *
     * @param seconds - the number of seconds to wait
     * @param title   - the title to wait for
     */
    public void titleEquals(double seconds, String title) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds);
            wait.until(ExpectedConditions.titleIs(title));
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkTitleEquals(title, seconds, timeTook);
        } catch (TimeoutException e) {
            checkTitleEquals(title, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for the title to show up in the application
     *
     * @param seconds - the number of seconds to wait
     * @param title   - the title to wait for
     */
    public void titleMatches(double seconds, String title) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.get().title().matches(title) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkTitleMatches(title, seconds, timeTook);
        if (!app.get().title().matches(title)) {
            file.addError();
        }
    }

    /**
     * Wait for a popup to be present, up to the default time (5 seconds), and
     * then returns the amount of time that was waited
     *
     * @param seconds - maximum time to wait in seconds
     * @return double - the total time waited
     */
    private double popup(double seconds) {
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        WebDriverWait wait = new WebDriverWait(app.getDriver(), (long) seconds);
        wait.until(ExpectedConditions.alertIsPresent());
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    private double popupEquals(double seconds, String text) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.get().alert().equals(text) && System.currentTimeMillis() < end) ;
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    private double popupMatches(double seconds, String pattern) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.get().alert().matches(pattern) && System.currentTimeMillis() < end) ;
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Wait for a popup to be present, up to the default time (5 seconds), and
     * then returns the amount of time that was waited
     *
     * @param seconds - maximum time to wait in seconds
     * @return double - the total time waited
     */
    private double noPopup(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().alertPresent() && System.currentTimeMillis() < end) ;
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Wait up to a specified time for an alert to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void alertPresent(double seconds) {
        try {
            double timeTook = popup(seconds);
            checkAlertPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkAlertPresent(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an alert to not be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void alertNotPresent(double seconds) {
        double timeTook = noPopup(seconds);
        checkAlertNotPresent(seconds, timeTook);
        if (app.is().alertPresent()) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an alert to have text
     *
     * @param seconds - the number of seconds to wait
     */
    public void alertEquals(double seconds, String text) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupEquals(seconds - timeTook, text);
            checkAlertEquals(text, seconds, timeTook);
            if (!app.get().alert().equals(text)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkAlertEquals(text, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an alert to have text
     *
     * @param seconds - the number of seconds to wait
     */
    public void alertMatches(double seconds, String text) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupMatches(seconds - timeTook, text);
            checkAlertMatches(text, seconds, timeTook);
            if (!app.get().alert().matches(text)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkAlertMatches(text, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a confirmation to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void confirmationPresent(double seconds) {
        try {
            double timeTook = popup(seconds);
            checkConfirmationPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkConfirmationPresent(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a confirmation to not be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void confirmationNotPresent(double seconds) {
        double timeTook = noPopup(seconds);
        checkConfirmationNotPresent(seconds, timeTook);
        if (app.is().confirmationPresent()) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an confirmation to have text
     *
     * @param seconds - the number of seconds to wait
     */
    public void confirmationEquals(double seconds, String text) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupEquals(seconds - timeTook, text);
            checkConfirmationEquals(text, seconds, timeTook);
            if (!app.get().confirmation().equals(text)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkConfirmationEquals(text, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an confirmation to have text
     *
     * @param seconds - the number of seconds to wait
     */
    public void confirmationMatches(double seconds, String text) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupMatches(seconds - timeTook, text);
            checkConfirmationMatches(text, seconds, timeTook);
            if (!app.get().confirmation().matches(text)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkConfirmationMatches(text, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a prompt to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void promptPresent(double seconds) {
        try {
            double timeTook = popup(seconds);
            checkPromptPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkPromptPresent(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a prompt to not be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void promptNotPresent(double seconds) {
        double timeTook = noPopup(seconds);
        checkPromptNotPresent(seconds, timeTook);
        if (app.is().promptPresent()) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an confirmation to have text
     *
     * @param seconds - the number of seconds to wait
     */
    public void promptEquals(double seconds, String text) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupEquals(seconds - timeTook, text);
            checkPromptEquals(text, seconds, timeTook);
            if (!app.get().prompt().equals(text)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkPromptEquals(text, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for an confirmation to have text
     *
     * @param seconds - the number of seconds to wait
     */
    public void promptMatches(double seconds, String text) {
        try {
            double timeTook = popup(seconds);
            timeTook = popupMatches(seconds - timeTook, text);
            checkPromptMatches(text, seconds, timeTook);
            if (!app.get().prompt().matches(text)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkPromptMatches(text, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for text to be present on the page
     *
     * @param seconds - the number of seconds to wait
     * @param text    - the text to wait for
     */
    public void textPresent(double seconds, String text) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.is().textPresent(text) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkTextPresent(text, seconds, timeTook);
        if (!app.is().textPresent(text)) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for text to not be present on the page
     *
     * @param seconds - the number of seconds to wait
     * @param text    - the to wait to not be present
     */
    public void textNotPresent(double seconds, String text) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().textPresent(text) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkTextNotPresent(text, seconds, timeTook);
        if (app.is().textPresent(text)) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a cookie to be present on the page
     *
     * @param seconds            - the number of seconds to wait
     * @param expectedCookieName - the to wait to not be present
     */
    public void cookieExists(double seconds, String expectedCookieName) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!app.is().cookiePresent(expectedCookieName) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieExists(expectedCookieName, seconds, timeTook);
        if (!app.is().cookiePresent(expectedCookieName)) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a cookie to not be present on the page
     *
     * @param seconds              - the number of seconds to wait
     * @param unexpectedCookieName - the to wait to not be present
     */
    public void cookieNotExists(double seconds, String unexpectedCookieName) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().cookiePresent(unexpectedCookieName) && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieNotExists(unexpectedCookieName, seconds, timeTook);
        if (app.is().cookiePresent(unexpectedCookieName)) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a cookie to be present on the page with an expected value
     *
     * @param seconds    - the number of seconds to wait
     * @param cookieName - the to wait to not be present
     */
    public void cookieEquals(double seconds, String cookieName, String expectedCookieValue) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().cookiePresent(cookieName) && System.currentTimeMillis() < end) ;
        if (app.is().cookiePresent(cookieName)) {
            while (!app.get().cookieValue(cookieName).equals(expectedCookieValue) && System.currentTimeMillis() < end) ;
        }
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieEquals(cookieName, expectedCookieValue, seconds, timeTook);
        if (!app.is().cookiePresent(cookieName) || !app.get().cookieValue(cookieName).equals(expectedCookieValue)) {
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a cookie to be present on the page with an expected value
     *
     * @param seconds    - the number of seconds to wait
     * @param cookieName - the to wait to not be present
     */
    public void cookieMatches(double seconds, String cookieName, String expectedCookiePattern) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (app.is().cookiePresent(cookieName) && System.currentTimeMillis() < end) ;
        if (app.is().cookiePresent(cookieName)) {
            while (!app.get().cookieValue(cookieName).matches(expectedCookiePattern) && System.currentTimeMillis() < end)
                ;
        }
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkCookieEquals(cookieName, expectedCookiePattern, seconds, timeTook);
        if (!app.is().cookiePresent(cookieName) || !app.get().cookieValue(cookieName).matches(expectedCookiePattern)) {
            file.addError();
        }
    }

}