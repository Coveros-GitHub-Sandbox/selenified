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

package com.coveros.selenified.element.check;

import com.coveros.selenified.element.Element;
import com.coveros.selenified.utilities.Property;
import com.coveros.selenified.utilities.Reporter;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.coveros.selenified.utilities.Constants.*;

/**
 * Check will handle all verifications performed on the actual element. These
 * checks are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 8/08/2019
 */
abstract class Check {
    static final String AVAILABLE_TO_BE_SELECTED = "</b> available to be selected on the page";

    // this will be the name of the file we write all commands out to
    Reporter reporter;

    // this is the driver that will be used for all selenium actions
    Element element;

    // the default wait for the system
    double defaultWait = Property.getDefaultWait();

    // the default poll for elements
    long defaultPoll = Property.getDefaultPoll();

    /**
     * Changes the default wait time from 5.0 seconds to some custom number.
     *
     * @param seconds - how many seconds should WaitFor wait for the condition to be
     *                met
     */
    public void changeDefaultWait(double seconds) {
        defaultWait = seconds;
    }

    /**
     * Changes the default poll time from 500.0 milliseconds to some custom number.
     *
     * @param milliseconds - how many milliseconds should WaitFor wait between pollings
     *                     for the condition to be met
     */
    public void changeDefaultPoll(long milliseconds) {
        defaultPoll = milliseconds;
    }

    ///////////////////////////////////////////////////////
    // assertions about the element in general
    ///////////////////////////////////////////////////////

    /**
     * Determines if the element is present, and if it is not writes a failure out to the detailed log
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is present or not
     */
    boolean isNotPresent(String check, double waitFor) {
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, waitFor);
            return true;
        }
        return false;
    }

    /**
     * Determines if the element is an input element, and if it is not writes a failure out to the detailed log
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is an input or not
     */
    private boolean isInput(String check, double waitFor) {
        if (!this.element.is().input()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_INPUT, waitFor);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a select element, and if it is not writes a failure out to the detailed log
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is an select or not
     */
    private boolean isSelect(String check, double waitFor) {
        if (!this.element.is().select()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_SELECT, waitFor);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a table element, and if it is not writes a failure out to the detailed log
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is an table or not
     */
    private boolean isTable(String check, double waitFor) {
        if (!this.element.is().table()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_TABLE, waitFor);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a present, and if it is, it is an input.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is a select or not
     */
    boolean isNotPresentInput(String check, double waitFor) {
        // verify this is a select element
        return (isNotPresent(check, waitFor) || !isInput(check, waitFor));
    }

    /**
     * Determines if the element is a present, and if it is, it is a select.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is a select or not
     */
    boolean isNotPresentSelect(String check, double waitFor) {
        // verify this is a select element
        return (isNotPresent(check, waitFor) || !isSelect(check, waitFor));
    }

    /**
     * Determines if the element is a present, and if it is, it is a table.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is present and a table or not
     */
    boolean isNotPresentTable(String check, double waitFor) {
        // verify this is a select element
        return (isNotPresent(check, waitFor) || !isTable(check, waitFor));
    }

    /**
     * Determines if an element has the provided table cells or not. Writes
     * out the action and expected outcome to the detailed log. Action is only
     * logged if waitFor is greater than 0 (implying we are waiting)
     *
     * @param row     - what row are we looking for (index starting at 0)
     * @param col     - what column are we looking for (index starting at 0)
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the table cell exists or not
     */
    boolean doesCellNotExist(int row, int col, String check, double waitFor) {
        if (this.element.get().tableCell(row, col) == null) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " doesn't exist. These cell coordinates are out of bounds", waitFor);
            return true;
        }
        return false;
    }

    /**
     * Performs a simple check for the element to be present. The provided wait time will be used
     * and the total time the action took will be returned. Nohing will be logged, but this will
     * be used as part of other methods.
     *
     * @param seconds - how many seconds to wait for
     * @return double: the time waited
     */
    double elementPresent(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            WebDriverWait wait = new WebDriverWait(this.element.getDriver(), (long) seconds, Property.getDefaultPoll());
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.element.defineByElement()));
            return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
        } catch (TimeoutException e) {
            return seconds;
        }
    }
}