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

package com.coveros.selenified.element.check.waitFor;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.element.check.State;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.coveros.selenified.element.check.Constants.DEFAULT_POLLING_INTERVAL;

/**
 * WaitForState implements State to provide some additional wait capabilities.
 * It will handle all waits performed on the actual element. These
 * waits are custom to the framework. WaitForState waits for elements to be
 * in a particular state.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 9/13/2018
 */
public class WaitForState implements State {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // this is the element that all actions will be performed on
    private final Element element;

    // the default wait for the system
    private double defaultWait = 5.0;

    public WaitForState(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputFile getOutputFile() {
        return file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getElement() {
        return element;
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
     * Waits for the element to be present. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void present() {
        present(defaultWait);
    }

    /**
     * Waits for the element to not be present. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notPresent() {
        notPresent(defaultWait);
    }

    /**
     * Waits for the element to be displayed. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void displayed() {
        displayed(defaultWait);
    }

    /**
     * Waits for the element to not be displated. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notDisplayed() {
        notDisplayed(defaultWait);
    }

    /**
     * Waits for the element to be checked. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void checked() {
        checked(defaultWait);
    }

    /**
     * Waits for the element to not be checked. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notChecked() {
        notChecked(defaultWait);
    }

    /**
     * Waits for the element to be editable. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     * If the element isn't an input, this will
     * constitute a failure, same as it not being editable.
     */
    public void editable() {
        editable(defaultWait);
    }

    /**
     * Waits for the element to not be editable. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     * If the element isn't an input, this will
     * constitute a pass, same as it not being editable.
     */
    public void notEditable() {
        notEditable(defaultWait);
    }

    /**
     * Waits for the element to be enabled. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void enabled() {
        enabled(defaultWait);
    }

    /**
     * Waits for the element to not be enabled. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notEnabled() {
        notEditable(defaultWait);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    private double elementPresent(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element.defineByElement()));
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }

    /**
     * Waits for the element to be present. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void present(double seconds) {
        try {
            double timeTook = elementPresent(seconds);
            checkPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkPresent(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to not be present. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notPresent(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(element.defineByElement())));
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkNotPresent(seconds, timeTook);
        } catch (TimeoutException e) {
            checkNotPresent(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to be displayed. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void displayed(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.visibilityOfElementLocated(element.defineByElement()));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkDisplayed(seconds, timeTook);
        } catch (TimeoutException e) {
            checkDisplayed(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to not be displayed. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notDisplayed(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(element.defineByElement()));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkNotDisplayed(seconds, timeTook);
        } catch (TimeoutException e) {
            checkNotDisplayed(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to be checked. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void checked(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        elementPresent(seconds);
        while (!element.is().checked() && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkChecked(seconds, timeTook);
        if (!element.is().checked()) {
            file.addError();
        }
    }

    /**
     * Waits for the element to not be checked. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notChecked(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        elementPresent(seconds);
        while (element.is().checked() && System.currentTimeMillis() < end) ;
        double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        checkNotChecked(seconds, timeTook);
        if (element.is().checked()) {
            file.addError();
        }
    }

    /**
     * Waits for the element to be editable. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     * If the element isn't an input, this will
     * constitute a failure, same as it not being editable.
     */
    public void editable(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.elementToBeSelected(element.defineByElement()));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkEditable(seconds, timeTook);
        } catch (TimeoutException e) {
            checkEditable(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to not be editable. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     * If the element isn't an input, this will
     * constitute a pass, same as it not being editable.
     */
    public void notEditable(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(element.defineByElement())));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkNotEditable(seconds, timeTook);
        } catch (TimeoutException e) {
            checkNotEditable(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to be enabled. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void enabled(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.elementToBeClickable(element.defineByElement()));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkEnabled(seconds, timeTook);
        } catch (TimeoutException e) {
            checkEnabled(seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element to not be enabled. The default wait time will be used
     * and if the element isn't present after that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     */
    public void notEnabled(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element.defineByElement())));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkNotEnabled(seconds, timeTook);
        } catch (TimeoutException e) {
            checkNotEnabled(seconds, seconds);
            file.addError();
        }
    }
}