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
import com.coveros.selenified.element.check.State;
import com.coveros.selenified.utilities.Reporter;

/**
 * VerifyState implements State to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. State checks that elements are in a particular state.
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 6/25/2019
 */
public class VerifyState extends State {

    /**
     * The default constructor passing in the element and output file
     *
     * @param element      - the element under test
     * @param reporter - the file to write all logging out to
     */
    public VerifyState(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element is present. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void present() {
        checkPresent(0, 0);
    }

    /**
     * Verifies that the element is not present. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void notPresent() {
        checkNotPresent(0, 0);
    }

    /**
     * Verifies that the element is displayed. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void displayed() {
        checkDisplayed(0, 0);
    }

    /**
     * Verifies that the element is not displayed. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void notDisplayed() {
        checkNotDisplayed(0, 0);
    }

    /**
     * Verifies that the element is checked. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void checked() {
        checkChecked(0, 0);
    }

    /**
     * Verifies that the element is not checked. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void notChecked() {
        checkNotChecked(0, 0);
    }

    /**
     * Verifies that the element is editable. If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void editable() {
        checkEditable(0, 0);
    }

    /**
     * Verifies that the element is not editable. If the element isn't an input,
     * this will constitute a pass, as non input elements are not editable. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     */
    public void notEditable() {
        checkNotEditable(0, 0);
    }

    /**
     * Verifies that the element is enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void enabled() {
        checkEnabled(0, 0);
    }

    /**
     * Verifies that the element is not enabled. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void notEnabled() {
        checkNotEnabled(0, 0);
    }
}