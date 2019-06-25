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
 * Matches extends Check to provide some additional checking capabilities.
 * It will handle all checks performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each check to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Matches checks that elements have a particular value associated
 * to them that fits the provided regular expression.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 6/25/2019
 */
abstract class Matches extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element's text matches the regular expression pattern provided. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedPattern - the expected pattern of the text of the element
     */
    abstract void text(String expectedPattern);

    /**
     * Checks that the element's text matches the regular expression pattern provided. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedPattern - the expected pattern of the text of the element
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the element. null will be returned if the element isn't present
     */
    String checkText(String expectedPattern, double waitFor, double timeTook) {
        // check for the object to the present on the page
        String elementText = this.element.get().text();
        // record the result
        if (elementText == null || !elementText.matches(expectedPattern)) {
            this.reporter.fail(this.element.prettyOutput() + MATCH_PATTERN + expectedPattern + "</b>", waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementText + "</b>", timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + MATCH_PATTERN + expectedPattern + "</b>", waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementText + "</b>", timeTook);
        }
        return elementText;
    }

    /**
     * Checks that the element's pattern in a particular cell matches the regular expression
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
    abstract void text(int row, int col, String pattern);

    /**
     * Checks that the element's pattern in a particular cell matches the regular expression
     * pattern provided. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row      - the number of the row in the table - note, row numbering
     *                 starts at 1, NOT 0
     * @param col      - the number of the column in the table - note, column
     *                 numbering starts at 1, NOT 0
     * @param pattern  - what pattern do we expect to be in the table cell
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the table cell. null will be returned if the element isn't present or a table, or table cell doesn't exist
     */
    String checkText(int row, int col, String pattern, double waitFor, double timeTook) {
        String column = " and column ";
        String within = " within element ";
        // record the action
        if (!isPresentTable("Expected to find cell at row " + row + column + col + within + this.element.prettyOutput() +
                MATCH_PATTERN + pattern + "</b>", waitFor)) {
            return null;
        }
        // get the table cell pattern
        String actualText = this.element.get().tableCell(row, col).get().text();
        // record the result
        if (!actualText.matches(pattern)) {
            this.reporter.fail("Expected to find cell at row " + row + column + col + within + this.element.prettyOutput() +
                    MATCH_PATTERN + pattern + "</b>", waitFor, "Cell at row " + row + column + col + within + this.element.prettyOutput() +
                    " has the value of <b>" + actualText + "</b>", timeTook);
        } else {
            this.reporter.pass("Expected to find cell at row " + row + column + col + within + this.element.prettyOutput() +
                            MATCH_PATTERN + pattern + "</b>", waitFor,
                    "Cell at row " + row + column + col + within + this.element.prettyOutput() + " has the text of <b>" +
                            actualText + "</b>", timeTook);
        }
        return actualText;
    }

    /**
     * Checks that the element's value matches the regular expression pattern
     * provided. If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    abstract void value(String expectedPattern);

    /**
     * Checks that the element's value matches the regular expression pattern
     * provided. If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual value of the element. null will be returned if the element isn't present or an input
     */
    String checkValue(String expectedPattern, double waitFor, double timeTook) {
        // record the action and check this is an input element
        if (!isPresentInput(this.element.prettyOutputStart() + " is not an input on the page", waitFor)) {
            return null;
        }
        // get the element value
        String elementValue = this.element.get().value();
        // record the result
        if (!elementValue.matches(expectedPattern)) {
            this.reporter.fail(this.element.prettyOutputStart() + " is not an input on the page", waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutputStart() + " is not an input on the page", waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }

    /**
     * Checks that the element's selected option matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input text of the element
     */
    abstract void selectedOption(String expectedPattern);

    /**
     * Checks that the element's selected option matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input text of the element
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual selected option of the element. null will be returned if the element isn't present or a select
     */
    String checkSelectedOption(String expectedPattern, double waitFor, double timeTook) {
        // record the action, and check it's a select
        if (!isPresentSelect(
                this.element.prettyOutput() + " having a selected option to match a pattern " +
                        "of <b>" + expectedPattern + "</b>", waitFor)) {
            return null;
        }
        // get the selected text
        String elementText = this.element.get().selectedOption();
        // record the result
        if (!elementText.matches(expectedPattern)) {
            this.reporter.fail(this.element.prettyOutput() + " having a selected option to match a pattern " +
                    "of <b>" + expectedPattern + "</b>", waitFor, this.element.prettyOutputStart() + HAS_OPTION + elementText + "</b>", timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + " having a selected option to match a pattern " +
                    "of <b>" + expectedPattern + "</b>", waitFor, this.element.prettyOutputStart() + HAS_OPTION + elementText + "</b>", timeTook);
        }
        return elementText;
    }

    /**
     * Checks that the element's selected value  matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    abstract void selectedValue(String expectedPattern);

    /**
     * Checks that the element's selected value  matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual selected value of the element. null will be returned if the element isn't present or a select
     */
    String checkSelectedValue(String expectedPattern, double waitFor, double timeTook) {
        // record the action, and check it's a select
        if (!isPresentSelect(
                this.element.prettyOutput() + " having a selected value to match a pattern " +
                        "of <b>" + expectedPattern + "</b>", waitFor)) {
            return null;
        }
        // get the selected value
        String elementValue = this.element.get().selectedValue();
        // record the result
        if (!elementValue.matches(expectedPattern)) {
            this.reporter.fail(this.element.prettyOutput() + " having a selected value to match a pattern " +
                    "of <b>" + expectedPattern + "</b>", waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + " having a selected value to match a pattern " +
                    "of <b>" + expectedPattern + "</b>", waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }
}