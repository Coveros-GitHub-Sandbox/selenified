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

/**
 * VerifyContains implements Contains to provide some additional verification
 * capabilities. It will handle all verifications performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Contains checks that elements
 * have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.3.1
 * @lastupdate 8/08/2019
 */
public class VerifyContains extends Contains {

    /**
     * The default constructor passing in the element and output file
     *
     * @param element  - the element under test
     * @param reporter - the file to write all logging out to
     */
    public VerifyContains(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element's class contains the provided expected class.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the expected class value
     */
    public void clazz(String expectedClass) {
        checkClazz(expectedClass, 0, 0);
    }

    /**
     * Verifies that the element contains the provided expected attribute. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedAttribute - the attribute to check for
     */
    public void attribute(String expectedAttribute) {
        checkAttribute(expectedAttribute, 0, 0);
    }

    /**
     * Verifies that the element's text contains the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    public void text(String expectedText) {
        checkText(expectedText, 0, 0);
    }

    /**
     * Verifies that the element's text contains the provided expected text. If
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
        checkText(row, col, expectedText, 0, 0);
    }

    /**
     * Verifies that the element's value contains the provided expected value.
     * If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void value(String expectedValue) {
        checkValue(expectedValue, 0, 0);
    }

    /**
     * Verifies that the element's options contains the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOption the option expected in the list
     */
    public void selectOption(String expectedOption) {
        checkSelectOption(expectedOption, 0, 0);
    }

    /**
     * Verifies that the element's options contains the provided expected value.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void selectValue(String expectedValue) {
        checkSelectValue(expectedValue, 0, 0);
    }

    /**
     * Verifies that the element has the expected number of options. If the
     * element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     */
    public void selectOptions(int numOfOptions) {
        checkSelectOptions(numOfOptions, 0, 0);
    }

    /**
     * Verifies that the element has the expected number of columns. If the
     * element isn't present or a table, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     */
    public void columns(int numOfColumns) {
        checkColumns(numOfColumns, 0, 0);
    }

    /**
     * Verifies that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     */
    public void rows(int numOfRows) {
        checkRows(numOfRows, 0, 0);
    }
}