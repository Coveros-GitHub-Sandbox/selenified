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

import com.coveros.selenified.OutputFile.Success;

import static com.coveros.selenified.element.check.Constants.*;

/**
 * State extends Check to provide some additional checking capabilities.
 * It will handle all checks performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each check to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. State checks that elements are in a particular state.
 *
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 3/7/2019
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_PRESENT, waitFor);
        // perform the check
        boolean isPresent = getElement().is().present();
        // record the result
        if (isPresent) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_PRESENT, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_PRESENT, timeTook, Success.FAIL);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_NOT_PRESENT, waitFor);
        // perform the check
        boolean isPresent = getElement().is().present();
        // record the result
        if (isPresent) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_PRESENT, timeTook, Success.PASS);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_DISPLAYED, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = getElement().is().displayed();
        // record the result
        if (!isDisplayed) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_DISPLAYED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_DISPLAYED, timeTook, Success.PASS);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_NOT_DISPLAYED, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = getElement().is().displayed();
        // record the result
        if (!isDisplayed) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_DISPLAYED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_DISPLAYED, timeTook, Success.FAIL);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_CHECKED, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = getElement().is().checked();
        // record the result
        if (!isChecked) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_CHECKED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_CHECKED, timeTook, Success.PASS);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_NOT_CHECKED, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = getElement().is().checked();
        // record the result
        if (!isChecked) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_CHECKED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_CHECKED, timeTook, Success.FAIL);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_EDITABLE, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = getElement().is().editable();
        // record the result
        if (!isEditable) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_EDITABLE, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_EDITABLE, timeTook, Success.PASS);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_NOT_EDITABLE, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = getElement().is().editable();
        // record the result
        if (!isEditable) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_EDITABLE, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_EDITABLE, timeTook, Success.FAIL);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_ENABLED, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = getElement().is().enabled();
        // record the result
        if (!isEnabled) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_ENABLED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_ENABLED, timeTook, Success.PASS);
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
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + IS_NOT_ENABLED, waitFor);
        // check the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = getElement().is().enabled();
        // record the result
        if (!isEnabled) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_ENABLED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_ENABLED, timeTook, Success.FAIL);
        }
        return !isEnabled;
    }
}