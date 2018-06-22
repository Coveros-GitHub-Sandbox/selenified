/*
 * Copyright 2018 Coveros, Inc.
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

package com.coveros.selenified.element;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Result;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

/**
 * WaitFor performs dynamic waits on a particular element, until a particular
 * condition is met. Nothing is ever returned. The default wait is 5 seconds,
 * but can be overridden. If the condition is not met in the allotted time,
 * still nothing is returned, but an error is logged
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class WaitFor {

    private static final Logger log = Logger.getLogger(WaitFor.class);

    // what element are we trying to interact with on the page
    private final Element element;

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // constants
    private static final String WAITED = "Waited ";
    private static final String UPTO = "Wait up to ";
    private static final String WAITING = "After waiting ";
    private static final String SECONDS_FOR = " seconds for ";

    private static final String PRESENT = " to be present";
    private static final String DISPLAYED = " to be displayed";
    private static final String ENABLED = " to be enabled";

    private double defaultWait = 5.0;

    public WaitFor(Element element, OutputFile file) {
        this.element = element;
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
     * Wait up to the default time (5 seconds) for the element to be present
     */
    public void present() {
        present(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for the element to not be present
     */
    public void notPresent() {
        notPresent(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for the element to be displayed
     */
    public void displayed() {
        displayed(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for the element to not be
     * displayed
     */
    public void notDisplayed() {
        notDisplayed(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for the element to be enabled
     */
    public void enabled() {
        enabled(defaultWait);
    }

    /**
     * Wait up to the default time (5 seconds) for the element to not be enabled
     */
    public void notEnabled() {
        notEnabled(defaultWait);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * Wait up to a specified time for the element to be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void present(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + PRESENT;
        String expected = element.prettyOutputStart() + " is present";
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                // a drop down.
                element.getWebElement().getText();
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                log.info(e);
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        timetook = timetook / 1000;
        if (!element.is().present()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not present", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + PRESENT,
                Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for the element to no longer be present
     *
     * @param seconds - the number of seconds to wait
     */
    public void notPresent(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be present";
        String expected = element.prettyOutputStart() + " is not present";
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            if (!element.is().present()) {
                break;
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        timetook = timetook / 1000;
        if (element.is().present()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still present", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected,
                WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be present", Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for the element to be displayed
     *
     * @param seconds - the number of seconds to wait
     */
    public void displayed(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + DISPLAYED;
        String expected = element.prettyOutputStart() + " is displayed";
        double start = System.currentTimeMillis();
        if (!element.is().present()) {
            present(seconds);
            if (!element.is().present()) {
                return;
            }
        }
        WebElement webElement = element.getWebElement();
        if (!webElement.isDisplayed()) {
            // wait for up to XX seconds
            double end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (webElement.isDisplayed()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (!webElement.isDisplayed()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not displayed", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + DISPLAYED,
                Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for the element to no longer be displayed
     *
     * @param seconds - the number of seconds to wait
     */
    public void notDisplayed(double seconds) {
        // this might fail if the element disappears completely
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be displayed";
        String expected = element.prettyOutputStart() + " is not displayed";
        if (!element.is().present()) {
            file.recordAction(action, expected,
                    element.prettyOutputStart() + " is not present, and therefore not " + "displayed", Result.SUCCESS);
            return;
        }
        double start = System.currentTimeMillis();
        WebElement webElement = element.getWebElement();
        // wait for up to XX seconds
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            while (webElement.isDisplayed() && System.currentTimeMillis() < end) ;
        } catch (StaleElementReferenceException e) {
            log.info(e);
            file.recordAction(action, expected,
                    element.prettyOutput() + " has been removed from the page, and therefore not displayed",
                    Result.SUCCESS);
            return;
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (webElement.isDisplayed()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still displayed", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected,
                WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be displayed", Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for the element to be enabled
     *
     * @param seconds - the number of seconds to wait
     */
    public void enabled(double seconds) {
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + ENABLED;
        String expected = element.prettyOutputStart() + " is enabled";
        double start = System.currentTimeMillis();
        if (!element.is().present()) {
            present(seconds);
        }
        if (!element.is().enabled()) {
            WebElement webElement = element.getWebElement();
            // wait for up to XX seconds for the error message
            double end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                // If results have been returned, the results are displayed
                // in a drop down.
                if (webElement.isEnabled()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (!element.is().enabled()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not enabled", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + ENABLED,
                Result.SUCCESS);
    }

    /**
     * Wait up to a specified time for the element to no longer be enabled
     *
     * @param seconds - the number of seconds to wait
     */
    public void notEnabled(double seconds) {
        // this might fail if the element is no longer present
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be enabled";
        String expected = element.prettyOutputStart() + " is not enabled";
        if (!element.is().present()) {
            file.recordAction(action, expected,
                    element.prettyOutputStart() + " is not present, and therefore not " + "enabled", Result.SUCCESS);
            return;
        }
        double start = System.currentTimeMillis();
        WebElement webElement = element.getWebElement();
        // wait for up to XX seconds
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            while (webElement.isEnabled() && System.currentTimeMillis() < end) ;
        } catch (StaleElementReferenceException e) {
            log.info(e);
            file.recordAction(action, expected,
                    element.prettyOutput() + " has been removed from the page, and therefore not displayed",
                    Result.SUCCESS);
            return;
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (webElement.isEnabled()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still enabled", Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected,
                WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be enabled", Result.SUCCESS);
    }
}