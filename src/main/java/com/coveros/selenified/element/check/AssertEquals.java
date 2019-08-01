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
import com.coveros.selenified.element.check.Equals;
import com.coveros.selenified.utilities.Reporter;

import java.util.Arrays;

import static com.coveros.selenified.utilities.Constants.ELEMENT_NOT_SELECT;
import static com.coveros.selenified.utilities.Constants.NO_ELEMENT_FOUND;
import static org.testng.AssertJUnit.*;

/**
 * AssertEquals implements Equals to provide some additional assertion capabilities.
 * It will handle all assertions performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each assertion to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Equals checks that elements have a particular value associated
 * to them.
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 6/25/2019
 */
public class AssertEquals extends Equals {

    /**
     * The default constructor passing in the element and output file
     *
     * @param element      - the element under test
     * @param reporter - the file to write all logging out to
     */
    public AssertEquals(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Asserts that the element has a the expected number of matches on the page, e.g.
     * how many elements match the locator and target provided.
     * This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedMatches the expected number of elements matching the locator
     */
    public void matches(int expectedMatches) {
        assertEquals("Element Match Mismatch", expectedMatches, checkMatches(expectedMatches, 0, 0));
    }

    /**
     * Asserts that the element's tag name equals the provided expected tag name. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    public void tagName(String expectedTagName) {
        String tagName = checkTagName(expectedTagName, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, tagName);
        assertEquals("Tag Name Mismatch", expectedTagName, tagName);
    }

    /**
     * Asserts that the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     */
    public void cssValue(String attribute, String expectedValue) {
        String cssValue = checkCssValue(attribute, expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (cssValue == null && expectedValue != null && this.element.is().present()) {
            reason = "CSS attribute not found";
        }
        assertFalse(reason, cssValue == null && expectedValue != null);
        assertEquals("CSS Value Mismatch", expectedValue, cssValue);
    }

    /**
     * Asserts that the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     */
    public void clazz(String expectedClass) {
        assertEquals("Class Mismatch", expectedClass, checkClazz(expectedClass, 0, 0));
    }

    /**
     * Asserts that the element has an attribute with a value equals to the
     * value provided. If the element isn't present, or the element does not
     * have the attribute, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue the expected value of the passed attribute of the element
     */
    public void attribute(String attribute, String expectedValue) {
        String value = checkAttribute(attribute, expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (value == null && this.element.is().present()) {
            reason = "Attribute doesn't exist";
        }
        assertNotNull(reason, value);
        assertEquals("Attribute Mismatch", expectedValue, value);
    }

    /**
     * Asserts that the element's text equals the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    public void text(String expectedText) {
        String text = checkText(expectedText, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, text);
        assertEquals("Text Mismatch", expectedText, text);
    }

    /**
     * Asserts that the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 1, NOT 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 1, NOT 0
     * @param expectedText - what text do we expect to be in the table cell
     */
    public void text(int row, int col, String expectedText) {
        String text = checkText(row, col, expectedText, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (text == null && this.element.is().present()) {
            reason = "Element not table";
        }
        assertNotNull(reason, text);
        assertEquals("Text Mismatch", expectedText, text);
    }

    /**
     * Asserts that the element's value equals the provided expected value. If
     * the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void value(String expectedValue) {
        String value = checkValue(expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (value == null && this.element.is().present()) {
            reason = "Element not input";
        }
        assertNotNull(reason, value);
        assertEquals("Value Mismatch", expectedValue, value);
    }

    /**
     * Asserts that the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected input text of the element
     */
    public void selectedOption(String expectedText) {
        String option = checkSelectedOption(expectedText, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (option == null && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, option);
        assertEquals("Selected Option Mismatch", expectedText, option);
    }

    /**
     * Asserts that the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void selectedValue(String expectedValue) {
        String value = checkSelectedValue(expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (value == null && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, value);
        assertEquals("Selected Value Mismatch", expectedValue, value);
    }

    /**
     * Asserts that the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions the expected input value of the element
     */
    public void selectOptions(String... expectedOptions) {
        String[] options = checkSelectOptions(expectedOptions, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (options == null && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, options);
        assertEquals("Selected Options Mismatch", Arrays.asList(expectedOptions), Arrays.asList(options));
    }

    /**
     * Asserts that the element's select values equal the provided expected
     * values. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValues the expected input value of the element
     */
    public void selectValues(String... expectedValues) {
        String[] values = checkSelectValues(expectedValues, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (values == null && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, values);
        assertEquals("Selected Values Mismatch", Arrays.asList(expectedValues), Arrays.asList(values));
    }
}