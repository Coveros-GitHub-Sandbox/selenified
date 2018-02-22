/*
 * Copyright 2017 Coveros, Inc.
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
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

/**
 * WaitFor performs dynamic waits on the app in general, until a particular
 * condition of the application is met, not one for a particular page or
 * element. Nothing is ever returned. The default wait is 5 seconds, but can be
 * overridden. If the condition is not met in the allotted time, still nothing
 * is returned, but an error is logged
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class WaitFor {

    private static final Logger log = Logger.getLogger(WaitFor.class);

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private final WebDriver driver;

    // the is class to determine if something exists, so the wait can end
    private final Is is;

    // constants
    private static final String WAITED = "Waited ";
    private static final String UPTO = "Wait up to ";
    private static final String WAITING = "After waiting ";
    private double defaultWait = 5.0;

    public WaitFor(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.file = file;
        this.is = new Is(driver);
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
     */
    public void location(String location) {
        location(defaultWait, location);
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
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                // a drop down.
                driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e) {
                log.info(e);
            }
        }
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
        double timetook = popup(seconds);
        if (!is.alertPresent()) {
            file.recordAction(action, expected, WAITING + timetook + " seconds, an alert is not present",
                    Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for an alert to be present", Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for a confirmation to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void confirmationPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a confirmation to be present";
        String expected = "A confirmation is present";
        double timetook = popup(seconds);
        if (!is.confirmationPresent()) {
            file.recordAction(action, expected, WAITING + timetook + " seconds, a confirmation is not present",
                    Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for a confirmation to be present",
                Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for a prompt to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void promptPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a prompt to be present";
        String expected = "A prompt is present";
        double timetook = popup(seconds);
        if (!is.promptPresent()) {
            file.recordAction(action, expected, WAITING + timetook + " seconds, a prompt is not present",
                    Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for a prompt to be present", Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for the url to show a particular location
     *
     * @param seconds - the number of seconds to wait
     * @param location - the location to wait for
     */
    public void location(double seconds, String location) {
        String action = UPTO + seconds + " seconds for url to show location";
        String expected = "Location shows as '" + location + "'";
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            if (location.equals(driver.getCurrentUrl())) {
                break;
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        if (!is.location(location)) {
            file.recordAction(action, expected,
                    WAITING + timetook + " seconds, a the location still shows as '" + location + "'", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected,
                WAITED + timetook + " seconds for the location to show as '" + location + "'", Result.SUCCESS);
    }
}