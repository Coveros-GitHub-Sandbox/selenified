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

/**
 * State extends Asserts to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. State checks that elements are in a particular state.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 9/13/2018
 */
public interface State extends Check {

    // constants
    String PRESENT = " is present on the page";
    String NOTPRESENT = " is not present on the page";
    String DISPLAYED = " is displayed on the page";
    String NOTDISPLAYED = " is not displayed on the page";
    String CHECKED = " is checked on the page";
    String NOTCHECKED = " is not checked on the page";
    String EDITABLE = " is editable on the page";
    String NOTEDITABLE = " is not editable on the page";
    String ENABLED = " is enabled on the page";
    String NOTENABLED = " is not enabled on the page";

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element is present. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this verification as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    void present();

    default boolean checkPresent(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + PRESENT, waitFor);
        // perform the check
        boolean isPresent = getElement().is().present();
        // record the result
        if (isPresent) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + PRESENT, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTPRESENT, timeTook, Success.FAIL);
        }
        return isPresent;
    }

    /**
     * Verifies that the element is not present. If the element is present, it
     * waits up to the default time (5 seconds) for the element to be removed,
     * before marking this verification as a failure. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void notPresent();

    default boolean checkNotPresent(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + NOTPRESENT, waitFor);
        // perform the check
        boolean isPresent = getElement().is().present();
        // record the result
        if (isPresent) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + PRESENT, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTPRESENT, timeTook, Success.PASS);
        }
        return isPresent;
    }

    /**
     * Verifies that the element is displayed. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this verification as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    void displayed();

    default boolean checkDisplayed(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + DISPLAYED, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = getElement().is().displayed();
        // record the result
        if (!isDisplayed) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTDISPLAYED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + DISPLAYED, timeTook, Success.PASS);
        }
        return isDisplayed;
    }

    /**
     * Verifies that the element is not displayed. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    void notDisplayed();

    default boolean checkNotDisplayed(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + NOTDISPLAYED, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = getElement().is().displayed();
        // record the result
        if (!isDisplayed) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTDISPLAYED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + DISPLAYED, timeTook, Success.FAIL);
        }
        return isDisplayed;
    }

    /**
     * Verifies that the element is checked. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    void checked();

    default boolean checkChecked(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + CHECKED, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = getElement().is().checked();
        // record the result
        if (!isChecked) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTCHECKED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CHECKED, timeTook, Success.PASS);
        }
        return isChecked;
    }

    /**
     * Verifies that the element is not checked. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    void notChecked();

    default boolean checkNotChecked(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + NOTCHECKED, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = getElement().is().checked();
        // record the result
        if (!isChecked) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTCHECKED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CHECKED, timeTook, Success.FAIL);
        }
        return isChecked;
    }

    /**
     * Verifies that the element is editable. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this verification as a failure. If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    void editable();

    default boolean checkEditable(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + EDITABLE, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = getElement().is().editable();
        // record the result
        if (!isEditable) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTEDITABLE, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + EDITABLE, timeTook, Success.PASS);
        }
        return isEditable;
    }

    /**
     * Verifies that the element is not editable. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this verification as a failure. If the element isn't an input,
     * this will constitute a pass, as non input elements are not editable. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     */
    void notEditable();

    default boolean checkNotEditable(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + NOTEDITABLE, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = getElement().is().editable();
        // record the result
        if (!isEditable) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTEDITABLE, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + EDITABLE, timeTook, Success.FAIL);
        }
        return isEditable;
    }

    /**
     * Verifies that the element is enabled. If the element isn't present, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    void enabled();

    default boolean checkEnabled(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + ENABLED, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = getElement().is().enabled();
        // record the result
        if (!isEnabled) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTENABLED, timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + ENABLED, timeTook, Success.PASS);
        }
        return isEnabled;
    }

    /**
     * Verifies that the element is not enabled. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * verification as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    void notEnabled();

    default boolean checkNotEnabled(double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + NOTENABLED, waitFor);
        // verify the element is present
        if (!isPresent(waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = getElement().is().enabled();
        // record the result
        if (!isEnabled) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTENABLED, timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + ENABLED, timeTook, Success.FAIL);
        }
        return isEnabled;
    }
}