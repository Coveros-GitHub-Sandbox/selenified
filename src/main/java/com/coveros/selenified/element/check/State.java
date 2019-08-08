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
 * @version 3.2.1
 * @lastupdate 6/25/2019
 */
abstract class State extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element is present. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    abstract void present();

    /**
     * Checks that the element is present. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element present
     */
    boolean checkPresent(double waitFor, double timeTook) {
        // perform the check
        boolean isPresent = this.element.is().present();
        // record the result
        if (!isPresent) {
            this.reporter.fail(this.element.prettyOutput() + IS_PRESENT, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_PRESENT, waitFor, this.element.prettyOutputStart() + IS_PRESENT, timeTook);
        }
        return isPresent;
    }

    /**
     * Checks that the element is not present. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void notPresent();

    /**
     * Checks that the element is not present. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not present
     */
    boolean checkNotPresent(double waitFor, double timeTook) {
        // perform the check
        boolean isPresent = this.element.is().present();
        // record the result
        if (isPresent) {
            this.reporter.fail(this.element.prettyOutput() + IS_NOT_PRESENT, waitFor, this.element.prettyOutputStart() + IS_PRESENT, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_NOT_PRESENT, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        }
        return !isPresent;
    }

    /**
     * Checks that the element is displayed. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    abstract void displayed();

    /**
     * Checks that the element is displayed. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element displayed. if the element is not present, false be sent
     */
    boolean checkDisplayed(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_DISPLAYED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = this.element.is().displayed();
        // record the result
        if (!isDisplayed) {
            this.reporter.fail(this.element.prettyOutput() + IS_DISPLAYED, waitFor, this.element.prettyOutputStart() + IS_NOT_DISPLAYED, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_DISPLAYED, waitFor, this.element.prettyOutputStart() + IS_DISPLAYED, timeTook);
        }
        return isDisplayed;
    }

    /**
     * Checks that the element is not displayed. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    abstract void notDisplayed();

    /**
     * Checks that the element is not displayed. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not displayed. if the element is not present, false be sent
     */
    boolean checkNotDisplayed(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_NOT_DISPLAYED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isDisplayed = this.element.is().displayed();
        // record the result
        if (isDisplayed) {
            this.reporter.fail(this.element.prettyOutput() + IS_NOT_DISPLAYED, waitFor, this.element.prettyOutputStart() + IS_DISPLAYED, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_NOT_DISPLAYED, waitFor, this.element.prettyOutputStart() + IS_NOT_DISPLAYED, timeTook);
        }
        return !isDisplayed;
    }

    /**
     * Check that the element is checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    abstract void checked();

    /**
     * Check that the element is checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element checked. if the element is not present, false be sent
     */
    boolean checkChecked(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_CHECKED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = this.element.is().checked();
        // record the result
        if (!isChecked) {
            this.reporter.fail(this.element.prettyOutput() + IS_CHECKED, waitFor, this.element.prettyOutputStart() + IS_NOT_CHECKED, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_CHECKED, waitFor, this.element.prettyOutputStart() + IS_CHECKED, timeTook);
        }
        return isChecked;
    }

    /**
     * Checks that the element is not checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    abstract void notChecked();

    /**
     * Checks that the element is not checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not checked. if the element is not present, false be sent
     */
    boolean checkNotChecked(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_NOT_CHECKED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isChecked = this.element.is().checked();
        // record the result
        if (isChecked) {
            this.reporter.fail(this.element.prettyOutput() + IS_NOT_CHECKED, waitFor, this.element.prettyOutputStart() + IS_CHECKED, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_NOT_CHECKED, waitFor, this.element.prettyOutputStart() + IS_NOT_CHECKED, timeTook);
        }
        return !isChecked;
    }

    /**
     * Checks that the element is editable. If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    abstract void editable();

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
    boolean checkEditable(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_EDITABLE, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = this.element.is().editable();
        // record the result
        if (!isEditable) {
            this.reporter.fail(this.element.prettyOutput() + IS_EDITABLE, waitFor, this.element.prettyOutputStart() + IS_NOT_EDITABLE, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_EDITABLE, waitFor, this.element.prettyOutputStart() + IS_EDITABLE, timeTook);
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
    abstract void notEditable();

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
    boolean checkNotEditable(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_NOT_EDITABLE, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEditable = this.element.is().editable();
        // record the result
        if (isEditable) {
            this.reporter.fail(this.element.prettyOutput() + IS_NOT_EDITABLE, waitFor, this.element.prettyOutputStart() + IS_EDITABLE, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_NOT_EDITABLE, waitFor, this.element.prettyOutputStart() + IS_NOT_EDITABLE, timeTook);
        }
        return !isEditable;
    }

    /**
     * Checks that the element is enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    abstract void enabled();

    /**
     * Checks that the element is enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element enabled. if the element is not present, false be sent
     */
    boolean checkEnabled(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_ENABLED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = this.element.is().enabled();
        // record the result
        if (!isEnabled) {
            this.reporter.fail(this.element.prettyOutput() + IS_ENABLED, waitFor, this.element.prettyOutputStart() + IS_NOT_ENABLED, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_ENABLED, waitFor, this.element.prettyOutputStart() + IS_ENABLED, timeTook);
        }
        return isEnabled;
    }

    /**
     * Checks that the element is not enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    abstract void notEnabled();

    /**
     * Checks that the element is not enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     *
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return Boolean: is the element not enabled. if the element is not present, false be sent
     */
    boolean checkNotEnabled(double waitFor, double timeTook) {
        // check the element is present
        if (isNotPresent(this.element.prettyOutput() + IS_NOT_ENABLED, waitFor)) {
            return false;
        }
        // perform the check
        boolean isEnabled = this.element.is().enabled();
        // record the result
        if (isEnabled) {
            this.reporter.fail(this.element.prettyOutput() + IS_NOT_ENABLED, waitFor, this.element.prettyOutputStart() + IS_ENABLED, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + IS_NOT_ENABLED, waitFor, this.element.prettyOutputStart() + IS_NOT_ENABLED, timeTook);
        }
        return !isEnabled;
    }
}