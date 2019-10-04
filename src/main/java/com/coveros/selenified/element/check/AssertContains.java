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

import java.util.Arrays;
import java.util.Set;

import static com.coveros.selenified.utilities.Constants.*;
import static org.testng.AssertJUnit.*;

/**
 * AssertContains implements Contains to provide some additional assertion
 * capabilities. It will handle all assertions performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * assertion to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Contains checks that elements
 * have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 8/08/2019
 */
public class AssertContains extends Contains {

    /**
     * The default constructor passing in the element and output file
     *
     * @param element  - the element under test
     * @param reporter - the file to write all logging out to
     */
    public AssertContains(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Asserts that the element's class contains the provided expected class.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the expected class value
     */
    public void clazz(String expectedClass) {
        String clazz = checkClazz(expectedClass, 0, 0);
        assertTrue(NO_ELEMENT_FOUND, this.element.is().present());
        assertNotNull("No Class Attribute Exists", clazz);
        assertTrue("Class Mismatch: class of '" + clazz + DOES_NOT_CONTAIN + expectedClass + "'", clazz.contains(expectedClass));
    }

    /**
     * Asserts that the element contains the provided expected attribute. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedAttribute - the attribute to check for
     */
    public void attribute(String expectedAttribute) {
        Set<String> attributes = checkAttribute(expectedAttribute, 0, 0);
        assertTrue(NO_ELEMENT_FOUND, this.element.is().present());
        assertNotNull("No Attributes Retrievable", attributes);
        assertTrue("Attribute not found: element attributes of '" + String.join(",", attributes) +
                DOES_NOT_CONTAIN + expectedAttribute + "'", attributes.contains(expectedAttribute));
    }

    /**
     * Asserts that the element's text contains the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    public void text(String expectedText) {
        String text = checkText(expectedText, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, text);
        assertTrue("Text not found: element text of '" + text + DOES_NOT_CONTAIN + expectedText + "'", text.contains(expectedText));
    }

    /**
     * Asserts that the element's text contains the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 0
     * @param expectedText the expected value of the element
     */
    public void text(int row, int col, String expectedText) {
        String text = checkText(row, col, expectedText, 0, 0);
        assertNotNull(NO_ELEMENT_FOUND, text);
        assertTrue("Text not found: element text of '" + text + DOES_NOT_CONTAIN + expectedText + "'", text.contains(expectedText));
    }

    /**
     * Asserts that the element's value contains the provided expected value.
     * If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    @SuppressWarnings("squid:S2259")
    public void value(String expectedValue) {
        String value = checkValue(expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (value == null && this.element.is().present()) {
            reason = "Element not input";
        }
        assertNotNull(reason, value);
        assertTrue("Value not found: element value of '" + value + DOES_NOT_CONTAIN + expectedValue + "'", value.contains(expectedValue));
    }

    /**
     * Asserts that the element's options contains the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOption the option expected in the list
     */
    public void selectOption(String expectedOption) {
        String[] options = checkSelectOption(expectedOption, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (options == null && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, options);
        assertTrue("Option not found: element options of '" + String.join(",", options) +
                DOES_NOT_CONTAIN + expectedOption + "'", Arrays.asList(options).contains(expectedOption));
    }

    /**
     * Asserts that the element's options contains the provided expected value.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void selectValue(String expectedValue) {
        String[] values = checkSelectValue(expectedValue, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (values == null && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertNotNull(reason, values);
        assertTrue("Value not found: element values of '" + String.join(",", values) +
                DOES_NOT_CONTAIN + expectedValue + "'", Arrays.asList(values).contains(expectedValue));
    }

    /**
     * Asserts that the element has the expected number of options. If the
     * element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     */
    public void selectOptions(int numOfOptions) {
        int options = checkSelectOptions(numOfOptions, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (options < 0 && this.element.is().present()) {
            reason = ELEMENT_NOT_SELECT;
        }
        assertTrue(reason, options >= 0);
        assertEquals("Number of options mismatch", numOfOptions, options);
    }

    /**
     * Asserts that the element has the expected number of columns. If the
     * element isn't present or a table, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     */
    public void columns(int numOfColumns) {
        int columns = checkColumns(numOfColumns, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (columns < 0 && this.element.is().present()) {
            reason = "Element not table";
        }
        assertTrue(reason, columns >= 0);
        assertEquals("Number of columns mismatch", numOfColumns, columns);

    }

    /**
     * Asserts that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     */
    public void rows(int numOfRows) {
        int rows = checkRows(numOfRows, 0, 0);
        String reason = NO_ELEMENT_FOUND;
        if (rows < 0 && this.element.is().present()) {
            reason = "Element not table";
        }
        assertTrue(reason, rows >= 0);
        assertEquals("Number of rows mismatch", numOfRows, rows);
    }
}