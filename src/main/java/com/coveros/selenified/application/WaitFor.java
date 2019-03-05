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
import com.coveros.selenified.OutputFile.Result;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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
 * @version 3.0.5
 * @lastupdate 8/7/2018
 */
public class WaitFor implements Check {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // this is the driver that will be used for all selenium actions
    private final App app;

    // this is the driver that will be used for all selenium actions
    private final WebDriver driver;

    // constants
    private static final String WAITED = "Waited ";
    private static final String UPTO = "Wait up to ";
    private static final String WAITING = "After waiting ";
    private double defaultWait = 5.0;

    public WaitFor(App app, OutputFile file) {
        this.app = app;
        this.driver = app.getDriver();
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
//        titleMatches(defaultWait, title);
    }

    /**
     * Wait up to the default time (5 seconds) for text to be present in the app
     *
     * @param expectedText the expected text to be present
     */
    @Override
    public void textPresent(String expectedText) {
//        textPresent(defaultWait, expectedText);
    }

    /**
     * Wait up to the default time (5 seconds) for text to not be present in the app
     *
     * @param expectedText the expected text to be not present
     */
    @Override
    public void textNotPresent(String expectedText) {
//        textNotPresent(defaultWait, expectedText);
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
//        alertNotPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    @Override
    public void alertEquals(String expectedAlertText) {
//        alertEquals(defaultWait, expectedAlertText);
    }

    /**
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    @Override
    public void alertMatches(String expectedAlertPattern) {
//        alertMatches(defaultWait, expectedAlertPattern);
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
//        confirmationNotPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to be present
     */
    @Override
    public void confirmationEquals(String expectedConfirmationText) {
//        confirmationEquals(defaultWait, expectedConfirmationText);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to not be present
     */
    @Override
    public void confirmationMatches(String expectedConfirmationPattern) {
//        confirmationMatches(defaultWait, expectedConfirmationPattern);
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
//        promptNotPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    @Override
    public void promptEquals(String expectedPromptText) {
//        promptEquals(defaultWait, expectedPromptText);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    @Override
    public void promptMatches(String expectedPromptPattern) {
//        promptMatches(defaultWait, expectedPromptPattern);
    }

    @Override
    public void cookieExists(String expectedCookieName) {
//        cookieExists(defaultWait, expectedCookieName);
    }

    @Override
    public void cookieNotExists(String unexpectedCookieName) {
//        cookieNotExists((defaultWait, unexpectedCookieName);
    }

    @Override
    public void cookieEquals(String cookieName, String expectedCookieValue) {
//        cookieEquals(defaultWait, cookieName, expectedCookieValue);
    }

    @Override
    public void cookieMatches(String cookieName, String expectedCookiePattern) {
//        cookieMatches((defaultWait, cookieName, expectedCookiePattern);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * Wait up to a specified time for the url to show a particular expectedURL
     *
     * @param seconds  - the number of seconds to wait
     * @param expectedURL - the expectedURL to wait for
     */
    public void urlEquals(double seconds, String expectedURL) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(driver, (long) seconds);
            wait.until(ExpectedConditions.urlToBe(expectedURL));
            double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkUrlEquals(expectedURL, "After waiting for " + timetook + " seconds, t");
        } catch (TimeoutException e) {
            checkUrlEquals(expectedURL, "After waiting for " + seconds + " seconds, t");
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
            WebDriverWait wait = new WebDriverWait(driver, (long) seconds);
            wait.until(ExpectedConditions.titleIs(title));
            double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkTitleEquals(title, "After waiting for " + timetook + " seconds, t");
        } catch (TimeoutException e) {
            checkTitleEquals(title, "After waiting for " + seconds + " seconds, t");
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
        WebDriverWait wait = new WebDriverWait(driver, (long) seconds);
        wait.until(ExpectedConditions.alertIsPresent());
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        return timetook / 1000;
    }

    /**
     * Wait up to a specified time for an alert to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void alertPresent(double seconds) {
        try {
            double timetook = popup(seconds);
            checkAlertPresent("After waiting for " + timetook + " seconds, a");
        } catch (TimeoutException e) {
            checkAlertPresent("After waiting for " + seconds + " seconds, a");
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
            double timetook = popup(seconds);
            checkConfirmationPresent("After waiting for " + timetook + " seconds, a");
        } catch (TimeoutException e) {
            checkConfirmationPresent("After waiting for " + seconds + " seconds, a");
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
            double timetook = popup(seconds);
            checkPromptPresent("After waiting for " + timetook + " seconds, a");
        } catch (TimeoutException e) {
            checkPromptPresent("After waiting for " + seconds + " seconds, a");
            file.addError();
        }
    }
}

/*
TODO
 - finish waitFor class (uncomment and fill out)
 - move assert waits into own class
 - expand out on tests in assert wait class
 - consider adding action in for recording waits
 - update java docs
 ** model/follow same pattern for element
 */