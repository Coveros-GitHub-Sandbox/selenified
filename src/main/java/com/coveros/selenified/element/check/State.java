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

import static com.coveros.selenified.utilities.Constants.*;

/**
 * State extends Check to provide some additional checking capabilities.
 * It will handle all checks performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each check to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. State checks that elements are in a particular state.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/20/2019
 */
public interface State extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element is present. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    void present();

    /**
     * Checks that the element is present. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element present
     */
    default boolean checkPresent(double waitFor, double timeTook) {
        // perform the check
        boolean isPresent = getElement().is().present();
        // record the result
        if (!isPresent) {
            getReporter().fail(getElement().prettyOutput() + IS_PRESENT, waitFor, getElement().prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_PRESENT, waitFor, getElement().prettyOutputStart() + IS_PRESENT, timeTook);
        }
        return isPresent;
    }

    /**
     * Checks that the element is not present. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void notPresent();

    /**
     * Checks that the element is not present. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not present
     */
    default boolean checkNotPresent(double waitFor, double timeTook) {
        // perform the check
        boolean isPresent = getElement().is().present();
        // record the result
        if (isPresent) {
            getReporter().fail(getElement().prettyOutput() + IS_NOT_PRESENT, waitFor, getElement().prettyOutputStart() + IS_PRESENT, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_NOT_PRESENT, waitFor, getElement().prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        }
        return !isPresent;
    }

    /**
     * Checks that the element is displayed. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    void displayed();

    /**
     * Checks that the element is displayed. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element displayed. if the element is not present, false be sent
     */
    default boolean checkDisplayed(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_DISPLAYED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = getElement().is().displayed();
        // record the result
        if (!isDisplayed) {
            getReporter().fail(getElement().prettyOutput() + IS_DISPLAYED, waitFor, getElement().prettyOutputStart() + IS_NOT_DISPLAYED, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_DISPLAYED, waitFor, getElement().prettyOutputStart() + IS_DISPLAYED, timeTook);
        }
        return isDisplayed;
    }

    /**
     * Checks that the element is not displayed. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    void notDisplayed();

    /**
     * Checks that the element is not displayed. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not displayed. if the element is not present, false be sent
     */
    default boolean checkNotDisplayed(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_NOT_DISPLAYED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = getElement().is().displayed();
        // record the result
        if (isDisplayed) {
            getReporter().fail(getElement().prettyOutput() + IS_NOT_DISPLAYED, waitFor, getElement().prettyOutputStart() + IS_DISPLAYED, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_NOT_DISPLAYED, waitFor, getElement().prettyOutputStart() + IS_NOT_DISPLAYED, timeTook);
        }
        return !isDisplayed;
    }

    /**
     * Check that the element is checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    void checked();

    /**
     * Check that the element is checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element checked. if the element is not present, false be sent
     */
    default boolean checkChecked(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_CHECKED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = getElement().is().checked();
        // record the result
        if (!isChecked) {
            getReporter().fail(getElement().prettyOutput() + IS_CHECKED, waitFor, getElement().prettyOutputStart() + IS_NOT_CHECKED, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_CHECKED, waitFor, getElement().prettyOutputStart() + IS_CHECKED, timeTook);
        }
        return isChecked;
    }

    /**
     * Checks that the element is not checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    void notChecked();

    /**
     * Checks that the element is not checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not checked. if the element is not present, false be sent
     */
    default boolean checkNotChecked(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_NOT_CHECKED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = getElement().is().checked();
        // record the result
        if (isChecked) {
            getReporter().fail(getElement().prettyOutput() + IS_NOT_CHECKED, waitFor, getElement().prettyOutputStart() + IS_CHECKED, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_NOT_CHECKED, waitFor, getElement().prettyOutputStart() + IS_NOT_CHECKED, timeTook);
        }
        return !isChecked;
    }

    /**
     * Checks that the element is editable. If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void editable();

    /**
     * Checks that the element is editable. Editable means an input type, and enabled.
     * If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element editable. if the element is not present, false be sent
     */
    default boolean checkEditable(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_EDITABLE, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = getElement().is().editable();
        // record the result
        if (!isEditable) {
            getReporter().fail(getElement().prettyOutput() + IS_EDITABLE, waitFor, getElement().prettyOutputStart() + IS_NOT_EDITABLE, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_EDITABLE, waitFor, getElement().prettyOutputStart() + IS_EDITABLE, timeTook);
        }
        return isEditable;
    }

    /**
     * Checks that the element is not editable. Editable means an input type,
     * and enabled. If the element isn't an input,
     * this will constitute a pass, as non input elements are not editable. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     */
    void notEditable();

    /**
     * Checks that the element is not editable. Editable means an input type,
     * and enabled. If the element isn't an input,
     * this will constitute a pass, as non input elements are not editable. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not editable. if the element is not present, false be sent
     */
    default boolean checkNotEditable(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_NOT_EDITABLE, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = getElement().is().editable();
        // record the result
        if (isEditable) {
            getReporter().fail(getElement().prettyOutput() + IS_NOT_EDITABLE, waitFor, getElement().prettyOutputStart() + IS_EDITABLE, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_NOT_EDITABLE, waitFor, getElement().prettyOutputStart() + IS_NOT_EDITABLE, timeTook);
        }
        return !isEditable;
    }

    /**
     * Checks that the element is enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    void enabled();

    /**
     * Checks that the element is enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element enabled. if the element is not present, false be sent
     */
    default boolean checkEnabled(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_ENABLED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = getElement().is().enabled();
        // record the result
        if (!isEnabled) {
            getReporter().fail(getElement().prettyOutput() + IS_ENABLED, waitFor, getElement().prettyOutputStart() + IS_NOT_ENABLED, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_ENABLED, waitFor, getElement().prettyOutputStart() + IS_ENABLED, timeTook);
        }
        return isEnabled;
    }

    /**
     * Checks that the element is not enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    void notEnabled();

    /**
     * Checks that the element is not enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not enabled. if the element is not present, false be sent
     */
    default boolean checkNotEnabled(double waitFor, double timeTook) {
        // check the element is present
        if (!isPresent(getElement().prettyOutput() + IS_NOT_ENABLED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = getElement().is().enabled();
        // record the result
        if (isEnabled) {
            getReporter().fail(getElement().prettyOutput() + IS_NOT_ENABLED, waitFor, getElement().prettyOutputStart() + IS_ENABLED, timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + IS_NOT_ENABLED, waitFor, getElement().prettyOutputStart() + IS_NOT_ENABLED, timeTook);
        }
        return !isEnabled;
    }
}