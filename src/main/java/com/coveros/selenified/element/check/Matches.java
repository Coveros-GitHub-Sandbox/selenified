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
 * Matches extends Asserts to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Matches checks that elements have a particular value associated
 * to them that fits the provided regular expression.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/21/2019
 */
public interface Matches extends Check {

    String MATCH_PATTERN = " to match a pattern of <b>";

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element's text matches the regular expression pattern provided. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedPattern the expected pattern of the text of the element
     */
    void text(String expectedPattern);

    default String checkText(String expectedPattern, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + MATCH_PATTERN + expectedPattern + "</b>", waitFor);
        // check for the object to the present on the page
        String elementText = getElement().get().text();
        // record the result
        if (elementText == null || !elementText.matches(expectedPattern)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + VALUE + elementText + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + VALUE + elementText + "</b>", timeTook, Success.PASS);
        }
        return elementText;
    }

    /**
     * Verifies that the element's pattern in a particular cell matches the regular expression
     * pattern provided. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row     - the number of the row in the table - note, row numbering
     *                starts at 1, NOT 0
     * @param col     - the number of the column in the table - note, column
     *                numbering starts at 1, NOT 0
     * @param pattern - what pattern do we expect to be in the table cell
     */
    void text(int row, int col, String pattern);

    default String checkText(int row, int col, String pattern, double waitFor, double timeTook) {
        String column = " and column ";
        String within = " within element ";
        // record the action
        if (!isPresentTable("Expected to find cell at row " + row + column + col + within + getElement().prettyOutput() +
                MATCH_PATTERN + pattern + "</b>", waitFor)) {
            return null;
        }
        // get the table cell pattern
        String actualText = getElement().get().tableCell(row, col).get().text();
        // record the result
        if (!actualText.matches(pattern)) {
            getOutputFile().recordActual("Cell at row " + row + column + col + within + getElement().prettyOutput() +
                    " has the value of <b>" + actualText + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(
                    "Cell at row " + row + column + col + within + getElement().prettyOutput() + " has the text of <b>" +
                            actualText + "</b>", timeTook, Success.PASS);
        }
        return actualText;
    }

    /**
     * Verifies that the element's value matches the regular expression pattern
     * provided. If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    void value(String expectedPattern);

    default String checkValue(String expectedPattern, double waitFor, double timeTook) {
        // record the action and verify this is an input element
        if (!isPresentInput(getElement().prettyOutputStart() + " is not an input on the page", waitFor)) {
            return null;
        }
        // get the element value
        String elementValue = getElement().get().value();
        // record the result
        if (!elementValue.matches(expectedPattern)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + VALUE + elementValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + VALUE + elementValue + "</b>", timeTook, Success.PASS);
        }
        return elementValue;
    }

    /**
     * Verifies that the element's selected option matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input text of the element
     */
    void selectedOption(String expectedPattern);

    default String checkSelectedOption(String expectedPattern, double waitFor, double timeTook) {
        // record the action, and verify it's a select
        if (!isPresentSelect(
                getElement().prettyOutput() + " having a selected option to match a pattern " +
                        "of <b>" + expectedPattern + "</b>", waitFor)) {
            return null;
        }
        // get the selected text
        String elementText = getElement().get().selectedOption();
        // record the result
        if (!elementText.matches(expectedPattern)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + OPTION + elementText + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + OPTION + elementText + "</b>", timeTook, Success.PASS);
        }
        return elementText;
    }

    /**
     * Verifies that the element's selected value  matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    void selectedValue(String expectedPattern);

    default String checkSelectedValue(String expectedPattern, double waitFor, double timeTook) {
        // record the action, and verify it's a select
        if (!isPresentSelect(
                getElement().prettyOutput() + " having a selected value to match a pattern " +
                        "of <b>" + expectedPattern + "</b>", waitFor)) {
            return null;
        }
        // get the selected value
        String elementValue = getElement().get().selectedValue();
        // record the result
        if (!elementValue.matches(expectedPattern)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + VALUE + elementValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + VALUE + elementValue + "</b>", timeTook, Success.PASS);
        }
        return elementValue;
    }
}