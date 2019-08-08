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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.coveros.selenified.utilities.Constants.*;

/**
 * Excludes extends Check to provide some additional checking
 * capabilities. It will handle all checks performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * check to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Excludes checks that elements
 * don't have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 6/25/2019
 */
abstract class Excludes extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element's class does not contain the provided expected
     * class. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedClass - the unexpected class value
     */
    abstract void clazz(String unexpectedClass);

    /**
     * Checks that the element's class does not contain the provided expected
     * class. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedClass - the unexpected class value
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual class of the element. null will be returned if the element isn't present
     */
    String checkClazz(String unexpectedClass, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " without class <b>" + unexpectedClass + ENDB;
        // check our classes
        String actualClass = this.element.get().attribute(CLASS);
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (actualClass == null || !actualClass.contains(unexpectedClass)) {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " does not contain a class value of <b>" + unexpectedClass + ENDB,
                    timeTook);
        } else {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + CLASS_VALUE + actualClass + "</b>, which contains <b>" +
                    unexpectedClass + ENDB, timeTook);
        }
        return actualClass;
    }

    /**
     * Checks that the element does not contain the provided expected
     * attribute. If the element isn't present, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     */
    abstract void attribute(String attribute);

    /**
     * Checks that the element does not contain the provided expected
     * attribute. If the element isn't present, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     * @param waitFor   - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook  - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the attributes of the element. null will be returned if the element isn't present
     */
    @SuppressWarnings("squid:S1168")
    Set<String> checkAttribute(String attribute, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " without attribute <b>" + attribute + ENDB;
        // record the action and get the attributes
        Map<String, String> atts = this.element.get().allAttributes();
        Set<String> allAttributes = new HashSet<>();
        if (atts != null) {
            allAttributes = atts.keySet();
        }
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (atts == null || !allAttributes.contains(attribute)) {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + ENDB +
                    " only the attributes " + String.join(", ", allAttributes) + ENDB, timeTook);
        } else {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " contains the attribute of <b>" + attribute + ENDB,
                    timeTook);
        }
        if (atts == null) {
            return null;
        } else {
            return allAttributes;
        }
    }

    /**
     * Checks that the element's text does not contain the provided expected
     * text. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    abstract void text(String expectedText);

    /**
     * Checks that the element's text does not contain the provided expected
     * text. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the element. null will be returned if the element isn't present
     */
    String checkText(String expectedText, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + EXCLUDES_TEXT + expectedText + ENDB;
        // check for the object to the present on the page
        String actualText = this.element.get().text();
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (actualText.contains(expectedText)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + HAS_TEXT + actualText + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + HAS_TEXT + actualText + ENDB, timeTook);
        }
        return actualText;
    }

    /**
     * Checks that the element's text in a particular cell excludes the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 0
     * @param expectedText - what text do we expect to be in the table cell
     */
    abstract void text(int row, int col, String expectedText);

    /**
     * Checks that the element's text in a particular cell excludes the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 0
     * @param expectedText - what text do we expect to be in the table cell
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the table cell. null will be returned if the element isn't present or a table, or table cell doesn't exist
     */
    @SuppressWarnings("squid:S1168")
    String checkText(int row, int col, String expectedText, double waitFor, double timeTook) {
        String column = " and column ";
        String within = " within element ";
        String check = "cell at row " + row + column + col + within + this.element.prettyOutput() +
                EXCLUDES_TEXT + expectedText + ENDB;
        // record the action, and check for table
        if (isNotPresentTable(check, waitFor) || !doesCellExist(row, col, check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the table cell text
        String actualText = this.element.get().tableCell(row, col).get().text();
        // record the result
        if (actualText.contains(expectedText)) {
            this.reporter.fail(check, waitFor, "Cell at row " + row + column + col + within + this.element.prettyOutput() +
                    HAS_TEXT + actualText + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, "Cell at row " + row + column + col + within + this.element.prettyOutput() +
                    HAS_TEXT + actualText + ENDB, timeTook);
        }
        return actualText;
    }

    /**
     * Checks that the element's value does not contain the provided expected
     * value. If the element isn't present or an input, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    abstract void value(String expectedValue);

    /**
     * Checks that the element's value does not contain the provided expected
     * value. If the element isn't present or an input, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual value of the element. null will be returned if the element isn't present or an input
     */
    String checkValue(String expectedValue, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + EXCLUDES_VALUE + expectedValue + ENDB;
        if (isNotPresentInput(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // record the action and get our value
        String actualValue = this.element.get().value();
        if (actualValue.contains(expectedValue)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + HAS_VALUE + actualValue + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + HAS_VALUE + actualValue + ENDB, timeTook);
        }
        return actualValue;
    }

    /**
     * Checks that the element's options do not contain the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param option the option not expected in the list
     */
    abstract void selectOption(String option);

    /**
     * Checks that the element's options do not contain the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param option   the option not expected in the list
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select options of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    String[] checkSelectOption(String option, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " without the option <b>" + option +
                AVAILABLE_TO_BE_SELECTED;
        // record the action, and check for select
        if (isNotPresentSelect(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // check for the object to the editable
        String[] allOptions = this.element.get().selectOptions();
        // record the result
        if (Arrays.asList(allOptions).contains(option)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " is editable and present and contains the option <b>" + option +
                    ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " is editable and present but does not contain the option <b>" + option +
                    ENDB, timeTook);
        }
        return allOptions;
    }

    /**
     * Checks that the element's options do not contain the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param selectValue the unexpected input value of the element
     */
    abstract void selectValue(String selectValue);

    /**
     * Checks that the element's options do not contain the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param selectValue the unexpected input value of the element
     * @param waitFor     - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook    - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select values of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    String[] checkSelectValue(String selectValue, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " without a select value of <b>" + selectValue +
                AVAILABLE_TO_BE_SELECTED;
        // record the action, and check for select
        if (isNotPresentSelect(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // check for the object to the present on the page
        String[] elementValues = this.element.get().selectValues();
        // record the result
        if (Arrays.asList(elementValues).contains(selectValue)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + CONTAINS_VALUE + selectValue + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + EXCLUDES_VALUE + selectValue + "</b>, only the values <b>" +
                    Arrays.toString(elementValues) + ENDB, timeTook);
        }
        return elementValues;
    }
}