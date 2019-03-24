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
import com.coveros.selenified.utilities.Reporter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.coveros.selenified.element.check.Constants.*;

/**
 * Check will handle all verifications performed on the actual element. These
 * checks are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/19/2019
 */
interface Check {

    /**
     * Retrieves the output file that we write all details out to
     *
     * @return Reporter
     */
    Reporter getReporter();

    /**
     * Retrieves the element that is used for all selenium actions and checks
     *
     * @return Element
     */
    Element getElement();

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
    default boolean isPresent(String check, double waitFor) {
        if (!getElement().is().present()) {
            getReporter().fail(check, waitFor, getElement().prettyOutputStart() + IS_NOT_PRESENT, waitFor);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is an input element, and if it is not writes a failure out to the detailed log
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is an input or not
     */
    default boolean isInput(String check, double waitFor) {
        if (!getElement().is().input()) {
            getReporter().fail(check, waitFor, getElement().prettyOutputStart() + IS_NOT_INPUT, waitFor);
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
    default boolean isSelect(String check, double waitFor) {
        if (!getElement().is().select()) {
            getReporter().fail(check, waitFor, getElement().prettyOutputStart() + IS_NOT_SELECT, waitFor);
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
    default boolean isTable(String check, double waitFor) {
        if (!getElement().is().table()) {
            getReporter().fail(check, waitFor, getElement().prettyOutputStart() + IS_NOT_TABLE, waitFor);
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
    default boolean isPresentInput(String check, double waitFor) {
        // verify this is a select element
        return (isPresent(check, waitFor) && isInput(check, waitFor));
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
    default boolean isPresentSelect(String check, double waitFor) {
        // verify this is a select element
        return (isPresent(check, waitFor) && isSelect(check, waitFor));
    }

    /**
     * Determines if the element is a present, and if it is, it is a table.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
     *
     * @param check   - the check being performed
     * @param waitFor - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @return Boolean: whether the element is an table or not
     */
    default boolean isPresentTable(String check, double waitFor) {
        // verify this is a select element
        return (isPresent(check, waitFor) && isTable(check, waitFor));
    }

    /**
     * Performs a simple check for the element to be present. The provided wait time will be used
     * and the total time the action took will be returned. Nohing will be logged, but this will
     * be used as part of other methods.
     *
     * @param seconds - how many seconds to wait for
     * @return double: the time waited
     */
    default double elementPresent(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        WebDriverWait wait = new WebDriverWait(getElement().getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getElement().defineByElement()));
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }
}