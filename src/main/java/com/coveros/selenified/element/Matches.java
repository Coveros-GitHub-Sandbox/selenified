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

package com.coveros.selenified.element;

import com.coveros.selenified.OutputFile;
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
public class Matches extends Assert {

    private static final String MATCH_PATTERN = " to match a pattern of <b>";

    Matches(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

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
    public void text(String expectedPattern) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + MATCH_PATTERN + expectedPattern + "</b>");
        // check for the object to the present on the page
        String elementText = element.get().text();
        if (!elementText.matches(expectedPattern)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementText + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementText + "</b>", Success.PASS);
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
    public void text(int row, int col, String pattern) {
        String column = " and column ";
        String within = " within element ";
        // wait for the table
        if (isPresentTable("Expected to find cell at row " + row + column + col + within + element.prettyOutput() +
                MATCH_PATTERN + pattern + "</b>")) {
            return;
        }
        // get the table cell pattern
        String actualText = element.get().tableCell(row, col).get().text();
        if (!actualText.matches(pattern)) {
            file.recordActual("Cell at row " + row + column + col + within + element.prettyOutput() +
                    " has the value of <b>" + actualText + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(
                "Cell at row " + row + column + col + within + element.prettyOutput() + " has the text of <b>" +
                        actualText + "</b>", Success.PASS);
    }

    /**
     * Verifies that the element's value matches the regular expression pattern
     * provided. If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    public void value(String expectedPattern) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + MATCH_PATTERN + expectedPattern + "</b>");
        // verify this is an input element
        if (!element.is().input()) {
            file.recordActual(element.prettyOutputStart() + " is not an input on the page", Success.FAIL);
            file.addError();
            return;
        }
        // get the element value
        String elementValue = element.get().value();
        if (!elementValue.matches(expectedPattern)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
    }

    /**
     * Verifies that the element's selected option matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input text of the element
     */
    public void selectedOption(String expectedPattern) {
        // wait for the select
        if (isPresentSelect(
                EXPECTED + element.prettyOutput() + " having a selected option to match a pattern " +
                        "of <b>" + expectedPattern + "</b>")) {
            return;
        }
        // get the selected text
        String elementText = element.get().selectedOption();
        if (!elementText.matches(expectedPattern)) {
            file.recordActual(element.prettyOutputStart() + OPTION + elementText + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + OPTION + elementText + "</b>", Success.PASS);
    }

    /**
     * Verifies that the element's selected value  matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    public void selectedValue(String expectedPattern) {
        // wait for the select
        if (isPresentSelect(
                EXPECTED + element.prettyOutput() + " having a selected value to match a pattern " +
                        "of <b>" + expectedPattern + "</b>")) {
            return;
        }
        // get the selected value
        String elementValue = element.get().selectedValue();
        if (!elementValue.matches(expectedPattern)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
    }
}