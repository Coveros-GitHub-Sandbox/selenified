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
import com.coveros.selenified.element.check.Excludes;
import com.coveros.selenified.utilities.Reporter;

import java.util.Arrays;
import java.util.Set;

import static com.coveros.selenified.element.check.Constants.*;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * AssertExcludes implements Excludes to provide some additional assertion
 * capabilities. It will handle all assertions performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * assertion to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Excludes checks that elements
 * don't have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 3/7/2019
 */
public class AssertExcludes implements Excludes {

    // this will be the name of the file we write all commands out to
    private final Reporter reporter;

    // this is the element that all actions will be performed on
    private final Element element;

    public AssertExcludes(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter getReporter() {
        return reporter;
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
     * Asserts that the element's class does not contain the provided expected
     * class. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedClass - the unexpected class value
     */
    public void clazz(String unexpectedClass) {
        String clazz = checkClazz(unexpectedClass, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, clazz);
        assertFalse("Class Mismatch: class of '" + clazz + CONTAINS + unexpectedClass + "'", clazz.contains(unexpectedClass));
    }

    /**
     * Asserts that the element does not contain the provided expected
     * attribute. If the element isn't present, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedAttribute - the attribute to check for
     */
    public void attribute(String expectedAttribute) {
        Set<String> attributes = checkAttribute(expectedAttribute, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, attributes);
        assertFalse("Attribute found: element attributes of '" + String.join(",", attributes) +
                CONTAINS + expectedAttribute + "'", attributes.contains(expectedAttribute));
    }

    /**
     * Asserts that the element's text does not contain the provided expected
     * text. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected text of the element
     */
    public void text(String expectedText) {
        String text = checkText(expectedText, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, text);
        assertFalse("Text found: element text of '" + text + CONTAINS + expectedText + "'", text.contains(expectedText));
    }

    /**
     * Asserts that the element's value does not contain the provided expected
     * value. If the element isn't present or an input, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    @SuppressWarnings("squid:S2259")
    public void value(String expectedValue) {
        String value = checkValue(expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (value == null && getElement().is().present()) {
            reason = "Element not input";
        }
        assertNotNull(reason, value);
        assertFalse("Value found: element value of '" + value + CONTAINS + expectedValue + "'", value.contains(expectedValue));
    }

    /**
     * Asserts that the element's options do not contain the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOption the option not expected in the list
     */
    public void selectOption(String expectedOption) {
        String[] options = checkSelectOption(expectedOption, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (options == null && getElement().is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, options);
        assertFalse("Option found: element options of '" + String.join(",", options) +
                CONTAINS + expectedOption + "'", Arrays.asList(options).contains(expectedOption));
    }

    /**
     * Asserts that the element's options do not contain the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the unexpected input value of the element
     */
    public void selectValue(String expectedValue) {
        String[] values = checkSelectValue(expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (values == null && getElement().is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, values);
        assertFalse("Value found: element values of '" + String.join(",", values) +
                CONTAINS + expectedValue + "'", Arrays.asList(values).contains(expectedValue));
    }
}