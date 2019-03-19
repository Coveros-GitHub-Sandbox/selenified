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

package com.coveros.selenified.element.check.azzert;

import com.coveros.selenified.element.Element;
import com.coveros.selenified.element.check.State;
import com.coveros.selenified.utilities.Reporter;

import static com.coveros.selenified.element.check.Constants.EXPECTED_ELEMENT_NOT_PRESENT;
import static org.testng.AssertJUnit.assertTrue;

/**
 * AssertState implements State to provide some additional assertion capabilities.
 * It will handle all assertions performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each assertion to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. State checks that elements are in a particular state.
 *
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 3/7/2019
 */
public class AssertState implements State {

    // this will be the name of the file we write all commands out to
    private final Reporter file;

    // this is the element that all actions will be performed on
    private final Element element;

    public AssertState(Element element, Reporter file) {
        this.element = element;
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter getReporter() {
        return file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getElement() {
        return element;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Asserts that the element is present. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this assertion as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void present() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, checkPresent(0, 0));
    }

    /**
     * Asserts that the element is not present. If the element is present, it
     * waits up to the default time (5 seconds) for the element to be removed,
     * before marking this assertion as a failure. This information will be
     * logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void notPresent() {
        assertTrue("Unexpected Element Present", checkNotPresent(0, 0));
    }

    /**
     * Asserts that the element is displayed. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this assertion as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void displayed() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Not Displayed", checkDisplayed(0, 0));
    }

    /**
     * Asserts that the element is not displayed. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this assertion as a failure. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void notDisplayed() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Displayed", checkNotDisplayed(0, 0));
    }

    /**
     * Asserts that the element is checked. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this assertion as a failure. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     */
    public void checked() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Not Checked", checkChecked(0, 0));
    }

    /**
     * Asserts that the element is not checked. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this assertion as a failure. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     */
    public void notChecked() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Checked", checkNotChecked(0, 0));
    }

    /**
     * Asserts that the element is editable. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking
     * this assertion as a failure. If the element isn't an input, this will
     * constitute a failure, same as it not being editable. This information
     * will be logged and recorded, with a screenshot for traceability and added
     * debugging support.
     */
    public void editable() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Not Editable", checkEditable(0, 0));
    }

    /**
     * Asserts that the element is not editable. If the element isn't present,
     * it waits up to the default time (5 seconds) for the element, before
     * marking this assertion as a failure. If the element isn't an input,
     * this will constitute a pass, as non input elements are not editable. This
     * information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     */
    public void notEditable() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Editable", checkNotEditable(0, 0));
    }

    /**
     * Asserts that the element is enabled. If the element isn't present, it waits
     * up to the default time (5 seconds) for the element, before marking this
     * assertion as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void enabled() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Not Enabled", checkEnabled(0, 0));
    }

    /**
     * Asserts that the element is not enabled. If the element isn't present, it
     * waits up to the default time (5 seconds) for the element, before marking this
     * assertion as a failure. This information will be logged and recorded, with
     * a screenshot for traceability and added debugging support.
     */
    public void notEnabled() {
        assertTrue(EXPECTED_ELEMENT_NOT_PRESENT, getElement().is().present());
        assertTrue("Expected Element Enabled", checkNotEnabled(0, 0));
    }
}