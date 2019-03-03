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
 * @version 3.1.0
 * @lastupdate 8/7/2018
 */
public class WaitFor {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private final WebDriver driver;

    // constants
    private static final String WAITED = "Waited ";
    private static final String UPTO = "Wait up to ";
    private static final String WAITING = "After waiting ";
    private double defaultWait = 5.0;

    public WaitFor(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.file = file;
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
     * Wait up to the default time (5 seconds) for an alert to be present
     */
    public void alertPresent() {
        alertPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a confirmation to be present
     */
    public void confirmationPresent() {
        confirmationPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a prompt to be present
     */
    public void promptPresent() {
        promptPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for a location to show in url
     *
     * @param location - the location to wait for
     */
    public void url(String location) {
        url(defaultWait, location);
    }

    /**
     * Wait up to the default time (5 seconds) for a title to show in the app
     *
     * @param title - the title to wait for
     */
    public void title(String title) {
        title(defaultWait, title);
    }

    /**
     * Wait up to the default time (5 seconds) for text to be present on the page
     *
     * @param text - the text to wait for
     */
    public void textPresent(String text) {
        textPresent(defaultWait, text);
    }

    /**
     * Wait up to the default time (5 seconds) for text to not be present on the page
     *
     * @param text - the text to wait for
     */
    public void textNotPresent(String text) {
        textNotPresent(defaultWait, text);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

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
        String action = UPTO + seconds + " seconds for an alert to be present";
        String expected = "An alert is present";
        try {
            double timetook = popup(seconds);
            file.recordAction(action, expected, WAITED + timetook + " seconds for an alert to be present",
                    Result.SUCCESS);
        } catch (TimeoutException e) {
            file.recordAction(action, expected, WAITING + seconds + " seconds, an alert is not present",
                    Result.FAILURE);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a confirmation to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void confirmationPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a confirmation to be present";
        String expected = "A confirmation is present";
        try {
            double timetook = popup(seconds);
            file.recordAction(action, expected, WAITED + timetook + " seconds for a confirmation to be present",
                    Result.SUCCESS);
        } catch (TimeoutException e) {
            file.recordAction(action, expected, WAITING + seconds + " seconds, a confirmation is not present",
                    Result.FAILURE);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for a prompt to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void promptPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a prompt to be present";
        String expected = "A prompt is present";
        try {
            double timetook = popup(seconds);
            file.recordAction(action, expected, WAITED + timetook + " seconds for a prompt to be present",
                    Result.SUCCESS);
        } catch (TimeoutException e) {
            file.recordAction(action, expected, WAITING + seconds + " seconds, a prompt is not present",
                    Result.FAILURE);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for the url to show a particular location
     *
     * @param seconds  - the number of seconds to wait
     * @param location - the location to wait for
     */
    public void url(double seconds, String location) {
        String action = UPTO + seconds + " seconds for url to show location <i>" + location + "</i>";
        String expected = "Location shows as <b>" + location + "</b>";
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(driver, (long) seconds);
            wait.until(ExpectedConditions.urlToBe(location));
            double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            file.recordAction(action, expected,
                    WAITED + timetook + " seconds for the location to show as <b>" + location + "</b>", Result.SUCCESS);
        } catch (TimeoutException e) {
            // No alert found in given time
            file.recordAction(action, expected,
                    WAITING + seconds + " seconds, the location shows as <b>" + driver.getCurrentUrl() + "</b>",
                    Result.FAILURE);
            file.addError();
        }
    }

    /**
     * Wait up to a specified time for the title to show up in the application
     *
     * @param seconds - the number of seconds to wait
     * @param title   - the title to wait for
     */
    public void title(double seconds, String title) {
        String action = UPTO + seconds + " seconds for title to show <i>" + title + "</i>";
        String expected = "Title shows as <b>" + title + "</b>";
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(driver, (long) seconds);
            wait.until(ExpectedConditions.titleIs(title));
            double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            file.recordAction(action, expected, WAITED + timetook + " seconds for the title to show as <b>" + title + "</b>",
                    Result.SUCCESS);
        } catch (TimeoutException e) {
            // No alert found in given time
            file.recordAction(action, expected,
                    WAITING + seconds + " seconds, the title shows as <b>" + driver.getTitle() + "</b>", Result.FAILURE);
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
        String action = UPTO + seconds + " seconds for page to contain text <i>" + text + "</i>";
        String expected = "Page contains text <b>" + text + "</b>";
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (!file.getApp().is().textPresent(text) && System.currentTimeMillis() < end) ;
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        if (file.getApp().is().textPresent(text)) {
            file.recordAction(action, expected, WAITED + timetook + " seconds for the page to contain text <b>" + text + "</b>",
                    Result.SUCCESS);
        } else {
            // No alert found in given time
            file.recordAction(action, expected,
                    WAITING + seconds + " seconds, the page does not show text <b>" + text + "</b>", Result.FAILURE);
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
        String action = UPTO + seconds + " seconds for page to not contain text <i>" + text + "</i>";
        String expected = "Page doesn't contain text <b>" + text + "</b>";
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (file.getApp().is().textPresent(text) && System.currentTimeMillis() < end) ;
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        if (!file.getApp().is().textPresent(text)) {
            file.recordAction(action, expected, WAITED + timetook + " seconds for the page to not contain text <b>" + text + "</b>",
                    Result.SUCCESS);
        } else {
            // No alert found in given time
            file.recordAction(action, expected,
                    WAITING + seconds + " seconds, the page shows text <b>" + text + "</b>", Result.FAILURE);
            file.addError();
        }
    }
}